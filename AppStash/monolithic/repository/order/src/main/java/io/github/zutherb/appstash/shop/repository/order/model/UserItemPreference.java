package io.github.zutherb.appstash.shop.repository.order.model;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.Date;

/**
 * User: christian.kroemer@comsysto.com
 * Date: 6/28/13
 * Time: 3:25 PM
 */
@Document(collection = UserItemPreference.COLLECTION_NAME)
public class UserItemPreference implements Serializable {
    public static final String COLLECTION_NAME = "recommendation.preference";

    @Indexed
    private ObjectId userId;
    @Indexed
    private ObjectId articleId;

    private Date lastUpdate;

    private int preference;

    private UserItemPreference() {
    }

    public ObjectId getUserId() {
        return userId;
    }

    public ObjectId getArticleId() {
        return articleId;
    }

    public Date getLastUpdate() {
        return lastUpdate;
    }

    public int getPreference() {
        return preference;
    }
}
