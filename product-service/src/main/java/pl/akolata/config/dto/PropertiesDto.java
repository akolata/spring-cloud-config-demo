package pl.akolata.config.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PropertiesDto {
    private Properties injectedUsingValue;
    private Properties injectedUsingEnvironment;
    private ConfigurationProperties configurationPropertiesWithRefreshScope;
    private ConfigurationProperties configurationPropertiesWithoutRefreshScope;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Properties {
        private String commonProperty;
        private String propLocation;
        private String hello;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ConfigurationProperties {
        private String configurationPropertiesHello;
    }
}
