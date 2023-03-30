package pl.com.mike.developer.api.courseplatform;

import java.util.List;

public class CourseCommentsRequestGetResponsePublic {
    private List<CourseCommentGetResponsePublic> courseComments;

    public CourseCommentsRequestGetResponsePublic(List<CourseCommentGetResponsePublic> courseComments) {
        this.courseComments = courseComments;
    }

    public List<CourseCommentGetResponsePublic> getCourseComments() {
        return courseComments;
    }
}
