package io.reactivestax.service;

import io.reactivestax.domain.User;

public interface UsersManagerService {

    void registerUser(User user);

    User getUser(String email);

    boolean authenticateUser(String username, String password);
}
