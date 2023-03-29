package pl.com.goodsolution.adviser.logic.lifeadviser;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.com.goodsolution.adviser.domain.DictionaryData;
import pl.com.goodsolution.adviser.domain.UserData;
import pl.com.goodsolution.adviser.domain.adviser.*;
import pl.com.goodsolution.adviser.logic.*;
import pl.com.goodsolution.adviser.logic.adviser.AccountsService;
import pl.com.goodsolution.adviser.logic.adviser.AdviceCategoriesService;
import pl.com.goodsolution.adviser.logic.adviser.AdviserService;
import pl.com.goodsolution.adviser.logic.courseplatform.EmailUtil;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class LifeAdviserService {
    private UsersService usersService;
    private DictionariesService dictionariesService;
    private AccountsService accountsService;
    private AdviceCategoriesService adviceCategoriesService;
    private AdviserService adviserService;

    public LifeAdviserService(UsersService usersService, DictionariesService dictionariesService, AccountsService accountsService, AdviceCategoriesService adviceCategoriesService, AdviserService adviserService) {
        this.usersService = usersService;
        this.dictionariesService = dictionariesService;
        this.accountsService = accountsService;
        this.adviceCategoriesService = adviceCategoriesService;
        this.adviserService = adviserService;
    }

    public UserData login(String login, String passwordHash) {
        EmailUtil.validateEmail(login);

        UserData user = usersService.getUserByLoginAndPassword(login, passwordHash);
        if (user != null && !AccountType.LIFE_ADVISER.equals(user.getAccount().getType())) {
            return null;
        }
        return user;
    }

    public Long register(String login, String passwordHash, Boolean newsletterAccepted,
            Boolean regulationsAccepted) {
        if (!regulationsAccepted) {
            throw new IllegalArgumentException("You have to accept regulations");
        }

        EmailUtil.validateEmail(login);

        UserData user = usersService.getUserByLogin(login);
        if (user != null) {
            throw new IllegalStateException("User with given login exists");
        }

        Long accountId = accountsService.create(prepareDefaultAccount(login, newsletterAccepted, regulationsAccepted));
        if (accountId == null) {
            throw new IllegalStateException("No account");
        }

        DictionaryData role = dictionariesService.getDictionaryElementByCode("LIFE-ADVISER", DictionaryType.ROLES, Language.PL);
        if (role == null) {
            throw new IllegalStateException("LIFE-ADVISER role no defined");
        }

        return usersService.create(new UserData(new UserData(login, passwordHash, role.getId()),
                prepareDefaultAccount(login, accountId, newsletterAccepted, regulationsAccepted))
            );
    }

    public Long startPurchase(String userId, Long categoryId, String applicationId,
             String domain, String period) {
        UserData user = getUserData(userId);
        PeriodType periodType = PeriodType.fromCode(period);

        List<AdviceCategoryData> categories = adviceCategoriesService.find(new AdviceCategoriesFilter(categoryId));
        if (categories == null || categories.isEmpty()) {
            throw new IllegalStateException("No category with id: " + categoryId);
        }
        if (categories.size() > 1) {
            throw new IllegalStateException("Too many categories for category id: " + categoryId);
        }
        AdviceCategoryData category = categories.get(0);
        LocalDate startDate = LocalDate.now();
        Long id = null; //adviceCategoriesService.storePurchase(new AdvisePurchaseData(userId, category.getId(), applicationId, domain, startDate, calculateEndDate(startDate, periodType), choosePrice(category, periodType), category.getCurrencyCode(), user.getAccount().getId(), periodType));
        return id;
    }

    private LocalDate calculateEndDate(LocalDate startDate, PeriodType period) {
        switch (period) {
            case MONTH:
                return startDate.plusMonths(1);
            case QUARTER:
                return startDate.plusMonths(3);
            case HALF_YEAR:
                return startDate.plusMonths(6);
            case YEAR:
                return startDate.plusYears(1);
        }
        throw new IllegalArgumentException("Unexpected period: " + period);
    }

    private BigDecimal choosePrice(AdviceCategoryData data, PeriodType period) {
        switch (period) {
            case MONTH:
                return data.getPricePerMonth();
            case QUARTER:
                return data.getPricePerQuarter();
            case HALF_YEAR:
                return data.getPricePerHalfYear();
            case YEAR:
                return data.getPricePerYear();
        }
        throw new IllegalArgumentException("Unexpected period: " + period);
    }

    public Long finishPurchase(String userId, Long purchasedCategoryId, String externalTransactionId) {
        getUserData(userId);
        //adviceCategoriesService.updatePurchase(new AdvisePurchaseData(purchasedCategoryId, externalTransactionId, PurchasedCategoryStatus.FINISHED));
        return purchasedCategoryId;
    }

    public AccountData getOptions(String userId) {
        UserData user = getUserData(userId);
        AccountData account = accountsService.get(user.getAccountId());
        return account;
    }

    public void updateOptions(String userId, Integer preferredHourFrom,
            Integer preferredHourTo,
            Boolean allDay,
            Boolean newsletterAccepted) {
        UserData user = getUserData(userId);
        accountsService.update(new AccountData(user.getAccount().getId(), preferredHourFrom,
                preferredHourTo,
                allDay,
                newsletterAccepted,
                user.getAccount().getName(),
                user.getAccount().getType()
        ));
    }

    public void deleteTriggeredAdvice(Long id, String applicationId,
            String domain, String domainId) {
        TriggeredAdviceData triggeredAdvice = adviserService.getTriggeredAdvice(id);
        if (triggeredAdvice == null) {
            throw new IllegalArgumentException("No advice with id: " + id);
        }
        TriggeredAdviceData data = new TriggeredAdviceData(LocalDateTime.now(), triggeredAdvice);
        adviserService.updateTriggeredAdvice(data);
    }

    private UserData getUserData(String userId) {
        UserData user = usersService.getUserWithAccountBy(Long.valueOf(userId));
        if (user == null) {
            throw new IllegalStateException("No user with id: " + userId);
        }
        if (user.getAccount() == null) {
            throw new IllegalStateException("No account for user with id: " + userId);
        }
        return user;
    }

    private AccountData prepareDefaultAccount(String login, Boolean newsletterAccepted,
                                              Boolean regulationsAccepted) {
        return prepareDefaultAccount(login, null, newsletterAccepted, regulationsAccepted);
    }

    private AccountData prepareDefaultAccount(String login, Long accountId, Boolean newsletterAccepted,
                                              Boolean regulationsAccepted) {
        return new AccountData(accountId, AccountType.LIFE_ADVISER, 8, 22, Boolean.FALSE, login, newsletterAccepted, regulationsAccepted);
    }

}
