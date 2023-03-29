package pl.com.goodsolution.adviser.logic.courseplatform;

import org.springframework.stereotype.Service;
import pl.com.goodsolution.adviser.domain.courseplatform.CustomerData;
import pl.com.goodsolution.adviser.domain.courseplatform.CustomersFilter;
import pl.com.goodsolution.adviser.domain.courseplatform.NewsletterData;

@Service
public class MarketingService {
    private MarketingJdbcRepository marketingJdbcRepository;
    private CourseCustomersService courseCustomersService;
    private NewsletterService newsletterService;


    public MarketingService(MarketingJdbcRepository marketingJdbcRepository, CourseCustomersService courseCustomersService, NewsletterService newsletterService) {
        this.marketingJdbcRepository = marketingJdbcRepository;
        this.courseCustomersService = courseCustomersService;
        this.newsletterService = newsletterService;
    }

    public void sendNewsletter(NewsletterData newsletter) {
        if (newsletter == null || newsletter.getContent() == null || newsletter.getContent().size() == 0) {
            throw new IllegalArgumentException("Incorrect content");
        }
        for (CustomerData customer : courseCustomersService.find(new CustomersFilter(true, true, 10000l, true))) {
            newsletterService.sendNewsletterToCustomer(customer, newsletter);
        }
    }
}
