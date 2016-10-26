package io.github.zutherb.appstash.shop.ui.panel;

import io.github.zutherb.appstash.shop.ui.application.ShopApplication;
import io.github.zutherb.appstash.shop.ui.application.ShopSession;
import io.github.zutherb.appstash.shop.ui.event.AjaxEvent;
import io.github.zutherb.appstash.shop.ui.event.cart.AddToCartEvent;
import io.github.zutherb.appstash.shop.ui.event.cart.RemoveFromCartEvent;
import io.github.zutherb.appstash.shop.ui.event.login.LoginEvent;
import io.github.zutherb.appstash.shop.ui.navigation.*;
import io.github.zutherb.appstash.shop.ui.panel.base.HighLightBehavior;
import io.github.zutherb.appstash.shop.ui.panel.login.LoginInfoPanel;
import io.github.zutherb.appstash.shop.ui.panel.login.LoginModalPanel;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.wicket.AttributeModifier;
import org.apache.wicket.Component;
import org.apache.wicket.Page;
import org.apache.wicket.ajax.AjaxEventBehavior;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.behavior.AttributeAppender;
import org.apache.wicket.event.IEvent;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.link.ExternalLink;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.*;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.apache.wicket.util.string.interpolator.MapVariableInterpolator;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class NavigationPanel extends Panel {
    private static final long serialVersionUID = 6989676548448303645L;

    @SpringBean(name = "navigationProvider")
    private NavigationProvider navigationProvider;

    private LoginModalPanel loginModalPanel;

    public NavigationPanel(String id, LoginModalPanel loginModalPanel) {
        super(id);
        this.loginModalPanel = loginModalPanel;
        add(homePageLink());
        add(mainNavigation());
        add(otherNavigation());
        add(loginInfoPanel());
        setOutputMarkupId(true);
        setOutputMarkupPlaceholderTag(true);
    }

    private Component homePageLink() {
        BookmarkablePageLink<Void> pageLink = new BookmarkablePageLink<>("home", ShopApplication.get().getHomePage());
        pageLink.add(new AttributeAppender("class", Model.of("homePageLink"), " "));
        if (((ShopSession) ShopSession.get()).isMicroServiceMode()) {
            return new ExternalLink("home", "http://shop.microservice.io");
        }
        return pageLink;
    }

    private LoginInfoPanel loginInfoPanel() {
        return new LoginInfoPanel("loginInfoPanel");
    }

    private Component otherNavigation() {
        WebMarkupContainer otherNavigationWrapper = new WebMarkupContainer("otherNavigationWrapper");
        otherNavigationWrapper.add(new ListView<NavigationGroup>("otherNavigation", otherNavigationModel()) {
            private static final long serialVersionUID = 5452289050410685190L;

            public int counter = 0;

            @Override
            protected void populateItem(ListItem<NavigationGroup> listItem) {
                listItem.add(dropDown(listItem));
            }

            private Component dropDown(ListItem<NavigationGroup> listItem) {
                WebMarkupContainer dropdown = new WebMarkupContainer("dropdown");
                IModel<String> id = Model.of("menu" + counter++);
                dropdown.add(new AttributeModifier("id", id));
                dropdown.add(navigationLink(listItem, id));
                ListView<NavigationEntry> items = navigationEntryView("items", navigationEntries(listItem.getModel()));
                dropdown.add(items);
                dropdown.setVisibilityAllowed(isVisible(items));
                return dropdown;
            }

            private boolean isVisible(ListView<NavigationEntry> items) {
                boolean result = false;
                for (NavigationEntry entry : items.getList()) {
                    result |= entry.isVisible();
                }
                return result;
            }

            private WebMarkupContainer navigationLink(ListItem<NavigationGroup> listItem, IModel<String> id) {
                WebMarkupContainer link = new WebMarkupContainer("link");
                link.add(new Label("name", new PropertyModel<String>(listItem.getModel(), "name")));
                link.add(new AttributeModifier("href", "#" + id.getObject()));
                return link;
            }

            private IModel<List<NavigationEntry>> navigationEntries(final IModel<NavigationGroup> navigationGroupModel) {
                return new AbstractReadOnlyModel<List<NavigationEntry>>() {
                    private static final long serialVersionUID = 5373877077756361153L;

                    @Override
                    public List<NavigationEntry> getObject() {
                        return navigationGroupModel.getObject().getNavigationEntries();
                    }
                };
            }
        });
        otherNavigationWrapper.add(new HighLightBehavior());
        return otherNavigationWrapper;
    }

    private IModel<List<NavigationGroup>> otherNavigationModel() {
        return new AbstractReadOnlyModel<List<NavigationGroup>>() {
            private static final long serialVersionUID = 6806034829063836686L;

            @Override
            public List<NavigationGroup> getObject() {
                List<NavigationGroup> navigationGroups = new LinkedList<>(navigationProvider.getNavigation().getNavigationGroups());
                CollectionUtils.filter(navigationGroups, object -> object instanceof NavigationGroup && !"main".equals(((NavigationGroup) object).getName()));
                return navigationGroups;
            }
        };
    }

    private Component mainNavigation() {
        WebMarkupContainer mainNavigationWrapper = new WebMarkupContainer("mainNavigationWrapper");
        mainNavigationWrapper.add(navigationEntryView("mainNavigation", mainNavigationEntriesModel()));
        mainNavigationWrapper.add(new HighLightBehavior());
        return mainNavigationWrapper;
    }

    private ListView<NavigationEntry> navigationEntryView(final String id, final IModel<List<NavigationEntry>> listModel) {
        return new ListView<NavigationEntry>(id, listModel) {
            private static final long serialVersionUID = -2695266767809997711L;

            @Override
            protected void populateItem(ListItem<NavigationEntry> listItem) {
                WebMarkupContainer item = new WebMarkupContainer("item");
                NavigationEntry navigationEntry = listItem.getModelObject();
                WebMarkupContainer pageLink = navigationLink(navigationEntry);
                pageLink.add(new AttributeAppender("class", new AbstractReadOnlyModel<String>() {

                    final String template = "%sLink";

                    @Override
                    public String getObject() {
                        return String.format(template, navigationEntry.getName().toLowerCase());
                    }
                }, " "));
                pageLink.add(new Label("name", Model.of(navigationEntry.getName())));
                if (navigationEntry.getPageClass().isAnnotationPresent(ModalPanelItem.class)) {
                    pageLink.add(new AjaxEventBehavior("click") {
                        private static final long serialVersionUID = 8111739824971748968L;

                        @Override
                        protected void onEvent(AjaxRequestTarget target) {
                            target.appendJavaScript(loginModalPanel.generateToggleScript());
                        }
                    });
                    pageLink.add(new AttributeModifier("href", "#"));
                }

                item.add(pageLink);
                item.setVisibilityAllowed(navigationEntry.isVisible());
                item.add(new AttributeModifier("class", activeModel(navigationEntry)));

                listItem.add(item);
            }

            private IModel<String> activeModel(NavigationEntry navigationEntry) {
                Class<? extends Page> currentPageClazz = getPage().getPageClass();
                Class<? extends Page> navigationPageClazz = navigationEntry.getPageClass();
                boolean assignableFrom = navigationPageClazz.isAssignableFrom(currentPageClazz);

                if (!assignableFrom)
                    return Model.of("");
                if (!currentPageClazz.isAnnotationPresent(EnumProductTypeNavigationItem.class))
                    return Model.of("active");

                PageParameters currentParameters = getPage().getPageParameters();
                PageParameters navigationParameters = navigationEntry.getPageParameters();

                if (currentParameters == null || currentParameters.isEmpty()) {
                    String enumName = navigationParameters.get("type").toString();
                    EnumProductTypeNavigationItem enumNavigationItem = currentPageClazz.getAnnotation(EnumProductTypeNavigationItem.class);
                    return Model.of(StringUtils.equals(enumNavigationItem.defaultEnum(), enumName) ? "active" : "");
                }
                return Model.of(currentParameters.equals(navigationParameters) ? "active" : "");
            }


        };
    }

    private WebMarkupContainer navigationLink(NavigationEntry navigationEntry) {
        String navigationEntryPageClassName = navigationEntry.getPageClass().getName();
        if (((ShopSession) ShopSession.get()).isMicroServiceMode()) {
            switch (navigationEntryPageClassName) {
                case "io.github.zutherb.appstash.shop.ui.page.catalog.ProductCatalogPage":
                    return new ExternalLink("link", new ProductCatalogPageStringResourceModel(new StringResourceModel(navigationEntryPageClassName, this, null), Model.of(navigationEntry)));
                default:
                    return new ExternalLink("link", new StringResourceModel(navigationEntryPageClassName, this, null));
            }
        }
        if (((ShopSession) ShopSession.get()).isDockerMode()) {
            String resourceKey = "docker." + navigationEntryPageClassName;
            switch (navigationEntryPageClassName) {
                case "io.github.zutherb.appstash.shop.ui.page.catalog.ProductCatalogPage":
                    return new ExternalLink("link", new ProductCatalogPageStringResourceModel(new StringResourceModel(resourceKey, this, null), Model.of(navigationEntry)));
                default:
                    return new ExternalLink("link", new StringResourceModel(resourceKey, this, null));
            }
        }
        return new BookmarkablePageLink<>("link",
                navigationEntry.getPageClass(), navigationEntry.getPageParameters());
    }

    private IModel<List<NavigationEntry>> mainNavigationEntriesModel() {
        return new AbstractReadOnlyModel<List<NavigationEntry>>() {
            private static final long serialVersionUID = 145705329210928083L;

            @Override
            public List<NavigationEntry> getObject() {
                return navigationProvider.getNavigation().getMainNavigationGroup().getNavigationEntries();
            }
        };
    }

    @Override
    public void onEvent(IEvent<?> event) {
        if (event.getPayload() instanceof AddToCartEvent
                || event.getPayload() instanceof RemoveFromCartEvent
                || event.getPayload() instanceof LoginEvent) {
            ((AjaxEvent) event.getPayload()).getTarget().add(this);
        }
    }

    private static class ProductCatalogPageStringResourceModel extends AbstractReadOnlyModel<String> {

        public static final String TYPE = "type";
        private final IModel<String> linkTemplateModel;
        private Model<NavigationEntry> navigationEntryModel;

        public ProductCatalogPageStringResourceModel(IModel<String> linkTemplateModel, Model<NavigationEntry> navigationEntryModel) {
            this.linkTemplateModel = linkTemplateModel;
            this.navigationEntryModel = navigationEntryModel;
        }

        @Override
        public String getObject() {
            NavigationEntry navigationEntry = this.navigationEntryModel.getObject();
            String type = navigationEntry.getPageParameters().get(TYPE).toString();
            Map<String, String> replacements = Collections.singletonMap(TYPE, type);
            return new MapVariableInterpolator(linkTemplateModel.getObject(), replacements).toString();
        }
    }
}
