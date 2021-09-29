package com.xschen.commonmistakes._11_nullvalue.avoidnullpointterexception;

import lombok.Getter;

/**
 * @author xschen
 * @date 2021/9/26 16:46
 */


public class FooService {

    @Getter
    private BarService barService;
}
