package io.github.zutherb.appstash.shop.repository.order.impl;

import io.github.zutherb.appstash.shop.repository.order.api.OrderIdProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;
import static org.springframework.data.mongodb.core.query.Update.update;

/**
 * @author zutherb
 */
@Service("orderIdProvider")
public class OrderIdProviderImpl implements OrderIdProvider {

    public static final String ORDER_SEQUENCE_ID = "ORDER_SEQUENCE_ID";
    private MongoOperations mongoOperations;

    @Autowired
    public OrderIdProviderImpl(@Qualifier("mongoTemplate") MongoOperations mongoOperations) {
        this.mongoOperations = mongoOperations;
    }

    @Override
    public Long nextVal() {
        Query query = query(where("id").is(ORDER_SEQUENCE_ID));
        mongoOperations.upsert(query, update("id", ORDER_SEQUENCE_ID).inc("nextVal", 1), OrderSequence.class);
        return mongoOperations.findOne(query, OrderSequence.class).getNextVal();
    }

    @PostConstruct
    public void init() {
        Query query = query(where("id").is(ORDER_SEQUENCE_ID));
        if (mongoOperations.findOne(query, OrderSequence.class) == null) {
            mongoOperations.save(new OrderSequence());
        }
    }

    @Document(collection = "sequence.order")
    public static class OrderSequence {
        @Indexed
        private String id = ORDER_SEQUENCE_ID;
        private Long nextVal = 10000L;

        public Long getNextVal() {
            return nextVal;
        }
    }
}
