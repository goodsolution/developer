package pl.com.mike.developer.api.courseplatform.external.payu;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import pl.com.mike.developer.config.ApplicationConfig;
import pl.com.mike.developer.domain.courseplatform.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class PayUApi {

    private RestTemplate restTemplate;
    private ApplicationConfig applicationConfig;

    public PayUApi(RestTemplate restTemplate, ApplicationConfig applicationConfig) {
        this.restTemplate = restTemplate;
        this.applicationConfig = applicationConfig;
    }

    public PayUAuthorizationData getAuthorization() {
        String url = prepareAuthorizationUrl();
        AuthorizationResponse response = restTemplate.exchange(url, HttpMethod.POST, null, AuthorizationResponse.class).getBody();
        return new PayUAuthorizationData(response);
    }

    public PayuCreateOrderResponseData createOrder(String accessToken, CustomerData loggedCustomer, BasketData basket, Long orderId, String customerIp) {
        String url = prepareCreateOrderUrl();
        HttpHeaders headers = prepareCreateOrderHeaders(accessToken);
        String body = createOrderRequestBodyToJson(prepareCreateOrderRequestBody(loggedCustomer, basket, orderId, customerIp));
        CreateOrderResponse response = restTemplate.exchange(url, HttpMethod.POST, new HttpEntity<>(body, headers), CreateOrderResponse.class).getBody();
        return new PayuCreateOrderResponseData(response);
    }

    public OrderCancelResponse cancelOrder(CourseOrderData orderData) {
        PayUAuthorizationData authData = getAuthorization();
        String url = prepareCancelOrderUrl(orderData.getPayuOrderId());
        HttpHeaders headers = prepareCancelOrderHeaders(authData.getAccessToken());
        return restTemplate.exchange(url, HttpMethod.DELETE, new HttpEntity<>(null, headers), OrderCancelResponse.class).getBody();
    }

    private String prepareAuthorizationUrl() {
        return applicationConfig.getPaymentsPayuUrl() + "/pl/standard/user/oauth/authorize?grant_type=client_credentials&" +
                "client_id=" + applicationConfig.getPaymentsPayuClientId() + "&client_secret=" + applicationConfig.getPaymentsPayuClientSecret();
    }

    private String prepareCreateOrderUrl() {
        return applicationConfig.getPaymentsPayuUrl() + "/api/v2_1/orders";
    }

    private String prepareCancelOrderUrl(String payuOrderId) {
        return applicationConfig.getPaymentsPayuUrl() + "/api/v2_1/orders/" + payuOrderId;
    }

    private HttpHeaders prepareCreateOrderHeaders(String accessToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(accessToken);
        return headers;
    }

    private HttpHeaders prepareCancelOrderHeaders(String accessToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(accessToken);
        return headers;
    }

    private CreateOrderRequest prepareCreateOrderRequestBody(CustomerData loggedCustomer, BasketData basket, Long orderId, String customerIp) {
        return new CreateOrderRequest(
                customerIp,
                applicationConfig.getPaymentsPayuMerchantPosId(),
                "Course/s",
                "PLN",
                basket.getTotalPrice().multiply(new BigDecimal(100)).toBigInteger(),
                new CreateOrderRequestBuyer(loggedCustomer.getLogin(), loggedCustomer.getLanguage()),
                prepareProducts(basket.getCourses()),
                applicationConfig.getCoursePlatformUrl() + "/thanks-for-shopping",
                orderId,
                applicationConfig.getCoursePlatformUrl() + "/api/crs/payu/notify");
    }

    private List<CreateOrderRequestProduct> prepareProducts(List<CourseData> courses) {
        List<CreateOrderRequestProduct> products = new ArrayList<>();
        for (CourseData course : courses) {
            products.add(new CreateOrderRequestProduct(course.getTitle(), course.getPrice().multiply(new BigDecimal(100)).toBigInteger(), 1, true));
        }
        return products;
    }

    private String createOrderRequestBodyToJson(CreateOrderRequest requestBody) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.writeValueAsString(requestBody);
        } catch (Exception ex) {
            throw new IllegalArgumentException("Error during JSON parsing");
        }
    }

}
