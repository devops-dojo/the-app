package io.github.zutherb.appstash.shop.repository.product.model;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.mongodb.core.query.Query;

import static org.springframework.data.mongodb.core.query.Criteria.where;

/**
 * User: lleovac
 * Date: 1/30/13
 */
public class ProductQueryUtils {
    public static Query applyCriterias(Query query, ProductQuery productQuery) {
        if (!StringUtils.isEmpty(productQuery.getId())) {
            query.addCriteria(where("_id").is(productQuery.getId()));
        }
        if (!StringUtils.isEmpty(productQuery.getArticleId())) {
            query.addCriteria(where("articleId").is(productQuery.getArticleId()));
        }
        if (!StringUtils.isEmpty(productQuery.getName())) {
            query.addCriteria(where("name").is(productQuery.getName()));
        }
        if (!StringUtils.isEmpty(productQuery.getUrlname())) {
            query.addCriteria(where("urlname").is(productQuery.getUrlname()));
        }
        if (productQuery.getType()!=null) {
            query.addCriteria(where("type").is(productQuery.getType()));
        }
        return query;
    }


}
