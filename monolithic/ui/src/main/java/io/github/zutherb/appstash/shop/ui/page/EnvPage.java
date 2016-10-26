package io.github.zutherb.appstash.shop.ui.page;

import com.google.common.base.Function;
import com.google.common.collect.Collections2;
import org.apache.commons.lang.NotImplementedException;
import org.wicketstuff.annotation.mount.MountPath;

import java.util.Collection;
import java.util.Map;

@MountPath("/env")
public class EnvPage extends AbstractPlainTextPage {

    private static final String MANIFEST_MF_PATH = "/META-INF/MANIFEST.MF";

    public EnvPage() {
    }

    @Override
    protected String responseText() {
        StringBuilder responseBuilder = new StringBuilder();

        responseBuilder.append("Environment-Variables:\n");
        append(responseBuilder, System.getenv().entrySet());

        responseBuilder.append("System-Properties:\n");
        append(responseBuilder, properties());

        return responseBuilder.toString();
    }

    private Collection<Map.Entry<String, String>> properties() {
        return Collections2.transform(System.getProperties().entrySet(), new Function<Map.Entry<Object, Object>, Map.Entry<String, String>>() {
            @Override
            public Map.Entry<String, String> apply(Map.Entry<Object, Object> input) {
                return new Map.Entry<String, String>() {
                    @Override
                    public String getKey() {
                        return input.getKey().toString();
                    }

                    @Override
                    public String getValue() {
                        return input.getValue().toString();
                    }

                    @Override
                    public String setValue(String value) {
                        throw new NotImplementedException("Setter should not be used");
                    }
                };
            }
        });
    }

    private void append(StringBuilder responseBuilder, Collection<Map.Entry<String, String>> entries) {
        for (Map.Entry<String, String> entry : entries) {
            responseBuilder.append(entry.getKey());
            responseBuilder.append(": ");
            responseBuilder.append(entry.getValue());
            responseBuilder.append("\n");
        }
    }


}