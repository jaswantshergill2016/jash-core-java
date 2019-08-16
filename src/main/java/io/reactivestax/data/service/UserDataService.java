package io.reactivestax.data.service;

import io.reactivestax.domain.User;

public interface UserDataService {

    void createUser(User user);

    User retrieveUser(String email);
}
