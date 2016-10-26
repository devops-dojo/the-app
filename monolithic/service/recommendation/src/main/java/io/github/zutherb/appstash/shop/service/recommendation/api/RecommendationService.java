package io.github.zutherb.appstash.shop.service.recommendation.api;

import io.github.zutherb.appstash.shop.repository.product.model.ProductType;
import io.github.zutherb.appstash.shop.service.product.model.ProductInfo;

import java.util.List;

/**
 * @author zutherb
 */
public interface RecommendationService {
    List<ProductInfo> getCollaborativeFilteringRecommendations(int limit);
    List<ProductInfo> getFrequentlyBoughtWithProductsRecommendations(int limit);
    List<ProductInfo> getRandomRecommendations(int limit);
    List<ProductInfo> getTopsellerRecommendations(int limit);
    List<ProductInfo> getTopsellerRecommendations(ProductType type, int limit);
    List<ProductInfo> getViewedByOthersRecommendations(String articleId, int limit);
}
