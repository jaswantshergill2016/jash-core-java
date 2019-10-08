package io.reactivestax.aop;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.reactivestax.domain.ResourceTypes;
import io.reactivestax.jmssender.Audits;
import io.reactivestax.jmssender.Sender;
import io.reactivestax.kafka.KafkaProducer;
import io.reactivestax.repository.ResourceTypeRepository;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import java.util.Date;
import java.util.Optional;

@Slf4j
@Aspect
@Configuration
public class AopResourceType {

    @Autowired
    private Sender sender;

    @Autowired
    KafkaProducer kafkaProducer;

    @Autowired
    private ResourceTypeRepository resourceTypeRepository;



    //What kind of method calls I would intercept
    //execution(* PACKAGE.*.*(..))
    //Weaving & Weaver
    //@Before("execution(* com.in28minutes.springboot.tutorial.basics.example.aop.data.*.*(..))")

    @After("execution(* io.reactivestax.service.ResourceTypeService.createResourceType(..))")
    public void after(JoinPoint joinPoint){
        //Advice
        log.info(" ============================= ");
        log.info(" createResourceType ");
        log.info(" ===========================", joinPoint.getSignature().getName());
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setDateFormat("dd-MM-yyyy HH:mm:ss");
        Object[] args = joinPoint.getArgs();
        System.out.println("*************************************");
        System.out.println("joinPoint.getTarget() ===>  "+joinPoint.getTarget());
        ResourceTypes resourceTypes = (ResourceTypes)args[0];
        Gson gson = gsonBuilder.create();

        Audits audits = new Audits(0,"create","ResourceType",resourceTypes.getResourceTypeId(),resourceTypes.getCreatedDt(),resourceTypes.getCreatedBy());

        String jsonMessage = gson.toJson(audits);


        System.out.println("jsonMessage==> "+ jsonMessage);

        sender.send(jsonMessage);
        kafkaProducer.sendMessageOnKafkaTopic(audits);
        /*
        try{
            sender.send(jsonMessage);
        }catch(Exception ex){
            ex.printStackTrace();
        }
        try{
            kafkaProducer.sendMessageOnKafkaTopic(audits);
        }catch(Exception ex){
            ex.printStackTrace();
        }
        */



    }


    @After("execution(* io.reactivestax.service.ResourceTypeService.updateResourceType(..))")
    public void update(JoinPoint joinPoint){
        //Advice
        log.info(" ============================= ");
        log.info(" updateResourceType ");
        log.info(" ===========================", joinPoint.getSignature().getName());
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setDateFormat("dd-MM-yyyy HH:mm:ss");
        Object[] args = joinPoint.getArgs();
        System.out.println("*************************************");
        System.out.println("joinPoint.getTarget() ===>  "+joinPoint.getTarget());
        ResourceTypes resourceTypes = (ResourceTypes)args[0];
        String resourceTypesId = (String)args[1];
        Gson gson = gsonBuilder.create();

        Audits audits = new Audits(0,"update","ResourceType",Integer.parseInt(resourceTypesId),resourceTypes.getCreatedDt(),resourceTypes.getCreatedBy());

        String jsonMessage = gson.toJson(audits);

        System.out.println("jsonMessage==> "+ jsonMessage);

        sender.send(jsonMessage);

    }

    @Before("execution(* io.reactivestax.service.ResourceTypeService.deleteResourceType(..))")
    public void delete(JoinPoint joinPoint){
        //Advice
        log.info(" ============================= ");
        log.info(" deleteResourceType ");
        log.info(" ===========================", joinPoint.getSignature().getName());
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setDateFormat("dd-MM-yyyy HH:mm:ss");
        Object[] args = joinPoint.getArgs();
        System.out.println("*************************************");
        System.out.println("joinPoint.getTarget() ===>  "+joinPoint.getTarget());
        String resourceTypesId = (String)args[0];
        Gson gson = gsonBuilder.create();

        Optional<ResourceTypes> resourceTypesRetrieved =  resourceTypeRepository.findById(Integer.parseInt(resourceTypesId));


        Audits audits = new Audits(0,"delete","ResourceType",Integer.parseInt(resourceTypesId),new Date(),resourceTypesRetrieved.get().getCreatedBy());

        String jsonMessage = gson.toJson(audits);

        System.out.println("jsonMessage==> "+ jsonMessage);

        sender.send(jsonMessage);

    }
}