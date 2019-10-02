package io.reactivestax.jmssender;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

@Component
public class  Sender {
  private static final Logger LOGGER = LoggerFactory.getLogger(Sender.class);

  @Value("${destination.input-queue}")
  private String queue;

  @Autowired
  private JmsTemplate jmsTemplate;

  public void send(String message) {
    LOGGER.info("sending message='{}' to destination='{}'", message, queue);
    jmsTemplate.convertAndSend(queue, message);
  }
}
