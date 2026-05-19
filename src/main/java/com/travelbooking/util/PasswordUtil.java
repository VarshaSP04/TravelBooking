package com.travelbooking.util;

import org.mindrot.jbcrypt.BCrypt;

/** BCrypt password hashing utility. */
public class PasswordUtil {

    private static final int ROUNDS = 10;

    public static String hash(String plainPassword) {
        return BCrypt.hashpw(plainPassword, BCrypt.gensalt(ROUNDS));
    }

    public static boolean verify(String plainPassword, String hashedPassword) {
        return BCrypt.checkpw(plainPassword, hashedPassword);
    }
}
