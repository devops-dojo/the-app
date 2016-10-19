package io.github.zutherb.appstash.shop.repository.order.api;

import io.github.zutherb.appstash.common.repository.AbstractRepository;
import io.github.zutherb.appstash.shop.repository.order.model.Order;
import io.github.zutherb.appstash.common.repository.AbstractRepository;
import io.github.zutherb.appstash.shop.repository.order.model.Order;
import org.springframework.data.domain.Sort;

import java.util.Date;
import java.util.List;

/**
 * @author zutherb
 */
public interface OrderRepository extends AbstractRepository<Order> {
    List<Order> findAll(int limit, int offset, Sort sort);
    List<Order> findInRange(Date fromDate, Date toDate, int limit, int offset, Sort sort);
    Order findFirstOrder();
    Order findLastOrder();
    long countInRange(Date fromDate, Date toDate);
    Order findByOrderId(long orderId);
}
