package io.github.zutherb.appstash.dataloader.impl.reader;

import io.github.zutherb.appstash.dataloader.reader.AddressCsvReader;
import io.github.zutherb.appstash.dataloader.reader.AddressCsvReader;
import org.junit.Test;

import static junit.framework.Assert.assertFalse;

/**
 * @author zuther
 */
public class AddressCsvReaderTest {
    @Test
    public void testParseCsv() throws Exception {
        AddressCsvReader addressCsvReader = new AddressCsvReader();
        assertFalse(addressCsvReader.parseCsv().isEmpty());
    }
}
