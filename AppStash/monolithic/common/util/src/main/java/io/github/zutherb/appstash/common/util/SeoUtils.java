package io.github.zutherb.appstash.common.util;

/**
 * @author zutherb
 */
public class SeoUtils {

    private SeoUtils() { /* NOOP */ }

    public static String urlFriendly(String urlUnfriendly) {
        String urlFriendly = urlUnfriendly.toLowerCase();
        urlFriendly = urlFriendly.replaceAll("ä", "ae");
        urlFriendly = urlFriendly.replaceAll("ö", "oe");
        urlFriendly = urlFriendly.replaceAll("ü", "ue");
        urlFriendly = urlFriendly.replaceAll("ß", "ss");
        urlFriendly = urlFriendly.replaceAll("ß", "ss");
        urlFriendly = urlFriendly.replaceAll("\\.", "-");
        urlFriendly = urlFriendly.replaceAll(",", "-");
        urlFriendly = urlFriendly.replaceAll("\\s", "-");
        urlFriendly = urlFriendly.replaceAll("[^a-z0-9\\-]+", "");
        urlFriendly = urlFriendly.replaceAll("\\-+", "-");
        if (urlFriendly.endsWith("-")) {
            return urlFriendly.substring(0, urlFriendly.length() - 1);
        }
        return urlFriendly;
    }

}

