package pl.com.mike.developer.logic.adviser.advices;

import pl.com.mike.developer.logic.adviser.Advice;
import pl.com.mike.developer.logic.adviser.AdviceContext;
import pl.com.mike.developer.logic.adviser.TriggeredResult;

import java.time.LocalDateTime;

public class GetUserLoginAdviceInHour implements Advice {
    @Override
    public TriggeredResult triggered(AdviceContext ctx) {
        if (LocalDateTime.now().getHour() == 21) {
            return TriggeredResult.triggered(ctx.getDataSource().get("user", "login"));
        }
        return TriggeredResult.noTriggered();
    }
}
