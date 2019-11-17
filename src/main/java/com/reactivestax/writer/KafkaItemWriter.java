package com.reactivestax.writer;

import com.reactivestax.model.TransactionRawData;
import com.reactivestax.producer.Sender;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class KafkaItemWriter<T> implements ItemWriter<TransactionRawData> {

    @Autowired
    private Sender sender;

    @Override
    public void write(List<? extends TransactionRawData> list) throws Exception {
        for (TransactionRawData transactionRawData:list) {
            System.out.println(transactionRawData);
            sender.send(transactionRawData);
        }
    }
}
