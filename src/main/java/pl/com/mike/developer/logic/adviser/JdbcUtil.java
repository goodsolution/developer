package pl.com.mike.developer.logic.adviser;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

public class JdbcUtil {
    public enum SortDirection {
        ASC, DESC
    }

    public static class SortColumn {
        private String name;
        private SortDirection direction;

        public SortColumn(String name) {
            this.name = name;
            this.direction = SortDirection.ASC;
        }

        public SortColumn(String name, SortDirection direction) {
            this.name = name;
            this.direction = direction;
        }

        public String getName() {
            return name;
        }

        public SortDirection getDirection() {
            return direction;
        }
    }

    public static class SortColumns {
        private List<SortColumn> columns;

        public SortColumns(List<SortColumn> columns) {
            this.columns = columns;
        }

        public String orderBy() {
            String orderBy = " ORDER BY ";
            for (int i = 0; i < columns.size(); i++) {
                SortColumn column = columns.get(i);
                orderBy += column.getName() + " " + column.getDirection().name();
                if (i < columns.size() - 1) {
                    orderBy += ",";
                }
            }
            return orderBy;
        }


    }

    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-LL-dd HH:mm:ss");
    private static final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-LL-dd");

    public static String preparePaginationQuery(Long page, Long pageSize) {
        if (page != null && pageSize != null && page > 0 && pageSize > 0) {
            long offset = pageSize * (page - 1L);
            return " LIMIT " + offset + ", " + pageSize;
        }
        return "";
    }

    public static Long getLong(ResultSet rs, String columnLabel) throws SQLException {

        long number = rs.getLong(columnLabel);

        if (rs.wasNull()) {
            return null;
        } else {
            return number;
        }
    }

    public static Integer getInteger(ResultSet rs, String columnLabel) throws SQLException {

        int number = rs.getInt(columnLabel);

        if (rs.wasNull()) {
            return null;
        } else {
            return number;
        }
    }

    public static Integer getInteger(Map<String, Object> row, String columnLabel) {
        String stringValue = String.valueOf(row.get(columnLabel));

        if (stringValue.equals("null")) {
            return null;
        } else {
            return Integer.valueOf(stringValue);
        }
    }

    public static Double getDouble(Map<String, Object> row, String columnLabel) {
        String stringValue = String.valueOf(row.get(columnLabel));
        if (stringValue.equals("null")) {
            return null;
        } else {
            return Double.valueOf(stringValue);
        }
    }

    public static Long getLong(Map<String, Object> row, String columnLabel) {
        String stringValue = String.valueOf(row.get(columnLabel));

        if (stringValue.equals("null")) {
            return null;
        } else {
            return Long.valueOf(stringValue);
        }
    }

    public static BigDecimal getBigDecimal(ResultSet rs, String columnLabel) throws SQLException {
        return rs.getBigDecimal(columnLabel);
    }

    public static BigDecimal getBigDecimal(Map<String, Object> row, String columnLabel) {
        String stringValue = String.valueOf(row.get(columnLabel));
        return new BigDecimal(stringValue);
    }

    public static BigDecimal getBigDecimal(Map<String, Object> row, String columnLabel, int scale, RoundingMode roundingMode) {
        return getBigDecimal(row, columnLabel).setScale(scale, roundingMode);
    }

    public static String getString(ResultSet rs, String columnLabel) throws SQLException {
        String value = rs.getString(columnLabel);
        if (value == null || value.equals("null")) {
            return null;
        } else {
            return value;
        }
    }

    public static String getString(Map<String, Object> row, String columnLabel) {
        String value = String.valueOf(row.get(columnLabel));
        if (value.equals("null")) {
            return null;
        } else {
            return value;
        }
    }

    public static Boolean getBoolean(ResultSet rs, String columnLabel) throws SQLException {
        return rs.getBoolean(columnLabel);
    }

    public static Boolean getBoolean(Map<String, Object> row, String columnLabel) {

        String booleanAsString = String.valueOf(row.get(columnLabel));

        if (booleanAsString == null || booleanAsString.equals("null")) {
            return null;
        } else if (booleanAsString.equals("true") || booleanAsString.equals("1")) {
            return true;
        } else if (booleanAsString.equals("false") || booleanAsString.equals("0")) {
            return false;
        } else {
            throw new IllegalArgumentException("Incorrect data in database");
        }

    }

    public static LocalDateTime getDateTime(ResultSet rs, String columnLabel) throws SQLException {
        return textToDateTime(String.valueOf(rs.getString(columnLabel)));
    }

    public static LocalDate getDate(ResultSet rs, String columnLabel) throws SQLException {
        return textToDate(String.valueOf(rs.getString(columnLabel)));
    }

    public static LocalDateTime getDateTime(Map<String, Object> row, String columnLabel) {
        return textToDateTime(String.valueOf(row.get(columnLabel)));
    }

    public static LocalDate getDate(Map<String, Object> row, String columnLabel) {
        return textToDate(String.valueOf(row.get(columnLabel)));
    }

    private static LocalDateTime textToDateTime(String text) {
        text = trimToDateSize(text);

        if (!text.equals("null") && !text.equals("")) {
            return LocalDateTime.parse(text, dateTimeFormatter);
        } else {
            return null;
        }
    }

    private static LocalDate textToDate(String text) {
        text = trimToDateSize(text);

        if (!text.equals("null") && !text.equals("")) {
            return LocalDate.parse(text, dateFormatter);
        } else {
            return null;
        }
    }

    private static String trimToDateSize(String text) {
        if (text.length() > 19) {
            return text.substring(0, 19);
        } else {
            return text;
        }
    }
}
