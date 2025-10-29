package tacos.configuration;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import tacos.configuration.properties.OrderProperties;

@Configuration
@EnableConfigurationProperties(OrderProperties.class)
public class PropertiesConfiguration {
}
