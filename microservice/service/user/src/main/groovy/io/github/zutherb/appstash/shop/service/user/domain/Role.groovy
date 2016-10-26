package io.github.zutherb.appstash.shop.service.user.domain

import com.fasterxml.jackson.databind.annotation.JsonSerialize

/**
 * @author zutherb
 */
@JsonSerialize
class Role implements Serializable {
    private String name;

    boolean equals(o) {
        if (this.is(o)) return true
        if (getClass() != o.class) return false

        Role role = (Role) o

        if (name != role.name) return false

        return true
    }

    int hashCode() {
        return (name != null ? name.hashCode() : 0)
    }
}
