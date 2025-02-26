package nampnguyen.app.practices.projecteuler.entity.problem21;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "problem21")
public class Problem21Entity {

    @Id
    @Column(length = 32, nullable = false)
    private String id;

    @Column(name = "upper_bound", length = 255, nullable = false)
    private String upperBound;

    @Column(name = "amicable_calculated", length = 1024, nullable = false)
    private String amicableCalculated;

    @Lob
    @Column(name = "trace_log", nullable = false, columnDefinition = "TEXT")
    private String traceLog;

    @Column(name = "created_at", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime createdAt;

    @Column(name = "update_at")
    private LocalDateTime updatedAt;

    @Column(name = "create_by", length = 255)
    private String createdBy;

    @Column(name = "update_by", length = 255)
    private String updatedBy;
}
