package io.github.zutherb.appstash.shop.ui.application.settings;

import org.apache.wicket.ajax.WicketAjaxDebugJQueryResourceReference;
import org.apache.wicket.ajax.WicketAjaxJQueryResourceReference;
import org.apache.wicket.ajax.WicketEventJQueryResourceReference;
import org.apache.wicket.request.resource.JavaScriptResourceReference;
import org.apache.wicket.request.resource.ResourceReference;
import org.apache.wicket.settings.JavaScriptLibrarySettings;

public class AbstractJavaScriptLibrarySettings extends JavaScriptLibrarySettings {

    private static String jQueryPath = "jquery/jquery-1.9.1.min.js";

    @Override
    public ResourceReference getJQueryReference() {
        return JQueryResourceReference.get();
    }

    @Override
    public ResourceReference getWicketEventReference() {
        return WicketEventJQueryResourceReference.get();
    }

    @Override
    public ResourceReference getWicketAjaxReference() {
        return WicketAjaxJQueryResourceReference.get();
    }

    @Override
    public ResourceReference getWicketAjaxDebugReference() {
        return WicketAjaxDebugJQueryResourceReference.get();
    }

    protected static void setJQueryPath (String newJQueryPath) {
        jQueryPath = newJQueryPath;
    }

    public static class JQueryResourceReference extends JavaScriptResourceReference {
        private static final long serialVersionUID = 1L;

        private static final JQueryResourceReference INSTANCE = new JQueryResourceReference();

        /**
         * Normally you should not use this method, but use
         * {@link IJavaScriptLibrarySettings#getJQueryReference()} to prevent version conflicts.
         *
         * @return the single instance of the resource reference
         */
        public static JQueryResourceReference get() {
            return INSTANCE;
        }

        private JQueryResourceReference() {
            super(AbstractJavaScriptLibrarySettings.class, jQueryPath);
        }
    }

}
