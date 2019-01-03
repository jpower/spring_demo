package com.wmh.myspringioc;

import com.wmh.myspringioc.annotation.MyResource;
import com.wmh.myspringioc.annotation.MyService;
import com.wmh.myspringioc.pojo.Car;
import com.wmh.myspringioc.pojo.User;

/**
 * Created by 周大侠
 * 2018-11-26 11:49
 */
@MyService
public class UserService {
    @MyResource
    private User user;
    @MyResource
    private Car carrr;
    public void shabi(){
        System.out.println(user);
        System.out.println(carrr);
    }
}
