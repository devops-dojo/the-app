package io.github.zutherb.appstash.shop.repository.order.model;

import java.io.Serializable;

/**
 * @author zutherb
 */
public class Supplier implements Serializable {
    private String name;

    public Supplier() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
