package io.github.zutherb.appstash.shop.service.user.model;

import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * @author zutherb
 */
public class UserInfo implements Serializable {

    private String id;
    private String username;
    private String password;
    private String firstname;
    private String lastname;
    private String email;
    private Set<RoleInfo> roles;
    private AddressInfo address;

    public UserInfo() {
        address = new AddressInfo();
    }

    public UserInfo(String username, String password, Set<RoleInfo> roles, AddressInfo address) {
        this.username = username;
        this.password = password;
        this.roles = roles;
        this.address = address;
    }

    public UserInfo(String firstname, String lastname,
                    String username, String email,
                    String password, Set<RoleInfo> roles,
                    AddressInfo address){
        this.firstname = firstname;
        this.lastname = lastname;
        this.username = username;
        this.email = email;
        this.password = password;
        this.roles = roles;
        this.address = address;
    }

    public String getId() {
        return id;
    }

    public Set<RoleInfo> getRoles() {
        if(roles == null){
            roles = new HashSet<>();
        }
        return roles;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public AddressInfo getAddress() {
        return address;
    }

    public boolean isPersisted() {
        return !StringUtils.isEmpty(id);
    }

    public String getFirstname() {   //
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public String getEmail(){
        return email;
    }
}
