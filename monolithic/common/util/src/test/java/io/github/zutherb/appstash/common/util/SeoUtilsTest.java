package io.github.zutherb.appstash.common.util;

import org.junit.Test;

import static junit.framework.Assert.assertEquals;

public class SeoUtilsTest {
    @Test
    public void testUrlFriendly() throws Exception {
        String sentence = "Männer fahren gerne mit dem Floß und springen über Möhren, daß ist schön. [  ] 12.01.2012 ?*#.";
        String urlfriendly = SeoUtils.urlFriendly(sentence);
        assertEquals("maenner-fahren-gerne-mit-dem-floss-und-springen-ueber-moehren-dass-ist-schoen-12-01-2012", urlfriendly);
    }
}
