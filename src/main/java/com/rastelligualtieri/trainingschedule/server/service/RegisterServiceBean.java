package com.rastelligualtieri.trainingschedule.server.service;


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
                    .body(ApiResponse.resultKo(HttpStatus.TOO_MANY_REQUESTS.toString(), "You are already registered.", "/sendData", HttpStatus.TOO_MANY_REQUESTS.value()));
        }

        /* encrypts password */
        String hashCalculated = DigestUtils.sha256Hex(passwd + "8b7db488-1d18-4827-81a2-326cdc4b3bb9");

        /* if not already inserted insterts new user */
        UserEntity userToInsert = new UserEntity(name, email, passwd);

        try{
            this.insert(userToInsert);
        }catch(Exception e){
            log.error("[Host: '{}', IP: '{}', Port: '{}'] Tried to register but error: '{}' occurred while inserting data.", request.getHeader("Host"), request.getRemoteAddr(), request.getServerPort(), e.getMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Internal server error");
        }

        return ResponseEntity.ok(ApiResponse.resultOk("/register", "Data insertion succesful."));
    }

    public void insert(UserEntity request) {
        userRepository.save(request);
    }
}
