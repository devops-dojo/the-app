package io.github.zutherb.appstash.shop.ui.navigation;

import java.lang.annotation.*;

/**
 * @author zutherb
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(value = ElementType.TYPE)
public @interface NavigationItem {
    String name();
    int sortOrder();
    String group() default "main";
    String visible() default "";
}
