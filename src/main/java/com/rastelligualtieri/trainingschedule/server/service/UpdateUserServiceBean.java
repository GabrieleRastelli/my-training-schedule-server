package com.rastelligualtieri.trainingschedule.server.service;


import com.rastelligualtieri.trainingschedule.server.apiresponse.ApiResponse;
import com.rastelligualtieri.trainingschedule.server.model.ScheduleEntity;
import com.rastelligualtieri.trainingschedule.server.model.UserEntity;
import com.rastelligualtieri.trainingschedule.server.repository.ScheduleRepository;
import com.rastelligualtieri.trainingschedule.server.repository.UserRepository;
import com.rastelligualtieri.trainingschedule.server.utils.ScheduleStatistic;
import com.rastelligualtieri.trainingschedule.server.utils.ScheduleUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletRequest;

@Service
public class UpdateUserServiceBean {

    private static Logger log = LoggerFactory.getLogger(UpdateUserServiceBean.class);

    @Autowired
    private UserRepository userRepository;


    public ResponseEntity<ApiResponse> updateUser(HttpServletRequest request){

        String guid = request.getParameter("guid");
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String nickname = request.getParameter("nickname");
        String image = request.getParameter("image");

        /* check that user is registered */
        UserEntity userToSearch = userRepository.findByGuid(guid);
        if(userToSearch==null){
            log.warn("[Host: '{}', IP: '{}', Port: '{}'] Tried to update user info but guid: '{}' is not registered in DB.", request.getHeader("Host"),request.getRemoteAddr(),request.getServerPort(), guid);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(ApiResponse.resultKo(HttpStatus.UNAUTHORIZED.toString(), "Wrong guid.", "/updateuser", HttpStatus.UNAUTHORIZED.value()));
        }

        userToSearch.setName(name);
        userToSearch.setEmail(email);
        userToSearch.setNickname(nickname);
        userToSearch.setProfileImage(image);

        /* insert user */
        try{
            this.insert(userToSearch);
        }catch(Exception e){
            log.error("[Host: '{}', IP: '{}', Port: '{}', GUID: '{}'] Tried to update user info but error: '{}' occurred while inserting data.", request.getHeader("Host"), request.getRemoteAddr(), request.getServerPort(), guid, e.getMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error inserting data in DB.");
        }

        /* return ack */
        log.info("[Host: '{}', IP: '{}', Port: '{}', GUID: '{}'] UpdateUserServiceBean ok.", request.getHeader("Host"),request.getRemoteAddr(),request.getServerPort(), guid);
        return ResponseEntity.ok(ApiResponse.resultOk("/updateuser", "User updated sucessfully.","{}"));

    }

    public void insert(UserEntity userToSave) {
        userRepository.save(userToSave);
    }
}
