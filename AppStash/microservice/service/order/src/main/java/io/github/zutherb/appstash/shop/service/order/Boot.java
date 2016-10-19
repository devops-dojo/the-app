package io.github.zutherb.appstash.shop.service.order;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.util.JSON;
import org.eclipse.jetty.http.HttpStatus;

import java.net.UnknownHostException;

import static com.google.common.base.Strings.isNullOrEmpty;
import static spark.Spark.put;

/**
 * @author zutherb
 */
public class Boot {

    private final static MongoClient MONGO_CLIENT;
    private final static DBCollection DB_COLLECTION;

    static {
        try {
            MONGO_CLIENT = new MongoClient("localhost");
            DB shopDB = MONGO_CLIENT.getDB("shop");
            DB_COLLECTION = shopDB.getCollection("order");
        } catch (UnknownHostException e) {
            throw new RuntimeException("Order Service could not be started", e);
        }
    }

    public static void main(String[] args) {
        put("/submitOrder", "application/json", (request, response) -> {
            String body = request.body();
            if (!isNullOrEmpty(body)) {
                DBObject order = (DBObject) JSON.parse(body);
                DB_COLLECTION.save(order);

                response.status(HttpStatus.CREATED_201);
                return "";
            }
            response.status(HttpStatus.BAD_REQUEST_400);
            return "";
        });
    }
}
