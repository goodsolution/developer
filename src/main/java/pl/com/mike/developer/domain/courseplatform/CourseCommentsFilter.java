package pl.com.mike.developer.domain.courseplatform;


import pl.com.mike.developer.logic.courseplatform.CourseCommentVisibilityStatus;

public class CourseCommentsFilter {
    private Long id;
    private Boolean deleted;
    private CourseCommentVisibilityStatus visibilityStatus;
    private Long page;
    private Long pageSize;
    private Long courseId;

    private Long authorId;

    public CourseCommentsFilter(Boolean deleted, Long page, Long pageSize) {
        this.deleted = deleted;
        this.page = page;
        this.pageSize = pageSize;
    }

    public CourseCommentsFilter(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public CourseCommentsFilter(Boolean deleted, CourseCommentVisibilityStatus visibilityStatus, Long courseId) {
        this.deleted = deleted;
        this.visibilityStatus = visibilityStatus;
        this.courseId = courseId;
    }

    public CourseCommentsFilter(Boolean deleted, Long page, Long pageSize, Long authorId) {
        this.deleted = deleted;
        this.page = page;
        this.pageSize = pageSize;
        this.authorId = authorId;
    }



    public Boolean getDeleted() {
        return deleted;
    }

    public CourseCommentVisibilityStatus getVisibilityStatus() {
        return visibilityStatus;
    }

    public Long getPage() {
        return page;
    }

    public Long getPageSize() {
        return pageSize;
    }

    public Long getCourseId() {
        return courseId;
    }

    public Long getAuthorId() {
        return authorId;
    }
}
