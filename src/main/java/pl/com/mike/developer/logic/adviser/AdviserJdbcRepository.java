package pl.com.mike.developer.logic.adviser;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import pl.com.mike.developer.domain.adviser.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
class AdviserJdbcRepository {
    private static final Logger log = LoggerFactory.getLogger(AdviserJdbcRepository.class);
    private JdbcTemplate jdbcTemplate;

    private DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-LL-dd HH:mm:ss.S");

    public AdviserJdbcRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    void createAdvice(AdviceData data) {
        String query = "INSERT INTO advices (app_id, context, content, type, title, variable_name, execution_condition, combo_json, status) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?);";
        jdbcTemplate.update(query, data.getAppId(), data.getContext(), data.getContent(), data.getType().code(), data.getTitle(),
                data.getVariableName(), data.getExecutionCondition(), data.getComboJson(), data.getStatus().code());
    }

    AdviceData getAdvice(Long id) {
        String query = "SELECT * FROM advices WHERE id = ?;";
        try {
            return jdbcTemplate.queryForObject(query, new Object[]{id},
                    (rs, rowNum) -> {
                        AdviceData data = new AdviceData(
                                rs.getLong("id"),
                                rs.getString("app_id"),
                                rs.getString("context"),
                                rs.getString("content"),
                                AdviseType.fromCode(rs.getString("type")),
                                rs.getString("title"),
                                rs.getString("variable_name"),
                                rs.getString("execution_condition"),
                                getComboOptions(rs.getString("combo_json")),
                                AdviceStatus.fromCode(rs.getString("status")),
                                rs.getString("response_condition"),
                                rs.getString("response_true_content"),
                                rs.getString("response_else_content")
                        );
                        return data;
                    });
        } catch (EmptyResultDataAccessException ex) {
            return null;
        }
    }

