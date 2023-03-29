package pl.com.goodsolution.adviser.logic.courseplatform;

import org.springframework.stereotype.Service;
import pl.com.goodsolution.adviser.domain.courseplatform.*;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CourseAttachmentsService {

    private CoursesService coursesService;
    private FilesService filesService;
    private CourseAttachmentsJdbcRepository courseAttachmentsJdbcRepository;

    public CourseAttachmentsService(CoursesService coursesService, FilesService filesService, CourseAttachmentsJdbcRepository courseAttachmentsJdbcRepository) {
        this.coursesService = coursesService;
        this.filesService = filesService;
        this.courseAttachmentsJdbcRepository = courseAttachmentsJdbcRepository;
    }

    public void create(CourseAttachmentData data) {

        validate(data);

        String fileName = filesService.saveCourseAttachmentOnServer(data.getFile());
        CourseData course = coursesService.find(new CoursesFilter(data.getCourseId())).get(0);
        String name = data.getName();
        String originalFileName = data.getFile().getOriginalFilename();

        CourseAttachmentData courseAttachment = new CourseAttachmentData(course, name, fileName, originalFileName);
        courseAttachmentsJdbcRepository.create(courseAttachment);
    }

    public List<CourseAttachmentData> findForCourse(Long courseId) {
        return courseAttachmentsJdbcRepository.find(new CourseAttachmentsFilter(courseId, false));
    }

    public void delete(Long id) {
        CourseAttachmentData dataToDelete = courseAttachmentsJdbcRepository.find(new CourseAttachmentsFilter(id)).get(0);
        courseAttachmentsJdbcRepository.update(new CourseAttachmentData(dataToDelete, LocalDateTime.now()));
    }

    public byte[] getFile(Long id) {
        CourseAttachmentData data = courseAttachmentsJdbcRepository.find(new CourseAttachmentsFilter(id)).get(0);
        return filesService.getCourseAttachment(data.getFileName());
    }

    public void duplicateCourseAttachments(Long fromCourseId, Long toCourseId) {
        List<CourseAttachmentData> courseAttachments = courseAttachmentsJdbcRepository.find(new CourseAttachmentsFilter(fromCourseId, false));
        CourseData toCourse = coursesService.get(toCourseId);

        for (CourseAttachmentData courseAttachment : courseAttachments) {
            String fileName = filesService.duplicateCourseAttachment(courseAttachment.getFileName());
            courseAttachmentsJdbcRepository.create(new CourseAttachmentData(toCourse, courseAttachment.getName(), fileName, courseAttachment.getOriginalFileName()));
        }
    }

    private void validate(CourseAttachmentData data) {

        if(data.getName() == null || data.getName().equals("")) {
            throw new IllegalArgumentException("Name can't be empty");
        } else if (data.getName().length() > 300) {
            throw new IllegalArgumentException("Name too log, max 300 characters");
        }

        if(data.getFile() == null || data.getFile().isEmpty()) {
            throw new IllegalArgumentException("You must choose file");
        }

        if(data.getFile().getOriginalFilename() == null || data.getFile().getOriginalFilename().equals("")) {
            throw new IllegalArgumentException("Original file name can't be empty");
        } else if(data.getFile().getOriginalFilename().length() > 500) {
            throw new IllegalArgumentException("Original file name too long, max 500 characters");
        } else if (data.getFile().getOriginalFilename().length() < 5) {
            throw new IllegalArgumentException("Original file name is too short, min 5 characters");
        }
    }
}
