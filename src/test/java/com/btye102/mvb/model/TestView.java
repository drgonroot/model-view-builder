package com.btye102.mvb.model;

import com.btye102.mvb.annotation.RelationModel;
import com.btye102.mvb.annotation.RelationView;
import com.btye102.mvb.annotation.View;
import com.btye102.mvb.dao.UserDAO;

@View(modelClass = TestModel.class)
public interface TestView {
    String getName();

    @RelationView(bindModelAttrGetMethod = "getUserId()", viewClass = UserView.class)
    UserView getUser();

    @RelationModel(bindModelAttrGetMethod = "getUserId()", modelDaoClass= UserDAO.class, modelDaoMethod = "accept(java.lang.Integer)")
    User getUser1();

    default String getOwner() {
        return "getOwner";
    }

    default String getOwner2() {
        return getOwner() + "2";
    }
}
