package io.github.zutherb.appstash.shop.ui.page.test

import geb.Page

class ProductDetailPage extends Page {
    static at = { waitFor { title == "Shop" } }
    static content = {
        results(wait: true) { $("a.addToCart") }
        result { i -> results[i] }
        addToCartLink { result(0) }
    }
}
