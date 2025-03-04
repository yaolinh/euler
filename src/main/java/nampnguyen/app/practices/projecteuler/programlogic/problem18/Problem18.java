package nampnguyen.app.practices.projecteuler.programlogic.problem18;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import nampnguyen.app.practices.projecteuler.common.model.Solution;
import nampnguyen.app.practices.projecteuler.configuration.configs.Problem18Config;
import nampnguyen.app.practices.projecteuler.model.Result;
import nampnguyen.app.practices.projecteuler.model.problem18.Node;

@Getter
@Setter
@Slf4j
@Component
public class Problem18 extends Solution{

    //Spring beans
    @Autowired
    private Problem18Config config;

    //Non spring beans
    private Node tree;

    private void initialize() {
        List<String> rawLines = new ArrayList<>();
        log.info("Data path: " + config.getDataPath());
        try (InputStream inputStream = Problem18.class.getResourceAsStream(config.getDataPath());) {
            log.info("Load data from path: ", config.getDataPath());

            if (inputStream != null) {
                rawLines = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))
                        .lines()
                        .collect(Collectors.toList());

                // Do something with the lines, for example:
                rawLines.forEach(e->log.debug("raw String: {}", e));
            } else {
                log.warn("The file is not found.");
            }
            // Path dataFilePath = Paths.get(config.getDataPath());
            // rawLines = Files.readAllLines(dataFilePath);
            List<List<Node>> multipleLinesData = new ArrayList<>();
            rawLines.forEach(e -> {
                String[] elementsOfSingleLine = e.split(" ");
                debugArray(elementsOfSingleLine,"Line string after split");
                List<Node> line = new ArrayList<>();
                for (String i : elementsOfSingleLine) {
                    line.add(new Node(Integer.parseInt(i), null));
                }
                //debug
                List<String> dataDebug = new ArrayList<>();
                for(Node debugN: line){
                    dataDebug.add(String.valueOf(debugN.getValue()));
                }
                debugArray(dataDebug.toArray(), "Line array after convert to Integer");
                //end debug
                multipleLinesData.add(line);

            });
            for (int i = 0; i < multipleLinesData.size() - 1; i++) {
                for (int j = 0; j < multipleLinesData.get(i).size(); j++) {
                    List<Node> next = new ArrayList<>();
                    next.add(multipleLinesData.get(i + 1).get(j));
                    next.add(multipleLinesData.get(i + 1).get(j + 1));
                    log.debug("Set the next values: current: {}", multipleLinesData.get(i).get(j).getValue());
                    log.debug("Set the next values: next1: {}", multipleLinesData.get(i + 1).get(j).getValue());
                    log.debug("Set the next values: next2: {}", multipleLinesData.get(i + 1).get(j + 1).getValue());
                    multipleLinesData.get(i).get(j).setNext(next);
                }
            }
            this.tree = multipleLinesData.get(0).get(0);
            log.info("Finished initialized data for problem 18...");
        } catch (Exception ex) {
            log.warn("Error while open input files, exception: {}, test:{}", ExceptionUtils.getStackTrace(ex), "I used this.");
        } finally {

        }
    }

    @Override
    public Result solve() {
        this.initialize();
        boolean running = true;
        List<Node> maxPathSum = new ArrayList<>();
        List<Node> toProcess = new ArrayList<>();
        LinkedList<List<Node>> lines = new LinkedList<>();
        toProcess.add(tree);
        maxPathSum.add(tree);
        lines.add(new ArrayList<>(maxPathSum));
        int level = 0;
        EDGE:
        while (running) {
            log.debug("Begin to process new layer.");
            this.debugArray(lines.toArray(), "List of lines before process: ");
            log.debug("Begin process layer: {}th", level);
            int col = 0;
            /**
             * 16/02/2025
             * Studying a way to calculate correct position of columns, because
             * whenever a new line is add, the pointer need to be pointed to the
             * next two elements.
             */
            for(int i = 0; i < (int)Math.pow(2, level+1); i+=2){
                log.debug("Begin process colum number: {}", col);
                Node cursor = lines.get(i).get(level);
                assert null != cursor;
                List<Node> children = cursor.getNext();
                if(CollectionUtils.isNotEmpty(children)){
                    log.debug("Size of current children: {}", children.size());
                    //extend line:
                    lines.get(i).add(children.get(0));
                    this.debugArray(lines.toArray(), "List of lines after add first line in a loop: ");
                    //new line:
                    List<Node> newLine = new ArrayList<>(lines.get(i));
                    newLine.remove(level+1);
                    // List<Node> newLine = new ArrayList<>(lines.get(i));
                    log.debug("new line: {}", newLine);
                    newLine.add(children.get(1));
                    lines.add(col+1, newLine);
                }else{
                    log.debug("This node has no children.");
                    break EDGE;
                }
                
                col+=2;
            }
            this.debugArray(lines.toArray(), "List of lines after loop: ");
            level += 1;
            this.debugArray(lines.toArray(), "List of lines after process: ");
            log.debug("End to process new layer.");
        }
        log.debug("There are {} lines in the pyramid.", lines.size());
        for (List<Node> list : lines) {
            StringBuilder sb = new StringBuilder();
            for(Node n: list){
                sb.append(n.getValue()).append(";");
            }
            log.debug("debug: line: {}", sb.substring(0, sb.length()-1));
        }
        //finish populated lines.
        int max = 0;
        for(List<Node> line: lines){
            int sum = line.stream().mapToInt(e->Integer.valueOf(e.getValue())).sum();
            this.debugArray(line.toArray(), "Find the sum of: ");
            log.debug("sum: {}", sum);
            if(sum > max){
                maxPathSum = new ArrayList<>(line);
                max = sum;
            }
        }
        this.displayResult(true, maxPathSum, max);
        // log.info("Max path sum: {}, with ", maxPathSum.toString());
        return new Result();
    }
    
    private void displayResult(boolean toLog, List<Node> maxPathSum, int sum){
        StringBuilder sb = new StringBuilder();
        for(Node n: maxPathSum){
            sb.append(n.toString()).append(";");
        }
        if(toLog){
            log.info("The max sum path is {}, with total sum is: {}", sb.substring(0, sb.length() - 1).toString(), String.valueOf(sum));
        }else{
            System.out.println("Max path result is : " + sum);
        }
    }

    private void debugArray(Object[] data, String nameDebug){
        StringBuilder sb = new StringBuilder();
        for(int i =0; i < data.length;i++){
            sb.append(data[i]).append(";");
        }
        log.debug("debug: data {}: {}", nameDebug, sb.substring(0, sb.length()-1));
    }
}
