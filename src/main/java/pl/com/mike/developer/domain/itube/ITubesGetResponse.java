package pl.com.mike.developer.domain.itube;

import java.util.List;

public class ITubesGetResponse {
    private List<ITubeGetResponse> iTubesGetResponse;

    public ITubesGetResponse(List<ITubeGetResponse> iTubesGetResponse) {
        this.iTubesGetResponse = iTubesGetResponse;
    }

    public List<ITubeGetResponse> getiTubesGetResponse() {
        return iTubesGetResponse;
    }
}
