package pl.com.goodsolution.adviser.logic.adviser;

import org.springframework.stereotype.Service;
import pl.com.goodsolution.adviser.domain.adviser.AccountData;
import pl.com.goodsolution.adviser.domain.adviser.AccountsFilter;
import pl.com.goodsolution.adviser.domain.adviser.SuggestionData;

import java.util.List;

@Service
public class SuggestionService {

    private final SuggestionJdbcRepository suggestionJdbcRepository;

    public SuggestionService(SuggestionJdbcRepository suggestionJdbcRepository) {
        this.suggestionJdbcRepository = suggestionJdbcRepository;
    }

    public void create(SuggestionData data) {
        suggestionJdbcRepository.create(data);
    }

}
