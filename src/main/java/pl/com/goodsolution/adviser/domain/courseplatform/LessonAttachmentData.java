package pl.com.goodsolution.adviser.domain.courseplatform;

import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

public class LessonAttachmentData {

    private Long id;
    private LessonData lesson;
    private FileData file;
    private String name;
    private LocalDateTime deleteDatetime;

    private MultipartFile multipartFile;
    private Long lessonId;


    public LessonAttachmentData(Long id, LessonData lesson, FileData file, String name, LocalDateTime deleteDatetime) {
        this.id = id;
        this.lesson = lesson;
        this.file = file;
        this.name = name;
        this.deleteDatetime = deleteDatetime;
    }

    public LessonAttachmentData(MultipartFile multipartFile, Long lessonId, String name) {
        this.multipartFile = multipartFile;
        this.lessonId = lessonId;
        this.name = name;
    }

    public LessonAttachmentData(LessonData lesson, FileData file, String name) {
        this.lesson = lesson;
        this.file = file;
        this.name = name;
    }

    public LessonAttachmentData(LessonAttachmentData data, LocalDateTime deleteDatetime) {
        this.id = data.getId();
        this.lesson = data.getLesson();
        this.file = data.getFile();
        this.name = data.getName();
        this.deleteDatetime = deleteDatetime;
    }

    public Long getId() {
        return id;
    }

    public LessonData getLesson() {
        return lesson;
    }

    public FileData getFile() {
        return file;
    }

    public String getName() {
        return name;
    }

    public LocalDateTime getDeleteDatetime() {
        return deleteDatetime;
    }

    public MultipartFile getMultipartFile() {
        return multipartFile;
    }

    public Long getLessonId() {
        return lessonId;
    }
}
