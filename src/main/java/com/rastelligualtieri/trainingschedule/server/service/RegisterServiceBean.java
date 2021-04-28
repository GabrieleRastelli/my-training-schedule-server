package com.rastelligualtieri.trainingschedule.server.service;


import com.rastelligualtieri.trainingschedule.server.apiresponse.ApiResponse;
import com.rastelligualtieri.trainingschedule.server.model.UserEntity;
import com.rastelligualtieri.trainingschedule.server.repository.UserRepository;
import com.rastelligualtieri.trainingschedule.server.utils.JsonUtils;
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
        String nickname = request.getParameter("nickname");

        /* check correct parameters */
        boolean nameIsOk= (name!=null && !name.isEmpty());
        boolean emailIsOk= (email!=null && !email.isEmpty());
        boolean passwdIsOk= (passwd!=null && !passwd.isEmpty());
        boolean nicknameIsOk= (nickname!=null && !nickname.isEmpty());

        if(!nameIsOk || !emailIsOk || !passwdIsOk || !nicknameIsOk){
            log.warn("[Host: '{}', IP: '{}', Port: '{}'] Tried to register with name: '{}', email: '{}', nickname: '{}' and passwd that are not all correct.", request.getHeader("Host"),request.getRemoteAddr(),request.getServerPort(), name, email, nickname);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ApiResponse.resultKo(HttpStatus.BAD_REQUEST.toString(), "Wrong data sent.", "/register", HttpStatus.BAD_REQUEST.value()));
        }

        /* check that not already registered */
        if(userRepository.findByEmail(email)!=null){
            log.warn("[Host: '{}', IP: '{}', Port: '{}'] Tried to register but email: '{}' is already registered in DB.", request.getHeader("Host"),request.getRemoteAddr(),request.getServerPort(), email);
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(ApiResponse.resultKo(HttpStatus.CONFLICT.toString(), "You are already registered.", "/register", HttpStatus.CONFLICT.value()));
        }

        /* check that nickname is free */
        if(userRepository.findByNickname(nickname)!=null){
            log.warn("[Host: '{}', IP: '{}', Port: '{}'] Tried to register but nickname: '{}' is already registered in DB.", request.getHeader("Host"),request.getRemoteAddr(),request.getServerPort(), nickname);
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(ApiResponse.resultKo(HttpStatus.CONFLICT.toString(), "Nickname already registered.", "/register", HttpStatus.CONFLICT.value()));
        }

        /* encrypts password */
        String hashCalculated = DigestUtils.sha256Hex(passwd + "8b7db488-1d18-4827-81a2-326cdc4b3bb9");

        /* assign guid */
        String guid = UUID.randomUUID().toString();

        /* if not already registered insterts new user */
        UserEntity userToInsert = new UserEntity(guid, nickname, name, email, hashCalculated);

        try{
            this.insert(userToInsert);
        }catch(Exception e){
            log.error("[Host: '{}', IP: '{}', Port: '{}'] Tried to register but error: '{}' occurred while inserting data.", request.getHeader("Host"), request.getRemoteAddr(), request.getServerPort(), e.getMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error inserting data in DB.");
        }

        /* returns guid */
        String jsonGuid = JsonUtils.stringToJson("guid",guid);
        log.info("[Host: '{}', IP: '{}', Port: '{}', GUID: '{}'] Host correctly registered.", request.getHeader("Host"),request.getRemoteAddr(),request.getServerPort(), guid);
        return ResponseEntity.ok(ApiResponse.resultOk("/register", "Registered sucessfully.", jsonGuid));

    }

    public void insert(UserEntity request) {
        userRepository.save(request);
    }

}
