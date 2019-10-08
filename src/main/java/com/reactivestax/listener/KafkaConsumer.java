package com.reactivestax.listener;

import com.reactivestax.model.Audits;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumer {

//    @KafkaListener(topics="audit_topic", groupId = "group 1")
//    public void consume(String message){
//        System.out.println("kafka message "+message);
//    }

    @KafkaListener(topics="audit_topic", groupId = "group 1")
    public void consume(Audits audits){
        System.out.println("kafka message "+audits);
    }
}
