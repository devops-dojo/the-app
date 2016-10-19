package io.github.zutherb.appstash.shop.ui.application.security;

import io.github.zutherb.appstash.shop.service.authentication.api.AuthenticationService;
import io.github.zutherb.appstash.shop.ui.page.user.LoginPage;
import org.apache.commons.lang.ArrayUtils;
import org.apache.wicket.Component;
import org.apache.wicket.Page;
import org.apache.wicket.RestartResponseAtInterceptPageException;
import org.apache.wicket.Session;
import org.apache.wicket.authorization.Action;
import org.apache.wicket.authorization.IAuthorizationStrategy;
import org.apache.wicket.injection.Injector;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.request.component.IRequestableComponent;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.request.resource.IResource;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.springframework.security.access.annotation.Secured;

public class SpringSecurityAuthorizationStrategy implements IAuthorizationStrategy {

    @SpringBean(name = "authenticationService")
    private AuthenticationService authenticationService;

    public SpringSecurityAuthorizationStrategy() {
        Injector.get().inject(this);
    }

    @Override
    public <T extends IRequestableComponent> boolean isInstantiationAuthorized(Class<T> componentClass) {
        if (!hasSpringSecuredAnnotation(componentClass)) {
            return true;
        }
        boolean authorized = isAuthorized(componentClass);
        if (Page.class.isAssignableFrom(componentClass) && !authorized) {
            String missingPermissions = ArrayUtils.toString(componentClass.getAnnotation(Secured.class).value());
            Session.get().error("Zugriff verweigert fehlende Berechtigung(en): " + missingPermissions);
            throw new RestartResponseAtInterceptPageException(LoginPage.class);
        }
        return authorized;
    }

    @Override
    public boolean isActionAuthorized(Component component, Action action) {
        Class<? extends Component> componentClass = component.getClass();
        if (hasSpringSecuredAnnotation(componentClass)) {
            return isAuthorized(componentClass);
        }
        if (component instanceof BookmarkablePageLink) {
            BookmarkablePageLink<?> pageLink = (BookmarkablePageLink<?>) component;
            Class<? extends Page> pageClass = pageLink.getPageClass();
            if (hasSpringSecuredAnnotation(pageClass)) {
                return isAuthorized(pageClass);
            }
        }
        return true;
    }

    @Override
    public boolean isResourceAuthorized(IResource resource, PageParameters parameters) {
        return true;
    }

    private boolean isAuthorized(Class<?> clazz) {
        Secured annotation = clazz.getAnnotation(Secured.class);
        return authenticationService.isAuthorized(annotation.value());
    }

    private boolean hasSpringSecuredAnnotation(Class<?> clazz) {
        return clazz.isAnnotationPresent(Secured.class);
    }
}
