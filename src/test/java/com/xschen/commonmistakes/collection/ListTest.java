package com.xschen.commonmistakes.collection;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 测试 list 元素的对象引用
 * @author xschen
 * @date 2021/9/7 17:51
 */
public class ListTest {

    public static void main(String[] args) {
        User song = new User("song", 23);
        User song24 = new User("song24", 24);
        List<User> users = new ArrayList<>();
        users.addAll(Arrays.asList(song, song24));
        for (User user : users) {
            user.setName(user.getName() + "test");
        }

        System.out.println(users);// 是否为user.getName() + "test"
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    static class User {
        private String name;
        private Integer age;
    }
}
