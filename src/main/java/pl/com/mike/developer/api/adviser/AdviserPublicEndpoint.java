package pl.com.mike.developer.api.adviser;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import pl.com.mike.developer.logic.DictionariesService;
import pl.com.mike.developer.logic.adviser.*;

@RestController
@RequestMapping("api/public/adviser/")
public class AdviserPublicEndpoint {
    private static final Logger log = LoggerFactory.getLogger(AdviserPublicEndpoint.class);

    private final AdviserBrainService adviserBrainService;
    private final AdviserService adviserService;
    private final AccountsService accountsService;
    private final DictionariesService dictionariesService;
    private final ApplicationsService applicationsService;
    private final ContextVariablesService contextVariablesService;
    private final ContextConfigsService contextConfigsService;
    private final AdviserEndpoint adviserEndpoint;

    public AdviserPublicEndpoint(AdviserBrainService adviserBrainService, AdviserService adviserService, AccountsService accountsService, DictionariesService dictionariesService, ApplicationsService applicationsService, ContextVariablesService contextVariablesService, ContextConfigsService contextConfigsService, AdviserEndpoint adviserEndpoint) {
        this.adviserBrainService = adviserBrainService;
        this.adviserService = adviserService;
        this.accountsService = accountsService;
        this.dictionariesService = dictionariesService;
        this.applicationsService = applicationsService;
        this.contextVariablesService = contextVariablesService;
        this.contextConfigsService = contextConfigsService;
        this.adviserEndpoint = adviserEndpoint;
    }

    @PostMapping(path = "trigger", consumes = "application/json; charset=UTF-8", produces = "application/json; charset=UTF-8")
    public TriggerPostResponse triggerAdvise(@RequestBody TriggerPostRequest req) {
        return adviserEndpoint.triggerAdvise(req);
    }

    @PostMapping(path = "respond", consumes = "application/json; charset=UTF-8", produces = "application/json; charset=UTF-8")
    public void respondAdvise(@RequestBody RespondPostRequest req) {
        adviserEndpoint.respondAdvise(req);
    }

    @GetMapping(path = "pull", produces = "application/json; charset=UTF-8")
    public PullGetResponse pullAdvise(@RequestParam String context,
                                      @RequestParam Long customerId,
                                      @RequestParam String appId,
                                      @RequestParam String lang,
                                      @RequestParam String country,
                                      @RequestParam String secret) {
        return adviserEndpoint.pullAdvise(context, customerId, appId, lang, country, secret);
    }

    @GetMapping(path = "generate-secret-key")
    public String generateSecretKey() {
        return SecretKeyUtil.generate();
    }


}
