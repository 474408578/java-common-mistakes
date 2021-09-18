package com.xschen.commonmistakes._18_advancedfeatures.reflectionissue;

import lombok.extern.slf4j.Slf4j;

/**
 * 反射调用方法不是以传参决定。
 * @author xschen
 * @date 2021/9/17 19:06
 */

@Slf4j
public class ReflectionIssueApplication {

    public static void main(String[] args) {

        System.out.println(Integer.TYPE); // int.class
        System.out.println(Integer.class);
    }
}
