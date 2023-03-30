package pl.com.mike.developer.logic.courseplatform;

public class ModulesFilter {

    private Long id;
    private Long courseId;
    private Boolean deleted;
    private ModuleVisibilityStatus visibilityStatus;

    public ModulesFilter(Long id) {
        this.id = id;
    }

    public ModulesFilter(Long courseId, Boolean deleted) {
        this.courseId = courseId;
        this.deleted = deleted;
    }

    public ModulesFilter(Long courseId, Boolean deleted, ModuleVisibilityStatus visibilityStatus) {
        this.courseId = courseId;
        this.deleted = deleted;
        this.visibilityStatus = visibilityStatus;
    }

    public Long getId() {
        return id;
    }

    public Long getCourseId() {
        return courseId;
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public ModuleVisibilityStatus getVisibilityStatus() {
        return visibilityStatus;
    }
}
