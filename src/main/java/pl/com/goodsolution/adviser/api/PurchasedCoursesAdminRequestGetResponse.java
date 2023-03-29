package pl.com.goodsolution.adviser.api;

import pl.com.goodsolution.adviser.api.courseplatform.PurchasedCourseGetResponse;

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
