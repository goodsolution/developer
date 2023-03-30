package pl.com.mike.developer.api.courseplatform;


import pl.com.mike.developer.domain.courseplatform.LessonAttachmentData;

public class LessonAttachmentGetResponse {
    private Long id;
    private LessonGetResponse lesson;
    private FileGetResponse file;
    private String name;

    private String originalFileName;

    public LessonAttachmentGetResponse(LessonAttachmentData data) {
        this.id = data.getId();
        this.lesson = new LessonGetResponse(data.getLesson());
        this.file = new FileGetResponse(data.getFile());
        this.name = data.getName();
    }

    public LessonAttachmentGetResponse(Long id, String name, String originalFileName) {
        this.id = id;
        this.name = name;
        this.originalFileName = originalFileName;
    }

    public Long getId() {
        return id;
    }

    public LessonGetResponse getLesson() {
        return lesson;
    }

    public FileGetResponse getFile() {
        return file;
    }

    public String getName() {
        return name;
    }

    public String getOriginalFileName() {
        return originalFileName;
    }
}
