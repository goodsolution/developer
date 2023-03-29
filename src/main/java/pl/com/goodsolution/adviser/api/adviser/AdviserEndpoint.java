package pl.com.goodsolution.adviser.api.adviser;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import pl.com.goodsolution.adviser.api.lifeadviser.MyCategoriesGetResponse;
import pl.com.goodsolution.adviser.api.lifeadviser.PurchasePostRequest;
import pl.com.goodsolution.adviser.api.lifeadviser.PurchasePostResponse;
import pl.com.goodsolution.adviser.domain.DictionaryData;
import pl.com.goodsolution.adviser.domain.adviser.*;
import pl.com.goodsolution.adviser.domain.courseplatform.CustomerData;
import pl.com.goodsolution.adviser.exceptions.DataNotFoundException;
import pl.com.goodsolution.adviser.logic.adviser.*;
import pl.com.goodsolution.adviser.logic.DictionariesService;
import pl.com.goodsolution.adviser.logic.DictionaryType;
import pl.com.goodsolution.adviser.logic.Language;
import pl.com.goodsolution.adviser.logic.courseplatform.CourseCustomersService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("api/adviser/")
public class AdviserEndpoint {
    private static final Logger log = LoggerFactory.getLogger(AdviserEndpoint.class);

    private AdviserBrainService adviserBrainService;
    private AdviserService adviserService;
    private final AccountsService accountsService;
    private DictionariesService dictionariesService;
    private final ApplicationsService applicationsService;
    private final ContextVariablesService contextVariablesService;
    private final ContextConfigsService contextConfigsService;
    private final AdviceCategoriesService adviceCategoriesService;
    private final CourseCustomersService courseCustomersService;

    public AdviserEndpoint(AdviserBrainService adviserBrainService, AdviserService adviserService, AccountsService accountsService, DictionariesService dictionariesService, ApplicationsService applicationsService, ContextVariablesService contextVariablesService, ContextConfigsService contextConfigsService, AdviceCategoriesService adviceCategoriesService, CourseCustomersService courseCustomersService) {
        this.adviserBrainService = adviserBrainService;
        this.adviserService = adviserService;
        this.accountsService = accountsService;
        this.dictionariesService = dictionariesService;
        this.applicationsService = applicationsService;
        this.contextVariablesService = contextVariablesService;
        this.contextConfigsService = contextConfigsService;
        this.adviceCategoriesService = adviceCategoriesService;
        this.courseCustomersService = courseCustomersService;
    }

    @GetMapping(path = "pull", produces = "application/json; charset=UTF-8")
    public PullGetResponse pullAdvise(
            @RequestParam String context,
            @RequestParam Long customerId,
            @RequestParam String appId,
            @RequestParam String lang,
            @RequestParam String country,
            @RequestParam String secret
            ) {
        TriggeredAdviceData advice = adviserBrainService.pullAdvise(new AdvisePullData(context, customerId, appId, lang, country, secret));
        return preparePullAdviseResponse(advice);
    }

    @Deprecated
    @PostMapping(path = "trigger", consumes = "application/json; charset=UTF-8", produces = "application/json; charset=UTF-8")
    public TriggerPostResponse triggerAdvise(@RequestBody TriggerPostRequest req) {
        long startTime = System.currentTimeMillis();
        AdviseScope scope = prepareScope(req);
        ContextRequest context = req.getContext();
        log.debug(context.toString());
        TriggeredAdviceData triggeredAdviceData = adviserBrainService.triggerAdvises(new AdviseTriggerData(context.getCustomerId(), context.getContext(), context.getAppId(), scope, req.getAction(), context.getSecret()));
        long timeTaken = System.currentTimeMillis() - startTime;
        log.info("Time Taken by {} is {}", "AdviserEndpoint.triggerAdvise", timeTaken);
        return prepareTriggerAdviseResponse(triggeredAdviceData);
    }

