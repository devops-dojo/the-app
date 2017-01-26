package io.github.zutherb.appstash.shop.ui.page.test

import geb.Page

class HomePage extends Page {
    static url = "http://test.monolith.io:8080/shop/"
    static at = { waitFor { title == "Shop" } }

    static content = {
        results(wait: true) { $("li a.tabletLink") }
        result { i -> results[i] }
        catalogLink { result(0) }
        homeLink { result(0) }
        discountHeader { $('span', id: 'discountHeader') }
        discountPercent { $('span', id: 'discountPercent') }
        discountLabel { $('div', id: 'discountLabel') }
    }
}
