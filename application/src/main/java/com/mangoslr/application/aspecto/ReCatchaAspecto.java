package com.mangoslr.application.aspecto;

import com.mangoslr.application.servicio.ReCaptchaValidacion;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

@Aspect
@Component
public class ReCatchaAspecto {
    @Autowired
    private ReCaptchaValidacion validacion;

    private static final String CAPTCHA_HEADER_NAME = "reCaptcha";

    @Around("@annotation(NecesitaReCaptcha)")
    public Object validateCaptcha(ProceedingJoinPoint joinPoint) throws Throwable {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        String captchaResponse = request.getHeader(CAPTCHA_HEADER_NAME);
        boolean isValidCaptcha = validacion.validateCaptcha(captchaResponse);
        if(!isValidCaptcha){
            throw new RuntimeException("Invalid captcha");
        }
        return joinPoint.proceed();
    }
}
