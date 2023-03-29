package pl.com.goodsolution.adviser.logic.courseplatform;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import pl.com.goodsolution.adviser.domain.courseplatform.TraceData;

import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static pl.com.goodsolution.adviser.logic.adviser.JdbcUtil.*;

@Service
public class TraceJdbcRepository {

    private JdbcTemplate jdbcTemplate;

    public TraceJdbcRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    void create(TraceData data) {
        String query = "INSERT INTO crs_action_trace (what, value, who, creation_datetime, ip_address, browser, operating_system) values(?,?,?,?,?,?,?)";
        jdbcTemplate.update(query, data.getWhat(), data.getValue(), data.getWho(), data.getLocalDateTime(), data.getIpAddress(), data.getBrowser(), data.getOperatingSystem());
    }

    List<TraceData> findTracesPerDay(Month month) {
        String query = "SELECT creation_datetime, what, value, count(id) as total " +
                " FROM crs_action_trace" +
                " WHERE  MONTH(creation_datetime) = ?" +
                " GROUP BY what, value " +
                " ORDER BY creation_datetime, what ";

        List<Map<String, Object>> rows = jdbcTemplate.queryForList(query, month.getValue());
        return prepareTraces(rows);
    }

    List<TraceData> findTracesPerMonth(Long year) {
        String query = "SELECT creation_datetime, what, value, count(id) as total " +
                " FROM crs_action_trace" +
                " WHERE  YEAR(creation_datetime) = " + year +
                " GROUP BY value ";

        List<Map<String, Object>> rows = jdbcTemplate.queryForList(query);
        return prepareTraces(rows);
    }

    private static List<TraceData> prepareTraces(List<Map<String, Object>> rows) {
        List<TraceData> list = new ArrayList<>();
        for (Map<String, Object> row : rows) {
            list.add(new TraceData(
                    getLong(row, "total" ),
                    getString(row, "what"),
                    getString(row,"value"),
                    getDateTime(row, "creation_datetime")
            ));
        }
        return list;
    }
}
