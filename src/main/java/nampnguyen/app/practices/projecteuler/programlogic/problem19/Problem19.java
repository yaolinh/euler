package nampnguyen.app.practices.projecteuler.programlogic.problem19;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;

import org.springframework.stereotype.Component;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import nampnguyen.app.practices.projecteuler.common.model.Solution;
import nampnguyen.app.practices.projecteuler.model.Result;

@Component
@Slf4j
@Setter
public class Problem19 extends Solution {

    private LocalDate fromDate;
    private LocalDate toDate;

    private int countSunday(final LocalDate fromDate, final LocalDate toDate) {
        LocalDate givenDate = fromDate;
        int count = 0;
        while (givenDate.isBefore(toDate) || givenDate.equals(toDate)) {
            log.debug("Processing given date: {}", DateTimeFormatter.ISO_DATE.format(givenDate));
            if (isSundayFirstOfMonth(givenDate)) {
                count += 1;
            }
            givenDate = this.shiftToNextMonth(givenDate);
        }
        return count;
    }

    /**
     *
     * @param givenDate
     * @return
     */
    private boolean isSundayFirstOfMonth(LocalDate givenDate) {
        if (givenDate.getDayOfMonth() == 1 && givenDate.getDayOfWeek().equals(DayOfWeek.SUNDAY)) {
            return true;
        } else {
            return false;
        }
    }

    private LocalDate shiftToNextMonth(LocalDate givenDate) {
        String newDate = (givenDate.getMonth() == Month.DECEMBER ? (String.valueOf(givenDate.getYear() + 1)) : (String.valueOf(givenDate.getYear()))) + "-" + String.format("%02d", (givenDate.getMonth().getValue() % 12) + 1) + "-01";
        log.debug("Shifting to date: {}", newDate);
        return LocalDate.parse(newDate);
    }

    @Override
    public Result solve() {
        log.info("Begin solve problem 19...");
        log.info("There are {} sundays from {} to {}.", countSunday(this.fromDate, this.toDate), DateTimeFormatter.ISO_DATE.format(fromDate), DateTimeFormatter.ISO_DATE.format(toDate));
        log.info("Finish solve problem 19...");
        return new Result<>();
    }

}
