package io.github.zutherb.appstash.shop.service.authentication.api;

import io.github.zutherb.appstash.shop.service.authentication.model.LoginInfo;
import io.github.zutherb.appstash.shop.service.user.model.UserInfo;

/**
 * @author zutherb
 */
public interface AuthenticationService {

    boolean authenticate(LoginInfo loginInfo);

    void clearAuthentication();

    boolean isAuthorized(String... permissions);

    UserInfo getAuthenticatedUserInfo();
}
