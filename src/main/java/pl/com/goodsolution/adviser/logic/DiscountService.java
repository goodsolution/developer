package pl.com.goodsolution.adviser.logic;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.com.goodsolution.adviser.domain.*;

import java.math.BigDecimal;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class DiscountService {
    private static final Logger log = LoggerFactory.getLogger(DiscountService.class);
    private DiscountJdbcRepository discountJdbcRepository;
    private DictionariesService dictionariesService;
    private ProductsService productsService;
    private DeliveryService deliveryService;

    private DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-LL-dd HH:mm:ss.S");
    private DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-LL-dd");
    private DateTimeFormatter timeFormatterLong = DateTimeFormatter.ofPattern("H:mm:ss");
    private DateTimeFormatter timeFormatterShort = DateTimeFormatter.ofPattern("H:mm");

    public DiscountService(DiscountJdbcRepository discountJdbcRepository, DictionariesService dictionariesService, ProductsService productsService, DeliveryService deliveryService) {
        this.discountJdbcRepository = discountJdbcRepository;
        this.dictionariesService = dictionariesService;
        this.productsService = productsService;
        this.deliveryService = deliveryService;

    }

    public DiscountResultData calculateDiscountsForOrderProducts(final List<OrderProductCreateData> items, Long deliveryMethodId, BigDecimal basketSum, BigDecimal basketSumNo) {
        List<AppliedDiscountData> dietDiscountsApplied = new ArrayList<>();
        List<AppliedDiscountData> saleDiscountsApplied = new ArrayList<>();

        BigDecimal salesDiscount = BigDecimal.ZERO;
        for (OrderProductCreateData productInOrder : items) {
            for (SaleTableData sale : findSaleTable(productInOrder.getDietId())) {
                int l = 0;
                final List<SaleVariantData> variants = findSaleVariants(sale.getId());
                int mustHave = variants.size();
                for (SaleVariantData variant : variants) {
                    if (productInOrder.getDietId().equals(variant.getDietId())) {
                        if (productInOrder.getDays().longValue() >= variant.getDays().longValue()) {
                            l++;
                        }
                    }
                }

                if (l == mustHave) {
                    salesDiscount = sale.getDiscount();
                }
            }
            BigDecimal extrasPrice = BigDecimal.ZERO;
            BigDecimal minusPrice = BigDecimal.ZERO;
            Long productId = productInOrder.getDietTypeId();
            ProductDetailData productData = productsService.getProduct(productId);
            if (productData == null) {
                throw new IllegalArgumentException("Nie mogę pobrać produktu productId: " + productId);
            }
            BigDecimal netPrice = productData.getNetPriceRetail();
            BigDecimal taxValue = productsService.getTaxValue(productData.getTaxId());
            BigDecimal grossPrice = productData.getGrossPriceRetail();
            BigDecimal days = BigDecimal.valueOf(productInOrder.getDays());
            BigDecimal quantity = BigDecimal.valueOf(productInOrder.getQuantity());
            log.debug("netPrice: " + netPrice + " grossPrice: " + grossPrice);
            if (deliveryMethodId.longValue() == Const.DELIVERY_METHOD_PERSONAL) {
                BigDecimal deliveryDiscount = deliveryService.getDeliveryDiscount(Const.DELIVERY_METHOD_PERSONAL);
                if (deliveryDiscount == null) {
                    throw new IllegalArgumentException("Nie mogę pobrać zniżki za odbiór osobisty");
                }
                minusPrice = days.multiply(deliveryDiscount).multiply(quantity);
            }
            for (Long id : productInOrder.getExtraIds()) {
                BigDecimal extPrice = productsService.getExtrasPrice(id);
                if (extPrice == null) {
                    throw new IllegalArgumentException("Nie mogę pobrać ceny extras id: " + id);
                }
                extrasPrice = extrasPrice.add(extPrice); //TODO getExtras should be
            }
            if (productInOrder.getTestDay()) {
                grossPrice = productsService.getTestSetPrice(productInOrder.getDietId());
                netPrice = productsService.getTestSetPrice(productInOrder.getDietId());
            }
            BigDecimal price = grossPrice.multiply(quantity).multiply(days);
            log.debug("price: " + price + " grossPrice: " + grossPrice + " quantity: " + quantity + " days: " + days);
            if (!salesDiscount.equals(BigDecimal.ZERO)) {
                BigDecimal basketSumBeforeSaleDiscount = basketSum;
                price = price.subtract(price.multiply(salesDiscount).divide(BigDecimal.valueOf(100), 2, BigDecimal.ROUND_HALF_UP));
                //TODo Check $price = round($price, 2);
                saleDiscountsApplied.add(new AppliedDiscountData(productInOrder.getOrderId(),  DiscountType.SALE, salesDiscount, salesDiscount, basketSumBeforeSaleDiscount, basketSum));
            }
            log.debug("price: " + price + " grossPrice: " + grossPrice + " quantity: " + quantity + " days: " + days);
            BigDecimal discountDiet = productsService.getDietDiscount(productInOrder.getDays(), productInOrder.getDietId()); // TODO move to discount service

            price = price.subtract(discountDiet.multiply(days)).add(extrasPrice).subtract(minusPrice);
            log.debug("price: " + price + " grossPrice: " + grossPrice + " quantity: " + quantity + " days: " + days);
            BigDecimal priceForPiece = grossPrice;
            BigDecimal qNetPriceForPiece = netPrice;
            BigDecimal qNetPricee = netPrice.multiply(quantity).multiply(days);
            BigDecimal qPriceForPiece = priceForPiece;
            BigDecimal qPrice = price;
            BigDecimal basketSumBeforeDietDiscount = basketSum;
            basketSum = basketSum.add(qPrice);
            basketSumNo = basketSumNo.add(qPrice);
            if (discountDiet.compareTo(BigDecimal.ZERO) == 1) {
                dietDiscountsApplied.add(new AppliedDiscountData(productInOrder.getOrderId(), DiscountType.DIET, discountDiet, discountDiet.multiply(days), basketSumBeforeDietDiscount, basketSum));
            }
            log.debug("end of iter discount calc basketSum: " + basketSum + " basketSumNo: " + basketSumNo);
        }
        log.debug("end of discount calc basketSum: " + basketSum + " basketSumNo: " + basketSumNo);
        return new DiscountResultData(basketSum, basketSumNo, dietDiscountsApplied, saleDiscountsApplied);
    }

    List<SaleTableData> findSaleTable(Long dietId) {
        List<Map<String, Object>> rows = discountJdbcRepository.findSaleTable(dietId);
        List<SaleTableData> list = new ArrayList<>();
        for (Map row : rows) {
            SaleTableData data = new SaleTableData(
                    Long.valueOf(String.valueOf(row.get("id"))),
                    BigDecimal.valueOf((Float) row.get("discount"))
            );
            list.add(data);
        }
        return list;
    }

    List<SaleVariantData> findSaleVariants(Long saleId) {
        List<Map<String, Object>> rows = discountJdbcRepository.findSaleVariants(saleId);
        List<SaleVariantData> list = new ArrayList<>();
        for (Map row : rows) {
            SaleVariantData data = new SaleVariantData(
                    Long.valueOf(String.valueOf(row.get("id"))),
                    Long.valueOf(String.valueOf(row.get("category_id"))),
                    Long.valueOf(String.valueOf(row.get("days"))),
                    Long.valueOf(String.valueOf(row.get("sale_id")))
            );
            list.add(data);
        }
        return list;
    }

    BigDecimal calculateOtherDiscountsSum(Long orderId) {
        BigDecimal result = discountJdbcRepository.calculateOtherDiscountsSum(orderId);
        return result == null ? BigDecimal.ZERO : result;
    }

    void storeAppliedDiscount(AppliedDiscountData data) {
        discountJdbcRepository.storeAppliedDiscount(data);
    }

    void removeAppliedDiscounts(Long orderId, DiscountType discountType) {
        discountJdbcRepository.removeAppliedDiscounts(orderId, discountType);
    }
}
