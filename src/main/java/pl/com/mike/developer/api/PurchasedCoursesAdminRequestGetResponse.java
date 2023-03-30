package pl.com.mike.developer.api;

import pl.com.mike.developer.api.courseplatform.PurchasedCourseGetResponse;

import java.util.List;

public class PurchasedCoursesAdminRequestGetResponse {
    List<PurchasedCourseGetResponse> purchasedCourses;

    public PurchasedCoursesAdminRequestGetResponse(List<PurchasedCourseGetResponse> purchasedCourses) {
        this.purchasedCourses = purchasedCourses;
    }

    public List<PurchasedCourseGetResponse> getPurchasedCourses() {
        return purchasedCourses;
    }
}
