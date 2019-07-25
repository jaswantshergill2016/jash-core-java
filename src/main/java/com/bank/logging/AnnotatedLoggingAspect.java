package com.bank.logging;

import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.web.servlet.ModelAndView;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

@Configuration
@EnableAspectJAutoProxy(proxyTargetClass=true)
@ComponentScan(basePackages = {"com.bank"})
@Aspect
public class AnnotatedLoggingAspect {

    private static Logger logger = Logger.getLogger(AnnotatedLoggingAspect.class);

    static {
        System.out.println("*************AnnotatedLoggingAspect**********loaded ");
        logger.debug("*************AnnotatedLoggingAspect**********loaded ");
    }




    //@Before("execution(* com.bank.LoginController.showLogin(..))")
    //@Before("execution(* showLogin(..))")
    @Before("execution(* com.bank.LoginController.loginProcess(..))")
    public void logBefore(JoinPoint joinPoint) {
        logger.debug("******************************************");
        logger.debug("logBefore() is running!");
        logger.debug("method intercepted : " + joinPoint.getSignature().getName());
        logger.debug("******************************************");

        /*
        System.out.println("******************************************");
        System.out.println("logBefore() is running!");
        System.out.println("method intercepted : " + joinPoint.getSignature().getName());
        System.out.println("******************************************");
        */
    }

    @After("execution(* com.bank.LoginController.loginProcess(..))")
    public void logAfter(JoinPoint joinPoint) {

        System.out.println("logAfter() is running!");
        System.out.println("method intercepted : " + joinPoint.getSignature().getName());
        System.out.println("******");

    }
    /*
    @AfterReturning(
            pointcut = "execution(* com.bank.LoginController.loginProcess(..))",
            returning = "result")
    public void logAfterReturning(JoinPoint joinPoint, Object result) {

        System.out.println("logAfterReturning() is running!");
        System.out.println("method intercepted : " + joinPoint.getSignature().getName());
        System.out.println("Method returned value is : " + result);
        System.out.println("******");

    }
    */
    /*
    @AfterThrowing(
            pointcut = "execution(* com.bank.LoginController.loginProcess(..))",
            throwing = "error")
    public void logAfterThrowing(JoinPoint joinPoint, Throwable error) {

        System.out.println("logAfterThrowing() is running!");
        System.out.println("method intercepted : " + joinPoint.getSignature().getName());
        System.out.println("Exception : " + error);
        System.out.println("******");

    }
    */


    //@Around("execution(* com.bank.LoginController.loginProcess(..))")
    @Around("execution(org.springframework.web.servlet.ModelAndView com.bank.LoginController.loginProcess(..))")
    public ModelAndView logAround(ProceedingJoinPoint joinPoint) throws Throwable {

        System.out.println("logAround() is running!");
        String methodName = joinPoint.getSignature().getName();
        System.out.println("method intercepted method : " + methodName);
        System.out.println("method intercepted arguments : " + Arrays.toString(joinPoint.getArgs()));

        System.out.println("Around before is running!");
        long start = System.nanoTime();
        ModelAndView returnType = (ModelAndView)joinPoint.proceed();
        long end = System.nanoTime();
        System.out.println("Execution of " + methodName + " took " + TimeUnit.NANOSECONDS.toMillis(end - start) + " ms");
        System.out.println("Around after is running!");

        System.out.println("******");

        return returnType;

    }



}
