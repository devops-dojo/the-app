package io.github.zutherb.appstash.shop.ui.page.test

import geb.Page
import geb.spock.GebReportingSpec
import org.openqa.selenium.remote.DesiredCapabilities
import org.openqa.selenium.remote.RemoteWebDriver


/*
add vm params: -Dgeb.build.reportsDir="/tmp/geb" -Dgeb.env="firefox"
 */
class ShoppingFlowSpec extends GebReportingSpec {


    def "Full ordering flow"() {
        when:
        to HomePage

        and:
        catalogLink.click()

        then:
        at ProductCatalogPage

        and:
        detailPageLink.click()

        then:
        at ProductDetailPage

        and:
        addToCartLink.click()

        then:
        at ProductDetailPage

        and:
        to CheckoutPage

        then:
        at CheckoutPage

        and:
        submitOrderLink.click()

        then:
        at OrderConfirmationPage
    }

}
