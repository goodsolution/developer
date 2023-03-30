package pl.com.mike.developer.logic;

import com.openhtmltopdf.pdfboxout.PdfRendererBuilder;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;
import pl.com.mike.developer.Application;
import pl.com.mike.developer.config.ApplicationConfig;
import pl.com.mike.developer.domain.*;

import java.io.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
public class ProductsService {
    private static final Logger log = LoggerFactory.getLogger(ProductsService.class);
    private SpringTemplateEngine templateEngine;
    private ProductsJdbcRepository productsJdbcRepository;
    private ApplicationConfig applicationConfig;

    public ProductsService(SpringTemplateEngine templateEngine, ProductsJdbcRepository productsJdbcRepository, ApplicationConfig applicationConfig) {
        this.templateEngine = templateEngine;
        this.productsJdbcRepository = productsJdbcRepository;
        this.applicationConfig = applicationConfig;
    }

    public List<ProductDemandData> findProductDemands(LocalDate date, Long page, Long limit) {
        List<Map<String, Object>> rows = productsJdbcRepository.findProductDemands(date, limit * (page - 1L), limit);

        Long no = 1L;
        List<ProductDemandData> list = new ArrayList<>();
        Set<String> categories = new TreeSet<>();
        for (Map row : rows) {
            categories.add((String) row.get("category_name"));
        }
        log.debug("categories count: " + categories.size());
        for (String category : categories) {
            ProductDemandData productDemandData = new ProductDemandData(no++, category, new ArrayList<>());
            for (Map row : rows) {
                String categoryName = (String) row.get("category_name");
                if (category.equals(categoryName)) {
                    Long quantity = Long.valueOf(((Double) row.get("quantity")).longValue());
                    Long deliveryId = Long.valueOf(String.valueOf(row.get("delivery_orders_id")));
                    if (!productsJdbcRepository.checkProductOrderGroup(deliveryId)) {
                        quantity = 1L;
                    }
                    ProductData product = new ProductData((String) row.get("product_name"), quantity);
                    productDemandData.getProducts().add(product);
                }
            }
            list.add(productDemandData);
        }
        return aggregateProducts(list);
    }

    private List<ProductDemandData> aggregateProducts(List<ProductDemandData> list) {
        log.debug("demands size: " + list.size());
        List<ProductDemandData> result = new ArrayList<>();
        for (ProductDemandData prod : list) {
            Map<String, Long> summing = prod.getProducts().stream().collect(
                    Collectors.groupingBy(ProductData::getName, Collectors.summingLong(ProductData::getQuantity)));

            List<ProductData> products = new ArrayList<>();
            for (Map.Entry<String, Long> entry : summing.entrySet()) {
                String name = entry.getKey();
                Long quantity = entry.getValue();
                ProductData productData = new ProductData(name,  quantity);
                products.add(productData);
            }
            Collections.sort(products, Comparator.comparing(ProductData::getName));
            ProductDemandData demand = new ProductDemandData(prod.getNo(), prod.getDiet(), products);
            result.add(demand);
        }

        // TODO pagination

        return result;
    }

    public ProductDemandResultData findProductDemandsWithSum(LocalDate date, Long page, Long limit) {
        List<ProductDemandData> list = findProductDemands(date, page, limit);
        Long sum = sumOfProductQuantities(list);
        return new ProductDemandResultData(sum, list);
    }

    private Long sumOfProductQuantities(List<ProductDemandData> list) {
        return list.stream().map(o -> o.getProducts().stream().map(p -> p.getQuantity()).reduce(0L, (a, b) -> a + b)).reduce(0L, (a, b) -> a + b);
    }

    public ByteArrayInputStream getProductDemandsPdf(LocalDate date, Long page, Long limit) {
        ProductDemandResultData data = findProductDemandsWithSum(date, page, limit);
        try {
            return new ByteArrayInputStream(
                    FileUtils.readFileToByteArray(
                            exportToPdfBox(fillPdfVariables(data, date), applicationConfig.getTemplateNameProductsDemandPdf(), applicationConfig.getPathToProductsDemandPdf() + getProductsDemandPdfName(date))));
        } catch (IOException e) {
            log.error("Exception while transforming to ByteArrayInputStream : {}", e);
        }
        return null;
    }

