package io.github.zutherb.appstash.shop.ui.application;

import org.apache.wicket.request.cycle.AbstractRequestCycleListener;
import org.apache.wicket.request.cycle.RequestCycle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;

public class DockerRequestCycleListener extends AbstractRequestCycleListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(DockerRequestCycleListener.class);
    private static final String HEADER_NAME = "X-Docker-Mode-Enabled";

    @Override
    public void onBeginRequest(RequestCycle cycle) {
        if (((HttpServletRequest) cycle.getRequest().getContainerRequest()).getHeaders(HEADER_NAME).hasMoreElements()) {
            ((ShopSession) ShopSession.get()).setDockerMode(true);
            LOGGER.debug("Docker mode Enabled");
        }
    }
}
