package com.rastelligualtieri.trainingschedule.server.repository;

import java.util.List;

import com.rastelligualtieri.trainingschedule.server.model.ScheduleGenericInfo;
import com.rastelligualtieri.trainingschedule.server.model.UserEntity;
import com.rastelligualtieri.trainingschedule.server.model.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
public interface UserRepository extends JpaRepository<UserEntity, String> {

    List<UserEntity> findByName(String name);

    UserEntity findByEmail(String email);

    UserEntity findByGuid(String guid);

    @Query("select new com.rastelligualtieri.trainingschedule.server.model.UserInfo(u.userId, u.guid, u.name, u.email, u.profileImage) from UserEntity u where u.userId = ?1")
    UserInfo findUserInfo(Long userId);
}
