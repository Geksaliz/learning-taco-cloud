package tacos.configuration.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("taco.order")
public record OrderProperties(
        int pageSize
) {
}
