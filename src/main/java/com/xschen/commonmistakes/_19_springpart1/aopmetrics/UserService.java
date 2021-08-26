package com.xschen.commonmistakes._19_springpart1.aopmetrics;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author xschen
 */
@Slf4j
@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Transactional
    @Metrics // 启用方法监控
    public void createUser(UserEntity entity) {
        userRepository.save(entity);
        System.out.println(getUserCount(entity.getName()));//
        if (entity.getName().contains("test")) {
            throw new RuntimeException("invalid username");
        }
    }

    public int getUserCount(String name) {
        return userRepository.findByName(name).size();
    }
}
