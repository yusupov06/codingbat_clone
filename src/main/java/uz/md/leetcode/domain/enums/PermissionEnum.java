package uz.md.leetcode.domain.enums;

import org.springframework.security.core.GrantedAuthority;


/**
 * Enum for Permissions
 */
public enum PermissionEnum implements GrantedAuthority {

    ADD_ROLE,
    DELETE_ROLE,
    ADD_LANGUAGE,
    EDIT_LANGUAGE,
    DELETE_LANGUAGE,
    SOLVE_PROBLEM,
    ADD_PROBLEM,
    DELETE_PROBLEM,
    ADD_SECTION,
    EDIT_SECTION,
    DELETE_SECTION;

    @Override
    public String getAuthority() {
        return null;
    }
}
