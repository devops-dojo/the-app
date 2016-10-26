package io.github.zutherb.appstash.shop.service.order.impl;

import io.github.zutherb.appstash.common.service.AbstractServiceImpl;
import io.github.zutherb.appstash.dataloader.reader.SupplierCsvReader;
import io.github.zutherb.appstash.shop.repository.order.api.OrderIdProvider;
import io.github.zutherb.appstash.shop.repository.order.api.OrderRepository;
import io.github.zutherb.appstash.shop.repository.order.model.Order;
import io.github.zutherb.appstash.shop.repository.order.model.Supplier;
import io.github.zutherb.appstash.shop.service.cart.api.Cart;
import io.github.zutherb.appstash.shop.service.order.api.OrderService;
import io.github.zutherb.appstash.shop.service.order.model.OrderInfo;
import io.github.zutherb.appstash.shop.service.user.api.UserService;
import org.apache.commons.lang3.Validate;
import org.dozer.Mapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 * @author zutherb
 */
@Service("orderService")
@Scope(value = "singleton", proxyMode = ScopedProxyMode.INTERFACES)
public class OrderServiceImpl extends AbstractServiceImpl<OrderInfo, Order> implements OrderService {

    private static Logger logger = LoggerFactory.getLogger(OrderServiceImpl.class);

    private OrderRepository orderRepository;
    private Mapper dozerMapper;
    private OrderIdProvider orderIdProvider;
    private Cart cart;
    private SupplierCsvReader supplierCsvReader;
    private UserService userService;

    List<Supplier> suppliers;

    @Autowired
    public OrderServiceImpl(@Qualifier("orderRepository") OrderRepository repository,
                            @Qualifier("dozerMapper") Mapper dozerMapper,
                            @Qualifier("orderIdProvider") OrderIdProvider orderIdProvider,
                            @Qualifier("cart") Cart cart,
                            @Qualifier("supplierCsvReader") SupplierCsvReader supplierCsvReader,
                            @Qualifier("userService") UserService userService) {
        super(repository, dozerMapper, OrderInfo.class, Order.class);
        orderRepository = repository;
        this.dozerMapper = dozerMapper;
        this.orderIdProvider = orderIdProvider;
        this.cart = cart;
        this.supplierCsvReader = supplierCsvReader;
        this.userService = userService;
    }

    @PostConstruct
    public void initSuppliers() throws IOException {
        suppliers = supplierCsvReader.parseCsv();
    }

    @Override
    public OrderInfo submitOrder(OrderInfo orderInfo, String sessionId) {
        Long orderId = orderIdProvider.nextVal();
        OrderInfo orderInfoToSave = new OrderInfo(orderId, sessionId, orderInfo);
        save(orderInfoToSave);
        logger.info(String.format("Order %d was submited", orderInfoToSave.getOrderId()));
        cart.clear();
        return orderInfoToSave;
    }

    @Override
    public List<OrderInfo> findAll(int limit, int offset, Sort sort) {
        return mapListOfSourceEntitiesToDestinationEntities(orderRepository.findAll(limit, offset, sort));
    }

    @Override
    public List<OrderInfo> findInRange(Date fromDate, Date toDate, int limit, int offset, Sort sort) {
        return mapListOfSourceEntitiesToDestinationEntities(orderRepository.findInRange(fromDate, toDate, limit, offset, sort));
    }

    @Override
    public OrderInfo findFirstOrder() {
        return dozerMapper.map(orderRepository.findFirstOrder(), OrderInfo.class);
    }

    @Override
    public OrderInfo findLastOrder() {
        return dozerMapper.map(orderRepository.findLastOrder(), OrderInfo.class);
    }

    @Override
    public long countInRange(Date fromDate, Date toDate) {
        return orderRepository.countInRange(fromDate, toDate);
    }

    @Override
    public void save(OrderInfo object) {
        Order mappedObject = dozerMapper.map(object, Order.class);
        Validate.notNull(userService.findById(mappedObject.getUserId()));
        mappedObject.setSupplier(suppliers.get(getRandom(0, suppliers.size() - 1)));
        orderRepository.save(mappedObject);
    }

    public int getRandom(int minimum, int maximum) {
        Random rn = new Random();
        int n = maximum - minimum + 1;
        int i = Math.abs(rn.nextInt()) % n;
        return minimum + i;
    }

    @Override
    protected OrderInfo mapSourceEntityToDestinationEntity(Order sourceEntity) {
        OrderInfo orderInfo = super.mapSourceEntityToDestinationEntity(sourceEntity);
        orderInfo.setUser(userService.findById(sourceEntity.getUserId()));
        return orderInfo;
    }
}
