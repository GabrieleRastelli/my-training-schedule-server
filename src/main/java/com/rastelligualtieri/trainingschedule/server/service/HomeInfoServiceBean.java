package com.rastelligualtieri.trainingschedule.server.service;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.util.DefaultIndenter;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rastelligualtieri.trainingschedule.server.apiresponse.ApiResponse;
import com.rastelligualtieri.trainingschedule.server.model.*;
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
public class HomeInfoServiceBean {

    private static Logger log = LoggerFactory.getLogger(HomeInfoServiceBean.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ScheduleRepository scheduleRepository;

    public ResponseEntity<ApiResponse> getHomeInfo(HttpServletRequest request){

        String guid = request.getParameter("guid");

        /* check that user is registered */
        UserEntity userToSearch = userRepository.findByGuid(guid);
        if(userToSearch==null){
            log.warn("[Host: '{}', IP: '{}', Port: '{}'] Tried to get home info but guid: '{}' is not registered in DB.", request.getHeader("Host"),request.getRemoteAddr(),request.getServerPort(), guid);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(ApiResponse.resultKo(HttpStatus.UNAUTHORIZED.toString(), "Wrong guid.", "/homeinfo", HttpStatus.UNAUTHORIZED.value()));
        }

        List<ScheduleGenericInfo> genericScheduleInfo = scheduleRepository.findDocumentsForListing(userToSearch.getUserId());

        /* converts list to json */
        try {
            String jsonSchedules = JsonUtils.objectToJson(genericScheduleInfo);
            return ResponseEntity.ok(ApiResponse.resultOk("/homeinfo", "Extraction succesful.", jsonSchedules));
        } catch (JsonProcessingException e) {
            log.error("[Host: '{}', IP: '{}', Port: '{}', GUID: '{}'] Error JSON parsing genericScheduleInfo: '{}'.", request.getHeader("Host"),request.getRemoteAddr(),request.getServerPort(), guid, e.getMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error json parsing schedules info.");
        }
    }
}
