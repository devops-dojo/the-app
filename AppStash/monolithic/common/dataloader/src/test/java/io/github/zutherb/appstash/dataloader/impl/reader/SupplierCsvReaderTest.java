package io.github.zutherb.appstash.dataloader.impl.reader;

import io.github.zutherb.appstash.dataloader.reader.SupplierCsvReader;
import io.github.zutherb.appstash.dataloader.reader.SupplierCsvReader;
import org.junit.Test;

import static junit.framework.Assert.assertFalse;

/**
 * @author zutherb
 */
public class SupplierCsvReaderTest {
    @Test
    public void testParseCsv() throws Exception {
        SupplierCsvReader supplierCsvReader = new SupplierCsvReader();
        assertFalse(supplierCsvReader.parseCsv().isEmpty());
    }
}
