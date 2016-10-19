package io.github.zutherb.appstash.shop.service.user.web

import io.github.zutherb.appstash.shop.service.user.domain.User
import io.github.zutherb.appstash.shop.service.user.domain.UserRepository
import io.github.zutherb.appstash.shop.service.user.domain.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.RestController

@RestController
class UserController {

    @Autowired
    def UserRepository userRepository;

    @RequestMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    List<User> users() {
        userRepository.findAll()
    }

    @RequestMapping(value = "/user/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    User userById(@PathVariable String id) {
        userRepository.findById(id)
    }

}
