package pl.com.mike.developer.domain.courseplatform;

import java.io.Serializable;
import java.time.LocalDateTime;

public class CustomerGroupData implements Serializable {
    private Long id;
    private LocalDateTime createDataTime;
    private LocalDateTime deleteDataTime;
    private String name;

    public CustomerGroupData(Long id, LocalDateTime createDataTime, String name) {
        this.id = id;
        this.createDataTime = createDataTime;
        this.name = name;
    }

    public CustomerGroupData(String name) {
        this.createDataTime = LocalDateTime.now();
        this.name = name;
    }

    public CustomerGroupData(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public LocalDateTime getCreateDataTime() {
        return createDataTime;
    }

    public LocalDateTime getDeleteDataTime() {
        return deleteDataTime;
    }

    public String getName() {
        return name;
    }
}
