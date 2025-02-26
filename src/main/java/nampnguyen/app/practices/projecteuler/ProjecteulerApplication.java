package nampnguyen.app.practices.projecteuler;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.event.EventListener;

import lombok.extern.slf4j.Slf4j;
import nampnguyen.app.practices.projecteuler.programlogic.problem18.Problem18;
import nampnguyen.app.practices.projecteuler.programlogic.problem19.Problem19;
import nampnguyen.app.practices.projecteuler.programlogic.problem20.Problem20;
import nampnguyen.app.practices.projecteuler.programlogic.problem21.Problem21;

@SpringBootApplication
@EnableAutoConfiguration
@Slf4j
@ComponentScan(basePackages = "nampnguyen.app.practices.projecteuler")
public class ProjecteulerApplication {

	@Autowired
    private Problem18 problem18;
    @Autowired
    private Problem19 problem19;
    @Autowired
    private Problem20 problem20;
    @Autowired
    private Problem21 problem21;

    public static void main(String[] args) {
        SpringApplication.run(ProjecteulerApplication.class, args);
    }

    @EventListener(ApplicationReadyEvent.class)
    public void onApplicationReady() throws CloneNotSupportedException {
        solveProblem21();
    }

    private void solveProblem19(){
        LocalDate fromDate = LocalDate.parse("1901-01-01", DateTimeFormatter.ISO_DATE);
        LocalDate toDate = LocalDate.parse("2000-12-31", DateTimeFormatter.ISO_DATE);
        problem19.setFromDate(fromDate);
        problem19.setToDate(toDate);
        problem19.solve();
    }

    private void solveProblem18(){
        log.info("Solve the problem 18");
        // Your logic here
		// problem18.solve();
    }

    private void solveProblem20(){
        problem20.solve();
    }

    private void solveProblem21(){
        problem21.solve();
    }
}
