package io.github.zutherb.appstash.dataloader.reader;


import io.github.zutherb.appstash.shop.repository.product.model.Product;
import org.springframework.stereotype.Component;

@Component("productCsvReader")
public class ProductCsvReader extends AbstractCsvReader<Product> {

    private String filePath = "io/github/zutherb/appstash/dataloader/product.csv";

    @Override
    protected String getClassPathFilePath() {
        return filePath;
    }

    @Override
    protected String[] getColumnMapping() {
        return new String[]{"articleId", "type", "name", "description", "price", "category"};
    }

    @Override
    protected Class<Product> getDestinationClass() {
        return Product.class;
    }

}
