package nampnguyen.app.practices.projecteuler.configuration.configs;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;
import lombok.Data;

@Primary
@Data
@Component
@ConfigurationProperties(prefix = "namnp.app.euler.config.problem18")
public class Problem18Config {
    private String dataPath;

    @PostConstruct
    public void init() {
        System.out.println("Loaded dataPath: " + dataPath);
    }
}
