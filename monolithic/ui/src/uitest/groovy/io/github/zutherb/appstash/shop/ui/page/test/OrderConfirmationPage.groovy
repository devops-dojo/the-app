package io.github.zutherb.appstash.shop.ui.page.test

import geb.Page

class OrderConfirmationPage extends Page {
    static at = {
        waitFor { waitFor { title == "Shop" } }
        //$("li.feedbackPanelINFO span.feedbackPanelINFO").text() == "Your Order was submitted. You can taste the best pizzas in the world in a few minutes."
    }

}
