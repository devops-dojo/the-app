package io.github.zutherb.appstash.shop.ui.page.test

import geb.Page
import geb.spock.GebReportingSpec
import org.openqa.selenium.remote.DesiredCapabilities
import org.openqa.selenium.remote.RemoteWebDriver
import io.github.zutherb.appstash.common.util.Config

class HomeSpec extends GebReportingSpec {


    def "Navigate to Home Page"() {
        when:
        to HomePage

        and:
        homeLink.click()

        then:
        at HomePage

    }

}
