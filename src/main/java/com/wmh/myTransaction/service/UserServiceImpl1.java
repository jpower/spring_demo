package com.wmh.myTransaction.service;

import com.wmh.myTransaction.MyTransaction;
import com.wmh.programmeTransaction.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by 周大侠
 * 2018-11-24 11:07
 */
@Service
public class UserServiceImpl1 implements UserService1 {
    @Autowired
    private UserDao userDao;

    @Override
    @MyTransaction
    public void add() {

        userDao.add("522", "www");
        int i =1/0;
        userDao.add("521", "eee");
    }
    public void fun1(){
        int i =1/0;
        System.out.println("煞笔东西");
    }

}


