package nampnguyen.app.practices.projecteuler.model;

import lombok.Builder;
import lombok.Data;

@Data
public class Result<T> {
    private String title;
    private T result;
}
