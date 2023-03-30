package pl.com.mike.developer.api.lifeadviser;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import pl.com.mike.developer.domain.UserData;
import pl.com.mike.developer.domain.adviser.*;
import pl.com.mike.developer.logic.DictionariesService;
import pl.com.mike.developer.logic.adviser.*;
import pl.com.mike.developer.logic.lifeadviser.LifeAdviserService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("api/public/life_adviser/")
public class LifeAdviserEndpoint {
    private static final Logger log = LoggerFactory.getLogger(LifeAdviserEndpoint.class);
    private final AdviserBrainService adviserBrainService;
    private final AdviserService adviserService;
    private final DictionariesService dictionariesService;
    private final ApplicationsService applicationsService;
    private final AdviceCategoriesService adviceCategoriesService;
    private final SuggestionService suggestionService;
    private final LifeAdviserService lifeAdviserService;

    public LifeAdviserEndpoint(AdviserBrainService adviserBrainService, AdviserService adviserService, DictionariesService dictionariesService, ApplicationsService applicationsService, AdviceCategoriesService adviceCategoriesService, SuggestionService suggestionService, LifeAdviserService lifeAdviserService) {
        this.adviserBrainService = adviserBrainService;
        this.adviserService = adviserService;
        this.dictionariesService = dictionariesService;
        this.applicationsService = applicationsService;
        this.adviceCategoriesService = adviceCategoriesService;
        this.suggestionService = suggestionService;
        this.lifeAdviserService = lifeAdviserService;
    }

    @GetMapping(path = "login", produces = "application/json; charset=UTF-8")
    public AdviserLoginGetResponse login(
            @RequestParam(name = "application_id", required = true) String appId,
            @RequestParam(name = "secret", required = true) String secret,
            @RequestParam(name = "login", required = true) String login,
            @RequestParam(name = "password_hash", required = true) String password_hash) {
        applicationsService.validateApplication(appId, secret);
        UserData user = lifeAdviserService.login(login, password_hash);
        if (user != null) {
            return new AdviserLoginGetResponse(user.getId());
        } else {
            return new AdviserLoginGetResponse(-1L);
        }
    }

    @PostMapping(path = "register", consumes = "application/json; charset=UTF-8", produces = "application/json; charset=UTF-8")
    public RegistrationPostResponse register(@RequestBody RegistrationPostRequest req) {
        applicationsService.validateApplication(req.getApplicationId(), req.getSecret());
        Long id = lifeAdviserService.register(req.getLogin(), req.getPasswordHash(), req.getNewsletterAccepted(), req.getRegulationsAccepted());
        return new RegistrationPostResponse(id);
    }



    @PutMapping(path = "purchase-finish/{purchasedCategoryId}", consumes = "application/json; charset=UTF-8", produces = "application/json; charset=UTF-8")
    public PurchasePostResponse purchaseFinish(@RequestBody PurchasePutRequest req, @PathVariable Long purchasedCategoryId) {
        applicationsService.validateApplication(req.getApplicationId(), req.getSecret());
        Long id = lifeAdviserService.finishPurchase(req.getDomainId(), purchasedCategoryId, req.getExternalTransactionId());
        return new PurchasePostResponse(id);
    }

    @PutMapping(path = "options/{userId}")
    public void options(@PathVariable String userId, @RequestBody OptionsPutRequest req) {
        applicationsService.validateApplication(req.getApplicationId(), req.getSecret());
        lifeAdviserService.updateOptions(userId, req.getPreferredHourFrom(), req.getPreferredHourTo(), req.getAllDay(), req.getNewsletterAccepted());
    }

    @GetMapping(path = "options", produces = "application/json; charset=UTF-8")
    public OptionsGetResponse options(
            @RequestParam(name = "application_id", required = true) String appId,
            @RequestParam(name = "domain", required = true) String domain,
            @RequestParam(name = "domain_id", required = true) String domainId,
            @RequestParam(name = "secret", required = true) String secret) {
        applicationsService.validateApplication(appId, secret);
        AccountData options = lifeAdviserService.getOptions(domainId);
        return new OptionsGetResponse(options.getPreferredHourFrom(), options.getPreferredHourTo(), options.getAllDay(), options.getNewsletterAccepted());
    }

    @DeleteMapping(path = "triggered-advice/{id}")
    public void deleteTriggeredAdvice(@PathVariable Long id, @RequestBody TriggeredAdviceDeleteRequest req) {
        applicationsService.validateApplication(req.getApplicationId(), req.getSecret());
        lifeAdviserService.deleteTriggeredAdvice(id, req.getApplicationId(), req.getDomain(), req.getDomainId());
    }

