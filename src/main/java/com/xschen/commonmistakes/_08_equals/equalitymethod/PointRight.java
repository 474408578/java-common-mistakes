package com.xschen.commonmistakes._08_equals.equalitymethod;

import java.util.Objects;

/**
 * hashCode 和 equals 要配对实现
 * @author xschen
 * @date 2021/9/23 11:49
 */

public class PointRight {

    private int x;
    private int y;
    private final String desc;

    public PointRight(int x, int y, String desc) {
        this.x = x;
        this.y = y;
        this.desc = desc;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;
        PointRight that = (PointRight) obj;
        return x == that.x && y == that.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}
