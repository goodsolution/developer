package pl.com.mike.developer.domain;

import java.math.BigDecimal;

public class DriverPayData {
    private Long id;
    private BigDecimal normal;
    private BigDecimal sameAddress;
    private Long driverId;

    public DriverPayData(Long id, BigDecimal normal, BigDecimal sameAddress, Long driverId) {
        this.id = id;
        this.normal = normal;
        this.sameAddress = sameAddress;
        this.driverId = driverId;
    }

    public Long getId() {
        return id;
    }

    public BigDecimal getNormal() {
        return normal;
    }

    public BigDecimal getSameAddress() {
        return sameAddress;
    }

    public Long getDriverId() {
        return driverId;
    }
}
