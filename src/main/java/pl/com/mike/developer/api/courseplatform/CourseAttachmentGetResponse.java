package pl.com.mike.developer.api.courseplatform;


import pl.com.mike.developer.domain.courseplatform.CourseAttachmentData;

public class CourseAttachmentGetResponse {

    private Long id;
    private CourseGetResponse course;
    private String name;
    private String fileName;
    private String originalFileName;

    public CourseAttachmentGetResponse(CourseAttachmentData data) {
        this.id = data.getId();
        this.course = new CourseGetResponse(data.getCourse());
        this.name = data.getName();
        this.fileName = data.getFileName();
        this.originalFileName = data.getOriginalFileName();
    }

    public Long getId() {
        return id;
    }

    public CourseGetResponse getCourse() {
        return course;
    }

    public String getName() {
        return name;
    }

    public String getFileName() {
        return fileName;
    }

    public String getOriginalFileName() {
        return originalFileName;
    }
}
