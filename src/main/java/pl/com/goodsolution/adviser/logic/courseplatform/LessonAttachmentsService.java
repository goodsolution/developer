package pl.com.goodsolution.adviser.logic.courseplatform;

import org.springframework.stereotype.Service;
import pl.com.goodsolution.adviser.domain.courseplatform.*;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class LessonAttachmentsService {

    private LessonAttachmentsJdbcRepository lessonAttachmentsJdbcRepository;
    private LessonsService lessonsService;
    private FilesService filesService;

    public LessonAttachmentsService(LessonAttachmentsJdbcRepository lessonAttachmentsJdbcRepository, LessonsService lessonsService, FilesService filesService) {
        this.lessonAttachmentsJdbcRepository = lessonAttachmentsJdbcRepository;
        this.lessonsService = lessonsService;
        this.filesService = filesService;
    }

    public void create(LessonAttachmentData data) {

        validate(data);

        LessonData lesson = lessonsService.get(data.getLessonId());
        Long fileId = filesService.create(data.getMultipartFile());
        FileData file = filesService.get(fileId);

        lessonAttachmentsJdbcRepository.create(new LessonAttachmentData(lesson, file, data.getName()));
    }

    public LessonAttachmentData get(Long id) {
        return lessonAttachmentsJdbcRepository.get(id);
    }

    public List<LessonAttachmentData> find(LessonAttachmentsFilter filter) {
        return lessonAttachmentsJdbcRepository.find(filter);
    }

    public void delete(Long id) {
        LessonAttachmentData data = get(id);
        lessonAttachmentsJdbcRepository.update(new LessonAttachmentData(data, LocalDateTime.now()));
    }

    public byte[] getFile(Long lessonAttachmentId) {
        LessonAttachmentData data = get(lessonAttachmentId);
        return filesService.getFile(data.getFile().getId());
    }

    private void validate(LessonAttachmentData data) {

        if(data.getName() == null || data.getName().equals("")) {
            throw new IllegalArgumentException("Name can't be empty");
        } else if (data.getName().length() > 300) {
            throw new IllegalArgumentException("Name too log, max 300 characters");
        }

    }

}
