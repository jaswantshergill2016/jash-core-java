package io.reactivestax.kafka;

import io.reactivestax.jmssender.Audits;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class KafkaProducer {

    private final static String TOPIC = "audit_topic";

    @Autowired
    KafkaTemplate<String, Audits> kafkaTemplate;

    public void sendMessageOnKafkaTopic(Audits audits){
        kafkaTemplate.send(TOPIC,audits);
    }
}
