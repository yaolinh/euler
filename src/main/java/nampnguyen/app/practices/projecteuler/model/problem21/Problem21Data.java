package nampnguyen.app.practices.projecteuler.model.problem21;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.data.util.Pair;

import io.micrometer.common.util.StringUtils;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import nampnguyen.app.practices.projecteuler.entity.problem21.Problem21AmicablePairsEntity;
import nampnguyen.app.practices.projecteuler.entity.problem21.Problem21Entity;

@Data
@Slf4j
public class Problem21Data {
    private BigInteger upperBound;
    private List<Pair<BigInteger, BigInteger>> amicableCalculated;
    private String traceLog;

    public Problem21Entity mapToProblem21Entity(String id){
        LocalDateTime now = LocalDateTime.now();
        Problem21Entity entity = new Problem21Entity();
        entity.setUpperBound(upperBound.toString());
        entity.setAmicableCalculated(amicableCalculated.toString());
        entity.setTraceLog(traceLog);
        if(StringUtils.isBlank(id)){
            String uuid = UUID.randomUUID().toString(); 
            log.debug("Problem21 UUID: {}", uuid);
            entity.setId(uuid);
            entity.setCreatedAt(now);
        }else{
            entity.setId(id);
            entity.setUpdatedAt(now);
        }
        return entity;
    }
    

    public List<Problem21AmicablePairsEntity> mapToAmicablePairsEntityList(Problem21Entity problem21Id){
        List<Problem21AmicablePairsEntity> amicablePairs = new ArrayList<>();
        for (Pair<BigInteger, BigInteger> pair : amicableCalculated) {
            Problem21AmicablePairsEntity entity = new Problem21AmicablePairsEntity();
            LocalDateTime now = LocalDateTime.now();
            entity.setAmicableA(pair.getFirst().intValue());
            entity.setAmicableB(pair.getSecond().intValue());
            entity.setCreatedAt(now);
            entity.setId(UUID.randomUUID().toString());
            entity.setProblem21(problem21Id);
            amicablePairs.add(entity);
        }
        return amicablePairs;
    }
}
