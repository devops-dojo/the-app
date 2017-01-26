package io.github.zutherb.appstash.shop.ui.page.test

import geb.Page

class CheckoutPage extends Page {
    static url = "http://test.monolith.io:8080/shop/checkout"
    static at = { waitFor { title == "Shop" } }
    static content = {
        results(wait: true) { $("div.container") }
        result { i -> results[i] }
        resultLink { i -> result(i).find("a")[0] }
        submitOrderLink { resultLink(0) }
    }
}
