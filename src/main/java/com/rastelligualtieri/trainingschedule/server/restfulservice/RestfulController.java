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
    private SuggestedScheduleInfoServiceBean suggestedScheduleInfoServiceBean;

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

    @Autowired
    private DeleteScheduleServiceBean deleteScheduleService;

    @Autowired
    private AllSchedulesServiceBean allSchedulesServiceBean;

    @Autowired
    private DownloadScheduleServiceBean downloadScheduleServiceBean;

    @Autowired
    private UpdateUserServiceBean updateUserService;

    @Autowired
    private PopularSchedulesServiceBean popularSchedulesServiceBean;

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

    @PostMapping(path = "/suggestedinfo", consumes = "application/x-www-form-urlencoded", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse> getSuggestedInfo(HttpServletRequest request) {
        return suggestedScheduleInfoServiceBean.getSuggestedScheduleInfo(request);
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

    @PostMapping(path = "/deleteschedule", consumes = "application/x-www-form-urlencoded", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse> deleteScheduleEndpoint(HttpServletRequest request) {
        return deleteScheduleService.deleteSchedule(request);
    }

    @PostMapping(path = "/allschedules", consumes = "application/x-www-form-urlencoded", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse> allScheduleEndpoint(HttpServletRequest request) {
        return allSchedulesServiceBean.getAll(request);
    }

    @PostMapping(path = "/downloadschedule", consumes = "application/x-www-form-urlencoded", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse> downloadScheduleEndpoint(HttpServletRequest request) {
        return downloadScheduleServiceBean.download(request);
    }

    @PostMapping(path = "/updateuser", consumes = "application/x-www-form-urlencoded", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse> updateUserEndpoint(HttpServletRequest request) {
        return updateUserService.updateUser(request);
    }

    @PostMapping(path = "/popularschedules", consumes = "application/x-www-form-urlencoded", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse> popularScheduleEndpoint(HttpServletRequest request) {
        return popularSchedulesServiceBean.getPopular(request);
    }
}