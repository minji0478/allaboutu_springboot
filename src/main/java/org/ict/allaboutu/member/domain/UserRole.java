package org.ict.allaboutu.member.domain;

public enum UserRole {
    ADMIN, USER;

    public static UserRole fromString(String role) {
        if (role != null) {
            for (UserRole userRole : UserRole.values()) {
                if (role.equalsIgnoreCase(userRole.name())) {
                    return userRole;
                }
            }
        }
        // 기본값으로 USER를 반환하도록 처리
        return USER;
    }
}
