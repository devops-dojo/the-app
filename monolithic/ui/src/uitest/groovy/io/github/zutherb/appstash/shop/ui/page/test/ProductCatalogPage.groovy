package io.github.zutherb.appstash.shop.ui.page.test

import geb.Page

class ProductCatalogPage extends Page {
    static at = { waitFor { title == "Shop" } }
    static content = {
        results(wait: true) { $("div.thumbnail") }
        result { i -> results[i] }
        resultLink { i -> result(i).find("a")[0] }
        detailPageLink { resultLink(0) }
    }
}
