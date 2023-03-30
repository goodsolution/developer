package pl.com.mike.developer.domain.courseplatform;

import pl.com.mike.developer.logic.courseplatform.CourseVisibilityStatus;

public class CoursesFilter {

    private Long id;
    private String title;
    private Boolean isDeleted;
    private String visibilityStatus;
    private Long page;
    private Long pageSize;
    private Long authorId;
    private String code;
    private String type;

    public CoursesFilter(String title, Boolean isDeleted, CourseVisibilityStatus visibilityStatus, String type) {
        this.title = title;
        this.isDeleted = isDeleted;
        this.visibilityStatus = visibilityStatus.getValue();
        this.type = type;
    }

    public CoursesFilter(Boolean isDeleted, CourseVisibilityStatus visibilityStatus, String type) {
        this.isDeleted = isDeleted;
        this.visibilityStatus = visibilityStatus.getValue();
        this.type = type;
    }

    public CoursesFilter(Long id) {
        this.id = id;
    }

    public CoursesFilter(Boolean isDeleted, Long page, Long pageSize, String type) {
        this.isDeleted = isDeleted;
        this.page = page;
        this.pageSize = pageSize;
        this.type = type;
    }

    public CoursesFilter(Boolean isDeleted, Long page, Long pageSize, String type, CourseVisibilityStatus visibilityStatus,  Long authorId){
        this.isDeleted = isDeleted;
        this.page = page;
        this.pageSize = pageSize;
        this.type = type;
        if (visibilityStatus == null) {
            this.visibilityStatus = null;
        } else {
            this.visibilityStatus = visibilityStatus.getValue();
        }
        this.authorId=authorId;
    }

    public CoursesFilter(CoursesFilter filter, Long authorId) {
        this.id = filter.getId();
        this.title = filter.getTitle();
        this.isDeleted = filter.getDeleted();
        this.visibilityStatus = filter.getVisibilityStatus();
        this.page = filter.getPage();
        this.pageSize = filter.getPageSize();
        this.authorId = authorId;
    }

    public CoursesFilter(String code) {
        this.code = code;
    }

    public String getTitle() {
        return title;
    }

    public Long getId() {
        return id;
    }

    public Boolean getDeleted() {
        return isDeleted;
    }

    public String getVisibilityStatus() {
        return visibilityStatus;
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

    public String getCode() {
        return code;
    }

    public String getType() {
        return type;
    }
}
