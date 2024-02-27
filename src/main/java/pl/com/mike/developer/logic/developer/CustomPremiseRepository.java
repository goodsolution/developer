package pl.com.mike.developer.logic.developer;

import pl.com.mike.developer.domain.developer.PremiseData;

import java.util.List;

public interface CustomPremiseRepository {
    List<PremiseData> findPriceByInvestmentId(Long id, String priceFunction);

}
