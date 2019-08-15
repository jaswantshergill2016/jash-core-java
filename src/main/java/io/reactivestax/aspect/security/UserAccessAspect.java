package io.reactivestax.aspect.security;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;

import org.springframework.context.annotation.Configuration;

@Aspect
@Configuration
@Slf4j
public class UserAccessAspect {


    //What kind of method calls I would intercept
    //execution(* PACKAGE.*.*(..))
    //Weaving & Weaver
    @Before("execution(* io.reactivestax.web.controller.*.*(..))")
    public void before(JoinPoint joinPoint){
        //Advice

        log.debug("perform the validation for authorization ");
        log.info(" Allowed execution for {}", joinPoint);
    }
}



