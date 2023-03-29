package pl.com.goodsolution.adviser.logic.courseplatform;

import com.github.kagkarlsson.scheduler.Scheduler;
import com.github.kagkarlsson.scheduler.task.helper.OneTimeTask;
import com.github.kagkarlsson.scheduler.task.helper.RecurringTask;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import pl.com.goodsolution.adviser.api.courseplatform.TracePerDayResponse;
import pl.com.goodsolution.adviser.domain.courseplatform.CustomerData;
import pl.com.goodsolution.adviser.domain.courseplatform.TraceData;

import java.time.Instant;
import java.time.Month;
import java.util.List;
import java.util.UUID;

@Service
public class TraceService {

    private TraceJdbcRepository traceJdbcRepository;
    private CourseCustomersService courseCustomersService;
    private Scheduler scheduler;
    @Qualifier("myAdhocTask")
    private OneTimeTask<Void> myAdhocTask;
    @Qualifier("myAdhocTask2")
    private OneTimeTask<Void> myAdhocTask2;

//    private RecurringTask<Void> hourlyTask;

    public TraceService(TraceJdbcRepository traceJdbcRepository, CourseCustomersService courseCustomersService, Scheduler scheduler, OneTimeTask<Void> myAdhocTask, OneTimeTask<Void> myAdhocTask2
//            , RecurringTask<Void> hourlyTask
    ) {
        this.traceJdbcRepository = traceJdbcRepository;
        this.courseCustomersService = courseCustomersService;
        this.scheduler = scheduler;
        this.myAdhocTask = myAdhocTask;
        this.myAdhocTask2 = myAdhocTask2;
//        this.hourlyTask = hourlyTask;


//        scheduler.schedule(this.myAdhocTask.instance(UUID.randomUUID().toString()), Instant.now().plusSeconds(5));
//        scheduler.schedule(this.myAdhocTask2.instance(UUID.randomUUID().toString()), Instant.now().plusSeconds(5));
//        scheduler.schedule(this.hourlyTask.instance(UUID.randomUUID().toString()), Instant.now());
    }

    public void trace(TraceData trace){
        TraceData preparedTrace = prepareTrace(trace);
        validateTrace(preparedTrace);
        traceJdbcRepository.create(preparedTrace);
    }

    public List<TraceData> findTracesPerDay(Month month) {
        return traceJdbcRepository.findTracesPerDay(month);
    }


    public List<TraceData> findTracesPerMonth(Long year) {
        return traceJdbcRepository.findTracesPerMonth(year);
    }

    private TraceData prepareTrace(TraceData trace) {
        return new TraceData(trace.getWhat(), trace.getValue(), getWho(trace.getWho()), trace.getIpAddress(), trace.getBrowser(), trace.getOperatingSystem());
    }

    private String getWho(String who) {
        if (who != null) {
            return who;
        } else {
            CustomerData loggedUser = courseCustomersService.getLoggedCustomer();
            if (loggedUser == null || loggedUser.getId() == null) {
                return null;
            } else {
                return String.valueOf(courseCustomersService.getLoggedCustomer().getId());
            }
        }
    }

    private void validateTrace(TraceData data){

        String ipAddress = data.getIpAddress();
        if(ipAddress != null) {
            if(ipAddress.length() > 50) {
                throw new IllegalArgumentException("IP Address to long, max 50 characters");
            }
        }

    }
}
