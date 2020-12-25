package com.xschen.commonmistakes._04_connectionpool.datasource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.TimeUnit;

/**
 * @author xschen
 */

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;


    // 开启事务，一个数据库事务对应一个TCP连接
    @Transactional
    public User register() {
        User user = new User();
        user.setName("new-user-" + System.currentTimeMillis());
        userRepository.save(user);

        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return user;
    }
}
