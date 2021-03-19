package com.rastelligualtieri.trainingschedule.server.service;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.rastelligualtieri.trainingschedule.server.apiresponse.ApiResponse;
import com.rastelligualtieri.trainingschedule.server.model.UserEntity;
import com.rastelligualtieri.trainingschedule.server.model.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

@Service
public class RegisterServiceBean {

    private static Logger log = LoggerFactory.getLogger(RegisterServiceBean.class);

    @Autowired
    private UserRepository userRepository;


    public ResponseEntity<ApiResponse> registerUser(HttpServletRequest request){

        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String passwd = request.getParameter("password");

        /* check correct parameters */
        boolean nameIsOk= (name!=null && !name.isEmpty());
        boolean emailIsOk= (email!=null && !email.isEmpty());
        boolean passwdIsOk= (passwd!=null && !passwd.isEmpty());

        /* check that not already registered */
        if(userRepository.findByEmail(email)!=null){
            log.warn("[Host: '{}', IP: '{}', Port: '{}'] Tried to register but email: '{}' is already registered in DB.", request.getHeader("Host"),request.getRemoteAddr(),request.getServerPort(), email);
            return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS)
                    .body(ApiResponse.resultKo(HttpStatus.TOO_MANY_REQUESTS.toString(), "You are already registered.", "/register", HttpStatus.TOO_MANY_REQUESTS.value()));
        }

        /* encrypts password */
        String hashCalculated = DigestUtils.sha256Hex(passwd + "8b7db488-1d18-4827-81a2-326cdc4b3bb9");

        /* assign guid */
        String guid = UUID.randomUUID().toString();

        /* if not already registered insterts new user */
        UserEntity userToInsert = new UserEntity(guid, name, email, hashCalculated);

        try{
            this.insert(userToInsert);
        }catch(Exception e){
            log.error("[Host: '{}', IP: '{}', Port: '{}'] Tried to register but error: '{}' occurred while inserting data.", request.getHeader("Host"), request.getRemoteAddr(), request.getServerPort(), e.getMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error inserting data in DB.");
        }

        /* returns guid */
        try {
            ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
            String jsonGuid = ow.writeValueAsString(guid);
            log.info("[Host: '{}', IP: '{}', Port: '{}'] Host correctly registered. Assigned guid: '{}'.", request.getHeader("Host"),request.getRemoteAddr(),request.getServerPort(), guid);
            return ResponseEntity.ok(ApiResponse.resultOk("/register", jsonGuid.replaceAll("\\\"","")));
        } catch (JsonProcessingException e) {
            log.error("[Host: '{}', IP: '{}', Port: '{}'] Error JSON parsing guid: '{}'.", request.getHeader("Host"),request.getRemoteAddr(),request.getServerPort(), e.getMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error json parsing guid.");
        }
    }

    public void insert(UserEntity request) {
        userRepository.save(request);
    }
}
