package io.github.zutherb.appstash.shop.repository.order.impl;

import io.github.zutherb.appstash.common.repository.AbstractRepositoryImpl;
import io.github.zutherb.appstash.shop.repository.order.api.OrderRepository;
import io.github.zutherb.appstash.shop.repository.order.model.Order;
import io.github.zutherb.appstash.shop.repository.user.api.UserRepository;
import org.apache.commons.lang3.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

/**
 * @author zutherb
 */
@Repository("orderRepository")
public class OrderRepositoryImpl extends AbstractRepositoryImpl<Order> implements OrderRepository {

    private UserRepository userRepository;

    @Autowired
    public OrderRepositoryImpl(@Qualifier("mongoTemplate") MongoOperations mongoOperations,
                               @Qualifier("userRepository") UserRepository userRepository) {
        super(mongoOperations, Order.class);
        this.userRepository = userRepository;
    }

    @Override
    public List<Order> findAll(int limit, int offset, Sort sort) {
        Query query = new Query();
        applySortAndPagination(query, limit, offset, sort);
        return mongoOperations.find(query, Order.class);
    }

    @Override
    public List<Order> findInRange(Date fromDate, Date toDate, int limit, int offset, Sort sort) {
        Query query = query(where("orderDate").gte(fromDate).lte(toDate));
        applySortAndPagination(query, limit, offset, sort);
        return mongoOperations.find(query, Order.class);
    }

    @Override
    public Order findFirstOrder() {
        Query query = query(where("orderDate").exists(true));
        query.with(new Sort(Sort.Direction.ASC, "orderDate"));
        return mongoOperations.findOne(query, Order.class);
    }

    @Override
    public Order findLastOrder() {
        Query query = query(where("orderDate").exists(true));
        query.with(new Sort(Sort.Direction.DESC, "orderDate"));
        return mongoOperations.findOne(query, Order.class);
    }

    @Override
    public long countInRange(Date fromDate, Date toDate) {
        Query query = query(where("orderDate").gte(fromDate).lte(toDate));
        return mongoOperations.count(query, Order.class);
    }

    @Override
    public Order findByOrderId(long orderId) {
        Query query = query(where("orderId").is(orderId));
        return mongoOperations.findOne(query, Order.class);
    }

    private Query applySortAndPagination(Query query, int limit, int offset, Sort sort) {
        if (offset != 0) {
            query.skip(offset);
        }
        if (limit != 0) {
            query.limit(limit);
        }
        if (sort != null) {
            query.with(sort);
        }
        return query;
    }

    @Override
    public void save(Order entity) {
        Validate.notNull(userRepository.findById(entity.getUserId()));
        super.save(entity);
    }
}
