package pl.com.mike.developer.logic.adviser;

import org.springframework.stereotype.Service;
import pl.com.mike.developer.domain.adviser.AccountData;
import pl.com.mike.developer.domain.adviser.AccountsFilter;

import java.util.List;

@Service
public class AccountsService {

    private final AccountsJdbcRepository accountsJdbcRepository;

    public AccountsService(AccountsJdbcRepository accountsJdbcRepository) {
        this.accountsJdbcRepository = accountsJdbcRepository;
    }

    public Long create(AccountData data) {
        validate(data);
        return accountsJdbcRepository.create(data);
    }

    public AccountData get(Long id) {
        return accountsJdbcRepository.get(id);
    }

    public List<AccountData> find(AccountsFilter filter) {
        return accountsJdbcRepository.find(filter);
    }

    public void update(AccountData data) {
        validate(data);
        accountsJdbcRepository.update(data);
    }

    public void delete(Long id) {
        accountsJdbcRepository.delete(id);
    }

    public Long getTotalAccountsCount() {
        return accountsJdbcRepository.getTotalAccountsCount();
    }

    private void validate(AccountData data) {
        if(data.getName().equals("")) {
            throw new IllegalArgumentException("Name can't be empty");
        } else if(data.getName().length() > 200) {
            throw new IllegalArgumentException("Name too long");
        }
    }
}
