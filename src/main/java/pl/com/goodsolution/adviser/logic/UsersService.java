package pl.com.goodsolution.adviser.logic;

import org.springframework.stereotype.Service;
import pl.com.goodsolution.adviser.domain.UserData;
import pl.com.goodsolution.adviser.domain.adviser.AccountData;
import pl.com.goodsolution.adviser.logic.adviser.AccountsService;

@Service
public class UsersService {
    private UsersJdbcRepository usersJdbcRepository;
    private AccountsService accountsService;

    public UsersService(UsersJdbcRepository usersJdbcRepository, AccountsService accountsService) {
        this.usersJdbcRepository = usersJdbcRepository;
        this.accountsService = accountsService;
    }

    public UserData getUserByLogin(String login) {
        return usersJdbcRepository.getUserByLogin(login);
    }

    public UserData get(Long id) {
        return usersJdbcRepository.get(id);
    }

    public UserData getUserWithAccountBy(Long id) {
        UserData user = usersJdbcRepository.get(id);
        if (user == null) {
            throw new IllegalStateException("No user with id: " + id);
        }
        AccountData account = accountsService.get(user.getAccountId());
        if (account == null) {
            throw new IllegalStateException("No account with id: " + user.getAccountId());
        }
        return new UserData(user, account);
    }

    public UserData getUserByLoginAndPassword(String login, String passwordHash) {
        UserData user = usersJdbcRepository.getUserByLoginAndPassword(login, passwordHash);
        if (user == null) {
            throw new IllegalStateException("No user with login: " + login);
        }
        AccountData account = accountsService.get(user.getAccountId());
        if (account == null) {
            throw new IllegalStateException("No account with id: " + user.getAccountId());
        }
        return new UserData(user, account);
    }

    public Long create(UserData user) {
        return usersJdbcRepository.create(user);
    }

    public Long createUserWithAccount(UserData user) {
        Long accountId = null;
        if (user.getAccount() != null) {
            accountId = accountsService.create(user.getAccount());
        }

        return usersJdbcRepository.create(user);
    }
}
