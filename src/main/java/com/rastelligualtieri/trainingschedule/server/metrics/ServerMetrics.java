package com.rastelligualtieri.trainingschedule.server.metrics;

import io.micrometer.core.instrument.MeterRegistry;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

/**
 * Class to handle server metrics
 *
 */
@Component
@Aspect
public class ServerMetrics {

    private static Logger log = LoggerFactory.getLogger(ServerMetrics.class);

    @Autowired
    private MeterRegistry meterRegistry;

    @Before("execution(* com.rastelligualtieri.trainingschedule.server.restfulservice.RestfulController.registerEndpoint(..))")
    public void registerReceived(JoinPoint joinPoint) {
        try{
            meterRegistry.counter("registrazioni_ricevute").increment();
        }catch(Exception e){
            log.error("Error updating registrazioni_ricevute counter. Error: {}", e.getMessage());
        }
    }

    @After("execution(* com.rastelligualtieri.trainingschedule.server.restfulservice.RestfulController.registerEndpoint(..))")
    public void registerElaborated(JoinPoint joinPoint) {
        try{
            meterRegistry.counter("registrazioni_terminate").increment();
        }catch(Exception e){
            log.error("Error updating registrazioni_terminate counter. Error: {}", e.getMessage());
        }
    }

    @AfterThrowing (value = "execution(* com.rastelligualtieri.trainingschedule.server.service.RegisterServiceBean.registerUser(..))", throwing = "e")
    public void registerErrors(JoinPoint joinPoint, ResponseStatusException e){
        try{
            meterRegistry.counter("errori_registrazione").increment();
            meterRegistry.counter("errore_registrazione_tipo_"+e.getReason().toLowerCase().replace(" ","_")).increment();
        }catch(Exception ex){
            log.error("Error updating errori_registrazione counter. Error: {}", ex.getMessage());
        }
    }
}
