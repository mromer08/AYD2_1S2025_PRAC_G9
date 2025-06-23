package com.usac.ayd2.musicplayer.utils;

import at.favre.lib.crypto.bcrypt.BCrypt;

public class PasswordUtil {

    public static String hashPassword(String plainPassword) {
        return BCrypt.withDefaults().hashToString(12, plainPassword.toCharArray());
    }

    public static boolean verifyPassword(String plainPassword, String hashedPassword) {
        return BCrypt.verifyer().verify(plainPassword.toCharArray(), hashedPassword).verified;
    }
    
}
