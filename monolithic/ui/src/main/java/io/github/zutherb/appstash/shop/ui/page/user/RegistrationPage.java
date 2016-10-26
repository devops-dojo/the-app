package io.github.zutherb.appstash.shop.ui.page.user;

import io.github.zutherb.appstash.shop.service.authentication.model.LoginInfo;
import io.github.zutherb.appstash.shop.service.user.api.UserService;
import io.github.zutherb.appstash.shop.service.user.model.UserInfo;
import io.github.zutherb.appstash.shop.ui.navigation.NavigationItem;
import io.github.zutherb.appstash.shop.ui.page.AbstractBasePage;
import io.github.zutherb.appstash.shop.ui.page.home.HomePage;
import io.github.zutherb.appstash.shop.ui.tracking.TrackingService;
import org.apache.wicket.Component;
import org.apache.wicket.markup.html.form.*;
import org.apache.wicket.markup.html.form.validation.EqualPasswordInputValidator;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.apache.wicket.validation.IValidatable;
import org.apache.wicket.validation.IValidator;
import org.apache.wicket.validation.validator.EmailAddressValidator;
import org.apache.wicket.validation.validator.PatternValidator;
import org.wicketstuff.annotation.mount.MountPath;

/**
 * @author zutherb
 */
@MountPath(value = "registration")
@NavigationItem(name = "New customer? Start here.", sortOrder = 2, group = "Sign In", visible = "!@authenticationService.isAuthorized()")
public class RegistrationPage extends AbstractBasePage {

    private static final long serialVersionUID = -5123549993398568602L;

    @SpringBean(name = "userService")
    private UserService userService;

    @SpringBean
    private TrackingService trackingService;

    private IModel<UserInfo> userInfoModel;

    @SuppressWarnings("unused")
    public RegistrationPage() {
        this(Model.of(new UserInfo()));
    }

    public RegistrationPage(Model<UserInfo> userInfoModel) {
        super(userInfoModel);
        this.userInfoModel = new CompoundPropertyModel<>(userInfoModel);
        add(loginData());
    }

    private Component loginData() {
        Form<UserInfo> loginData = new Form<>("registration");

        loginData.add(firstnameField());
        loginData.add(lastnameField());
        loginData.add(usernameField());

        PasswordTextField password = passwordField();
        loginData.add(password);

        PasswordTextField repeatPassword = passwordRepeatField();
        loginData.add(repeatPassword);
        loginData.add(new EqualPasswordInputValidator(password, repeatPassword));

        loginData.add(streetField());
        loginData.add(zipField());
        loginData.add(cityField());
        loginData.add(submitLink());
        loginData.add(emailField());
        return loginData;
    }

    private Component firstnameField() {
        return new RequiredTextField<>("firstname", new PropertyModel<>(userInfoModel, "firstname"));
    }

    private Component lastnameField() {
        return new RequiredTextField<>("lastname", new PropertyModel<>(userInfoModel, "lastname"));
    }

    private Component cityField() {
        return new RequiredTextField<>("city", new PropertyModel<>(userInfoModel, "address.city"));
    }

    private Component streetField() {
        return new RequiredTextField<>("street", new PropertyModel<>(userInfoModel, "address.street"));
    }

    private Component zipField() {
        RequiredTextField<String> zip = new RequiredTextField<>("zip", new PropertyModel<>(userInfoModel, "address.zip"));
        zip.add(new PatternValidator("^[0-9]{5}$"));
        return zip;
    }

    private Component submitLink() {
        return new SubmitLink("submit") {
            private static final long serialVersionUID = -4435792111897180886L;

            @Override
            public void onSubmit() {
                if (!RegistrationPage.this.hasErrorMessage()) {
                    getSession().info("Your profile was created");

                    UserInfo userInfo = userInfoModel.getObject();
                    userService.save(userInfo);
                    trackingService.trackSignUp(userInfo);

                    LoginInfo loginInfo = new LoginInfo(userInfo.getUsername(), userInfo.getPassword());
                    getAuthenticationService().authenticate(loginInfo);
                    trackingService.trackLogin(getAuthenticationService().getAuthenticatedUserInfo());
                    setResponsePage(HomePage.class);
                }
            }
        };
    }

    private PasswordTextField passwordRepeatField() {
        PasswordTextField repeatPassword = new PasswordTextField("repeatPassword", Model.of(""));

        repeatPassword.setRequired(true);

        return repeatPassword;
    }

    private PasswordTextField passwordField() {
        PasswordTextField password = new PasswordTextField("password", new PropertyModel<>(userInfoModel, "password"));
        password.setRequired(true);

        return password;
    }

    private Component usernameField() {
        TextField<String> username = new TextField<>("username", new PropertyModel<>(userInfoModel, "username"));
        username.add(new IValidator<String>() {
            private static final long serialVersionUID = 5670647976176255775L;

            @Override
            public void validate(IValidatable<String> userNameValidatable) {
                UserInfo userInfo = userService.findByUsername(userNameValidatable.getValue());
                if (userInfo != null) {
                    error("The entered username is already in use.");
                }
            }
        });
        username.setRequired(true);
        return username;
    }

    private Component emailField() {
        TextField<String> email = new TextField<>("email", new PropertyModel<>(userInfoModel, "email"));
        email.add(new IValidator<String>() {

            private static final long serialVersionUID = 5670647976176255775L;

            @Override
            public void validate(IValidatable<String> email) {
                if (userService.existsUserWithEmail(email.getValue())) {
                    error("The entered email is already in use.");
                }
            }
        });
        email.add(EmailAddressValidator.getInstance());
        return email;
    }

}
