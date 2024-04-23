package pl.com.mike.developer.config;

import org.springframework.aop.Advisor;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Lazy;

@Configuration
@EnableAspectJAutoProxy
@Lazy
public class PerformanceMonitoringConfiguration {

    @Bean
    public MyPerformanceMonitorInterceptor myPerformanceMonitorInterceptor() {
        return new MyPerformanceMonitorInterceptor(true);
    }

    @Bean
    public Advisor myPerformanceMonitorAdvisor(MyPerformanceMonitorInterceptor interceptor) {
        AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
        pointcut.setExpression("execution(public * pl.com.mike.developer.logic.developer.PremiseService.fetchTranslation(..))");
        return new DefaultPointcutAdvisor(pointcut, interceptor);
    }
}


