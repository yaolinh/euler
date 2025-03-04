package nampnguyen.app.practices.projecteuler.model.problem21;

import java.math.BigInteger;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Problem21Result{
    private BigInteger upperBound;
    private BigInteger sum;
    @JsonIgnore
    private String imgId;
}
