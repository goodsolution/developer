package pl.com.mike.developer.api.adviser;

import java.util.List;

public class ApplicationRequestGetResponse {
    private List<ApplicationGetResponse> applications;
    private Long totalApplicationsCount;

    public ApplicationRequestGetResponse(List<ApplicationGetResponse> applications, Long totalApplicationsCount) {
        this.applications = applications;
        this.totalApplicationsCount = totalApplicationsCount;
    }

    public List<ApplicationGetResponse> getApplications() {
        return applications;
    }

    public Long getTotalApplicationsCount() {
        return totalApplicationsCount;
    }
}
