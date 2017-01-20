package io.github.zutherb.appstash.shop.ui.page.test

import geb.Page
import geb.spock.GebReportingSpec
import org.openqa.selenium.remote.DesiredCapabilities
import org.openqa.selenium.remote.RemoteWebDriver
import io.github.zutherb.appstash.common.util.Config

class HomeSpec extends GebReportingSpec {


    def "Navigate to Home Page - check articles"() {
        when:
        to HomePage
        if (Config.getProperty("GLOBAL_DISCOUNT")!=null && Double.parseDouble(Config.getProperty("GLOBAL_DISCOUNT")))>0{
          assert theDiv.text() == "You save"
        }
        
        and:
        homeLink.click()

        then:
        at HomePage

    }

}
