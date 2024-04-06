package pl.com.mike.developer.logic.developer;

import org.springframework.stereotype.Repository;
import pl.com.mike.developer.domain.developer.Premise;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.*;
import java.util.List;

@Repository
public class CustomPremiseRepositoryImpl implements CustomPremiseRepository{
    @PersistenceContext
    private EntityManager entityManager;
    @Override
    public List<Premise> findPriceByInvestmentId(Long id, String priceFunction) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Premise> cq = cb.createQuery(Premise.class);
        Root<Premise> premiseRoot = cq.from(Premise.class);

        // Create a subquery to select the minimum or maximum price
        Subquery<Double> priceSubquery = cq.subquery(Double.class);
        Root<Premise> premiseSubRoot = priceSubquery.from(Premise.class);

        // Determine the aggregate function based on the input parameter
        Expression<Double> priceExpression;
        if ("min".equals(priceFunction)) {
            priceExpression = cb.min(premiseSubRoot.get("totalPrice"));
        } else if ("max".equals(priceFunction)) {
            priceExpression = cb.max(premiseSubRoot.get("totalPrice"));
        } else {
            throw new IllegalArgumentException("Invalid priceFunction argument: " + priceFunction);
        }

        priceSubquery.select(priceExpression);
        priceSubquery.where(cb.equal(premiseSubRoot.get("building").get("investmentId"), id));

        // Use the aggregate result in the main query
        cq.select(premiseRoot).where(cb.equal(premiseRoot.get("totalPrice"), priceSubquery), cb.equal(premiseRoot.get("building").get("investmentId"), id));

        return entityManager.createQuery(cq).getResultList();
    }

}
