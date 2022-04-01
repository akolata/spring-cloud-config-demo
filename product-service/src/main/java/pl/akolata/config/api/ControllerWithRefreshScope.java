package pl.akolata.config.api;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.akolata.config.dto.PropertiesDto;
import pl.akolata.config.props.DemoProperties;

@RestController
@RefreshScope
@RequiredArgsConstructor
@RequestMapping(value = "/with-refresh-scope")
class ControllerWithRefreshScope {
    private final Environment environment;
    private final DemoProperties demoProperties;

    @Value("${prop-location}")
    private String propLocation;

    @Value("${hello}")
    private String hello;

    @Value("${common-property:null}")
    private String commonProperty;

    @GetMapping(value = "/props")
    public ResponseEntity<PropertiesDto> getProps() {
        return ResponseEntity.ok(buildPropertiesDto());
    }

    private PropertiesDto buildPropertiesDto() {
        return PropertiesDto.builder()
                .configurationPropertiesWithRefreshScope(
                        PropertiesDto.ConfigurationProperties.builder()
                                .configurationPropertiesHello(demoProperties.getHello())
                                .build()
                )
                .injectedUsingValue(
                        PropertiesDto.Properties.builder()
                                .commonProperty(commonProperty)
                                .propLocation(propLocation)
                                .hello(hello)
                                .build()
                )
                .injectedUsingEnvironment(
                        PropertiesDto.Properties.builder()
                                .commonProperty(environment.getProperty("common-property"))
                                .propLocation(environment.getProperty("prop-location"))
                                .hello(environment.getProperty("hello"))
                                .build()
                )
                .build();
    }

}
