package io.github.zutherb.appstash.shop.service.navigation.domain

import org.springframework.hateoas.ResourceSupport

class Navigation extends ResourceSupport implements Serializable {

    def String type;
    def long sum;

    Navigation(String type, long sum) {
        this.type = type
        this.sum = sum
    }

    def String getName() {
        return Character.toString(type.charAt(0)).toUpperCase() + type.substring(1).toLowerCase();
    }

    def String getUrlname() {
        return type.toLowerCase();
    }

    def long getSum() {
        return sum;
    }
}
