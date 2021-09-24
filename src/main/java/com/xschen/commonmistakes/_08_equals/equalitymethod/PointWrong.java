package com.xschen.commonmistakes._08_equals.equalitymethod;

/**
 * @author xschen
 * @date 2021/9/23 11:21
 */

public class PointWrong {
    private int x;
    private int y;
    private final String desc;

    public PointWrong(int x, int y, String desc) {
        this.x = x;
        this.y = y;
        this.desc = desc;
    }

    // 期望 两个对象只需要比较x,y，如果相等，则判断对象相等
    @Override
    public boolean equals(Object obj) {
        PointWrong that = (PointWrong) obj;
        return x == that.x && y == that.y;
    }
}
