package pl.com.goodsolution.adviser.logic;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.com.goodsolution.adviser.auth.AuthenticatedUser;
import pl.com.goodsolution.adviser.config.ApplicationConfig;
import pl.com.goodsolution.adviser.domain.*;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
public class OrdersService {
    private static final Logger log = LoggerFactory.getLogger(OrdersService.class);
    public static final BigDecimal DIVISOR_100 = BigDecimal.valueOf(100);
    private OrderRepository orderRepository;
    private OrdersJdbcRepository ordersJdbcRepository;
    private DictionariesService dictionariesService;
    private AuthenticatedUser authenticatedUser;
    private DriversService driversService;
    private ProductsService productsService;
    private DeliveryService deliveryService;
    private CustomersService customersService;
    private EmailsService emailsService;
    private PaymentsService paymentsService;
    private CitiesService citiesService;
    private DiscountService discountService;
    private ApplicationConfig applicationConfig;


    private DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-LL-dd HH:mm:ss.S");
    private DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-LL-dd");

    public OrdersService(OrderRepository orderRepository, OrdersJdbcRepository ordersJdbcRepository, DictionariesService dictionariesService, AuthenticatedUser authenticatedUser, DriversService driversService, ProductsService productsService, DeliveryService deliveryService, CustomersService customersService, EmailsService emailsService, PaymentsService paymentsService, CitiesService citiesService, DiscountService discountService, ApplicationConfig applicationConfig) {
        this.orderRepository = orderRepository;
        this.ordersJdbcRepository = ordersJdbcRepository;
        this.dictionariesService = dictionariesService;
        this.authenticatedUser = authenticatedUser;
        this.driversService = driversService;
        this.productsService = productsService;
        this.deliveryService = deliveryService;
        this.customersService = customersService;
        this.emailsService = emailsService;
        this.paymentsService = paymentsService;
        this.citiesService = citiesService;
        this.discountService = discountService;
        this.applicationConfig = applicationConfig;
    }

    public Long create(OrderCreateData data) {
        return ordersJdbcRepository.create(data);
    }

    public void update(OrderUpdateData data) {
        log.debug("UPDATE ORDER orderId: " + data.getId());
        if (data == null) {
            throw new IllegalArgumentException("data is null");
        }
        if (data.getId() == null) {
            throw new IllegalArgumentException("orderId is null");
        }

        // editFullOrder
        OrderDetailsData order = getOrder(data.getId());
        if (data.getEmail() != null) {
            log.debug("data.getEmail(): " + data.getEmail());
            if (invalidEmail(data.getEmail())) {
                throw new IllegalArgumentException("Email: " + data.getEmail() + " jest niepoprawny");
            }
            ordersJdbcRepository.updateEmail(data.getId(), data.getEmail());
        }

        data.setHoursOf(LocalTime.of(4, 0));
        data.setWeekendHoursOf(LocalTime.of(4, 0));
        if (getNumberOfEditOrderCount(data.getId()).longValue() == 0) {
            addOrderVersion(data.getId());
        }
        ;

        CityDiscountData cityDiscountData = citiesService.getCityDiscount(data.getCustomerCityId());
        boolean isNewCity = citiesService.isNewCity(dictionariesService.getDictionaryValueById(data.getCustomerCityId(), DictionaryType.CITIES, Language.PL));

        BigDecimal cityDiscount = BigDecimal.ZERO;
        if (cityDiscountData != null) {
            if (isNewCity) {
                if (cityDiscountData.getDateTo() != null && !order.getOrderDate().toLocalDate().isAfter(cityDiscountData.getDateTo())) {
                    cityDiscount = cityDiscountData.getDiscountValue();
                }
            }
        }
        data.setCityDiscount(cityDiscount);

        customersService.updateCustomerDemanding(order.getCustomerForOrder().getId(), data.getDemandingCustomer());
        if (order.getPaymentStatusId().longValue() != data.getPaymentStatusId().longValue()) {
            paymentsService.updatePaymentUpdateDateOnOrder(data.getId());
        }
        if (order.getComment() != null && data.getComments() != null && !order.getComment().equals(data.getComments())) {
            data.setCommentVersion(order.getCommentVersion() + 1L);
        }
        createOrderStatusChange(data.getId(), order.getStatusId(), data.getOrderStatusId());

        boolean groupOrder = data.getGroupOrders() != null && data.getGroupOrders() ? true : false;
        Long deliveredCount = deliveryService.getDeliveryForOrderWithStatus(data.getId(), "1");
        boolean oldGroupOrder = data.getGroupOrders();
        if (deliveredCount.longValue() > 0) {
            groupOrder = oldGroupOrder;
        }
        if (data.getCancelReason() != null) {
            log.debug("data.getCancelReason(): " + data.getCancelReason());
            cancelOrderWithReason(data.getId(), data.getCancelReason());
        }
        checkStatusTransition(data, order);

        ordersJdbcRepository.change(data);
        addOrderVersion(data.getId());
        updateOrderValue(data.getId());
        // ----------------------------- after update -----------------

        //IMP duplicated code
        if (data.getDeliveryMethodId().longValue() == Const.DELIVERY_METHOD_PERSONAL) {
            deliveryService.updateDriverOnDeliveriesForOrderWithoutStatus(data.getId(), 14L);
        } else {
            if (order.getShipmentTypeId().longValue() == Const.DELIVERY_METHOD_PERSONAL) {
                if (order.getOrderWeekendAddress().getWeekendAddressStatus()) {
                    List<OrderDeliveryOrderData> deliveries = deliveryService.getDeliveriesForOrder(data.getId());
                    for (OrderDeliveryOrderData delivery : deliveries) {
                        String city = null;
                        if (delivery.getDeliveryDate().getDayOfWeek().equals(DayOfWeek.SUNDAY) || delivery.getDeliveryDate().getDayOfWeek().equals(DayOfWeek.SATURDAY)) {
                            city = order.getOrderWeekendAddress().getCityName();
                        } else {
                            city = order.getCustomerForOrder().getCityName();
                        }
                        Long cityId = (city != null && !city.isEmpty()) ? dictionariesService.getDictionaryIdByValue(city, DictionaryType.CITIES, Language.PL) : null;
                        DriverData defaultDriverForCity = driversService.getDefaultDriverForCity(cityId);
                        deliveryService.updateDriverOnDelivery(delivery.getId(), defaultDriverForCity.getId());
                    }
                } else {
                    DriverData defaultDriverForCity = driversService.getDefaultDriverForCity(order.getCustomerForOrder().getCityId());
                    deliveryService.updateDriverOnDeliveriesForOrder(data.getId(), defaultDriverForCity.getId());
                }
            }
        }

        OrderDetailsData orderUpdated = getOrder(data.getId());
        if (orderUpdated.getStatusId().longValue() == Const.ORDER_STATUS_APPROVED) {


            Long count = deliveryService.getDeliveryOrdersCount(data.getId());
            if (count.longValue() == 0) {
                List<OrderProductData> products = findOrderProductsNotDeleted(data.getId());
                for (OrderProductData product : products) {
                    /*
                    $from = $product['from'];
				$hour_to = getDeliverySettings($database_connect_link, 'hours_to');
				$now = date("H:i:s");
				$hour_to = date("H:i:s", strtotime("+30 minutes", strtotime( $hour_to )));
				$addDay = 1;
				if( strtotime( $now ) > strtotime( $hour_to ) ) {
					$addDay = 2;
				}
				$tmpDate = date("Y-m-d", strtotime("+".$addDay." day"));
				if( $from < $tmpDate ) $from = $tmpDate;

        $od = $from;
                     */
                    LocalDate dateFrom = prepareDeliveryDates(product.getDateFrom()); //HMM??
                    if (!order.getNewCalendar()) {
                        calculateDelivery(data.getId(), dateFrom, product.getWeekendOptionId(), orderUpdated, Long.valueOf(product.getDays()), product.getQuantity(), product.getId());
                    } else {
                        calculateDeliveryNewCalendar(data.getId(), product.getWeekendOptionId(), orderUpdated, Long.valueOf(product.getDays()), product.getQuantity(), product.getId());
                    }
                }
            }


        }

        if (groupOrder != oldGroupOrder) {
            if (deliveredCount.longValue() == 0) {
                if (groupOrder) {
                    groupOrder(data.getId());
                } else {
                    deliveryService.updateDriverToUngroup(data.getId());
                }
            }
        }


        log.debug("OLD order.getPaymentStatusId().longValue(): " + order.getPaymentStatusId().longValue() + " NEW data.getPaymentStatusId(): " + data.getPaymentStatusId());
        if (order.getPaymentStatusId().longValue() != data.getPaymentStatusId()) {
            paymentsService.createPaymentStatusChange(new OrderPaymentChangeData(order.getId(), data.getPaymentStatusId(), authenticatedUser.getUserId()));
            if (orderUpdated.getPaymentStatusId().longValue() == Const.PAYMENT_STATUS_PAID) {
                List<OrderProductData> products = findOrderProductsNotDeleted(data.getId());
                List<OrderProductCreateData> items = new ArrayList<>();
                for (OrderProductData product : products) {
                    items.add(new OrderProductCreateData(
                                    data.getId(), //Long orderId,
                                    product.getDietId(), //Long dietId,
                                    product.getProductId(), //Long dietTypeId,  //TODO mess
                                    product.getQuantity(), //Long quantity,
                                    product.getDateFrom(), //LocalDate dateFrom,
                                    product.getDays(), //Long days,
                                    product.getWeekendOptionId(), //Long weekendOptionId,
                                    product.getTestIndicatorCode(), //Boolean testDay,
                                    null, //Boolean extrasOne,
                                    null, //Set<Long> extraIds
                                    product.getId()
                            )
                    );
                }
                emailsService.sendOrderPaid(orderUpdated, items);
            }
        }
        log.debug("OLD order.getStatusId().longValue(): " + order.getStatusId().longValue() + " NEW data.getOrderStatusId(): " + data.getOrderStatusId());
        if (data.getStartDate() != null && order.getStatusId().longValue() == 7 && data.getOrderStatusId() == 5) {
            log.debug("data.getStartDate(): " + data.getStartDate());
            startDiet(data.getId(), data.getStartDate());
        }
        if (data.getStopDate() != null && data.getOrderStatusId().longValue() == 7) {
            log.debug("data.getStopDate(): " + data.getStopDate() + "" + "");
            stopDiet(data.getId(), data.getStopDate());
        }
    }

    public void startDiet(Long orderId, LocalDate date) {
        log.debug("START DIET orderId: " + orderId + " date: " + date);
        if (orderId == null) {
            throw new IllegalArgumentException("orderId is null");
        }
        if (date == null) {
            throw new IllegalArgumentException("date is null");
        }
        if (date == null) {
            date = LocalDate.now();
        }
        LocalDate from = null;
        from = prepareDeliveryDates(date);
        log.debug("from: " + from + " date: " + date);
        List<OrderProductInfoData> orderProducts = findOrderProducts(orderId);
        for (OrderProductInfoData orderProduct : orderProducts) {
            Long quantity = orderProduct.getQuantity();
            Long deletedDays = deliveryService.getDeliveryDeletedDays(orderProduct.getId());
            Long each = deletedDays / quantity;
            log.debug("quantity: " + quantity + " deletedDays: " + deletedDays + " each: " + each);
            startDeliveries(from, orderProduct, quantity, each);
        }
    }

    private LocalDate prepareDeliveryDates(LocalDate date) {
        LocalDate from;
        if (date.isEqual(LocalDate.now())) {
            DeliverySettingData setting = deliveryService.getDeliverySettings("hours_to");
            if (setting == null) {
                throw new IllegalArgumentException("Cannot get delivery setting for " + "hours_to");
            }
            LocalTime hourTo = setting.getValue();
            hourTo = hourTo.plusMinutes(30);
            if (hourTo.isBefore(LocalTime.now())) {
                from = LocalDate.now().plusDays(2); //$from = date("Y-m-d", time() + ( 86400 * 2 ) );
            } else {
                from = LocalDate.now().plusDays(1); //$from = date("Y-m-d", time() + ( 86400 * 1 ) );
            }
        } else {
            from = date;
        }
        return from;
    }

    private void startDeliveries(LocalDate from, OrderProductInfoData orderProduct, Long quantity, Long each) {
        OrderDetailsData order = getOrder(orderProduct.getOrderId());
        if (order.getNewCalendar()) {
            //List<OrderDaysData> orderDays = findOrderDaysForReactivateDeliveryFrom(orderProduct.getOrderId(), orderProduct.getId(), from);
            List<OrderDeliveryOrderData> deliveries = deliveryService.getDeliveryOrdersReleased(orderProduct.getId(), 10000L); //TODO
            for (OrderDeliveryOrderData delivery : deliveries) {
                Long priority = deliveryService.getDeliveryMaxPriority(delivery.getDriverId(), delivery.getDeliveryDate());
                log.debug("priority: " + priority);

                if (orderProduct.getWeekendOptionId().longValue() == 0) {
                    deliveryService.updateDeliveryOrderToStart(delivery.getId(), delivery.getDeliveryDate(), priority, false, delivery.getDeliveryDate());
                } else if (orderProduct.getWeekendOptionId().longValue() == 2) {
                    deliveryService.updateDeliveryOrderToStart(delivery.getId(), delivery.getDeliveryDate(), priority, false, delivery.getDeliveryDate());
                } else {
                    LocalDate newDay = delivery.getDeliveryDate();
                    boolean sunday = false;
                    DayOfWeek weekDay = delivery.getOriginalDeliveryDate().getDayOfWeek();
                    if (weekDay.equals(DayOfWeek.SUNDAY)) {
                        sunday = true;
                        newDay = delivery.getDeliveryDate();
                    }
                    deliveryService.updateDeliveryOrderToStart(delivery.getId(), newDay, priority, sunday, delivery.getOriginalDeliveryDate());
                }
            }

        } else {
            for (long i = 0; i < each; i++) {
                LocalDate deliveryDate = from.plusDays(i);
                deliveryDate = deliveryService.getReplaceDay(deliveryDate, orderProduct.getId());
                log.debug("after getReplaceDay deliveryDate: " + deliveryDate);
                if (!deliveryService.checkIfDayIsExclusion(deliveryDate)) {
                    log.debug("after checkIfDayIsExclusion");
                    DayOfWeek weekDay = deliveryDate.getDayOfWeek();
                    List<OrderDeliveryOrderData> deliveries = deliveryService.getDeliveryOrdersReleased(orderProduct.getId(), quantity);


                    for (OrderDeliveryOrderData delivery : deliveries) {
                        Long priority = deliveryService.getDeliveryMaxPriority(delivery.getDriverId(), deliveryDate);
                        log.debug("priority: " + priority);

                        if (orderProduct.getWeekendOptionId().longValue() == 0) {
                            if (weekDay.equals(DayOfWeek.SATURDAY) || weekDay.equals(DayOfWeek.SUNDAY)) {
                                each++;
                            } else {
                                deliveryService.updateDeliveryOrderToStart(delivery.getId(), deliveryDate, priority, false, deliveryDate);
                            }
                        } else if (orderProduct.getWeekendOptionId().longValue() == 2) {
                            if (weekDay.equals(DayOfWeek.SUNDAY)) {
                                each++;
                            } else {
                                deliveryService.updateDeliveryOrderToStart(delivery.getId(), deliveryDate, priority, false, deliveryDate);
                            }
                        } else {
                            LocalDate newDay = deliveryDate;
                            LocalDate originalDate = LocalDate.from(deliveryDate);
                            boolean sunday = false;
                            if (weekDay.equals(DayOfWeek.SUNDAY)) {
                                sunday = true;
                                newDay = deliveryDate.minusDays(1);
                            }
                            deliveryService.updateDeliveryOrderToStart(delivery.getId(), newDay, priority, sunday, originalDate);
                        }
                    }
                } else {
                    log.debug("each++");
                    each++;
                }
            }
        }
    }

    public void stopDiet(Long orderId, LocalDate date) {
        log.debug("STOP DIET orderId: " + orderId + " date: " + date);
        if (orderId == null) {
            throw new IllegalArgumentException("orderId is null");
        }
        if (date == null) {
            throw new IllegalArgumentException("date is null");
        }
        ordersJdbcRepository.stopDiet(orderId, date);
    }

