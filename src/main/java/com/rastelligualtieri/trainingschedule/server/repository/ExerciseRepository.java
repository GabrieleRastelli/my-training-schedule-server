package com.rastelligualtieri.trainingschedule.server.repository;

import com.rastelligualtieri.trainingschedule.server.model.ExerciseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExerciseRepository extends JpaRepository<ExerciseEntity, String> {

    List<ExerciseEntity> findAll();

}
