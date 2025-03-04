package nampnguyen.app.practices.projecteuler.entity.problem21;

import java.sql.Timestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "problem21_charts")
public class Problem21ChartEntity {
    @Id
    @Column(length = 36, nullable = false)
    private String id;

    @OneToOne
    @JoinColumn(name = "problem21_id", referencedColumnName = "id", nullable = false)
    private Problem21Entity problem21;

    @Column(name = "image_path", length = 1024)
    private String imagePath;

    @Lob
    @Column(name = "image", nullable = false)
    private byte[] image;

    @Column(name = "created_at", insertable = false, updatable = false)
    private Timestamp createdAt;

    @Column(name = "update_at")
    private Timestamp updatedAt;

    @Column(name = "create_by", length = 255)
    private String createdBy;

    @Column(name = "update_by", length = 255)
    private String updatedBy;
}
