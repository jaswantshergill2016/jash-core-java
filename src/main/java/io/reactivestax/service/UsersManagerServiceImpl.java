package io.reactivestax.service;

import io.reactivestax.data.service.UserDataService;
import io.reactivestax.domain.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UsersManagerServiceImpl implements UsersManagerService {


    @Autowired
    private UserDataService userDataService;


    @Override
    public void registerUser(User user) {
        userDataService.createUser(user);
    }

    @Override
    public User getUser(String email) {
        return userDataService.retrieveUser(email);
    }

    @Override
    public boolean authenticateUser(String username, String password) {
        return false;
    }
}
