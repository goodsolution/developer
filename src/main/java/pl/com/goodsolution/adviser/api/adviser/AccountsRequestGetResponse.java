package pl.com.goodsolution.adviser.api.adviser;

import java.util.List;

public class AccountsRequestGetResponse {

    private final List<AccountGetResponse> accounts;
    private final Long totalAccountsCount;

    public AccountsRequestGetResponse(List<AccountGetResponse> accounts, Long totalAccountsCount) {
        this.accounts = accounts;
        this.totalAccountsCount = totalAccountsCount;
    }

    public List<AccountGetResponse> getAccounts() {
        return accounts;
    }

    public Long getTotalAccountsCount() {
        return totalAccountsCount;
    }
}
