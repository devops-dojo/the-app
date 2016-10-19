package io.github.zutherb.appstash.shop.ui.navigation;

import io.github.zutherb.appstash.shop.repository.product.model.ProductType;

import java.lang.annotation.*;

@Documented
@Retention( RetentionPolicy.RUNTIME )
@Target( value = ElementType.TYPE )
public @interface EnumProductTypeNavigationItem {

    Class<? extends ProductType> enumClazz();
    String defaultEnum();
    String group() default "main";
    String visible() default "";
    int sortOrder();
}
