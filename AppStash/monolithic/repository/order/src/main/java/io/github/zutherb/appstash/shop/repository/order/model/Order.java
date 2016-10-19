package io.github.zutherb.appstash.shop.repository.order.model;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author zutherb
 */
@Document(collection = Order.COLLECTION_NAME)
public class Order implements Serializable {
    public static final String COLLECTION_NAME = "order";

    @Id
    private ObjectId id;

    @Indexed(unique = true)
    private long orderId;

    private ObjectId userId;
    private List<OrderItem> orderItems;

    @Indexed
    private Date orderDate = new Date();

    private DeliveryAddress deliveryAddress;

    private Supplier supplier;

    private String sessionId;


    public Order(long orderId, ObjectId userId, List<OrderItem> orderItems, DeliveryAddress deliveryAddress, String sessionId) {
        this.orderId = orderId;
        this.userId = userId;
        this.orderItems = orderItems;
        this.deliveryAddress = deliveryAddress;
        this.sessionId = sessionId;
    }

    public Order() {}

    public long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public ObjectId getUserId() {
        return userId;
    }

    public void setUserId(ObjectId userId) {
        this.userId = userId;
    }

    public List<OrderItem> getOrderItems() {
        if (orderItems == null) {
            orderItems = new ArrayList<>();
        }
        return orderItems;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public DeliveryAddress getDeliveryAddress() {
        return deliveryAddress;
    }

    public void setDeliveryAddress(DeliveryAddress deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }

    public Supplier getSupplier() {
        return supplier;
    }

    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public void setOrderId(long orderId) {
        this.orderId = orderId;
    }

    public BigDecimal getItemCount() {
        return new OrderItemAggregator() {
            @Override
            protected BigDecimal add(BigDecimal sum, OrderItem orderItem) {
                return sum.add(BigDecimal.ONE);
            }
        }.aggregate();
    }

    public BigDecimal getItemPriceSum() {
        return new OrderItemAggregator() {
            @Override
            protected BigDecimal add(BigDecimal sum, OrderItem orderItem) {
                return sum.add(BigDecimal.valueOf(orderItem.getProduct().getPrice()));
            }
        }.aggregate();
    }

    public BigDecimal getTotalSum() {
        return getItemPriceSum();
    }

    private abstract class OrderItemAggregator {
        public BigDecimal aggregate() {
            BigDecimal sum = BigDecimal.ZERO;
            for (OrderItem orderItem : getOrderItems()) {
                sum = add(sum, orderItem);
            }
            return sum;
        }

        protected abstract BigDecimal add(BigDecimal sum, OrderItem orderItem);
    }
}
