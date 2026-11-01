package by.java.enterprise.micrometer.config;

import io.micrometer.core.instrument.Meter;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.config.MeterFilter;
import io.micrometer.core.instrument.config.MeterFilterReply;
import org.jspecify.annotations.NullMarked;
import org.springframework.boot.micrometer.metrics.autoconfigure.MeterRegistryCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

@Configuration
public class MetricsConfig {

    @Bean
    public MeterRegistryCustomizer<MeterRegistry> commonTags() {
        return registry -> registry.config()
                .commonTags("app", "user-service", "env", "demo");
    }

    /**
     * Применяется ПЕРВЫМ: блокируем технические метрики.
     */
    @Bean
    @Order(0)
    public MeterFilter denyDebugMetrics() {
        return new MeterFilter() {
            @Override
            @NullMarked
            public MeterFilterReply accept(Meter.Id id) {
                if (id.getName().startsWith("user.debug.")) {
                    return MeterFilterReply.DENY;
                }
                return MeterFilterReply.NEUTRAL;
            }
        };
    }

    /**
     * Применяется ВТОРЫМ: переименовываем user.* -> app.user.*.
     */
    @Bean
    @Order(1)
    public MeterFilter renameUserMetrics() {
        return new MeterFilter() {
            @Override
            @NullMarked
            public Meter.Id map(Meter.Id id) {
                if (id.getName().startsWith("user.")) {
                    return id.withName("app." + id.getName());
                }
                return id;
            }
        };
    }
}
