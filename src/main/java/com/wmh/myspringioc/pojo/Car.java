package com.wmh.myspringioc.pojo;

/**
 * Created by 周大侠
 * 2018-11-26 9:57
 */
public class Car {
    private String name;
    private String color;

    public Car() {
        System.out.println("我被初始化了");

    }

    @Override
    public String toString() {
        return "Car{" +
                "name='" + name + '\'' +
                ", color='" + color + '\'' +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}
