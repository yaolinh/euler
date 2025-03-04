package nampnguyen.app.practices.projecteuler.repository.problem21;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import nampnguyen.app.practices.projecteuler.entity.problem21.Problem21ChartEntity;
import nampnguyen.app.practices.projecteuler.entity.problem21.Problem21Entity;

@Repository
public interface Problem21ChartRepository extends JpaRepository<Problem21ChartEntity, String> {
    @Query("SELECT p FROM Problem21ChartEntity p WHERE p.problem21 = :problem21")
    public Problem21ChartEntity findByProblem21(@Param("problem21") Problem21Entity problem21);
}
