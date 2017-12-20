package com.actitime.listeners;

import com.actitime.domain.User;
import com.actitime.utils.ApplicationContextHelper;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

public class UserListener {
    @PrePersist
    private void onPrePersist(User user) {
        //hard code. New users are always enabled and can login
        user.setEnabled(true);

        encodePassword(user);
    }

    @PreUpdate
    private void onPreUpdate(User user) {
        if (user.getDirtyFieldNames().contains("password")) encodePassword(user);
    }

    private void encodePassword(User user) {
        user.setPassword(ApplicationContextHelper.getApplicationContext().
                getBean(PasswordEncoder.class).encode(user.getPassword()));
    }
}
