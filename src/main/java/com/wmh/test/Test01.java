package com.wmh.test;

import com.wmh.myTransaction.service.UserService1;
import com.wmh.myTransaction.service.UserServiceImpl1;
import com.wmh.programmeTransaction.service.UserService;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by 周大侠
 * 2018-11-24 11:12
 */
public class Test01 {
    @Test
    public void fun1(){
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("spring.xml");
        UserService service = context.getBean(UserService.class);
        service.add("4","傻逼");
    }
    @Test
    public void fun2(){
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("spring1.xml");
        UserService1 service = context.getBean(UserService1.class);
//        service.add();
        service.fun1();

    }


}
