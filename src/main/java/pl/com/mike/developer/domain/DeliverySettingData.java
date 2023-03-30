package pl.com.mike.developer.domain;

import java.time.LocalTime;

public class DeliverySettingData {
    private Long id;
    private String setting;
    private LocalTime value;

    public DeliverySettingData(Long id, String setting, LocalTime value) {
        this.id = id;
        this.setting = setting;
        this.value = value;
    }

    public Long getId() {
        return id;
    }

    public String getSetting() {
        return setting;
    }

    public LocalTime getValue() {
        return value;
    }
}
