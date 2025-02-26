package nampnguyen.app.practices.projecteuler.repository.problem21;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import nampnguyen.app.practices.projecteuler.entity.problem21.Problem21Entity;

@Repository
public interface  Problem21Repository extends JpaRepository<Problem21Entity, String>{
    
}
