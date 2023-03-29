package pl.com.goodsolution.adviser.domain.courseplatform;

public class AuthorsFilter {
    private Long id;
    private String firstName;
    private String lastName;
    private Long page;
    private Long pageSize;

    public AuthorsFilter(Long id) {
        this.id = id;
    }

    public AuthorsFilter(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public AuthorsFilter(Long page, Long pageSize) {
        this.page = page;
        this.pageSize = pageSize;
    }

    public Long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public Long getPage() {
        return page;
    }

    public Long getPageSize() {
        return pageSize;
    }
}
