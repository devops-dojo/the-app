package io.github.zutherb.appstash.shop.ui.application;

import org.apache.wicket.protocol.http.WebSession;
import org.apache.wicket.request.Request;

public class ShopSession extends WebSession {

    private boolean microServiceMode;
    private boolean dockerMode;

    public ShopSession(Request request) {
        super(request);
    }

    public boolean isMicroServiceMode() {
        return microServiceMode;
    }

    public void setMicroServiceMode(boolean microServiceMode) {
        this.microServiceMode = microServiceMode;
    }

    public boolean isDockerMode() {
        return dockerMode;
    }

    public void setDockerMode(boolean dockerMode) {
        this.dockerMode = dockerMode;
    }
}
