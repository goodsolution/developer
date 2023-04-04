package pl.com.mike.developer.config.courseplatform;

import com.github.kagkarlsson.scheduler.task.helper.OneTimeTask;
import com.github.kagkarlsson.scheduler.task.helper.Tasks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TaskConfig {

    @Bean
    public OneTimeTask<Void> myAdhocTask() {
        return Tasks.oneTime("my-typed-adhoc-task", Void.class)
                .execute((inst, ctx) -> {
                    System.out.println("Executed! Custom data, Id: ");
                });
    }

//    @Bean
//    public OneTimeTask<Void> myAdhocTask2() {
//        return Tasks.oneTime("my-typed-adhoc-task2", Void.class)
//                .execute((inst, ctx) -> {
//                    System.out.println("Executed! Custom data, Id:2 ");
//                    for (CustomerData customer : customersService.findCustomers(new CustomersFilter(Boolean.FALSE))) {
//                        System.out.println(customer.toString());
//                    }
//                    ;
//                });
//    }

//    @Bean
//    public RecurringTask<Void> hourlyTask() {
//            return Tasks.recurring("my-hourly-task", FixedDelay.ofSeconds(1))
//            .execute((inst, ctx) -> {
//                System.out.println("Executed reccuritn!");
//            });
//    }
}
