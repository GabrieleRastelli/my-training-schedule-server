package com.rastelligualtieri.trainingschedule.server.service;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.rastelligualtieri.trainingschedule.server.apiresponse.ApiResponse;
import com.rastelligualtieri.trainingschedule.server.model.*;
import com.rastelligualtieri.trainingschedule.server.repository.UserRepository;
import com.rastelligualtieri.trainingschedule.server.repository.ExerciseRepository;
import com.rastelligualtieri.trainingschedule.server.utils.JsonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Service
public class ExerciseServiceBean {

    private static Logger log = LoggerFactory.getLogger(ExerciseServiceBean.class);

    @Autowired
    private ExerciseRepository ExerciseRepository;

    @Autowired
    private UserRepository userRepository;

    public ResponseEntity<ApiResponse> getExercises(HttpServletRequest request){

        String guid = request.getParameter("guid");

        /* check that user is registered */
        UserEntity userToSearch = userRepository.findByGuid(guid);
        if(userToSearch==null){
            log.warn("[Host: '{}', IP: '{}', Port: '{}'] Tried to get all exercises but guid: '{}' is not registered in DB.", request.getHeader("Host"),request.getRemoteAddr(),request.getServerPort(), guid);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(ApiResponse.resultKo(HttpStatus.UNAUTHORIZED.toString(), "Wrong guid.", "/exercise", HttpStatus.UNAUTHORIZED.value()));
        }

        List<ExerciseEntity> allExercises = ExerciseRepository.findAll();

        try {
            String jsonExercises = JsonUtils.objectToJson(allExercises);
            log.info("[Host: '{}', IP: '{}', Port: '{}', GUID: '{}'] Exercises succesfully sent.", request.getHeader("Host"),request.getRemoteAddr(),request.getServerPort(), guid);
            return ResponseEntity.ok(ApiResponse.resultOk("/exercise", "Extraction succesful.",jsonExercises));
        } catch (JsonProcessingException e) {
            log.error("[Host: '{}', IP: '{}', Port: '{}', GUID: '{}'] Error JSON parsing allExercises: '{}'.", request.getHeader("Host"),request.getRemoteAddr(),request.getServerPort(), guid, e.getMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error json parsing.");
        }

    }
}
