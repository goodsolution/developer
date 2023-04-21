package pl.com.mike.developer.logic.courseplatform;

import pl.com.mike.developer.domain.itube.ITubeData;
import pl.com.mike.developer.domain.itube.ITubeGetResponse;
import pl.com.mike.developer.domain.itube.ITubeGetResponseAdmin;
import pl.com.mike.developer.logic.Language;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class ConverterToResponsesUtil {
    private final static DateTimeFormatter DATE_TIME_FORMATTER_FULL = DateTimeFormatter.ofPattern("yyyy-LL-dd");


//    public static List<MemeGetResponse> memesToResponses(List<MemeData> memes) {
//        List<MemeGetResponse> list = new ArrayList<>();
//
//        for (MemeData meme : memes) {
//            list.add(new MemeGetResponse(meme));
//        }
//
//        return list;
//    }

//    public static List<StatisticCourseCompletionGetResponse> courseCompletionToResponses(List<StatisticCourseCompletionData> courseCompletion) {
//        List<StatisticCourseCompletionGetResponse> list = new ArrayList<>();
//        for (StatisticCourseCompletionData course : courseCompletion) {
//            list.add(new StatisticCourseCompletionGetResponse(course));
//        }
//        return list;
//    }



//    public static List<JobOfferGetResponse> jobOffersToResponses(List<JobOfferData> jobOffers) {
//        List<JobOfferGetResponse> list = new ArrayList<>();
//
//        for (JobOfferData jobOffer : jobOffers) {
//            list.add(new JobOfferGetResponse(jobOffer));
//        }
//
//        return list;
//    }

    public static List<ITubeGetResponse> iTubesToResponses(List<ITubeData> iTubes) {
        List<ITubeGetResponse> list = new ArrayList<>();
        Language currentLanguage = LanguagesUtil.getCurrentLanguage();
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

//    public static List<StatisticNewCustomerGetResponse> statisticNewCustomersToResponses(List<StatisticNewCustomerData> statisticNewCustomers) {
//        List<StatisticNewCustomerGetResponse> list = new ArrayList<>();
//        statisticNewCustomers.forEach(element -> list.add(new StatisticNewCustomerGetResponse(element)));
//        return list;
//    }

//    public static List<TracePerDayGetResponse> traceToResponses(List<TraceData> traceData) {
//        List<TracePerDayGetResponse> list = new ArrayList<>();
//        traceData.forEach(element -> list.add(
//                        new TracePerDayGetResponse(
//                                new TraceData(
//                                    element.getId(),
//                                    element.getWhat(),
//                                    element.getValue(),
//                                    element.getLocalDateTime()
//                                ),
//                                DATE_TIME_FORMATTER_FULL
//                        )
//                )
//        );
//        return list;
//    }
}
