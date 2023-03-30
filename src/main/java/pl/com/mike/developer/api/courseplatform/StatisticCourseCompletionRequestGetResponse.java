package pl.com.mike.developer.api.courseplatform;

import java.util.List;

public class StatisticCourseCompletionRequestGetResponse {
    private List<StatisticCourseCompletionGetResponse> courseCompletion;

    public StatisticCourseCompletionRequestGetResponse(List<StatisticCourseCompletionGetResponse> courseCompletion) {
        this.courseCompletion = courseCompletion;
    }

    public List<StatisticCourseCompletionGetResponse> getCourseCompletion() {
        return courseCompletion;
    }
}
