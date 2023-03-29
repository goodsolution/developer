package pl.com.goodsolution.adviser.domain;

public class CustomersFilter {
    private Long page;
    private Long pageSize;
    private String nameAndSurname;
    private String email;
    private Long groupId;
    private Long statusId;
    private String sortRecordsBy;
    private String recordArrangement;
    private Boolean withoutCount = false;

    public CustomersFilter(Boolean withoutCount) {
        this.withoutCount = withoutCount;
    }

    public CustomersFilter(Long page, Long pageSize, String nameAndSurname, String email, Long groupId, Long statusId, String sortRecordsBy, String recordArrangement) {
        this.page = page;
        this.pageSize = pageSize;
        this.nameAndSurname = nameAndSurname;
        this.email = email;
        this.groupId = groupId;
        this.statusId = statusId;
        this.sortRecordsBy = sortRecordsBy;
        this.recordArrangement = recordArrangement;
    }

    public Long getPage() {
        return page;
    }

    public Long getPageSize() {
        return pageSize;
    }

    public String getNameAndSurname() {
        return nameAndSurname;
    }

    public String getEmail() {
        return email;
    }

    public Long getGroupId() {
        return groupId;
    }

    public Long getStatusId() {
        return statusId;
    }

    public String getSortRecordsBy() {
        return sortRecordsBy;
    }

    public String getRecordArrangement() {
        return recordArrangement;
    }

    public Boolean getWithoutCount() {
        return withoutCount;
    }

    @Override
    public String toString() {
        return "CustomersFilter{" +
                "page=" + page +
                ", pageSize=" + pageSize +
                ", nameAndSurname='" + nameAndSurname + '\'' +
                ", email='" + email + '\'' +
                ", groupId=" + groupId +
                ", statusId=" + statusId +
                ", sortRecordsBy='" + sortRecordsBy + '\'' +
                ", recordArrangement='" + recordArrangement + '\'' +
                '}';
    }
}
