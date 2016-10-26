package io.github.zutherb.appstash.shop.ui.application;

import io.github.zutherb.appstash.shop.ui.application.security.SpringSecurityAuthorizationStrategy;
import io.github.zutherb.appstash.shop.ui.application.settings.JavaScriptLibrarySettings;
import io.github.zutherb.appstash.shop.ui.page.home.HomePage;
import org.apache.wicket.Page;
import org.apache.wicket.Session;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.request.Request;
import org.apache.wicket.request.Response;
import org.apache.wicket.settings.RequestCycleSettings;
import org.apache.wicket.spring.injection.annot.SpringComponentInjector;
import org.springframework.jmx.export.annotation.ManagedOperation;
import org.springframework.jmx.export.annotation.ManagedResource;
import org.springframework.stereotype.Component;
import org.wicketstuff.annotation.scan.AnnotatedMountScanner;

@Component
@ManagedResource(objectName = "io.github.zutherb.appstash.ui:name=ShopApplication")
public class ShopApplication extends WebApplication {

    @Override
    protected void init() {
        super.init();
        new AnnotatedMountScanner().scanPackage("io.github.zutherb.appstash.shop.ui.page").mount(this);
        getMarkupSettings().setStripWicketTags(true);
        getRequestCycleSettings().setRenderStrategy(
                RequestCycleSettings.RenderStrategy.ONE_PASS_RENDER);
        getComponentInstantiationListeners().add(new SpringComponentInjector(this));
        getSecuritySettings().setAuthorizationStrategy(new SpringSecurityAuthorizationStrategy());
        getDebugSettings().setAjaxDebugModeEnabled(false);

        setJavaScriptLibrarySettings(new JavaScriptLibrarySettings());

        getRequestCycleListeners().add(new MicroserviceRequestCycleListener());
        getRequestCycleListeners().add(new DockerRequestCycleListener());
        getRequestCycleListeners().add(new BootstrapDesignRequestCycleListener());
        getRequestCycleListeners().add(new DirectBuyRequestCycleListener());
        getRequestCycleListeners().add(new CartRequestCycleListener());
    }

    @Override
    public Class<? extends Page> getHomePage() {
        return HomePage.class;
    }

    @Override
    public Session newSession(Request request, Response response) {
        return new ShopSession(request);
    }

    @ManagedOperation
    public void enableAjaxDebugMode() {
        ShopApplication.get().getDebugSettings().setAjaxDebugModeEnabled(true);
    }

    @ManagedOperation
    public void disableAjaxDebugMode() {
        ShopApplication.get().getDebugSettings().setAjaxDebugModeEnabled(false);
    }
}
