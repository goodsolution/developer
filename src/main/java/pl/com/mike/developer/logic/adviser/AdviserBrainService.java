package pl.com.mike.developer.logic.adviser;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import pl.com.mike.developer.domain.adviser.*;
import pl.com.mike.developer.domain.courseplatform.CustomerData;
import pl.com.mike.developer.domain.courseplatform.CustomersFilter;
import pl.com.mike.developer.logic.adviser.evaluator.Context;
import pl.com.mike.developer.logic.adviser.evaluator.Evaluator;
import pl.com.mike.developer.logic.adviser.evaluator.Expression;
import pl.com.mike.developer.logic.adviser.evaluator.VariableValue;
import pl.com.mike.developer.logic.courseplatform.CourseCustomersService;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Service
public class AdviserBrainService {
    private static final Logger log = LoggerFactory.getLogger(AdviserBrainService.class);
    private AdviserService adviserService;
    private AdviceCategoriesService adviceCategoriesService;
    private CourseCustomersService customersService;
    private ApplicationContext ctx;
    private Evaluator evaluator;
    private AdviserVariablesService adviserVariablesService;


    public AdviserBrainService(AdviserService adviserService, AdviceCategoriesService adviceCategoriesService, CourseCustomersService customersService, ApplicationContext ctx, Evaluator evaluator, AdviserVariablesService adviserVariablesService) {
        this.adviserService = adviserService;
        this.adviceCategoriesService = adviceCategoriesService;
        this.customersService = customersService;
        this.ctx = ctx;
        this.evaluator = evaluator;
        this.adviserVariablesService = adviserVariablesService;
    }



    public TriggeredAdviceData pullAdvise(AdvisePullData data) {

        List<TriggeredAdviceData> triggeredAdvices = adviserService.findTriggeredAdvices(
                new TriggeredAdvicesFilter(data.getCustomerId(), TriggeredAdviceStatus.TRIGGERED, data.getAppId(), data.getContext(),
                        new JdbcUtil.SortColumns(Arrays.asList(new JdbcUtil.SortColumn("id")))
                )
        );

        return triggeredAdvices.isEmpty() ? null : triggeredAdvices.get(0);

//        // pull the oldest one and mark as pulled/remove
//        List<AdviceData> list = triggeredAdvices.get(data.getId());
//        if (list != null || !list.isEmpty()) {
//            AdviceData res = list.get(0);
//            list.remove(0);
//            return res;
//        }
//
//        Long id = System.currentTimeMillis();
//        String appId = data.getAppId();
//        String context = data.getContext();
//        String content = "Czy wiesz że jest pięknie?";
//        AdviseType type = AdviseType.SIMPLE_MESSAGE;
//        String title = "Tytulik tytuliczek";
//        String variableName = "customer.douYouKnowIsBeautiful";
//        String executionCondition = "true";
//        String comboJson = null;
//        AdviceStatus status = AdviceStatus.ACTIVE;
//        Long triggeredAdviceId = System.currentTimeMillis();
//        //------------
//        Long id = System.currentTimeMillis();
//        String appId = data.getAppId();
//        String context = data.getContext();
//        String content = "Jak masz na imie?";
//        AdviseType type = AdviseType.QUESTION_TEXT;
//        String title = "Pytanie";
//        String variableName = "customer.douYouKnowIsBeautiful";
//        String executionCondition = "true";
//        String comboJson = null;
//        AdviceStatus status = AdviceStatus.ACTIVE;
//        Long triggeredAdviceId = System.currentTimeMillis();
        //----------------
//        Long id = System.currentTimeMillis();
//        String appId = data.getAppId();
//        String context = data.getContext();
//        String content = "Jaka jest Twoja ulubiona pora roku?";
//        AdviseType type = AdviseType.QUESTION_COMBO;
//        String title = "Pytanie";
//        String variableName = "customer.favouriteSeason";
//        String executionCondition = "true";
//        String comboJson = null;
//        AdviceStatus status = AdviceStatus.ACTIVE;
//        Long triggeredAdviceId = System.currentTimeMillis();
//        return new AdviceData(id, appId, context, content, type, title, variableName, executionCondition, comboJson, status, triggeredAdviceId); //new AdviceData("Error");
    }

