package org.tutict.example.provider;


import org.tutict.common.model.User;
import org.tutict.common.service.UserService;

public class UserServiceImpl implements UserService {

    public User getUser(User user) {
        System.out.println("username" + user.getName());
        return user;
    }
}
