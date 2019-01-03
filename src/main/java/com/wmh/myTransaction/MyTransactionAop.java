package com.wmh.myTransaction;

import com.wmh.myTransaction.util.TransactionUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.TransactionStatus;

import java.lang.reflect.Method;

/**
 * Created by 周大侠
 * 2018-11-24 12:02
 */

@Component
@Aspect
public class MyTransactionAop {
    @Autowired
    private TransactionUtils transactionUtils;

    @Pointcut("execution(* com.wmh.myTransaction.service.*.*(..))")
    public void pc() {

    }

    @AfterThrowing("pc()")
    public void afterThrowing(){

        transactionUtils.rollback();
    }
    @Around("pc()")
    public void around(ProceedingJoinPoint pjp) throws Throwable {

        /*
         * 1.获取该方法上的注解
         * 2.判断有无MyTransaction
         * 3.如果有 开启事务
         * 4.执行该方法
         * 5.提交事务
         */
        // 获取事务注解
        MyTransaction annotation = getMyTransaction(pjp);
        // 开启事务
        TransactionStatus transactionStatus = getTransactionStatus(annotation);
        // 执行目标方法
        pjp.proceed();
        // 提交事务
        commit(transactionStatus);
    }

    private void commit(TransactionStatus transactionStatus) {
        if (transactionStatus != null) {
            transactionUtils.commit();
        }
    }

    private TransactionStatus getTransactionStatus(MyTransaction annotation) {
        TransactionStatus transactionStatus = null;
        if (annotation != null) {
            transactionStatus = transactionUtils.begin();
        }
        return transactionStatus;
    }

    private MyTransaction getMyTransaction(ProceedingJoinPoint pjp) throws NoSuchMethodException {
        // 获取方法名称
        String methodName = pjp.getSignature().getName();
        // 获取目标对象
        Class<?> classTarget = pjp.getTarget().getClass();
        // 获取目标对象类型
        Class<?>[] par = ((MethodSignature) pjp.getSignature()).getParameterTypes();
        // 获取目标对象方法
        Method objMethod = classTarget.getMethod(methodName, par);
        // 获取该方法上的事务注解
        return objMethod.getDeclaredAnnotation(MyTransaction.class);
    }


}
