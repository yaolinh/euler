package nampnguyen.app.practices.projecteuler.entity.problem21;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name="problem21_amicable_pairs")
public class Problem21AmicablePairsEntity {
    @Id
    @Column(length = 32, nullable = false)
    private String id;

    @ManyToOne
    @JoinColumn(name = "problem21_id",referencedColumnName = "id", nullable = false)
    private Problem21Entity problem21; 

    @Column(name = "amicableA", nullable = false)
    private int amicableA;

    @Column(name = "amicableB", nullable = false)
    private int amicableB;

    @Column(name = "created_at", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime createdAt;

    @Column(name = "update_at")
    private LocalDateTime updatedAt;

    @Column(name = "create_by", length = 255)
    private String createdBy;

    @Column(name = "update_by", length = 255)
    private String updatedBy;
}
