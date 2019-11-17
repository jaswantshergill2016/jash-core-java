package com.reactivestax.producer;

import com.avrogenerated.TransactionStagedData;
import example.avro.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
//import org.springframework.kafka.core.KafkaTemplate;


//import example.avro.User;

public class Sender {

  private static final Logger LOGGER = LoggerFactory.getLogger(Sender.class);

  @Value("${kafka.topic.avro}")
  private String avroTopic;

  @Autowired
  private KafkaTemplate<String, TransactionStagedData> kafkaTemplate;

  public void send(TransactionStagedData transactionStagedData) {
    LOGGER.info("sending transactionStagedData='{}'", transactionStagedData.toString());
    kafkaTemplate.send(avroTopic, transactionStagedData);
  }
}
