package org.tutict.common.service;

import org.tutict.common.model.User;

public interface UserService {

    User getUser(User user);

    default short getNumber() {
        return 1;
    }
}
