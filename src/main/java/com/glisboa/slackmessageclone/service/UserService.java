package com.glisboa.slackmessageclone.service;


import com.glisboa.slackmessageclone.entity.User;
import com.glisboa.slackmessageclone.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public Collection<User> getAllUsers() {
        return userRepository.getAllUsers();
    }

    public User getUserById(Integer id) {
        return userRepository.getUserById(id);
    }

    public void updateUser(User updatesUser) {
        userRepository.updateUser(updatesUser);
    }

    public void createUser(User user) {
        userRepository.createUser(user);
    }

    public void deleteUserById(Integer id) {
        userRepository.deleteUserById(id);
    }
}
