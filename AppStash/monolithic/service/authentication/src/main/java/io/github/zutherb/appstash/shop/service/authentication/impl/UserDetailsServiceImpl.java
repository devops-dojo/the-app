package io.github.zutherb.appstash.shop.service.authentication.impl;

import io.github.zutherb.appstash.shop.repository.user.api.UserRepository;
import io.github.zutherb.appstash.shop.repository.user.model.User;
import io.github.zutherb.appstash.shop.repository.user.api.UserRepository;
import io.github.zutherb.appstash.shop.repository.user.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.stream.Collectors;

/**
 * @author zutherb
 */
@Component("userDetailsService")
public class UserDetailsServiceImpl implements UserDetailsService {

    private UserRepository userRepository;

    @Autowired
    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if (user != null) {
            return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), mapRoles(user));
        }
        throw new UsernameNotFoundException(String.format("Username '%s' not found", username));
    }

    private Collection<? extends GrantedAuthority> mapRoles(User user) {
        return user.getRoles()
                .stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toSet());
    }
}
