package pl.com.goodsolution.adviser.api.itube;

import org.springframework.web.bind.annotation.*;
import pl.com.goodsolution.adviser.domain.itube.ITubeData;
import pl.com.goodsolution.adviser.domain.itube.ITubePostRequest;
import pl.com.goodsolution.adviser.domain.itube.ITubeSearchFilter;
import pl.com.goodsolution.adviser.domain.itube.ITubesGetResponse;
import pl.com.goodsolution.adviser.logic.itube.ITubeService;

import java.time.LocalDateTime;
import java.util.List;

import static pl.com.goodsolution.adviser.logic.courseplatform.ConverterToResponsesUtil.iTubesToResponses;

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
        return new ITubesGetResponse(iTubesToResponses(
                iTubeService.find(new ITubeSearchFilter(
                        phrase,
                        page,
                        12L,
                        Boolean.FALSE
                ))));
    }

}
