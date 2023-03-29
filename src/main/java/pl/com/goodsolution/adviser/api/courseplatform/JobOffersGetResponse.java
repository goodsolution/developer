package pl.com.goodsolution.adviser.api.courseplatform;

import java.util.List;

public class JobOffersGetResponse {
    private List<JobOfferGetResponse> jobOffers;

    public JobOffersGetResponse() {
    }

    public JobOffersGetResponse(List<JobOfferGetResponse> jobOffers) {
        this.jobOffers = jobOffers;
    }

    public List<JobOfferGetResponse> getJobOffers() {
        return jobOffers;
    }

    public void setJobOffers(List<JobOfferGetResponse> jobOffers) {
        this.jobOffers = jobOffers;
    }
}
