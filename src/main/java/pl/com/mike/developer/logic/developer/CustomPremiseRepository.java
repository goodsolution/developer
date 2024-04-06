package pl.com.mike.developer.logic.developer;

import pl.com.mike.developer.domain.developer.Premise;

import java.util.List;

public interface CustomPremiseRepository {
    List<Premise> findPriceByInvestmentId(Long id, String priceFunction);

}
