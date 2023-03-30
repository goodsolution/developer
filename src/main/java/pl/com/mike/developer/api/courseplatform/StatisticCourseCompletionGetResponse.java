package pl.com.mike.developer.api.courseplatform;

import pl.com.mike.developer.domain.courseplatform.StatisticCourseCompletionData;

import java.math.BigDecimal;

public class StatisticCourseCompletionGetResponse {
    private String login;
    private String courseTitle;
    private BigDecimal percentOfCompletion;

    public StatisticCourseCompletionGetResponse(StatisticCourseCompletionData statisticCourseCompletionData) {
        this.login = statisticCourseCompletionData.getLogin();
        this.courseTitle = statisticCourseCompletionData.getCourseTitle();
        this.percentOfCompletion = statisticCourseCompletionData.getPercentOfCompletion();
    }

    public String getLogin() {
        return login;
    }

    public String getCourseTitle() {
        return courseTitle;
    }

    public BigDecimal getPercentOfCompletion() {
        return percentOfCompletion;
    }
}
