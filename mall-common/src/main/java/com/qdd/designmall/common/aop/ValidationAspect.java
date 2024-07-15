package com.qdd.designmall.common.aop;

import lombok.RequiredArgsConstructor;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.validation.Validator;
import org.springframework.validation.annotation.Validated;

import java.lang.reflect.Method;

@Aspect
@Component
@RequiredArgsConstructor
public class ValidationAspect {

    private final Validator validator;

    @Before("@within(org.springframework.web.bind.annotation.RestController)" +
            "&& (@annotation(org.springframework.web.bind.annotation.GetMapping)" +
            "|| @annotation(org.springframework.web.bind.annotation.PostMapping)" +
            "|| @annotation(org.springframework.web.bind.annotation.PutMapping)" +
            "|| @annotation(org.springframework.web.bind.annotation.DeleteMapping)" +
            "|| @annotation(org.springframework.web.bind.annotation.PatchMapping)" +
            "|| @annotation(org.springframework.web.bind.annotation.RequestMapping))")
    public void validateMethod(JoinPoint joinPoint) throws Throwable {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        Object[] args = joinPoint.getArgs();

        // Check if method or class is annotated with @Validated
        if (method.isAnnotationPresent(Validated.class) || method.getDeclaringClass().isAnnotationPresent(Validated.class)) {
            for (Object arg : args) {
                if (arg != null) {
                    validator.validateObject(arg).failOnError(IllegalStateException::new);
                }
            }
        }
    }
}