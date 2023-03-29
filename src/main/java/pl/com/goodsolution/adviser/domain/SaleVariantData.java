package pl.com.goodsolution.adviser.domain;

public class SaleVariantData {
    private Long id;
    private Long dietId;
    private Long days;
    private Long saleId;

    public SaleVariantData(Long id, Long dietId, Long days, Long saleId) {
        this.id = id;
        this.dietId = dietId;
        this.days = days;
        this.saleId = saleId;
    }

    public Long getId() {
        return id;
    }

    public Long getDietId() {
        return dietId;
    }

    public Long getDays() {
        return days;
    }

    public Long getSaleId() {
        return saleId;
    }
}
