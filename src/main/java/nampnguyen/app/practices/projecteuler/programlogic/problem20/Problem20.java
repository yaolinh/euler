package nampnguyen.app.practices.projecteuler.programlogic.problem20;

import java.math.BigInteger;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;
import nampnguyen.app.practices.projecteuler.common.model.Solution;
import nampnguyen.app.practices.projecteuler.model.Result;


@Component
@Slf4j
public class Problem20 extends Solution{

    @Override
    public Result solve() {
        // TODO Auto-generated method stub
        String base = "100";
        int sum = 0;
        String factorialResult = "null";
        try {
            factorialResult = this.factorial(new BigInteger(base)).toString();
            sum = 0;
            char[] digits = factorialResult.toCharArray();
            for(int i = 0; i < factorialResult.length(); i++){
                log.debug("Calculate sum while adding the {} at step{}", digits[i], i);
                sum = sum + (digits[i] - '0');
            }
        } catch (NumberFormatException e) {
            log.error("Error while parse the number{}", ExceptionUtils.getStackTrace(e));
        }
        
        log.info("The sum digit of factorial of {} is {}, with factored result is {}", base, sum, factorialResult);  
        return new Result();
    }
    
    private BigInteger factorial(BigInteger base){
        log.debug("Factorial at step {}", base.toString(0));
        if(base.equals(new BigInteger("1"))){
            return base;
        }else{
            return this.factorial(base.subtract(new BigInteger("1"))).multiply(base);
        }
    }
}
