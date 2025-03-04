package nampnguyen.app.practices.projecteuler.configuration;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import nampnguyen.app.practices.projecteuler.configuration.configs.Problem18Config;

@Configuration
@PropertySource("classpath:application.properties")
@EnableConfigurationProperties(Problem18Config.class)
public class GeneralConfig {
    @Bean(name = "GeneralObjectMapper")
    public ObjectMapper objectMapper() {
        ObjectMapper objectMapper = new ObjectMapper(); 
        objectMapper.registerModule(new JavaTimeModule());  
        return objectMapper;    
    }
}
