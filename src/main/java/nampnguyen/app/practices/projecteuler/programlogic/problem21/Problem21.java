package nampnguyen.app.practices.projecteuler.programlogic.problem21;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import nampnguyen.app.practices.projecteuler.common.model.Solution;
import nampnguyen.app.practices.projecteuler.configuration.configs.Problem21Config;
import nampnguyen.app.practices.projecteuler.entity.problem21.Problem21AmicablePairsEntity;
import nampnguyen.app.practices.projecteuler.entity.problem21.Problem21ChartEntity;
import nampnguyen.app.practices.projecteuler.entity.problem21.Problem21Entity;
import nampnguyen.app.practices.projecteuler.model.Result;
import nampnguyen.app.practices.projecteuler.model.problem21.Problem21Data;
import nampnguyen.app.practices.projecteuler.model.problem21.Problem21Result;
import nampnguyen.app.practices.projecteuler.repository.problem21.Problem21AmicablePairsRepository;
import nampnguyen.app.practices.projecteuler.repository.problem21.Problem21ChartRepository;
import nampnguyen.app.practices.projecteuler.repository.problem21.Problem21Repository;

@Slf4j
@Component
public class Problem21<T> extends Solution<T> {

    @Autowired
    private Problem21Repository repository;
    @Autowired
    private Problem21AmicablePairsRepository problem21AmicablePairsRepository;
    @Autowired
    private Problem21ChartRepository problem21ChartRepository;;
    @Autowired
    private Problem21Config config;
    @Autowired
    @Qualifier("GeneralObjectMapper")
    private ObjectMapper objectMapper;  

    private StringBuilder traceLog;
    private static String logDelimiter = "\n";

    @Override
    public Result solve() {
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
        String id = this.persist(data);
        this.generateChart(id);
        log.info("Finish to persist data to database");
        Result<Problem21Result> r = new Result<>();
        r.setResult(Problem21Result.builder().upperBound(data.getUpperBound()).sum(result).build());
        r.setTitle(MessageFormat.format(config.getResultMessage(), data.getUpperBound(), r.getResult().getSum()));
        r.getResult().setImgId(id);
        try {
            log.debug("Debug Problem21 Result: {}", objectMapper.writeValueAsString(r));
        } catch (JsonProcessingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return r;
    }

    public Result solve(BigInteger upperBound) {
        traceLog = new StringBuilder().append("initial...").append(logDelimiter);
        traceLog.append("Begin to solve problem 21...").append(logDelimiter);
        Problem21Data data = new Problem21Data();
        data.setTraceLog(traceLog.toString());
        data.setUpperBound(upperBound);
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
        String id = this.persist(data);
        this.generateChart(id);
        log.info("Finish to persist data to database");
        Result<Problem21Result> r = new Result<>();
        r.setResult(Problem21Result.builder().upperBound(data.getUpperBound()).sum(result).build());
        r.setTitle(MessageFormat.format(config.getResultMessage(), data.getUpperBound(), r.getResult().getSum()));
        r.getResult().setImgId(id);
        try {
            log.debug("Debug Problem21 Result: {}", objectMapper.writeValueAsString(r));
        } catch (JsonProcessingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return r;
    }

    
    @Transactional
    private String persist(Problem21Data data) {
        traceLog.append("Begin to persist data to database").append(logDelimiter);
        Problem21Entity entity = this.saveTheGeneralData(data);
        this.saveDetailsData(data, entity);
        return entity.getId();
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

    private void generateChart(String problem21Id) {
        log.info("Begin to generate chart");
        ProcessBuilder processBuilder = new ProcessBuilder();
        processBuilder.command("bash", "-c", "PYTHONPATH=/Users/nguyennam/dev/sources/my_project/projecteuler/src/main/python /Users/nguyennam/dev/python/virtualenv/macos/bin/python3 /Users/nguyennam/dev/sources/my_project/projecteuler/src/main/python/problem21/problem21.py " + problem21Id);
        try {
            Process process = processBuilder.start();
            int exitCode = process.waitFor();
            log.info("Exit code: {}", exitCode);
            if (exitCode != 0) {
                BufferedReader errorReader = new BufferedReader(new InputStreamReader(process.getErrorStream()));
                String line;
                while ((line = errorReader.readLine()) != null) {
                    log.error("[Python execute error]{}", line);  // Print to stderr (red text in terminal)
                }
            }
            BufferedReader logOutputReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
                while ((line = logOutputReader.readLine()) != null) {
                    log.info("[Python execute info]{}", line);  // Print to stderr (red text in terminal)
                }

        } catch (Exception e) {
            log.error("Error when generating chart: {}", e.getMessage());
        }
        log.info("End of generate chart");
    }

    public byte[] retrieveImage(String problem21Id){
        Problem21Entity parentEntity = repository.findById(problem21Id).get();
        Problem21ChartEntity chartEntity = problem21ChartRepository.findByProblem21(parentEntity);
        return chartEntity.getImage();
    }
}
