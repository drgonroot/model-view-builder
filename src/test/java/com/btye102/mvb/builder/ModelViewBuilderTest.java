package com.btye102.mvb.builder;

import com.btye102.mvb.dao.UserDAO;
import com.btye102.mvb.model.TestModel;
import com.btye102.mvb.model.TestView;
import com.btye102.mvb.model.UserView;
import org.junit.jupiter.api.Test;

class ModelViewBuilderTest {

    @Test
    public void build() throws Exception {
        ModelViewBuilder modelViewBuilder = new ModelViewBuilder(new UserDAO());
        TestView testView = modelViewBuilder.build(new TestModel("testName", 1), TestView.class, new BuildContext());
        System.out.println(testView.getName());
        System.out.println(testView.getOwner());
        System.out.println(testView.getOwner2());
        UserView userView = testView.getUser();
        System.out.println(userView);
        System.out.println(userView.getName());
        System.out.println(testView.getUser1());

//        UserView userView1 = modelViewBuilder.build(new User("用户"), UserView.class, new BuildContext());
//        System.out.println(userView1.getName());
    }
}