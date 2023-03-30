package pl.com.mike.developer.logic.courseplatform;

import org.springframework.stereotype.Service;
import pl.com.mike.developer.domain.courseplatform.CustomerData;
import pl.com.mike.developer.domain.courseplatform.TokenData;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class TokensService {

    private final static Long TOKEN_VALIDITY_TIME_IN_HOURS = 24L;

    private TokensJdbcRepository tokensJdbcRepository;

    public TokensService(TokensJdbcRepository tokensJdbcRepository) {
        this.tokensJdbcRepository = tokensJdbcRepository;
    }

    public void create(TokenData data) {
        tokensJdbcRepository.create(data);
    }

    public TokenData get(String value) {
        return tokensJdbcRepository.get(value);
    }

    public TokenData prepareAndCreate(CustomerData customer) {
        TokenData tokenData = prepare(customer);
        create(tokenData);
        return tokenData;
    }

    private TokenData prepare(CustomerData customer) {
        String value = UUID.randomUUID().toString();
        LocalDateTime expirationDatetime = LocalDateTime.now().plusHours(TOKEN_VALIDITY_TIME_IN_HOURS);
        return new TokenData(value, expirationDatetime, customer);
    }
}