    private AdviceComboData getComboOptions(String field) {
        if (field == null || "".equals(field)) {
            return null;
        }
        AdviceComboData result = null;
        try {
            result = new ObjectMapper().readValue(field, AdviceComboData.class);
            return result;
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return result;
    }

    List<AdviceData> findAdvices(AdvicesFilter filter) {
        String query = "SELECT * FROM advices";

        if (filter != null) {

            query += " WHERE 1=1";
//
//            if (filter.getAppIdAsSubstring() != null) {
//                query += " AND app_id LIKE '%" + filter.getAppIdAsSubstring() + "%'";
//            }
//
//            if (filter.getDomainAsSubstring() != null) {
//                query += " AND domain LIKE '%" + filter.getDomainAsSubstring() + "%'";
//            }
//
//            if (filter.getNameAsSubstring() != null) {
//                query += " AND name LIKE '%" + filter.getNameAsSubstring() + "%'";
//            }
//
//            if (filter.getAppId() != null) {
//                query += " AND app_id = '" + filter.getAppId() + "'";
//            }
//
//            if (filter.getDomain() != null) {
//                query += " AND domain = '" + filter.getDomain() + "'";
//            }
//
//            if (filter.getName() != null) {
//                query += " AND name = '" + filter.getName() + "'";
//            }
//
//            if (filter.getStartDateTo() != null) {
//                query += " AND start_date <= '" + filter.getStartDateTo() + "'";
//            }
//
//            if (filter.getStopDateFrom() != null) {
//                query += " AND stop_date >= '" + filter.getStopDateFrom() + "'";
//            }
//
//
//            if (filter.getPage() != null && filter.getPageSize() != null) {
//                query += " LIMIT " + filter.getPageSize() * (filter.getPage() - 1L) + ", " + filter.getPageSize();
//            }
//
            if (filter.getStatus() != null) {
                query += " AND status = '" + filter.getStatus().code() + "'";
            }

        }
//
//        log.trace(query);
//
        return prepareAdvicesData(jdbcTemplate.queryForList(query));
//        return null;
    }

    List<AdviceData> findAdvicesForProcessing(AdvicesForProcessingFilter filter) {
        String query = "SELECT * " +
                "FROM advices as a " +
                "JOIN advice_categories as ac ON a.category_id=ac.id " +
                "JOIN advice_categories_used as acu ON acu.category_id = ac.id ";

        if (filter != null) {

            query += " WHERE 1=1 " +
                    "AND acu.customer_id = " + filter.getCustomerId() + " ";
//
//            if (filter.getAppIdAsSubstring() != null) {
//                query += " AND app_id LIKE '%" + filter.getAppIdAsSubstring() + "%'";
//            }
//
//            if (filter.getDomainAsSubstring() != null) {
//                query += " AND domain LIKE '%" + filter.getDomainAsSubstring() + "%'";
//            }
//
//            if (filter.getNameAsSubstring() != null) {
//                query += " AND name LIKE '%" + filter.getNameAsSubstring() + "%'";
//            }
//
//            if (filter.getAppId() != null) {
//                query += " AND app_id = '" + filter.getAppId() + "'";
//            }
//
//            if (filter.getDomain() != null) {
//                query += " AND domain = '" + filter.getDomain() + "'";
//            }
//
//            if (filter.getName() != null) {
//                query += " AND name = '" + filter.getName() + "'";
//            }
//
//            if (filter.getStartDateTo() != null) {
//                query += " AND start_date <= '" + filter.getStartDateTo() + "'";
//            }
//
//            if (filter.getStopDateFrom() != null) {
//                query += " AND stop_date >= '" + filter.getStopDateFrom() + "'";
//            }
//
//
//            if (filter.getPage() != null && filter.getPageSize() != null) {
//                query += " LIMIT " + filter.getPageSize() * (filter.getPage() - 1L) + ", " + filter.getPageSize();
//            }
//
            if (filter.getStatus() != null) {
                query += " AND a.status = '" + filter.getStatus().code() + "'";
            }

        }
        query += "order by a.id ";
//
        log.trace(query);
//
        return prepareAdvicesData(jdbcTemplate.queryForList(query));
//        return null;
    }

    private List<AdviceData> prepareAdvicesData(List<Map<String, Object>> rows) {
        List<AdviceData> list = new ArrayList<>();
        for (Map<String, Object> row : rows) {
            AdviceData advice = new AdviceData(
                    Long.valueOf(String.valueOf(row.get("id"))),
                    String.valueOf(row.get("app_id")),
                    String.valueOf(row.get("context")),
                    String.valueOf(row.get("content")),
                    AdviseType.fromCode(String.valueOf(row.get("type"))),
                    String.valueOf(row.get("title")),
                    String.valueOf(row.get("variable_name")),
                    String.valueOf(row.get("execution_condition")),
                    getComboOptions(String.valueOf(row.get("combo_json"))),
                    AdviceStatus.fromCode(String.valueOf(row.get("status"))),
                    String.valueOf(row.get("response_condition")),
                    String.valueOf(row.get("response_true_content")),
                    String.valueOf(row.get("response_else_content"))
            );
            list.add(advice);
        }
        return list;
    }

    List<AdviceAnswerOptionData> findAdviceAnswerOptions(Long adviceId) {
        String query = "SELECT * FROM advice_answer_options where advice_id = " + adviceId + " order by option_order";
        log.trace(query);

        return prepareAdviceAnswerOptionData(jdbcTemplate.queryForList(query));
    }

    private List<AdviceAnswerOptionData> prepareAdviceAnswerOptionData(List<Map<String, Object>> rows) {
        List<AdviceAnswerOptionData> list = new ArrayList<>();
        for (Map<String, Object> row : rows) {
            list.add(new AdviceAnswerOptionData(
                            Long.valueOf(String.valueOf(row.get("id"))),
                            Long.valueOf(String.valueOf(row.get("advice_id"))),
                            String.valueOf(row.get("option_name")),
                            String.valueOf(row.get("value")),
                            Long.valueOf(String.valueOf(row.get("option_order")))
                    )
            );
        }
        return list;
    }


    void updateAdvice(AdviceData data) {
        String query = "UPDATE advices SET app_id = ?, context = ?, content = ?, type = ?, title = ?, variable_name = ?, execution_condition = ?, combo_json = ?, status = ? WHERE id = ?;";
        jdbcTemplate.update(query, data.getAppId(), data.getContext(), data.getContent(), data.getType().code(),
                data.getTitle(), data.getVariableName(), data.getExecutionCondition(), data.getComboJson(), data.getStatus().code(), data.getId());
    }

    void deleteAdvice(Long id) {
        String query = "DELETE FROM advices WHERE id=?;";
        jdbcTemplate.update(query, id);
    }


    //???
    void createTriggeredAdvice(TriggeredAdviceData data) {
        String query = "INSERT INTO triggered_advices (advice_id, app_id, context, context_id, content, type, lang, data_type, status, trigger_datetime, customer_id, create_datetime, title, execution_condition, combo_json, variable_name) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
        jdbcTemplate.update(query, data.getAdviceId(), data.getAppId(), data.getContext(), data.getContextId(), data.getContent(), data.getType().code(),
                data.getLang(), data.getDataType() == null ? null : data.getDataType().name(), data.getStatus().code(), data.getTriggerDateTime(), data.getCustomerId(), LocalDateTime.now(), data.getTitle(), data.getExecutionCondition(), data.getComboJson(), data.getVariableName());
    }

    TriggeredAdviceData getTriggeredAdvice(Long id) {
        String query = "SELECT * FROM triggered_advices WHERE id = ?;";
        try {
            return jdbcTemplate.queryForObject(query, new Object[]{id},
                    (rs, rowNum) -> {
                        TriggeredAdviceData data = new TriggeredAdviceData(
                                rs.getLong("id"),
                                rs.getLong("advice_id"),
                                rs.getString("app_id"),
                                rs.getString("context"),
                                rs.getString("context_id"),
                                rs.getString("content"),
                                rs.getString("title"),
                                AdviseType.fromCode(rs.getString("type")),
                                rs.getString("lang"),
                                rs.getString("data_type") == null ? null : AdviseDataType.valueOf(rs.getString("data_type")),
                                TriggeredAdviceStatus.fromCode(rs.getString("status")),
                                rs.getString("variable_name"),
                                rs.getString("combo_json"),
                                rs.getString("response_value")
                        );
                        return data;
                    });
        } catch (EmptyResultDataAccessException ex) {
            return null;
        }
    }

    List<TriggeredAdviceData> findTriggeredAdvices(TriggeredAdvicesFilter filter) {
        String query = "SELECT * FROM triggered_advices";

        if (filter != null) {

            query += " WHERE 1=1";

//            if (filter.getNameAsSubstring() != null && !filter.getNameAsSubstring().isEmpty()) {
//                query += " AND name LIKE '%" + filter.getNameAsSubstring() + "%'";
//            }

//            if (filter.getAppIdAsSubstring() != null && !filter.getAppIdAsSubstring().isEmpty()) {
//                query += " AND app_id LIKE '%" + filter.getAppIdAsSubstring() + "%'";
//            }
//
//            if (filter.getDomainAsSubstring() != null && !filter.getDomainAsSubstring().isEmpty()) {
//                query += " AND context LIKE '%" + filter.getDomainAsSubstring() + "%'";
//            }

            if (filter.getAppId() != null && !filter.getAppId().isEmpty()) {
                query += " AND app_id = '" + filter.getAppId() + "'";
            }

            if (filter.getContext() != null && !filter.getContext().isEmpty()) {
                query += " AND context = '" + filter.getContext() + "'";
            }

            if (filter.getContextId() != null && !filter.getContextId().isEmpty()) {
                query += " AND context_id = '" + filter.getContextId() + "'";
            }

            if (filter.getCustomerId() != null) {
                query += " AND customer_id = '" + filter.getCustomerId() + "'";
            }
            if (filter.getMode() == null) {
                if (filter.getStatus() != null) {
                    query += " AND status = '" + filter.getStatus().code() + "'";
                }
            } else {
                if (filter.getStatus() != null) {
                    query += " AND status = '" + filter.getStatus().code() + "'";
                }
                if ("ANSWERS".equals(filter.getMode())) {
                    query += " AND type <> 'P'";
                } else if ("ADVISES".equals(filter.getMode())) {
                    query += " AND type = 'P'";
                }
            }
            if (filter.getAdviceId() != null) {
                query += " AND advice_id = " + filter.getAdviceId() + " ";
            }


//            if (filter.getTriggerDateTimeFrom() != null && filter.getTriggerDateTimeTo() != null) {
//                query += " AND trigger_datetime BETWEEN '" + filter.getTriggerDateTimeFrom() + "' AND '" + filter.getTriggerDateTimeTo() + "'";
//            } else if (filter.getTriggerDateTimeFrom() != null) {
//                query += " AND trigger_datetime >= '" + filter.getTriggerDateTimeFrom() + "'";
//            } else if (filter.getTriggerDateTimeTo() != null) {
//                query += " AND trigger_datetime <= '" + filter.getTriggerDateTimeTo() + "'";
//            }

            if (filter.getSortColumns() != null) {
                query += filter.getSortColumns().orderBy();
            }

            if (filter.getPage() != null && filter.getPageSize() != null) {
                query += " LIMIT " + filter.getPageSize() * (filter.getPage() - 1L) + ", " + filter.getPageSize();
            }
        }
        log.trace(query);
        return prepareTriggeredAdvicesData(jdbcTemplate.queryForList(query));
    }

    private List<TriggeredAdviceData> prepareTriggeredAdvicesData(List<Map<String, Object>> rows) {
        List<TriggeredAdviceData> list = new ArrayList<>();
        for (Map<String, Object> row : rows) {
            list.add(new TriggeredAdviceData(
                    Long.valueOf(String.valueOf(row.get("id"))),
                    Long.valueOf(String.valueOf(row.get("advice_id"))),
                    String.valueOf(row.get("app_id")),
                    String.valueOf(row.get("context")),
                    String.valueOf(row.get("context_id")),
                    String.valueOf(row.get("content")),
                    String.valueOf(row.get("title")),
                    AdviseType.fromCode(String.valueOf(row.get("type"))),
                    String.valueOf(row.get("lang")),
                    row.get("data_type") == null ? null : AdviseDataType.valueOf(String.valueOf(row.get("data_type"))),
                    TriggeredAdviceStatus.fromCode(String.valueOf(row.get("status"))),
                    String.valueOf(row.get("variable_name")),
                    String.valueOf(row.get("combo_json")),
                    row.get("response_value") == null ? null : String.valueOf(row.get("response_value"))
                    //row.get("trigger_datetime") != null ? LocalDateTime.parse(String.valueOf(row.get("trigger_datetime")), dateTimeFormatter) : null,


            ));
        }

        return list;
    }

    void updateTriggeredAdvice(TriggeredAdviceData data) {
        String query = "UPDATE triggered_advices " +
                "SET status = ?, response_value = ?, data_type = ? " +
                "WHERE id = ?;";
        jdbcTemplate.update(query, data.getStatus().code(), data.getResponseValue(), data.getDataType() != null ? data.getDataType().name() : null, data.getId());
    }

    void deleteTriggeredAdvice(Long id) {
        String query = "DELETE FROM triggered_advices WHERE id=?;";
        jdbcTemplate.update(query, id);
    }

    Long getTriggeredAdvicesCount() {
        String query = "SELECT COUNT(id) AS count FROM triggered_advices";
        return jdbcTemplate.queryForObject(query, (rs, rowNum) -> Long.valueOf(rs.getString("count")));
    }

    Long getAdvicesCount() {
        String query = "SELECT COUNT(id) AS count FROM advices";
        return jdbcTemplate.queryForObject(query, (rs, rowNum) -> Long.valueOf(rs.getString("count")));
    }

    void log(TriggeredAdviceLogData data) {
        String query = "INSERT INTO advices_log (advice_id,\n" +
                "triggered_advice_id,\n" +
                "app_id,\n" +
                "domain,\n" +
                "message,\n" +
                "date_time) VALUES (?, ?, ?, ?, ?, ?);";
        jdbcTemplate.update(query, data.getAdviceId(), data.getTriggeredAdviceId(), data.getAppId(), data.getDomain(), data.getMessage(),
                LocalDateTime.now());
    }
}
