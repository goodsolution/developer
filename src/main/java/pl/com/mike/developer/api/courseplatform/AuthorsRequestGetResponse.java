package pl.com.mike.developer.api.courseplatform;

import java.util.List;

public class AuthorsRequestGetResponse {
    private List<AuthorGetResponse> authors;

    public AuthorsRequestGetResponse(List<AuthorGetResponse> authors) {
        this.authors = authors;
    }

    public List<AuthorGetResponse> getAuthors() {
        return authors;
    }
}
