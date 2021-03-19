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
public class CreateScheduleServiceBean {

    private static Logger log = LoggerFactory.getLogger(CreateScheduleServiceBean.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ScheduleRepository scheduleRepository;

    public ResponseEntity<ApiResponse> createSchedule(HttpServletRequest request){

        String guid = request.getParameter("guid");
        Long scheduleToSearch = Long.parseLong(request.getParameter("schedule"));
        String dataJson = request.getParameter("dataJson");
        String title = request.getParameter("title");
        String description = request.getParameter("description");

        /* check that user is registered */
        UserEntity userToSearch = userRepository.findByGuid(guid);
        if(userToSearch==null){
            log.warn("[Host: '{}', IP: '{}', Port: '{}'] Tried to login but guid: '{}' is not registered in DB.", request.getHeader("Host"),request.getRemoteAddr(),request.getServerPort(), guid);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(ApiResponse.resultKo(HttpStatus.UNAUTHORIZED.toString(), "Wrong guid.", "/createschedule", HttpStatus.UNAUTHORIZED.value()));
        }

        /* check that schedule exists */
        ScheduleEntity scheduleFound = new ScheduleEntity(userToSearch.getUserId(), dataJson, title, description);

        /* insert schedule */
        try{
            this.insert(scheduleFound);
        }catch(Exception e){
            log.error("[Host: '{}', IP: '{}', Port: '{}'] Tried to register but error: '{}' occurred while inserting data.", request.getHeader("Host"), request.getRemoteAddr(), request.getServerPort(), e.getMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error inserting data in DB.");
        }

        /* return ack */
        return ResponseEntity.ok(ApiResponse.resultOk("/createschedule", "Schedule insertion successfull."));

    }

    public void insert(ScheduleEntity scheduleToSave) {
        scheduleRepository.save(scheduleToSave);
    }
}
