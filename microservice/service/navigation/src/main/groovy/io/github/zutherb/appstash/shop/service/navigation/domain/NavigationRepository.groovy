package io.github.zutherb.appstash.shop.service.navigation.domain

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.aggregation.Aggregation
import static org.springframework.data.mongodb.core.aggregation.Aggregation.group;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.project;
import org.springframework.stereotype.Component

@Component
class NavigationRepository {
    MongoTemplate mongoTemplate

    @Autowired
    def NavigationRepository(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate
    }

    def List<Navigation> all() {
        // db.product.aggregate({$group: {_id: "$type", count: { $sum: 1 }}} )
        mongoTemplate.aggregate(
                Aggregation.newAggregation(
                        group("type").count().as("sum"),
                        project("sum").and("type").previousOperation(),
                ),
                "product",
                Navigation.class).mappedResults
    }
}
