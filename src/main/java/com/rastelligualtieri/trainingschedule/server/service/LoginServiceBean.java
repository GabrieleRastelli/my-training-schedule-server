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

@Service
public class LoginServiceBean {

    private static Logger log = LoggerFactory.getLogger(LoginServiceBean.class);

    @Autowired
    private UserRepository userRepository;


    public ResponseEntity<ApiResponse> loginUser(HttpServletRequest request){

        String email = request.getParameter("email");
        String passwd = request.getParameter("password");

        /* check that user is registered */
        UserEntity userToSearch = userRepository.findByEmail(email);
        if(userToSearch==null){
            log.warn("[Host: '{}', IP: '{}', Port: '{}'] Tried to login but email: '{}' is not registered in DB.", request.getHeader("Host"),request.getRemoteAddr(),request.getServerPort(), email);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(ApiResponse.resultKo(HttpStatus.UNAUTHORIZED.toString(), "User not registered.", "/login", HttpStatus.UNAUTHORIZED.value()));
        }

        /* check that passwd is correct */
        String hashedSentPassword = DigestUtils.sha256Hex(passwd + "8b7db488-1d18-4827-81a2-326cdc4b3bb9");
        boolean passwdSentIsCorrect = userToSearch.getPassword().equals(hashedSentPassword);
        if(!passwdSentIsCorrect){
            log.warn("[Host: '{}', IP: '{}', Port: '{}'] Tried to login but password is not registered in DB.", request.getHeader("Host"),request.getRemoteAddr(),request.getServerPort(), hashedSentPassword);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(ApiResponse.resultKo(HttpStatus.UNAUTHORIZED.toString(), "Password is wrong.", "/login", HttpStatus.UNAUTHORIZED.value()));
        }

        String guid = userToSearch.getGuid();

        /* returns guid */
        try {
            ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
            String jsonGuid = ow.writeValueAsString(guid);
            log.info("[Host: '{}', IP: '{}', Port: '{}', GUID: '{}'] Host correctly login.", request.getHeader("Host"),request.getRemoteAddr(),request.getServerPort(), guid);
            return ResponseEntity.ok(ApiResponse.resultOk("/login", jsonGuid.replaceAll("\\\"","")));
        } catch (JsonProcessingException e) {
            log.error("[Host: '{}', IP: '{}', Port: '{}', GUID: '{}'] Error JSON parsing guid: '{}'.", request.getHeader("Host"),request.getRemoteAddr(),request.getServerPort(), guid, e.getMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error json parsing guid.");
        }
    }
}
