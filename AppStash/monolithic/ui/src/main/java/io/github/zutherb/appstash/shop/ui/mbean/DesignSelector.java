package io.github.zutherb.appstash.shop.ui.mbean;

import java.util.List;

public interface DesignSelector {
    String getDesignType();

    void setDesignType(String name);

    List<String> getAvailableDesignTypes();

    void standard();

    void next();

    boolean isUsedToSelectedDesign();

    void setIsUsedToSelectedDesign(boolean isUsedToSelectedDesign);
}
