package com.reactivestax;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.reactivestax.repository.AuditRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.TextMessage;
import java.util.Date;
import java.util.concurrent.CountDownLatch;

@Component
public class Listener {

    @Autowired
    private AuditRepository auditRepository;


    CountDownLatch latch = new CountDownLatch(100);

    public CountDownLatch getLatch() {
        return latch;
    }

    @JmsListener(destination = "${destination.input-queue}")
    @SendTo("${destination.output-queue}")
    public String receiveMessage(final Message jsonMessage) throws JMSException {
        String messageData = null;
        System.out.println("Received message " + jsonMessage);
        String response = null;
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setDateFormat("dd-MM-yyyy HH:mm:ss");
        //gsonBuilder.registerTypeAdapter(Date.class, new DateDeserializer());

        Gson gson = gsonBuilder.create();

        if(jsonMessage instanceof TextMessage) {
            TextMessage textMessage = (TextMessage)jsonMessage;
            messageData = textMessage.getText();
            Audits audits = gson.fromJson(messageData,Audits.class);
            System.out.println(audits);
            auditRepository.save(audits);
            //response  = "Hello from " + messageData;
            //System.out.println("response ===> "+response);
            //latch.countDown();
        }
        return response;
    }
}