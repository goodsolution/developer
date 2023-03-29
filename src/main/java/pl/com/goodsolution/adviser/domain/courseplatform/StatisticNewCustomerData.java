package pl.com.goodsolution.adviser.domain.courseplatform;

import java.time.LocalDateTime;

public class StatisticNewCustomerData {
    private String login;
    private LocalDateTime registrationDateTime;

    public StatisticNewCustomerData(String login, LocalDateTime registrationDateTime) {
        this.login = login;
        this.registrationDateTime = registrationDateTime;
    }

    public String getLogin() {
        return login;
    }

    public LocalDateTime getRegistrationDateTime() {
        return registrationDateTime;
    }
}


