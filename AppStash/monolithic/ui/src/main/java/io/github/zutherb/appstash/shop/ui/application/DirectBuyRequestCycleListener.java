package io.github.zutherb.appstash.shop.ui.application;

import io.github.zutherb.appstash.shop.repository.product.model.ProductQuery;
import io.github.zutherb.appstash.shop.service.cart.api.Cart;
import io.github.zutherb.appstash.shop.service.product.api.ProductService;
import io.github.zutherb.appstash.shop.service.product.model.ProductInfo;
import org.apache.wicket.injection.Injector;
import org.apache.wicket.request.Url;
import org.apache.wicket.request.cycle.AbstractRequestCycleListener;
import org.apache.wicket.request.cycle.RequestCycle;
import org.apache.wicket.request.http.WebResponse;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.apache.wicket.util.string.StringValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;


public class DirectBuyRequestCycleListener extends AbstractRequestCycleListener {

    private static final String DIRECT_BUY_PROCESSING_FAILED_MESSAGE = "DirectBuy processing failed";

    private static final Logger LOGGER = LoggerFactory.getLogger(DirectBuyRequestCycleListener.class);
    private static final String DIRECT_BUY_PARAMETER = "directBuy";

    @SpringBean
    private Cart cart;

    @SpringBean
    private ProductService productService;

    public DirectBuyRequestCycleListener() {
        super();
        Injector.get().inject(this);
    }

    @Override
    public void onBeginRequest(RequestCycle cycle) {
        StringValue directBuyParameterValue = cycle.getRequest().getRequestParameters().getParameterValue(DIRECT_BUY_PARAMETER);
        if (!directBuyParameterValue.isEmpty()) {
            try {
                ProductInfo productInfo = productService.findByQuery(ProductQuery.create().withUrlname(directBuyParameterValue.toString()));
                if (productInfo != null) {
                    cart.addItem(productInfo);
                } else {
                    ShopSession.get().error(String.format("Das Product '%s' konnte nicht gefunden werden.", directBuyParameterValue));
                }
                Url urlWithoutDirectBuy = removeDirectBuyFromUrl(cycle);
                redirectTo(cycle, urlWithoutDirectBuy);
            } catch (Exception e) {
                ShopSession.get().error(DIRECT_BUY_PROCESSING_FAILED_MESSAGE);
                LOGGER.error(DIRECT_BUY_PROCESSING_FAILED_MESSAGE, e);
            }
        }
    }

    private void redirectTo(RequestCycle cycle, Url urlWithoutDirectBuy) {
        Url requestUrl = cycle.getRequest().getUrl();
        if (!requestUrl.equals(urlWithoutDirectBuy)) {
            WebResponse response = (WebResponse) cycle.getResponse();
            response.reset();
            response.sendRedirect(urlWithoutDirectBuy.toString(Url.StringMode.FULL));
        }
    }

    private Url removeDirectBuyFromUrl(RequestCycle cycle) {
        Url requestUrl = cycle.getRequest().getUrl();
        Optional<Url.QueryParameter> directBuyParamter = requestUrl.getQueryParameters()
                .stream()
                .filter(p -> DIRECT_BUY_PARAMETER.equals(p.getName()))
                .findFirst();
        if (directBuyParamter.isPresent()) {
            Url requestUrlWithoutDirectBuy = new Url(requestUrl);
            //TODO-BERND: make change request for wicket - contextpath is removed in wicket implementation
            requestUrlWithoutDirectBuy.getSegments().add(0, cycle.getRequest().getContextPath().replaceAll("/", ""));
            requestUrlWithoutDirectBuy.getQueryParameters().remove(directBuyParamter.get());
            return requestUrlWithoutDirectBuy;
        }
        return requestUrl;
    }
}
