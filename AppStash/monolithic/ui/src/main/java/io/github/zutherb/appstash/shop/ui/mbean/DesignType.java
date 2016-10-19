package io.github.zutherb.appstash.shop.ui.mbean;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public enum DesignType {
    standard,
    amelia,
    darkly,
    simplex,
    superhero;

    public static List<String> names() {
        List<String> names = new ArrayList<>();
        for (DesignType type : values()) {
            names.add(type.name());
        }
        return Collections.unmodifiableList(names);
    }

    public static DesignType fromName(String name) {
        for (DesignType type : values()) {
            if (type.name().equals(name)) {
                return type;
            }
        }
        throw new IllegalArgumentException("Not DesignType found for name: " + name);
    }
}
