package io.github.zutherb.appstash.shop.service.authentication.impl;

import io.github.zutherb.appstash.shop.service.authentication.api.AuthenticationService;
import io.github.zutherb.appstash.shop.service.authentication.model.LoginInfo;
import io.github.zutherb.appstash.shop.service.user.api.UserService;
import io.github.zutherb.appstash.shop.service.user.model.UserInfo;
import io.github.zutherb.appstash.shop.service.authentication.api.AuthenticationService;
import io.github.zutherb.appstash.shop.service.authentication.model.LoginInfo;
import io.github.zutherb.appstash.shop.service.user.api.UserService;
import io.github.zutherb.appstash.shop.service.user.model.UserInfo;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author zutherb
 */
@Service("authenticationService")
public class AuthenticationServiceImpl implements AuthenticationService {

    private Logger logger = LoggerFactory.getLogger(AuthenticationServiceImpl.class);

    private AuthenticationManager authenticationManager;
    private UserService userService;

    @Autowired
    public AuthenticationServiceImpl(@Qualifier("org.springframework.security.authenticationManager")
                                     AuthenticationManager authenticationManager,
                                     UserService userService) {
        this.authenticationManager = authenticationManager;
        this.userService = userService;
    }

    @Override
    public boolean authenticate(LoginInfo loginInfo) {
        try {
            Authentication usernamePasswordAuthentication =
                    new UsernamePasswordAuthenticationToken(loginInfo.getUsername(), loginInfo.getPassword());
            Authentication authenticateResult = authenticationManager.authenticate(usernamePasswordAuthentication);
            SecurityContextHolder.getContext().setAuthentication(authenticateResult);
            logger.info(String.format("Authentication of '%s' was %ssuccessful", loginInfo.getUsername(),
                    (authenticateResult.isAuthenticated() ? "" : "not ")));
            return authenticateResult.isAuthenticated();
        } catch (AuthenticationException e) {
            String msg = String.format("User '%s' could not authenticated correct:", loginInfo.getUsername());
            logger.info(msg, e);
        }
        return false;
    }

    @Override
    public void clearAuthentication() {
        SecurityContextHolder.clearContext();
    }

    @Override
    public boolean isAuthorized(String... permissions) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            return false;
        } else {
            if (CollectionUtils.isEmpty(Arrays.asList(permissions))) {
                return true;
            }
            List<GrantedAuthority> expectedAuthorities = getAuthorities(permissions);
            for (GrantedAuthority authority : authentication.getAuthorities()) {
                if (expectedAuthorities.contains(authority)) {
                    return true;
                }
            }
            return false;
        }
    }

    private List<GrantedAuthority> getAuthorities(String[] permissions) {
        ArrayList<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        for (String permission : permissions) {
            grantedAuthorities.add(new SimpleGrantedAuthority(permission));
        }
        return grantedAuthorities;
    }

    @Override
    public UserInfo getAuthenticatedUserInfo() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() != null) {
            User user = (User) authentication.getPrincipal();
            return userService.findByUsername(user.getUsername());
        }
        return null;
    }

}
