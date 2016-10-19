package io.github.zutherb.appstash.shop.ui.panel.login;

import io.github.zutherb.appstash.shop.ui.event.login.LoginEvent;
import io.github.zutherb.appstash.shop.ui.navigation.ModalPanel;
import io.github.zutherb.appstash.shop.service.authentication.model.LoginInfo;
import io.github.zutherb.appstash.shop.ui.panel.base.AbstractShopBasePanel;
import io.github.zutherb.appstash.shop.service.authentication.model.LoginInfo;
import io.github.zutherb.appstash.shop.ui.navigation.ModalPanel;
import io.github.zutherb.appstash.shop.ui.panel.base.AbstractShopBasePanel;
import org.apache.wicket.Component;
import org.apache.wicket.Session;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.ajax.markup.html.form.AjaxSubmitLink;
import org.apache.wicket.event.Broadcast;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.model.LoadableDetachableModel;


/**
 * @author zutherb.
 */
public class LoginModalPanel extends AbstractShopBasePanel implements ModalPanel {

    private WebMarkupContainer modalContainer;

    public LoginModalPanel(String id) {
        super(id);
        add(modalContainer());
        add(javaScript());
    }

    private Component javaScript() {
        Label modalContainerJS = new Label("modalContainerJS", new LoadableDetachableModel<String>() {
            @Override
            protected String load() {
                return String.format("$('#%s').hide()", modalContainer.getMarkupId());
            }
        });
        modalContainerJS.setEscapeModelStrings(false);
        return modalContainerJS;
    }

    private Component modalContainer() {
        modalContainer = new WebMarkupContainer("modalContainer");
        modalContainer.add(loginForm());
        modalContainer.setOutputMarkupId(true);
        modalContainer.setOutputMarkupPlaceholderTag(true);
        return modalContainer;
    }

    private Component loginForm() {
        Form<Void> form = new Form<>("loginForm");
        LoginPanel loginPanel = loginPanel();
        form.add(loginPanel);

        AjaxSubmitLink submitLink = submitLink(loginPanel);
        form.add(submitLink);
        form.setDefaultButton(submitLink);
        form.add(closeLink("close"));
        return form;
    }

    private AjaxLink<Void> closeLink(String id) {
        return new AjaxLink<Void>(id) {
            @Override
            public void onClick(AjaxRequestTarget target) {
                target.appendJavaScript(generateToggleScript());
            }
        };
    }

    private AjaxSubmitLink submitLink(final LoginPanel loginPanel) {
        return new AjaxSubmitLink("login") {
            @Override
            protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
                loginPanel.submit(target);
            }

            @Override
            protected void onError(AjaxRequestTarget target, Form<?> form) {
                target.add(modalContainer);
            }
        };
    }

    private LoginPanel loginPanel() {
        return new LoginPanel("loginPanel") {

            @Override
            protected boolean isSubmitLinkVisible() {
                return false;
            }

            @Override
            protected void submitLoginForm(AjaxRequestTarget target, LoginInfo loginInfo) {
                boolean authenticate = authenticate(loginInfo);
                if (authenticate) {
                    Session.get().info(getString("authentication.success"));

                    setResponsePage(getPage());
                    send(getPage(), Broadcast.BREADTH, new LoginEvent(LoginModalPanel.this, target));

                } else {
                    error(getString("authentication.failed"));
                    target.add(modalContainer);
                }
            }
        };
    }

    @Override
    public String generateToggleScript() {
        return String.format("$('#%s').modal('toggle')", modalContainer.getMarkupId());
    }
}
