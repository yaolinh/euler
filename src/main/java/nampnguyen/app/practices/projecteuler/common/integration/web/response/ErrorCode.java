package nampnguyen.app.practices.projecteuler.common.integration.web.response;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ErrorCode<K, V> {
    private K code;
    private V message;

    public static final ErrorCode<HttpStatus, String> SUCCESS = new ErrorCode<>(HttpStatus.OK, "Success");
    public static final ErrorCode<HttpStatus, String> ERROR = new ErrorCode<>(HttpStatus.INTERNAL_SERVER_ERROR, "General Error");
}
