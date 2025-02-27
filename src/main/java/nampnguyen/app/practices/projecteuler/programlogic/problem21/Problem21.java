package nampnguyen.app.practices.projecteuler.programlogic.problem21;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Component;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import nampnguyen.app.practices.projecteuler.common.model.Solution;
import nampnguyen.app.practices.projecteuler.configuration.configs.Problem21Config;
import nampnguyen.app.practices.projecteuler.entity.problem21.Problem21AmicablePairsEntity;
import nampnguyen.app.practices.projecteuler.entity.problem21.Problem21Entity;
import nampnguyen.app.practices.projecteuler.model.problem21.Problem21Data;
import nampnguyen.app.practices.projecteuler.repository.problem21.Problem21AmicablePairsRepository;
import nampnguyen.app.practices.projecteuler.repository.problem21.Problem21Repository;

@Slf4j
@Component
public class Problem21 extends Solution{

    @Autowired
    private Problem21Repository repository;
    @Autowired
    private Problem21AmicablePairsRepository problem21AmicablePairsRepository;
    @Autowired
    private Problem21Config config;

    private StringBuilder traceLog;
    private static String logDelimiter = "\n";

    @Override
    public void solve() {
        traceLog = new StringBuilder().append("initial...").append(logDelimiter);
        traceLog.append("Begin to solve problem 21...").append(logDelimiter);
        Problem21Data data = new Problem21Data();
        data.setTraceLog(traceLog.toString());
        data.setUpperBound(config.getUpperBound());
        data.setAmicableCalculated(this.calculateAmicable(data.getUpperBound()));
        traceLog.append(String.format("Upper bound: %s", data.getUpperBound()));
        log.debug("First Amicable pair: {}", new StringBuilder().append(data.getAmicableCalculated().get(0).getFirst().toString()).append(",").append(data.getAmicableCalculated().get(0).getSecond().toString()));
        Set<BigInteger> amicableNumbers = new HashSet<>();
        for (Pair<BigInteger, BigInteger> pair : data.getAmicableCalculated()) {
            amicableNumbers.add(pair.getFirst());
            amicableNumbers.add(pair.getSecond());
        }
        BigInteger result = amicableNumbers.stream().reduce(BigInteger.ZERO, BigInteger::add);
        traceLog.append("Result of sum of all amicable numbers under ").append(data.getUpperBound()).append(" is ").append(result).append(logDelimiter);
        log.info("Result of sum of all amicable numbers under {} is {}: ", data.getUpperBound(), amicableNumbers.stream().reduce(BigInteger.ZERO, BigInteger::add));
        log.info("Begin to persist data to database");
        this.persist(data);
        log.info("Finish to persist data to database");
    }

    @Transactional
    private void persist(Problem21Data data) {
        traceLog.append("Begin to persist data to database").append(logDelimiter);
        Problem21Entity entity = this.saveTheGeneralData(data);
        this.saveDetailsData(data, entity);
    }

    private List<Pair<BigInteger, BigInteger>> calculateAmicable(BigInteger upperBound) {
        traceLog.append("Begin to calculate amicable numbers").append(logDelimiter);
        List<Pair<BigInteger, BigInteger>> result = new ArrayList<>();
        for (BigInteger i = BigInteger.ONE; i.compareTo(upperBound) < 0; i = i.add(BigInteger.ONE)) {
            BigInteger sum = sumOfDivisors(i);
            if (sum.compareTo(i) > 0 && sum.compareTo(upperBound) < 0) {
                BigInteger sum2 = sumOfDivisors(sum);
                if (sum2.compareTo(i) == 0) {
                    traceLog.append("Found an amicable pair: ").append(i).append(",").append(sum).append(logDelimiter);
                    result.add(Pair.of(i, sum));
                }
            }
        }
        return result;
    }

    private BigInteger sumOfDivisors(BigInteger number) {
        traceLog.append("Begin to calculate sum of divisors of ").append(number).append(logDelimiter);
        BigInteger sum = BigInteger.ZERO;
        for (BigInteger i = BigInteger.ONE; i.compareTo(number) < 0; i = i.add(BigInteger.ONE)) {
            if (number.mod(i).compareTo(BigInteger.ZERO) == 0) {
                sum = sum.add(i);
            }
        }
        traceLog.append("Sum of divisors of ").append(number).append(" is ").append(sum).append(logDelimiter);
        return sum;
    } 

    private Problem21Entity saveTheGeneralData(Problem21Data data) {
        traceLog.append("Begin to save the general data").append(logDelimiter);
        Problem21Entity entity = data.mapToProblem21Entity(null);
        return repository.save(entity);
    }

    private void saveDetailsData(Problem21Data data, Problem21Entity id) {
        traceLog.append("Begin to save the details data").append(logDelimiter);
        List<Problem21AmicablePairsEntity> entities = data.mapToAmicablePairsEntityList(id);
        problem21AmicablePairsRepository.saveAll(entities);
    }
}
