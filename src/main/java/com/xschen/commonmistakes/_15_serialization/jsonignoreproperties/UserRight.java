package com.xschen.commonmistakes._15_serialization.jsonignoreproperties;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

/**
 * @author xschen
 * @date 2021/9/7 8:57
 */

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserRight {
    private String name;

}

