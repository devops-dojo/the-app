package io.github.zutherb.appstash.shop.service.user.impl;

import io.github.zutherb.appstash.common.service.AbstractServiceImpl;
import io.github.zutherb.appstash.shop.repository.user.api.UserRepository;
import io.github.zutherb.appstash.shop.repository.user.model.User;
import io.github.zutherb.appstash.shop.service.user.api.UserService;
import io.github.zutherb.appstash.shop.service.user.model.UserInfo;
import io.github.zutherb.appstash.common.service.AbstractServiceImpl;
import io.github.zutherb.appstash.shop.repository.user.api.UserRepository;
import io.github.zutherb.appstash.shop.repository.user.model.User;
import io.github.zutherb.appstash.shop.service.user.api.UserService;
import io.github.zutherb.appstash.shop.service.user.model.UserInfo;
import org.bson.types.ObjectId;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.encoding.ShaPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author zutherb
 */
@Service("userService")
public class UserServiceImpl extends AbstractServiceImpl<UserInfo, User> implements UserService {

    private UserRepository userRepository;
    private ShaPasswordEncoder passwordEncoder;

    @Value("${authentication.salt}")
    private String authenticationSalt;

    @Autowired
    public UserServiceImpl(@Qualifier("userRepository") UserRepository repository,
                           @Qualifier("dozerMapper") Mapper dozerMapper,
                           @Qualifier("userPasswordEncoder") ShaPasswordEncoder passwordEncoder) {
        super(repository, dozerMapper, UserInfo.class, User.class);
        this.userRepository = repository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserInfo findById(ObjectId userId) {
        User user = userRepository.findById(userId);
        return (user != null) ? getDozerMapper().map(user, UserInfo.class) : null;
    }

    @Override
    public UserInfo findByUsername(String username) {
        User user = userRepository.findByUsername(username);
        return (user != null) ? getDozerMapper().map(user, UserInfo.class) : null;
    }

    @Override
    public boolean existsUserWithEmail(String email){
        return userRepository.existsUserWithEmail(email);
    }

    @Override
    public List<UserInfo> findAll(){
        return super.findAll();
    }

    @Override
    public void save(UserInfo userInfo) {
        if(!userInfo.isPersisted()){
            String password = passwordEncoder.encodePassword(userInfo.getPassword(), authenticationSalt);
            userInfo = new UserInfo(userInfo.getFirstname(), userInfo.getLastname(), userInfo.getUsername(), userInfo.getEmail(), password,
                    userInfo.getRoles(), userInfo.getAddress());
        }
        super.save(userInfo);
    }
}
