package io.github.zutherb.appstash.shop.ui.application;

import io.github.zutherb.appstash.shop.ui.mbean.DesignSelector;
import io.github.zutherb.appstash.shop.ui.mbean.DesignSelectorBean;
import org.apache.wicket.injection.Injector;
import org.apache.wicket.request.cycle.AbstractRequestCycleListener;
import org.apache.wicket.request.cycle.RequestCycle;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;

import static java.lang.String.format;

public class BootstrapDesignRequestCycleListener extends AbstractRequestCycleListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(BootstrapDesignRequestCycleListener.class);
    private static final String HEADER_NAME = "X-Bootstrap-Design";

    @SpringBean(name = "designSelector")
    private DesignSelector designSelectorBean;

    public BootstrapDesignRequestCycleListener() {
        Injector.get().inject(this);
    }

    @Override
    public void onBeginRequest(RequestCycle cycle) {
        Enumeration<String> headers = ((HttpServletRequest) cycle.getRequest().getContainerRequest()).getHeaders(HEADER_NAME);
        if (headers.hasMoreElements() && !designSelectorBean.isUsedToSelectedDesign()) {
            String themeName = headers.nextElement();
            if (designSelectorBean.getAvailableDesignTypes().contains(themeName)) {
                designSelectorBean.setDesignType(themeName);
                LOGGER.debug(format("Bootstrap Design is set to %s.", themeName));
            } else {
                LOGGER.error(format("Bootstrap Design '%s' is present.", themeName));
            }
        }
    }
}
