package pl.com.mike.developer.logic.adviser;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.com.mike.developer.domain.adviser.AdviceVariableData;
import pl.com.mike.developer.domain.adviser.AdviceVariablesFilter;
import pl.com.mike.developer.logic.CommonJdbcRepository;
import pl.com.mike.developer.logic.adviser.evaluator.DataType;
import pl.com.mike.developer.logic.adviser.evaluator.VariableValue;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class AdviserVariablesService {
    private AdviserVariablesJdbcRepository adviserVariablesJdbcRepository;
    private CommonJdbcRepository commonJdbcRepository;

    public AdviserVariablesService(AdviserVariablesJdbcRepository adviserVariablesJdbcRepository, CommonJdbcRepository commonJdbcRepository) {
        this.adviserVariablesJdbcRepository = adviserVariablesJdbcRepository;
        this.commonJdbcRepository = commonJdbcRepository;
    }

    List<AdviceVariableData> find(AdviceVariablesFilter filter) {
        return adviserVariablesJdbcRepository.find(filter);
    }

    Map<String, VariableValue> findVars(AdviceVariablesFilter filter) {
        List<AdviceVariableData> varsList = find(filter);
        Map<String, VariableValue> vars = new HashMap<>();
        for (AdviceVariableData element : varsList) {
            vars.put(element.getName(), new VariableValue(element.getValue(), DataType.valueOf(element.getType().name())));
        }
        return vars;
    }
}