package pl.com.mike.developer;

import java.util.List;

public class BuildingsGetResponse {
    private List<BuildingGetResponse> buildingsGetResponse;

    public BuildingsGetResponse(List<BuildingGetResponse> buildingsGetResponse) {
        this.buildingsGetResponse = buildingsGetResponse;
    }

    public List<BuildingGetResponse> getBuildingsGetResponse() {
        return buildingsGetResponse;
    }

    public void setBuildingsGetResponse(List<BuildingGetResponse> buildingsGetResponse) {
        this.buildingsGetResponse = buildingsGetResponse;
    }
}
