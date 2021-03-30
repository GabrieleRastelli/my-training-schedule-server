package com.rastelligualtieri.trainingschedule.server.service;


import com.rastelligualtieri.trainingschedule.server.apiresponse.ApiResponse;
import com.rastelligualtieri.trainingschedule.server.model.*;
import com.rastelligualtieri.trainingschedule.server.repository.UserRepository;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Service
public class UserInfoServiceBean {

    private static Logger log = LoggerFactory.getLogger(UserInfoServiceBean.class);

    @Autowired
    private UserRepository userRepository;


    public ResponseEntity<ApiResponse> getUserInfo(HttpServletRequest request){

        String guid = request.getParameter("guid");

        /* check that user is registered */
        UserEntity userToSearch = userRepository.findByGuid(guid);
        if(userToSearch==null){
            log.warn("[Host: '{}', IP: '{}', Port: '{}'] Tried to get home info but guid: '{}' is not registered in DB.", request.getHeader("Host"),request.getRemoteAddr(),request.getServerPort(), guid);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(ApiResponse.resultKo(HttpStatus.UNAUTHORIZED.toString(), "Wrong guid.", "/userinfo", HttpStatus.UNAUTHORIZED.value()));
        }

        UserEntity userEntityInfo = userRepository.findByGuid(guid);

        /* returns guid */
        JSONObject jsonData = new JSONObject();
        jsonData.put("email", userEntityInfo.getEmail());
        jsonData.put("name", userEntityInfo.getName());
        jsonData.put("image", userEntityInfo.getProfileImage());
        String data = jsonData.toString();
        log.info("[Host: '{}', IP: '{}', Port: '{}', GUID: '{}'] Correctly sent user info.", request.getHeader("Host"),request.getRemoteAddr(),request.getServerPort(), guid);
        return ResponseEntity.ok(ApiResponse.resultOk("/userinfo", "Sucessfully returned user info.", data));
    }
}
