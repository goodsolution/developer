package pl.com.goodsolution.adviser.logic.adviser;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import pl.com.goodsolution.adviser.domain.adviser.*;
import pl.com.goodsolution.adviser.logic.CommonJdbcRepository;
import pl.com.goodsolution.adviser.logic.adviser.evaluator.*;

import java.util.List;
import java.util.Map;

@Service
@Transactional
public class AdviserService {
    private static final Logger log = LoggerFactory.getLogger(AdviserService.class);
    private AdviserJdbcRepository adviserJdbcRepository;
    private ApplicationsService applicationsService;
    private ContextVariablesService contextVariablesService;
    private CommonJdbcRepository commonJdbcRepository;
    private Evaluator evaluator;
    private AdviserVariablesService adviserVariablesService;

    public AdviserService(AdviserJdbcRepository adviserJdbcRepository, ApplicationsService applicationsService, ContextVariablesService contextVariablesService, CommonJdbcRepository commonJdbcRepository, Evaluator evaluator, AdviserVariablesService adviserVariablesService) {
        this.adviserJdbcRepository = adviserJdbcRepository;
        this.applicationsService = applicationsService;
        this.contextVariablesService = contextVariablesService;
        this.commonJdbcRepository = commonJdbcRepository;
        this.evaluator = evaluator;
        this.adviserVariablesService = adviserVariablesService;
    }

    public void createAdvice(AdviceData data) {
        validateAdvice(data);
        adviserJdbcRepository.createAdvice(data);
    }

    public AdviceData getAdvice(Long id) {
        return adviserJdbcRepository.getAdvice(id);
    }

    public List<AdviceData> findAdvices(AdvicesFilter filer) {
        return adviserJdbcRepository.findAdvices(filer);
    }

    public List<AdviceData> findAdvicesForProcessing(AdvicesForProcessingFilter filer) {
        return adviserJdbcRepository.findAdvicesForProcessing(filer);
    }

    public void updateAdvice(AdviceData data) {
        validateAdvice(data);
        adviserJdbcRepository.updateAdvice(data);
    }

    public void deleteAdvice(Long id) {
        adviserJdbcRepository.deleteAdvice(id);
    }

    public Long createTriggeredAdvice(TriggeredAdviceData data) {
        adviserJdbcRepository.createTriggeredAdvice(data);
        return commonJdbcRepository.getLastInsertedId();
    }

    public TriggeredAdviceData getTriggeredAdvice(Long id) {
        return adviserJdbcRepository.getTriggeredAdvice(id);
    }

    public List<TriggeredAdviceData> findTriggeredAdvices(TriggeredAdvicesFilter filter) {
        return adviserJdbcRepository.findTriggeredAdvices(filter);
    }

    public void updateTriggeredAdvice(TriggeredAdviceData data) {
        adviserJdbcRepository.updateTriggeredAdvice(data);
    }

    public void deleteTriggeredAdvice(Long id) {
        adviserJdbcRepository.deleteTriggeredAdvice(id);
    }

    public Long getTriggeredAdvicesCount() {
        return adviserJdbcRepository.getTriggeredAdvicesCount();
    }

    public Long getAdvicesCount() {
        return adviserJdbcRepository.getAdvicesCount();
    }

