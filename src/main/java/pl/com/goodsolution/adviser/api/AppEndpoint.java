package pl.com.goodsolution.adviser.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.InputStreamResource;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.com.goodsolution.adviser.domain.*;
import pl.com.goodsolution.adviser.logic.*;
import pl.com.goodsolution.adviser.logic.dietetics.DieteticsService;

import java.io.ByteArrayInputStream;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static pl.com.goodsolution.adviser.logic.DictionaryType.*;

@RestController
public class AppEndpoint {
    private static final Logger log = LoggerFactory.getLogger(AppEndpoint.class);
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-LL-dd HH:mm:ss");
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-LL-dd");

    private static final DateTimeFormatter TIME_FORMATTER_SHORT = DateTimeFormatter.ofPattern("H:mm");

    private OrdersService ordersService;
    private CustomersService customersService;
    private StatisticsService statisticsService;
    private ProductsService productsService;
    private DictionariesService dictionariesService; //TODO remove from controller
    private DeliveryService deliveryService;
    private GalleryService galleryService;
    private DieteticsService dieteticsService;

    public AppEndpoint(OrdersService ordersService, CustomersService customersService, StatisticsService statisticsService, ProductsService productsService, DictionariesService dictionariesService, DeliveryService deliveryService, GalleryService galleryService, DieteticsService dieteticsService) {
        this.ordersService = ordersService;
        this.customersService = customersService;
        this.statisticsService = statisticsService;
        this.productsService = productsService;
        this.dictionariesService = dictionariesService;
        this.deliveryService = deliveryService;
        this.galleryService = galleryService;
        this.dieteticsService = dieteticsService;
    }


    @GetMapping(path = "/api/orders/{id}", produces = "application/json; charset=UTF-8")
    public OrderGetResponse getOrder(@PathVariable Long id) {
        log.trace("Getting order details for order: {}", id);
        return new OrderGetResponse(ordersService.getOrder(id));
    }

