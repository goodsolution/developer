package pl.com.goodsolution.adviser.api.courseplatform;

import pl.com.goodsolution.adviser.domain.courseplatform.StatisticNewCustomerData;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class StatisticNewCustomerGetResponse {

    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-LL-dd HH:mm:ss");
    private String login;
    private String registrationDateTime;

    public StatisticNewCustomerGetResponse(StatisticNewCustomerData statisticNewCustomerData) {
        this.login = statisticNewCustomerData.getLogin();
        this.registrationDateTime = statisticNewCustomerData.getRegistrationDateTime().format(DATE_TIME_FORMATTER);
    }

    public String getLogin() {
        return login;
    }

    public String getRegistrationDateTime() {
        return registrationDateTime;
    }
}