    public AdviceReturnResponseData storeResponse(AdviceResponseData data) {
        TriggeredAdviceData triggeredAdvice = getTriggeredAdvice(data.getTriggeredAdviceId());
        TriggeredAdviceData triggeredAdviceToUpdate = TriggeredAdviceData.of(triggeredAdvice, data.getStatus(), data.getAnswer(), data.getAdviseDataType());
        AdviceData advice = getAdvice(triggeredAdvice.getAdviceId());
        handleNoAdvise(triggeredAdvice, advice);
        updateTriggeredAdvice(triggeredAdviceToUpdate);
        if (data.getAnswer() != null) {
            createOrUpdateContextVar(new ContextVariableData(triggeredAdvice.getAppId(), triggeredAdvice.getContext(), advice.getVariableName(), data.getAdviseDataType(), data.getAnswer(), null, Long.valueOf(data.getContext().getCustomerId())));
        }
        if (advice.getResponseExecutionCondition() != null && !advice.getResponseExecutionCondition().isEmpty()) {
            Map<String, VariableValue> vars = adviserVariablesService.findVars(new AdviceVariablesFilter(triggeredAdvice.getCustomerId()));
            vars.forEach((k,v) -> log.debug(k + ": " + v.getValue()));
            Context context = new Context(vars);

            if (evaluator.evaluate(context, new Expression(advice.getResponseExecutionCondition()))) {
                return new AdviceReturnResponseData(advice.getResponseTrueContent());
            } else {
                return new AdviceReturnResponseData(advice.getResponseElseContent());
            }
        }
        return new AdviceReturnResponseData(null);
    }

    private void handleNoAdvise(TriggeredAdviceData triggeredAdvice, AdviceData advice) {
        if (advice == null) {
            throw new IllegalStateException("no advice for id: " + triggeredAdvice.getAdviceId());
        }
    }

    private void createOrUpdateContextVar(ContextVariableData data) {
        List<ContextVariableData> list = contextVariablesService.find(new ContextVariablesFilter(null, null, data.getApplicationId(), data.getName(), data.getContext(), null, null, data.getCustomerId()));
        if (list == null || list.isEmpty()) {
            contextVariablesService.create(data);
        } else {
            ContextVariableData contextVariableData = list.get(0);
            contextVariablesService.update(new ContextVariableData(
                    contextVariableData.getId(),
                    contextVariableData.getApplicationId(),
                    contextVariableData.getContext(),
                    contextVariableData.getName(),
                    contextVariableData.getType(),
                    data.getValue(),
                    contextVariableData.getContextId(),
                    contextVariableData.getCustomerId()
                    ));
        }
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void log(TriggeredAdviceLogData data) {
        adviserJdbcRepository.log(data);
    }

    public void changeAdviceStatusWithValidation(AdviceData advice, AdviceStatus status) {
        validateAdviceStatus(status, advice.getStatus());
        changeAdviceStatus(advice, status);
    }

    public void validateApplication(AdviseTriggerData data) {
        applicationsService.validateApplication(data.getAppId(), data.getSecret());
    }

    public boolean validateCategory(AdviceData data) {
        //TODO check if category used/purchased or general
        return true;
    }

    private void validateAdviceStatus(AdviceStatus status, AdviceStatus old) {
//        if (AdviceStatus.ACTIVE.equals(old) && AdviceStatus.ONE_TIME_TRIGGERED.equals(status)) {
//            ;
//        } else {
//            throw new IllegalStateException("Status transition from: " + old + " to: " + status + " not allowed");
//        }
    }

    private void changeAdviceStatus(AdviceData advice, AdviceStatus status) {
//        AdviceData adviceToUpdate = new AdviceData(status, advice);
//        adviserJdbcRepository.updateAdvice(adviceToUpdate);
    }

    private void validateAdvice(AdviceData data) {
//        ValidationUtil.validateLength(data.getAppId(), 100, "Application ID");
//        ValidationUtil.validateLength(data.getDomain(), 100, "Domain");
//        ValidationUtil.validateLength(data.getType().code(), 1, "Type");
//        ValidationUtil.validateLength(data.getScope(), 100, "Scope");
//        ValidationUtil.validateLength(data.getAction(), 500, "Action");
//        ValidationUtil.validateLength(data.getDataType(), 100, "Data type");
//        ValidationUtil.validateLength(data.getComponent(), 100, "Component");
//        ValidationUtil.validateLength(data.getAdviceClass(), 500, "Class");
//        ValidationUtil.validateLength(data.getName(), 100, "Name");
//        ValidationUtil.validateLength(data.getContent(), 20000, "Content");
    }

}