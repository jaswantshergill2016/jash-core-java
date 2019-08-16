package io.reactivestax.data.service;

import io.reactivestax.data.repository.UserRepository;
import io.reactivestax.domain.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UserDataServiceImpl implements  UserDataService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public void createUser(User user) {
        userRepository.save(user);
    }

    @Override
    public User retrieveUser(String username) {
        return userRepository.findUserByEmail(username);
    }
}
