package pl.com.mike.developer.logic.adviser.advices;

import pl.com.mike.developer.logic.adviser.Advice;
import pl.com.mike.developer.logic.adviser.AdviceContext;
import pl.com.mike.developer.logic.adviser.AdviceOperationsService;
import pl.com.mike.developer.logic.adviser.TriggeredResult;

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
