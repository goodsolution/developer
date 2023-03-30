package pl.com.mike.developer.api.courseplatform;

import java.util.List;

public class CourseCommentsRequestGetResponseAdmin {
    private List<CourseCommentGetResponseAdmin> courseComments;

    public CourseCommentsRequestGetResponseAdmin(List<CourseCommentGetResponseAdmin> courseComments) {
        this.courseComments = courseComments;
    }

    public List<CourseCommentGetResponseAdmin> getCourseComments() {
        return courseComments;
    }
}
