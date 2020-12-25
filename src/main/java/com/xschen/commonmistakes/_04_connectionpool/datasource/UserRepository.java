package com.xschen.commonmistakes._04_connectionpool.datasource;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author xschen
 */


@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}
