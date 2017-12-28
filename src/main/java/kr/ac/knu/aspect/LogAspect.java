package kr.ac.knu.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

/**
 * Created by rokim on 2017. 12. 19..
 */
@Aspect
@Component

public class LogAspect {

    @Around("execution(* kr.ac.knu.repository.*.* (..))")
    public Object log(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        long before = System.currentTimeMillis();


        Object obj = joinPoint.proceed();


        long after = System.currentTimeMillis();

        System.out.println(signature.getMethod().getName() + " 이 걸린 시간 입니다." + (after - before));
        return obj;
    }
}
