package io.github.zutherb.appstash.shop.repository.user.model;

import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

/**
 * @author zutherb
 */
@Document
public class Role implements Serializable {

    private String name;

    public Role(){}

    public Role(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