    @PostMapping(path = "respond", consumes = "application/json; charset=UTF-8", produces = "application/json; charset=UTF-8")
    public RespondPostResponse respondAdvise(@RequestBody RespondPostRequest req) {
        log.debug(req.toString());
        return prepareRespondPostResponse(adviserService.storeResponse(req.toAdviceResponseData()));
    }

    private RespondPostResponse prepareRespondPostResponse(AdviceReturnResponseData data) {
        log.debug(data.toString());
      return new RespondPostResponse(data.getResponse());
    }

    @PostMapping(path = "subscribe", consumes = "application/json; charset=UTF-8", produces = "application/json; charset=UTF-8")
    public PurchasePostResponse subscribe(@RequestBody PurchasePostRequest req) {
//        applicationsService.validateApplication(req.getApplicationId(), req.getSecret());
        CustomerData loggedCustomer = courseCustomersService.getLoggedCustomer();
        Long id = adviceCategoriesService.storePurchase(new AdvisePurchaseData(req.getCategoryId(), loggedCustomer.getId()));
        return new PurchasePostResponse(id);
    }

    @DeleteMapping(path = "unsubscribe/{id}")
    public void unsubscribe(@PathVariable Long id) {
        adviceCategoriesService.removePurchase(id);
    }

    @GetMapping(path = "triggered-advices", consumes = "application/json; charset=UTF-8", produces = "application/json; charset=UTF-8")
    public TriggeredAdvicesRequestGetResponse getTriggeredAdvices(
            @RequestParam(name = "page", required = false, defaultValue = "1") Long page,
            @RequestParam(name = "page_size", required = false, defaultValue = "100") Long pageSize,
            @RequestParam(name = "name", required = false) String name,
            @RequestParam(name = "application_id", required = false) String appId,
            @RequestParam(name = "context", required = false) String context,
            @RequestParam(name = "status", required = false) String status,
            @RequestParam(name = "mode", required = false) String mode,
            @RequestParam(name = "customer_id", required = false) Long customerId,
            @RequestParam(name = "trigger_date_time_from", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime triggerDateTimeFrom,
            @RequestParam(name = "trigger_date_time_to", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime triggerDateTimeTo) {
        Long count = adviserService.getTriggeredAdvicesCount();
        List<TriggeredAdviceData> list = adviserService.findTriggeredAdvices(new TriggeredAdvicesFilter(
                status, customerId, page, pageSize, appId, context, mode));
        return new TriggeredAdvicesRequestGetResponse(count, triggeredAdvicesDataToResponse(list));
    }

    @DeleteMapping(path = "triggered-advices/{id}")
    public void deleteTriggeredAdvice(@PathVariable Long id) {
        adviserService.deleteTriggeredAdvice(id);
    }

    @GetMapping(path = "languages")
    public List<DictionaryData> getLanguages() {

        Language language;

        if(LocaleContextHolder.getLocale().getLanguage().equals("pl")) {
            language = Language.PL;
        } else {
            language = Language.US;
        }

        return dictionariesService.getDictionary(DictionaryType.LANGUAGES, language);
    }

    @GetMapping(path = "advices", consumes = "application/json; charset=UTF-8", produces = "application/json; charset=UTF-8")
    public AdvicesRequestGetResponse getAdvices(
            @RequestParam(name = "page", required = false, defaultValue = "1") Long page,
            @RequestParam(name = "page_size", required = false, defaultValue = "100") Long pageSize,
            @RequestParam(name = "name", required = false) String name,
            @RequestParam(name = "application_id", required = false) String appId,
            @RequestParam(name = "domain", required = false) String domain
    ) {
        Long count = adviserService.getAdvicesCount();
        List<AdviceData> advices = adviserService.findAdvices(new AdvicesFilter(page, pageSize, name, appId, domain));
        return new AdvicesRequestGetResponse(count, toResponse(advices));
    }

    @DeleteMapping(path = "advices/{id}")
    public void deleteAdvice(@PathVariable Long id) {
        adviserService.deleteAdvice(id);
    }

    @PostMapping(path = "advices")
    public void createAdvice(@RequestBody AdvicePostRequest request) {
        adviserService.createAdvice(AdviceData.of(request));
    }

    @PutMapping(path = "advices/{id}")
    public void updateAdvice(@PathVariable Long id, @RequestBody AdvicePutRequest request) {
        //adviserService.updateAdvice(new AdviceData(id, request));
    }

    @PostMapping(path = "/accounts")
    public void createAccount(@RequestBody AccountPostRequest request) {
        accountsService.create(new AccountData(request.getName()));
    }

    @GetMapping(path = "/accounts")
    public AccountsRequestGetResponse findAccounts(
            @RequestParam(name = "page", required = false, defaultValue = "1") Long page,
            @RequestParam(name = "page_size", required = false, defaultValue = "100") Long pageSize,
            @RequestParam(name = "name", required = false) String name) {
        return new AccountsRequestGetResponse(accountsToResponses(accountsService.find(new AccountsFilter(page, pageSize, name))),
                accountsService.getTotalAccountsCount());
    }

    @PutMapping(path = "/accounts/{id}")
    public void updateAccount(@PathVariable Long id, @RequestBody AccountPutRequest request) {
        accountsService.update(new AccountData(id, request.getName()));
    }

    @DeleteMapping(path = "/accounts/{id}")
    public void deleteAccount(@PathVariable Long id) {
        accountsService.delete(id);
    }

    @PostMapping(path = "/applications")
    public void createApplication(@RequestBody ApplicationPostRequest request) {
        applicationsService.create(new ApplicationData(request.getApplicationId(), request.getDescription(), request.getSecretKey()));
    }

    @GetMapping(path = "/applications")
    public ApplicationRequestGetResponse findApplications(
            @RequestParam(name = "page", required = false, defaultValue = "1") Long page,
            @RequestParam(name = "page_size", required = false, defaultValue = "100") Long pageSize,
            @RequestParam(name = "application_id", required = false) String applicationId) {
        return new ApplicationRequestGetResponse(applicationsToResponses(applicationsService.find(new ApplicationsFilter(page, pageSize, applicationId, null, null))),
                applicationsService.getTotalApplicationsCount());
    }

    @PutMapping(path = "/applications/{id}")
    public void updateApplications(@PathVariable Long id, @RequestBody ApplicationPutRequest request) {
        applicationsService.update(new ApplicationData(id, request.getApplicationId(), request.getDescription(), request.getSecretKey()));
    }

    @DeleteMapping(path = "/applications/{id}")
    public void deleteApplications(@PathVariable Long id) {
        applicationsService.delete(id);
    }

    @GetMapping(path = "/context-variables")
    public ContextVariablesRequestGetResponse findContextVariables(
            @RequestParam(name = "page", required = false, defaultValue = "1") Long page,
            @RequestParam(name = "page_size", required = false, defaultValue = "100") Long pageSize,
            @RequestParam(name = "application_id", required = false) String applicationId,
            @RequestParam(name = "name", required = false) String name,
            @RequestParam(name = "context", required = false) String context,
            @RequestParam(name = "type", required = false) String type,
            @RequestParam(name = "context_id", required = false) String contextId) {
        ContextVariablesFilter filter = new ContextVariablesFilter(page, pageSize, applicationId, name, context, type, contextId, null);
        List<ContextVariableData> contextVariables = contextVariablesService.find(filter);
        return new ContextVariablesRequestGetResponse(contextVariablesToResponses(contextVariables), contextVariablesService.getTotalContextVariablesCount());
    }

    @GetMapping(path = "/context-configs")
    public ContextConfigsRequestGetResponse findContextConfigs(
            @RequestParam(name = "page", required = false, defaultValue = "1") Long page,
            @RequestParam(name = "page_size", required = false, defaultValue = "100") Long pageSize,
            @RequestParam(name = "application_id", required = false) String applicationId,
            @RequestParam(name = "name", required = false) String name,
            @RequestParam(name = "context", required = false) String context,
            @RequestParam(name = "type", required = false) String type) {
        ContextConfigsFilter filter = new ContextConfigsFilter(page, pageSize, applicationId, name, context, type);
        List<ContextConfigData> contextConfigs = contextConfigsService.find(filter);
        return new ContextConfigsRequestGetResponse(contextConfigsToResponses(contextConfigs), contextConfigsService.getTotalContextConfigsCount());
    }

    @DeleteMapping(path = "/context-configs/{id}")
    public void deleteContextConfigs(@PathVariable Long id) {
        contextConfigsService.delete(id);
    }

    @PutMapping(path = "/context-configs/{id}")
    public void updateContextConfigs(@PathVariable Long id, @RequestBody ContextConfigPutRequest request) {
        contextConfigsService.update(new ContextConfigData(id, request.getApplicationId(), request.getContext(),
                request.getName(), request.getType(), request.getValue()));
    }

    @PostMapping(path = "/context-configs")
    public void createContextConfig(@RequestBody ContextConfigPostRequest request) {
        contextConfigsService.create(new ContextConfigData(request.getApplicationId(), request.getContext(),
                request.getName(), request.getType(), request.getValue()));
    }

    @GetMapping(path = "/advice-categories")
    public AdviceCategoriesRequestGetResponse findCategories(
            @RequestParam(name = "page", required = false, defaultValue = "1") Long page,
            @RequestParam(name = "page_size", required = false, defaultValue = "100") Long pageSize) {
        List<AdviceCategoryData> adviceCategories = adviceCategoriesService.find(new AdviceCategoriesFilter(page, pageSize));
        return new AdviceCategoriesRequestGetResponse(adviceCategoriesToResponses(adviceCategories),
                adviceCategoriesService.getTotalAdviceCategoriesCount());
    }

    @GetMapping(path = "my-categories", produces = "application/json; charset=UTF-8")
    public List<MyCategoriesGetResponse> myCategories(
            @RequestParam(name = "application_id", required = true) String appId,
            @RequestParam(name = "context", required = true) String context,
            @RequestParam(name = "secret", required = false) String secret,
            @RequestParam(name = "page", required = false) Long page,
            @RequestParam(name = "page_size", required = false) Long pageSize
    ) {
        //applicationsService.validateApplication(appId, secret);

        List<AdvicePurchasedCategoryData> list = adviceCategoriesService.findPurchasedCategories(
                new AdvicePurchasedCategoriesFilter(1L, 1000L, context, appId, context));

        return prepareMyCategories(list);
    }

    private List<MyCategoriesGetResponse> prepareMyCategories(List<AdvicePurchasedCategoryData> list) {
        List<MyCategoriesGetResponse> result = new ArrayList<>();
        for (AdvicePurchasedCategoryData adviceCategoryData : list) {
            MyCategoriesGetResponse data = new MyCategoriesGetResponse(adviceCategoryData.getId(), adviceCategoryData.getName(), adviceCategoryData.getCategoryId());
            result.add(data);
        }
        return result;
    }

    private List<AdviceCategoryGetResponse> adviceCategoriesToResponses(List<AdviceCategoryData> adviceCategories) {
        List<AdviceCategoryGetResponse> list = new ArrayList<>();
        for (AdviceCategoryData adviceCategory : adviceCategories) {
            list.add(new AdviceCategoryGetResponse(adviceCategory.getName(), adviceCategory.getApplicationId(),
                    adviceCategory.getContext(), adviceCategory.getPrice(), adviceCategory.getCurrencyCode()));
        }
        return list;
    }

    private List<ContextConfigGetResponse> contextConfigsToResponses(List<ContextConfigData> contextConfigs) {
        List<ContextConfigGetResponse> list = new ArrayList<>();
        for (ContextConfigData contextConfig : contextConfigs) {
            list.add(new ContextConfigGetResponse(contextConfig.getId(), contextConfig.getApplicationId(),
                    contextConfig.getContext(), contextConfig.getName(), contextConfig.getType(), contextConfig.getValue()));
        }
        return list;
    }

    private List<ContextVariableGetResponse> contextVariablesToResponses(List<ContextVariableData> contextVariables) {
        List<ContextVariableGetResponse> list = new ArrayList<>();
        for (ContextVariableData contextVariable : contextVariables) {
            list.add(new ContextVariableGetResponse(contextVariable.getId(), contextVariable.getApplicationId(),
                    contextVariable.getContext(), contextVariable.getName(), contextVariable.getType().name(),
                    contextVariable.getValue(), contextVariable.getContextId()));
        }
        return list;
    }

    private List<ApplicationGetResponse> applicationsToResponses(List<ApplicationData> applications) {
        List<ApplicationGetResponse> list = new ArrayList<>();
        for (ApplicationData application : applications) {
            list.add(new ApplicationGetResponse(application.getId(), application.getApplicationId(), application.getDescription()));
        }
        return list;
    }


    private List<AccountGetResponse> accountsToResponses(List<AccountData> accounts) {
        List<AccountGetResponse> list = new ArrayList<>();
        for (AccountData account : accounts) {
            list.add(new AccountGetResponse(account.getId(), account.getName()));
        }
        return list;
    }

    private List<AdviceGetResponse> toResponse(List<AdviceData> advices) {
        List<AdviceGetResponse> list = new ArrayList<>();
        for (AdviceData advice : advices) {
            list.add(new AdviceGetResponse(advice.getId(), advice.getTitle(), advice.getAppId(), advice.getContext()));
        }
        return list;
    }

    private List<TriggeredAdviceGetResponse> triggeredAdvicesDataToResponse(List<TriggeredAdviceData> triggeredAdvicesData) {
        List<TriggeredAdviceGetResponse> list = new ArrayList<>();
        for (TriggeredAdviceData triggeredAdviceData : triggeredAdvicesData) {
            list.add(new TriggeredAdviceGetResponse(
                    triggeredAdviceData.getId(),
                    triggeredAdviceData.getTitle(),//triggeredAdviceData.getName(),
                    triggeredAdviceData.getAppId(),
                    triggeredAdviceData.getContext(),
                    null,
                    triggeredAdviceData.getContent(), triggeredAdviceData.getResponseValue()));
        }
        return list;
    }

    private AdviseScope prepareScope(final TriggerPostRequest req) {
        AdviseScope scope = AdviseScope.GENERAL;
        if (req.getAction() != null && !req.getAction().isEmpty()) {
            scope = AdviseScope.ACTION;
        }
        return scope;
    }

    private TriggerPostResponse prepareTriggerAdviseResponse(TriggeredAdviceData triggeredAdviceData) {
        if (triggeredAdviceData == null) {
            return null;
        }
        return new TriggerPostResponse(new TriggeredAdviceGetResponse(triggeredAdviceData));
    }

    private PullGetResponse preparePullAdviseResponse(TriggeredAdviceData data) {
        if (data == null) {
            throw new DataNotFoundException();
        }
        return new PullGetResponse(data.getId(), data.getTitle(), data.getContent(), data.getVariableName(), data.getType().code(), data.getId(), getComboOptions(data.getComboJson()));
    }

    private AdviceComboData getComboOptions(String field) {
        if (field == null || "".equals(field)) {
            return null;
        }
        AdviceComboData result = null;
        try {
            result = new ObjectMapper().readValue(field, AdviceComboData.class);
            return result;
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return result;
    }
}
