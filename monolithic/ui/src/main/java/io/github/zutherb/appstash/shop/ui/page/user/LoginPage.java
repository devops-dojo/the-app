package io.github.zutherb.appstash.shop.ui.page.user;

import io.github.zutherb.appstash.shop.ui.navigation.ModalPanelItem;
import io.github.zutherb.appstash.shop.ui.navigation.NavigationItem;
import io.github.zutherb.appstash.shop.ui.page.AbstractBasePage;
import io.github.zutherb.appstash.shop.ui.panel.login.LoginModalPanel;
import io.github.zutherb.appstash.shop.ui.panel.login.LoginPanel;
import org.wicketstuff.annotation.mount.MountPath;

/**
 * @author zuther
 */
@MountPath("login")
@NavigationItem(name = "Sign In", sortOrder = 1, group = "Sign In", visible = "!@authenticationService.isAuthorized()")
@ModalPanelItem(modalPanel = LoginModalPanel.class)
public class LoginPage extends AbstractBasePage {
    
    public LoginPage(){
        super();
        feedback.setVisibilityAllowed(false);
        add(new LoginPanel("loginPanel"));
    }

}