    private boolean invalidEmail(String email) {
        String regex = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w-]+\\.)+[\\w]+[\\w]$";
        return !email.matches(regex);
    }

    public void remove(Long orderId) {
        log.debug("DELETE ORDER id: " + orderId + " user id: " + authenticatedUser.getUserId() + " user name: " + authenticatedUser.getUserName());
        ordersJdbcRepository.deleteOrder(orderId);
        ordersJdbcRepository.deleteOrderProducts(orderId);
        deliveryService.deleteOrderDeliveries(orderId);
        ordersJdbcRepository.deleteOrderProductDiscounts(orderId);
    }

    private void checkStatusTransition(OrderUpdateData data, OrderDetailsData order) {
        checkDeliveryMethodTransition(data, order);
        checkOrderStatusTransition(data, order);
        checkPaymentMethodTransition(data, order);
        checkPaymentStatusTransition(data, order);
        checkDemandingCustomerTransition(data, order);
        checkInvoiceTransition(data, order);
        checkReceiptTransition(data, order);
    }

    private void checkDeliveryMethodTransition(OrderUpdateData data, OrderDetailsData order) {
        // TODO new BadRequestException when impossible transition
    }

    private void checkOrderStatusTransition(OrderUpdateData data, OrderDetailsData order) {
        // TODO new BadRequestException when impossible transition
    }

    private void checkPaymentMethodTransition(OrderUpdateData data, OrderDetailsData order) {
        // TODO new BadRequestException when impossible transition
    }

    private void checkPaymentStatusTransition(OrderUpdateData data, OrderDetailsData order) {
        // TODO new BadRequestException when impossible transition
    }

    private void checkDemandingCustomerTransition(OrderUpdateData data, OrderDetailsData order) {
        // TODO new BadRequestException when impossible transition
    }

    private void checkInvoiceTransition(OrderUpdateData data, OrderDetailsData order) {
        // TODO new BadRequestException when impossible transition
    }

    private void checkReceiptTransition(OrderUpdateData data, OrderDetailsData order) {
        // TODO new BadRequestException when impossible transition
    }


    public OrderResultData findOrdersAll(OrdersFilter filter, Long page, Long limit) {
        long startTime = System.currentTimeMillis();
        List<OrderData> list = findOrders(filter,
                page, limit);
        long timeTaken = System.currentTimeMillis() - startTime;
        log.info("Time Taken by {} is {}", "findOrdersAll (1)", timeTaken);

        startTime = System.currentTimeMillis();
        BigDecimal result = sumOfOrderValues(list);
        timeTaken = System.currentTimeMillis() - startTime;
        log.info("Time Taken by {} is {}", "findOrdersAll (2)", timeTaken);
        return new OrderResultData(list, result, ordersCount(filter));
    }

    private BigDecimal sumOfOrderValues(List<OrderData> list) {
        return list.stream().map(o -> o.getValue()).reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private List<OrderData> findOrders(OrdersFilter filter,
                                       Long page, Long limit) {
        long startTime = System.currentTimeMillis();
        List<Map<String, Object>> rows = ordersJdbcRepository.findOrders(filter,
                limit * (page - 1L), limit);
        long timeTaken = System.currentTimeMillis() - startTime;
        log.info("Time Taken by {} is {}", "findOrders (1)", timeTaken);
        Long no = 1L + limit * (page - 1L);

        List<OrderData> list = new ArrayList<>();
        for (Map row : rows) {
            LocalDateTime dateTime = LocalDateTime.parse(String.valueOf(row.get("order_datatime")), dateTimeFormatter);

            Long days = null;
            if (row.get("days_left") != null) {
                if (row.get("days_left") instanceof Long) {
                    days = (Long) row.get("days_left");
                } else {
                    days = Long.valueOf((Integer) row.get("days_left"));
                }
            }
            Long daysLeft = days != null ? days : 0L;
            Long nextOrders = Long.valueOf((Long) row.get("next_orders_count"));
            String paymentStatus = String.valueOf(row.get("payment_status"));
            Long paymentStatusId = Long.valueOf(paymentStatus.equals("") ? "0" : paymentStatus);
            Long orderStatusId = Long.valueOf(String.valueOf(row.get("order_status_id")));
            Long paymentMethodId = Long.valueOf(String.valueOf(row.get("payment_id")));
            OrderData data = new OrderData(no++,
                    Long.valueOf(String.valueOf(row.get("order_id"))),
                    (String) row.get("customer_name"),
                    (String) row.get("customer_surname"),
                    (String) row.get("user_order_id"),
                    dateTime,
                    BigDecimal.valueOf((Float) row.get("order_basket_sum")),
                    Long.valueOf(String.valueOf(row.get("shipping_id"))),
                    (String) row.get("shipping_operator_name"),
                    paymentMethodId,
                    (String) row.get("payment_company_name"),
                    paymentStatusId,
                    getPaymentStatusName(paymentStatusId),
                    dictionariesService.getColorCodeByElementId(paymentStatusId, DictionaryType.PAYMENT_STATUSES, Language.PL),
                    daysLeft.intValue() >= 0 ? daysLeft.intValue() : 0,
                    orderStatusId,
                    getOrderStatusName(orderStatusId),
                    dictionariesService.getColorCodeByElementId(orderStatusId, DictionaryType.ORDER_STATUSES, Language.PL),
                    row.get("discount_code") != null ? (String) row.get("discount_code") : "---",
                    isCustomerDemanding(row),
                    getPaymentStyle(paymentStatusId),
                    getDaysLeftStyle(daysLeft),
                    getOrderStatusStyle(orderStatusId),
                    isNextOrder(nextOrders),
                    dictionariesService.getDictionaryElementByCode(String.valueOf(isNextOrder(nextOrders)), DictionaryType.YES_NO, Language.PL).getValue(),
                    dictionariesService.getDictionaryElementByCode(String.valueOf(isCustomerDemanding(row)), DictionaryType.YES_NO, Language.PL).getValue(),
                    isReceipt(row),
                    isInvoiceWanted(row),
                    isInvoiceIssued(row),
                    Long.valueOf(String.valueOf(row.get("logged_buyer_id"))),
                    (String) row.get("status_change_source")
            );
            list.add(data);
        }
        return list;
    }

    private String getOrderStatusName(Long orderStatusId) {
        return dictionariesService.getDictionaryValueById(orderStatusId, DictionaryType.ORDER_STATUSES, Language.PL);
    }

    private String getPaymentStatusName(Long paymentStatusId) {
        return dictionariesService.getDictionaryValueById(paymentStatusId, DictionaryType.PAYMENT_STATUSES, Language.PL);
    }

    private String getOrderStatusStyle(Long orderStatusId) {
        String color = dictionariesService.getColorCodeByElementId(orderStatusId, DictionaryType.ORDER_STATUSES, Language.PL);
        return color != null ? "background-color: " + color + ";" : "";
    }

    private String getDaysLeftStyle(Long daysLeft) {
        String style = "";
        if (daysLeft.intValue() <= 3) {
            style = "background: #fff400";
        }
        return style;
    }

    private String getPaymentStyle(Long paymentStatusId) {
        String color = dictionariesService.getColorCodeByElementId(paymentStatusId, DictionaryType.PAYMENT_STATUSES, Language.PL);
        if (paymentStatusId.longValue() == 0L) {
            return color != null ? "background-color: " + color + ";color: white !important;" : "";
        }
        return color != null ? "background-color: " + color + ";" : "";
    }

    private Long ordersCount(OrdersFilter filter) {
        return ordersJdbcRepository.ordersCount(filter);
    }

    private boolean isNextOrder(Long nextOrders) {
        return nextOrders.intValue() > 0;
    }

    private boolean isCustomerDemanding(Map row) {
        return row.get("customer_w") != null ? Integer.valueOf(String.valueOf(row.get("customer_w"))).equals(1) : false;
    }

    private boolean isInvoiceWanted(Map row) {
        return row.get("invoice") != null ? Integer.valueOf(String.valueOf(row.get("invoice"))).equals(1) : false;
    }

    private boolean isInvoiceIssued(Map row) {
        return row.get("invoice_status") != null ? Integer.valueOf(String.valueOf(row.get("invoice_status"))).equals(1) : false;
    }

    private boolean isReceipt(Map row) {
        return row.get("receipt") != null ? Integer.valueOf(String.valueOf(row.get("receipt"))).equals(1) : false;
    }

    public List<OrderData> findOrdersShort(OrdersFilter filter, Long page, Long limit) {
        if (shouldOrdersShortReturnEmptyResult(filter.getFirstName(), filter.getLastName(), filter.getOrderId())) {
            return Collections.emptyList();
        }
        return findOrders(filter,
                page, limit);
    }

    private boolean shouldOrdersShortReturnEmptyResult(String firstName, String lastName, String orderId) {
        if (lastName != null && !lastName.isEmpty() && lastName.length() >= 3) {
            return false;
        }
        /*
        		if (isset($filters['name']) && strlen($filters['name']) >= 0 && isset($filters['surname']) && strlen($filters['surname']) >= 3) $flag = true;


                if (!$flag) {
                    return [];
                }
        */
        return !checkNamesConditions(firstName, lastName);
    }

    private boolean checkNamesConditions(String firstName, String lastName) {
        return firstName != null && !firstName.isEmpty() && lastName != null && lastName.length() >= 3;
    }

    public OrderDetailsData getOrder(Long id) {
        return ordersJdbcRepository.getOrder(id);
    }

    public OrderPaymentResultData getPaymentResultForOrder(Long id, BigDecimal orderBasketSum) {
        List<Map<String, Object>> rows = ordersJdbcRepository.getPaymentsForOrder(id);
        List<OrderPaymentData> payments = new ArrayList<>();

        long no = 1;
        for (Map row : rows) {
            payments.add(
                    new OrderPaymentData(
                            Long.valueOf(String.valueOf(row.get("order_id"))),
                            no++,
                            Long.valueOf(String.valueOf(row.get("id"))),
                            LocalDateTime.parse(String.valueOf(row.get("payment_date")), dateTimeFormatter),
                            (BigDecimal) row.get("value"),
                            dictionariesService.getDictionaryValueById(Long.valueOf(String.valueOf(row.get("type"))), DictionaryType.PAYMENT_PAYMENT_METHODS, Language.PL),
                            Long.valueOf(String.valueOf(row.get("type")))
                    )
            );
        }

        BigDecimal sum = calculateOrderPaymentSum(payments);
        return new OrderPaymentResultData(payments,
                sum, calculateToPayLeft(sum, orderBasketSum), calculateRefund(sum, orderBasketSum));
    }

    private BigDecimal calculateRefund(BigDecimal sum, BigDecimal orderBasketSum) {
        if (sum.compareTo(orderBasketSum) <= 0) {
            return BigDecimal.ZERO;
        } else {
            return sum.subtract(orderBasketSum);
        }
    }

    private BigDecimal calculateOrderPaymentSum(List<OrderPaymentData> payments) {
        /*
        if ($payment['type'] != '6') {
								$value += $payment['value'];
							} else {
								$value -= $payment['value'];
							}

         */

        return payments.stream().filter(o -> !(o.getValue().compareTo(BigDecimal.valueOf(6)) == 0)).map(OrderPaymentData::getValue).reduce(BigDecimal::add).orElse(BigDecimal.ZERO).subtract(
                payments.stream().filter(o -> (o.getValue().compareTo(BigDecimal.valueOf(6)) == 0)).map(OrderPaymentData::getValue).reduce(BigDecimal::add).orElse(BigDecimal.ZERO));
    }

    private BigDecimal calculateToPayLeft(BigDecimal sum, BigDecimal orderBasketSum) {
        if (orderBasketSum.subtract(sum).compareTo(BigDecimal.ZERO) < 0) {
            return BigDecimal.ZERO;
        } else {
            return orderBasketSum.subtract(sum);
        }
        /*
        <td>
							<b>Do zapłaty pozostało:</b>
						</td>
						<td>
							<?php if( $order_basket_sum - $value < 0 ) echo 0; else echo $order_basket_sum - $value ?> zł
						</td>
						<?php if($value <= $order_basket_sum) { ?>
						<td colspan="2">

						</td>
						<?php } else { ?>
						<td>
							<b>Zwrot: </b>
						</td>
						<td>
							<?php echo $value - $order_basket_sum; ?> zł
						</td>
						<?php } ?>
         */
    }

    public OrderProductResultData getProductResultForOrder(Long id, BigDecimal orderBasketSumNo, BigDecimal orderBasketSum) {
        return new OrderProductResultData(getProductsForOrder(id),
                orderBasketSumNo,
                orderBasketSum);
    }

    private List<OrderProductData> getProductsForOrder(Long id) {
        List<Map<String, Object>> rows = ordersJdbcRepository.getProductsForOrder(id);

        List<OrderProductData> products = new ArrayList<>();
        for (Map row : rows) {
            products.add(
                    new OrderProductData(Long.valueOf(String.valueOf(row.get("id"))),
                            String.valueOf(row.get("category_name")), //dietName
                            Long.valueOf(String.valueOf(row.get("category_id"))), //dietID
                            //TODO think about dict usage
                            String.valueOf(row.get("name")), //typeName
                            Long.valueOf((long) Double.parseDouble(String.valueOf(row.get("quantity")))),
                            row.get("from") != null ? LocalDate.parse(String.valueOf(row.get("from")), dateFormatter) : null,
                            Long.valueOf(String.valueOf(row.get("days"))),
                            prepareExtras(row),
                            BigDecimal.valueOf((Float) row.get("net_price_for_piece")),
                            prepareVatPercent(row),
                            BigDecimal.valueOf((Float) row.get("price_for_piece")),
                            preparePromo(row),
                            BigDecimal.valueOf((Float) row.get("price")),
                            prepareWeekendIndicator(row),
                            prepareTestIndicatorName(row),
                            prepareTestIndicatorCode(row),
                            Long.valueOf(String.valueOf(row.get("product_id"))),
                            String.valueOf(row.get("order_product_stop")).equals("true") ? true : false,
                            Long.valueOf(String.valueOf(row.get("in_weekend"))),
                            getExtras(Long.valueOf(String.valueOf(row.get("id")))))
            );
        }
        return products;
    }

    private List<ExtrasData> getExtras(Long orderProductId) {
        List<Map<String, Object>> rows = ordersJdbcRepository.getExtras(orderProductId);
        List<ExtrasData> list = new ArrayList<>();
        for (Map row : rows) {
            ExtrasData data = new ExtrasData(
                    Long.valueOf(String.valueOf(row.get("extras_id")))
            );
            list.add(data);
        }
        return list;
    }

    private int prepareVatPercent(Map row) {
        return Integer.valueOf(String.valueOf(row.get("tax_name")).replace("%", ""));
    }

    private String prepareExtras(Map row) {
        // TODO if ($products['extras'] != 0 )

        List<Map<String, Object>> rows = ordersJdbcRepository.getProductExtrasForOrder(Long.valueOf(String.valueOf(row.get("id"))));
        String extras = "";
        List<String> extrasList = new ArrayList<>();
        for (Map extra : rows) {
            extrasList.add(String.valueOf(extra.get("name")));
        }
        extras = extrasList.stream().collect(Collectors.joining(", "));
        if (rows.size() == 0) {
            extras = "---";
        }
        return extras;
    }

    private String preparePromo(Map row) {
        return row.get("promo") == null ? "---" : String.valueOf(row.get("promo"));
    }

    private String prepareTestIndicatorName(Map row) {
        if (prepareTestIndicatorCode(row)) {
            return "Tak";
        }
        return "Nie";
    }

    private Boolean prepareTestIndicatorCode(Map row) {
        return Boolean.valueOf(String.valueOf(row.get("is_test")));
    }

    private String prepareWeekendIndicator(Map row) {
        // TODO use WEEKEND_OPTIONS dict
        int inWeekend = Integer.valueOf(String.valueOf(row.get("in_weekend")));
        if (inWeekend == 1) {
            return "Tak";
        } else if (inWeekend == 2) {
            return "Bez niedziel";
        } else if (inWeekend == 0) {
            return "Nie";
        }
        return "Nie wiadomo";
    }

    public List<OrderDeliveryData> getDeliveryDataForOrder(Long id, OrderDetailsData data) {
        List<Map<String, Object>> rows = ordersJdbcRepository.getDeliveryDataForOrder(id);
        List<OrderDeliveryData> list = new ArrayList<>();
        long no = 1;
        for (Map row : rows) {
            DayOfWeek dayOfWeek = LocalDate.parse(String.valueOf(row.get("ddate")), dateFormatter).getDayOfWeek();
            Integer dayOfWeekValue = row.get("day_of_week_value") != null ? Integer.valueOf(String.valueOf(row.get("day_of_week_value"))) : null;
            Boolean weekend = row.get("is_weekend") != null ? Integer.valueOf(String.valueOf(row.get("is_weekend"))).equals(1) : null;
            list.add(
                    new OrderDeliveryData(
                            Long.valueOf(String.valueOf(row.get("id"))),
                            no++,
                            row.get("prd_name") + " (" + row.get("category_name") + ")",
                            String.valueOf(row.get("driver") == null ? "" : row.get("driver")),
                            LocalDate.parse(String.valueOf(row.get("ddate")), dateFormatter),
                            row.get("delivery_date") != null ? LocalDate.parse(String.valueOf(row.get("delivery_date")), dateFormatter) : null,
                            prepareHourFrom(row, dayOfWeek, data),
                            prepareHourTo(row, dayOfWeek, data),
                            prepareAddress(row, data, dayOfWeek),
                            prepareCityId(row, data, dayOfWeek),
                            ordersJdbcRepository.getDeliveryPicturesCount(Long.valueOf(String.valueOf(row.get("DeliveryID")))),
                            Integer.valueOf(String.valueOf(row.get("status"))),
                            row.get("delivery_timestamp") != null ? LocalDateTime.parse(String.valueOf(row.get("delivery_timestamp")), dateTimeFormatter) : null,
                            getWeekend(dayOfWeek, weekend),
                            getDayOfWeekValue(dayOfWeek, dayOfWeekValue)
                    )
            );
        }

        return list;
    }

    private Integer getDayOfWeekValue(DayOfWeek dayOfWeek, Integer dayOfWeekValue) { //IMP
        if (dayOfWeekValue != null) {
            return dayOfWeekValue;
        }
        return dayOfWeek.getValue();
    }

    private Boolean getWeekend(DayOfWeek dayOfWeek, Boolean weekend) { //IMP
        if (weekend != null) {
            return weekend;
        }
        return DateTimeUtil.isWeekend(dayOfWeek);
    }

    public OrderDeliveryData getDeliveryData(Long orderId, Long deliveryId) {
        OrderDetailsData order = getOrder(orderId);
        return getDeliveryDataForOrder(orderId, order).stream().filter(o -> o.getId().equals(deliveryId)).findFirst().get();
    }

    /*
              if($weekDay <= 5) {
							$week_hours = $orderInfo['week_hour_of'].' - '.$orderInfo['week_hour_to'];
						} else {
							if($orderInfo['another_weekend_hours'] == 1) {
								$week_hours = $orderInfo['weekend_hour_of'].' - '.$orderInfo['weekend_hour_to'];
							} else {
								$week_hours = $orderInfo['week_hour_of'].' - '.$orderInfo['week_hour_to'];
							}
						}

											if(!empty($tmp['dh_from']) && !empty($tmp['dh_to'])) {
							$week_hours = date("G:i", strtotime($tmp['dh_from']));
							$week_hours .= " - ";
							$week_hours .= date("G:i", strtotime($tmp['dh_to']));
						}
     */

    private LocalTime prepareHourFrom(Map row, DayOfWeek dayOfWeek, OrderDetailsData data) {
        LocalTime hourFrom = null;
        if (!DateTimeUtil.isWeekend(dayOfWeek)) {
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
        if (!DateTimeUtil.isWeekend(dayOfWeek)) {
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

    private String prepareAddress(Map row, OrderDetailsData data, DayOfWeek dayOfWeek) {
        String address = "";
        if (data.getOrderWeekendAddress().getWeekendAddressStatus()) {
            if (dayOfWeek.equals(DayOfWeek.SUNDAY) || dayOfWeek.equals(DayOfWeek.SATURDAY)) {
                address = makeAddress(data.getOrderWeekendAddress().getCityName(),
                        data.getOrderWeekendAddress().getStreet(), data.getOrderWeekendAddress().getBuildingNumber(),
                        data.getOrderWeekendAddress().getApartmentNumber(), data.getOrderWeekendAddress().getPostalCode());
            } else {
                address = makeAddress(data.getCustomerForOrder().getCityName(),
                        data.getCustomerForOrder().getStreet(), data.getCustomerForOrder().getBuildingNumber(),
                        data.getCustomerForOrder().getApartmentNumber(), data.getCustomerForOrder().getPostalCode());
            }
        } else {
            address = makeAddress(data.getCustomerForOrder().getCityName(),
                    data.getCustomerForOrder().getStreet(), data.getCustomerForOrder().getBuildingNumber(),
                    data.getCustomerForOrder().getApartmentNumber(), data.getCustomerForOrder().getPostalCode());
        }
        if (Integer.valueOf(String.valueOf(row.get("da"))).equals(1)) {
            return makeAddress(String.valueOf(row.get("da_city")),
                    String.valueOf(row.get("da_street")), String.valueOf(row.get("da_house_no")),
                    String.valueOf(row.get("da_apartament_no")), String.valueOf(row.get("da_postal_code")));
        }
        return address;
    }

    //TODO: cityId needed for the Zmiana adresu modal (priority low - default is sosnowiec)
    private Long prepareCityId(Map row, OrderDetailsData data, DayOfWeek dayOfWeek) {
        return 1L;
    }

    private String makeAddress(String city, String street, String houseNo, String apartmentNo, String postalCode) {
        String address = city + ", " + street + " " + houseNo;
        if (apartmentNo != null && !apartmentNo.isEmpty()) {
            address += "/" + apartmentNo;
        }
        address += " (" + postalCode + ")";
        return address;
    }

    private String makeAddressWithoutBrackets(String city, String street, String houseNo, String apartmentNo, String postalCode) {
        String address = city + ", " + street + " " + houseNo;
        if (apartmentNo != null && !apartmentNo.isEmpty()) {
            address += "/" + apartmentNo;
        }
        address += " " + postalCode + "";
        return address;
    }

    public List<OrderDeliveryChangeData> getDeliveryChangesForOrder(Long id) {
        List<Map<String, Object>> rows = ordersJdbcRepository.getDeliveryChangesForOrder(id);
        List<OrderDeliveryChangeData> list = new ArrayList<>();
        Long no = 1L;
        for (Map row : rows) {
            OrderDeliveryChangeData data =
                    new OrderDeliveryChangeData(
                            Long.valueOf(String.valueOf(row.get("id"))),
                            no++,
                            prepareDeliveryChangeType(row),
                            row.get("before") != null ? String.valueOf(row.get("before")) : null,
                            row.get("after") != null ? String.valueOf(row.get("after")) : null,
                            row.get("product_name") + " (" + dictionariesService.getDictionaryValueById(Long.valueOf(String.valueOf(row.get("category_id"))), DictionaryType.DIETS, Language.PL) + ")",
                            String.valueOf(row.get("who_edited") == null ? "" : row.get("who_edited")),
                            LocalDateTime.parse(String.valueOf(row.get("edited_on")), dateTimeFormatter)
                    );
            list.add(data);
        }
        return list;

        /*
        $change_id = $data['change_id'];
	$change = getChangeTimetableDetails($db, $change_id);

	$q = mysqli_query($db, "SELECT * FROM delivery_orders WHERE id = " . $change['delivery_id']);
	$details = mysqli_fetch_assoc($q);

	$qProd = mysqli_query($db, "SELECT * FROM orders_products WHERE id = " . $details['order_product_id']);
	$order_product = mysqli_fetch_assoc($qProd);
	$product_category = getCategoryNameInLang($db, $order_product['category_id'], 'pl_PL');
	$product_name = getProductName($db, $order_product['product_id'] );

	$text = '<table class="styled_table" style="margin: 0 auto">';

		if ($change['type'] == 'relase_date') {
			$text .= '<tr>';
				$text .= '<td>Typ zmiany</td>';
				$text .= '<td>';
					$text .= 'Zwolnienie dostawy';
				$text .= '</td>';
			$text .= '</tr>';
			$text .= '<tr>';
				$text .= '<td>Z dnia</td>';
				$text .= '<td>'.$change['before'].'</td>';
			$text .= '</tr>';
		}

		if ($change['type'] == 'add_date_to_delivery') {
			$text .= '<tr>';
				$text .= '<td>Typ zmiany</td>';
				$text .= '<td>';
					$text .= 'Zmiana dostawy';
				$text .= '</td>';
			$text .= '</tr>';
			$text .= '<tr>';
				$text .= '<td>Z dnia</td>';
				$text .= '<td>'.$change['before'].'</td>';
			$text .= '</tr>';
			$text .= '<tr>';
				$text .= '<td>Na dzień</td>';
				$text .= '<td>'.$change['after'].'</td>';
			$text .= '</tr>';
		}

		if ($change['type'] == 'change_hours') {
			$text .= '<tr>';
				$text .= '<td>Typ zmiany</td>';
				$text .= '<td>';
					$text .= 'Zmiana godziny dostawy';
				$text .= '</td>';
			$text .= '</tr>';
			$text .= '<tr>';
				$text .= '<td>Z</td>';
				$text .= '<td>'.$change['before'].'</td>';
			$text .= '</tr>';
			$text .= '<tr>';
				$text .= '<td>Na</td>';
				$text .= '<td>'.$change['after'].'</td>';
			$text .= '</tr>';
		}

		if ($change['type'] == 'change_address') {
			$text .= '<tr>';
				$text .= '<td>Typ zmiany</td>';
				$text .= '<td>';
					$text .= 'Zmiana adresu dostawy';
				$text .= '</td>';
			$text .= '</tr>';
			$text .= '<tr>';
				$text .= '<td>Z</td>';
				$text .= '<td>'.$change['before'].'</td>';
			$text .= '</tr>';
			$text .= '<tr>';
				$text .= '<td>Na</td>';
				$text .= '<td>'.$change['after'].'</td>';
			$text .= '</tr>';
		}

		$text .= '<tr>';
			$text .= '<td>';
				$text .= 'Na produkt';
			$text .= '</td>';
			$text .= '<td>';
				$text .= $product_category . ' ('.$product_name.')';
			$text .= '</td>';
		$text .= '</tr>';

		$text .= '<tr>';
			$text .= '<td>';
				$text .= 'Przez';
			$text .= '</td>';
			$text .= '<td>';
				$text .= getUserDetailsName( $db, $change['edited_by'] ) . " " . getUserDetailsSurname( $db, $change['edited_by'] );
			$text .= '</td>';
		$text .= '</tr>';

		$text .= '<tr>';
			$text .= '<td>';
				$text .= 'Data/godzina';
			$text .= '</td>';
			$text .= '<td>';
				$text .= $change['edited_on'];
			$text .= '</td>';
		$text .= '</tr>';
	$text .= '</table>';

	$result['text'] = $text;
	return $result;
         */
    }

    private String prepareDeliveryChangeType(Map row) {
        //TODO dictionary DELIVERY_CHANGE_TYPE
        String type = String.valueOf(row.get("type"));
        if ("relase_date".equals(type)) {
            return "Zwolnienie dostawy";
        } else if ("add_date_to_delivery".equals(type)) {
            return "Uzupełnienie zwolnionej dostawy";
        } else if ("change_hours".equals(type)) {
            return "Zmiana godziny dostawy";
        } else if ("change_address".equals(type)) {
            return "Zmiana adresu dostawy";
        }
        return "Nieznane";
    }

    public List<OrderChangeData> getChangesForOrder(Long orderId) {
        List<Map<String, Object>> rows = ordersJdbcRepository.getChangesForOrder(orderId);
        List<OrderChangeData> list = new ArrayList<>();
        Long no = 1L;
        for (Map row : rows) {
            OrderChangeData data = new OrderChangeData(
                    no++,
                    LocalDateTime.parse(String.valueOf(row.get("edited_on")), dateTimeFormatter),
                    String.valueOf(row.get("who_edited")),
                    String.valueOf(row.get("customer_name")),
                    String.valueOf(row.get("customer_surname")),
                    String.valueOf(row.get("customer_phone")),
                    String.valueOf(row.get("customer_street")),
                    String.valueOf(row.get("customer_house_number")),
                    String.valueOf(row.get("customer_apartment_number")),
                    String.valueOf(row.get("customer_postal_code")),
                    String.valueOf(row.get("customer_city")),
                    DateTimeUtil.parseHour(String.valueOf(row.get("week_hour_of"))),
                    DateTimeUtil.parseHour(String.valueOf(row.get("week_hour_to"))),
                    dictionariesService.getDictionaryElementByCode(String.valueOf(row.get("customer_w").equals(1)), DictionaryType.YES_NO, Language.PL).getValue(),
                    dictionariesService.getDictionaryValueById(Long.valueOf(String.valueOf(row.get("payment_id"))), DictionaryType.ORDER_PAYMENT_METHODS, Language.PL),
                    dictionariesService.getDictionaryElementByCode(String.valueOf(row.get("card_payment").equals(1)), DictionaryType.YES_NO, Language.PL).getValue(),
                    dictionariesService.getDictionaryValueById(Long.valueOf(String.valueOf(row.get("payment_status"))), DictionaryType.PAYMENT_STATUSES, Language.PL),
                    dictionariesService.getDictionaryValueById(Long.valueOf(String.valueOf(row.get("shipping_id"))), DictionaryType.SHIPMENT_TYPES, Language.PL),
                    Long.valueOf(String.valueOf(row.get("id"))),
                    findChangedProducts(Long.valueOf(String.valueOf(row.get("id"))))
            );
            list.add(data);
        }
        return list;

        /*
        	$version_id = $data['version_id'];
	$q = mysqli_query($db, "SELECT * FROM orders_versions WHERE id = " . $version_id);
	$version = mysqli_fetch_assoc($q);
	if (!isset($data['getTextVersion'])) return $version;

	$text = '<table class="styled_table" style="width: 100%">';

		$text .= '<tr>';
			$text .= '<td>Klient wymagający</td>';
			$text .= '<td>';
				$text .= ($version['customer_w'] == 1) ? 'Tak' : 'Nie';
			$text .= '</td>';
		$text .= '</tr>';

		$text .= '<tr>';
			$text .= '<td>Rodzaj płatności</td>';
			$text .= '<td>';
				$text .= getPaymentTypeNameInLang2($db, $version['payment_id'], $_SESSION['lang']);
			$text .= '</td>';
		$text .= '</tr>';

		$text .= '<tr>';
			$text .= '<td>Płatność kartą</td>';
			$text .= '<td>';
				$text .= ($version['card_payment'] == 1) ? 'Tak' : 'Nie';
			$text .= '</td>';
		$text .= '</tr>';

		$text .= '<tr>';
			$text .= '<td>Status płatności</td>';
			$text .= '<td>';
				$text .= ($version['payment_status'] == 1) ? 'Opłacone' : 'Nieopłacone';
			$text .= '</td>';
		$text .= '</tr>';

		$text .= '<tr>';
			$text .= '<td>Sposób dostawy</td>';
			$text .= '<td>';
				$shipp = getShipmentTypeNameInLang($db, $version['shipping_id'], $_SESSION['lang']);
				$text .= $shipp['name'];
			$text .= '</td>';
		$text .= '</tr>';

		$text .= '<tr>';
			$text .= '<td>Imię</td>';
			$text .= '<td>';
				$text .= $version['customer_name'];
			$text .= '</td>';
		$text .= '</tr>';

		$text .= '<tr>';
			$text .= '<td>Nazwisko</td>';
			$text .= '<td>';
				$text .= $version['customer_surname'];
			$text .= '</td>';
		$text .= '</tr>';

		$text .= '<tr>';
			$text .= '<td>Telefon</td>';
			$text .= '<td>';
				$text .= $version['customer_phone'];
			$text .= '</td>';
		$text .= '</tr>';

		$text .= '<tr>';
			$text .= '<td>Ulica</td>';
			$text .= '<td>';
				$text .= $version['customer_street'];
			$text .= '</td>';
		$text .= '</tr>';

		$text .= '<tr>';
			$text .= '<td>Numer domu</td>';
			$text .= '<td>';
				$text .= $version['customer_house_number'];
			$text .= '</td>';
		$text .= '</tr>';

		if (!empty($version['customer_apartment_number'])) {
			$text .= '<tr>';
				$text .= '<td>Numer mieszkania</td>';
				$text .= '<td>';
					$text .= $version['customer_apartment_number'];
				$text .= '</td>';
			$text .= '</tr>';
		}

		$text .= '<tr>';
			$text .= '<td>Kod pocztowy</td>';
			$text .= '<td>';
				$text .= $version['customer_postal_code'];
			$text .= '</td>';
		$text .= '</tr>';

		$text .= '<tr>';
			$text .= '<td>Miejscowość</td>';
			$text .= '<td>';
				$text .= $version['customer_city'];
			$text .= '</td>';
		$text .= '</tr>';

		$text .= '<tr>';
			$text .= '<td>Godziny dostawy</td>';
			$text .= '<td>';
				$text .= $version['week_hour_of'] . " - " . $version['week_hour_to'];
			$text .= '</td>';
		$text .= '</tr>';

		if ($version['another_weekend_hours']) {
			$text .= '<tr>';
				$text .= '<td>Godziny dostawy w weekendy</td>';
				$text .= '<td>';
					$text .= $version['weekend_hour_of'] . " - " . $version['weekend_hour_to'];
				$text .= '</td>';
			$text .= '</tr>';
		}

		if ($version['invoice'] == 1) {
			$text .= '
				<tr>
					<td colspan="2">
						<h4>Dane do faktury</h4>
					</td>
				</tr>';
			$text .= '<tr>';
				$text .= '<td>Nazwa firmy</td>';
				$text .= '<td>';
					$text .= $version['invoice_company_name'];
				$text .= '</td>';
			$text .= '</tr>';

			$text .= '<tr>';
				$text .= '<td>NIP</td>';
				$text .= '<td>';
					$text .= $version['invoice_tax_number'];
				$text .= '</td>';
			$text .= '</tr>';

			$text .= '<tr>';
				$text .= '<td>Ulica</td>';
				$text .= '<td>';
					$text .= $version['invoice_street'];
				$text .= '</td>';
			$text .= '</tr>';

			$text .= '<tr>';
				$text .= '<td>Numer domu</td>';
				$text .= '<td>';
					$text .= $version['invoice_house_number'];
				$text .= '</td>';
			$text .= '</tr>';

			if (!empty($version['invoice_apartment_number'])) {
				$text .= '<tr>';
					$text .= '<td>Numer lokalu</td>';
					$text .= '<td>';
						$text .= $version['invoice_apartment_number'];
					$text .= '</td>';
				$text .= '</tr>';
			}

			$text .= '<tr>';
				$text .= '<td>Kod pocztowy</td>';
				$text .= '<td>';
					$text .= $version['invoice_postal_code'];
				$text .= '</td>';
			$text .= '</tr>';

			$text .= '<tr>';
				$text .= '<td>Miejscowość</td>';
				$text .= '<td>';
					$text .= $version['invoice_city'];
				$text .= '</td>';
			$text .= '</tr>';
		}

		if ($version['another_weekend_address'] == 1) {
			$text .= '
				<tr>
					<td colspan="2">
						<h4>Inny adres w weekendy</h4>
					</td>
				</tr>';

				$text .= '<tr>';
					$text .= '<td>Ulica</td>';
					$text .= '<td>';
						$text .= $version['weekend_street'];
					$text .= '</td>';
				$text .= '</tr>';

				$text .= '<tr>';
					$text .= '<td>Numer domu</td>';
					$text .= '<td>';
						$text .= $version['weekend_house_no'];
					$text .= '</td>';
				$text .= '</tr>';

				if (!empty($version['weekend_apartment_no'])) {
					$text .= '<tr>';
						$text .= '<td>Numer mieszkania</td>';
						$text .= '<td>';
							$text .= $version['weekend_apartment_no'];
						$text .= '</td>';
					$text .= '</tr>';
				}

				$text .= '<tr>';
					$text .= '<td>Kod pocztowy</td>';
					$text .= '<td>';
						$text .= $version['weekend_postal_code'];
					$text .= '</td>';
				$text .= '</tr>';

				$text .= '<tr>';
					$text .= '<td>Miejscowość</td>';
					$text .= '<td>';
						$text .= $version['weekend_city'];
					$text .= '</td>';
				$text .= '</tr>';

		}
	$text .= '</table>';
         */
    }


    public List<OrderEmailData> findOrderSentEmails(Long id) {
        List<Map<String, Object>> rows = ordersJdbcRepository.findOrderSentEmails(id);
        List<OrderEmailData> list = new ArrayList<>();
        Long no = 1L;
        for (Map row : rows) {
            OrderEmailData data = new OrderEmailData(
                    no++,
                    Long.valueOf(String.valueOf(row.get("id"))),
                    (String) row.get("title"),
                    (String) row.get("sent_by"),
                    LocalDateTime.parse(String.valueOf(row.get("date")), dateTimeFormatter),
                    Long.valueOf(String.valueOf(row.get("order_id"))),
                    (String) row.get("message"));
            list.add(data);
        }
        return list;
    }

    List<OrderCancelledData> getCanceledOrdersWithUser(Long orderId) {
        List<Map<String, Object>> rows = ordersJdbcRepository.getCanceledOrdersWithUser(orderId);
        List<OrderCancelledData> list = new ArrayList<>();
        for (Map row : rows) {
            OrderCancelledData data = new OrderCancelledData(
                    (String) row.get("reason"), // reason,
                    Long.valueOf(String.valueOf(row.get("changed_by"))),
                    LocalDateTime.parse(String.valueOf(row.get("canceled_at")), dateTimeFormatter),// date
                    new UserData((String) row.get("first_name"), (String) row.get("last_name")));
            list.add(data);
        }
        return list;
    }

    private List<OrderChangeProductData> findChangedProducts(Long id) {
        List<Map<String, Object>> rows = ordersJdbcRepository.findChangedProducts(id);
        List<OrderChangeProductData> list = new ArrayList<>();
        for (Map row : rows) {
            OrderChangeProductData data = new OrderChangeProductData(
                    dictionariesService.getDictionaryValueById(Long.valueOf(String.valueOf(row.get("category_id"))), DictionaryType.DIETS, Language.PL) + " " + "(" + row.get("product_name") + ")",
                    Long.valueOf((long) Double.parseDouble(String.valueOf(row.get("quantity")))),
                    BigDecimal.valueOf((Float) row.get("price")),
                    row.get("from") != null ? LocalDate.parse(String.valueOf(row.get("from")), dateFormatter) : null,
                    Long.valueOf(String.valueOf(row.get("days"))));
            list.add(data);
        }
        return list;
    }

    // SECTION: Użyty kod rabatowy: BUTTON: Zapisz
    public void updateDiscountCode(Long orderId, String code) {
        log.debug("UPDATE DISCOUNT CODE for orderId: " + orderId + " code: " + code);
        Long count = ordersJdbcRepository.getDiscountForOrderCount(orderId);
        DiscountData discountForOrder = null;
        if (count.longValue() > 0) {
            discountForOrder = ordersJdbcRepository.getDiscountForOrder(orderId);
            log.debug("There is some code for orderId: " + orderId + " code: " + code + " discountForOrder.getCode(): " + discountForOrder.getCode());
            if (discountForOrder.getCode().equals(code)) {
                log.trace("the same code used: " + code);
                return;
            }
        }
        if (code != null && !code.isEmpty()) {
            log.debug("code is not empty for orderId: " + orderId + " code: " + code);
            DiscountData discount = ordersJdbcRepository.getDiscount(code);
            if (discount == null) {
                discount = ordersJdbcRepository.getVoucher(code);
            }
            if (discount == null) {
                throw new IllegalArgumentException("Podany kod nie istnieje lub został już wykorzystany");
            } else {
                log.debug("Using code for orderId: " + orderId + " code: " + code + " discount.getType(): " + discount.getType() + " discount.getId(): " + discount.getId());
                ordersJdbcRepository.useDiscountCode(new OrderDiscountCodeUsageData(orderId, code, discount.getType(), discount.getId(), "IP<TODO>"));
                if (discount.getType().equals("voucher")) {
                    ordersJdbcRepository.updateVoucherStatus(discount.getId(), "0");
                }
            }
        }

        if ("".equals(code)) {
            discountService.removeAppliedDiscounts(orderId, DiscountType.PRICE);
            discountService.removeAppliedDiscounts(orderId, DiscountType.PERCENT);
        }

        if (count.longValue() > 0) {
            log.debug("remove old code for orderId: " + orderId + " code: " + code + " discountForOrder.getId(): " + discountForOrder.getId());
            ordersJdbcRepository.removeUsedDiscountCode(orderId, discountForOrder.getId());
        }
        updateOrderValue(orderId);
    }

    private void updateOrderValue(Long orderId) {
        log.debug("START updateOrderValue orderId: " + orderId);
        BigDecimal sum = BigDecimal.ZERO;
        List<OrderProductData> products = getProductsForOrder(orderId);
        log.debug("orderId: " + orderId + " products.size(): " + products.size());
        for (OrderProductData prd : products) {
            log.debug("orderId: " + orderId + " prd.getId(): " + prd.getId() + " prd.getProductId(): " + prd.getProductId() + "  prd.getGrossPrice(): " + prd.getGrossPrice() + " prd.getGrossValue(): " + prd.getGrossValue());
            sum = sum.add(prd.getGrossValue());
        }
        BigDecimal sumWithout = BigDecimal.ZERO;
        sumWithout = sumWithout.add(sum);
        log.debug("orderId: " + orderId + " sum: " + sum + " sumWithout: " + sumWithout);

        Long count = ordersJdbcRepository.getDiscountForOrderCount(orderId);
        DiscountType discountType = null;
        BigDecimal discountCodeValue = BigDecimal.ZERO;
        BigDecimal discountCodeValueTotal = BigDecimal.ZERO;
        if (count.longValue() > 0) {
            DiscountData discount = ordersJdbcRepository.getDiscountForOrder(orderId);
            log.debug("orderId: " + orderId + " discount.getCode(): " + discount.getCode() + " discount.getType(): " + discount.getType());

            DiscountData discountValue = null;
            if ("discount_code".equals(discount.getType())) {
                discountValue = ordersJdbcRepository.getDiscountValue(discount.getCode());
            } else if ("voucher".equals(discount.getType())) {
                discountValue = ordersJdbcRepository.getVoucherValue(discount.getCode());
            }
            log.debug("orderId: " + orderId + " discount.getType(): " + discount.getType() + " discountValue.getDiscountType(): " + discountValue.getDiscountType());
            if ("discount_code".equals(discount.getType())) {
                discountValue.setType(discountValue.getDiscountType());
            }
            BigDecimal value = BigDecimal.valueOf(Double.valueOf(discountValue.getValue()));
            log.debug("before discount apply orderId: " + orderId + " discountValue.getType(): " + discountValue.getType() + " value: " + value + " discount.getType(): " + discount.getType() + " discountValue.getDiscountType(): " + discountValue.getDiscountType());
            BigDecimal sumBefore = sum;

            if (DiscountType.PERCENT.code().equals(discountValue.getType())) {
                discountService.removeAppliedDiscounts(orderId, DiscountType.PERCENT);
                discountService.removeAppliedDiscounts(orderId, DiscountType.PRICE);
                BigDecimal tmp = value.divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP).multiply(sum);
                sum = sum.subtract(tmp);
                discountType = DiscountType.PERCENT;
                discountCodeValue = value;
                discountCodeValueTotal = tmp;
                discountService.storeAppliedDiscount(new AppliedDiscountData(orderId, discountType, value, tmp, sumBefore, sum));
                log.debug("In percent orderId: " + orderId + " tmp: " + tmp + " sum: " + sum);
            } else if (DiscountType.PRICE.code().equals(discountValue.getType())) {
                discountService.removeAppliedDiscounts(orderId, DiscountType.PERCENT);
                discountService.removeAppliedDiscounts(orderId, DiscountType.PRICE);
                sum = sum.subtract(value);
                discountType = DiscountType.PRICE;
                discountCodeValue = value;
                discountCodeValueTotal = value;
                discountService.storeAppliedDiscount(new AppliedDiscountData(orderId, discountType, value, value, sumBefore, sum));
            }
        }

        OrderDetailsData order = getOrder(orderId);
        log.debug("orderId: " + orderId + " order.getCityDiscount(): " + order.getCityDiscount());

        if (order.getCityDiscount().compareTo(BigDecimal.ZERO) != 0) {
            BigDecimal sumBefore = sum;
            discountService.removeAppliedDiscounts(orderId, DiscountType.CITY);
            log.debug("calculation city discount for order.getCityDiscount(): " + order.getCityDiscount());
            BigDecimal tmp = order.getCityDiscount().divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP);
            tmp = sum.multiply(tmp);
            sum = sum.subtract(tmp);

            discountService.storeAppliedDiscount(new AppliedDiscountData(orderId, DiscountType.CITY, order.getCityDiscount(), tmp, sumBefore, sum));
            log.debug("after calculation city discount for order.getCityDiscount(): " + order.getCityDiscount() + " sum: " + sum);
        }

        if (sum.compareTo(BigDecimal.ZERO) < 0) {
            log.debug("sum < 0; orderId: " + orderId + " sum: " + sum + " sumWithout: " + sumWithout);
            sum = BigDecimal.ZERO;
        }
        log.debug("before update sum; orderId: " + orderId + " sum: " + sum + " sumWithout: " + sumWithout);
        ordersJdbcRepository.updateSums(orderId, sum, sumWithout);

        BigDecimal discountValue = sumWithout.subtract(sum);
        log.debug("orderId: " + orderId + " discountValue: " + discountValue);
        ordersJdbcRepository.removePartialProductDiscounts(orderId);
        BigDecimal divide = BigDecimal.ZERO;
        for (OrderProductData prd : products) {
            BigDecimal multi = BigDecimal.valueOf(prd.getDays()).multiply(BigDecimal.valueOf(prd.getQuantity()));
            divide = divide.add(multi);
            log.debug("orderId: " + orderId + " prd.getDays(): " + prd.getDays() + " prd.getQuantity(): " + prd.getQuantity() + " multi: " + multi + " divide: " + divide);
        }

        BigDecimal discountValuePerItem = discountValue.divide(divide, 2, BigDecimal.ROUND_HALF_UP);
        log.debug("orderId: " + orderId + " divide: " + divide + " discountValuePerItem: " + discountValuePerItem);

        for (OrderProductData prd : products) {
            log.debug("orderId: " + orderId + " prd.getDays(): " + prd.getDays() + " prd.getQuantity(): " + prd.getQuantity() + " prd.getGrossPrice(): " + prd.getGrossPrice());
            BigDecimal multi = discountValuePerItem.multiply(BigDecimal.valueOf(prd.getDays())).multiply(BigDecimal.valueOf(prd.getQuantity()));
            BigDecimal priceItemAfterDiscount = prd.getGrossPrice().subtract(multi);
            BigDecimal tmp = BigDecimal.valueOf(prd.getDays()).multiply(BigDecimal.valueOf(prd.getQuantity()));
            priceItemAfterDiscount = priceItemAfterDiscount.divide(tmp, BigDecimal.ROUND_HALF_UP);
            log.debug("orderId: " + orderId + " prd.getId(): " + prd.getId() + " prd.getDays(): " + prd.getDays() + " prd.getQuantity(): " + prd.getQuantity() + " prd.getGrossPrice(): " + prd.getGrossPrice() + " priceItemAfterDiscount: " + priceItemAfterDiscount + " multi: " + multi + " tmp: " + tmp);

            priceItemAfterDiscount = calculatePriceItemAfterDiscount(prd.getGrossValue(), BigDecimal.valueOf(prd.getDays()), BigDecimal.valueOf(prd.getQuantity()), discountCodeValue, discountCodeValueTotal,  discountType);
            ordersJdbcRepository.createPartialProductAfterDiscount(orderId, prd.getId(), priceItemAfterDiscount);
        }
    }

    // SECTION: Produkty w zamówieniu: BUTTON: Wstrzymaj
    public void stopProduct(OrderProductUpdateData data) {
        log.debug("STOP PRODUCT data.getOrderProductId(): " + data.getOrderProductId() + " data.getSuspensionDate(): " + data.getSuspensionDate());
        if (data.getSuspensionDate() == null) {
            throw new IllegalArgumentException("suspensionDate is null");
        }
        if (data.getOrderProductId() == null) {
            throw new IllegalArgumentException("orderProductId is null");
        }

        OrderProductInfoData orderProduct = getOrderProductInfo(data.getOrderProductId());
        if (orderProduct == null) {
            throw new IllegalArgumentException("Nie mogę pobrać produktu w zamówieniu o id: " + data.getOrderProductId());
        }
        if (orderProduct.getStopped()) {
            throw new IllegalArgumentException("Produkt już jest zwolniony");
        }
        ordersJdbcRepository.changeOrderProductStopStatus(data.getOrderProductId(), "1");
        deliveryService.stopOrderDelivery(data.getOrderProductId(), data.getSuspensionDate());
        OrderDetailsData order = getOrder(data.getOrderId());
        if (order.getNewCalendar()) {
            ordersJdbcRepository.suspendOrderDaysForOrderProductFromDate(data.getOrderProductId(), data.getSuspensionDate());
        }
        ordersJdbcRepository.createChangeOrderProductStatus(data.getOrderProductId(), "0", "1");
    }

    public void startProduct(OrderProductUpdateData data) {
        log.debug("START PRODUCT data.getOrderProductId(): " + data.getOrderProductId() + " data.getStartDate(): " + data.getStartDate());
        if (data.getStartDate() == null) {
            throw new IllegalArgumentException("Uzupełnij datę wznowienia diety");
        }
        if (data.getStartDate().isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("Niepoprawna data");
        }
        if (data.getOrderProductId() == null) {
            throw new IllegalArgumentException("orderProductId is null");
        }
        OrderProductInfoData orderProduct = getOrderProductInfo(data.getOrderProductId());
        if (orderProduct == null) {
            throw new IllegalArgumentException("Nie mogę pobrać produktu w zamówieniu o id: " + data.getOrderProductId());
        }
        if (!orderProduct.getStopped()) {
            throw new IllegalArgumentException("Produkt już jest wznowiony");
        }
        ordersJdbcRepository.changeOrderProductStopStatus(data.getOrderProductId(), "0");
        ordersJdbcRepository.createChangeOrderProductStatus(data.getOrderProductId(), "1", "0");

        LocalDate from = null;
        LocalDate date = data.getStartDate();
        from = prepareDeliveryDates(date);
        log.debug("from: " + from + " data.getStartDate(): " + data.getStartDate());
        OrderDetailsData order = getOrder(orderProduct.getOrderId());
        if (order == null) {
            throw new IllegalArgumentException("Nie mogę pobrać zamówienia o id: " + orderProduct.getOrderId());
        }
        Long quantity = orderProduct.getQuantity();
        Long deletedDays = deliveryService.getDeliveryDeletedDays(orderProduct.getId());
        if (order.getGroupOrders()) {
            quantity = 1L;
        }
        Long each = deletedDays / quantity;
        if (order.getGroupOrders()) {
            each = deletedDays;
        }
        log.debug("quantity: " + quantity + " deletedDays: " + deletedDays + " each: " + each + " from: " + from + " order.getGroupOrders(): " + order.getGroupOrders());

        startDeliveries(from, orderProduct, quantity, each);
        if (order.getNewCalendar()) {
            ordersJdbcRepository.startOrderDaysForOrderProductFromDate(data.getOrderProductId(), from, order.getId());
        }
    }

    public void updateProduct(OrderProductUpdateData data) {
        if (data.getSuspensionDate() != null) {
            stopProduct(data);
        } else if (data.getStartDate() != null) {
            startProduct(data);
        } else {
            modifyProduct(data);
        }
    }

    // SECTION: Produkty w zamówieniu: BUTTON: Edytuj
    private void modifyProduct(OrderProductUpdateData data) {
        log.debug("MODIFY ORDER PRODUCT orderId: " + data.getOrderId() + " data.getOrderProductId(): " + data.getOrderProductId());
        if (data.getOrderId() == null) {
            throw new IllegalArgumentException("orderId is null");
        }
        if (data.getOrderProductId() == null) {
            throw new IllegalArgumentException("productId is null");
        }
        if (data.getDietId() == null) {
            throw new IllegalArgumentException("Wybierz dietę");
        }
        if (data.getDietTypeId() == null) {
            throw new IllegalArgumentException("Wybierz rodzaj");
        }
        if (data.getQuantity() == null) {
            throw new IllegalArgumentException("Wprowadzono nieprawidłową ilość sztuk");
        }
        if (data.getQuantity().longValue() < 0) {
            throw new IllegalArgumentException("Wprowadzono nieprawidłową ilość sztuk");
        }
        if (data.getDays() == null) {
            throw new IllegalArgumentException("Wprowadź ilość dni");
        }
        if (data.getDays().longValue() <= 0) {
            throw new IllegalArgumentException("Wprowadzono nieprawidłową ilość dni");
        }
        if (data.getChangePrice()) {
            if (data.getChangePriceValue() == null) {
                throw new IllegalArgumentException("Podaj kwotę");
            }
        }
        if (data.getChangePrice() != null) {
            if (data.getChangePriceValue().compareTo(BigDecimal.ZERO) < 0) {
                throw new IllegalArgumentException("Nieprawidłowa kwota");
            }
        }
        Long orderProductId = data.getOrderProductId();
        OrderProductInfoData orderProduct = getOrderProductInfo(orderProductId);
        if (orderProduct == null) {
            throw new IllegalArgumentException("Nie mogę pobrać produktu z zamówienia orderProductId: " + orderProductId);
        }
        OrderDetailsData order = getOrder(data.getOrderId());
        Long count = ordersJdbcRepository.getNumberOfEditOrderCount(data.getOrderId());
        if (count.longValue() == 0) {
            addOrderVersion(data.getOrderId());
        }
        ProductDetailData product = productsService.getProduct(data.getDietTypeId());
        if (product == null) {
            throw new IllegalArgumentException("Nie mogę pobrać produktu o id: " + data.getDietTypeId());
        }
        BigDecimal discount = productsService.getDietDiscount(data.getDays(), data.getDietId());
        BigDecimal taxValue = productsService.getTaxValue(product.getTaxId());
        BigDecimal netPriceForPiece = product.getNetPriceRetail();
        BigDecimal priceForPiece = product.getGrossPriceRetail();
        log.debug("orderId: " + data.getOrderId() + " data.getDietTypeId(): " + data.getDietTypeId() + " netPriceForPiece: " + netPriceForPiece + " priceForPiece: " + priceForPiece);

        log.debug("orderId: " + data.getOrderId() + " data.getDietTypeId(): " + data.getDietTypeId() + " discount: " + discount + " product.getTaxId(): " + product.getTaxId() + " taxValue: " + taxValue);

        Long days = data.getDays();
        Long quantity = data.getQuantity();
        if (data.getTestDay()) {
            netPriceForPiece = productsService.getTestSetPrice(product.getCategoryId(), priceForPiece);
            priceForPiece = productsService.getTestSetPrice(product.getCategoryId(), priceForPiece);
            ;
            days = 1L;
            quantity = 1L;
        }
        BigDecimal minusShip = BigDecimal.ZERO;
        if (order.getShipmentTypeId().longValue() == 2) {
            BigDecimal deliveryDiscount = deliveryService.getDeliveryDiscount(2L);
            minusShip = BigDecimal.valueOf(days).multiply(deliveryDiscount).multiply(BigDecimal.valueOf(quantity));
        }
        log.debug("orderId: " + data.getOrderId() + " minusShip: " + minusShip);
        BigDecimal extrasPrice = BigDecimal.ZERO;
        boolean orderExtras = false;


        for (Long extraId : data.getExtraIds()) {
            BigDecimal price = productsService.getExtrasPrice(extraId);
            BigDecimal multi = price.multiply(BigDecimal.valueOf(days)).multiply(BigDecimal.valueOf(quantity));
            extrasPrice = extrasPrice.add(multi);
            orderExtras = true;
        }

        String name = productsService.getProductNameInLanguage(data.getDietTypeId(), Language.PL.getCode());
        log.debug("orderId: " + data.getOrderId() + " orderExtras: " + orderExtras + " name: " + name + " extrasPrice: " + extrasPrice);

        BigDecimal price = priceForPiece.multiply(BigDecimal.valueOf(days)).multiply(BigDecimal.valueOf(quantity));
        price = price.subtract(discount.multiply(BigDecimal.valueOf(days))).add(extrasPrice).subtract(minusShip);

        BigDecimal qNetPriceForPiece = BigDecimal.ZERO;
        qNetPriceForPiece = qNetPriceForPiece.add(netPriceForPiece);
        BigDecimal qNetPricee = netPriceForPiece.multiply(BigDecimal.valueOf(days)).multiply(BigDecimal.valueOf(quantity));

        BigDecimal netPricee = qNetPricee;
        BigDecimal qPriceForPiece = priceForPiece;
        BigDecimal qPrice = price;
        BigDecimal discountPriceForPiece = priceForPiece;

        log.debug("orderId: " + data.getOrderId() + " price: " + price + " qNetPriceForPiece: " + qNetPriceForPiece + " qNetPricee: " + qNetPricee + " netPricee: " + netPricee + " qPriceForPiece: " + qPriceForPiece + " qPrice: " + qPrice + " discountPriceForPiece: " + discountPriceForPiece + " days: " + days + " quantity: " + quantity);

        Long c = deliveryService.checkDeliveryForProduct(orderProductId);
        if (c.longValue() != 0) {
            log.debug("c.longValue() != 0 was");
            if (orderProduct.getDays().longValue() != days.longValue() || orderProduct.getQuantity().longValue() != quantity.longValue() || orderProduct.getWeekendOptionId().longValue() != data.getWeekendOptionId().longValue()) {
                log.debug("in condition");
                Long deliveredDays = deliveryService.getDeliveredForProductCount(orderProductId);
                LocalDate lastDay = deliveryService.getLastDeliveryDate(orderProductId);
                LocalDate dateFrom = null;
                Long deliveryDays = days - deliveredDays;
                if (lastDay != null) {
                    dateFrom = lastDay.plusDays(1);
                } else {
                    dateFrom = orderProduct.getDateFrom();
                }
                deliveryService.deleteDeliveryOrderForProductNotCancelled(orderProductId);
                log.debug("calculateDelivery: data.getOrderId(): " + data.getOrderId() + " dateFrom: " + dateFrom + " data.getWeekendOptionId(): " + data.getWeekendOptionId() + " days: " + days + " quantity: " + quantity);
                if (!order.getNewCalendar()) {
                    calculateDelivery(data.getOrderId(), dateFrom, data.getWeekendOptionId(), order, deliveryDays, quantity, orderProductId);
                } else {
                    calculateDeliveryNewCalendar(data.getOrderId(), data.getWeekendOptionId(), order, deliveryDays, product.getQuantity(), orderProductId);
                }

                Long quans = deliveryService.getAllDeliveryDaysCount(orderProductId);
                price = priceForPiece.multiply(BigDecimal.valueOf(quans));
                price = price.subtract(discount.multiply(BigDecimal.valueOf(days))).add(extrasPrice).subtract(minusShip);
                log.debug("orderId: " + data.getOrderId() + " orderProductId: " + orderProductId + " quans: " + quans + " price: " + price);
                    /*
                    $quans = getAllDeliveryDays($db, $order_product_id);
			        $price = $price_for_piece * $quans;
			        $price = $price - ( $discount_diet * $days ) + $extrs_price - $minusShip;
                     */
            }
        }

        boolean priceChange = false;
        if (data.getChangePrice()) {
            price = data.getChangePriceValue();
            netPricee = price;
            priceChange = true;
        }
        log.debug("orderId(2): " + data.getOrderId() + " price: " + price + " qNetPriceForPiece: " + qNetPriceForPiece + " qNetPricee: " + qNetPricee + " netPricee: " + netPricee + " qPriceForPiece: " + qPriceForPiece + " qPrice: " + qPrice + " discountPriceForPiece: " + discountPriceForPiece);

        ordersJdbcRepository.updateOrderProduct(new OrderProductModifyData(
                data.getOrderProductId(),
                data.getDietTypeId(),
                quantity,
                data.getDietId(),
                name,
                product.getTaxId(),
                taxValue,
                netPriceForPiece,
                netPricee,
                priceForPiece,
                price,
                days,
                data.getWeekendOptionId(),
                orderExtras,
                data.getTestDay(),
                priceChange
        ));


        ordersJdbcRepository.deleteOrderProductExtras(orderProductId);
        for (Long extraId : data.getExtraIds()) {
            ordersJdbcRepository.createOrderProductExtras(orderProductId, extraId);
        }
        ordersJdbcRepository.createOrderEdit(data.getOrderId(), orderProductId, "edit_product");
        updateOrderValue(data.getOrderId());
        addOrderVersion(data.getOrderId());
    }

    // SECTION: Produkty w zamówieniu: BUTTON: Usuń
    public void removeProduct(Long orderId, Long productId) {
        log.debug("REMOVE ORDER PRODUCT orderId: " + orderId + " productId: " + productId);
        if (orderId == null) {
            throw new IllegalArgumentException("orderId is null");
        }
        if (productId == null) {
            throw new IllegalArgumentException("productId is null");
        }
        Long count = ordersJdbcRepository.getProductsInOrderCount(orderId);
        if (count.longValue() == 1) {
            throw new IllegalArgumentException("Nie możesz usunąć ostatniego produktu z zamówienia");
        }
        ordersJdbcRepository.deleteProductFromOrder(orderId, productId);
        ordersJdbcRepository.deleteDeliveryOrderForProduct(productId);
        OrderDetailsData order = getOrder(orderId);
        if (order.getNewCalendar()) {
            ordersJdbcRepository.deleteOrderDaysForOrderProduct(productId);
        }

        count = ordersJdbcRepository.getNumberOfEditOrderCount(orderId);
        if (count.longValue() == 0) {
            addOrderVersion(orderId);
        }
        try {
            ordersJdbcRepository.createOrderProductDeleted(orderId, productId, authenticatedUser.getUserId(), InetAddress.getLocalHost().getHostAddress());
        } catch (UnknownHostException e) {
            log.error("Problem during get ip " + e.getMessage());
            throw new IllegalArgumentException("Nie można pobrać adresu ip");
        }
        addOrderVersion(orderId);
        updateOrderValue(orderId);
    }

    private void addOrderVersion(Long orderId) {
        Long versionId = createOrderVersion(orderId);
        createOrderProductVersions(orderId, versionId);
    }

    private Long createOrderVersion(Long orderId) {
        return ordersJdbcRepository.createOrderVersion(orderId);
    }

    private void createOrderProductVersions(Long orderId, Long versionId) {
        ordersJdbcRepository.createOrderProductVersions(orderId, versionId);
    }

    // SECTION: Produkty w zamówieniu: BUTTON: Nowy produkt
    public void addProduct(OrderProductCreateData data) {
        log.debug("ADD PRODUCT FOR ORDER orderId: " + data.getOrderId() + " productId: " + data.getDietTypeId());
        if (data.getOrderId() == null) {
            throw new IllegalArgumentException("orderId is null");
        }
        if (data.getDietId() == null) {
            throw new IllegalArgumentException("Wybierz dietę");
        }
        if (data.getDietTypeId() == null) {
            throw new IllegalArgumentException("Wybierz rodzaj");
        }
        if (data.getQuantity() == null) {
            throw new IllegalArgumentException("Wprowadzono nieprawidłową ilość sztuk");
        }
        if (data.getQuantity().longValue() < 0) {
            throw new IllegalArgumentException("Wprowadzono nieprawidłową ilość sztuk");
        }
        if (data.getDateFrom() == null) {
            throw new IllegalArgumentException("Nieprawidłowa data");
        }
        if (data.getDateFrom().isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("Nieprawidłowa data");
        }
        if (data.getDays() == null) {
            throw new IllegalArgumentException("Wprowadź ilość dni");
        }
        if (data.getTestDay() == null) {
            throw new IllegalArgumentException("data.getTestDay() is null");
        }
        if (data.getDays().longValue() <= 0) {
            throw new IllegalArgumentException("Wprowadzono nieprawidłową ilość dni");
        }
        OrderDetailsData order = getOrder(data.getOrderId());
        Long count = ordersJdbcRepository.getNumberOfEditOrderCount(data.getOrderId());
        if (count.longValue() == 0) {
            addOrderVersion(data.getOrderId());
        }
        ProductDetailData product = productsService.getProduct(data.getDietTypeId());
        if (product == null) {
            throw new IllegalArgumentException("Nie mogę pobrać produktu o id: " + data.getDietTypeId());
        }
        BigDecimal discount = productsService.getDietDiscount(data.getDays(), data.getDietId());
        BigDecimal taxValue = productsService.getTaxValue(product.getTaxId());
        BigDecimal netPriceForPiece = product.getNetPriceRetail();
        BigDecimal priceForPiece = product.getGrossPriceRetail();
        log.debug("orderId: " + data.getOrderId() + " data.getDietTypeId(): " + data.getDietTypeId() + " discount: " + discount + " product.getTaxId(): " + product.getTaxId() + " taxValue: " + taxValue);

        Long days = data.getDays();
        Long quantity = data.getQuantity();
        if (data.getTestDay()) {
            netPriceForPiece = productsService.getTestSetPrice(product.getCategoryId(), priceForPiece);
            priceForPiece = productsService.getTestSetPrice(product.getCategoryId(), priceForPiece);
            days = 1L;
            quantity = 1L;
        }
        BigDecimal minusShip = BigDecimal.ZERO;
        if (order.getShipmentTypeId().longValue() == 2) {
            BigDecimal deliveryDiscount = deliveryService.getDeliveryDiscount(2L);
            minusShip = BigDecimal.valueOf(days).multiply(deliveryDiscount).multiply(BigDecimal.valueOf(quantity));
        }
        log.debug("orderId: " + data.getOrderId() + " minusShip: " + minusShip);

        BigDecimal extrasPrice = BigDecimal.ZERO;
        boolean orderExtras = false;

        for (Long extraId : data.getExtraIds()) {
            BigDecimal price = productsService.getExtrasPrice(extraId);
            BigDecimal multi = price.multiply(BigDecimal.valueOf(days)).multiply(BigDecimal.valueOf(quantity));
            extrasPrice = extrasPrice.add(multi);
            orderExtras = true;
        }

        String name = productsService.getProductNameInLanguage(data.getDietTypeId(), Language.PL.getCode());
        log.debug("orderId: " + data.getOrderId() + " orderExtras: " + orderExtras + " name: " + name + " extrasPrice: " + extrasPrice);

        BigDecimal price = priceForPiece.multiply(BigDecimal.valueOf(days)).multiply(BigDecimal.valueOf(quantity));
        price = price.subtract(discount.multiply(BigDecimal.valueOf(days))).add(extrasPrice).subtract(minusShip);

        BigDecimal qNetPriceForPiece = BigDecimal.ZERO;
        qNetPriceForPiece = qNetPriceForPiece.add(netPriceForPiece);
        BigDecimal qNetPricee = netPriceForPiece.multiply(BigDecimal.valueOf(days)).multiply(BigDecimal.valueOf(quantity));

        BigDecimal netPricee = qNetPricee;
        BigDecimal qPriceForPiece = priceForPiece;
        BigDecimal qPrice = price;
        BigDecimal discountPriceForPiece = priceForPiece;

        log.debug("orderId: " + data.getOrderId() + " price: " + price + " qNetPriceForPiece: " + qNetPriceForPiece + " qNetPricee: " + qNetPricee + " netPricee: " + netPricee + " qPriceForPiece: " + qPriceForPiece + " qPrice: " + qPrice + " discountPriceForPiece: " + discountPriceForPiece);

        Long orderProductId = ordersJdbcRepository.createOrderProduct(data, name, product.getTaxId(), taxValue,
                netPriceForPiece, netPricee, priceForPiece, price, days);

        if (order.getNewCalendar()) {
            storeOrderDays(data, days, orderProductId);
        }

        for (Long extraId : data.getExtraIds()) {
            ordersJdbcRepository.createOrderProductExtras(orderProductId, extraId);
        }
        updateOrderValue(data.getOrderId());
        addOrderVersion(data.getOrderId());

        log.debug("before delivery calc condition orderId: " + data.getOrderId() + " order.getStatusId(): " + order.getStatusId());
        if (order.getStatusId().longValue() == 5) {
            // paid
            if (!order.getNewCalendar()) {
                calculateDelivery(data.getOrderId(), data.getDateFrom(), data.getWeekendOptionId(), order, days, quantity, orderProductId);
            } else {
                calculateDeliveryNewCalendar(data.getOrderId(), data.getWeekendOptionId(), order, days, quantity, orderProductId);
            }
        }
        ordersJdbcRepository.createOrderEdit(data.getOrderId(), orderProductId, "add_product");
    }

    private void storeOrderDays(OrderProductCreateData data, Long days, Long orderProductId) {
        LocalDate dayDate = data.getDateFrom();
        for (Long i = 0L; i < days; i++) {
            if (data.getWeekendOptionId().longValue() == Const.WEEKEND_OPTION_ID_WITHOUT_WEEKENDS && DateTimeUtil.isWeekend(dayDate)) {
                dayDate = dayDate.plusDays(1);
                i--;
                continue;
            }
            if (data.getWeekendOptionId().longValue() == Const.WEEKEND_OPTION_ID_WITHOUT_SUNDAYS && DateTimeUtil.isSunday(dayDate)) {
                dayDate = dayDate.plusDays(1);
                i--;
                continue;
            }
            for (int q = 0; q < data.getQuantity(); q++) {
                ordersJdbcRepository.createOrderDay(new OrderDaysData(dayDate, orderProductId, data.getOrderId(), Const.ORDER_DAY_STATUS_ADDED));
            }
            dayDate = dayDate.plusDays(1);
        }
    }

    private void calculateDeliveryNewCalendar(Long orderId, Long weekendOptionId, OrderDetailsData order, Long days, Long quantity, Long orderProductId) {
        log.debug("start delivery calc orderId: " + orderId + " days: " + days + " quantity: " + quantity + " data.getWeekendOptionId(): " + weekendOptionId + " newCalendar: " + order.getNewCalendar());

        if (order.getNewCalendar()) {
            log.debug("new calendar");
            List<OrderDaysData> orderDays = findOrderDaysForDelivery(orderId, orderProductId);
            for (OrderDaysData orderDay : orderDays) {
                LocalDate datePlus = orderDay.getDate();
                LocalDate originalDate = LocalDate.from(orderDay.getDate());
                if (weekendOptionId.longValue() == 1) {
                    boolean sunday = false;
                    if (datePlus.getDayOfWeek().equals(DayOfWeek.SUNDAY)) {
                        sunday = true;
                        datePlus = datePlus.minusDays(1);
                    }
                    Long driverId = null;
                    if (datePlus.getDayOfWeek().equals(DayOfWeek.SUNDAY) || datePlus.getDayOfWeek().equals(DayOfWeek.SATURDAY)) {
                        if (order.getOrderWeekendAddress().getWeekendAddressStatus()) {
                            driverId = driversService.getDefaultDriverForCity(order.getOrderWeekendAddress().getCityId()).getId();
                        } else {
                            driverId = driversService.getDefaultDriverForCity(order.getCustomerForOrder().getCityId()).getId();
                        }
                    } else {
                        driverId = driversService.getDefaultDriverForCity(order.getCustomerForOrder().getCityId()).getId();
                    }
                    Long tmpDriverId = driversService.getDriverIdForOrder(orderId);
                    if (tmpDriverId.longValue() != 0) {
                        driverId = tmpDriverId;
                    }
                    if (order.getShipmentTypeId().longValue() == 2) {
                        driverId = 14L;  //IMP: not hardcoded
                    }

                    LocalDate date = deliveryService.getReplaceDay(datePlus, orderProductId);
                    Long priority = deliveryService.getDeliveryMaxPriority(driverId, date);
                    log.debug("before createDeliveryOrder orderId: " + orderId + " date: " + date + " priority: " + priority);
                    ordersJdbcRepository.createDeliveryOrder(new OrderDeliveryOrderCreateData(orderId, orderProductId, driverId, date, priority, sunday, "0", DateTimeUtil.isWeekend(date), DateTimeUtil.dayOfWeek(date), orderProductId, originalDate));

                } else if (weekendOptionId.longValue() == 0) {
                    Long driverId = driversService.getDefaultDriverForCity(order.getCustomerForOrder().getCityId()).getId();
                    Long tmpDriverId = driversService.getDriverIdForOrder(orderId);
                    log.debug("orderId: " + orderId + " driverId: " + driverId + " tmpDriverId: " + tmpDriverId);
                    if (tmpDriverId.longValue() != 0) {
                        driverId = tmpDriverId;
                    }
                    if (order.getShipmentTypeId().longValue() == 2) {
                        driverId = 14L;  //IMP: not hardcoded
                    }

                    LocalDate date = deliveryService.getReplaceDay(datePlus, orderProductId);
                    Long priority = deliveryService.getDeliveryMaxPriority(driverId, date);
                    log.debug("before createDeliveryOrder orderId: " + orderId + " date: " + date + " priority: " + priority);
                    ordersJdbcRepository.createDeliveryOrder(new OrderDeliveryOrderCreateData(orderId, orderProductId, driverId, date, priority, false, "0", DateTimeUtil.isWeekend(date), DateTimeUtil.dayOfWeek(date), orderProductId, originalDate));


                } else if (weekendOptionId.longValue() == 2) {
                    boolean sunday = false;
                    Long driverId = null;
                    if (datePlus.getDayOfWeek().equals(DayOfWeek.SUNDAY) || datePlus.getDayOfWeek().equals(DayOfWeek.SATURDAY)) {
                        if (order.getOrderWeekendAddress().getWeekendAddressStatus()) {
                            driverId = driversService.getDefaultDriverForCity(order.getOrderWeekendAddress().getCityId()).getId();
                        } else {
                            driverId = driversService.getDefaultDriverForCity(order.getCustomerForOrder().getCityId()).getId();
                        }
                    } else {
                        driverId = driversService.getDefaultDriverForCity(order.getCustomerForOrder().getCityId()).getId();
                    }
                    Long tmpDriverId = driversService.getDriverIdForOrder(orderId);
                    if (tmpDriverId.longValue() != 0) {
                        driverId = tmpDriverId;
                    }
                    if (order.getShipmentTypeId().longValue() == 2) {
                        driverId = 14L;  //IMP: not hardcoded
                    }

                    LocalDate date = deliveryService.getReplaceDay(datePlus, orderProductId);
                    Long priority = deliveryService.getDeliveryMaxPriority(driverId, date);
                    log.debug("before createDeliveryOrder orderId: " + orderId + " date: " + date + " priority: " + priority);
                    ordersJdbcRepository.createDeliveryOrder(new OrderDeliveryOrderCreateData(orderId, orderProductId, driverId, date, priority, sunday, "0", DateTimeUtil.isWeekend(date), DateTimeUtil.dayOfWeek(date), orderProductId, originalDate));

                }
            }


        }
    }

    private void calculateDelivery(Long orderId, LocalDate dateFrom, Long weekendOptionId, OrderDetailsData order, Long days, Long quantity, Long orderProductId) {
        log.debug("start delivery calc orderId: " + orderId + " days: " + days + " quantity: " + quantity + " data.getWeekendOptionId(): " + weekendOptionId + " newCalendar: " + order.getNewCalendar());


        Long numberOfDays = days;
        LocalDate from = dateFrom;
        if (weekendOptionId.longValue() == 1) {
            for (long i = 0; i < numberOfDays; i++) {
                LocalDate datePlus = from.plusDays(i);
                LocalDate originalDate = from.plusDays(i);
                if (!deliveryService.checkIfDayIsExcluded(datePlus)) {
                    boolean sunday = false;
                    if (datePlus.getDayOfWeek().equals(DayOfWeek.SUNDAY)) {
                        sunday = true;
                        datePlus = datePlus.minusDays(1);
                    }
                    Long driverId = null;
                    if (datePlus.getDayOfWeek().equals(DayOfWeek.SUNDAY) || datePlus.getDayOfWeek().equals(DayOfWeek.SATURDAY)) {
                        if (order.getOrderWeekendAddress().getWeekendAddressStatus()) {
                            driverId = driversService.getDefaultDriverForCity(order.getOrderWeekendAddress().getCityId()).getId();
                        } else {
                            driverId = driversService.getDefaultDriverForCity(order.getCustomerForOrder().getCityId()).getId();
                        }
                    } else {
                        driverId = driversService.getDefaultDriverForCity(order.getCustomerForOrder().getCityId()).getId();
                    }
                    Long tmpDriverId = driversService.getDriverIdForOrder(orderId);
                    if (tmpDriverId.longValue() != 0) {
                        driverId = tmpDriverId;
                    }
                    if (order.getShipmentTypeId().longValue() == 2) {
                        driverId = 14L;  //IMP: not hardcoded
                    }
                    for (int y = 0; y < quantity; y++) {
                        LocalDate date = deliveryService.getReplaceDay(datePlus, orderProductId);
                        Long priority = deliveryService.getDeliveryMaxPriority(driverId, date);
                        log.debug("before createDeliveryOrder orderId: " + orderId + " date: " + date + " priority: " + priority);
                        ordersJdbcRepository.createDeliveryOrder(new OrderDeliveryOrderCreateData(orderId, orderProductId, driverId, date, priority, sunday, "0", DateTimeUtil.isWeekend(date), DateTimeUtil.dayOfWeek(date), orderProductId, originalDate));
                    }
                } else {
                    numberOfDays++;
                }
            }
        } else if (weekendOptionId.longValue() == 0) {
            for (long i = 0; i < numberOfDays; i++) {
                LocalDate datePlus = from.plusDays(i);
                LocalDate originalDate = from.plusDays(i);
                log.debug("orderId: " + orderId + " datePlus: " + datePlus);
                if (!deliveryService.checkIfDayIsExcluded(datePlus)) {
                    Long driverId = driversService.getDefaultDriverForCity(order.getCustomerForOrder().getCityId()).getId();
                    Long tmpDriverId = driversService.getDriverIdForOrder(orderId);
                    log.debug("orderId: " + orderId + " driverId: " + driverId + " tmpDriverId: " + tmpDriverId);
                    if (tmpDriverId.longValue() != 0) {
                        driverId = tmpDriverId;
                    }
                    if (order.getShipmentTypeId().longValue() == 2) {
                        driverId = 14L;  //IMP: not hardcoded
                    }
                    if (!datePlus.getDayOfWeek().equals(DayOfWeek.SUNDAY) && !datePlus.getDayOfWeek().equals(DayOfWeek.SATURDAY)) {
                        for (int y = 0; y < quantity; y++) {
                            LocalDate date = deliveryService.getReplaceDay(datePlus, orderProductId);
                            Long priority = deliveryService.getDeliveryMaxPriority(driverId, date);
                            log.debug("before createDeliveryOrder orderId: " + orderId + " date: " + date + " priority: " + priority);
                            ordersJdbcRepository.createDeliveryOrder(new OrderDeliveryOrderCreateData(orderId, orderProductId, driverId, date, priority, false, "0", DateTimeUtil.isWeekend(date), DateTimeUtil.dayOfWeek(date), orderProductId, originalDate));
                        }
                    } else {
                        numberOfDays++;
                    }
                } else {
                    numberOfDays++;
                }
            }
        } else if (weekendOptionId.longValue() == 2) {
            for (long i = 0; i < numberOfDays; i++) {
                LocalDate datePlus = from.plusDays(i);
                LocalDate originalDate = from.plusDays(i);
                if (!deliveryService.checkIfDayIsExcluded(datePlus)) {
                    boolean sunday = false;
                    if (!datePlus.getDayOfWeek().equals(DayOfWeek.SUNDAY)) {
                        Long driverId = null;
                        if (datePlus.getDayOfWeek().equals(DayOfWeek.SUNDAY) || datePlus.getDayOfWeek().equals(DayOfWeek.SATURDAY)) {
                            if (order.getOrderWeekendAddress().getWeekendAddressStatus()) {
                                driverId = driversService.getDefaultDriverForCity(order.getOrderWeekendAddress().getCityId()).getId();
                            } else {
                                driverId = driversService.getDefaultDriverForCity(order.getCustomerForOrder().getCityId()).getId();
                            }
                        } else {
                            driverId = driversService.getDefaultDriverForCity(order.getCustomerForOrder().getCityId()).getId();
                        }
                        Long tmpDriverId = driversService.getDriverIdForOrder(orderId);
                        if (tmpDriverId.longValue() != 0) {
                            driverId = tmpDriverId;
                        }
                        if (order.getShipmentTypeId().longValue() == 2) {
                            driverId = 14L;  //IMP: not hardcoded
                        }
                        for (int y = 0; y < quantity; y++) {
                            LocalDate date = deliveryService.getReplaceDay(datePlus, orderProductId);
                            Long priority = deliveryService.getDeliveryMaxPriority(driverId, date);
                            log.debug("before createDeliveryOrder orderId: " + orderId + " date: " + date + " priority: " + priority);
                            ordersJdbcRepository.createDeliveryOrder(new OrderDeliveryOrderCreateData(orderId, orderProductId, driverId, date, priority, sunday, "0", DateTimeUtil.isWeekend(date), DateTimeUtil.dayOfWeek(date), orderProductId, originalDate));
                        }
                    } else {
                        numberOfDays++;
                    }
                } else {
                    numberOfDays++;
                }
            }

        }
    }

    // SECTION: Płatności BUTTON: Dodaj płatność
    public void addPayment(OrderPaymentCreateData data) {
        log.debug("ADD PAYMENT data.getOrderId(): " + data.getOrderId() + " paymentMethodId: " + data.getPaymentMethodId() + " data.getAmount(): " + data.getAmount());
        if (data.getOrderId() == null) {
            throw new IllegalArgumentException("OrderPaymentCreateData.orderId is null");
        }
        if (data.getPaymentMethodId() == null) {
            throw new IllegalArgumentException("OrderPaymentCreateData.paymentMethodId is null");
        }
        if (data.getAmount() == null) {
            throw new IllegalArgumentException("Podaj kwotę");
        }
        if (data.getAmount().compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Kwota musi być większa od 0: " + data.getAmount());
        }
        dictionariesService.getDictionaryValueById(data.getPaymentMethodId(), DictionaryType.PAYMENT_PAYMENT_METHODS, Language.PL);
        ordersJdbcRepository.createPayment(data);
    }

    // SECTION: Płatności:  BUTTON: Usuń
    public void removePayment(OrderPaymentRemoveData data) {
        log.debug("REMOVE PAYMENT data.getPaymentId(): " + data.getPaymentId());
        if (data.getPaymentId() == null) {
            throw new IllegalArgumentException("paymentId is null");
        }
        ordersJdbcRepository.setPaymentStatus(data.getPaymentId(), "0");
        ordersJdbcRepository.createDeletedPayment(new OrderPaymentDeletedData(data.getPaymentId(), LocalDateTime.now(), authenticatedUser.getUserId()));
    }

    public void updateDelivery(OrderDeliveryUpdateData data) {
        if (data.getComplete() != null) {
            setDeliveryAsDelivered(data.getOrderId(), data.getDeliveryId());
        } else if (data.getHourFrom() != null && data.getHourTo() != null) {
            changeDeliveryHours(new OrderDeliveryChangeHoursData(data.getOrderId(), data.getDeliveryId(), data.getHourFrom(), data.getHourTo()));
        } else if (data.getCityId() != null) {
            changeDeliveryAddress(data);
        } else if (data.getSuspensionDate() != null) {
            stopDelivery(data.getOrderId(), data.getDeliveryId(), data.getUserAgent());
        } else if (data.getStartDate() != null) {
            startDelivery(data.getOrderId(), data.getDeliveryId(), data.getStartDate());
        } else {
            throw new IllegalArgumentException("Błędna operacja");
        }
    }

    // SECTION: Informacje na temat dostaw:  BUTTON: Zwolnij
    public void stopDelivery(Long orderId, Long deliveryId, String userAgent) {
        log.debug("START DELIVERY orderId: " + orderId + " deliveryId: " + deliveryId + " userAgent: " + userAgent);
        if (orderId == null) {
            throw new IllegalArgumentException("orderId is null");
        }
        if (deliveryId == null) {
            throw new IllegalArgumentException("deliveryId is null");
        }
        OrderDetailsData order = getOrder(orderId);
        if (order == null) {
            throw new IllegalArgumentException("Nie mogę pobrać zamówienia o id: " + orderId);
        }
        OrderDeliveryOrderData deliveryOrder = deliveryService.getDeliveryOrder(deliveryId);
        if (deliveryOrder == null) {
            throw new IllegalArgumentException("Nie mogę pobrać dostawy o id: " + deliveryId);
        }
        if (deliveryOrder.getStatus().longValue() != Const.DELIVERY_ORDER_STATUS_WAITING) {
            throw new IllegalArgumentException("Niepoprawny status zamówienia: " + deliveryOrder.getStatus().longValue());
        }
        deliveryService.createDeliveryChangeLog(new OrderDeliveryChangeLogData(orderId, deliveryId, deliveryOrder.getOrderProductId(), "relase_date", deliveryOrder.getDeliveryDate().toString(), "", authenticatedUser.getUserId()));
        deliveryService.stopDeliveryUpdate(deliveryId);
        try {
            deliveryService.createDeliveryRelease(new OrderDeliveryReleaseCreateData(orderId, deliveryId, deliveryOrder.getOrderProductId(), deliveryOrder.getDeliveryDate(), null, LocalDateTime.now(), authenticatedUser.getUserId(), authenticatedUser.getUserName(), InetAddress.getLocalHost().getHostAddress(), userAgent));
        } catch (UnknownHostException e) {
            log.error("Problem during get ip " + e.getMessage());
            throw new IllegalArgumentException("Nie można pobrać adresu ip");
        }
        if (order.getNewCalendar()) {
            ordersJdbcRepository.releaseOrderDaysForOrderProductForDate(deliveryOrder.getOrderProductId(), deliveryOrder.getOriginalDeliveryDate(), orderId);
        }
    }

    // SECTION: BUTTON:
    public void startDelivery(Long orderId, Long deliveryId, LocalDate date) {
        log.debug("START DELIVERY orderId: " + orderId + " deliveryId: " + deliveryId + " date: " + date);
        if (orderId == null) {
            throw new IllegalArgumentException("orderId is null");
        }
        if (deliveryId == null) {
            throw new IllegalArgumentException("deliveryId is null");
        }
        if (date == null) {
            throw new IllegalArgumentException("Uzupełnij datę dostawy");
        }
        if (date.isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("Nieprawidłowa data dostawy");
        }
        OrderDetailsData order = getOrder(orderId);
        if (order == null) {
            throw new IllegalArgumentException("Nie mogę pobrać zamówienia o id: " + orderId);
        }

        LocalDate originalDate = LocalDate.from(date);
        boolean sunday = false;
        if (date.getDayOfWeek().equals(DayOfWeek.SUNDAY)) {
            if (!deliveryService.checkDeliveryInSunday(deliveryId, date)) {
                throw new IllegalArgumentException("Nie można uzupełnić dostawy w podany dzień, ponieważ to zamówienie nie posiada dostawy w sobotę");
            }
            date = date.minusDays(1);
            sunday = true;
        }
        OrderDeliveryOrderData deliveryOrder = deliveryService.getDeliveryOrder(deliveryId);
        if (deliveryOrder == null) {
            throw new IllegalArgumentException("Nie mogę pobrać dostawy z zamówienia deliveryId: " + deliveryId);
        }
        deliveryService.createDeliveryChangeLog(new OrderDeliveryChangeLogData(orderId, deliveryId, deliveryOrder.getOrderProductId(), "add_date_to_delivery", deliveryOrder.getDeliveryDate().toString(), date.toString(), authenticatedUser.getUserId()));
        deliveryService.startDeliveryUpdate(deliveryId, date, sunday, originalDate);
        if (order.getNewCalendar()) {
            createOrderDay(originalDate, orderId, deliveryOrder.getOrderProductId(), Const.ORDER_DAY_STATUS_FILLED);
        }
    }

    // SECTION: Informacje na temat dostaw: BUTTON: Zmień adres
    public void changeDeliveryAddress(OrderDeliveryUpdateData data) {
        log.debug("CHANGE DELIVERY ADDRESS orderId: " + data.getOrderId() + " deliveryId: " + data.getDeliveryId());
        if (data.getOrderId() == null) {
            throw new IllegalArgumentException("order id is null");
        }
        if (data.getDeliveryId() == null) {
            throw new IllegalArgumentException("delivery id is null");
        }
        if (data.getStreet() == null || data.getStreet().isEmpty()) {
            throw new IllegalArgumentException("Uzupełnij ulicę");
        }
        if (data.getBuildingNumber() == null || data.getBuildingNumber().isEmpty()) {
            throw new IllegalArgumentException("Uzupełnij numer domu");
        }
        if (data.getPostalCode() == null || data.getPostalCode().isEmpty()) {
            throw new IllegalArgumentException("Uzupełnij kod pocztowy");
        }
        OrderDeliveryOrderData deliveryOrder = deliveryService.getDeliveryOrder(data.getDeliveryId());
        if (deliveryOrder == null) {
            throw new IllegalArgumentException("Nie mogę pobrać dostawy zamowienia id: " + data.getDeliveryId());
        }
        OrderDetailsData order = getOrder(data.getOrderId());
        if (order == null) {
            throw new IllegalArgumentException("Nie mogę pobrać zamówienia o id: " + data.getOrderId());
        }
        String after = makeAddress(dictionariesService.getDictionaryValueById(data.getCityId(), DictionaryType.CITIES, Language.PL), data.getStreet(), data.getBuildingNumber(), data.getApartmentNumber(), data.getPostalCode());
        String before = "";

        if (deliveryOrder.getDa()) {
            before = makeAddress(deliveryOrder.getDaCity(), deliveryOrder.getDaStreet(), deliveryOrder.getDaHouseNo(), deliveryOrder.getDaApartmentNo(), deliveryOrder.getDaPostalCode());
        } else {
            before = makeAddress(order.getCustomerForOrder().getCityName(),
                    order.getCustomerForOrder().getStreet(), order.getCustomerForOrder().getBuildingNumber(),
                    order.getCustomerForOrder().getApartmentNumber(), order.getCustomerForOrder().getPostalCode());
            if (deliveryOrder.getDeliveryDate().getDayOfWeek().equals(DayOfWeek.SATURDAY) || deliveryOrder.getDeliveryDate().getDayOfWeek().equals(DayOfWeek.SUNDAY)) {
                if (order.getOrderWeekendAddress().getWeekendAddressStatus()) {
                    before = makeAddress(order.getOrderWeekendAddress().getCityName(),
                            order.getOrderWeekendAddress().getStreet(), order.getOrderWeekendAddress().getBuildingNumber(),
                            order.getOrderWeekendAddress().getApartmentNumber(), order.getOrderWeekendAddress().getPostalCode());
                }
            }
        }
        deliveryService.createDeliveryChangeLog(new OrderDeliveryChangeLogData(data.getOrderId(), data.getDeliveryId(), deliveryOrder.getOrderProductId(), "change_address", before, after, authenticatedUser.getUserId())); // TODO type as enum
        DriverData driver = driversService.getDefaultDriverForCity(data.getCityId());
        ordersJdbcRepository.changeDeliveryAddress(data, driver.getId());
    }

    // SECTION: Informacje na temat dostaw: BUTTON: Zmień Godziny
    public void changeDeliveryHours(OrderDeliveryChangeHoursData data) {
        log.debug("CHANGE DELIVERY HOURS orderId: " + data.getOrderId() + " deliveryId: " + data.getDeliveryId());
        if (data.getOrderId() == null) {
            throw new IllegalArgumentException("order id is null");
        }
        if (data.getDeliveryId() == null) {
            throw new IllegalArgumentException("delivery id is null");
        }
        if (data.getHourFrom() == null) {
            throw new IllegalArgumentException("Podaj godzine od");
        }
        if (data.getHourFrom() == null) {
            throw new IllegalArgumentException("Podaj godzine do");
        }
        if (data.getHourFrom().isAfter(data.getHourTo())) {
            throw new IllegalArgumentException("hour from > to");
        }
        String before = "";
        String after = "" + data.getHourFrom() + " - " + data.getHourTo();
        OrderDeliveryOrderData deliveryOrder = deliveryService.getDeliveryOrder(data.getDeliveryId());
        if (deliveryOrder == null) {
            throw new IllegalArgumentException("Nie mogę pobrać dostawy o id: " + data.getDeliveryId());
        }
        if (deliveryOrder.getHourFrom() != null) {
            before = deliveryOrder.getHourFrom() + " - " + deliveryOrder.getHourTo();
        } else {
            OrderDetailsData order = getOrder(data.getOrderId());
            if (order == null) {
                throw new IllegalArgumentException("Nie mogę pobrać zamówienia o id: " + data.getOrderId());
            }
            before = order.getHoursFrom() + " - " + order.getHoursTo();
            if (order.getWeekendHoursStatus()) {
                if (DayOfWeek.SATURDAY.equals(deliveryOrder.getDeliveryDate().getDayOfWeek()) || DayOfWeek.SUNDAY.equals(deliveryOrder.getDeliveryDate().getDayOfWeek())) {
                    before = order.getWeekendHoursFrom() + " - " + order.getWeekendHoursTo();
                }
            }
        }
        deliveryService.createDeliveryChangeLog(new OrderDeliveryChangeLogData(data.getOrderId(), data.getDeliveryId(), deliveryOrder.getOrderProductId(), "change_hours", before, after, authenticatedUser.getUserId())); //TODO enum for type
        ordersJdbcRepository.changeDeliveryHours(data);
    }

    // SECTION: Informacje na temat dostaw:  BUTTON: Oznacz jako dostarczone
    public void setDeliveryAsDelivered(Long orderId, Long deliveryId) {
        log.debug("SET DELIVERY AS DELIVERED orderId: " + orderId + " deliveryId: " + deliveryId);
        if (orderId == null) {
            throw new IllegalArgumentException("orderId is null");
        }
        if (deliveryId == null) {
            throw new IllegalArgumentException("deliveryId is null");
        }
        OrderDeliveryOrderData deliveryOrder = deliveryService.getDeliveryOrder(deliveryId);
        if (deliveryOrder == null) {
            throw new IllegalArgumentException("Nie mogę pobrać dostawy w zamówieniu id: " + deliveryId);
        }
        if (deliveryOrder.getStatus().longValue() != Const.DELIVERY_ORDER_STATUS_WAITING) {
            throw new IllegalArgumentException("Niepoprawny status zamówienia: " + deliveryOrder.getStatus().longValue());
        }
        // only for driver
/*
if( isset( $data['type'] ) && $data['type'] == 'driver' ) {
			$q = mysqli_query( $db, "SELECT * FROM delivery_orders WHERE id = " . $data['id'] );
			$delivery = mysqli_fetch_assoc( $q );
			$res = strtotime( date("Y-m-d") ) - strtotime( $delivery['delivery_date'] );
			if( $res >= 172800 ) {
				$ret['error'] = "Nie można oznaczyć dostawy jako dostarczonej";
				return $ret;
			}
		}
 */
        ordersJdbcRepository.updateDeliveryStatus(deliveryId, "1");

        Long nextDevliveryToOrderCount = ordersJdbcRepository.getNextDevliveryToOrderCount(orderId);
        if (nextDevliveryToOrderCount.longValue() == 0) {
            ordersJdbcRepository.updateStatusOfOrder(orderId, Const.ORDER_STATUS_COMPLETED);
        }
        deliveryOrder = deliveryService.getDeliveryOrder(deliveryId);
        if (deliveryOrder == null) {
            throw new IllegalArgumentException("Nie mogę pobrać dostawy w zamówieniu id: " + deliveryId);
        }
        OrderDetailsData order = getOrder(orderId);
        if (!order.getExceptionDriver()) {
            LocalDate date = LocalDate.now();
            DriverPayData driverPay = driversService.getAndCreateDriverPay(deliveryOrder.getDriverId());
            OrderCustomerData customerAddress = order.getCustomerForOrder();
            String address = prepareAddressWithoutBrackets(deliveryOrder, date, order, customerAddress);
            log.debug("orderId: " + orderId + " deliveryId: " + deliveryId + " deliveryOrder.getOrderProductId(): " + deliveryOrder.getOrderProductId() + " address: " + address);
            Long count = driversService.getDriverSettlementCountFor(deliveryOrder.getDriverId(), date, address);
            BigDecimal value = null;
            if (count.longValue() > 0) {
                value = driverPay.getSameAddress();
            } else {
                value = driverPay.getNormal();
            }
            log.debug("orderId: " + orderId + " deliveryId: " + deliveryId + " deliveryOrder.getOrderProductId(): " + deliveryOrder.getOrderProductId() + " address: " + address + " value: " + value);
            count = driversService.getDriverSettlementForDeliveryCount(deliveryId);
            if (count.longValue() == 0) {
                driversService.createDriverSettlement(
                        new DriverSettlementCreateData(deliveryId,//deliveryId,
                                LocalDateTime.of(date, LocalTime.now()),// dateTime,
                                value,    // amount,
                                deliveryOrder.getDriverId(),    // driverId,
                                address // address
                        ));
            }
            count = deliveryService.getGroupedDeliveriesCount(deliveryId);
            if (count.longValue() > 0) {
                List<OrderDeliveryOrderData> list = deliveryService.getGroupedDeliveries(deliveryId);
                for (OrderDeliveryOrderData delivery : list) {
                    driversService.createDriverSettlement(
                            new DriverSettlementCreateData(delivery.getId(),//deliveryId,
                                    LocalDateTime.of(date, LocalTime.now()),// dateTime,
                                    driverPay.getSameAddress(),    // amount,
                                    deliveryOrder.getDriverId(),    // driverId,
                                    address // address
                            ));
                }
            }
        }
        if (order.getNewCalendar()) {
            ordersJdbcRepository.setOrderDayAsDeliveredForOrderProductForDate(deliveryOrder.getOrderProductId(), deliveryOrder.getOriginalDeliveryDate(), orderId);
        }
    }

    private String prepareAddressWithoutBrackets(OrderDeliveryOrderData deliveryOrder, LocalDate date, OrderDetailsData order, OrderCustomerData customerAddress) {
        String address = null;
        if (!date.getDayOfWeek().equals(DayOfWeek.SUNDAY) && !date.getDayOfWeek().equals(DayOfWeek.SATURDAY)) {
            address = makeAddressWithoutBrackets(customerAddress.getCityName(), customerAddress.getStreet(), customerAddress.getBuildingNumber(), customerAddress.getApartmentNumber(), customerAddress.getPostalCode());
        } else {
            if (order.getOrderWeekendAddress().getWeekendAddressStatus()) {
                OrderWeekendAddressData weekendAddress = order.getOrderWeekendAddress();
                address = makeAddressWithoutBrackets(weekendAddress.getCityName(), weekendAddress.getStreet(), weekendAddress.getBuildingNumber(), weekendAddress.getApartmentNumber(), weekendAddress.getPostalCode());
            } else {
                address = makeAddressWithoutBrackets(customerAddress.getCityName(), customerAddress.getStreet(), customerAddress.getBuildingNumber(), customerAddress.getApartmentNumber(), customerAddress.getPostalCode());
            }
        }
        if (deliveryOrder.getDa()) {
            address = makeAddressWithoutBrackets(deliveryOrder.getDaCity(), deliveryOrder.getDaStreet(), deliveryOrder.getDaHouseNo(), deliveryOrder.getDaApartmentNo(), deliveryOrder.getDaPostalCode());
        }
        return address;
    }

    OrderProductInfoData getOrderProductInfo(Long orderProductId) {
        return ordersJdbcRepository.getOrderProductInfo(orderProductId);
    }

    public void cancelOrderWithReason(Long orderId, String reason) {
        log.debug("CANCEL ORDER WITH REASON orderId: " + orderId + " reason: " + reason);
        if (orderId == null) {
            throw new IllegalArgumentException("orderId is null");
        }
        if (reason == null || reason.isEmpty()) {
            throw new IllegalArgumentException("Uzupełnij powód anulowania zamówienia");
        }
        ordersJdbcRepository.createCanceledOrder(orderId, reason);
        updateOrderStatus(orderId, 6L, null); //IMP
    }

    public void updateOrderStatus(Long orderId, Long statusId, LocalDate date) {
        if (orderId == null) {
            throw new IllegalArgumentException("orderId is null");
        }
        if (statusId == null) {
            throw new IllegalArgumentException("statusId is null");
        }
        OrderDetailsData oldOrder = getOrder(orderId);  //IMP
        if (oldOrder == null) {
            throw new IllegalArgumentException("(1 oldOrder) Nie mogę pobrać zamówienia o id: " + orderId);
        }
        if (oldOrder.getStatusId().longValue() == 7 && statusId.longValue() == 5) {
            startDiet(orderId, date);
        }
        ordersJdbcRepository.createOrderStatusChange(orderId, oldOrder.getStatusId(), statusId.longValue());
        ordersJdbcRepository.updateStatusOfOrder(orderId, statusId);

        if (statusId.longValue() == 7) {
            stopDiet(orderId, date);
        }

        OrderDetailsData tmp = getOrder(orderId);  //IMP
        if (tmp == null) {
            throw new IllegalArgumentException("(2 tmp)Nie mogę pobrać zamówienia o id: " + orderId);
        }

        if (tmp.getStatusId().longValue() == 5) {
            OrderDetailsData order = tmp;

            Long count = deliveryService.getDeliveryOrdersCount(orderId);
            if (count.longValue() == 0) {
                List<OrderProductData> products = findOrderProductsNotDeleted(orderId);
                for (OrderProductData product : products) {
                    if (!order.getNewCalendar()) {
                        calculateDelivery(orderId, product.getDateFrom(), product.getWeekendOptionId(), order, Long.valueOf(product.getDays()), product.getQuantity(), product.getId());
                    } else {
                        calculateDeliveryNewCalendar(orderId, product.getWeekendOptionId(), order, Long.valueOf(product.getDays()), product.getQuantity(), product.getId());
                    }
                }
            }

        } else {
            ;
        }
        if (tmp.getGroupOrders() && tmp.getStatusId().longValue() == 5) {
            Integer count = ordersJdbcRepository.getDeliveryOrdersByOrderAndStatusCount(orderId, "1");
            if (count.intValue() == 0) {
                groupOrder(orderId);
            }
        }
    }

    List<OrderProductInfoData> findOrderProducts(Long orderId) {
        List<Map<String, Object>> rows = ordersJdbcRepository.findOrderProducts(orderId);
        List<OrderProductInfoData> products = new ArrayList<>();
        for (Map row : rows) {
            products.add(
                    new OrderProductInfoData(
                            Long.valueOf(String.valueOf(row.get("id"))),
                            Long.valueOf(String.valueOf(row.get("order_id"))),
                            Long.valueOf(String.valueOf(row.get("days"))),
                            Long.valueOf((long) Double.parseDouble(String.valueOf(row.get("quantity")))),
                            Long.valueOf(String.valueOf(row.get("in_weekend"))),
                            row.get("from") != null ? LocalDate.parse(String.valueOf(row.get("from")), dateFormatter) : null,
                            Long.valueOf(String.valueOf(row.get("days"))).longValue() == 1L)
            );
        }
        return products;
    }

    List<OrderProductData> findOrderProductsNotDeleted(Long orderId) {
        List<Map<String, Object>> rows = ordersJdbcRepository.findOrderProductsNotDeleted(orderId);

        List<OrderProductData> products = new ArrayList<>();
        for (Map row : rows) {
            products.add(
                    new OrderProductData(
                            Long.valueOf(String.valueOf(row.get("id"))),
                            Long.valueOf((long) Double.parseDouble(String.valueOf(row.get("quantity")))),
                            row.get("from") != null ? LocalDate.parse(String.valueOf(row.get("from")), dateFormatter) : null,
                            Long.valueOf(String.valueOf(row.get("days"))),
                            Long.valueOf(String.valueOf(row.get("product_id"))),
                            Long.valueOf(String.valueOf(row.get("in_weekend"))),
                            prepareTestIndicatorCode(row),
                            Long.valueOf(String.valueOf(row.get("category_id")))
                    )
            );
        }
        return products;

    }

    void groupOrder(Long orderId) {
        Long count = deliveryService.getDeliveryOrdersCount(orderId);
        if (count.longValue() > 0) {
            deliveryService.updateDeliveryStatusForOrder(orderId, "3");
            List<OrderProductInfoData> orderProducts = findOrderProducts(orderId);
            for (OrderProductInfoData orderProduct : orderProducts) {
                List<OrderDeliveryOrderData> deliveries = deliveryService.findDeliveryOrdersGroupByDeliveryDate(orderProduct.getId());
                for (OrderDeliveryOrderData delivery : deliveries) {
                    deliveryService.updateDeliveryStatus(delivery.getId(), "0");
                    deliveryService.updateDeliveryGroupFor(delivery.getId(), orderProduct.getId(), delivery.getDeliveryDate(), "3");
                }
            }
        }
    }

    public void changeOrder(Long orderId, String what, String value) {
        if ("deliveryMethod".equals(what)) {
            changeDeliveryMethod(orderId, Long.valueOf(value));
        } else if ("paymentMethod".equals(what)) {
            changePaymentMethod(orderId, Long.valueOf(value));
        } else if ("paymentStatus".equals(what)) {
            changePaymentStatus(orderId, Long.valueOf(value));
        } else if ("orderStatus".equals(what)) {
            changeOrderStatus(orderId, Long.valueOf(value));
        } else if ("receipt".equals(what)) {
            changeReceiptStatus(orderId, Boolean.valueOf(value));
        } else if ("invoice".equals(what)) {
            changeInvoiceStatus(orderId, Boolean.valueOf(value));
        }
    }

    void changeDeliveryMethod(Long orderId, Long deliveryMethodId) {
        OrderDetailsData order = getOrder(orderId);
        deliveryService.changeDeliveriesStatus(order, orderId, deliveryMethodId);
    }

    void changePaymentMethod(Long orderId, Long paymentMethodId) {
        // changePaymentMethodOrder
        OrderDetailsData order = getOrder(orderId); //IMP
        paymentsService.changePaymentMethod(orderId, paymentMethodId, order.getPaymentMethodId());
    }

    void changePaymentStatus(Long orderId, Long paymentStatusId) {
        // changePaymentStatusToOrder
        OrderDetailsData order = getOrder(orderId);
        List<OrderProductData> products = findOrderProductsNotDeleted(orderId);
        List<OrderProductCreateData> items = new ArrayList<>();
        for (OrderProductData product : products) {
            items.add(new OrderProductCreateData(
                            orderId, //Long orderId,
                            product.getDietId(), //Long dietId,
                            product.getProductId(), //Long dietTypeId,  //TODO mess
                            product.getQuantity(), //Long quantity,
                            product.getDateFrom(), //LocalDate dateFrom,
                            product.getDays(), //Long days,
                            product.getWeekendOptionId(), //Long weekendOptionId,
                            product.getTestIndicatorCode(), //Boolean testDay,
                            null, //Boolean extrasOne,
                            null, //Set<Long> extraIds
                            product.getId()
                    )
            );
        }
        paymentsService.changePaymentStatus(order, items, paymentStatusId);
    }

    void changeOrderStatus(Long orderId, Long orderStatusId) {
        updateOrderStatus(orderId, orderStatusId, null);
    }

    void changeReceiptStatus(Long orderId, Boolean status) {
        // changeReceiptStatus
        changeReceiptStatus(orderId, status.equals(Boolean.TRUE) ? "1" : "0");
    }

    void changeInvoiceStatus(Long orderId, Boolean status) {
        // changeCustomerW
        ordersJdbcRepository.changeInvoiceStatus(orderId, status.equals(Boolean.TRUE) ? "1" : "0");
    }

    void changeReceiptStatus(Long orderId, String status) {
        ordersJdbcRepository.changeReceiptStatus(orderId, status);
    }


    public Long addOrder(OrderAddData data) {
        log.debug("ADD ORDER");
        if (data == null) {
            throw new IllegalArgumentException("order is null");
        }
        if (data.getCustomerId() == null) {
            throw new IllegalArgumentException("Wybierz klienta");
        }
        if (data.getPurchaseDateTime() == null) {
            throw new IllegalArgumentException("Uzupełnij datę sprzedaży");
        }
        if (data.getPurchaseDateTime().toLocalDate().isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("Nieprawidłowa data sprzedaży");
        }
        if (data.getPaymentMethodId() == null) {
            throw new IllegalArgumentException("Wybierz metodę płatności");
        }
        if (data.getDeliveryMethodId() == null) {
            throw new IllegalArgumentException("Wybierz metodę dostawy");
        }
        if (data.getDriverId() == null) {
            throw new IllegalArgumentException("Wybierz kierowcę");
        }
        if (data.getOrderStatusId() == null) {
            throw new IllegalArgumentException("Wybierz status");
        }

        // Dodaj co najmniej 1 produkt do zamówienia

        String orderUserId = calculateUserOrderId();
        CustomerData customer = customersService.getCustomer(data.getCustomerId());
        if (customer == null) {
            throw new IllegalArgumentException("Nie mogę pobrać klienta id: " + data.getCustomerId());
        }

        String firstName = customer.getFirstName();
        String lastName = customer.getLastName();
        String phone = customer.getPhoneNumber();
        String type = "";
        String street = customer.getStreet();
        String houseNo = customer.getBuildingNumber();
        String apartment = customer.getApartmentNumber();
        String postalCode = customer.getPostalCode();
        String city = customer.getCity();
        String deliveryAddressType = "";
        String deliveryAddressFirstName = "";
        String deliveryAddressLastName = "";
        String deliveryAddressCompanyName = "";
        String deliveryAddressStreet = "";
        String deliveryAddressHouseNo = "";
        String deliveryAddressApartmentNo = "";
        String deliveryAddressPostalCode = "";
        String deliveryAddressCity = "";
        Boolean invoice = customer.getInvoice();
        String invoiceType = "the_same";
        String invoiceFirstName = customer.getFirstName();
        String invoiceLastName = customer.getLastName();
        String invoiceTaxNo = customer.getInvoiceTaxNo();
        String invoiceCompanyName = customer.getInvoiceCompanyName();
        String invoiceStreet = customer.getInvoiceStreet();
        String invoiceHouseNo = customer.getInvoiceHouseNo();
        String invoiceApartmentNo = customer.getInvoiceApartmentNo();
        String invoicePostalCode = customer.getInvoicePostalCode();
        String invoiceCity = customer.getInvoiceCity();
        Language lang = Language.PL;
        String currency = "PLN";
        String uniqueId = "";
        customersService.updateCustomerDemanding(data.getCustomerId(), data.getDemandingCustomer());

        Long loggedBuyerId = customer.getId();
        String loggedBuyerEmail = customer.getEmail();
        String loggedBuyerType = customer.getType();
        String loggedBuyerFirstName = customer.getFirstName();
        String loggedBuyerLastName = customer.getLastName();
        Long deliveryMethodId = data.getDeliveryMethodId() != null ? data.getDeliveryMethodId() : 1;
        String deliveryOperatorName = dictionariesService.getDictionaryValueById(deliveryMethodId, DictionaryType.SHIPMENT_TYPES, lang);
        Long deliveryQuantity = 0L;
        BigDecimal deliveryPrice = BigDecimal.ZERO;
        Boolean exceptionDriver = data.getExceptionDriver() != null ? data.getExceptionDriver() : false;
        Long orderDriverId = data.getDriverId() != null ? data.getDriverId() : 0L;
        Boolean groupOrder = data.getNewOrderGroup() != null ? data.getNewOrderGroup() : false;
        Long paymentMethodId = data.getPaymentMethodId();
        PaymentMethodData paymentMethodData = paymentsService.getPaymentMethod(paymentMethodId);
        if (paymentMethodData == null) {
            throw new IllegalArgumentException("Nie mogę pobrać metody płatności id: " + paymentMethodId);
        }
        Long paymentStatus = 0L;
        String paymentCompanyName = paymentMethodData.getName();
        String paymentOperator = paymentMethodData.getOperator();
        BigDecimal paymentPrice = paymentMethodData.getFee();

//        if (paymentMethodId.intValue() == Const.PAYMENT_METHOD_CASH) {
//            Set<String> citiesWithCash = new HashSet<>();
//            citiesWithCash.add("Dąbrowa Górnicza");
//            citiesWithCash.add("Sosnowiec");
//            citiesWithCash.add("Będzin");
//
//            if (citiesWithCash.contains(city)) {
//                citiesService.addFailedCityCheck(city);
//                throw new IllegalArgumentException("Płatność gotówką dostępna jest tylko w następujących miastach: " + citiesWithCash.stream().collect(Collectors.joining(", ")));
//            }
//        }
        String comment = data.getComment();
        LocalTime hourOf = LocalTime.of(4, 0);
        LocalTime hourTo = customer.getWeekPreferredHoursTo();
        Boolean anotherHoursWeekend = customer.getAnotherHoursWeekend();
        LocalTime weekendHourOf = LocalTime.of(4, 0);
        LocalTime weekendHourTo = customer.getWeekendPreferredHoursTo();

        Boolean anotherWeekendAddress = customer.getAnotherWeekendAddress();
        String weekendStreet = customer.getWeekendStreet();
        String weekendHouseNo = customer.getWeekendHouseNo();
        String weekendApartmentNo = customer.getWeekendApartmentNo();
        String weekendPostalCode = customer.getWeekendPostalCode();
        String weekendCity = customer.getWeekendCity();
        LocalDateTime purchaseDate = data.getPurchaseDateTime();
        if (purchaseDate == null) {
            purchaseDate = LocalDateTime.now();
        }
        BigDecimal basketSum = BigDecimal.ZERO;
        BigDecimal basketSumNo = BigDecimal.ZERO;

        Long cityId = (city != null && !city.isEmpty()) ? dictionariesService.getDictionaryIdByValue(city, DictionaryType.CITIES, Language.PL) : null;

        CityDiscountData cityDiscountData = citiesService.getCityDiscount(cityId);
        boolean isNewCity = citiesService.isNewCity(city);

        if (cityDiscountData != null) {
            if (isNewCity) {
                if (cityDiscountData.getDateTo() != null && !LocalDate.now().isAfter(cityDiscountData.getDateTo())) {
                    if (data.getDiscountName() != null && !data.getDiscountName().isEmpty()) {
                        throw new IllegalArgumentException("Nie można użyć kodu rabatowego ponieważ klient posiada już rabat na zamówienia na 10%");
                    }
                }
            }
        }

        List<OrderProductCreateData> items = new ArrayList<>();
        for (OrderProductCreateData product : data.getProducts()) {
            if (product.getDietId() == null) {
                throw new IllegalArgumentException("Wybierz dietę dla produktu");
            }
            if (product.getDietTypeId() == null) {
                throw new IllegalArgumentException("Wybierz typ diety dla produktu");
            }
            if (product.getDateFrom() == null) {
                throw new IllegalArgumentException("Wybierz datę dla produktu");
            }
            if (product.getQuantity() == null) {
                throw new IllegalArgumentException("Podaj ilość na produkcie");
            }
            if (product.getDays() == null) {
                throw new IllegalArgumentException("Podaj liczbę dni na produkcie");
            }
            if (product.getQuantity().longValue() <= 0) {
                throw new IllegalArgumentException("Nieprawidłowa ilość na produkcie");
            }
            if (product.getDays().longValue() <= 0) {
                throw new IllegalArgumentException("Nieprawidłowa liczba dni na produkcie");
            }

            Long quantity = product.getQuantity();
            Long days = product.getDays();
            if (product.getTestDay()) {
                quantity = 1L;
                days = 1L;
            }

            ProductDetailData productData = productsService.getProduct(product.getDietTypeId());
            if (productData == null) {
                throw new IllegalArgumentException("Nie mogę pobrać produktu product.getDietTypeId(): " + product.getDietTypeId());
            }

            items.add(new OrderProductCreateData(
                            product.getOrderId(),
                            product.getDietId(), //Long dietId,
                            product.getDietTypeId(), //Long dietTypeId,
                            quantity,
                            product.getDateFrom(), //LocalDate dateFrom,
                            days,
                            product.getWeekendOptionId(), //Long weekendOptionId,
                            product.getTestDay(), //Boolean testDay,
                            product.getExtrasOne(), //Boolean extrasOne, //TODO remove
                            product.getExtraIds(), //Set<Long> extraIds
                            productData.getGrossPriceRetail()
                    )
            );
        }

        if (data.getProducts() == null || data.getProducts().size() == 0) {
            throw new IllegalArgumentException("Zamówienie musi posiadać produkty");
        }

        DiscountResultData discountResultData = discountService.calculateDiscountsForOrderProducts(items, data.getDeliveryMethodId(), basketSum, basketSumNo);
        basketSum = discountResultData.getBasketSum();
        basketSumNo = discountResultData.getBasketSumNo();
        BigDecimal otherDiscount = BigDecimal.ZERO;
        DiscountType discountType = null;
        BigDecimal discountCodeValue = null;
        BigDecimal discountCodeValueTotal = null;
        BigDecimal basketSumBeforeDiscountCode = basketSum;
        BigDecimal basketSumAfterDiscountCode = null;
        log.debug("after calculateDiscountsForOrderProducts basketSum: " + basketSum + " basketSumNo: " + basketSumNo);
        if (data.getDiscountName() != null && !data.getDiscountName().isEmpty()) {
            DiscountData discount = ordersJdbcRepository.getDiscount(data.getDiscountName());
            if (discount == null) {
                discount = ordersJdbcRepository.getVoucher(data.getDiscountName());
            }
            if (discount != null) {
                log.debug("Using code for code: " + data.getDiscountName() + " discount.getType(): " + discount.getType() + " discount.getId(): " + discount.getId() + " discount.getValue: " + discount.getValue());
                if (discount.getDiscountType().equals("percent")) {
                    log.debug("discount percent");
                    discountType = DiscountType.PERCENT;
                    discountCodeValue = BigDecimal.valueOf(Double.valueOf(discount.getValue()));
                    discountCodeValueTotal = BigDecimal.valueOf(Double.valueOf(discount.getValue())).divide(BigDecimal.valueOf(100), 2, BigDecimal.ROUND_HALF_UP).multiply(basketSum);
                    basketSum = basketSum.subtract(discountCodeValueTotal);
                    basketSumAfterDiscountCode = basketSum;
                } else {
                    log.debug("discount value");
                    discountType = DiscountType.PRICE;
                    discountCodeValue = BigDecimal.valueOf(Double.valueOf(discount.getValue()));
                    discountCodeValueTotal = BigDecimal.valueOf(Double.valueOf(discount.getValue()));
                    basketSum = basketSum.subtract(BigDecimal.valueOf(Double.valueOf(discount.getValue())));
                    basketSumAfterDiscountCode = basketSum;
                }
                log.debug("After discount applied: basketSum: " + basketSum + " basketSumNo: " + basketSumNo);
            } else {
                throw new IllegalArgumentException("Nieprawidłowy kod rabatowy");
            }
        }

        BigDecimal discountCity = BigDecimal.ZERO;
        if (cityDiscountData != null) {
            if (isNewCity) {
                boolean doDiscount = false;
                if (cityDiscountData.getDateTo() != null && !LocalDate.now().isAfter(cityDiscountData.getDateTo())) {
                    doDiscount = true;
                }
                if (doDiscount) {
                    discountCity = cityDiscountData.getDiscountValue();
                } else {
                    discountCity = BigDecimal.ZERO;
                }
            }
        }


        OrderCustomerData customerData = prepareCustomer(data, firstName, lastName, phone, type, street, houseNo, apartment, postalCode, city, loggedBuyerEmail);

        OrderWeekendAddressData orderWeekendAddress = new OrderWeekendAddressData(
                anotherWeekendAddress, //boolean weekendAddressStatus,
                weekendStreet, //String street,
                weekendHouseNo, //String buildingNumber,
                weekendApartmentNo, //String apartmentNumber,
                weekendPostalCode, //String postalCode,
                (weekendCity != null && !weekendCity.isEmpty()) ? dictionariesService.getDictionaryIdByValue(weekendCity, DictionaryType.CITIES, Language.PL) : null, //Long cityId,
                weekendCity //String cityName
        );
        OrderInvoiceData orderInvoice = new OrderInvoiceData(
                invoice, //boolean invoiceWantedStatus,
                invoiceCompanyName, //String companyName,
                invoiceTaxNo, //String nip,
                invoiceStreet, //String street,
                invoiceHouseNo, //String buildingNumber,
                invoiceApartmentNo, //String apartmentNumber,
                invoicePostalCode, //String postalCode,
                (invoiceCity != null && !invoiceCity.isEmpty()) ? dictionariesService.getDictionaryIdByValue(invoiceCity, DictionaryType.CITIES, Language.PL) : null, //Long cityId,
                invoiceCity, //String cityName,
                false, //boolean invoiceIssuedStatus,
                null, //String invoiceWantedString
                invoiceType,
                invoiceFirstName,
                invoiceLastName
        );
        PaymentMethodData paymentMethod = new PaymentMethodData(
                paymentMethodData.getId(), paymentMethodData.getName(), paymentMethodData.getOperator(), paymentMethodData.getFee(), "0"
        );
        DeliveryMethodData deliveryMethod = deliveryService.getDeliveryMethod(deliveryMethodId);
        if (deliveryMethod == null) {
            throw new IllegalArgumentException("Nie mogę pobrać metody dostawy: " + deliveryMethodId);
        }
        DeliveryData deliveryData =
                new DeliveryData(
                        deliveryMethodId, // Long id,
                        "", // String dataType,
                        "", // String firstName,
                        "", // String lastName,
                        "", // String companyName,
                        "", // String street,
                        "", // String buildingNumber,
                        "", // String apartmentNumber,
                        "", // String postalCode,
                        null, // Long cityId,
                        "", // String cityName,
                        deliveryMethod.getName(), // String operatorName,
                        0L, // Long quantity,
                        BigDecimal.ZERO // BigDecimal price
                );

        BigDecimal basketSumBeforeCityDiscount = null;
        BigDecimal basketSumAfterCityDiscount = null;
        BigDecimal cityDiscountTotalValue = null;
        if (discountCity.compareTo(BigDecimal.ZERO) != 0) {
            basketSumBeforeCityDiscount = basketSum;
            cityDiscountTotalValue = basketSum.multiply(cityDiscountData.getDiscountValue().divide(BigDecimal.valueOf(100), 2, BigDecimal.ROUND_HALF_UP));
            basketSum = basketSum.subtract(cityDiscountTotalValue);
            otherDiscount = otherDiscount.add(cityDiscountTotalValue);
            basketSumAfterCityDiscount = basketSum;
            log.debug("After city discount applied: basketSum: " + basketSum + " basketSumNo: " + basketSumNo + " cityDiscountData.getDiscountValue(): " + cityDiscountData.getDiscountValue());
        }

        if (basketSum.compareTo(BigDecimal.ZERO) < 0) {
            basketSum = BigDecimal.ZERO;
        }
        BigDecimal discountValue = basketSumNo.subtract(basketSum);
        log.debug("before order insert discountValue: " + discountValue + " basketSum: " + basketSum + " basketSumNo: " + basketSumNo);
        OrderCreateData orderToInsert = null;
        try {
            orderToInsert = new OrderCreateData( //TODO finish
                    type,
                    purchaseDate, //LocalDateTime purchaseDate,
                    deliveryMethodId, //Long deliveryMethodId,
                    paymentMethodId, //Long paymentMethodId,
                    orderDriverId, //Long driverId,
                    data.getOrderStatusId(),
                    calculateUserOrderId(),
                    data.getNewOrderGroup(),
                    data.getComment(),
                    data.getExceptionDriver(),
                    lang,
                    InetAddress.getLocalHost().getHostAddress(),
                    customerData,
                    orderWeekendAddress,
                    orderInvoice,
                    paymentMethod,
                    deliveryData,
                    basketSum,
                    basketSumNo,
                    hourOf,
                    hourTo,
                    weekendHourOf,
                    weekendHourTo,
                    authenticatedUser.getUserId().toString(),
                    anotherHoursWeekend,
                    discountCity
            );
        } catch (UnknownHostException e) {
            throw new IllegalArgumentException("Nie mogę pobrać adresu ip: " + e.getMessage());
        }
        Long orderId = create(orderToInsert);
        log.debug("inserted orderId: " + orderId);
        log.debug("data.getDiscountName(): " + data.getDiscountName());

        if (DiscountType.PERCENT.equals(discountType)) {
            discountService.storeAppliedDiscount(new AppliedDiscountData(orderId, DiscountType.PERCENT, discountCodeValue, discountCodeValueTotal, basketSumBeforeDiscountCode, basketSumAfterDiscountCode));
        } else if (DiscountType.PRICE.equals(discountType)) {
            discountService.storeAppliedDiscount(new AppliedDiscountData(orderId, DiscountType.PRICE, discountCodeValue, discountCodeValueTotal, basketSumBeforeDiscountCode, basketSumAfterDiscountCode));
        }
        if (discountCity.compareTo(BigDecimal.ZERO) != 0) {
            discountService.storeAppliedDiscount(new AppliedDiscountData(orderId, DiscountType.CITY, cityDiscountData.getDiscountValue(), cityDiscountData.getDiscountValue(), basketSumBeforeCityDiscount, basketSumAfterCityDiscount));
        }
        if (discountResultData.getDietDiscountsApplied().size() > 0) {
            for (AppliedDiscountData dietDiscount: discountResultData.getDietDiscountsApplied()) {
                otherDiscount = otherDiscount.add(dietDiscount.getDiscountValueTotal());
                discountService.storeAppliedDiscount(new AppliedDiscountData(orderId, DiscountType.DIET, dietDiscount.getDiscountValue(), dietDiscount.getDiscountValue(), dietDiscount.getGrossPriceBefore(), dietDiscount.getGrossPriceAfter()));
            }
        }
        if (discountResultData.getSaleDiscountsApplied().size() > 0) {
            for (AppliedDiscountData saleDiscount: discountResultData.getSaleDiscountsApplied()) {
                otherDiscount = otherDiscount.add(saleDiscount.getDiscountValue());
                discountService.storeAppliedDiscount(new AppliedDiscountData(orderId, DiscountType.SALE, saleDiscount.getDiscountValue(), saleDiscount.getDiscountValue(), saleDiscount.getGrossPriceBefore(), saleDiscount.getGrossPriceAfter()));
            }
        }
        if (data.getDiscountName() != null && !data.getDiscountName().isEmpty()) {
            DiscountData discount = ordersJdbcRepository.getDiscount(data.getDiscountName());
            if (discount == null) {
                discount = ordersJdbcRepository.getVoucher(data.getDiscountName());
            }
            if (discount != null) {
                log.debug("Using discount for store code: " + data.getDiscountName() + " discount.getType(): " + discount.getType() + " discount.getId(): " + discount.getId() + " discount.getValue: " + discount.getValue());
                try {
                    ordersJdbcRepository.useDiscountCode(new OrderDiscountCodeUsageData(orderId, data.getDiscountName(), discount.getType(), discount.getId(), InetAddress.getLocalHost().getHostAddress()));
                } catch (UnknownHostException e) {
                    log.error("Problem during get ip " + e.getMessage());
                    throw new IllegalArgumentException("Nie można pobrać adresu ip");
                }
                if (discount.getType().equals("voucher")) {
                    ordersJdbcRepository.updateVoucherStatus(discount.getId(), "0");
                }
                log.debug("After discount stored: basketSum: " + basketSum + " basketSumNo: " + basketSumNo);
            } else {
                throw new IllegalArgumentException("Nieprawidłowy kod rabatowy");
            }
        }

        BigDecimal divide = BigDecimal.ZERO;
        for (OrderProductCreateData productInOrder : items) {
            divide = divide.add(BigDecimal.valueOf(productInOrder.getDays()).multiply(BigDecimal.valueOf(productInOrder.getQuantity())));
        }
        BigDecimal discountValuePerItem = discountValue.divide(divide, 2, BigDecimal.ROUND_HALF_UP);

        DriverData defaultDriverForCity = driversService.getDefaultDriverForCity(cityId);
        if (defaultDriverForCity == null) {
            throw new IllegalArgumentException("Nie mogę pobrać domyślnego kierowcy dla miasta id: " + cityId);
        }
        Long driverId = defaultDriverForCity.getId();

        BigDecimal salesDiscount = BigDecimal.ZERO;
        for (OrderProductCreateData productInOrder : items) {
            for (SaleTableData sale : discountService.findSaleTable(productInOrder.getDietId())) {
                int l = 0;
                final List<SaleVariantData> variants = discountService.findSaleVariants(sale.getId());
                int mustHave = variants.size();
                for (SaleVariantData variant : variants) {
                    if (productInOrder.getDietId().equals(variant.getDietId())) {
                        if (productInOrder.getDays().longValue() >= variant.getDays().longValue()) {
                            l++;
                        }
                    }
                }

                if (l == mustHave) {
                    salesDiscount = sale.getDiscount();
                }
            }


            BigDecimal extrasPrice = BigDecimal.ZERO;
            BigDecimal minusPrice = BigDecimal.ZERO;
            Long productId = productInOrder.getDietTypeId();
            ProductDetailData productData = productsService.getProduct(productId);
            if (productData == null) {
                throw new IllegalArgumentException("Nie mogę pobrać produktu productId: " + productId);
            }


            BigDecimal netPrice = productData.getNetPriceRetail();
            BigDecimal taxValue = productsService.getTaxValue(productData.getTaxId());
            BigDecimal grossPrice = productData.getGrossPriceRetail();
            BigDecimal days = BigDecimal.valueOf(productInOrder.getDays());
            BigDecimal quantity = BigDecimal.valueOf(productInOrder.getQuantity());
            log.debug("netPrice: " + netPrice + " taxValue: " + taxValue);

            BigDecimal personalDeliveryDiscount = BigDecimal.ZERO;
            if (deliveryMethodId.longValue() == Const.DELIVERY_METHOD_PERSONAL) {
                BigDecimal deliveryDiscount = deliveryService.getDeliveryDiscount(Const.DELIVERY_METHOD_PERSONAL);
                minusPrice = days.multiply(deliveryDiscount).multiply(quantity);
                //otherDiscount = otherDiscount.add(minusPrice);
                personalDeliveryDiscount = deliveryDiscount;
                discountService.storeAppliedDiscount(new AppliedDiscountData(orderId, DiscountType.DELIVERY_PERSONAL, deliveryDiscount, minusPrice, basketSum, basketSum));
                log.debug("minusPrice applied: " + minusPrice);
            }


            for (Long id : productInOrder.getExtraIds()) {
                BigDecimal extPrice = productsService.getExtrasPrice(id);
                if (extPrice == null) {
                    throw new IllegalArgumentException("Nie mogę pobrać ceny extras id: " + id);
                }
                extrasPrice = extrasPrice.add(extPrice); //TODO getExtras should be
            }


            log.debug("extrasPrice: " + extrasPrice);
            if (productInOrder.getTestDay()) {
                grossPrice = productsService.getTestSetPrice(productInOrder.getDietId());
                netPrice = productsService.getTestSetPrice(productInOrder.getDietId());
                log.debug("test product grossPrice: " + grossPrice + " netPrice: " + netPrice);
            }
            BigDecimal price = grossPrice.multiply(quantity).multiply(days);
            log.debug("price: " + price);
            if (salesDiscount.compareTo(BigDecimal.ZERO) != 0) {
                price = price.subtract(price.multiply(salesDiscount).divide(BigDecimal.valueOf(100), 2, BigDecimal.ROUND_HALF_UP));
                //TODo Check $price = round($price, 2);
                log.debug("salesDiscount applied price: " + price + " salesDiscount: " + salesDiscount);
            }
            BigDecimal discountDiet = productsService.getDietDiscount(productInOrder.getDays(), productInOrder.getDietId()); // TODO move to discount service
            price = price.subtract(discountDiet.multiply(days)).add(extrasPrice).subtract(minusPrice);

            log.debug("discountDiet: " + discountDiet + " price: " + price);
            BigDecimal priceForPiece = grossPrice;
            BigDecimal qNetPriceForPiece = netPrice;
            BigDecimal qNetPricee = netPrice.multiply(quantity).multiply(days);
            BigDecimal qPriceForPiece = priceForPiece;
            BigDecimal qPrice = price;
            log.debug("priceForPiece: " + priceForPiece + " qNetPriceForPiece: " + qNetPriceForPiece + " qNetPricee: " + qNetPricee + " qPriceForPiece: " + qPriceForPiece + " qPrice: " + qPrice);
            String name = productsService.getProductNameInLanguage(productInOrder.getDietTypeId(), Language.PL.getCode());

            BigDecimal priceItemAfterDiscount = price.subtract(discountValuePerItem.multiply(days).multiply(quantity));
            log.debug("priceItemAfterDiscount: " + priceItemAfterDiscount + " discountValuePerItem: " + discountValuePerItem);
            priceItemAfterDiscount = priceItemAfterDiscount.divide(days.multiply(quantity), 2, RoundingMode.HALF_UP);
            log.debug("priceItemAfterDiscount: " + priceItemAfterDiscount + " productData.getTaxId(): " + productData.getTaxId() + " taxValue: " + taxValue);

            productInOrder.setOrderId(orderId); //TODO change in the future
            Long orderProductId = ordersJdbcRepository.createOrderProduct(productInOrder, name, productData.getTaxId(), taxValue, qNetPriceForPiece,
                    qNetPricee, priceForPiece, price, productInOrder.getDays());

            for (Long extraId : productInOrder.getExtraIds()) {
                log.debug("store extras orderProductId: " + orderProductId + " extraId: " + extraId);
                ordersJdbcRepository.createOrderProductExtras(orderProductId, extraId);
            }
            priceItemAfterDiscount = calculatePriceItemAfterDiscount(price, days, quantity, discountCodeValue, discountCodeValueTotal, discountType);

            ordersJdbcRepository.createPartialProductAfterDiscount(orderId, orderProductId, priceItemAfterDiscount);
        }

        log.debug("getting order id: " + orderId);
        OrderDetailsData order = getOrder(orderId);

        if (data.getOrderStatusId().longValue() == Const.ORDER_STATUS_APPROVED) {
            List<OrderProductData> products = findOrderProductsNotDeleted(orderId);
            for (OrderProductData product : products) {
                if (!order.getNewCalendar()) {
                    calculateDelivery(orderId, product.getDateFrom(), product.getWeekendOptionId(), order, product.getDays(), product.getQuantity(), product.getId());
                } else {
                    calculateDeliveryNewCalendar(orderId, product.getWeekendOptionId(), order, product.getDays(), product.getQuantity(), product.getId());
                }
            }
            log.debug("delivery schema set: " + orderId + " data.getOrderStatusId(): " + data.getOrderStatusId());
        }

        //emails
        if (data.getSendEmail()) {
            log.debug("sending email order request");
            emailsService.sendOrderRequested(order, items);
        }
        log.debug("sending email order created");
        emailsService.sendOrderNew(order, items);

        if (data.getNewOrderGroup()) {
            log.debug("grouping order id: " + orderId);
            groupOrder(orderId);
        }
        return orderId;
    }

    private BigDecimal calculatePriceItemAfterDiscount(BigDecimal itemPrice, BigDecimal days, BigDecimal sets, BigDecimal codeDiscount, BigDecimal codeDiscountTotal, DiscountType discountType) {
        log.debug("calculatePriceItemAfterDiscount itemPrice: " + itemPrice + " days: " + days + " sets: " + sets + " codeDiscount:  " + codeDiscount + " codeDiscountTotal: " + codeDiscountTotal + " discountType: " + (discountType != null ? discountType.code() : null));
        BigDecimal calculatedPrice = null;
        BigDecimal deliveryCount = days.multiply(sets);
        if (discountType == null) {
            return itemPrice.divide(deliveryCount, 2, BigDecimal.ROUND_HALF_UP);
        } else if (DiscountType.PERCENT.equals(discountType)) {
            calculatedPrice = itemPrice.subtract(itemPrice.multiply(codeDiscount).divide(DIVISOR_100, 2, BigDecimal.ROUND_HALF_UP));
            calculatedPrice = calculatedPrice.divide(deliveryCount, 2, BigDecimal.ROUND_HALF_UP);
            return calculatedPrice;
        } else if (DiscountType.PRICE.equals(discountType)) {
            calculatedPrice = itemPrice.subtract(codeDiscountTotal).divide(deliveryCount, 2, BigDecimal.ROUND_HALF_UP);
            return calculatedPrice;
        } else {
            throw new IllegalStateException("Illegal discount type: " + discountType.code());
        }
    }

    private OrderCustomerData prepareCustomer(OrderAddData data, String firstName, String lastName, String phone, String type, String street, String houseNo, String apartment, String postalCode, String city, String loggedBuyerEmail) {
        return new OrderCustomerData(
                data.getCustomerId(),
                firstName,
                lastName,
                phone,
                street,
                houseNo,
                apartment,
                postalCode,
                (city != null && !city.isEmpty()) ? dictionariesService.getDictionaryIdByValue(city, DictionaryType.CITIES, Language.PL) : null,
                city,
                type,
                loggedBuyerEmail);
    }

    public void sendOrderSummaryEmail(Long orderId) {
        List<OrderProductData> products = findOrderProductsNotDeleted(orderId);
        List<OrderProductCreateData> items = new ArrayList<>();
        for (OrderProductData product : products) {
            items.add(new OrderProductCreateData(
                            orderId, //Long orderId,
                            product.getDietId(), //Long dietId,
                            product.getProductId(), //Long dietTypeId,  //TODO mess
                            product.getQuantity(), //Long quantity,
                            product.getDateFrom(), //LocalDate dateFrom,
                            product.getDays(), //Long days,
                            product.getWeekendOptionId(), //Long weekendOptionId,
                            product.getTestIndicatorCode(), //Boolean testDay,
                            null, //Boolean extrasOne,
                            null, //Set<Long> extraIds
                            product.getId()
                    )
            );
        }
        emailsService.sendUpdateDiet(getOrder(orderId), items);
    }

    public List<OrderDaysData> findOrderDays(Long orderId) {
        List<Map<String, Object>> rows = ordersJdbcRepository.findOrderDays(orderId);
        List<OrderDaysData> list = new ArrayList<>();

        for (Map row : rows) {
            LocalDate date = LocalDate.parse(String.valueOf(row.get("delivery_date")), dateFormatter);
            OrderDaysData data = new OrderDaysData(
                    Long.valueOf(String.valueOf(row.get("id"))),
                    (String) row.get("name") + " (" + (String) row.get("category_name") + ")",
                    date,
                    "",
                    "",
                    Long.valueOf(String.valueOf(row.get("quantity")))
            );
            list.add(data);
        }

        return list;
    }

    public List<OrderDaysData> findOrderDaysHistory(Long orderId) {
        List<Map<String, Object>> rows = ordersJdbcRepository.findOrderDaysHistory(orderId);
        List<OrderDaysData> list = new ArrayList<>();

        for (Map row : rows) {
            LocalDate date = LocalDate.parse(String.valueOf(row.get("delivery_date")), dateFormatter);
            OrderDaysData data = new OrderDaysData(
                    Long.valueOf(String.valueOf(row.get("id"))),
                    (String) row.get("name") + " (" + (String) row.get("category_name") + ")",
                    date,
                    (String) row.get("status"),
                    dictionariesService.getDictionaryElementByCode((String) row.get("status"), DictionaryType.ORDER_DAY_STATUS, Language.PL).getValue(),
                    Long.valueOf(String.valueOf(row.get("quantity"))),
                    dictionariesService.getDictionaryElementByCode((String) row.get("delivery_info"), DictionaryType.ORDER_DAY_DELIVERY_INFO, Language.PL).getValue());
            list.add(data);
        }

        return list;
    }

    public List<OrderDaysData> findOrderDaysForDelivery(Long orderId, Long orderProductId) {
        List<Map<String, Object>> rows = ordersJdbcRepository.findOrderDaysForDelivery(orderId, orderProductId);
        List<OrderDaysData> list = new ArrayList<>();

        for (Map row : rows) {
            LocalDate date = LocalDate.parse(String.valueOf(row.get("delivery_date")), dateFormatter);
            OrderDaysData data = new OrderDaysData(
                    Long.valueOf(String.valueOf(row.get("id"))),
                    (String) row.get("name") + " (" + (String) row.get("category_name") + ")",
                    date,
                    (String) row.get("status"),
                    dictionariesService.getDictionaryElementByCode((String) row.get("status"), DictionaryType.ORDER_DAY_STATUS, Language.PL).getValue(),
                    Long.valueOf(String.valueOf(row.get("quantity"))),
                    dictionariesService.getDictionaryElementByCode((String) row.get("delivery_info"), DictionaryType.ORDER_DAY_DELIVERY_INFO, Language.PL).getValue());
            list.add(data);
        }

        return list;
    }

    public List<OrderDaysData> findOrderDaysForReactivateDeliveryFrom(Long orderId, Long orderProductId, LocalDate dateFrom) {
        List<Map<String, Object>> rows = ordersJdbcRepository.findOrderDaysForReactivateDeliveryFrom(orderId, orderProductId, dateFrom);
        List<OrderDaysData> list = new ArrayList<>();

        for (Map row : rows) {
            LocalDate date = LocalDate.parse(String.valueOf(row.get("delivery_date")), dateFormatter);
            OrderDaysData data = new OrderDaysData(
                    Long.valueOf(String.valueOf(row.get("id"))),
                    (String) row.get("name") + " (" + (String) row.get("category_name") + ")",
                    date,
                    (String) row.get("status"),
                    dictionariesService.getDictionaryElementByCode((String) row.get("status"), DictionaryType.ORDER_DAY_STATUS, Language.PL).getValue(),
                    Long.valueOf(String.valueOf(row.get("quantity"))),
                    dictionariesService.getDictionaryElementByCode((String) row.get("delivery_info"), DictionaryType.ORDER_DAY_DELIVERY_INFO, Language.PL).getValue());
            list.add(data);
        }

        return list;
    }

    private String calculateUserOrderId() {
        Random rand = new Random();
        String userOrderId = null;
        do {
            userOrderId = "md";
            for (int i = 0; i < 5; i++) {
                userOrderId += rand.nextInt(9 + 1);
            }
        } while (ordersJdbcRepository.getOrderIdByUserOrderId(userOrderId) != null);
        return userOrderId;
    }

    void createOrderStatusChange(Long orderId, Long oldStatusId, Long newStatusId) {
        ordersJdbcRepository.createOrderStatusChange(orderId, oldStatusId, newStatusId);
    }

    Long getNumberOfEditOrderCount(Long orderId) {
        return ordersJdbcRepository.getNumberOfEditOrderCount(orderId);
    }

    public void updateDemandingCustomer(Long orderId, Long customerId, Boolean state) {
        ordersJdbcRepository.update(new OrderUpdateData(state));
    }

    public void updateStatusForNewOrdersWithCashPayment(LocalDateTime from, LocalDateTime to) {
        log.debug("updateStatusForNewOrdersWithCashPayment start for from: " + from + " to: " + to);
        List<Map<String, Object>> rows = ordersJdbcRepository.findOrders(Const.ORDER_STATUS_NEW, Const.PAYMENT_METHOD_CASH, from, to, applicationConfig.getSchedulerUpdateOrderStatusLimit());
        for (Map row : rows) {
            updateStatusForOrdersWithCashPayment(Long.valueOf(String.valueOf(row.get("order_id"))));
        }
        log.debug("updateStatusForNewOrdersWithCashPayment stop");
    }

    public void updateStatusForNewOrdersWithTraditionalTransferPayment(LocalDateTime from, LocalDateTime to) {
        log.debug("updateStatusForNewOrdersWithTraditionalTransferPayment start for from: " + from + " to: " + to);
        List<Map<String, Object>> rows = ordersJdbcRepository.findOrders(Const.ORDER_STATUS_NEW, Const.PAYMENT_METHOD_TRADITIONAL_TRANSFER, from, to, applicationConfig.getSchedulerUpdateOrderStatusTraditionalLimit());
        for (Map row : rows) {
            updateStatusForOrdersWithTraditionalTransferPayment(Long.valueOf(String.valueOf(row.get("order_id"))));
        }
        log.debug("updateStatusForNewOrdersWithTraditionalTransferPayment stop");
    }

    public Long getCustomersLastOrderId(Long customerId) {
        return ordersJdbcRepository.getCustomersLastOrderId(customerId);
    }

    void setDeliveriesAsDelivered() {
        List<OrderDeliveryOrderData> deliveries = deliveryService.findUndelivered();
        log.debug("Undelivered procecessing started");
        for (OrderDeliveryOrderData delivery : deliveries) {
            log.debug("after findUndelivered delivery.getId(): " + delivery.getId());
            setDeliveryAsDeliveredForCron(delivery.getId(), delivery.getOrderId());
        }
        log.debug("Undelivered procecessing finished");
        log.debug("DeliveryToDeliveredTwoDays processing started");
        deliveries = deliveryService.findDeliveryToDeliveredTwoDays();
        for (OrderDeliveryOrderData delivery : deliveries) {
            log.debug("after findDeliveryToDeliveredTwoDays delivery.getId(): " + delivery.getId());
            setDeliveryAsDeliveredForCron(delivery.getId(), delivery.getOrderId());
        }
        log.debug("DeliveryToDeliveredTwoDays processing finished");
    }

    void setDeliveryAsDeliveredForCron(Long deliveryId, Long orderId) {
        log.debug("setDeliveryAsDeliveredForCron deliveryId: " + deliveryId + " orderId: " + orderId);
        deliveryService.setDeliveryAsDelivered(deliveryId);
        OrderDeliveryOrderData deliveryOrder = deliveryService.getDeliveryOrder(deliveryId);
        OrderDetailsData order = getOrder(orderId);
        if (order.getNewCalendar()) {
            log.debug("setDeliveryAsDeliveredForCron for new calendar deliveryOrder.getDeliveryDate(): " + deliveryOrder.getOriginalDeliveryDate() + " deliveryOrder.getOrderProductId(): " + deliveryOrder.getOrderProductId() + " orderId: " + orderId);
            ordersJdbcRepository.setOrderDayAsDeliveredForOrderProductForDate(deliveryOrder.getOrderProductId(), deliveryOrder.getOriginalDeliveryDate(), orderId);
        }
    }

    private void createOrderDay(LocalDate date, Long orderId, Long orderProductId, String status) {
        ordersJdbcRepository.createOrderDay(new OrderDaysData(date, orderProductId, orderId, status));
    }

    private void updateStatusForOrdersWithCashPayment(Long orderId) {
        log.debug("updateStatusForOrdersWithCashPayment start for order id: " + orderId);
        OrderDetailsData order = getOrder(orderId);
        if (Const.ORDER_STATUS_NEW != order.getStatusId().longValue() || Const.PAYMENT_METHOD_CASH != order.getPaymentMethodId().longValue()) {
            log.debug("updateStatusForOrdersWithCashPayment order rejected wrong status or paymentMethod id: " + orderId);
        }
        update(order.toOrderUpdateData(Const.ORDER_STATUS_APPROVED, Const.ORDER_CHANGE_STATUS_SOURCE_SCHEDULER_FOR_PAYMENT_BY_CASH));
        log.debug("updateStatusForOrdersWithCashPayment stop for order id: " + orderId);
    }

    private void updateStatusForOrdersWithTraditionalTransferPayment(Long orderId) {
        log.debug("updateStatusForOrdersWithTraditionalTransferPayment start for order id: " + orderId);
        OrderDetailsData order = getOrder(orderId);
        if (Const.ORDER_STATUS_NEW != order.getStatusId().longValue() || Const.PAYMENT_METHOD_TRADITIONAL_TRANSFER != order.getPaymentMethodId().longValue()) {
            log.debug("updateStatusForOrdersWithTraditionalTransferPayment order rejected wrong status or paymentMethod id: " + orderId);
        }
        update(order.toOrderUpdateData(Const.ORDER_STATUS_APPROVED, Const.ORDER_CHANGE_STATUS_SOURCE_SCHEDULER_FOR_TRADITIONAL_TRANSFER));
        log.debug("updateStatusForOrdersWithTraditionalTransferPayment stop for order id: " + orderId);
    }
}
