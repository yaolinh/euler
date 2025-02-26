package nampnguyen.app.practices.projecteuler.configuration.configs;

import java.math.BigInteger;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;
import lombok.Data;

@Primary
@Data
@Component
@ConfigurationProperties(prefix = "namnp.app.euler.config.problem21")
public class Problem21Config {
    private BigInteger upperBound;

    @PostConstruct
    public void init() {
        System.out.println("Loaded upperBound: " + upperBound);
    }
}