    private String getProductsDemandPdfName(LocalDate date) {
        return "request_" + getUnique() + "_" + date.format(DateTimeFormatter.ofPattern("dd-MM-yyyy")) + ".pdf";
        // pdf name should be: request_5d43387153e95_01-08-2019.pdf
    }

    private String getUnique() {
        long time = System.currentTimeMillis();
        return String.format("%s%08x%05x", "", time / 1000, time);
    }

    private Map<String, Object> fillPdfVariables(ProductDemandResultData data, LocalDate date) {
        Map<String, Object> vars = new HashMap<>();
        vars.put("list", data.getProducts());
        vars.put("sum", data.getSum());
        vars.put("date", date);
        return vars;
    }

    private File exportToPdfBox(Map<String, Object> variables, String templatePath, String outPath) {
        PdfRendererBuilder builder = new PdfRendererBuilder();
        try (OutputStream os = new FileOutputStream(outPath);) {
            log.debug("fonts dir: " + applicationConfig.getPathToFonts());
            if (applicationConfig.getPathToFonts() == null || "".equals(applicationConfig.getPathToFonts())) {
                builder.useFont(new File(Application.class.getClassLoader().getResource("static/fonts/dejavu/DejaVuSans.ttf").getFile()), "dejavu");
            } else {
                builder.useFont(new File(applicationConfig.getPathToFonts() + "DejaVuSans.ttf"), "dejavu");
            }

            String html = getHtmlString(variables, templatePath);
            log.trace(html);
            builder.withHtmlContent(html, "file:");
            builder.toStream(os);
            builder.run();
        } catch (Exception e) {
            log.error("Exception while generating pdf : {}", e);
        }
        return new File(outPath);
    }

    private String getHtmlString(Map<String, Object> variables, String templatePath) {
        final Context ctx = new Context();
        ctx.setVariables(variables);
        try {
            return templateEngine.process(templatePath, ctx);
        } catch (Exception e) {
            log.error("Exception while getting html string from template engine : {}", e);
            return null;
        }
    }

    public ProductDetailData getProduct(Long productId) {
        return productsJdbcRepository.getProduct(productId);
    }

    public BigDecimal getDietDiscount(Long days, Long dietId) {
        if(dietId != null) {
            Set<Long> ids = productsJdbcRepository.getAllExclusiveID();
            if (ids.contains(dietId)) {
                return BigDecimal.ZERO;
            }
        }
        return productsJdbcRepository.getDietDiscount(days);
    }

    BigDecimal getTaxValue(Long taxId) {
        return productsJdbcRepository.getTaxValue(taxId);
    }

    BigDecimal getTestSetPrice(Long categoryId, BigDecimal priceForPiece) {
        CategoryData category = productsJdbcRepository.getCategory(categoryId);
        BigDecimal discount = category.getTestSetPriceDiscount();
        if (discount == null) {
            discount = BigDecimal.ZERO;
        }
        BigDecimal price = priceForPiece.subtract(discount);
        if (price.compareTo(BigDecimal.ZERO) < 0) {
            price = BigDecimal.ZERO;
        }
        return price;
    }

    BigDecimal getTestSetPrice(Long categoryId) {
        CategoryData category = productsJdbcRepository.getCategory(categoryId);
        BigDecimal discount = category.getTestSetPriceDiscount();
        if (discount == null) {
            discount = BigDecimal.ZERO;
        }
        BigDecimal price = discount;
        if (price.compareTo(BigDecimal.ZERO) < 0) {
            price = BigDecimal.ZERO;
        }
        return price;
    }

    BigDecimal getExtrasPrice(Long extraId) {
        return productsJdbcRepository.getExtrasPrice(extraId);
    }

    String getProductNameInLanguage(Long productId, String lang) {
        String result = productsJdbcRepository.getProductNameInLanguage(productId, lang);
        if (result == null) {
            return "No variable in this language";
        } else {
            return result;
        }
    }
}


