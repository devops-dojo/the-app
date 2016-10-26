package io.github.zutherb.appstash.dataloader.impl.reader;

import io.github.zutherb.appstash.dataloader.reader.UserCsvReader;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;

/**
 * @author zutherb
 */
public class UserCsvReaderTest {
    @Test
    public void testParseCsv() throws Exception {
        UserCsvReader userReader = new UserCsvReader();
        assertEquals(100, userReader.parseCsv().size());
    }
}