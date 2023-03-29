package pl.com.goodsolution.adviser.api;

public class CustomerGroupGetResult {

    private Long groupId;
    private String name;
    private Long numberOfUsers;
    private Long status;

    public CustomerGroupGetResult(Long groupId, String name, Long numberOfUsers, Long status) {
        this.groupId = groupId;
        this.name = name;
        this.numberOfUsers = numberOfUsers;
        this.status = status;
    }

    public Long getGroupId() {
        return groupId;
    }

    public String getName() {
        return name;
    }

    public Long getNumberOfUsers() {
        return numberOfUsers;
    }

    public Long getStatus() {
        return status;
    }
}
