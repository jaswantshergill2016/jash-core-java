package com.reactivestax.producer;

import com.reactivestax.model.TransactionRawData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;

public class Sender {

    private static final Logger LOGGER =
            LoggerFactory.getLogger(Sender.class);

    @Autowired
    private KafkaTemplate<String, TransactionRawData> kafkaTemplate;

    public void send(TransactionRawData payload) {
        LOGGER.info("sending payload='{}'", payload);
        kafkaTemplate.send("transactions-raw-topic", payload);
    }
}