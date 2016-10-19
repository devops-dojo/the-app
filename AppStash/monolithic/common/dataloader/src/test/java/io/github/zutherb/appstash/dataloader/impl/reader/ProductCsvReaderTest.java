package io.github.zutherb.appstash.dataloader.impl.reader;


import io.github.zutherb.appstash.dataloader.reader.ProductCsvReader;
import io.github.zutherb.appstash.shop.repository.product.model.Product;
import io.github.zutherb.appstash.shop.repository.product.model.ProductType;
import io.github.zutherb.appstash.dataloader.reader.ProductCsvReader;
import io.github.zutherb.appstash.shop.repository.product.model.Product;
import io.github.zutherb.appstash.shop.repository.product.model.ProductType;
import org.apache.commons.collections.ListUtils;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static io.github.zutherb.appstash.shop.repository.product.model.ProductType.*;
import static junit.framework.Assert.assertEquals;

public class ProductCsvReaderTest {

    private List<Product> parsedProducts;


    @Before
    @SuppressWarnings( "unchecked" )
    public void parseCsv() throws IOException {
        parsedProducts = ListUtils.unmodifiableList( new ProductCsvReader().parseCsv( ));
    }

    @Test
    public void testAllProductsParsed() throws IOException {
        assertEquals( 20, parsedProducts.size( ));
    }

    @Test
    public void testParsedHandy() {
        checkCountAndArticleIds(ProductType.HANDY, 16);
    }

    @Test
    public void testParsedTablet() {
        checkCountAndArticleIds(ProductType.TABLET, 4);
    }

    private void checkCountAndArticleIds( ProductType type, int expectedProductCount) {
        List<Product> copyOfparsedProducts = new ArrayList<>(parsedProducts);
        assertEquals( expectedProductCount, copyOfparsedProducts.stream()
            .filter( product -> type.equals(product.getType()))
            .count()
        );
    }
}
