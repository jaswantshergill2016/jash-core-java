package com.reactivestax.consumer;

import com.avrogenerated.TransactionStagedData;
import com.reactivestax.model.TransactionRawData;
import com.reactivestax.producer.Sender;
import example.avro.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;

import java.util.Date;
import java.util.concurrent.CountDownLatch;

public class Receiver {


  private static final Logger LOGGER = LoggerFactory.getLogger(Receiver.class);

  @Autowired
  private Sender sender;

  private CountDownLatch latch = new CountDownLatch(1);

  public CountDownLatch getLatch() {
    return latch;
  }

  @KafkaListener(topics = "${kafka.topic.raw}")
  public void receive(TransactionRawData transactionRawData) {
    LOGGER.info("received TransactionRawData='{}'", transactionRawData.toString());

    TransactionStagedData transactionStagedData = convertFromTransactionRawData2StagedData(transactionRawData);
    sender.send(transactionStagedData);
    latch.countDown();
  }

  private TransactionStagedData convertFromTransactionRawData2StagedData(TransactionRawData transactionRawData) {
    String accountNo = transactionRawData.getAccountNo();

    //"date", "transactionDetails", "chqNo","valueDate","withDrawalAmt","depositAmt","balanceAmt"
    String transactionStringArr[] = transactionRawData.getTransactionString().split("#");

    String date = transactionStringArr[0];
    String transactionDetails = transactionStringArr[1];
    String chqNo = transactionStringArr[2];
    String valueDate = transactionStringArr[3];
    String withDrawlAmt = transactionStringArr[4];
    String depositAmt = transactionStringArr[5];
    String balanceAmt = transactionStringArr[6];

    Date txnDate = new Date(date);

    System.out.println("txnDate  "+txnDate);

    TransactionStagedData transactionStagedData = new TransactionStagedData();

    transactionStagedData.setAccountNo(accountNo);
    transactionStagedData.setTransactionDate(txnDate.getTime());
    transactionStagedData.setTransactionDetails(transactionDetails);
    if(chqNo.trim().equals("")){
      transactionStagedData.setChqNo(0L);
    }else{
      transactionStagedData.setChqNo(Long.parseLong(chqNo));
    }
    transactionStagedData.setValueDate(new Date(valueDate).getTime());
    if(withDrawlAmt.trim().equals("")) {
      transactionStagedData.setWithDrawlAmt(0.0);
    }else{
      transactionStagedData.setWithDrawlAmt(Double.parseDouble(withDrawlAmt.replaceAll(",","")));
    }
    if(depositAmt.trim().equals("")) {
      transactionStagedData.setDepositAmt(0.0);
    }else{
      transactionStagedData.setDepositAmt(Double.parseDouble(depositAmt.replaceAll(",","")));
    }
    if(balanceAmt.trim().equals("")) {
      transactionStagedData.setBalanceAmt(0.0);
    }else{
      transactionStagedData.setBalanceAmt(Double.parseDouble(balanceAmt.replaceAll(",","")));
    }

    return transactionStagedData;

  }
}
