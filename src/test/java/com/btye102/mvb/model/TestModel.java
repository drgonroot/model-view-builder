package com.btye102.mvb.model;

/**
 * @author: rd13
 * @since: 2024/12/24
 **/
public class TestModel {
    private final String name;
    private final Integer userId;

    public TestModel(String name, Integer userId) {
        this.name = name;
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public Integer getUserId() {
        return userId;
    }
}
