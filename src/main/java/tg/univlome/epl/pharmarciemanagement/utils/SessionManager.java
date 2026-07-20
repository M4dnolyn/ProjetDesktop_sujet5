package tg.univlome.epl.pharmarciemanagement.utils;

import tg.univlome.epl.pharmarciemanagement.models.User;

public class SessionManager {

    private static User currentUser;

    public static void login(User user) {
        currentUser = user;
    }

    public static void logout() {
        currentUser = null;
    }

    public static User getCurrentUser() {
        return currentUser;
    }

    public static boolean isLoggedIn() {
        return currentUser != null;
    }
}
