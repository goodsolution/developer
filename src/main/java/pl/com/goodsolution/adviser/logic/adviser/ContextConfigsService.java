package pl.com.goodsolution.adviser.logic.adviser;

import org.springframework.stereotype.Service;
import pl.com.goodsolution.adviser.domain.adviser.*;

import java.util.List;

@Service
public class ContextConfigsService {

    private final ContextConfigsJdbcRepository contextConfigsJdbcRepository;

    public ContextConfigsService(ContextConfigsJdbcRepository contextConfigsJdbcRepository) {
        this.contextConfigsJdbcRepository = contextConfigsJdbcRepository;
    }

    public void create(ContextConfigData data) {
        validate(data);
        contextConfigsJdbcRepository.create(data);
    }

    public ContextConfigData get(Long id) {
        return contextConfigsJdbcRepository.get(id);
    }

    public List<ContextConfigData> find(ContextConfigsFilter filter) {
        return contextConfigsJdbcRepository.find(filter);
    }

    public void update(ContextConfigData data) {
        validate(data);
        contextConfigsJdbcRepository.update(data);
    }

    public void delete(Long id) {
        contextConfigsJdbcRepository.delete(id);
    }

    public Long getTotalContextConfigsCount() {
        return contextConfigsJdbcRepository.getTotalContextConfigsCount();
    }

    private void validate(ContextConfigData data) {
        ValidationUtil.validateLength(data.getApplicationId(), 200, "Application ID");
        ValidationUtil.validateLength(data.getContext(), 200, "Context");
        ValidationUtil.validateLength(data.getName(), 200, "Name");
        ValidationUtil.validateLength(data.getType(), 200, "Type");
        ValidationUtil.validateLength(data.getValue(), 200, "Value");
    }

}
