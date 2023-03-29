package pl.com.goodsolution.adviser.logic.adviser.advices;

import pl.com.goodsolution.adviser.logic.adviser.Advice;
import pl.com.goodsolution.adviser.logic.adviser.AdviceContext;
import pl.com.goodsolution.adviser.logic.adviser.AdviceOperationsService;
import pl.com.goodsolution.adviser.logic.adviser.TriggeredResult;

public class JustDisplayAdvice implements Advice {
    private AdviceOperationsService adviceOperationsService;

    public JustDisplayAdvice(AdviceOperationsService adviceOperationsService) {
        this.adviceOperationsService = adviceOperationsService;
    }

    @Override
    public TriggeredResult triggered(AdviceContext ctx) {
        return TriggeredResult.triggered(ctx.getAdvice().getContent());
    }
}
