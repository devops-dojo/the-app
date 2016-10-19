package io.github.zutherb.appstash.shop.ui.navigation;

import java.io.Serializable;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * @author zutherb
 */
public class NavigationGroup implements Serializable {

    private String name;
    private List<NavigationEntry> navigationEntries;

    public NavigationGroup(String name) {
        this.name = name;
    }

    public List<NavigationEntry> getNavigationEntries() {
        if(navigationEntries == null){
            navigationEntries = new LinkedList<>();
        }
        Collections.sort(navigationEntries);
        return navigationEntries;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        NavigationGroup that = (NavigationGroup) o;

        return !(name != null ? !name.equals(that.name) : that.name != null);

    }

    @Override
    public int hashCode() {
        return name != null ? name.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "NavigationGroup{" +
                "name='" + name + '\'' +
                ", navigationEntries=" + navigationEntries +
                '}';
    }
}
