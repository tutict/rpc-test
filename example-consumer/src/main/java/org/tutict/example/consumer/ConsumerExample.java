package org.tutict.example.consumer;

import org.tutict.common.model.User;
import org.tutict.common.service.UserService;
import org.tutict.yurpc.config.RpcConfig;
import org.tutict.yurpc.proxy.ServiceProxyFactory;
import org.tutict.yurpc.utils.ConfigUtils;

public class ConsumerExample {

    public static void main(String[] args) {

        UserService userService = ServiceProxyFactory.getProxy(UserService.class);
        User user = new User();
        user.setName("yupi");

        User newUser = userService.getUser(user);
        if (newUser != null) {
            System.out.println(newUser.getName());
        } else {
            System.err.println("user == null");
        }
        long number = userService.getNumber();
        System.out.println(number);
    }
}
