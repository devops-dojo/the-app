package io.github.zutherb.appstash.shop.ui.navigation;

import org.apache.wicket.Page;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import java.io.Serializable;

/**
 * @author zutherb
 */
public class NavigationEntry implements Serializable, Comparable<NavigationEntry>{

    private final String name;
    private final int sortOrder;
    private final Class<? extends Page> pageClass;
    private final PageParameters pageParameters;
    private boolean visible;

    public NavigationEntry(String name, int sortOrder, Class<? extends Page> pageClass, boolean visible) {
        this( name, sortOrder, pageClass, null, visible );
    }

    public NavigationEntry(String name, int sortOrder, Class<? extends Page> pageClass, PageParameters pageParameters, boolean visible) {
        this.name = name;
        this.sortOrder = sortOrder;
        this.pageClass = pageClass;
        this.pageParameters = pageParameters;
        this.visible = visible;

    }

    public String getName() {
        return name;
    }

    public int getSortOrder() {
        return sortOrder;
    }

    public Class<? extends Page> getPageClass() {
        return pageClass;
    }

    public PageParameters getPageParameters() {
        return pageParameters;
    }

    @Override
    public int compareTo(NavigationEntry navigationEntry) {
        return Integer.valueOf(sortOrder).compareTo(navigationEntry.getSortOrder());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        NavigationEntry that = (NavigationEntry) o;

        return sortOrder == that.sortOrder && name.equals(that.name) && pageClass.equals(that.pageClass);

    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + sortOrder;
        result = 31 * result + pageClass.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "NavigationEntry{" +
                "name='" + name + '\'' +
                ", sortOrder=" + sortOrder +
                ", pageClass=" + pageClass +
                ", visible=" + visible +
                '}';
    }

    public boolean isVisible(){
        return visible;
    }
}
