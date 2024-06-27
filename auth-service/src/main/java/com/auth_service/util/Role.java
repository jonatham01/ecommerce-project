package com.auth_service.util;

import java.util.Arrays;
import java.util.List;

public enum Role {

    CUSTOMER(List.of(
                RolePermision.READ_MY_PROFILE
            )
    ),

    ADMINISTRATOR(Arrays.asList(
            RolePermision.READ_MY_PROFILE,
            RolePermision.CREATE_ONE_,
            RolePermision.READ_ONE_,
            RolePermision.READ_ALL_,
            RolePermision.UPDATE_ONE_,
            RolePermision.DELETE_ONE_
    ));

    private List<RolePermision> permissions;

    Role(List<RolePermision> permisions) {
        this.permissions = permisions;
    }

    public List<RolePermision> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<RolePermision> permissions) {
        this.permissions = permissions;
    }
}
