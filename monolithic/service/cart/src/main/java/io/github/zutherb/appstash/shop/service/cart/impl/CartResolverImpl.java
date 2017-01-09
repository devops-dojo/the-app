package io.github.zutherb.appstash.shop.service.cart.impl;

import io.github.zutherb.appstash.shop.service.cart.api.CartFulfillmentProvider;
import io.github.zutherb.appstash.shop.service.cart.api.CartResolver;
import io.github.zutherb.appstash.shop.service.cart.model.CartItemInfo;
import io.github.zutherb.appstash.shop.service.product.model.ProductInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jmx.export.annotation.ManagedOperation;
import org.springframework.jmx.export.annotation.ManagedResource;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import static java.util.Collections.unmodifiableList;

@Component("cart")
@ManagedResource(objectName = "io.github.zutherb.appstash.shop.service.cart:name=CartResolver")
public class CartResolverImpl implements CartResolver {

    private static final Logger LOGGER = LoggerFactory.getLogger(CartResolverImpl.class);

    private final List<CartDelegator> cartFulfillmentProviders;
    private final CyclicCounter cardCounter;


    @Autowired
    public CartResolverImpl(Map<String, CartFulfillmentProvider> cartFulfillmentProviders) {
        Assert.notEmpty(cartFulfillmentProviders, "No CartFulfillmentProviders found");
        this.cartFulfillmentProviders = unmodifiableList(cartFulfillmentProviders.entrySet()
                .stream()
                .map(entry -> new CartDelegator(entry.getKey(), entry.getValue()))
                .collect(Collectors.toList()));
        cardCounter = new CyclicCounter(this.cartFulfillmentProviders.size());
    }

    @Override
    public CartItemInfo addItem(ProductInfo product) {
        return getCartDelegator().getProvider().addItem(product);
    }

    @Override
    public void removeItem(CartItemInfo item) {
        getCartDelegator().getProvider().removeItem(item);
    }

    @Override
    public List<CartItemInfo> getAll() {
        return getCartDelegator().getProvider().getAll();
    }

    @Override
    public void clear() {
        getCartDelegator().getProvider().clear();
    }

    @Override
    public boolean isEmpty() {
        return getCartDelegator().getProvider().isEmpty();
    }

    @Override
    public BigDecimal getTotalSum() {
        return getCartDelegator().getProvider().getTotalSum();
    }
    
    @Override
    public BigDecimal getDiscountSum() {
        return getCartDelegator().getProvider().getDiscountSum();
    }    

    @ManagedOperation
    @Override
    public String getActiveCartFulfillmentProviderName() {
        return getCartDelegator().getName();
    }

    @ManagedOperation
    @Override
    public void next() {
        cardCounter.cyclicallyIncrementAndGet();
        LOGGER.info(String.format("Switch Cart to '%s'", getActiveCartFulfillmentProviderName()));
    }

    private CartDelegator getCartDelegator() {
        return getCartFulfillmentProviders().get(cardCounter.current());
    }

    private List<CartDelegator> getCartFulfillmentProviders() {
        return cartFulfillmentProviders;
    }

    private CyclicCounter getCardCounter() {
        return cardCounter;
    }

    private static final class CyclicCounter {

        private final int maxVal;
        private final AtomicInteger ai = new AtomicInteger(0);

        public CyclicCounter(int maxVal) {
            this.maxVal = maxVal;
        }

        public int cyclicallyIncrementAndGet() {
            int curVal, newVal;
            do {
                curVal = this.ai.get();
                newVal = (curVal + 1) % this.maxVal;
            } while (!this.ai.compareAndSet(curVal, newVal));
            return newVal;
        }

        public int current() {
            return ai.get();

        }
    }

    private static final class CartDelegator {
        private String name;
        private CartFulfillmentProvider provider;

        private CartDelegator(String name, CartFulfillmentProvider provider) {
            this.name = name;
            this.provider = provider;
        }

        public String getName() {
            return name;
        }

        public CartFulfillmentProvider getProvider() {
            return provider;
        }
    }
}
