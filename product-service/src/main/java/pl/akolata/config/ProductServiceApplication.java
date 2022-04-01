package pl.akolata.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.context.scope.refresh.RefreshScopeRefreshedEvent;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.event.EventListener;
import pl.akolata.config.props.DemoProperties;

@Slf4j
@SpringBootApplication
@EnableConfigurationProperties(DemoProperties.class)
public class ProductServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProductServiceApplication.class, args);
    }

    @EventListener({
            ApplicationReadyEvent.class,
            RefreshScopeRefreshedEvent.class
    })
    public void onEvent(ApplicationEvent event) {
        log.info("Received [{}]", event.getClass());
    }

}
