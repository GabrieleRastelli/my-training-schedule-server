package com.rastelligualtieri.trainingschedule.server.model;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
public interface UserRepository extends JpaRepository<UserEntity, String> {

    List<UserEntity> findByName(String name);

    UserEntity findByEmail(String email);
}
