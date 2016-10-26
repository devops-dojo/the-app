package io.github.zutherb.appstash.shop.service.authentication.impl;

import io.github.zutherb.appstash.shop.repository.user.api.UserRepository;
import io.github.zutherb.appstash.shop.repository.user.model.Address;
import io.github.zutherb.appstash.shop.repository.user.model.Role;
import io.github.zutherb.appstash.shop.repository.user.model.User;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Collections;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

/**
 * @author zutherb
 */
public class UserDetailsServiceImplTest {

    private static final String ADMIN = "admin";
    private static final String CUSTOMER = "customer";

    @Mock
    private UserRepository userRepository;
    private UserDetailsService userDetailsService;

    @Before
    public void setUp() throws Exception {
        initMocks(this);
        userDetailsService = new UserDetailsServiceImpl(userRepository);
        when(userRepository.findByUsername(ADMIN)).thenReturn(new User(ADMIN, "Bernd", "Zuther", "admin123",
                new Address("Lindwurmstraße", "80337", "München", "97"), Collections.singleton(new Role(ADMIN))));
        when(userRepository.findByUsername(CUSTOMER)).thenReturn(null);
    }

    @Test
    public void testLoadUserByUsername() throws Exception {
        UserDetails userDetails = userDetailsService.loadUserByUsername(ADMIN);
        assertNotNull(userDetails);
        assertEquals(ADMIN, userDetails.getUsername());
        assertFalse(userDetails.getAuthorities().isEmpty());
        assertEquals(ADMIN, userDetails.getAuthorities().iterator().next().getAuthority());
    }

    @Test(expected = UsernameNotFoundException.class)
    public void testLoadUserByUsernameNotFound() throws Exception {
        userDetailsService.loadUserByUsername("customer");
    }


}
