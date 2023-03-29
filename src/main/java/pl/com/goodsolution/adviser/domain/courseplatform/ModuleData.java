package pl.com.goodsolution.adviser.domain.courseplatform;

import pl.com.goodsolution.adviser.logic.courseplatform.ModuleVisibilityStatus;

import java.time.LocalDateTime;

public class ModuleData {
    private Long id;
    private CourseData course;
    private String name;
    private Long orderNumber;
    private LocalDateTime deleteDatetime;
    private String visibilityStatus;

    private Long courseId;

    public ModuleData(Long id, String name, String visibilityStatus) {
        this.id = id;
        this.name = name;
        this.visibilityStatus = visibilityStatus;
    }

    public ModuleData(CourseData course, String name, Long orderNumber, ModuleVisibilityStatus visibilityStatus) {
        this.course = course;
        this.name = name;
        this.orderNumber = orderNumber;
        this.visibilityStatus = visibilityStatus.getValue();
    }

    public ModuleData(Long id, CourseData course, String name, Long orderNumber, LocalDateTime deleteDatetime, String visibilityStatus) {
        this.id = id;
        this.course = course;
        this.name = name;
        this.orderNumber = orderNumber;
        this.deleteDatetime = deleteDatetime;
        this.visibilityStatus = visibilityStatus;
    }

    public ModuleData(Long courseId, String name) {
        this.courseId = courseId;
        this.name = name;
    }

    public ModuleData(ModuleData data, Long orderNumber) {
        this.id = data.getId();
        this.course = data.getCourse();
        this.name = data.getName();
        this.orderNumber = orderNumber;
        this.deleteDatetime = data.getDeleteDatetime();
        this.visibilityStatus = data.getVisibilityStatus();
    }

    public ModuleData(ModuleData data, LocalDateTime deleteDatetime) {
        this.id = data.getId();
        this.course = data.getCourse();
        this.name = data.getName();
        this.orderNumber = data.getOrderNumber();
        this.deleteDatetime = deleteDatetime;
        this.visibilityStatus = data.getVisibilityStatus();
    }

    public Long getId() {
        return id;
    }

    public CourseData getCourse() {
        return course;
    }

    public String getName() {
        return name;
    }

    public Long getOrderNumber() {
        return orderNumber;
    }

    public LocalDateTime getDeleteDatetime() {
        return deleteDatetime;
    }

    public Long getCourseId() {
        return courseId;
    }

    public String getVisibilityStatus() {
        return visibilityStatus;
    }
}
