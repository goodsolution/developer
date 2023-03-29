package pl.com.goodsolution.adviser.domain.courseplatform;

import java.math.BigDecimal;

public class StatisticCourseCompletionData {
    private String login;
    private String courseTitle;
    private BigDecimal percentOfCompletion;

    public StatisticCourseCompletionData(String login, String courseTitle, BigDecimal percentOfCompletion) {
        this.login = login;
        this.courseTitle = courseTitle;
        this.percentOfCompletion = percentOfCompletion;
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
