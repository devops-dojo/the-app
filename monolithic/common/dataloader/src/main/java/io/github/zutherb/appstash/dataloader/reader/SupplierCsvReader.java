package io.github.zutherb.appstash.dataloader.reader;

import io.github.zutherb.appstash.shop.repository.order.model.Supplier;
import org.springframework.stereotype.Component;

/**
 * @author zutherb
 */
@Component("supplierCsvReader")
public class SupplierCsvReader extends AbstractCsvReader<Supplier> {

    private String filePath = "io/github/zutherb/appstash/dataloader/supplier.csv";

    @Override
    protected String getClassPathFilePath() {
        return filePath;
    }

    @Override
    protected String[] getColumnMapping() {
        return new String[]{"name"};
    }

    @Override
    protected Class<Supplier> getDestinationClass() {
        return Supplier.class;
    }

    /**
     * Setter for property override configure
     * @param filePath ilePath of the csv file that should be loaded
     */
    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }
}
