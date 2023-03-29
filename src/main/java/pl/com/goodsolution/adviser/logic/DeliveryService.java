package pl.com.goodsolution.adviser.logic;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.com.goodsolution.adviser.domain.*;

import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class DeliveryService {
    private static final Logger log = LoggerFactory.getLogger(DeliveryService.class);
    private DeliveryJdbcRepository deliveryJdbcRepository;
    private DictionariesService dictionariesService;
    private CitiesService citiesService;
    private DriversService driversService;

    private DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-LL-dd HH:mm:ss.S");
    private DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-LL-dd");
    private DateTimeFormatter timeFormatterLong = DateTimeFormatter.ofPattern("H:mm:ss");
    private DateTimeFormatter timeFormatterShort = DateTimeFormatter.ofPattern("H:mm");

    public DeliveryService(DeliveryJdbcRepository deliveryJdbcRepository, DictionariesService dictionariesService, CitiesService citiesService, DriversService driversService) {
        this.deliveryJdbcRepository = deliveryJdbcRepository;
        this.dictionariesService = dictionariesService;
        this.citiesService = citiesService;
        this.driversService = driversService;
    }

    boolean checkIfDayIsExcluded(LocalDate date) {
        Long count = deliveryJdbcRepository.checkIfDayIsExcluded(date);
        if (count.longValue() == 0) {
            return false;
        } else {
            return true;
        }
    }

    // TODO hmm
    boolean checkIfDayIsExclusion(LocalDate date) {
        Long count = deliveryJdbcRepository.checkIfDayIsExclusion(date);
        if (count.longValue() == 0) {
            return false;
        } else {
            return true;
        }
    }

    Long getDeliveryMaxPriority(Long driverId, LocalDate date) {
        Long count = deliveryJdbcRepository.getDeliveryMaxPriorityCount(driverId, date);
        if (count.longValue() > 0) {
            return deliveryJdbcRepository.getDeliveryMaxPriority(driverId, date) + 1L;
        }
        return 1L;
    }

    LocalDate getReplaceDay(LocalDate date, Long orderProductId) {
        Long count = deliveryJdbcRepository.getDeliveryDateReplaceCount(date);
        if (count.longValue() == 0) {
            return date;
        }
        LocalDate order1day = LocalDate.now().plusDays(1);

        if (order1day.isEqual(date)) {
            return date;
        }

        //TODO is it correct?

        return date;
    }

    Long checkDeliveryForProduct(Long orderProductId) {
        return deliveryJdbcRepository.checkDeliveryForProduct(orderProductId);
    }

    void deleteDeliveryOrderForProductNotCancelled(Long orderProductId) {
        deliveryJdbcRepository.deleteDeliveryOrderForProductNotCancelled(orderProductId);
    }

    void stopOrderDelivery(Long orderProductId, LocalDate stopDate) {
        deliveryJdbcRepository.stopOrderDelivery(orderProductId, stopDate);
    }

    Long getDeliveredForProductCount(Long orderProductId) {
        return deliveryJdbcRepository.getDeliveredForProductCount(orderProductId);
    }

    LocalDate getLastDeliveryDate(Long orderProductId) {
        return deliveryJdbcRepository.getLastDeliveryDate(orderProductId);
    }

    Long getAllDeliveryDaysCount(Long orderProductId) {
        return deliveryJdbcRepository.getAllDeliveryDaysCount(orderProductId);
    }

    void createDeliveryChangeLog(OrderDeliveryChangeLogData data) {
        deliveryJdbcRepository.createDeliveryChangeLog(data);
    }

    void stopDeliveryUpdate(Long deliveryId) {
        deliveryJdbcRepository.stopDeliveryUpdate(deliveryId);
    }

    void startDeliveryUpdate(Long deliveryId, LocalDate date, boolean sunday, LocalDate originalDate) {
        deliveryJdbcRepository.startDeliveryUpdate(deliveryId, date, sunday, originalDate);
    }

    void createDeliveryRelease(OrderDeliveryReleaseCreateData data) {
        deliveryJdbcRepository.createDeliveryRelease(data);
    }

    boolean checkDeliveryInSunday(Long deliveryId, LocalDate date) {
        OrderDeliveryOrderData deliveryOrder = deliveryJdbcRepository.getDeliveryOrder(deliveryId);
        date = date.minusDays(1);
        Long count = deliveryJdbcRepository.checkDeliveryInSundayCount(deliveryOrder.getOrderId(), date);
        if (count.intValue() == 0) {
            return false;
        }
        return true;
    }

    OrderDeliveryOrderData getDeliveryOrder(Long deliveryId) {
        return deliveryJdbcRepository.getDeliveryOrder(deliveryId);
    }

    public List<OrderDeliveryData> findReleasedDeliveriesForOrder(Long orderId, OrderDetailsData orderData) {
        List<Map<String, Object>> rows = deliveryJdbcRepository.findReleasedDeliveriesForOrder(orderId);
        log.debug("released deliveries rows.size(): " + rows.size());
        List<OrderDeliveryData> list = new ArrayList<>();
        long no = 1;
        for (Map row : rows) {
            DayOfWeek dayOfWeek = LocalDate.parse(String.valueOf(row.get("ddate")), dateFormatter).getDayOfWeek();
            OrderDeliveryData data = new OrderDeliveryData(
                    Long.valueOf(String.valueOf(row.get("id"))), //Long id,
                    no++,// Long no,
                    row.get("name") + " (" + row.get("category_name") + ")",// String productName,
                    String.valueOf(row.get("driver") == null ? "" : row.get("driver")), // String driver,
                    prepareHourFrom(row, dayOfWeek, orderData),// LocalTime hourFrom,
                    prepareHourTo(row, dayOfWeek, orderData)// LocalTime hourTo
            );
            list.add(data);
        }
        return list;

    }

    // TODO duplicated order service
    private LocalTime prepareHourFrom(Map row, DayOfWeek dayOfWeek, OrderDetailsData data) {
        LocalTime hourFrom = null;
        if (!dayOfWeek.equals(DayOfWeek.SATURDAY) && !dayOfWeek.equals(DayOfWeek.SUNDAY)) {
            hourFrom = data.getHoursFrom();
        } else {
            if (data.getWeekendHoursStatus()) {
                hourFrom = data.getWeekendHoursFrom();
            } else {
                hourFrom = data.getHoursFrom();
            }
        }
        if (row.get("dh_from") != null) {
            return DateTimeUtil.parseHour(String.valueOf(row.get("dh_from")));
        }
        return hourFrom;
    }

    private LocalTime prepareHourTo(Map row, DayOfWeek dayOfWeek, OrderDetailsData data) {
        LocalTime hourTo = null;
        if (!dayOfWeek.equals(DayOfWeek.SATURDAY) && !dayOfWeek.equals(DayOfWeek.SUNDAY)) {
            hourTo = data.getHoursTo();
        } else {
            if (data.getWeekendHoursStatus()) {
                hourTo = data.getWeekendHoursTo();
            } else {
                hourTo = data.getHoursTo();
            }
        }
        if (row.get("dh_to") != null) {
            return DateTimeUtil.parseHour(String.valueOf(row.get("dh_to")));
        }
        return hourTo;
    }

    Long getGroupedDeliveriesCount(Long deliveryId) {
        return deliveryJdbcRepository.getGroupedDeliveriesCount(deliveryId);
    }

    List<OrderDeliveryOrderData> getGroupedDeliveries(Long deliveryId) {
        List<Map<String, Object>> rows = deliveryJdbcRepository.getGroupedDeliveries(deliveryId);

        List<OrderDeliveryOrderData> deliveries = new ArrayList<>();
        for (Map row : rows) {
            deliveries.add(new OrderDeliveryOrderData(Long.valueOf(String.valueOf(row.get("id")))));
        }
        return deliveries;
    }

    DeliverySettingData getDeliverySettings(String setting) {
        return deliveryJdbcRepository.getDeliverySettings(setting);
    }

    Long getDeliveryDeletedDays(Long orderProductId) {
        return deliveryJdbcRepository.getDeliveryDeletedDays(orderProductId);
    }

    List<OrderDeliveryOrderData> getDeliveryOrdersReleased(Long orderProductId, Long quantity) {
        List<Map<String, Object>> rows = deliveryJdbcRepository.getDeliveryOrdersReleased(orderProductId, quantity);
        List<OrderDeliveryOrderData> list = new ArrayList<>();
        for (Map row : rows) {
            OrderDeliveryOrderData data = new OrderDeliveryOrderData(
                    Long.valueOf(String.valueOf(row.get("id"))), //Long id,
                    Long.valueOf(String.valueOf(row.get("driver_id"))),
                    row.get("delivery_date") != null ? LocalDate.parse(String.valueOf(row.get("delivery_date")), dateFormatter) : null,
                    row.get("sunday") != null ? Integer.valueOf(String.valueOf(row.get("sunday"))).equals(1) : null,
                    row.get("original_delivery_date") != null ? LocalDate.parse(String.valueOf(row.get("original_delivery_date")), dateFormatter) : null
                    );
            list.add(data);
        }
        return list;
    }

    void updateDeliveryOrderToStart(Long deliveryId, LocalDate date, Long priority, boolean sunday, LocalDate originalDate) {
        deliveryJdbcRepository.updateDeliveryOrderToStart(deliveryId, date, priority, sunday, originalDate);
    }

    Long getDeliveryOrdersCount(Long orderId) {
        return deliveryJdbcRepository.getDeliveryOrdersCount(orderId);
    }

    void updateDeliveryStatusForOrder(Long orderId, String status) {
        deliveryJdbcRepository.updateDeliveryStatusForOrder(orderId, status);
    }


    List<OrderDeliveryOrderData> findDeliveryOrdersGroupByDeliveryDate(Long orderProductId) {
        List<Map<String, Object>> rows = deliveryJdbcRepository.findDeliveryOrdersGroupByDeliveryDate(orderProductId);
        List<OrderDeliveryOrderData> list = new ArrayList<>();
        for (Map row : rows) {
            OrderDeliveryOrderData data = new OrderDeliveryOrderData(
                    Long.valueOf(String.valueOf(row.get("id"))), //Long id,
                    row.get("delivery_date") != null ? LocalDate.parse(String.valueOf(row.get("delivery_date")), dateFormatter) : null
            );
            list.add(data);
        }
        return list;
    }

    void updateDeliveryStatus(Long id, String status) {
        deliveryJdbcRepository.updateDeliveryStatus(id, status);
    }

    void updateDeliveryGroupFor(Long deliveryId, Long orderProductId, LocalDate deliveryDate, String status) {
        deliveryJdbcRepository.updateDeliveryGroupFor(deliveryId, orderProductId, deliveryDate, status);
    }

    void deleteOrderDeliveries(Long orderId) {
        deliveryJdbcRepository.deleteOrderDeliveries(orderId);
    }

    BigDecimal getDeliveryDiscount(Long deliveryId) {
        return deliveryJdbcRepository.getDeliveryDiscount(deliveryId);
    }

    DeliveryMethodData getDeliveryMethod(Long deliveryMethodId) {
        return deliveryJdbcRepository.getDeliveryMethod(deliveryMethodId);
    }

    LocalDate getMinDeliveryDateForProductInOrder(Long orderProductId) {
        return deliveryJdbcRepository.getMinDeliveryDateForProductInOrder(orderProductId);
    }

    LocalDate getMaxDeliveryDateForProductInOrder(Long orderProductId) {
        return deliveryJdbcRepository.getMaxDeliveryDateForProductInOrder(orderProductId);
    }

    void changeDeliveriesStatus(OrderDetailsData order, Long orderId, Long deliveryMethodId) {
        Long driverId = null;
        if (order == null) {
            throw new IllegalArgumentException("order is null");
        }
        if (orderId == null) {
            throw new IllegalArgumentException("orderId is null");
        }
        if (deliveryMethodId == null) {
            throw new IllegalArgumentException("deliveryMethodId is null");
        }
        deliveryJdbcRepository.updateOrderDeliveryMethod(orderId, deliveryMethodId);

        //IMP duplicated code
        if (deliveryMethodId.longValue() == Const.DELIVERY_METHOD_PERSONAL) {
            driverId = 14L;
            deliveryJdbcRepository.updateDriverOnDeliveriesForOrder(orderId, driverId);
        } else {
            if (order.getOrderWeekendAddress().getWeekendAddressStatus()) {
                List<OrderDeliveryOrderData> deliveries = getDeliveriesForOrder(orderId);
                for (OrderDeliveryOrderData delivery : deliveries) {
                    String city = null;
                    if (delivery.getDeliveryDate().getDayOfWeek().equals(DayOfWeek.SUNDAY) || delivery.getDeliveryDate().getDayOfWeek().equals(DayOfWeek.SATURDAY)) {
                        city = order.getOrderWeekendAddress().getCityName();
                    } else {
                        city = order.getCustomerForOrder().getCityName();
                    }
                    Long cityId = (city != null && !city.isEmpty()) ? dictionariesService.getDictionaryIdByValue(city, DictionaryType.CITIES, Language.PL) : null;
                    DriverData defaultDriverForCity = driversService.getDefaultDriverForCity(cityId);
                    deliveryJdbcRepository.updateDriverOnDelivery(delivery.getId(), defaultDriverForCity.getId());
                }
            } else {
                String city = order.getCustomerForOrder().getCityName();
                Long cityId = (city != null && !city.isEmpty()) ? dictionariesService.getDictionaryIdByValue(city, DictionaryType.CITIES, Language.PL) : null;
                DriverData defaultDriverForCity = driversService.getDefaultDriverForCity(cityId);
                deliveryJdbcRepository.updateDriverOnDeliveriesForOrder(orderId, defaultDriverForCity.getId());
            }
        }
    }

    List<OrderDeliveryOrderData> getDeliveriesForOrder(Long orderId) {
        List<Map<String, Object>> rows = deliveryJdbcRepository.getDeliveriesForOrder(orderId);

        List<OrderDeliveryOrderData> deliveries = new ArrayList<>();
        for (Map row : rows) {
            deliveries.add(new OrderDeliveryOrderData(
                            Long.valueOf(String.valueOf(row.get("id"))),
                            row.get("delivery_date") != null ? LocalDate.parse(String.valueOf(row.get("delivery_date")), dateFormatter) : null
                    )
            );
        }
        return deliveries;
    }

    Long getDeliveryForOrderWithStatus(Long orderId, String status) {
        return deliveryJdbcRepository.getDeliveryForOrderWithStatus(orderId, status);
    }

    void updateDriverToUngroup(Long orderId) {
        deliveryJdbcRepository.updateDriverToUngroup(orderId);
    }

    void updateDriverOnDeliveriesForOrderWithoutStatus(Long orderId, Long driverId) {
        deliveryJdbcRepository.updateDriverOnDeliveriesForOrderWithoutStatus(orderId, driverId);
    }

    void updateDriverOnDeliveriesForOrder(Long orderId, Long driverId) {
        deliveryJdbcRepository.updateDriverOnDeliveriesForOrder(orderId, driverId);
    }
    void updateDriverOnDelivery(Long deliveryId, Long driverId) {
        deliveryJdbcRepository.updateDriverOnDelivery(deliveryId, driverId);
    }

    void setDeliveryAsDelivered(Long deliveryId) {
        deliveryJdbcRepository.setDeliveryAsDelivered(deliveryId);
    }

    List<OrderDeliveryOrderData> findUndelivered() {
        List<Map<String, Object>> rows = deliveryJdbcRepository.findUndelivered();

        List<OrderDeliveryOrderData> deliveries = new ArrayList<>();
        for (Map row : rows) {
            deliveries.add(new OrderDeliveryOrderData(
                            Long.valueOf(String.valueOf(row.get("delivery_id"))),
                            row.get("delivery_date") != null ? LocalDate.parse(String.valueOf(row.get("delivery_date")), dateFormatter) : null,
                            Long.valueOf(String.valueOf(row.get("order_id")))
                    )
            );
        }
        return deliveries;


    }

    List<OrderDeliveryOrderData> findDeliveryToDeliveredTwoDays() {
        List<Map<String, Object>> rows = deliveryJdbcRepository.findDeliveryToDeliveredTwoDays();

        List<OrderDeliveryOrderData> deliveries = new ArrayList<>();
        for (Map row : rows) {
            deliveries.add(new OrderDeliveryOrderData(
                            Long.valueOf(String.valueOf(row.get("id"))),
                            row.get("delivery_date") != null ? LocalDate.parse(String.valueOf(row.get("delivery_date")), dateFormatter) : null,
                            Long.valueOf(String.valueOf(row.get("order_id")))
                    )
            );
        }
        return deliveries;
    }


}
