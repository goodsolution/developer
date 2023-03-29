package pl.com.goodsolution.adviser.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.com.goodsolution.adviser.logic.adviser.AdviceOperationsService;
import pl.com.goodsolution.adviser.logic.adviser.advices.EveryFourthSecondAdvice;
import pl.com.goodsolution.adviser.logic.adviser.advices.EverySecondMinuteAdvice;
import pl.com.goodsolution.adviser.logic.adviser.advices.JustDisplayAdvice;
import pl.com.goodsolution.adviser.logic.adviser.evaluator.AdviseSimpleEvaluator;
import pl.com.goodsolution.adviser.logic.adviser.evaluator.Evaluator;

@Configuration
public class AdviserConfig {

    private AdviceOperationsService adviceOperationsService;

    public AdviserConfig(AdviceOperationsService adviceOperationsService) {
        this.adviceOperationsService = adviceOperationsService;
    }

    @Value("${adviser-active}")
    private Boolean adviserActive;

    public Boolean getAdviserActive() {
        return adviserActive;
    }

    @Bean
    public Evaluator evaluator() {
        return new AdviseSimpleEvaluator();
    }


    @Bean(name = "EveryFourthSecondAdvice")
    public EveryFourthSecondAdvice everyFourthSecondAdvice() {
        return new EveryFourthSecondAdvice();
    }

    @Bean(name = "EverySecondMinuteAdvice")
    public EverySecondMinuteAdvice everySecondMinuteAdvice() {
        return new EverySecondMinuteAdvice();
    }

    @Bean(name = "JustDisplayAdvice")
    public JustDisplayAdvice justDisplayAdvice() {
        return new JustDisplayAdvice(adviceOperationsService);
    }
}
