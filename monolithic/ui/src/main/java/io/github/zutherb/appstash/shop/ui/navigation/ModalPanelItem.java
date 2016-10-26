package io.github.zutherb.appstash.shop.ui.navigation;

import java.lang.annotation.*;

/**
 * @author zutherb
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(value = ElementType.TYPE)
public @interface ModalPanelItem {
    Class<? extends ModalPanel> modalPanel();
}
