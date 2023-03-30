package pl.com.mike.developer.api.courseplatform;

import pl.com.mike.developer.domain.courseplatform.CustomerGroupData;

import java.time.format.DateTimeFormatter;

public class CustomerGroupGetResponse {
    private Long id;
    private String createDateTime;
    private String name;

    public CustomerGroupGetResponse(CustomerGroupData customerGroupData) {
        this.id = customerGroupData.getId();
        this.createDateTime = customerGroupData.getCreateDataTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        this.name = customerGroupData.getName();
    }

    public Long getId() {
        return id;
    }

    public String getCreateDateTime() {
        return createDateTime;
    }

    public String getName() {
        return name;
    }
}
