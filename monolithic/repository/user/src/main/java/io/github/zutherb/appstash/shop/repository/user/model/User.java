package io.github.zutherb.appstash.shop.repository.user.model;


import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * @author zutherb
 */
@Document(collection = User.COLLECTION_NAME)
public class User implements Serializable {
    public static final String COLLECTION_NAME = "user";

    private ObjectId id;
    private SalutationType salutationType;
    @Indexed(unique = true)
    private String username;
    private String password;
    private String firstname;
    private String lastname;
    @Indexed(unique = true)
    private String email;

    private Set<Role> roles;
    private Set<String> categories;

    private Address address;

    public User() { /* NOOP */ }

    public User(String username, String firstname, String lastname, String password, Address address, Set<Role> roles) {
        this.username = username;
        this.firstname = firstname;
        this.lastname = lastname;
        this.password = password;
        this.address = address;
        this.roles = roles;
    }

    public ObjectId getId() {
        return id;
    }

    public Set<Role> getRoles() {
        if (roles == null) {
            roles = new HashSet<>();
        }
        return roles;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public void setSalutationType(SalutationType salutationType) {
        this.salutationType = salutationType;
    }

    public SalutationType getSalutationType() {
        return salutationType;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Set<String> getCategories() {
        if (categories == null) {
            categories = new HashSet<>();
        }
        return categories;
    }

    public void setCategories(Set<String> categories) {
        this.categories = categories;
    }
}
