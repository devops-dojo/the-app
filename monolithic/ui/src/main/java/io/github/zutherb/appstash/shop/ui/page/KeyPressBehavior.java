package io.github.zutherb.appstash.shop.ui.page;

import io.github.zutherb.appstash.shop.ui.mbean.DesignSelector;
import io.github.zutherb.appstash.shop.ui.mbean.DesignSelectorBean;
import io.github.zutherb.appstash.shop.ui.mbean.FeatureTooglesBean;
import org.apache.wicket.Component;
import org.apache.wicket.Page;
import org.apache.wicket.ajax.AjaxEventBehavior;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.attributes.AjaxCallListener;
import org.apache.wicket.ajax.attributes.AjaxRequestAttributes;
import org.apache.wicket.ajax.attributes.IAjaxCallListener;
import org.apache.wicket.request.Request;
import org.apache.wicket.request.cycle.RequestCycle;
import org.apache.wicket.spring.injection.annot.SpringBean;

public class KeyPressBehavior extends AjaxEventBehavior {

    @SpringBean(name = "designSelector")
    private DesignSelector selectorBean;

    @SpringBean
    private FeatureTooglesBean tooglesBean;

    private Page currentPage;

    public KeyPressBehavior(Page currentPage) {
        super("keydown");
        this.currentPage = currentPage;
    }

    @Override
    protected void updateAjaxAttributes(AjaxRequestAttributes attributes) {
        super.updateAjaxAttributes(attributes);

        IAjaxCallListener listener = new AjaxCallListener() {
            @Override
            public CharSequence getPrecondition(Component component) {
                //this javascript code evaluates wether an ajaxcall is necessary.
                //Here only by keyocdes for F9 and F10
                return "var keycode = Wicket.Event.keyCode(attrs.event);" +
                        "if ((keycode == 112) || (keycode == 113) || (keycode == 114) || (keycode == 115))" +
                        "    return true;" +
                        "else" +
                        "    return false;";
            }
        };
        attributes.getAjaxCallListeners().add(listener);

        //Append the pressed keycode to the ajaxrequest
        attributes.getDynamicExtraParameters()
                .add("var eventKeycode = Wicket.Event.keyCode(attrs.event);" +
                        "return {keycode: eventKeycode};");

        //whithout setting, no keyboard events will reach any inputfield
        attributes.setPreventDefault(true);
    }

    @Override
    protected void onEvent(AjaxRequestTarget target) {
        //Extract the keycode parameter from RequestCycle
        final Request request = RequestCycle.get().getRequest();
        final String jsKeycode = request.getRequestParameters()
                .getParameterValue("keycode").toString("");

        switch (jsKeycode) {
            case "112":
                selectorBean.standard();
                target.getPage().setResponsePage(currentPage);
                break;
            case "113":
                selectorBean.next();
                target.getPage().setResponsePage(currentPage);
                break;
            case "114":
                tooglesBean.toogleHighlightingFeature();
                target.getPage().setResponsePage(currentPage);
                break;
            case "115":
                tooglesBean.toogleTopSellerFeature();
                target.getPage().setResponsePage(currentPage);
                break;
            default:
        }
    }
}
