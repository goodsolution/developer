package pl.com.mike.developer.domain.adviser;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.List;

public class AdviceComboData {

    private List<AdviceComboOptionData> options = new ArrayList();

    public AdviceComboData() {
    }

    public AdviceComboData(List<AdviceComboOptionData> options) {
        this.options = options;
    }

    public List<AdviceComboOptionData> getOptions() {
        return options;
    }

    public String toComboJsonColumnValue() {
        ObjectMapper mapper = new ObjectMapper();

        String json = null;
        try {
            json = mapper.writeValueAsString(this);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return json;
    }

    @Override
    public String toString() {
        return "AdviceComboData{" +
                "options=" + options +
                '}';
    }
}
