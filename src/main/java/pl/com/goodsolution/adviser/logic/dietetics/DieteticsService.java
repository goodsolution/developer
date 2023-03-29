package pl.com.goodsolution.adviser.logic.dietetics;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.com.goodsolution.adviser.domain.AllergenData;
import pl.com.goodsolution.adviser.domain.ProductTypeData;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class DieteticsService {
    private DieteticsJdbcRepository dieteticsJdbcRepository;
    private ProductsTypesJdbcRepository productsTypesJdbcRepository;

    public DieteticsService(DieteticsJdbcRepository dieteticsJdbcRepository, ProductsTypesJdbcRepository productsTypesJdbcRepository) {
        this.dieteticsJdbcRepository = dieteticsJdbcRepository;
        this.productsTypesJdbcRepository = productsTypesJdbcRepository;
    }

    public List<AllergenData> findAllergens() {

        List<Map<String, Object>> rows = dieteticsJdbcRepository.findAllergens();
        Long no = 1L;
        List<AllergenData> list = new ArrayList<>();
        for (Map row : rows) {
            AllergenData data = new AllergenData(
                    Long.valueOf(String.valueOf(row.get("allergen_id"))),
                    no++,
                    (String) row.get("name")
            );
            list.add(data);
        }

        return list;
    }

    public void updateAllergen(AllergenData data) {
        dieteticsJdbcRepository.updateAllergen(data);
    }

    public void createAllergen(AllergenData data) {
        dieteticsJdbcRepository.createAllergen(data);
    }

    public void deleteAllergen(Long id) {
        dieteticsJdbcRepository.deleteAllergen(id);
    }

    public List<ProductTypeData> findProductsTypes() {

        List<Map<String, Object>> rows = productsTypesJdbcRepository.findProducts();
        Long no = 1L;
        List<ProductTypeData> list = new ArrayList<>();
        for (Map row : rows) {
            ProductTypeData data = new ProductTypeData(
                    Long.valueOf(String.valueOf(row.get("id"))),
                    no++,
                    (String) row.get("type")
            );
            list.add(data);
        }

        return list;
    }

    public void updateProductType(ProductTypeData data) {
        productsTypesJdbcRepository.updateProduct(data);
    }

    public void createProductType(ProductTypeData data) {
        productsTypesJdbcRepository.createProduct(data);
    }

    public void deleteProductType(Long id) {
        productsTypesJdbcRepository.deleteProduct(id);
    }


}
