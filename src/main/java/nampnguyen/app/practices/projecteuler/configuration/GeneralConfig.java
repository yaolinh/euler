package nampnguyen.app.practices.projecteuler.configuration;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import nampnguyen.app.practices.projecteuler.configuration.configs.Problem18Config;

@Configuration
@PropertySource("classpath:application.properties")
@EnableConfigurationProperties(Problem18Config.class)
public class GeneralConfig {
    
}
