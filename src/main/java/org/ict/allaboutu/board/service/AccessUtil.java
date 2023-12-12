package org.ict.allaboutu.board.service;


public class AccessUtil {

    public static boolean isWriter(String currentUser, String writer) {
        return currentUser.equals(writer);
    }

}
