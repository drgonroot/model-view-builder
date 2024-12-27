package com.btye102.mvb.model;

import com.btye102.mvb.annotation.View;
import com.btye102.mvb.dao.UserDAO;

/**
 * @author: rd13
 * @since: 2024/12/24
 **/
@View(modelClass = User.class, modelDaoClass = UserDAO.class)
public interface UserView {
    String getName();
}
