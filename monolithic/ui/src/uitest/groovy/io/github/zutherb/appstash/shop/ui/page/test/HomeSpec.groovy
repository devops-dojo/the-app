package io.github.zutherb.appstash.shop.ui.page.test

import geb.Page
import geb.spock.GebReportingSpec
import org.openqa.selenium.remote.DesiredCapabilities
import org.openqa.selenium.remote.RemoteWebDriver


class HomeSpec extends GebReportingSpec {


    def "Navigate to Home Page - check articles"() {
        when:
        to HomePage
        assert theDiv.text() == "You save"

        and:
        homeLink.click()

        then:
        at HomePage

    }

}
