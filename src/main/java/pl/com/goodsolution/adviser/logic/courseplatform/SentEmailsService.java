package pl.com.goodsolution.adviser.logic.courseplatform;

import org.springframework.stereotype.Service;
import pl.com.goodsolution.adviser.domain.courseplatform.SentEmailData;

import java.time.LocalDateTime;

@Service
public class SentEmailsService {

    private SentEmailsJdbcRepository sentEmailsJdbcRepository;

    public SentEmailsService(SentEmailsJdbcRepository sentEmailsJdbcRepository) {
        this.sentEmailsJdbcRepository = sentEmailsJdbcRepository;
    }

    public void create(SentEmailData data) {
        sentEmailsJdbcRepository.create(new SentEmailData(data, LocalDateTime.now()));
    }
}
