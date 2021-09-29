package com.xschen.commonmistakes._11_nullvalue.pojonull.repository;

import com.xschen.commonmistakes._11_nullvalue.pojonull.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author xschen
 * @date 2021/9/28 17:22
 */

@Repository
public interface UserEntityRepository extends JpaRepository<UserEntity, Long> {
}
