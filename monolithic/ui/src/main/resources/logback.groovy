import ch.qos.logback.classic.encoder.PatternLayoutEncoder
import ch.qos.logback.core.ConsoleAppender
import net.logstash.logback.encoder.LogstashEncoder
import static ch.qos.logback.classic.Level.*

def defaultPattern = "%d{HH:mm:ss.SSS} [%thread] %-5level %logger{36} - %msg%n"

if (System.getProperty("spring.profiles.active") != "production") {
    appender("stdout", ConsoleAppender) {
        encoder(PatternLayoutEncoder) {
            pattern = defaultPattern
        }
    }

    root(INFO, ["stdout"])
} else {
    appender("stdout", ConsoleAppender) {
        encoder(LogstashEncoder) {
        }
    }

    appender("readable", FileAppender) {
        file = "/var/log/tomcat7/appstash-readable.log"
        encoder(PatternLayoutEncoder) {
            pattern = defaultPattern
        }
    }

    appender("logstash", FileAppender) {
        file = "/var/log/tomcat7/appstash.log"
        append = true
        encoder(PatternLayoutEncoder) {
            pattern = "%msg%n"
        }
    }

    root(INFO, ["stdout", "readable", "logstash"])
}


