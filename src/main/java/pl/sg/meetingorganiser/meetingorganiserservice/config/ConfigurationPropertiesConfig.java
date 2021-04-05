package pl.sg.meetingorganiser.meetingorganiserservice.config;

import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties
@ConfigurationPropertiesScan(basePackages = {"pl.sg.meetingorganiser.meetingorganiserservice.config.properties"})
class ConfigurationPropertiesConfig {
}
