package io.github.zutherb.appstash.shop.ui.panel.base;

import io.github.zutherb.appstash.shop.ui.mbean.FeatureTooglesBean;
import org.apache.wicket.Component;
import org.apache.wicket.behavior.AttributeAppender;
import org.apache.wicket.spring.injection.annot.SpringBean;

/**
 * @author zutherb
 */
public class HighLightBehavior extends AttributeAppender {

    @SpringBean
    private FeatureTooglesBean featureTooglesBean;

    public HighLightBehavior() {
        super("class", " highlight");
    }

    @Override
    public boolean isEnabled(Component component) {
        return featureTooglesBean.isHighlightingFeatureEnabled();
    }
}
