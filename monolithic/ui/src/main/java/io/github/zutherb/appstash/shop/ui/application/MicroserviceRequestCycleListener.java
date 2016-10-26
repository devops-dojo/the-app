package io.github.zutherb.appstash.shop.ui.application;

import org.apache.wicket.request.cycle.AbstractRequestCycleListener;
import org.apache.wicket.request.cycle.RequestCycle;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class MicroserviceRequestCycleListener extends AbstractRequestCycleListener {

    public static final List<String> HOSTS = Collections.unmodifiableList(
            Arrays.asList(
                    "registration.microservice.io",
                    "checkout.microservice.io"
            ));

    @Override
    public void onBeginRequest(RequestCycle cycle) {
        if (HOSTS.contains(cycle.getRequest().getUrl().getHost())) {
            ((ShopSession) ShopSession.get()).setMicroServiceMode(true);
        }
    }
}
