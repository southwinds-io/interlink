package features;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = { "features.*, io.southwinds.*" })
public class TestConfig {
    @Bean
    public Util util() {
        return new Util();
    }
}