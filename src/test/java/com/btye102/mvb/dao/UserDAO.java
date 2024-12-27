package com.btye102.mvb.dao;

import com.btye102.mvb.builder.ModelDaoFactory;
import com.btye102.mvb.builder.GetModel;
import com.btye102.mvb.model.User;

/**
 * @author: rd13
 * @since: 2024/12/24
 **/
public class UserDAO implements ModelDaoFactory, GetModel<Integer, User> {

    public User accept(Integer id) {
        return new User("用户名称");
    }

    @Override
    public User getModel(Integer integer) {
        return new User("用户名称");
    }

    @Override
    public <T> T getModelDao(Class<T> modelDaoClass) {
        return (T)new UserDAO();
    }
}
