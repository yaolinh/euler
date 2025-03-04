package nampnguyen.app.practices.projecteuler.common.integration.web.response.problem21;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import lombok.Builder;
import lombok.Data;
import nampnguyen.app.practices.projecteuler.common.integration.web.response.HttpResponse;
import nampnguyen.app.practices.projecteuler.model.Result;
import nampnguyen.app.practices.projecteuler.model.problem21.Problem21Data;
import nampnguyen.app.practices.projecteuler.model.problem21.Problem21Result;


@Data
@Builder
public class Problem21Response{
    private HttpResponse response;
    private Result<Problem21Result> result;
    private byte[] images;

    public Problem21Response(HttpResponse response, Result<Problem21Result> result, byte[] images) {
        this.response = response;
        this.result = result;
        this.images = images;
    }
}
