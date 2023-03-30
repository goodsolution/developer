package pl.com.mike.developer.domain.courseplatform;

import pl.com.mike.developer.logic.courseplatform.LessonCommentStatus;

public class LessonCommentsFilter {

    private Long id;
    private Long lessonId;
    private Boolean deleted;
    private LessonCommentStatus status;
    private Long page;
    private Long pageSize;
    private Long authorId;

    public LessonCommentsFilter(Long lessonId, Boolean deleted, LessonCommentStatus status) {
        this.lessonId = lessonId;
        this.deleted = deleted;
        this.status = status;
    }

    public LessonCommentsFilter(Boolean deleted, Long page, Long pageSize) {
        this.deleted = deleted;
        this.page = page;
        this.pageSize = pageSize;
    }

    public LessonCommentsFilter(Boolean deleted, Long page, Long pageSize, Long authorId) {
        this.deleted = deleted;
        this.page = page;
        this.pageSize = pageSize;
        this.authorId = authorId;
    }

    public LessonCommentsFilter(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public Long getLessonId() {
        return lessonId;
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public LessonCommentStatus getStatus() {
        return status;
    }

    public Long getPage() {
        return page;
    }

    public Long getPageSize() {
        return pageSize;
    }

    public Long getAuthorId() {
        return authorId;
    }
}
