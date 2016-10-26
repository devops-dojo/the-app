package io.github.zutherb.appstash.shop.service.authentication.model;

import java.io.Serializable;

/**
 * @author zuther
 */
public class LoginInfo implements Serializable {
    private String username;
    private String password;

    public LoginInfo() {}

    public LoginInfo(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
