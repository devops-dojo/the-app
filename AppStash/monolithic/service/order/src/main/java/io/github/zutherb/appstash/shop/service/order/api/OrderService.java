package io.github.zutherb.appstash.shop.service.order.api;

import io.github.zutherb.appstash.common.service.AbstractService;
import io.github.zutherb.appstash.shop.service.order.model.OrderInfo;
import io.github.zutherb.appstash.common.service.AbstractService;
import io.github.zutherb.appstash.shop.service.order.model.OrderInfo;
import org.springframework.data.domain.Sort;

import java.util.Date;
import java.util.List;

/**
 * @author zutherb
 */
public interface OrderService extends AbstractService<OrderInfo> {
    OrderInfo submitOrder(OrderInfo orderInfo, String sessionId);
    List<OrderInfo> findAll(int limit, int offset, Sort sort);
    List<OrderInfo> findInRange(Date fromDate, Date toDate, int limit, int offset, Sort sort);
    OrderInfo findFirstOrder();
    OrderInfo findLastOrder();
    long countInRange(Date fromDate, Date toDate);
}
