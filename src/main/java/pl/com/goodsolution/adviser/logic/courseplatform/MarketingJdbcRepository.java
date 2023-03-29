package pl.com.goodsolution.adviser.logic.courseplatform;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import pl.com.goodsolution.adviser.domain.courseplatform.CourseData;
import pl.com.goodsolution.adviser.domain.courseplatform.CoursesFilter;
import pl.com.goodsolution.adviser.domain.courseplatform.StatisticData;
import pl.com.goodsolution.adviser.domain.courseplatform.StatisticSellingData;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static pl.com.goodsolution.adviser.logic.adviser.JdbcUtil.getBigDecimal;
import static pl.com.goodsolution.adviser.logic.adviser.JdbcUtil.getString;

@Service
public class MarketingJdbcRepository {

    private JdbcTemplate jdbcTemplate;

}
