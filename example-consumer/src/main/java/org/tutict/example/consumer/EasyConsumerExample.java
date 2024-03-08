package org.tutict.example.consumer;


import org.tutict.common.model.User;
import org.tutict.common.service.UserService;

public class EasyConsumerExample {

    public static void main(String[] args) {
        UserService userService = new UserServiceProxy();
        User user  = new User();
        user.setName("tutict");

        User newUser = userService.getUser(user);
        if(newUser != null) {
            System.out.println("username: " + newUser.getName());
        } else {
            System.out.println("user == null");
        }
    }
}

