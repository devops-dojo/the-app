package io.github.zutherb.appstash.shop.repository.order.impl;

import io.github.zutherb.appstash.shop.repository.order.model.Order;
import io.github.zutherb.appstash.shop.repository.order.model.OrderItem;
import io.github.zutherb.appstash.shop.repository.order.model.UserItemPreference;
import com.mongodb.DBObject;
import io.github.zutherb.appstash.shop.repository.order.model.Order;
import io.github.zutherb.appstash.shop.repository.order.model.OrderItem;
import io.github.zutherb.appstash.shop.repository.order.model.UserItemPreference;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * User: christian.kroemer@comsysto.com
 * Date: 6/28/13
 * Time: 3:27 PM
 */
@Component
public class UserItemPreferenceUpdateListener extends AbstractMongoEventListener<Order> {

    @Autowired
    private MongoOperations mongoOperations;

    @Override
    public void onAfterSave(Order order, DBObject dbo) {
        ObjectId userId = order.getUserId();
        for (OrderItem item : order.getOrderItems()) {
            String articleId = item.getProduct().getArticleId();

            Criteria criteria = Criteria.where("userId").is(userId)
                    .and("articleId").is(articleId);
            Update update = Update.update("created_at", new Date()).inc("preference", 1);

            mongoOperations.upsert(Query.query(criteria), update, UserItemPreference.class);
        }
    }
}