    @GetMapping(path = "newest", produces = "application/json; charset=UTF-8")
    public List<TriggeredAdviceGetResponse> newest(
            @RequestParam(name = "application_id", required = true) String appId,
            @RequestParam(name = "domain", required = true) String domain,
            @RequestParam(name = "domain_id", required = true) String domainId,
            @RequestParam(name = "secret", required = true) String secret) {
        applicationsService.validateApplication(appId, secret);
        List<TriggeredAdviceData> list = adviserService.findTriggeredAdvices(
                new TriggeredAdvicesFilter(domainId, 1L, 10L, appId, domain,
                        new JdbcUtil.SortColumns(Arrays.asList(new JdbcUtil.SortColumn("id", JdbcUtil.SortDirection.DESC)))));
        return prepareTriggeredAdvices(list);
    }

    @GetMapping(path = "liked", produces = "application/json; charset=UTF-8")
    public List<TriggeredAdviceGetResponse> liked(
            @RequestParam(name = "application_id", required = true) String appId,
            @RequestParam(name = "domain", required = true) String domain,
            @RequestParam(name = "domain_id", required = true) String domainId,
            @RequestParam(name = "secret", required = true) String secret,
            @RequestParam(name = "page", required = false, defaultValue = "1L") Long page,
            @RequestParam(name = "page_size", required = false, defaultValue = "100L") Long pageSize) {
        applicationsService.validateApplication(appId, secret);
        List<TriggeredAdviceData> list = adviserService.findTriggeredAdvices(
                new TriggeredAdvicesFilter(domainId, 1L, 100L, appId, domain,
                        new JdbcUtil.SortColumns(Arrays.asList(new JdbcUtil.SortColumn("id", JdbcUtil.SortDirection.DESC))), 1L));
        return prepareTriggeredAdvices(list);
    }

    @GetMapping(path = "not_liked", produces = "application/json; charset=UTF-8")
    public List<TriggeredAdviceGetResponse> notLiked(
            @RequestParam(name = "application_id", required = true) String appId,
            @RequestParam(name = "domain", required = true) String domain,
            @RequestParam(name = "domain_id", required = true) String domainId,
            @RequestParam(name = "secret", required = true) String secret,
            @RequestParam(name = "page", required = false, defaultValue = "1L") Long page,
            @RequestParam(name = "page_size", required = false, defaultValue = "100L") Long pageSize
    ) {
        applicationsService.validateApplication(appId, secret);
        List<TriggeredAdviceData> list = adviserService.findTriggeredAdvices(
                new TriggeredAdvicesFilter(domainId, page, pageSize, appId, domain,
                        new JdbcUtil.SortColumns(Arrays.asList(new JdbcUtil.SortColumn("id", JdbcUtil.SortDirection.DESC))), -1L));
        return prepareTriggeredAdvices(list);
    }

    // TODO remove liked/did not like
    // DELETE








    @GetMapping(path = "market", produces = "application/json; charset=UTF-8")
    public List<MarketItemGetResponse> market(
            @RequestParam(name = "application_id", required = true) String appId,
            @RequestParam(name = "domain", required = true) String domain,
            @RequestParam(name = "search", required = true) String search,
            @RequestParam(name = "secret", required = true) String secret,
            @RequestParam(name = "page", required = false, defaultValue = "1L") Long page,
            @RequestParam(name = "page_size", required = false, defaultValue = "100L") Long pageSize) {
        applicationsService.validateApplication(appId, secret);
        List<AdviceCategoryData> list = adviceCategoriesService.find(
                new AdviceCategoriesFilter(page, pageSize, null, search, appId, domain));
        return prepareMarketItems(list);
    }

    @PostMapping(path = "suggestion", consumes = "application/json; charset=UTF-8", produces = "application/json; charset=UTF-8")
    public SuggestionPostResponse suggestion(@RequestBody SuggestionPostRequest req) {
        applicationsService.validateApplication(req.getApplicationId(), req.getSecret());
        suggestionService.create(new SuggestionData(req.getSuggestion(), req.getApplicationId(), req.getDomain(), req.getDomainId()));
        return new SuggestionPostResponse(req.getSuggestion());
    }




    private List<MarketItemGetResponse> prepareMarketItems(List<AdviceCategoryData> list) {
        List<MarketItemGetResponse> result = new ArrayList<>();
        for (AdviceCategoryData adviceCategoryData : list) {
            MarketItemGetResponse data = new MarketItemGetResponse(adviceCategoryData.getId(), adviceCategoryData.getName(), adviceCategoryData.getTags(), adviceCategoryData.getDescription(),
                    adviceCategoryData.getPricePerMonth().toString(),
                    adviceCategoryData.getPricePerQuarter().toString(),
                    adviceCategoryData.getPricePerHalfYear().toString(),
                    adviceCategoryData.getPricePerYear().toString());
            result.add(data);
        }
        return result;
    }

    private List<TriggeredAdviceGetResponse> prepareTriggeredAdvices(List<TriggeredAdviceData> list) {
        List<TriggeredAdviceGetResponse> result = new ArrayList<>();
        for (TriggeredAdviceData triggeredAdviceData : list) {
            TriggeredAdviceGetResponse data = new TriggeredAdviceGetResponse(triggeredAdviceData.getId(), triggeredAdviceData.getContent(), null, null);
            result.add(data);
        }
        return result;
    }
}
