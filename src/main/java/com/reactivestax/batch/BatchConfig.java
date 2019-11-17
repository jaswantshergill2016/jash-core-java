package com.reactivestax.batch;

import com.javaetmoi.core.batch.writer.ConsoleItemWriter;
import com.reactivestax.listener.JobResultListener;
import com.reactivestax.listener.StepResultListener;
import com.reactivestax.mapper.TransactionRawDataFieldMapper;
import com.reactivestax.model.TransactionRawData;
import com.reactivestax.processor.ValidationProcessor;
//import com.reactivestax.springbatchapp.listeners.JobResultListener;
//import com.reactivestax.springbatchapp.listeners.StepResultListener;
//import com.reactivestax.springbatchapp.model.Employee;
//import com.reactivestax.springbatchapp.processor.ValidationProcessor;
import com.reactivestax.writer.KafkaItemWriter;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.DefaultBatchConfigurer;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

import javax.sql.DataSource;


@Configuration
@EnableBatchProcessing
public class BatchConfig extends DefaultBatchConfigurer {
    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Override
    public void setDataSource(DataSource dataSource) {
        //This BatchConfigurer ignores any DataSource
    }

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Value("/bank_data.csv")
    private Resource inputResource;

    @Autowired
    private TransactionRawDataFieldMapper transactionRawDataFieldMapper;

    @Bean
    public Job readCSVFilesJob() {
        return jobBuilderFactory
                .get("readCSVFilesJob")
                .listener(new JobResultListener())
                .incrementer(new RunIdIncrementer())
                .start(step1())
                .build();
    }

    @Bean
    public Step step1() {
        return stepBuilderFactory
                .get("step1")
                .listener(new StepResultListener())
                .<TransactionRawData, TransactionRawData>chunk(1)
                .reader(reader())
                .processor(processor())
                .writer(writer())
                .build();
    }

    @Bean
    public ItemProcessor<TransactionRawData, TransactionRawData> processor() {
        return new ValidationProcessor();
    }

    @Bean
    public FlatFileItemReader<TransactionRawData> reader() {
        FlatFileItemReader<TransactionRawData> itemReader = new FlatFileItemReader<TransactionRawData>();
        itemReader.setLineMapper(lineMapper());
        itemReader.setLinesToSkip(1);
        itemReader.setResource(inputResource);
        return itemReader;
    }

    @Bean
    public LineMapper<TransactionRawData> lineMapper() {
        DefaultLineMapper<TransactionRawData> lineMapper = new DefaultLineMapper<TransactionRawData>();
        DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer();
        //lineTokenizer.setNames(new String[] { "Account No", "DATE", "TRANSACTION DETAILS" });
        //Account No,DATE,TRANSACTION DETAILS,CHQ.NO.,VALUE DATE, WITHDRAWAL AMT , DEPOSIT AMT ,BALANCE AMT
        lineTokenizer.setNames(new String[] { "accountNo", "date", "transactionDetails", "chqNo","valueDate","withDrawalAmt","depositAmt","balanceAmt" });
        lineTokenizer.setIncludedFields(new int[] {0,1,2,3,4,5,6,7});
        //BeanWrapperFieldSetMapper<TransactionRawData> fieldSetMapper = new BeanWrapperFieldSetMapper<TransactionRawData>();
        //fieldSetMapper.setTargetType(TransactionRawData.class);
        lineMapper.setLineTokenizer(lineTokenizer);
        lineMapper.setFieldSetMapper(transactionRawDataFieldMapper);
        return lineMapper;
    }

    @Bean
    public ItemWriter<TransactionRawData> writer() {


        //return new ConsoleItemWriter<TransactionRawData>();
        return new KafkaItemWriter<TransactionRawData>();

    }


}