package io.github.zutherb.appstash.shop.service.navigation.web

import io.github.zutherb.appstash.shop.service.navigation.domain.NavigationRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.core.io.ClassPathResource
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*

import java.util.jar.Manifest

@RestController
class NavigationController {

    @Autowired
    def NavigationRepository navigationRepository;

    @RequestMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
    @ResponseBody
    def all() {
        navigationRepository.all()
    }

    @RequestMapping(value = "/manifest", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
    @ResponseBody
    def manifest() {
        def manifestResource = new ClassPathResource("META-INF/MANIFEST.MF");
        new Manifest(manifestResource.getInputStream())
    }

    @RequestMapping(value = "/shutdown", produces = MediaType.APPLICATION_JSON_VALUE, method = [RequestMethod.GET, RequestMethod.PUT])
    @ResponseBody
    def shutdown() {
        System.exit(0)
    }
}
