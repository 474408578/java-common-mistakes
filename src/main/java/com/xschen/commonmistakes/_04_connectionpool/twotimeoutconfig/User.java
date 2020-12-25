package com.xschen.commonmistakes._04_connectionpool.twotimeoutconfig;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * @author xschen
 */

@Entity // 这个类为实体类，接受JPA控制管理，对应数据库中的一个表
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;
}
