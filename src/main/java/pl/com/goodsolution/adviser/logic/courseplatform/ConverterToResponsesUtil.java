package pl.com.goodsolution.adviser.logic.courseplatform;

import pl.com.goodsolution.adviser.api.courseplatform.*;
import pl.com.goodsolution.adviser.domain.JobOfferData;
import pl.com.goodsolution.adviser.domain.PeopleData;
import pl.com.goodsolution.adviser.domain.courseplatform.*;
import pl.com.goodsolution.adviser.domain.itube.ITubeData;
import pl.com.goodsolution.adviser.domain.itube.ITubeGetResponse;
import pl.com.goodsolution.adviser.domain.itube.ITubeGetResponseAdmin;
import pl.com.goodsolution.adviser.logic.Language;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static pl.com.goodsolution.adviser.logic.courseplatform.LanguagesUtil.getCurrentLanguage;

public class ConverterToResponsesUtil {
    private final static DateTimeFormatter DATE_TIME_FORMATTER_FULL = DateTimeFormatter.ofPattern("yyyy-LL-dd");


    public static List<MemeGetResponse> memesToResponses(List<MemeData> memes) {
        List<MemeGetResponse> list = new ArrayList<>();

        for (MemeData meme : memes) {
            list.add(new MemeGetResponse(meme));
        }

        return list;
    }

    public static List<CourseCommentGetResponseAdmin> courseCommentsToResponsesAdmin(List<CourseCommentData> courseComments) {
        List<CourseCommentGetResponseAdmin> list = new ArrayList<>();

        for (CourseCommentData courseComment : courseComments) {
            list.add(new CourseCommentGetResponseAdmin(courseComment));
        }

        return list;
    }

    public static List<LessonCommentGetResponseAdmin> lessonCommentsToResponsesAdmin(List<LessonCommentData> lessonComments) {
        List<LessonCommentGetResponseAdmin> list = new ArrayList<>();

        for (LessonCommentData lessonComment : lessonComments) {
            list.add(new LessonCommentGetResponseAdmin(lessonComment));
        }

        return list;
    }

    public static List<CourseCommentGetResponsePublic> courseCommentsToResponsesPublic(List<CourseCommentData> courseComments) {
        List<CourseCommentGetResponsePublic> list = new ArrayList<>();

        for (CourseCommentData courseComment : courseComments) {
            list.add(new CourseCommentGetResponsePublic(courseComment));
        }

        return list;
    }

    public static List<LessonCommentGetResponsePublic> lessonCommentsToResponsesPublic(List<LessonCommentData> lessonComments) {
        List<LessonCommentGetResponsePublic> list = new ArrayList<>();

        for (LessonCommentData lessonComment : lessonComments) {
            list.add(new LessonCommentGetResponsePublic(lessonComment));
        }

        return list;
    }

    public static List<CourseAttachmentGetResponse> courseAttachmentsToResponses(List<CourseAttachmentData> courseAttachments) {
        List<CourseAttachmentGetResponse> list = new ArrayList<>();

        for (CourseAttachmentData courseAttachment : courseAttachments) {
            list.add(new CourseAttachmentGetResponse(courseAttachment));
        }

        return list;
    }

    public static List<LessonGetResponse> lessonsToResponses(List<LessonData> lessons) {
        List<LessonGetResponse> list = new ArrayList<>();

        for (LessonData lesson : lessons) {
            list.add(new LessonGetResponse(lesson));
        }

        return list;
    }

    public static List<ModuleGetResponse> modulesToResponses(List<ModuleData> modules) {
        List<ModuleGetResponse> list = new ArrayList<>();

        for (ModuleData module : modules) {
            list.add(new ModuleGetResponse(module));
        }

        return list;
    }

    public static List<StatisticCourseCompletionGetResponse> courseCompletionToResponses(List<StatisticCourseCompletionData> courseCompletion) {
        List<StatisticCourseCompletionGetResponse> list = new ArrayList<>();
        for (StatisticCourseCompletionData course : courseCompletion) {
            list.add(new StatisticCourseCompletionGetResponse(course));
        }
        return list;
    }

    public static List<LessonAttachmentGetResponse> lessonAttachmentsToResponsesAdmin(List<LessonAttachmentData> lessonAttachments) {
        List<LessonAttachmentGetResponse> list = new ArrayList<>();

        for (LessonAttachmentData lessonAttachment : lessonAttachments) {
            list.add(new LessonAttachmentGetResponse(lessonAttachment));
        }

        return list;
    }

    public static List<JobOfferGetResponse> jobOffersToResponses(List<JobOfferData> jobOffers) {
        List<JobOfferGetResponse> list = new ArrayList<>();

        for (JobOfferData jobOffer : jobOffers) {
            list.add(new JobOfferGetResponse(jobOffer));
        }

        return list;
    }

    public static List<PeopleElementGetResponse> peopleToResponses(List<PeopleData> people) {
        List<PeopleElementGetResponse> list = new ArrayList<>();

        for (PeopleData peopleData : people) {
            list.add(new PeopleElementGetResponse(peopleData));
        }

        return list;
    }

    public static List<ITubeGetResponse> iTubesToResponses(List<ITubeData> iTubes) {
        List<ITubeGetResponse> list = new ArrayList<>();
        Language currentLanguage = getCurrentLanguage();
        if (currentLanguage.getCode().equals("pl_PL")) {
            for (ITubeData iTube : iTubes) {
                list.add(new ITubeGetResponse(iTube.getId(),
                        iTube.getTitlePl(),
                        iTube.getKeywords(),
                        iTube.getLink(),
                        iTube.getCreateDateTime().toString()
                ));
            }
        } else {
            for (ITubeData iTube : iTubes) {
                list.add(new ITubeGetResponse(iTube.getId(),
                        iTube.getTitleEn(),
                        iTube.getKeywords(),
                        iTube.getLink(),
                        iTube.getCreateDateTime().toString()
                ));
            }
        }
        return list;
    }

    public static List<ITubeGetResponseAdmin> iTubesToResponsesAdmin(List<ITubeData> iTubes) {
        List<ITubeGetResponseAdmin> list = new ArrayList<>();
        for (ITubeData iTube : iTubes) {
            list.add(new ITubeGetResponseAdmin(
                    iTube.getId(),
                    iTube.getTitlePl(),
                    iTube.getTitleEn(),
                    iTube.getKeywords(),
                    iTube.getLink(),
                    iTube.getCreateDateTime().toString()
            ));
        }

        return list;
    }

    public static List<StatisticNewCustomerGetResponse> statisticNewCustomersToResponses(List<StatisticNewCustomerData> statisticNewCustomers) {
        List<StatisticNewCustomerGetResponse> list = new ArrayList<>();
        statisticNewCustomers.forEach(element -> list.add(new StatisticNewCustomerGetResponse(element)));
        return list;
    }

    public static List<TracePerDayGetResponse> traceToResponses(List<TraceData> traceData) {
        List<TracePerDayGetResponse> list = new ArrayList<>();
        traceData.forEach(element -> list.add(
                        new TracePerDayGetResponse(
                                new TraceData(
                                    element.getId(),
                                    element.getWhat(),
                                    element.getValue(),
                                    element.getLocalDateTime()
                                ),
                                DATE_TIME_FORMATTER_FULL
                        )
                )
        );
        return list;
    }
}
