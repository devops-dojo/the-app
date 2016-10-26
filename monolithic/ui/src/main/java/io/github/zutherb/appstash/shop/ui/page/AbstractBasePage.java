package io.github.zutherb.appstash.shop.ui.page;

import io.github.zutherb.appstash.shop.service.authentication.api.AuthenticationService;
import io.github.zutherb.appstash.shop.ui.mbean.DesignSelector;
import io.github.zutherb.appstash.shop.ui.mbean.DesignSelectorBean;
import io.github.zutherb.appstash.shop.ui.panel.NavigationPanel;
import io.github.zutherb.appstash.shop.ui.panel.base.FeedbackPanel;
import io.github.zutherb.appstash.shop.ui.panel.login.LoginModalPanel;
import org.apache.wicket.Component;
import org.apache.wicket.markup.head.CssHeaderItem;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.JavaScriptHeaderItem;
import org.apache.wicket.markup.head.StringHeaderItem;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.model.IModel;
import org.apache.wicket.request.cycle.RequestCycle;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.apache.wicket.util.string.interpolator.MapVariableInterpolator;

import java.util.Collections;
import java.util.Map;

/**
 * @author zutherb
 */
public abstract class AbstractBasePage extends WebPage {

    private static final String FAVICON_HEADER = "<link rel=\"icon\" type=\"image/png\" href=\"${contextPath}/assets/ico/favicon.ico\" />";
    private static final long serialVersionUID = -9213806230323972218L;

    @SpringBean(name = "authenticationService")
    private AuthenticationService authenticationService;

    @SpringBean(name = "designSelector")
    private DesignSelector designSelector;

    protected LoginModalPanel loginModal;
    protected WebMarkupContainer header;
    protected WebMarkupContainer navigation;
    protected Component feedback;

    public AbstractBasePage(PageParameters pageParameters) {
        super(pageParameters);
        initialize();
    }

    public AbstractBasePage(IModel<?> model) {
        super(model);
        initialize();
    }

    public AbstractBasePage() {
        super();
        initialize();
    }

    private void initialize() {
        add(loginModalPanel());
        add(headerContainer());
        add(shopFeedbackPanel());
        add(new KeyPressBehavior(this));
    }

    private Component loginModalPanel() {
        loginModal = new LoginModalPanel("loginModal");
        return loginModal;
    }

    private Component headerContainer() {
        header = new WebMarkupContainer("header");
        header.add(navigation());
        return header.setOutputMarkupId(true);
    }

    private Component navigation() {
        navigation = new NavigationPanel("navigation", loginModal);
        return navigation.setOutputMarkupId(true);
    }

    @Override
    public void renderHead(IHeaderResponse response) {
        super.renderHead(response);

        String contextPath = RequestCycle.get().getRequest().getContextPath();

        Map<String, String> replacements = Collections.singletonMap("contextPath", getContextPath());
        MapVariableInterpolator variableInterpolator = new MapVariableInterpolator(FAVICON_HEADER, replacements);
        response.render(StringHeaderItem.forString(variableInterpolator.toString()));

        String designUrl = String.format("/assets/css/bootstrap-%s.min.css", designSelector.getDesignType());
        response.render(CssHeaderItem.forUrl(contextPath + designUrl));
        response.render(CssHeaderItem.forUrl(contextPath + "/assets/css/bootstrap-theme-shop.css"));

        response.render(JavaScriptHeaderItem.forUrl(contextPath + "/assets/js/bootstrap.min.js"));
        response.render(CssHeaderItem.forUrl(contextPath + "/assets/css/bootstrap-addon.css"));
    }

    public AuthenticationService getAuthenticationService() {
        return authenticationService;
    }

    protected Component shopFeedbackPanel() {
        feedback = new FeedbackPanel("feedback");
        feedback.setOutputMarkupId(true);
        feedback.setOutputMarkupPlaceholderTag(true);
        return feedback;
    }

    protected String getContextPath() {
        return RequestCycle.get().getRequest().getContextPath();
    }
}