package com.xschen.commonmistakes._15_serialization.redistemplate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author xschen
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
class User implements Serializable {
    private String name;
    private int age;
}
