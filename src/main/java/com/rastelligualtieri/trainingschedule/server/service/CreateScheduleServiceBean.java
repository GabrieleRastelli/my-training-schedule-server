package com.rastelligualtieri.trainingschedule.server.service;


import com.rastelligualtieri.trainingschedule.server.apiresponse.ApiResponse;
import com.rastelligualtieri.trainingschedule.server.model.*;
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
public class CreateScheduleServiceBean {

    private static Logger log = LoggerFactory.getLogger(CreateScheduleServiceBean.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ScheduleRepository scheduleRepository;

    public ResponseEntity<ApiResponse> createSchedule(HttpServletRequest request){

        String guid = request.getParameter("guid");
        String dataJson = request.getParameter("dataJson");
        String title = request.getParameter("title");
        String description = request.getParameter("description");

        /* check that user is registered */
        UserEntity userToSearch = userRepository.findByGuid(guid);
        if(userToSearch==null){
            log.warn("[Host: '{}', IP: '{}', Port: '{}'] Tried to create schedule but guid: '{}' is not registered in DB.", request.getHeader("Host"),request.getRemoteAddr(),request.getServerPort(), guid);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(ApiResponse.resultKo(HttpStatus.UNAUTHORIZED.toString(), "Wrong guid.", "/createschedule", HttpStatus.UNAUTHORIZED.value()));
        }

        ScheduleStatistic statistic= ScheduleUtils.calculateStatistics(dataJson);

        /* check that schedule exists */
        ScheduleEntity scheduleFound = new ScheduleEntity(userToSearch.getUserId(), dataJson, title, description, statistic.getCategoria1(), statistic.getCategoria2(), statistic.getEquipmentNedeed());

        /* insert schedule */
        try{
            this.insert(scheduleFound);
        }catch(Exception e){
            log.error("[Host: '{}', IP: '{}', Port: '{}', GUID: '{}'] Tried to create schedule but error: '{}' occurred while inserting data.", request.getHeader("Host"), request.getRemoteAddr(), request.getServerPort(), guid, e.getMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error inserting data in DB.");
        }

        /* return ack */
        log.info("[Host: '{}', IP: '{}', Port: '{}', GUID: '{}'] CreateScheduleServiceBean ok.", request.getHeader("Host"),request.getRemoteAddr(),request.getServerPort(), guid);
        return ResponseEntity.ok(ApiResponse.resultOk("/createschedule", "Schedule created sucessfully.","{}"));

    }

    public void insert(ScheduleEntity scheduleToSave) {
        scheduleRepository.save(scheduleToSave);
    }
}
