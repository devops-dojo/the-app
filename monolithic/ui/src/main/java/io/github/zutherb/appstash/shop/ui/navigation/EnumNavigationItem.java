package io.github.zutherb.appstash.shop.ui.navigation;


import java.lang.annotation.*;

@Documented
@Retention( RetentionPolicy.RUNTIME )
@Target( value = ElementType.TYPE )
public @interface EnumNavigationItem {

    Class<? extends Enum<?>> enumClazz();
    String defaultEnum();
    String group() default "main";
    String visible() default "";
    int sortOrder();
}
