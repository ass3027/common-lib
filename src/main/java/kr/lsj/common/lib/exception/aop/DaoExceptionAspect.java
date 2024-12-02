package kr.lsj.common.lib.exception.aop;

import kr.lsj.common.lib.exception.CustomException;
import kr.lsj.common.lib.exception.ExceptionEnum;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
public class DaoExceptionAspect {

    @Pointcut("@annotation(org.apache.ibatis.annotations.Mapper)")
    public void allMapper() {}

    @Pointcut("allMapper() && execution(int *.*(..)) ")
    private void cudOperation() {}

    @AfterReturning(pointcut = "cudOperation()", returning = "returnValue")
    public void afterReturning(JoinPoint joinPoint, int returnValue) {

        if(returnValue == 0){
            String method = joinPoint.getSignature().getName().substring(0, 5);
            switch (method) {
                case "insert":
                    throw new CustomException(ExceptionEnum.NO_INSERTED);
                case "update":
                    throw new CustomException(ExceptionEnum.NO_UPDATED);
                case "delete":
                    throw new CustomException(ExceptionEnum.NO_DELETED);
            }
        }
    }

    @AfterThrowing(value = "allMapper()",throwing = "ex")
    public void logParameter(JoinPoint jp, Exception ex) throws Exception {
        int index=0;
        for (Object arg : jp.getArgs()) {
            log.error("args no.{} : {}",index,arg.toString());
            index++;
        }
        throw ex;
    }
}
