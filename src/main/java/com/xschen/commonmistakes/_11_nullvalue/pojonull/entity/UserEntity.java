package com.xschen.commonmistakes._11_nullvalue.pojonull.entity;

import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.Date;

/**
 * @author xschen
 * @date 2021/9/28 17:14
 */

@Entity
@DynamicUpdate(value = true) // 默认即为 true, 只更新修改后的字段
@Data
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String nickName;
    @Column(nullable = false)
    private Integer age;
    @Column(nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Date createDate = new Date();
}
