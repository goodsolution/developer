package pl.com.mike.developer.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.InputStreamResource;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.com.mike.developer.domain.*;
import pl.com.mike.developer.logic.*;
import pl.com.mike.developer.logic.dietetics.DieteticsService;

import java.io.ByteArrayInputStream;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@RestController
public class AppEndpoint {
    private static final Logger log = LoggerFactory.getLogger(AppEndpoint.class);
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-LL-dd HH:mm:ss");
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-LL-dd");

    private static final DateTimeFormatter TIME_FORMATTER_SHORT = DateTimeFormatter.ofPattern("H:mm");

    private CustomersService customersService;
    private StatisticsService statisticsService;
    private ProductsService productsService;
    private DictionariesService dictionariesService; //TODO remove from controller
    private DeliveryService deliveryService;
    private GalleryService galleryService;
    private DieteticsService dieteticsService;

    public AppEndpoint(CustomersService customersService, StatisticsService statisticsService, ProductsService productsService, DictionariesService dictionariesService, DeliveryService deliveryService, GalleryService galleryService, DieteticsService dieteticsService) {
        this.customersService = customersService;
        this.statisticsService = statisticsService;
        this.productsService = productsService;
        this.dictionariesService = dictionariesService;
        this.deliveryService = deliveryService;
        this.galleryService = galleryService;
        this.dieteticsService = dieteticsService;
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
                    dictionariesService.getDictionaryValueById(order.getDeliveryMethodId(), DictionaryType.SHIPMENT_TYPES, Language.PL),
                    order.getPaymentMethodId(),
                    dictionariesService.getDictionaryValueById(order.getPaymentMethodId(), DictionaryType.ORDER_PAYMENT_METHODS, Language.PL),
                    order.getPaymentStatusId(),
                    order.getPaymentStatusId() != 0 ? dictionariesService.getDictionaryValueById(order.getPaymentStatusId(), DictionaryType.PAYMENT_STATUSES, Language.PL) : "",
                    order.getPaymentStatusCode(),
                    order.getDaysLeft(),
                    order.getNextOrderIndicator(),
                    order.getOrderStatusId(),
                    dictionariesService.getDictionaryValueById(order.getOrderStatusId(), DictionaryType.ORDER_STATUSES, Language.PL),
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



    private String getTimeWithoutLeadingZeros(LocalTime timeWithLeadingZeros) {
        if (timeWithLeadingZeros == null) {
            return "";
        }
        return timeWithLeadingZeros.format(DateTimeFormatter.ofPattern("H:mm"));
    }

    // from ewidencja zamówień - the usuń button


    // from order details




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


    @GetMapping(path = "/api/dictionary/{name}", produces = "application/json; charset=UTF-8")
    public List<DictionaryData> getDictionary(@PathVariable String name) {
        return dictionariesService.getDictionary(DictionaryType.valueOf(name.toUpperCase()), Language.PL);
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
