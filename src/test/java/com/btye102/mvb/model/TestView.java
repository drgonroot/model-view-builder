package com.btye102.mvb.model;

import com.btye102.mvb.annotation.RelationModel;
import com.btye102.mvb.annotation.RelationView;
import com.btye102.mvb.annotation.View;
import com.btye102.mvb.dao.UserDAO;
import com.btye102.mvb.proxy.instance.ProxyType;

@View(modelClass = TestModel.class, proxyType = ProxyType.CGLIB)
public abstract class TestView {
    public abstract String getName();

    @RelationView(bindModelAttrGetMethod = "getUserId()", viewClass = UserView.class)
    public abstract UserView getUser();

    @RelationModel(bindModelAttrGetMethod = "getUserId()", modelDaoClass= UserDAO.class, modelDaoMethod = "accept(java.lang.Integer)")
    public abstract User getUser1();

    public  String getOwner() {
        return "getOwner";
    }

    public  String getOwner2() {
        return getOwner() + "2";
    }
}
