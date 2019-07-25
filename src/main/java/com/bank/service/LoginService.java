package com.bank.service;

import com.bank.dao.LoginDAO;
import com.bank.domain.Login;
import com.bank.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class LoginService {

    @Autowired
    LoginDAO loginDAO;

    public User validateUser(Login login) {

        System.out.println("======In validateUser ===========");

        return loginDAO.getUser(login);


    }
}
