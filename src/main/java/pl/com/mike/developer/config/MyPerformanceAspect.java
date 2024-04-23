package pl.com.mike.developer.config;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

@Aspect
public class MyPerformanceAspect {

    @Pointcut("execution(public * pl.com.mike.developer.logic.developer.PremiseService.fetchTranslation(..))")
    public void myMonitor() {}
}

