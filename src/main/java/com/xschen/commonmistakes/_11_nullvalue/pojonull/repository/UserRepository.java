package com.xschen.commonmistakes._11_nullvalue.pojonull.repository;

import com.xschen.commonmistakes._11_nullvalue.pojonull.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author xschen
 * @date 2021/9/28 15:26
 */

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}
