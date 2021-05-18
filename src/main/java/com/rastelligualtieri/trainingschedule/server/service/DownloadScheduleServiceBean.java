package com.rastelligualtieri.trainingschedule.server.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.rastelligualtieri.trainingschedule.server.apiresponse.ApiResponse;
import com.rastelligualtieri.trainingschedule.server.model.ScheduleEntity;
import com.rastelligualtieri.trainingschedule.server.model.ScheduleGenericInfo;
import com.rastelligualtieri.trainingschedule.server.model.UserEntity;
import com.rastelligualtieri.trainingschedule.server.repository.ScheduleRepository;
import com.rastelligualtieri.trainingschedule.server.repository.UserRepository;
import com.rastelligualtieri.trainingschedule.server.utils.JsonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.util.List;

@Service
public class DownloadScheduleServiceBean {

    private static Logger log = LoggerFactory.getLogger(DownloadScheduleServiceBean.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ScheduleRepository scheduleRepository;

    @Transactional
    public ResponseEntity<ApiResponse> download(HttpServletRequest request){

        String guid = request.getParameter("guid");
        Long scheduleIdToSearch = Long.parseLong(request.getParameter("schedule"));

        /* check that user is registered */
        UserEntity userToSearch = userRepository.findByGuid(guid);
        if(userToSearch==null){
            log.warn("[Host: '{}', IP: '{}', Port: '{}'] Tried to get download schedule but guid: '{}' is not registered in DB.", request.getHeader("Host"),request.getRemoteAddr(),request.getServerPort(), guid);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(ApiResponse.resultKo(HttpStatus.UNAUTHORIZED.toString(), "Wrong guid.", "/downloadschedule", HttpStatus.UNAUTHORIZED.value()));
        }

        /* check that schedule exists */
        ScheduleEntity scheduleToDownload = scheduleRepository.findByScheduleId(scheduleIdToSearch);

        if(scheduleToDownload == null){
            log.warn("[Host: '{}', IP: '{}', Port: '{}', GUID: '{}'] Sent schedule id: '{}' that is not in DB.", request.getHeader("Host"),request.getRemoteAddr(),request.getServerPort(), guid, scheduleIdToSearch);
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ApiResponse.resultKo(HttpStatus.NOT_FOUND.toString(), "Schedule id not found.", "/downloadschedule", HttpStatus.UNAUTHORIZED.value()));
        }

        Integer downloads=scheduleToDownload.getDownloads();
        if(downloads==null){
            downloads=0;
        }
        scheduleToDownload.setDownloads(downloads+1);

        ScheduleEntity scheduleDownloaded = new ScheduleEntity(userToSearch.getUserId(), scheduleToDownload.getDataJson(), scheduleToDownload.getTitle(), scheduleToDownload.getDescription(), scheduleToDownload.getCategoria1(), scheduleToDownload.getCategoria2(), scheduleToDownload.getEquipment(),scheduleToDownload.getCreator());

        /* insert schedule */
        try{
            this.insert(scheduleDownloaded);
            /* update download value */
            this.insert(scheduleToDownload);
        }catch(Exception e){
            log.error("[Host: '{}', IP: '{}', Port: '{}', GUID: '{}'] Tried to download schedule but error: '{}' occurred while inserting data.", request.getHeader("Host"), request.getRemoteAddr(), request.getServerPort(), guid, e.getMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error inserting data in DB.");
        }

        /* return ack */
        log.info("[Host: '{}', IP: '{}', Port: '{}', GUID: '{}'] DownloadScheduleServiceBean ok.", request.getHeader("Host"),request.getRemoteAddr(),request.getServerPort(), guid);
        return ResponseEntity.ok(ApiResponse.resultOk("/downloadschedule", "Schedule downloaded sucessfully.","{}"));
    }

    public void insert(ScheduleEntity scheduleToSave) {
        scheduleRepository.save(scheduleToSave);
    }
}
