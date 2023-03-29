package pl.com.goodsolution.adviser.logic.courseplatform;

import org.springframework.stereotype.Service;
import pl.com.goodsolution.adviser.domain.JobOfferData;
import pl.com.goodsolution.adviser.domain.courseplatform.*;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class JobOffersService {
    private JobOffersJdbcRepository jobOffersJdbcRepository;
    private CourseCustomersService courseCustomersService;
    private CustomerAuthoritiesService customerAuthoritiesService;

    public JobOffersService(JobOffersJdbcRepository jobOffersJdbcRepository, CourseCustomersService courseCustomersService, CustomerAuthoritiesService customerAuthoritiesService) {
        this.jobOffersJdbcRepository = jobOffersJdbcRepository;
        this.courseCustomersService = courseCustomersService;
        this.customerAuthoritiesService = customerAuthoritiesService;
    }

    public List<JobOfferData> find(JobOffersFilter filter) {
        return jobOffersJdbcRepository.find(filter);
    }

    public void delete(Long id) {
        CustomerData loggedCustomer = courseCustomersService.getLoggedCustomer();
        JobOfferData jobOfferData = find(new JobOffersFilter(id)).get(0);
        jobOffersJdbcRepository.update(new JobOfferData(jobOfferData, LocalDateTime.now()), loggedCustomer.getId());
    }

    public JobOfferData get(Long id){
        return find(new JobOffersFilter(id)).get(0);
    }

    public void create(JobOfferData data) {
        CustomerData loggedCustomer = courseCustomersService.getLoggedCustomer();
        if (customerAuthoritiesService.hasCustomerAuthority(loggedCustomer, CustomerAuthority.EMPLOYER)) {
            JobOfferData jobOfferData = new JobOfferData(data.getTitle(), data.getContent());
            validate(jobOfferData);
            jobOffersJdbcRepository.create(jobOfferData, loggedCustomer.getId());
        } else {
            throw new IllegalArgumentException("You don't have permission to this endpoint!");
        }

    }

    private void validate(JobOfferData data) {
        validateTitle(data.getTitle());
        validateContent(data.getContent());
    }

    private void validateContent(String content) {
        if (content == null || content.equals("")) {
            throw new IllegalArgumentException("Content can't be empty");
        } else if (content.length() > 2000) {
            throw new IllegalArgumentException("Content too long, max 200 characters!");
        }
    }

    private void validateTitle(String title) {
        if (title == null || title.equals("")) {
            throw new IllegalArgumentException("Title can't be empty");
        } else if (title.length() > 200) {
            throw new IllegalArgumentException("Title too long, max 200 characters!");
        }
    }


}