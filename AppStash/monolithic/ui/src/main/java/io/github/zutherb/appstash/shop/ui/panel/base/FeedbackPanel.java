package io.github.zutherb.appstash.shop.ui.panel.base;

import org.apache.wicket.Component;
import org.apache.wicket.behavior.AttributeAppender;
import org.apache.wicket.feedback.FeedbackMessage;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.LoadableDetachableModel;

/**
 * @author zutherb
 */
public class FeedbackPanel extends Panel {
    private static final long serialVersionUID = -8253921627199180455L;

    private org.apache.wicket.markup.html.panel.FeedbackPanel feedbackPanel;

    public FeedbackPanel(String id) {
        super(id);
        add(wrapperContainer());
        setOutputMarkupId(true);
    }

    private Component wrapperContainer() {
        WebMarkupContainer wrapperContainer = new WebMarkupContainer("wrapperContainer");
        wrapperContainer.add(feedbackPanel());
        wrapperContainer.add(new AttributeAppender("class", new LoadableDetachableModel<String>() {
            @Override
            protected String load() {
                if (feedbackPanel.anyMessage(FeedbackMessage.ERROR)) {
                    return "alert-danger";
                }
                if (feedbackPanel.anyMessage(FeedbackMessage.SUCCESS)) {
                    return "alert-success";
                }
                if (feedbackPanel.anyMessage(FeedbackMessage.INFO)) {
                    return "alert-info";
                }
                return "";
            }
        }, " "));
        return wrapperContainer;
    }

    private org.apache.wicket.markup.html.panel.FeedbackPanel feedbackPanel() {
        feedbackPanel = new org.apache.wicket.markup.html.panel.FeedbackPanel("feedbackPanel");
        return feedbackPanel;
    }

    @Override
    protected void onConfigure() {
        super.onConfigure();
        setVisible(feedbackPanel.anyMessage());
        //setVisible(false);
    }
}