    @GetMapping(path = "/api/orders", produces = "application/json; charset=UTF-8")
    public OrdersResultGetResponse findOrders(
            @RequestParam(value = "first_and_last_name", required = false) String firstAndLastName,
            @RequestParam(value = "date_purchased_from", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate purchasedDateFrom,
            @RequestParam(value = "date_purchased_to", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate purchasedDateTo,
            @RequestParam(value = "payment_status_id", required = false) Long paymentStatusId,
            @RequestParam(value = "order_id", required = false) String orderId,

            @RequestParam(value = "discount_code", required = false) String discountCode,
            @RequestParam(value = "payment_method_id", required = false) Long paymentMethodId,
            @RequestParam(value = "driver_id", required = false) Long driverId,
            @RequestParam(value = "is_invoice", required = false) Boolean isInvoice,

            @RequestParam(value = "date_delivery_from", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate deliveryDateFrom,
            @RequestParam(value = "date_delivery_to", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate deliveryDateTo,
            @RequestParam(value = "delivery_method_id", required = false) Long deliveryMethodId,
            @RequestParam(value = "diet_id", required = false) Long dietId,
            @RequestParam(value = "order_status_id", required = false) Long orderStatusId,
            @RequestParam(value = "days_to_finish", required = false) Long daysToFinish,
            @RequestParam(value = "city", required = false) String city,
            @RequestParam(name = "date_marked_as_paid_from", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate markedAsPaidDateFrom,
            @RequestParam(name = "date_marked_as_paid_to", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate markedAsPaidDateTo,
            @RequestParam(value = "phone", required = false) String phone,
            @RequestParam(name = "page", required = false, defaultValue = "1") Long page,
            @RequestParam(name = "page_size", required = false, defaultValue = "100") Long pageSize
    ) {
        OrdersFilter filter = prepareOrdersFilter(
                firstAndLastName,
                purchasedDateFrom,
                purchasedDateTo,
                paymentStatusId,
                orderId,
                discountCode,
                paymentMethodId,
                driverId,
                isInvoice,
                deliveryDateFrom,
                deliveryDateTo,
                deliveryMethodId,
                dietId,
                orderStatusId,
                daysToFinish,
                city,
                markedAsPaidDateFrom,
                markedAsPaidDateTo,
                phone,
                page,
                pageSize
        );

        OrderResultData result = ordersService.findOrdersAll(filter, page, pageSize);
        return allOrdersToResponse(result);
    }

    private OrdersResultGetResponse allOrdersToResponse(OrderResultData result) {
        List<OrdersGetResponse> list = new ArrayList<>();
        for (OrderData order : result.getOrders()) {
            // TODO not all with strings, move dicts to service, check paymentstatus id = 0
            list.add(new OrdersGetResponse(
                    order.getId(),
                    order.getNo(),
                    order.getOrderId(),
                    order.getFirstName(),
                    order.getLastName(),
                    order.getPurchaseDate().toString(), //purchaseDateTime
                    order.getValue().toString(), //value
                    "PLN", //currency
                    order.getDeliveryMethodId(), //
                    dictionariesService.getDictionaryValueById(order.getDeliveryMethodId(), SHIPMENT_TYPES, Language.PL),
                    order.getPaymentMethodId(),
                    dictionariesService.getDictionaryValueById(order.getPaymentMethodId(), ORDER_PAYMENT_METHODS, Language.PL),
                    order.getPaymentStatusId(),
                    order.getPaymentStatusId() != 0 ? dictionariesService.getDictionaryValueById(order.getPaymentStatusId(), PAYMENT_STATUSES, Language.PL) : "",
                    order.getPaymentStatusCode(),
                    order.getDaysLeft(),
                    order.getNextOrderIndicator(),
                    order.getOrderStatusId(),
                    dictionariesService.getDictionaryValueById(order.getOrderStatusId(), ORDER_STATUSES, Language.PL),
                    order.getOrderStatusCode(),
                    order.getDiscountName(),
                    order.getInvoiceWanted(),
                    order.getInvoiceIssued(),
                    order.getReceipt(),
                    order.getDemandingCustomer(),
                    order.getCustomerId(), order.getPaymentStyle(), order.getDaysLeftStyle(), order.getOrderStatusStyle(), order.getStatusChangeSource()));
        }

        return new OrdersResultGetResponse(result.getSum(), result.getCount(), list);
    }

    private OrdersFilter prepareOrdersFilter(String firstAndLastName,
                                             LocalDate purchasedDateFrom,
                                             LocalDate purchasedDateTo,
                                             Long paymentStatusId,
                                             String orderId,

                                             String discountCode,
                                             Long paymentMethodId,
                                             Long driverId,
                                             Boolean isInvoice,

                                             LocalDate deliveryDateFrom,
                                             LocalDate deliveryDateTo,
                                             Long deliveryMethodId,
                                             Long dietId,
                                             Long orderStatusId,
                                             Long daysToFinish,
                                             String city,
                                             LocalDate markedAsPaidDateFrom,
                                             LocalDate markedAsPaidDateTo,
                                             String phone,
                                             Long page,
                                             Long pageSize) {
        return new OrdersFilter(
                purchasedDateFrom, //LocalDate orderDateFrom,
                purchasedDateTo, // LocalDate orderDateTo,
                firstAndLastName, // String firstNameAndLastName,
                deliveryDateFrom, // LocalDate deliveryDateFrom,
                deliveryDateTo, // LocalDate deliveryDateTo,
                paymentStatusId, // Long paymentStatusId,
                discountCode, // String discountCode,
                paymentMethodId, // Long paymentMethodId,
                driverId, // Long driverId,
                deliveryMethodId, // Long deliveryMethodId,
                dietId, // Long dietId,
                orderStatusId, // Long orderStatusId,
                daysToFinish, // Long ordersFinishingInDays,
                city,// String city,
                markedAsPaidDateFrom,// LocalDate markedAsPaidDateFrom,
                markedAsPaidDateTo,// LocalDate markedAsPaidDateTo,
                isInvoice,// Boolean invoice
                orderId,
                phone
        );

    }

    @GetMapping(path = "/api/orders/ordersCount", produces = "application/json; charset=UTF-8")
    public OrderCountGetResponse countOrders() {
        return new OrderCountGetResponse(1234L);
    }

    @PostMapping(path = "/api/orders", consumes = "application/json; charset=UTF-8", produces = "application/json; charset=UTF-8")
    public OrderPostResponse createOrder(@RequestBody OrderPostRequest req) {
        log.trace("Create new order with the following data: ");
        log.trace("req: " + req);


        Map<Long, List<Long>> extrasMap = new HashMap<>();
        for (long i = 0; i < req.getProducts().getDietId().length; i++) {
            extrasMap.put(i, new ArrayList<>());
        }
        long prd = -1;
        for (int i = 0; i < req.getProducts().getExtras().length; i++) {
            String[] parts = req.getProducts().getExtras()[i].split(";");
            log.debug("parts[0]: " + parts[0] + " parts[1]: " + parts[1] + " parts[2]: " + parts[2]);
            if (parts[0].equals("ex_0")) {
                prd++;
            }
            if ("true".equals(parts[2])) {
                extrasMap.get(prd).add(Long.valueOf(parts[1]));
            }
        }

        List<OrderProductCreateData> products = new ArrayList<>();
        for (int i = 0; i < req.getProducts().getDietId().length; i++) {
            Set<Long> extras = new HashSet<>(extrasMap.get(Long.valueOf(i)));
            OrderProductCreateData product = new OrderProductCreateData(
                    null, req.getProducts().getDietId()[i], req.getProducts().getDietTypeId()[i], req.getProducts().getQuantity()[i], req.getProducts().getStartDate()[i], req.getProducts().getDays()[i], req.getProducts().getWeekendOptionId()[i], req.getProducts().getTestDay()[i], null, extras
            );
            products.add(product);
        }
        if (req.getPurchaseDateTime() == null) {
            throw new IllegalArgumentException("Uzupełnij datę sprzedaży");//TODO move to service
        }

        OrderAddData orderAddData = new OrderAddData(req.getCustomerId(), LocalDateTime.of(req.getPurchaseDateTime(), req.getPurchaseTime() == null ? LocalTime.of(0, 0, 0) : req.getPurchaseTime()), req.getPaymentMethodId(), req.getStatus(), req.getSendEmail(), req.getNewOrderGroup(), req.getDriverExclude(), req.getDriverId(), req.getComments(), req.getDeliveryMethodId(), products, req.getDiscountCode());
        Long id = ordersService.addOrder(orderAddData);
        return new OrderPostResponse(id);
    }

    @PutMapping(path = "/api/orders/{orderId}/change/{what}/value/{value}", consumes = "application/json; charset=UTF-8", produces = "application/json; charset=UTF-8")
    public void changeOrder(@PathVariable Long orderId, @PathVariable String what, @PathVariable String value) {
        log.debug("Should change " + what + " for order id: " + orderId + " value: " + value);
        ordersService.changeOrder(orderId, what, value);
    }

    @PutMapping(path = "/api/orders/{id}/demanding_customer/{customerId}/value/{state}")
    public void updateOrderDemandingCustomer(@PathVariable Long id, @PathVariable Long customerId, @PathVariable Boolean state) {
        log.debug("id: " + id + " state: " + state + " customerId: " + customerId);
        customersService.updateCustomerDemanding(customerId, state);
        ordersService.updateDemandingCustomer(id, customerId, state);
    }

    @PutMapping(path = "/api/orders/{id}/stop_diet/{date}")
    public void orderStopDiet(@PathVariable Long id, @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        log.debug("id: " + id + " date: " + date);
        ordersService.updateOrderStatus(id, 7L, date);
    }

    @PutMapping(path = "/api/orders/{id}/start_diet/{date}")
    public void orderStartDiet(@PathVariable Long id, @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        log.debug("id: " + id + " date: " + date);
        ordersService.updateOrderStatus(id, 5L, date);
    }

    @PutMapping(path = "/api/orders/{orderId}", consumes = "application/json; charset=UTF-8", produces = "application/json; charset=UTF-8")
    public void updateOrder(@PathVariable Long orderId,
                            @RequestBody OrderPutRequest req) {


        log.debug(req.toString());
        ordersService.update(new OrderUpdateData(
                orderId,
                req.getDeliveryMethodId(),
                req.getPaymentMethodId(),
                req.getPaymentStatusId(),
                req.getOrderStatusId(),
                req.getDemandingCustomer(),
                req.getReceipt(),
                req.getInvoiceIssuedStatus(), //TODO: issued or wanted?
                req.getEmail(),
                req.getDriverExclude(),
                req.getComments(),
                req.getAdminComments(),
                req.getPaymentByCard(),

                req.getCustomerFirstName(),
                req.getCustomerLastName(),
                req.getCustomerPhone(),
                req.getCustomerStreet(),
                req.getCustomerBuildingNumber(),
                req.getCustomerApartmentNumber(),
                req.getCustomerPostalCode(),
                req.getCustomerCityId(),

                req.getWeekendAddress(),
                req.getWeekendStreet(),
                req.getWeekendBuildingNumber(),
                req.getWeekendApartmentNumber(),
                req.getWeekendPostalCode(),
                req.getWeekendCityId(),

                req.getInvoiceWanted(),
                req.getInvoiceCompanyName(),
                req.getInvoiceNip(),
                req.getInvoiceStreet(),
                req.getInvoiceBuildingNumber(),
                req.getInvoiceApartmentNumber(),
                req.getInvoicePostalCode(),
                req.getInvoiceCityName(),

                req.getHoursTo(),
                req.getWeekendHours(),
                req.getWeekendHoursTo(),
                req.getStartDate(),
                req.getStopDate(),
                req.getCancelReason(),
                req.getGroupOrders(),
                null)
        );
    }

    private String getTimeWithoutLeadingZeros(LocalTime timeWithLeadingZeros) {
        if (timeWithLeadingZeros == null) {
            return "";
        }
        return timeWithLeadingZeros.format(DateTimeFormatter.ofPattern("H:mm"));
    }

    // from ewidencja zamówień - the usuń button
    @DeleteMapping(path = "/api/orders/{orderId}")
    public void deleteOrder(@PathVariable Long orderId) {
        System.out.println("delete the specified order (" + orderId + ").");
        ordersService.remove(orderId);
    }


    // from order details
    @PutMapping(path = "/api/orders/{orderId}/discountCode/{discountCode}")
    public void updateOrderDiscountCode(@PathVariable Long orderId, @PathVariable String discountCode) {
        System.out.println("update the discount for the order");
        if ("adr34r4fdscccc4rfff5w54gwwygwgbfdbst544tgstgst4".equals(discountCode)) {
            discountCode = "";
        }
        ordersService.updateDiscountCode(orderId, discountCode);
    }


    @GetMapping(path = "/api/orders/{orderId}/payments", produces = "application/json; charset=UTF-8")
    public OrderPaymentResultGetResponse getAllOrderPayments(@PathVariable Long orderId) {
        OrderDetailsData data = ordersService.getOrder(orderId);
        OrderPaymentResultData result = ordersService.getPaymentResultForOrder(orderId, data.getOrderBasketSum());
        return allOrdersPaymentsToResponse(result);
    }

    private OrderPaymentResultGetResponse allOrdersPaymentsToResponse(OrderPaymentResultData result) {
        List<OrderPaymentGetResponse> list = new ArrayList<>();
        for (OrderPaymentData payments : result.getPayments()) {
            // TODO not all with strings, move dicts to service, check paymentstatus id = 0
            list.add(new OrderPaymentGetResponse(
                    payments.getOrderId(),
                    payments.getPaymentNo(),
                    payments.getPaymentId(),
                    payments.getDateTime().format(DATE_TIME_FORMATTER),
                    payments.getValue(),
                    payments.getPaymentMethodName(),
                    payments.getType()
            ));
        }
        return new OrderPaymentResultGetResponse(list, result.getSum(), result.getToPayLeft(), result.getRefund());
    }

    // from order details (SECTION: Płatności)
    @PostMapping(path = "/api/orders/{orderId}/payments")
    public void createOrderPayment(@PathVariable Long orderId,
                                   @RequestBody OrderPaymentPostRequest request) {
        ordersService.addPayment(new OrderPaymentCreateData(orderId, request.getAmount(), request.getPaymentMethodId()));
    }

    // from order details (SECTION: Płatności)
    @DeleteMapping(path = "/api/orders/{orderId}/payments/{paymentId}")
    public void deleteOrderPayment(@PathVariable Long orderId,
                                   @PathVariable Long paymentId) {
        ordersService.removePayment(new OrderPaymentRemoveData(orderId, paymentId));
    }


    //: from order details (SECTION: produkty w zamówieniu)
    @GetMapping(path = "/api/orders/{orderId}/products", produces = "application/json; charset=UTF-8")
    public OrderProductResultGetResponse getAllOrderProducts(
            @PathVariable Long orderId
    ) {
        OrderDetailsData data = ordersService.getOrder(orderId);
        OrderProductResultData result = ordersService.getProductResultForOrder(orderId, data.getOrderBasketSumNo(), data.getOrderBasketSum());
        return allOrdersProductsToResponse(result);
    }

    @GetMapping(path = "/api/orders/{orderId}/products/{productId}", produces = "application/json; charset=UTF-8")
    public OrderProductGetResponse getOrderProduct(
            @PathVariable Long orderId,
            @PathVariable Long productId

    ) {
        OrderDetailsData data = ordersService.getOrder(orderId);
        OrderProductResultData result = ordersService.getProductResultForOrder(orderId, data.getOrderBasketSumNo(), data.getOrderBasketSum());

        OrderProductData product = result.getProducts().stream().filter(o -> o.getId().equals(productId)).findFirst().get();
        List<ExtrasData> extrasList = product.getExtrasList();
        List<OrderExtrasGetResponse> extras = new ArrayList<>();
        for (ExtrasData ext : extrasList) {
            extras.add(new OrderExtrasGetResponse(ext.getId()));
        }
        return new OrderProductGetResponse(
                product.getId(), //id (productId)
                product.getDietName(),
                product.getDietId(),
                product.getTypeName(),
                product.getQuantity(),
                product.getDateFrom() != null ? product.getDateFrom().format(DATE_FORMATTER) : "",
                product.getDays(),
                product.getExtras(),
                product.getNetPrice(),
                product.getVatPercent(),
                product.getGrossPrice(),
                product.getPromotion(),
                product.getGrossValue(),
                product.getWeekendsIndicator(),
                product.getTestIndicatorName(),
                product.getTestIndicatorCode(),
                product.getProductId(),
                product.getProductStopped(),
                product.getWeekendOptionId(),
                extras);
    }


    @GetMapping(path = "/api/orders/{orderId}/deliveries_released", produces = "application/json; charset=UTF-8")
    public OrderReleasedDeliveriesGetResponse getAllOrderReleasedDeliveries(
            @PathVariable Long orderId
    ) {
        log.debug("Should return all released deliveries for orderId: " + orderId);
        OrderDetailsData data = ordersService.getOrder(orderId);
        List<OrderDeliveryData> list = deliveryService.findReleasedDeliveriesForOrder(orderId, data);
        return prepareOrderReleasedDeliveriesGetResponse(list);
    }

    private OrderReleasedDeliveriesGetResponse prepareOrderReleasedDeliveriesGetResponse(List<OrderDeliveryData> list) {
        List<OrderReleasedDeliveryGetResponse> deliveries = new ArrayList<>();
        for (OrderDeliveryData delivery : list) {
            deliveries.add(new OrderReleasedDeliveryGetResponse(delivery.getId(), delivery.getNo(), delivery.getProductName(), delivery.getDriver(), delivery.getHourFrom() == null ? null : delivery.getHourFrom().toString(), delivery.getHourTo() == null ? null : delivery.getHourTo().toString()));
        }
        return new OrderReleasedDeliveriesGetResponse(deliveries);
    }

    private OrderProductResultGetResponse allOrdersProductsToResponse(OrderProductResultData result) {
        List<OrderProductGetResponse> list = new ArrayList<>();
        for (OrderProductData product : result.getProducts()) {
            // TODO not all with strings, move dicts to service, check paymentstatus id = 0
            list.add(new OrderProductGetResponse(
                    product.getId(), //id (productId)
                    product.getDietName(),
                    product.getDietId(),
                    product.getTypeName(),
                    product.getQuantity(),
                    product.getDateFrom() != null ? product.getDateFrom().format(DATE_FORMATTER) : "",
                    product.getDays(),
                    product.getExtras(),
                    product.getNetPrice(),
                    product.getVatPercent(),
                    product.getGrossPrice(),
                    product.getPromotion(),
                    product.getGrossValue(),
                    product.getWeekendsIndicator(),
                    product.getTestIndicatorName(),
                    product.getTestIndicatorCode(),
                    product.getProductId(),
                    product.getProductStopped(),
                    product.getWeekendOptionId(),
                    null)
            );
        }
        return new OrderProductResultGetResponse(list, result.getPriceBeforeDiscount(), result.getPriceAfterDiscount());
    }

    // from order details (SECTION: produkty w zamówieniu)
    @PostMapping(path = "/api/orders/{orderId}/products", consumes = "application/json; charset=UTF-8")
    public void createOrderProduct(@PathVariable Long orderId,
                                   @RequestBody OrderProductPostRequest request) {
        log.trace(request.toString());
        ordersService.addProduct(new OrderProductCreateData(orderId, request.getDietId(), request.getDietTypeId(), request.getQuantity(), request.getDateFrom(), request.getDays(), request.getWeekendOptionId(), request.getTestDay(), request.getExtrasOne(), request.getExtras()));
    }

    // from order details (SECTION: produkty w zamówieniu)
    @PutMapping(path = "/api/orders/{orderId}/products/{productId}", consumes = "application/json; charset=UTF-8")
    public void updateOrderProduct(@PathVariable Long orderId,
                                   @PathVariable Long productId,
                                   @RequestBody OrderProductPutRequest request) {
        log.debug(request.toString());
        ordersService.updateProduct(new OrderProductUpdateData(orderId, productId, request.getDietId(), request.getTypeId(), request.getQuantity(), request.getDays(), request.getWeekendOptionId(), request.getTestDay(), request.getExtrasOne(), request.getSuspensionDate(), request.getStartDate(), request.getExtras(), request.getChangePrice(), request.getChangePriceValue()));
    }

    // from order details (SECTION: produkty w zamówieniu)
    @DeleteMapping(path = "/api/orders/{orderId}/products/{productId}")
    public void deleteOrderProduct(@PathVariable Long orderId, @PathVariable Long productId) {
        ordersService.removeProduct(orderId, productId);
    }


    //TODO: from order details (SECTION: Informacje o dostawach)
    @GetMapping(path = "/api/orders/{orderId}/deliveries/{deliveryId}", produces = "application/json; charset=UTF-8")
    public OrderDeliveryGetResponse getOrderDelivery(
            @PathVariable Long orderId,
            @PathVariable Long deliveryId
    ) {
        System.out.println("Should return delivery data for delivery " + deliveryId + " for order " + orderId + ".");
        OrderDeliveryData data = ordersService.getDeliveryData(orderId, deliveryId);

        return new OrderDeliveryGetResponse(
                data.getId(),
                data.getNo(), 1L,
                "Miasto testowe",
                "Testowa",
                "32323",
                "000AA",
                "00-000",
                data.getAddress(),
                data.getDeliveredDate() == null ? "" : data.getDeliveredDate().format(DATE_TIME_FORMATTER), // remove in future
                data.getHourFrom().toString(),
                data.getHourTo().toString(),
                data.getProductName(),
                data.getDriver(),
                data.getDate().format(DATE_FORMATTER),
                data.getNumberOfPhotos(),
                null, null, data.getWeekend(), data.getDeliveryDate().format(DATE_FORMATTER));
    }

    @GetMapping(path = "/api/orders/{orderId}/deliveries", produces = "application/json; charset=UTF-8")
    public OrderDeliveryResultGetResponse findOrderDeliveries(
            @PathVariable Long orderId
    ) {

        OrderDetailsData order = ordersService.getOrder(orderId);
        List<OrderDeliveryData> deliveries = ordersService.getDeliveryDataForOrder(orderId, order);
        List<OrderDeliveryGetResponse> list = new ArrayList<>();
        for (OrderDeliveryData delivery : deliveries) {
            list.add(new OrderDeliveryGetResponse(delivery.getId(), delivery.getNo(), delivery.getCityId(), null, null, null, null, null, delivery.getAddress(), delivery.getDeliveredDate() == null ? "" : delivery.getDeliveredDate().format(DATE_TIME_FORMATTER), delivery.getHourFrom().toString(),
                    delivery.getHourTo() != null ? delivery.getHourTo().toString() : null, delivery.getProductName(), delivery.getDriver(), delivery.getDate().format(DATE_FORMATTER), delivery.getNumberOfPhotos(), delivery.getDeliveredDate() == null ? "" : delivery.getDeliveredDate().format(DATE_TIME_FORMATTER), delivery.getStatus(), delivery.getWeekend(), delivery.getDeliveryDate() == null ? "" : delivery.getDeliveryDate().format(DATE_FORMATTER)));
        }
        return new OrderDeliveryResultGetResponse(list);
    }

    //TODO: from order details (SECTION: Informacje o dostawach)
    @PutMapping(path = "/api/orders/{orderId}/deliveries/{deliveryId}", consumes = "application/json; charset=UTF-8")
    public void updateOrderDelivery(
            @PathVariable Long orderId,
            @PathVariable Long deliveryId,
            @RequestBody OrderDeliveryPutRequest request, @RequestHeader(value = "User-Agent") String userAgent) {
        System.out.println("Should change the delivery properties for delivery " + deliveryId + " for order " + orderId + ".");
        System.out.println("New properties:");
        System.out.println("cityId: " + request.getCityId());
        System.out.println("street: " + request.getStreet());
        System.out.println("building: " + request.getBuildingNumber());
        System.out.println("apartment: " + request.getApartmentNumber());
        System.out.println("postal code: " + request.getPostalCode());
        System.out.println("hourFrom: " + request.getHourFrom());
        System.out.println("hourTo: " + request.getHourTo());
        //TODO: on getting complete=true, the service should get the current dateTime as completionDateTime
        System.out.println("complete: " + request.getComplete());
        System.out.println("suspensionDate: " + request.getSuspensionDate());
        System.out.println("getStartDate: " + request.getStartDate());
        ordersService.updateDelivery(new OrderDeliveryUpdateData(orderId, deliveryId, request.getCityId(), request.getStreet(), request.getBuildingNumber(), request.getApartmentNumber(), request.getPostalCode(), request.getSuspensionDate(), request.getComplete(), request.getHourFrom(), request.getHourTo(), request.getStartDate(), userAgent));
    }


    @GetMapping(path = "/api/orders/{orderId}/delivery_changes", produces = "application/json; charset=UTF-8")
    public List<OrderDeliveryChangeGetResponse> getOrderDeliveryChanges(
            @PathVariable Long orderId
    ) {
        List<OrderDeliveryChangeData> result = ordersService.getDeliveryChangesForOrder(orderId);
        return allOrderDeliveryChangesToResponse(result);
    }

    @GetMapping(path = "/api/orders/{orderId}/delivery_changes/{deliveryChangeId}", produces = "application/json; charset=UTF-8")
    public OrderDeliveryChangeGetResponse getOrderDeliveryChange(
            @PathVariable Long orderId,
            @PathVariable Long deliveryChangeId
    ) {
        List<OrderDeliveryChangeData> result = ordersService.getDeliveryChangesForOrder(orderId);
        OrderDeliveryChangeData data = result.stream().filter(o -> o.getId().equals(deliveryChangeId)).findFirst().get();
        return new OrderDeliveryChangeGetResponse(data.getId(), data.getNo(), data.getType(),
                data.getBefore() != null ? data.getBefore() : "",
                data.getAfter() != null ? data.getAfter() : "", data.getProductName(),
                data.getUserName(), data.getDateTime().format(DATE_TIME_FORMATTER));
    }

    private List<OrderDeliveryChangeGetResponse> allOrderDeliveryChangesToResponse(List<OrderDeliveryChangeData> result) {
        List<OrderDeliveryChangeGetResponse> list = new ArrayList<>();
        for (OrderDeliveryChangeData deliveryChange : result) {
            list.add(new OrderDeliveryChangeGetResponse(
                    deliveryChange.getId(),
                    deliveryChange.getNo(),
                    deliveryChange.getType(),
                    deliveryChange.getBefore() != null ? deliveryChange.getBefore() : "",
                    deliveryChange.getAfter() != null ? deliveryChange.getAfter() : "",
                    deliveryChange.getProductName(),
                    deliveryChange.getUserName(),
                    deliveryChange.getDateTime().format(DATE_TIME_FORMATTER)
            ));
        }
        return list;
    }


    @GetMapping(path = "/api/product/demand", produces = "application/json; charset=UTF-8")
    public List<DemandsGetResponse> findProductDemands(
            @RequestParam(value = "date", required = false) String date
    ) {
        return Arrays.asList(new DemandsGetResponse(
                1L));
    }

    @GetMapping(path = "/api/product/demand/pdf/{date}", produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<InputStreamResource> getProductDemandsPdf(@PathVariable(name = "date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        ByteArrayInputStream bis = productsService.getProductDemandsPdf(date, 1L, 1000000L);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "inline; filename=request_" + date + ".pdf");

        return ResponseEntity
                .ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_PDF)
                .body(new InputStreamResource(bis));
    }


    @GetMapping(path = "/api/customer/{id}", produces = "application/json; charset=UTF-8")
    public CustomerGetResponse getCustomer(@PathVariable Long id) {
        return new CustomerGetResponse(customersService.getCustomer(id));
    }

    @GetMapping(path = "/api/customer/substring/{substring}", produces = "application/json; charset=UTF-8")
    public List<CustomerGetResponse> getCustomersBySubstring(@PathVariable String substring) {
        List<DictionaryData> dictionaryCustomers = dictionariesService.getDictionaryCustomers(substring);
        List<CustomerGetResponse> list = new ArrayList<>();
        for (DictionaryData data : dictionaryCustomers) {
            list.add(new CustomerGetResponse(data.getId(), data.getValue(), data.getExtraString()));
        }
        return list;
    }

    @GetMapping(path = "/api/customer/group/{id}", produces = "application/json; charset=UTF-8")
    public CustomerGroupGetResponse getCustomerGroup(@PathVariable Long id) {
        return new CustomerGroupGetResponse(1L);
    }


    @GetMapping(path = "/api/user/{id}", produces = "application/json; charset=UTF-8")
    public UserGetResponse getUser(@PathVariable Long id) {
        return new UserGetResponse(1L);
    }


    @GetMapping(path = "/api/statistics", produces = "application/json; charset=UTF-8")
    public StatisticsResultGetResponse getStatistics(
            @RequestParam(name = "date_from", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateFrom,
            @RequestParam(name = "date_to", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateTo,
            @RequestParam(name = "drivers", required = false) Long drivers,
            @RequestParam(name = "diet_id", required = false) Long[] dietId,
            @RequestParam(name = "payment_method", required = false) String paymentMethod,
            @RequestParam(name = "payment_status", required = false) Long paymentStatus,
            @RequestParam(name = "shipment_type", required = false) Long shipmentType
    ) {
        return statisticsService.getStatisticsGetResponse(dateFrom, dateTo, drivers, dietId, paymentMethod, paymentStatus, shipmentType);
    }

    @GetMapping(path = "/api/gallery", produces = "application/json; charset=UTF-8")
    public List<ImageResponse> findImages(
            @RequestParam(name = "diet_id", required = false) Long[] dietId,
            @RequestParam(name = "page", required = false, defaultValue = "1") Long page,
            @RequestParam(name = "page_size", required = false, defaultValue = "100") Long pageSize
    ) {
        List<ImageData> images = galleryService.findImages(dietId, "A", page, pageSize);
        List<ImageResponse> list = new ArrayList<>();
        for (ImageData image : images) {
            list.add(new ImageResponse(image.getId(), image.getFileName(), image.getPath()));
        }
        return list;
    }

    @DeleteMapping(path = "/api/gallery/image/{id}", produces = "application/json; charset=UTF-8")
    public void deleteImage(
            @PathVariable(name = "id") Long id
    ) {
        galleryService.deleteImage(id);
    }

    @PutMapping(path = "/api/gallery/image/{id}", produces = "application/json; charset=UTF-8")
    public void modifyImage(
            @PathVariable(name = "id") Long id,
            @RequestParam(name = "dietIds", required = false) Long[] dietIds,
            @RequestParam(name = "fileName") String fileName,
            @RequestParam(name = "kind") String kind
    ) {
        galleryService.modifyImage(new ImageData(fileName, id, dietIds, GalleryImageKind.toGalleryImageKind(kind)));
    }

    @PostMapping(path = "/api/gallery/image", produces = "application/json; charset=UTF-8")
    public void addImage(
            @RequestParam(name = "dietIds", required = false) Long[] dietIds,
            @RequestParam(name = "fileName") String fileName,
            @RequestParam(name = "kind") String kind
    ) {
        galleryService.addImage(new ImageData(fileName, dietIds, GalleryImageKind.toGalleryImageKind(kind)));
    }

    @GetMapping(path = "/api/customers-new", produces = "application/json; charset=UTF-8")
    public CustomerNewResultGetResponse getNewCustomers(
            @RequestParam(name = "date_from", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateFrom,
            @RequestParam(name = "date_to", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateTo,
            @RequestParam(name = "order_status_id", required = false) Long orderStatusId,
            @RequestParam(name = "diet_id", required = false) Long[] dietIds
    ) {
        return customersService.findNewCustomersAll(new CustomerNewFilter(dateFrom, dateTo, dietIds, orderStatusId));
    }

    @GetMapping(path = "/api/allergens", produces = "application/json; charset=UTF-8")
    public List<GetAllergenResponse> getAllergens() {

        return prepareAllergenGetResponses(dieteticsService.findAllergens());
    }

    @GetMapping(path = "/api/products-types", produces = "application/json; charset=UTF-8")
    public List<GetProductTypeResponse> getProductsTypes() {

        return prepareProductTypeGetResponses(dieteticsService.findProductsTypes());
    }

    @DeleteMapping(path = "/api/allergens/{id}")
    public void deleteAllergens(@PathVariable Long id) {
        dieteticsService.deleteAllergen(id);
    }

    @DeleteMapping(path = "/api/products-types/{id}")
    public void deleteProductType(@PathVariable Long id) {
        dieteticsService.deleteProductType(id);
    }

    @PostMapping(path = "/api/allergens", consumes = "application/json; charset=UTF-8", produces = "application/json; charset=UTF-8")
    public void createAllergen(@RequestBody AllergenPostRequest data) {
        dieteticsService.createAllergen(new AllergenData(data.getName()));
    }

    @PostMapping(path = "/api/products-types", consumes = "application/json; charset=UTF-8", produces = "application/json; charset=UTF-8")
    public void createProductsTypes(@RequestBody ProductTypePostRequest data) {
        dieteticsService.createProductType(new ProductTypeData(data.getType()));
    }

    @PutMapping(path = "/api/allergens/{id}", consumes = "application/json; charset=UTF-8", produces = "application/json; charset=UTF-8")
    public void updateAllergen(@RequestBody AllergenPutRequest data, @PathVariable Long id) {
        dieteticsService.updateAllergen(new AllergenData(id, data.getName()));
    }

    @PutMapping(path = "/api/products-types/{id}", consumes = "application/json; charset=UTF-8", produces = "application/json; charset=UTF-8")
    public void updateProductType(@RequestBody ProductTypePutRequest data, @PathVariable Long id) {
        dieteticsService.updateProductType(new ProductTypeData(id, data.getType()));
    }


    @GetMapping(path = "/api/orders/{orderId}/emails", produces = "application/json; charset=UTF-8")
    public OrderEmailResultGetResponse getOrderEmails(@PathVariable Long orderId) {
        List<OrderEmailData> orderSentEmails = ordersService.findOrderSentEmails(orderId);
        return new OrderEmailResultGetResponse(prepareOrderEmailGetResponses(orderSentEmails));
    }

    @GetMapping(path = "/api/orders/{orderId}/order_days", produces = "application/json; charset=UTF-8")
    public List<OrderDaysGetResponse> getOrderDays(@PathVariable Long orderId) {
        return prepareOrderDaysGerResponses(orderId, ordersService.findOrderDays(orderId));
    }

    @GetMapping(path = "/api/orders/{orderId}/order_days_history", produces = "application/json; charset=UTF-8")
    public List<OrderDaysGetResponse> getOrderDaysHistory(@PathVariable Long orderId) {
        return prepareOrderDaysGerResponses(orderId, ordersService.findOrderDaysHistory(orderId));
    }

    private List<OrderDaysGetResponse> prepareOrderDaysGerResponses(@PathVariable Long orderId, List<OrderDaysData> listOrderDays) {
        List<OrderDaysGetResponse> list = new ArrayList<>();
        for (OrderDaysData day : listOrderDays) {
            list.add(new OrderDaysGetResponse(day.getId(), day.getProduct(), day.getDate().format(DATE_FORMATTER), day.getStatus(), day.getStatusName(), day.getQuantity(), day.getDeliveryInfo()));
        }
        return list;
    }

    private List<OrderEmailGetResponse> prepareOrderEmailGetResponses(List<OrderEmailData> orderSentEmails) {
        List<OrderEmailGetResponse> list = new ArrayList<>();
        for (OrderEmailData email : orderSentEmails) {
            list.add(new OrderEmailGetResponse(email.getNo(), email.getId(), email.getTitle(), email.getSentBy(), email.getDateTime().format(DATE_TIME_FORMATTER), email.getOrderId(), email.getMessage()));
        }
        return list;
    }

    @GetMapping(path = "/api/orders/{orderId}/emails/{emailId}", produces = "application/json; charset=UTF-8")
    public OrderEmailGetResponse getOrderEmail(@PathVariable Long orderId, @PathVariable Long emailId) {
        List<OrderEmailData> orderSentEmails = ordersService.findOrderSentEmails(orderId);
        OrderEmailData email = orderSentEmails.stream().filter(o -> o.getId().equals(emailId)).findFirst().get(); //IMP
        return new OrderEmailGetResponse(email.getNo(), email.getId(), email.getTitle(), email.getSentBy(), email.getDateTime().format(DATE_TIME_FORMATTER), email.getOrderId(), email.getMessage());
    }

    @GetMapping(path = "/api/orders/{orderId}/changes", produces = "application/json; charset=UTF-8")
    public OrderChangeResultGetResponse getOrderChanges(@PathVariable Long orderId) {
        List<OrderChangeData> changesForOrder = ordersService.getChangesForOrder(orderId);
        List<OrderChangeGetResponse> list = new ArrayList<>();
        for (OrderChangeData change : changesForOrder) {
            list.add(new OrderChangeGetResponse(change.getNo(), change.getId(), change.getName(), change.getDateTime().format(DATE_TIME_FORMATTER)));
        }
        return new OrderChangeResultGetResponse(list);
    }

    @GetMapping(path = "/api/orders/{orderId}/changes/{changeId}", produces = "application/json; charset=UTF-8")
    public OrderChangeGetResponse getOrderChange(@PathVariable Long orderId,
                                                 @PathVariable Long changeId) {
        List<OrderChangeData> changesForOrder = ordersService.getChangesForOrder(orderId);
        OrderChangeData data = changesForOrder.stream().filter(o -> o.getId().equals(changeId)).findFirst().get();
        List<OrderChangeProductGetResponse> products = new ArrayList<>();
        for (OrderChangeProductData product : data.getProducts()) {
            products.add(new OrderChangeProductGetResponse(product.getProductName(), product.getQuantity(), product.getPrice(), product.getDateFrom() != null ? product.getDateFrom().format(DATE_FORMATTER) : "", product.getDaysLeft()));
        }
        return new OrderChangeGetResponse(data.getNo(), data.getId(), data.getName(), data.getDateTime().format(DATE_TIME_FORMATTER),
                data.getFirstName(), data.getLastName(), data.getPhoneNumber(), data.getStreet(), data.getBuildingNumber(), data.getApartmentNumber(), data.getPostalCode(),
                data.getCityName(), data.getHourFrom() == null ? null : data.getHourFrom().format(TIME_FORMATTER_SHORT), data.getHourTo() == null ? null : data.getHourTo().format(TIME_FORMATTER_SHORT),
                data.getDemandingCustomerIndicator(), data.getPaymentMethodName(), data.getCardPaymentIndicator(),
                data.getPaymentStatus(), data.getDeliveryMethodName(), products);
    }

    @GetMapping(path = "/api/dictionary/{name}", produces = "application/json; charset=UTF-8")
    public List<DictionaryData> getDictionary(@PathVariable String name) {
        return dictionariesService.getDictionary(DictionaryType.valueOf(name.toUpperCase()), Language.PL);
    }

    @PutMapping(path = "/api/orders/{orderId}/cancel_order", consumes = "application/json; charset=UTF-8")
    public void cancelOrder(@PathVariable Long orderId,
                            @RequestBody CancelOrderPutRequest req) {
        log.trace("Should cancel orderId: " + orderId + " with reason: " + req.getReason());
        ordersService.cancelOrderWithReason(orderId, req.getReason());
    }

    @PutMapping(path = "/api/orders/{id}/send_order_summary_email")
    public void sendOrderSummaryEmail(@PathVariable Long id) {
        log.debug("sendOrderSummaryEmail for orderId: " + id);
        ordersService.sendOrderSummaryEmail(id);
    }

    private List<GetAllergenResponse> prepareAllergenGetResponses(List<AllergenData> allergens) {
        List<GetAllergenResponse> list = new ArrayList<>();
        for (AllergenData allergen : allergens) {
            list.add(new GetAllergenResponse(allergen.getId(), allergen.getNo(), allergen.getName()));
        }
        return list;
    }

    private List<GetProductTypeResponse> prepareProductTypeGetResponses(List<ProductTypeData> products) {
        List<GetProductTypeResponse> list = new ArrayList<>();
        for (ProductTypeData product : products) {
            list.add(new GetProductTypeResponse(product.getId(), product.getNo() ,product.getType()));
        }
        return list;
    }


    @PostMapping(path = "/api/customer-groups", consumes = "application/json; charset=UTF-8", produces = "application/json; charset=UTF-8")
    public void createCustomerGroup(@RequestBody CustomerGroupPostRequest req) {
        log.trace("Create new customer group with the following data: ");
        log.trace("req: " + req);
        customersService.createCustomerGroup(new CustomerGroupData(req.getName(), req.getDiscount(), req.getStatus()));
    }

    @GetMapping(path = "/api/customer-groups", produces = "application/json; charset=UTF-8")
    public CustomerGroupResultGetResponse findCustomerGroups(
            @RequestParam(name = "sort_by", required = false, defaultValue = "group_id") String sortBy,
            @RequestParam(name = "page", required = false, defaultValue = "1") Long page,
            @RequestParam(name = "page_size", required = false, defaultValue = "100") Long pageSize,
            @RequestParam(name = "order_by", required = false, defaultValue = "ASC") String orderBy) {
        return customersService.findCustomerGroupsAll(new CustomerGroupsFilter(sortBy, page, pageSize, orderBy));
    }

    @PutMapping(path = "/api/customer-groups/{groupId}", consumes = "application/json; charset=UTF-8", produces = "application/json; charset=UTF-8")
    public void updateCustomerGroup(@PathVariable Long groupId,
                                    @RequestParam(name = "name", required = false) String name,
                                    @RequestParam(name = "discount", required = false) Integer discount,
                                    @RequestParam(name = "status", required = false) Integer status) {
        customersService.updateCustomerGroup(getCustomerGroupToUpdate(groupId, name, discount, status));
    }

    private CustomerGroupData getCustomerGroupToUpdate(Long groupId, String name, Integer discount, Integer status) {

        CustomerGroupData oldData = customersService.getCustomerGroup(groupId);

        if (name == null) {
            name = oldData.getName();
        }

        if (discount == null) {
            discount = oldData.getDiscount();
        }

        if (status == null) {
            status = oldData.getStatus();
        }

        return new CustomerGroupData(groupId, name, discount, status);
    }

    @DeleteMapping(path = "/api/customer-groups/{groupId}")
    public void deleteCustomerGroup(@PathVariable Long groupId) {
        log.trace("delete the specified customer group (" + groupId + ").");
        customersService.deleteCustomerGroup(groupId);
    }


    @GetMapping(path = "/api/customers", produces = "application/json; charset=UTF-8")
    public CustomersResultGetResponse findCustomers(
            @RequestParam(name = "page", required = false, defaultValue = "1") Long page,
            @RequestParam(name = "page_size", required = false, defaultValue = "100") Long pageSize,
            @RequestParam(name = "name_and_surname", required = false) String nameAndSurname,
            @RequestParam(name = "email", required = false) String email,
            @RequestParam(name = "group_id", required = false) Long groupId,
            @RequestParam(name = "status_id", required = false) Long statusId,
            @RequestParam(name = "sort_records_by", required = false) String sortRecordsBy,
            @RequestParam(name = "records_arrangement", required = false) String recordArrangement) {
        List<CustomerData> customers = customersService.findCustomers(new CustomersFilter(page, pageSize, nameAndSurname, email, groupId, statusId, sortRecordsBy, recordArrangement));
        return new CustomersResultGetResponse(customersService.getCustomersCount(), customersDataToResponses(customers));
    }

    private List<CustomersGetResponse> customersDataToResponses(List<CustomerData> data) {
        List<CustomersGetResponse> responses = new ArrayList<>();

        for (CustomerData datum : data) {
            responses.add(customerDataToResponse(datum));
        }

        return responses;
    }

    private CustomersGetResponse customerDataToResponse(CustomerData data) {
        return new CustomersGetResponse(
                data.getId(),
                data.getFirstName(),
                data.getLastName(),
                data.getFirstAddressLine(),
                data.getSecondAddressLine(),
                data.getBuildingNumber(),
                data.getApartmentNumber(),
                data.getPostalCode(),
                data.getCity(),
                data.getEmail(),
                data.getRegistrationDate(),
                data.getType(),
                data.getGroupId(),
                data.getActive(),
                data.getOrdersCount());
    }

    @DeleteMapping(path = "/api/customers/{customerId}")
    public void deleteCustomer(@PathVariable Long customerId) {
        log.trace("delete the specified customer (" + customerId + ").");
        customersService.deleteCustomer(customerId);
    }

    @PostMapping(path = "/api/customers", consumes = "application/json; charset=UTF-8", produces = "application/json; charset=UTF-8")
    public Long createCustomer(@RequestBody CustomerPostRequest req ) {
        log.trace("Create new customer with the following data: ");
        log.trace("req: " + req);
        return customersService.createCustomer(new CustomerData(req.getName(), req.getSurname(), req.getStreet(), req.getHouseNumber(), req.getApartmentNumber(), req.getPostalCode(), req.getCity(), req.getMail(), req.getGroup(), req.getStatus(), req.getCustomerFrom(), req.getType()));
    }

    @PutMapping(path = "/api/customer/{id}", produces = "application/json; charset=UTF-8")
    public void updateCustomer(@PathVariable Long id, @RequestBody CustomerPutRequest req) {
        customersService.updateCustomer(customerPutRequestToData(req, customersService.getCustomer(id)));
    }

    private CustomerData customerPutRequestToData (CustomerPutRequest req, CustomerData oldData) {
        Boolean isAnotherWeekendAddress = req.getAnotherWeekendAddress();
        Boolean isInvoice = req.getInvoice();
        Boolean isAnotherHoursWeekend = req.getAnotherHoursWeekend();
        return new CustomerData(
                oldData.getId(),
                req.getFirstName(),
                req.getLastName(),
                req.getPhone(),
                req.getStreet(),
                req.getHouseNumber(),
                req.getApartmentNumber(),
                req.getPostalCode(),
                req.getCity(),
                req.getEmail(),
                req.getGroupId(),
                req.getStatusId(),
                req.getDemanding(),
                isAnotherWeekendAddress,
                (isAnotherWeekendAddress) ? req.getWeekendStreet() : oldData.getWeekendStreet(),
                (isAnotherWeekendAddress) ? req.getWeekendHouseNumber() : oldData.getWeekendHouseNo(),
                (isAnotherWeekendAddress) ? req.getWeekendApartmentNumber() : oldData.getWeekendApartmentNo(),
                (isAnotherWeekendAddress) ? req.getWeekendPostalCode() : oldData.getWeekendPostalCode(),
                (isAnotherWeekendAddress) ? req.getWeekendCity() : oldData.getWeekendCity(),
                isInvoice,
                (isInvoice) ? req.getInvoiceCompanyName() : oldData.getInvoiceCompanyName(),
                (isInvoice) ? req.getInvoiceTaxNo() : oldData.getInvoiceTaxNo(),
                (isInvoice) ? req.getInvoiceStreet() : oldData.getInvoiceStreet(),
                (isInvoice) ? req.getInvoiceHouseNumber() : oldData.getInvoiceHouseNo(),
                (isInvoice) ? req.getInvoiceApartmentNumber() : oldData.getInvoiceApartmentNo(),
                (isInvoice) ? req.getInvoicePostalCode() : oldData.getInvoicePostalCode(),
                (isInvoice) ? req.getInvoiceCity() : oldData.getInvoiceCity(),
                req.getWeekPreferredHoursTo(),
                isAnotherHoursWeekend,
                (isAnotherHoursWeekend) ? req.getWeekendPreferredHoursTo() : oldData.getWeekendPreferredHoursTo(),
                req.getComment()
        );
    }

}
