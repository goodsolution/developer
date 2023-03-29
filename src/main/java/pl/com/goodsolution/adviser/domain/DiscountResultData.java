package pl.com.goodsolution.adviser.domain;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class DiscountResultData {
    private BigDecimal basketSum;
    private BigDecimal basketSumNo;

    private List<AppliedDiscountData> dietDiscountsApplied = new ArrayList<>();
    private List<AppliedDiscountData> saleDiscountsApplied = new ArrayList<>();

    public DiscountResultData(BigDecimal basketSum, BigDecimal basketSumNo, List<AppliedDiscountData> dietDiscountsApplied, List<AppliedDiscountData> saleDiscountsApplied) {
        this.basketSum = basketSum;
        this.basketSumNo = basketSumNo;
        this.dietDiscountsApplied = dietDiscountsApplied;
        this.saleDiscountsApplied = saleDiscountsApplied;
    }

    public BigDecimal getBasketSum() {
        return basketSum;
    }

    public BigDecimal getBasketSumNo() {
        return basketSumNo;
    }

    public List<AppliedDiscountData> getDietDiscountsApplied() {
        return dietDiscountsApplied;
    }

    public List<AppliedDiscountData> getSaleDiscountsApplied() {
        return saleDiscountsApplied;
    }
}
