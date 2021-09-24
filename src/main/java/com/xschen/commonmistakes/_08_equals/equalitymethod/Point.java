package com.xschen.commonmistakes._08_equals.equalitymethod;

/**
 * @author xschen
 * @date 2021/9/23 11:21
 */

public class Point {
    private int x;
    private int y;
    private final String desc;

    public Point(int x, int y, String desc) {
        this.x = x;
        this.y = y;
        this.desc = desc;
    }
}
