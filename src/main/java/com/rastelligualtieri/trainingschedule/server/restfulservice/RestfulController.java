package com.rastelligualtieri.trainingschedule.server.restfulservice;

import com.rastelligualtieri.trainingschedule.server.apiresponse.ApiResponse;
import com.rastelligualtieri.trainingschedule.server.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
public class RestfulController {

    @Autowired
    private RegisterServiceBean registerService;

    @Autowired
    private LoginServiceBean loginService;

    @Autowired
    private HomeInfoServiceBean homeInfoService;

    @Autowired
    private ExerciseServiceBean exerciseService;

    @Autowired
    private ScheduleInfoServiceBean scheduleInfoService;

    @Autowired
    private CreateScheduleServiceBean createScheduleService;

    @Autowired
    private UpdateScheduleServiceBean updateScheduleService;

    @Autowired
    private UserInfoServiceBean userInfoService;


    @PostMapping(path = "/register", consumes = "application/x-www-form-urlencoded", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse> registerEndpoint(HttpServletRequest request) {
        return registerService.registerUser(request);
    }

    @PostMapping(path = "/login", consumes = "application/x-www-form-urlencoded", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse> loginEndpoint(HttpServletRequest request) {
        return loginService.loginUser(request);
    }

    @PostMapping(path = "/homeinfo", consumes = "application/x-www-form-urlencoded", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse> getHomeInfo(HttpServletRequest request) {
        return homeInfoService.getHomeInfo(request);
    }

    @PostMapping(path = "/exercise", consumes = "application/x-www-form-urlencoded", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse> exerciseEndpoint(HttpServletRequest request) {
        return exerciseService.getExercises(request);
    }

    @PostMapping(path = "/scheduleinfo", consumes = "application/x-www-form-urlencoded", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse> scheduleInfoEndpoint(HttpServletRequest request) {
        return scheduleInfoService.getScheduleInfo(request);
    }

    @PostMapping(path = "/createschedule", consumes = "application/x-www-form-urlencoded", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse> createScheduleEndpoint(HttpServletRequest request) {
        return createScheduleService.createSchedule(request);
    }

    @PostMapping(path = "/updateschedule", consumes = "application/x-www-form-urlencoded", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse> updateScheduleEndpoint(HttpServletRequest request) {
        return updateScheduleService.updateSchedule(request);
    }

    @PostMapping(path = "/userinfo", consumes = "application/x-www-form-urlencoded", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse> getUserInfoEndpoint(HttpServletRequest request) {
        return userInfoService.getUserInfo(request);
    }

}