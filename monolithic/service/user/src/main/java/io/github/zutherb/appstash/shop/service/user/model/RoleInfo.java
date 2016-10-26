package io.github.zutherb.appstash.shop.service.user.model;

import java.io.Serializable;

/**
 * @author zutherb
 */
public class RoleInfo implements Serializable {

    private String name;

    private RoleInfo(){}

    public RoleInfo(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
