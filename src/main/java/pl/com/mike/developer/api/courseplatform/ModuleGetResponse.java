package pl.com.mike.developer.api.courseplatform;

import pl.com.mike.developer.domain.courseplatform.ModuleData;

import java.util.List;

public class ModuleGetResponse {
    private Long id;
    private String name;
    private String visibilityStatus;
    private CourseGetResponse course;

    private List<LessonGetResponse> lessons;

    public ModuleGetResponse(ModuleData data) {
        this.id = data.getId();
        this.name = data.getName();
        this.visibilityStatus = data.getVisibilityStatus();
        this.course = new CourseGetResponse(data.getCourse());
    }

    public ModuleGetResponse(ModuleData data, List<LessonGetResponse> lessons) {
        this.id = data.getId();
        this.name = data.getName();
        this.visibilityStatus = data.getVisibilityStatus();
        this.lessons = lessons;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getVisibilityStatus() {
        return visibilityStatus;
    }

    public List<LessonGetResponse> getLessons() {
        return lessons;
    }

    public CourseGetResponse getCourse() {
        return course;
    }
}
