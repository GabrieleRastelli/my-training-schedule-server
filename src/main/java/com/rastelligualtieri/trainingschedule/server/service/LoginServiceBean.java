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
public class LoginServiceBean {

    private static Logger log = LoggerFactory.getLogger(RegisterServiceBean.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ScheduleRepository scheduleRepository;

    public ResponseEntity<ApiResponse> loginUser(HttpServletRequest request){

        String email = request.getParameter("email");
        String passwd = request.getParameter("password");

        /* check that user is registered */
        UserEntity userToSearch = userRepository.findByEmail(email);
        if(userToSearch==null){
            log.warn("[Host: '{}', IP: '{}', Port: '{}'] Tried to login but email: '{}' is not registered in DB.", request.getHeader("Host"),request.getRemoteAddr(),request.getServerPort(), email);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(ApiResponse.resultKo(HttpStatus.UNAUTHORIZED.toString(), "You are not registered.", "/login", HttpStatus.UNAUTHORIZED.value()));
        }

        /* check that passwd is correct */
        String hashedSentPassword = DigestUtils.sha256Hex(passwd + "8b7db488-1d18-4827-81a2-326cdc4b3bb9");
        boolean passwdSentIsCorrect = userToSearch.getPassword().equals(hashedSentPassword);
        if(!passwdSentIsCorrect){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(ApiResponse.resultKo(HttpStatus.UNAUTHORIZED.toString(), "You are not registered.", "/login", HttpStatus.UNAUTHORIZED.value()));
        }

        List<Object> schedulesGenericInfo = scheduleRepository.findSchedulesGenericInfo(userToSearch.getUserId());

        /* converts list in json */
        try {
            ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
            String jsonSchedules = ow.writeValueAsString(schedulesGenericInfo);
            return ResponseEntity.ok(ApiResponse.resultOk("/login", jsonSchedules));
        } catch (JsonProcessingException e) {
            log.warn("[Host: '{}', IP: '{}', Port: '{}'] Error JSON parsing schedule generic infos.", request.getHeader("Host"),request.getRemoteAddr(),request.getServerPort());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Internal server error");
        }
    }
}
