package io.github.zutherb.appstash.shop.service.user.domain

/**
 * @author zutherb
 */
public enum SalutationType {
    MISTER("Herr"),
    MISS("Frau");

    private String salutation;

    SalutationType(String salutation) {
        this.salutation = salutation
    }
}