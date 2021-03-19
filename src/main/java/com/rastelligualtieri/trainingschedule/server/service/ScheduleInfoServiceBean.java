package com.rastelligualtieri.trainingschedule.server.service;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.rastelligualtieri.trainingschedule.server.apiresponse.ApiResponse;
import com.rastelligualtieri.trainingschedule.server.model.*;
import org.apache.commons.codec.digest.DigestUtils;
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
public class ScheduleInfoServiceBean {

    private static Logger log = LoggerFactory.getLogger(ScheduleInfoServiceBean.class);

    @Autowired
    private ExerciseRepository ExerciseRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ScheduleRepository scheduleRepository;

    public ResponseEntity<ApiResponse> getScheduleInfo(HttpServletRequest request){

        String guid = request.getParameter("guid");
        Long scheduleIdToSearch = Long.parseLong(request.getParameter("schedule"));

        /* check that user is registered */
        UserEntity userToSearch = userRepository.findByGuid(guid);
        if(userToSearch==null){
            log.warn("[Host: '{}', IP: '{}', Port: '{}'] Tried to login but guid: '{}' is not registered in DB.", request.getHeader("Host"),request.getRemoteAddr(),request.getServerPort(), guid);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(ApiResponse.resultKo(HttpStatus.UNAUTHORIZED.toString(), "Wrong guid.", "/scheduleinfo", HttpStatus.UNAUTHORIZED.value()));
        }

        /* check that schedule exists */
        ScheduleEntity scheduleFound = scheduleRepository.findByScheduleId(scheduleIdToSearch);

        if(scheduleFound == null){
            log.warn("[Host: '{}', IP: '{}', Port: '{}'] Sent schedule id: '{}' that is not in DB.", request.getHeader("Host"),request.getRemoteAddr(),request.getServerPort(), scheduleIdToSearch);
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ApiResponse.resultKo(HttpStatus.NOT_FOUND.toString(), "Schedule id not found.", "/scheduleinfo", HttpStatus.UNAUTHORIZED.value()));
        }

        /* only returns datajson representing schedule */
        try {
            ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
            String jsonSchedules = ow.writeValueAsString(scheduleFound.getDataJson());
            return ResponseEntity.ok(ApiResponse.resultOk("/scheduleinfo", jsonSchedules));
        } catch (JsonProcessingException e) {
            log.error("[Host: '{}', IP: '{}', Port: '{}', GUID: '{}'] Error JSON parsing datajson: '{}'.", request.getHeader("Host"),request.getRemoteAddr(),request.getServerPort(), guid, e.getMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error json parsing.");
        }

    }
}
