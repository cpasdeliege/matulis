package be.cpasdeliege.logs.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import be.cpasdeliege.logs.model.Log;

@Repository
public interface LogRepository extends JpaRepository<Log, Integer>{

}