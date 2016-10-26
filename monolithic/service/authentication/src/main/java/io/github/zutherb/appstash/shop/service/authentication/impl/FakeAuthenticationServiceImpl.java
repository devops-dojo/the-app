package io.github.zutherb.appstash.shop.service.authentication.impl;

import io.github.zutherb.appstash.dataloader.reader.UserCsvReader;
import io.github.zutherb.appstash.shop.repository.user.model.User;
import io.github.zutherb.appstash.shop.service.authentication.api.FakeAuthenticationService;
import io.github.zutherb.appstash.shop.service.authentication.model.LoginInfo;
import io.github.zutherb.appstash.shop.service.user.api.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
import java.util.Random;

/**
 * @author zutherb
 */
@Component("fakeAuthenticationService")
public class FakeAuthenticationServiceImpl extends AuthenticationServiceImpl implements FakeAuthenticationService {

    private final List<User> users;

    @Autowired
    public FakeAuthenticationServiceImpl(@Qualifier("org.springframework.security.authenticationManager")
                                         AuthenticationManager authenticationManager,
                                         UserService userService,
                                         UserCsvReader userCsvReader) throws IOException {
        super(authenticationManager, userService);
        users = userCsvReader.parseCsv();
    }

    @Override
    public boolean authenticate() {
        User user = users.get(getRandom(0, users.size()-1));
        user.setUsername( user.getFirstname().toLowerCase() + "." + user.getLastname().toLowerCase( ));
        LoginInfo loginInfo = new LoginInfo(user.getUsername(), "customer123");
        return super.authenticate(loginInfo);
    }

    public int getRandom(int minimum, int maximum){
        Random rn = new Random();
        int n = maximum - minimum + 1;
        int i = Math.abs(rn.nextInt()) % n;
        return  minimum + i;
    }
}
