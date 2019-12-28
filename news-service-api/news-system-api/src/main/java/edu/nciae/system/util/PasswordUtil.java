package edu.nciae.system.util;

import edu.nciae.system.domain.SysUser;

public class PasswordUtil {
    public static boolean matches(SysUser user, String newPassword) {
        return user.getPassword().equals(encryptPassword(user.getUserName(), newPassword, "news"));
    }

    public static String encryptPassword(String username, String password, String salt) {
        return password;
        // return Md5Utils.hash(username + password + salt);
    }
}
