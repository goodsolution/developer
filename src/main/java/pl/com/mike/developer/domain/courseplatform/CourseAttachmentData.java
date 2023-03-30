package pl.com.mike.developer.domain.courseplatform;

import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

public class CourseAttachmentData {

    private Long id;
    private CourseData course;
    private String name;
    private String fileName;
    private LocalDateTime deleteDatetime;
    private String originalFileName;


    private Long courseId;
    private MultipartFile file;

    public CourseAttachmentData(String name, Long courseId, MultipartFile file) {
        this.name = name;
        this.courseId = courseId;
        this.file = file;
    }

    public CourseAttachmentData(CourseData course, String name, String fileName, String originalFileName) {
        this.course = course;
        this.name = name;
        this.fileName = fileName;
        this.originalFileName = originalFileName;
    }

    public CourseAttachmentData(Long id, CourseData course, String name, String fileName, LocalDateTime deleteDatetime, String originalFileName) {
        this.id = id;
        this.course = course;
        this.name = name;
        this.fileName = fileName;
        this.deleteDatetime = deleteDatetime;
        this.originalFileName = originalFileName;
    }

    public CourseAttachmentData(CourseAttachmentData data, LocalDateTime deleteDatetime) {
        this.id = data.getId();
        this.course = data.getCourse();
        this.name = data.getName();
        this.fileName = data.getFileName();
        this.deleteDatetime = deleteDatetime;
        this.originalFileName = data.getOriginalFileName();
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

    public String getFileName() {
        return fileName;
    }

    public LocalDateTime getDeleteDatetime() {
        return deleteDatetime;
    }

    public Long getCourseId() {
        return courseId;
    }

    public MultipartFile getFile() {
        return file;
    }

    public String getOriginalFileName() {
        return originalFileName;
    }
}
