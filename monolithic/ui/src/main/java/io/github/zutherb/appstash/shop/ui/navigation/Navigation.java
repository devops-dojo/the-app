package io.github.zutherb.appstash.shop.ui.navigation;

import org.apache.commons.lang.Validate;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

/**
 * @author zutherb
 */
public class Navigation implements Serializable {

    private List<NavigationGroup> navigationGroups;

    public List<NavigationGroup> getNavigationGroups(){
        if(navigationGroups == null){
            navigationGroups = new LinkedList<>();
        }
        return navigationGroups;
    }

    public NavigationGroup getNavigationGroup(final String groupName) {
        Validate.notNull(groupName, "GroupName must not be null");
        for(NavigationGroup navigationGroup : getNavigationGroups()){
            if(groupName.equals(navigationGroup.getName())){
                return navigationGroup;
            }
        }
        return null;
    }

    public NavigationGroup getMainNavigationGroup(){
        return getNavigationGroup("main");
    }

    @Override
    public String toString() {
        return "Navigation{" +
                "navigationGroups=" + navigationGroups +
                '}';
    }
}
