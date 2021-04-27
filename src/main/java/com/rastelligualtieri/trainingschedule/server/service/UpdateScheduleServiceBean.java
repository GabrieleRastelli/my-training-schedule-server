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
public class UpdateScheduleServiceBean {

    private static Logger log = LoggerFactory.getLogger(UpdateScheduleServiceBean.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ScheduleRepository scheduleRepository;

    public ResponseEntity<ApiResponse> updateSchedule(HttpServletRequest request){

        String guid = request.getParameter("guid");
        Long scheduleIdToSearch = Long.parseLong(request.getParameter("schedule"));
        String dataJson = request.getParameter("dataJson");
        String title = request.getParameter("title");
        String description = request.getParameter("description");

        /* check that user is registered */
        UserEntity userToSearch = userRepository.findByGuid(guid);
        if(userToSearch==null){
            log.warn("[Host: '{}', IP: '{}', Port: '{}'] Tried to update schedule but guid: '{}' is not registered in DB.", request.getHeader("Host"),request.getRemoteAddr(),request.getServerPort(), guid);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(ApiResponse.resultKo(HttpStatus.UNAUTHORIZED.toString(), "Wrong guid.", "/updateschedule", HttpStatus.UNAUTHORIZED.value()));
        }

        /* check that schedule exists */
        ScheduleEntity scheduleFound = scheduleRepository.findByScheduleId(scheduleIdToSearch);

        if(scheduleFound == null){
            log.error("[Host: '{}', IP: '{}', Port: '{}', GUID: '{}'] Tried to update schedule: '{}' which is not in DB.", request.getHeader("Host"),request.getRemoteAddr(),request.getServerPort(), guid, scheduleIdToSearch);
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ApiResponse.resultKo(HttpStatus.NOT_FOUND.toString(), "Wrong schedule id.", "/updateschedule", HttpStatus.NOT_FOUND.value()));
        }
        else{
            /* update schedule */
            ScheduleStatistic statistic= ScheduleUtils.calculateStatistics(dataJson);
            scheduleFound.setCategoria1(statistic.getCategoria1());
            scheduleFound.setCategoria2(statistic.getCategoria2());
            scheduleFound.setEquipment(statistic.getEquipmentNedeed());
            scheduleFound.setUserId(userToSearch.getUserId());
            scheduleFound.setTitle(title);
            scheduleFound.setDescription(description);
            scheduleFound.setDataJson(dataJson);
        }

        /* insert schedule */
        try{
            this.insert(scheduleFound);
        }catch(Exception e){
            log.error("[Host: '{}', IP: '{}', Port: '{}', GUID: '{}'] Tried to update schedule but error: '{}' occurred while inserting data.", request.getHeader("Host"), request.getRemoteAddr(), request.getServerPort(), guid, e.getMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error inserting data in DB.");
        }

        /* return ack */
        log.info("[Host: '{}', IP: '{}', Port: '{}', GUID: '{}'] UpdateScheduleServiceBean ok.", request.getHeader("Host"),request.getRemoteAddr(),request.getServerPort(), guid);
        return ResponseEntity.ok(ApiResponse.resultOk("/updateschedule", "Schedule updated sucessfully.","{}"));

    }

    public void insert(ScheduleEntity scheduleToSave) {
        scheduleRepository.save(scheduleToSave);
    }
}
