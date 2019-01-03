package com.wmh.myspringioc.pojo;

/**
 * Created by 周大侠
 * 2018-11-26 9:56
 */
public class User {
    private String name;
    private Integer age;
    private Integer height;

    public User() {
        System.out.println("我被初始化了");
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", height=" + height +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }
}
