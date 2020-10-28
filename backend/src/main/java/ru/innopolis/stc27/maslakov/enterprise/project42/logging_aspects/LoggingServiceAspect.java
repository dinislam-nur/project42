package ru.innopolis.stc27.maslakov.enterprise.project42.logging_aspects;

import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import ru.innopolis.stc27.maslakov.enterprise.project42.entities.session.Session;

import java.util.Arrays;

@Slf4j
@Aspect
@Component
public class LoggingServiceAspect {

    @Pointcut(value = "execution(public * ru.innopolis.stc27.maslakov.enterprise.project42.services.*.*.*(..))")
    public void callAtServices() {}

    @Around("callAtServices()")
    public Object logSuccessCall(ProceedingJoinPoint point) throws Throwable {

        val signature = point.getSignature();
        val fullNameClass = signature.getDeclaringType().getName().split("\\.");
        val args = point.getArgs();
        val methodName = signature.getName();

        try {
            val details = SecurityContextHolder.getContext().getAuthentication().getDetails();
            if (details instanceof Session) {
                val user = ((Session) details).getUser();
                log.info("Пользователь: " + user);
            }
            log.info("Вызван метод '" + methodName + "()' на сервисе '" + fullNameClass[fullNameClass.length - 1] + "' с входными аргументами " + Arrays.toString(args));

            val result = point.proceed();

            log.info("Метод '" + methodName + "()' успешно выполнился");
            return result;
        } catch (Throwable throwable) {
            log.info("Метод '" + methodName + "()' выбросил исключение");
            throw throwable;
        }
    }
}
