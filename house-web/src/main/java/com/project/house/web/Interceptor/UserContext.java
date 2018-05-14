package com.project.house.web.Interceptor;

import com.project.house.common.model.User;


public class UserContext {
    private static final ThreadLocal<User> USER_HOLDER = new ThreadLocal<>();
    public static User get() {
        return USER_HOLDER.get();
    }

    public static void set(User user) {
        USER_HOLDER.set(user);
    }

    public static void remove() {
        USER_HOLDER.remove();
    }
}