    public TriggeredAdviceData triggerAdvises(AdviseTriggerData data) {
        adviserService.validateApplication(data);

        List<AdviceData> advicesInContext = adviserService.findAdvices(
                new AdvicesFilter(data.getAppId(), data.getDomain(), null, null, LocalDateTime.now(), LocalDateTime.now(), AdviceStatus.ACTIVE)); //TODO scope, action, only one advise
        if (advicesInContext != null) {
            for (AdviceData adviceData : advicesInContext) {
                boolean process = adviserService.validateCategory(adviceData);
                if (!process) {
                    continue;
                }
                process = startProcessing(adviceData);
                if (process) {
                    Long triggeredAdviceId = null;
                    try {
                        Advice advice = prepareAdvice(adviceData);
                        TriggeredResult res = advice.triggered(new AdviceContext(data.getId(), prepareDataSource(data), adviceData));
                        if (res.wasTriggered()) {
                            TriggeredAdviceData triggeredAdviceData = storeTriggeredAdvice(null /*new AdviceData(res.getContent(), adviceData)*/, data.getId());
                            triggeredAdviceId = triggeredAdviceData.getId();

                            finishProcessing(adviceData);
                            return triggeredAdviceData;
                        }
                    } catch (Exception ex) {
                        adviserService.log(null/*new TriggeredAdviceLogData(adviceData.getId(), triggeredAdviceId, adviceData.getAppId(), adviceData.getDomain(), ex.getMessage())*/);
                        return null;
                    }
                }
            }
        }
        return null;
    }

    private void finishProcessing(AdviceData advice) {
//        if (AdvicePeriod.ONE_TIME.equals(advice.getPeriod())) {
//
//
//        }
    }

    private boolean startProcessing(AdviceData advice) {
//        if (AdvicePeriod.ONE_TIME.equals(advice.getPeriod())) {
//            // TODO check if was triggered ealier !!!!!!!!!!!!
//        }
        return true;
    }

    private Advice prepareAdvice(AdviceData advice) {
        return null;
                //(Advice) ctx.getBean(advice.getAdviceClass());
    }

    private AdviseDataSource prepareDataSource(AdviseTriggerData data) {


        AdviseDataSource dataSource = new AdviseDataSource();
        if ("user".equals(data.getDomain())) {

            dataSource.add(data.getDomain(), "login", "pkbiker" /* to get from db*/);
            // ...
        }
        return dataSource;
    }

    private TriggeredAdviceData storeTriggeredAdvice(AdviceData advice, String domainId) {
        TriggeredAdviceData data = null;// new TriggeredAdviceData(
//                advice.getId(), // adviceId,
//                advice.getAppId(), ////  appId,
//                advice.getDomain(), ////  domain,
//                domainId, ////  domainId,
//                advice.getContent(), ////  content,
//                advice.getType().code(), ////  type,
//                null, ////  lang,
//                advice.getScope(), ////  scope,
//                advice.getAction(), ////  action,
//                advice.getDataType(), ////  dataType,
//                advice.getComponent(), ////  component,
//                0L, ////  score,
//                "N", ////  status
//                LocalDateTime.now(),
//                advice.getName(),
//                advice.getAnswerOptions()
//        );
        Long id = adviserService.createTriggeredAdvice(data);
        return new TriggeredAdviceData(id, data);
    }

//    @Scheduled(fixedDelay = 30 * 1000)
    public void scheduleTrigger() {
        log.debug("-- Adviser trigger --");
        process();
    }

    private void process() {
        List<CustomerData> customers = customersService.find(new CustomersFilter(true));
        for (CustomerData customer : customers) {
            log.debug("--> Customer: " + customer);

            Map<String, VariableValue> vars = adviserVariablesService.findVars(new AdviceVariablesFilter(customer.getId()));
            vars.forEach((k,v) -> log.debug(k + ": " + v.getValue()));
            Context context = new Context(vars);

            List<AdviceData> advices = adviserService.findAdvicesForProcessing(new AdvicesForProcessingFilter(AdviceStatus.ACTIVE, customer.getId()));
            for (AdviceData advice : advices) {
                log.debug("---> Advice: " + advice);
                if (evaluator.evaluate(context, new Expression(advice.getExecutionCondition()))) {
                    List<TriggeredAdviceData> triggeredAdvises = adviserService.findTriggeredAdvices(new TriggeredAdvicesFilter(advice.getId()));
                    if (triggeredAdvises == null || triggeredAdvises.isEmpty()) {
                        adviserService.createTriggeredAdvice(TriggeredAdviceData.of(advice, customer.getId()));
                    }
                } else {
                    log.debug("advise not evaluated");
                }
            }

        }
    }
}
