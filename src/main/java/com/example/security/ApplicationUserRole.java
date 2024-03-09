package com.example.security;

import com.google.common.collect.Sets;

import java.util.Set;

import static com.example.security.ApplicationUserPermission.*;

public enum ApplicationUserRole {
    STUDENT (Sets.newHashSet()), //student ma pusty zbiór
    ADMIN (Sets.newHashSet(COURSE_READ, COURSE_WRITE, STUDENT_READ, STUDENT_WRITE));

    private final Set<ApplicationUserPermission> permissions;

    ApplicationUserRole(Set<ApplicationUserPermission> permissions) {
        this.permissions = permissions;
    }
}
