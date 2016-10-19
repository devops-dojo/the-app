package io.github.zutherb.appstash.shop.ui.page.catalog;


import io.github.zutherb.appstash.shop.repository.product.model.ProductType;
import io.github.zutherb.appstash.shop.service.product.api.ProductService;
import io.github.zutherb.appstash.shop.service.product.model.ProductInfo;
import io.github.zutherb.appstash.shop.ui.navigation.EnumProductTypeNavigationItem;
import io.github.zutherb.appstash.shop.ui.page.AbstractBasePage;
import io.github.zutherb.appstash.shop.ui.panel.base.HighLightBehavior;
import io.github.zutherb.appstash.shop.ui.panel.cart.CartPanel;
import io.github.zutherb.appstash.shop.ui.panel.product.ProductItemPanel;
import io.github.zutherb.appstash.shop.ui.panel.product.TopSellerRecommendationPanel;
import io.github.zutherb.appstash.shop.ui.tracking.TrackingService;
import org.apache.wicket.Component;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.data.DataView;
import org.apache.wicket.markup.repeater.data.IDataProvider;
import org.apache.wicket.model.AbstractReadOnlyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.wicketstuff.annotation.mount.MountPath;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


@MountPath("productcatalog/${type}")
@EnumProductTypeNavigationItem(enumClazz = ProductType.class, defaultEnum = "HANDY", sortOrder = 2)
public class ProductCatalogPage extends AbstractBasePage {

    @SpringBean(name = "productService")
    private ProductService productService;

    @SpringBean
    private TrackingService trackingService;

    private IModel<ProductType> productTypeModel;
    private IModel<List<List<ProductInfo>>> productListModel;
    private Component cartPanel;

    public ProductCatalogPage(PageParameters pageParameters) {
        super(pageParameters);

        productTypeModel = productTypeModel();
        productListModel = productListModel();
        cartPanel = cartPanel();
        add(productsWrapper());
        add(cartPanel);
        add(recommendationPanel());
        setOutputMarkupId(true);
    }

    @Override
    protected void onBeforeRender() {
        super.onBeforeRender();
        List<List<ProductInfo>> lists = productListModel.getObject();
        List<ProductInfo> trackedProducts = new ArrayList<>();
        for(List<ProductInfo> productInfos : lists) {
            trackedProducts.addAll(productInfos);
        }
        trackingService.trackProductView(trackedProducts, getAuthenticationService().getAuthenticatedUserInfo());
    }

    private Component productsWrapper() {
        WebMarkupContainer productsWrapper = new WebMarkupContainer("productsWrapper");
        productsWrapper.add(rowView());
        productsWrapper.add(new HighLightBehavior());
        return productsWrapper;
    }

    private Component rowView() {
        return new ListView<List<ProductInfo>>("row", productListModel) {
            @Override
            protected void populateItem(ListItem<List<ProductInfo>> item) {
                item.add(productView(item.getModel()));
            }
        };
    }

    private IModel<ProductType> productTypeModel() {
        return new AbstractReadOnlyModel<ProductType>() {

            private ProductType productType;

            @Override
            public ProductType getObject() {
                if (productType == null) {
                    if (getPageParameters() == null || getPageParameters().get("type") == null) {
                        productType = ProductType.HANDY;
                    } else {
                        productType = ProductType.fromUrlname(getPageParameters().get("type").toString());
                    }
                }
                return productType;
            }
        };
    }

    @Override
    protected void onConfigure() {
        cartPanel.setVisible(productListModel.getObject().iterator().hasNext());
    }

    private IModel<List<List<ProductInfo>>> productListModel() {
        return new LoadableDetachableModel<List<List<ProductInfo>>>() {
            @Override
            protected List<List<ProductInfo>> load() {
                List<List<ProductInfo>> lists = new ArrayList<>();
                List<ProductInfo> allProductInfos = new ArrayList<>(productService.findAll(productTypeModel.getObject()));
                while (allProductInfos.size() > 4) {
                    List<ProductInfo> subList = allProductInfos.subList(0, 4);
                    lists.add(new ArrayList<>(subList));
                    allProductInfos.removeAll(subList);
                }
                lists.add(allProductInfos);
                return lists;
            }
        };
    }

    private Component productView(IModel<List<ProductInfo>> model) {
        return new DataView<ProductInfo>("products", productDataProvider(model)) {

            @Override
            protected void populateItem(final Item<ProductInfo> item) {
                ProductItemPanel productItem = new ProductItemPanel("productItem", feedback, item.getModel());
                productItem.setOutputMarkupId(true);
                item.add(productItem);
            }
        };
    }


    private IDataProvider<ProductInfo> productDataProvider(IModel<List<ProductInfo>> model) {
        return new IDataProvider<ProductInfo>() {
            @Override
            public Iterator<ProductInfo> iterator(long first, long count) {
                return model.getObject().iterator();
            }

            @Override
            public long size() {
                return model.getObject().size();
            }

            @Override
            public IModel<ProductInfo> model(ProductInfo object) {
                return Model.of(object);
            }

            @Override
            public void detach() {
            }
        };
    }

    private Component cartPanel() {
        return new CartPanel("cartPanel");
    }

    private TopSellerRecommendationPanel recommendationPanel() {
        return new TopSellerRecommendationPanel("recommendationPanel", feedback, productTypeModel);
    }
}
