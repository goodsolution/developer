package pl.com.mike.developer.logic.adviser;

import org.springframework.stereotype.Service;
import pl.com.mike.developer.domain.adviser.*;

import java.util.List;

@Service
public class AdviceCategoriesService {

    private final AdviceCategoriesJdbcRepository adviceCategoriesJdbcRepository;

    public AdviceCategoriesService(AdviceCategoriesJdbcRepository adviceCategoriesJdbcRepository) {
        this.adviceCategoriesJdbcRepository = adviceCategoriesJdbcRepository;
    }

    public List<AdviceCategoryData> find(AdviceCategoriesFilter filter) {
        return adviceCategoriesJdbcRepository.find(filter);
    }

    public Long getTotalAdviceCategoriesCount() {
        return adviceCategoriesJdbcRepository.getTotalAdviceCategoriesCount();
    }

    public Long storePurchase(AdvisePurchaseData data) {
        return adviceCategoriesJdbcRepository.storePurchase(data);
    }

    public void removePurchase(Long id) {
        adviceCategoriesJdbcRepository.removePurchase(id);
    }

    //public void updatePurchase(AdvisePurchaseData data) {
//        adviceCategoriesJdbcRepository.updatePurchase(data);
//    }

    public List<AdvicePurchasedCategoryData> findPurchasedCategories(AdvicePurchasedCategoriesFilter filter) {
        return adviceCategoriesJdbcRepository.findPurchasedCategories(filter);
    }
}
