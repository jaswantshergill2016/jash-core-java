package com.reactivestax.mapper;

import com.reactivestax.model.TransactionRawData;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.stereotype.Component;

@Component
public class TransactionRawDataFieldMapper implements FieldSetMapper<TransactionRawData> {

    @Override
    public TransactionRawData mapFieldSet(FieldSet fieldSet) {

        TransactionRawData transactionRawData = new TransactionRawData();
        transactionRawData.setAccountNo(fieldSet.readString("accountNo"));
        //"accountNo", "date", "transactionDetails", "chqNo","valueDate","withDrawalAmt","depositAmt","balanceAmt" });
        transactionRawData.setTransactionString(fieldSet.readString("date")+
                "#"+fieldSet.readString("transactionDetails")+
                "#"+fieldSet.readString("chqNo")+
                "#"+fieldSet.readString("valueDate")+
                "#"+fieldSet.readString("withDrawalAmt")+
                "#"+fieldSet.readString("depositAmt")+
                "#"+fieldSet.readString("balanceAmt")
        );

        return transactionRawData;
    }
}
