package pl.com.mike.developer.domain.courseplatform;

import pl.com.mike.developer.logic.courseplatform.LessonVisibilityStatus;

public class LessonsFilter {
    private Long id;
    private Long courseId;
    private LessonVisibilityStatus visibilityStatus;
    private Boolean withoutModule;
    private Long moduleId;
    private Boolean deleted;

    public LessonsFilter(Long id) {
        this.id = id;
    }

    public LessonsFilter(Long courseId, Boolean deleted) {
        this.courseId = courseId;
        this.deleted = deleted;
    }

    public LessonsFilter(Long courseId, Boolean withoutModule, Boolean deleted) {
        this.courseId = courseId;
        this.withoutModule = withoutModule;
        this.deleted = deleted;
    }

    public LessonsFilter(Long courseId, LessonVisibilityStatus visibilityStatus, Boolean withoutModule, Boolean deleted) {
        this.courseId = courseId;
        this.visibilityStatus = visibilityStatus;
        this.withoutModule = withoutModule;
        this.deleted = deleted;
    }

    public LessonsFilter(LessonVisibilityStatus visibilityStatus, Long moduleId, Boolean deleted) {
        this.visibilityStatus = visibilityStatus;
        this.moduleId = moduleId;
        this.deleted = deleted;
    }

    public LessonsFilter(Boolean deleted, Long moduleId) {
        this.moduleId = moduleId;
        this.deleted = deleted;
    }

    public Long getId() {
        return id;
    }

    public Long getCourseId() {
        return courseId;
    }

    public LessonVisibilityStatus getVisibilityStatus() {
        return visibilityStatus;
    }

    public Boolean getWithoutModule() {
        return withoutModule;
    }

    public Long getModuleId() {
        return moduleId;
    }

    public Boolean getDeleted() {
        return deleted;
    }
}
