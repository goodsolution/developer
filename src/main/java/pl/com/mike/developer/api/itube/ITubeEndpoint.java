package pl.com.mike.developer.api.itube;

import org.springframework.web.bind.annotation.*;
import pl.com.mike.developer.domain.itube.ITubeSearchFilter;
import pl.com.mike.developer.domain.itube.ITubesGetResponse;
import pl.com.mike.developer.logic.courseplatform.ConverterToResponsesUtil;
import pl.com.mike.developer.logic.itube.ITubeService;

@RestController
@RequestMapping("api/crs/")
public class ITubeEndpoint {

    private ITubeService iTubeService;

    public ITubeEndpoint(ITubeService iTubeService) {
        this.iTubeService = iTubeService;
    }

    @GetMapping("itube/{page}")
    public ITubesGetResponse findITubes(@PathVariable Long page,
                                        @RequestParam(name = "phrase", required = false) String phrase
    ) {
        return new ITubesGetResponse(ConverterToResponsesUtil.iTubesToResponses(
                iTubeService.find(new ITubeSearchFilter(
                        phrase,
                        page,
                        12L,
                        Boolean.FALSE
                ))));
    }

}
