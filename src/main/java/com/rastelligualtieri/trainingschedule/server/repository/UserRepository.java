package com.rastelligualtieri.trainingschedule.server.repository;

import java.util.List;

import com.rastelligualtieri.trainingschedule.server.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
public interface UserRepository extends JpaRepository<UserEntity, String> {

    List<UserEntity> findByName(String name);

    UserEntity findByEmail(String email);

    UserEntity findByGuid(String guid);
}
