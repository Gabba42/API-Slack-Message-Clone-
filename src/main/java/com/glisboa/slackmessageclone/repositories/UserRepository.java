package com.glisboa.slackmessageclone.repositories;


import com.glisboa.slackmessageclone.entity.User;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Repository
public class UserRepository {

    private static Map<Integer, User> users;

    static {

        users = new HashMap<Integer, User>() {
            {
                put(1, new User(1, "Raul"));
                put(2, new User(2, "Greg"));
                put(3, new User(3, "Andrea"));
            }
        };
    }

    public Collection<User> getAllUsers() {
        return this.users.values();
    }

    public User getUserById(Integer id) {
        return this.users.get(id);
    }

    public void updateUser(User updatesUser) {
        User user1 = users.get(updatesUser.getId());
        user1.setName(updatesUser.getName());
        users.put(updatesUser.getId(), updatesUser);
    }

    public void createUser(User user) {
        users.put(user.getId(), user);
    }

    public void deleteUserById(Integer id) {
        users.remove(id);
    }
}
