package pl.com.goodsolution.adviser.api.courseplatform;

import java.util.List;

public class PeopleGetResponse {
    private List<PeopleElementGetResponse> people;

    public PeopleGetResponse() {
    }

    public PeopleGetResponse(List<PeopleElementGetResponse> peopleGetResponses) {
        this.people = peopleGetResponses;
    }

    public List<PeopleElementGetResponse> getPeople() {
        return people;
    }

    public void setPeople(List<PeopleElementGetResponse> people) {
        this.people = people;
    }
}
