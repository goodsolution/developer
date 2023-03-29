package pl.com.goodsolution.adviser.api.courseplatform;

import java.util.List;

public class LessonAttachmentsGetResponse {
    private List<LessonAttachmentGetResponse> lessonAttachments;

    public LessonAttachmentsGetResponse(List<LessonAttachmentGetResponse> lessonAttachments) {
        this.lessonAttachments = lessonAttachments;
    }

    public List<LessonAttachmentGetResponse> getLessonAttachments() {
        return lessonAttachments;
    }
}
