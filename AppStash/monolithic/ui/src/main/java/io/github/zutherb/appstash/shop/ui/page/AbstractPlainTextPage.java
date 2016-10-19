package io.github.zutherb.appstash.shop.ui.page;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.request.http.WebResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;

public abstract class AbstractPlainTextPage extends WebPage {
    protected final static Logger LOGGER = LoggerFactory.getLogger(VersionPage.class);

    private WebResponse newStringResponse() {
        WebResponse response = (WebResponse) getResponse();
        response.setContentType("text/plain");
        response.write(responseText());
        response.flush();
        response.setStatus(HttpStatus.OK.value());
        return response;
    }

    @Override
    public void renderPage() {
        WebResponse response = newStringResponse();
        getRequestCycle().setResponse(response);
    }

    protected abstract String responseText();
}
