package net.khaibq.javabackend.config.annotation;

import lombok.extern.slf4j.Slf4j;
import net.khaibq.javabackend.config.security.CustomUser;
import net.khaibq.javabackend.ultis.SecurityUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class CheckLevelValidator {
    @Pointcut(value = "execution(* *.*(..))")
    public void allMethods() {
    }

    @Around("@annotation(net.khaibq.javabackend.config.annotation.CheckLevel)")
    public Object level(ProceedingJoinPoint joinPoint) throws Throwable {
//        Object[] args = joinPoint.getArgs();
//        for (Object obj : args){
//            System.out.println("===" + obj.toString());
//        }
        var checkLevel = ((MethodSignature) joinPoint.getSignature()).getMethod().getAnnotation(CheckLevel.class);
        if (SecurityUtils.isAuthenticated()) {
            UserDetails userDetails = SecurityUtils.getCurrentUser().orElse(null);
            if (userDetails instanceof CustomUser customUser &&
                (customUser.getLevel() != null && customUser.getLevel() >= checkLevel.value())) {
                return joinPoint.proceed();
            }
        }
        throw new AccessDeniedException("Truy cập không được phép");
    }
}
