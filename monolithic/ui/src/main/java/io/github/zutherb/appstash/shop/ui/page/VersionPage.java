package io.github.zutherb.appstash.shop.ui.page;

import io.github.zutherb.appstash.shop.ui.application.ShopApplication;
import net.logstash.logback.encoder.org.apache.commons.io.FileUtils;
import org.wicketstuff.annotation.mount.MountPath;

import java.io.File;

@MountPath("/version")
public class VersionPage extends AbstractPlainTextPage {

    private static final String MANIFEST_MF_PATH = "/META-INF/MANIFEST.MF";

    public VersionPage() { /* NOOP */}

    @Override
    protected String responseText() {
        try {
            String realPath = ShopApplication.get().getServletContext().getRealPath(MANIFEST_MF_PATH);
            return FileUtils.readFileToString(new File(realPath));
        } catch (Exception e) {
            LOGGER.error("Manifest could not be opened: ", e);
            throw new RuntimeException(e);
        }
    }


}