package tg.univlome.epl.pharmarciemanagement.utils;

import org.mindrot.jbcrypt.BCrypt;

public class PasswordHasher {

    public static String hash(String plainPassword) {
        return BCrypt.hashpw(plainPassword, BCrypt.gensalt());
    }

    public static boolean check(String plainPassword, String hashed) {
        return BCrypt.checkpw(plainPassword, hashed);
    }
}
