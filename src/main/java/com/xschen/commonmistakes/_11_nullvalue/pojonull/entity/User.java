package com.xschen.commonmistakes._11_nullvalue.pojonull.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

/**
 * @author xschen
 * @date 2021/9/28 15:20
 */

@Entity
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    private String name;
    private String nickName;
    private Integer age;
    private Date createDate = new Date();

}
