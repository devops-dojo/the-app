package io.github.zutherb.appstash.shop.ui.panel.base;

import io.github.zutherb.appstash.shop.service.authentication.api.AuthenticationService;
import io.github.zutherb.appstash.shop.service.authentication.model.LoginInfo;
import io.github.zutherb.appstash.shop.ui.tracking.TrackingService;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

/**
 * @author zutherb
 */
public abstract class AbstractShopBasePanel extends Panel {
    private static final long serialVersionUID = -2274645877865227328L;

    @SpringBean(name = "authenticationService")
    private AuthenticationService authenticationService;

    @SpringBean
    private TrackingService trackingService;

    public AbstractShopBasePanel(String id) {
        super(id);
    }

    public AbstractShopBasePanel(String id, IModel<?> model) {
        super(id, model);
    }

    public AuthenticationService getAuthenticationService() {
        return authenticationService;
    }

    public boolean isAuthorized() {
        return getAuthenticationService().isAuthorized();
    }

    public boolean authenticate(LoginInfo loginInfo) {
        boolean authenticate = authenticationService.authenticate(loginInfo);
        trackingService.trackLogin(getAuthenticationService().getAuthenticatedUserInfo());
        return authenticate;
    }
}
