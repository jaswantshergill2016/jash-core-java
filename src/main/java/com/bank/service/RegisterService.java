package com.bank.service;

import com.bank.dao.RegisterDAO;
import com.bank.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RegisterService {

    @Autowired
    RegisterDAO registerDAO;


    public void register(User user) {
        registerDAO.register(user);
    }
}
