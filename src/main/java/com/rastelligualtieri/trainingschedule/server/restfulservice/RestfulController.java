package com.rastelligualtieri.trainingschedule.server.restfulservice;

import com.rastelligualtieri.trainingschedule.server.apiresponse.ApiResponse;
import com.rastelligualtieri.trainingschedule.server.service.LoginServiceBean;
import com.rastelligualtieri.trainingschedule.server.service.RegisterServiceBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@RestController
public class RestfulController {

    @Autowired
    private LoginServiceBean loginService;

    @Autowired
    private RegisterServiceBean registerService;


    //@RequestMapping("/login")
    @PostMapping(path = "/login", consumes = "application/x-www-form-urlencoded", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse> loginEndpoint(HttpServletRequest request) {
        return loginService.loginUser(request);
    }

    @PostMapping(path = "/register", consumes = "application/x-www-form-urlencoded", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse> registerEndpoint(HttpServletRequest request) {
        return registerService.registerUser(request);
    }


}