package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.User;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;

public class UserDaoTests extends BaseDaoTests{

    private JdbcUserDao sut;
    private User testUser = new User(1001L, "testUserOne", "","Authority{name=ROLE_USER}");

    @Before
    public void setup() {
        sut = new JdbcUserDao(new JdbcTemplate(dataSource));
        testUser.setAuthorities("USER");
    }


    @Test
    public void find_all_user_returns_array_of_users(){
        User[] testArrayOfUsers =  sut.findAll("testUserOne");
        Assert.assertEquals(3, testArrayOfUsers.length);
    }

    @Test
    public void create_user_returns_true(){
        boolean wasUserCreated = sut.create("testUserZero", "password");
        Assert.assertTrue(wasUserCreated);
    }

    @Test
    public void find_by_username_returns_a_user(){
        User testUserOne = sut.findByUsername("testUserOne");
        Assert.assertEquals(testUser,testUserOne);
    }
}
