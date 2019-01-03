package com.wmh.test;

import com.wmh.myspringioc.MyClassPathXmlApplicationContext;
import com.wmh.myspringioc.UserService;
import com.wmh.myspringioc.pojo.User;
import org.junit.Test;

import java.lang.reflect.Field;
import java.util.Arrays;

/**
 * Created by 周大侠
 * 2018-11-26 9:58
 */
public class Test02 {
    @Test
    public void fun1() throws Exception {
        MyClassPathXmlApplicationContext applicationContext = new MyClassPathXmlApplicationContext("myspringioc.xml");
        Object user = applicationContext.getBean("user");
        System.out.println(user);
        Object bean = applicationContext.getBean(User.class);
        System.out.println(user == bean);
    }

    @Test
    public void fun2() throws Exception {
        MyClassPathXmlApplicationContext applicationContext = new MyClassPathXmlApplicationContext("myspringioc.xml");
        UserService userService = (UserService) applicationContext.getBean("userService");
        userService.shabi();
    }


}

