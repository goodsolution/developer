package pl.com.mike.developer.api.itube;

import org.springframework.web.bind.annotation.*;
import pl.com.mike.developer.domain.itube.*;
import pl.com.mike.developer.logic.courseplatform.ConverterToResponsesUtil;
import pl.com.mike.developer.logic.itube.ITubeService;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("admin/api/crs/")
public class ITubeEndpointAdmin {

    private ITubeService iTubeService;

    public ITubeEndpointAdmin(ITubeService iTubeService) {
        this.iTubeService = iTubeService;
    }

    @PostMapping("itubes")
    public void createITube(@RequestBody ITubePostRequest request) {
        iTubeService.create(new ITubeData(request.getTitlePl(), request.getTitleEn(), request.getKeywords(), request.getLink(), LocalDateTime.now(), null));
    }

    @GetMapping("itubes")
    public ITubesGetResponseAdmin getITube(
            @RequestParam(name = "page", required = false, defaultValue = "1") Long page,
            @RequestParam(name = "page_size", required = false, defaultValue = "12") Long pageSize) {
        List<ITubeData> iTubeData = iTubeService.find(new ITubeSearchFilter(page, pageSize, Boolean.FALSE));
        List<ITubeGetResponseAdmin> iTubeGetResponseAdmins = ConverterToResponsesUtil.iTubesToResponsesAdmin(iTubeData);
        return new ITubesGetResponseAdmin(iTubeGetResponseAdmins);
    }

    @PutMapping("/itube-page/{id}")
    public void updateCourse(@PathVariable Long id, @RequestBody ITubePutRequestAdmin request) {
        iTubeService.updateFromPanelAdmin(new ITubePutRequestAdmin(
                id,
                request.getTitlePl(),
                request.getTitleEn(),
                request.getKeywords(),
                request.getLink()
                ));
    }

    @DeleteMapping("itubes/{id}")
    public void deleteITube(@PathVariable Long id) {
        iTubeService.delete(id);
    }

}
