package io.github.zutherb.appstash.shop.ui.page.test

import geb.Page
import geb.spock.GebReportingSpec
import org.openqa.selenium.remote.DesiredCapabilities
import org.openqa.selenium.remote.RemoteWebDriver
import io.github.zutherb.appstash.common.util.Config

class DiscountSpec extends GebReportingSpec {
    def "Check discount"() {
        when:
        to HomePage
        assert discountHeader.text() == "Save!"
        assert discountPercent.text() == Config.getProperty("GLOBAL_DISCOUNT")
        assert discountLabel.text() ==  "You save"

        and:
        homeLink.click()

        then:
        at HomePage
    }
}
