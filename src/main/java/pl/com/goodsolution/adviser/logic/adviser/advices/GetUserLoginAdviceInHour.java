package pl.com.goodsolution.adviser.logic.adviser.advices;

import pl.com.goodsolution.adviser.logic.adviser.Advice;
import pl.com.goodsolution.adviser.logic.adviser.AdviceContext;
import pl.com.goodsolution.adviser.logic.adviser.TriggeredResult;

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
