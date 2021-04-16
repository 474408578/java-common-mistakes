package com.xschen.commonmistakes._01_concurrenttool.threadlocal.batter.utils;

/**
 * @author xschen
 */

public class LoginUtil {

    public LoginUtil() {
    }

    private static final ThreadLocal<Integer> currentUser = ThreadLocal.withInitial(() -> null);

    public static Integer get() {
        return currentUser.get();
    }

    public static void set(Integer currentUserId) {
        currentUser.set(currentUserId);
    }

    public static void clear() {
        currentUser.remove();
    }
}
