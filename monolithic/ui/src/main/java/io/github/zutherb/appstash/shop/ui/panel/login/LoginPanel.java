package io.github.zutherb.appstash.shop.ui.panel.login;

import io.github.zutherb.appstash.shop.service.authentication.model.LoginInfo;
import io.github.zutherb.appstash.shop.service.user.api.UserService;
import io.github.zutherb.appstash.shop.service.user.model.UserInfo;
import io.github.zutherb.appstash.shop.ui.event.login.LoginEvent;
import io.github.zutherb.appstash.shop.ui.panel.base.AbstractShopBasePanel;
import io.github.zutherb.appstash.shop.ui.panel.base.FeedbackPanel;
import org.apache.wicket.Application;
import org.apache.wicket.Component;
import org.apache.wicket.Session;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxSubmitLink;
import org.apache.wicket.event.Broadcast;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.PasswordTextField;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.apache.wicket.util.string.interpolator.MapVariableInterpolator;
import org.apache.wicket.validation.IValidatable;
import org.apache.wicket.validation.IValidator;

import java.util.Collections;

/**
 * @author zutherb.
 */
public class LoginPanel extends AbstractShopBasePanel {
    private static final long serialVersionUID = 4143808726154574329L;

    @SpringBean(name = "userService")
    private UserService userService;

    private IModel<LoginInfo> loginInfoModel;
    private Component feedback;

    public LoginPanel(String id) {
        this(id, Model.of(new LoginInfo()));
    }

    public LoginPanel(String id, IModel<LoginInfo> model) {
        super(id, model);
        this.loginInfoModel = model;
        add(feedbackPanel());
        add(loginForm());
        setOutputMarkupId(true);
        setOutputMarkupPlaceholderTag(true);
    }

    private Component feedbackPanel() {
        feedback = new FeedbackPanel("feedback");
        feedback.setOutputMarkupId(true);
        return feedback;
    }

    private Component loginForm() {
        Form<LoginInfo> loginForm = new Form<>("loginForm");
        loginForm.add(usernameField());
        loginForm.add(passwordField());
        loginForm.add(submitLink());
        return loginForm;
    }

    private Component submitLink() {
        return new AjaxSubmitLink("submitLink") {
            private static final long serialVersionUID = 4199121382799294265L;

            @Override
            protected void onBeforeRender() {
                setVisible(isSubmitLinkVisible());
                super.onBeforeRender();
            }

            @Override
            protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
                submitLoginForm(target, loginInfoModel.getObject());
            }

            @Override
            protected void onError(AjaxRequestTarget target, Form<?> form) {
                target.add(feedback);
            }
        };
    }

    private Component passwordField() {
        PasswordTextField passwordField = new PasswordTextField("password", passwordModel());
        passwordField.setRequired(true);
        return passwordField;
    }

    private IModel<String> passwordModel() {
        return new PropertyModel<>(loginInfoModel, "password");
    }

    private TextField<String> usernameField() {
        TextField<String> usernameField = new TextField<>("username", usernameModel());
        usernameField.add(new IValidator<String>() {
            private static final long serialVersionUID = 1315522649098034068L;

            @Override
            public void validate(IValidatable<String> userNameValidatable) {
                String username = userNameValidatable.getValue();
                UserInfo userInfo = userService.findByUsername(username);
                if (userInfo == null) {
                    error(new MapVariableInterpolator(getString("username.notKnown"),
                            Collections.singletonMap("username", username)).toString());
                }
            }
        });
        usernameField.setRequired(true);
        return usernameField;
    }

    private PropertyModel<String> usernameModel() {
        return new PropertyModel<>(loginInfoModel, "username");
    }

    //HOOK METHOD
    protected void submitLoginForm(AjaxRequestTarget target, LoginInfo loginInfo) {
        boolean authenticate = authenticate(loginInfo);
        if (!authenticate) {
            error(getString("authentication.failed"));
            target.add(feedback);
        } else {
            getAuthenticationService().getAuthenticatedUserInfo();
            Session.get().info(getString("authentication.success"));
            send(this, Broadcast.BREADTH, new LoginEvent(LoginPanel.this, target));
            setResponsePage(Application.get().getHomePage());
        }
    }

    //HOOK METHOD
    protected boolean isSubmitLinkVisible() {
        return true;
    }

    public void submit(AjaxRequestTarget target) {
        submitLoginForm(target, loginInfoModel.getObject());
    }
}
