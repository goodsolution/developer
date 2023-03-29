package pl.com.goodsolution.adviser.logic;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import pl.com.goodsolution.adviser.domain.ViewerData;

@Service
public class ViewersService {
    private static final Logger log = LoggerFactory.getLogger(ViewersService.class);
    private ViewersJdbcRepository viewersJdbcRepository;

    public ViewersService(ViewersJdbcRepository viewersJdbcRepository) {
        this.viewersJdbcRepository = viewersJdbcRepository;
    }

    public ViewerData get(String sessionId) {
        long startTime = System.currentTimeMillis();
        ViewerData data = viewersJdbcRepository.get(sessionId);
        long timeTaken = System.currentTimeMillis() - startTime;
        log.info("Time Taken by {} is {}", "getViewer", timeTaken);
        return data;
    }
}
