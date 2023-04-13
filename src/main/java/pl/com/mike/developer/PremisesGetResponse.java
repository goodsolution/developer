package pl.com.mike.developer;

import java.util.List;

public class PremisesGetResponse {

    private final List<PremiseGetResponse> premisesGetResponse;

    public PremisesGetResponse(List<PremiseGetResponse> premisesGetResponse) {
        this.premisesGetResponse = premisesGetResponse;
    }

    public List<PremiseGetResponse> getPremisesGetResponse() {
        return premisesGetResponse;
    }

}
