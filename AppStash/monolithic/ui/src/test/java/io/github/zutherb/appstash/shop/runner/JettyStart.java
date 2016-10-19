package io.github.zutherb.appstash.shop.runner;

import ch.qos.logback.classic.util.ContextInitializer;
import net.logstash.logback.encoder.org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.Validate;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.webapp.WebAppContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author zutherb
 */
public class JettyStart {

    private static final Logger LOGGER = LoggerFactory.getLogger(JettyStart.class);

    static {
        if (StringUtils.isEmpty(System.getProperty("spring.profiles.active"))) {
            System.setProperty("spring.profiles.active", "test");
        }
    }

    public static void main(final String[] args) {
        if (args.length < 1) {
            System.out.println("JettyStart <httpport>");
            return;
        }

        Validate.notNull(args[0], "A Port is needed to start the server");

        Server server = new Server(Integer.valueOf(args[0]));
        WebAppContext context = new WebAppContext();
        context.setContextPath("/shop");
        context.setResourceBase("src/main/webapp/");
        context.setDescriptor("src/main/webapp/WEB-INF/web.xml");
        context.setParentLoaderPriority(true);
        server.setHandler(context);

        try {
            LOGGER.info("JETTY SERVER STARTING NOW ...");
            server.start();
            server.join();
        } catch (Exception e) {
            LOGGER.error("Jetty Server could not be started", e);
            System.exit(100);
        }
    }
}
