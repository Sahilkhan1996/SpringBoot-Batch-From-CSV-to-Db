package com.nt.BatchDemo.config;

import com.nt.BatchDemo.model.Employee;
import com.nt.BatchDemo.processor.EmployeeInfoItemProcessor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.interceptor.DefaultTransactionAttribute;
import org.springframework.transaction.interceptor.TransactionAttribute;

import javax.sql.DataSource;

@Configuration
@EnableBatchProcessing
public class BatchCofig {
    @Autowired
    private DataSource ds;
    @Autowired
    private JobBuilderFactory jobFactory;
    @Autowired
    private StepBuilderFactory stepBuilderFactory;
    private PlatformTransactionManager transactionManager;


    @Bean(name = "reader")
    public ItemReader<Employee> createReader() {
        return new FlatFileItemReaderBuilder<Employee>().name("reader").resource(new ClassPathResource("EmployeesInfo.csv")).delimited().delimiter(",").names("empno", "ename", "salary", "eadd").targetType(Employee.class).build();
    }

    @Bean(name = "writer")
    public ItemWriter<Employee> createWriter() {
        return new JdbcBatchItemWriterBuilder<Employee>().dataSource(ds).sql("INSERT INTO EMPLOYEE VALUES(:empno,:empname,:salary,:eadd,:grossSalary,:netSalary").beanMapped().build();

    }

    @Bean
    public ItemProcessor<Employee, Employee> createProcessor() {
        return new EmployeeInfoItemProcessor();

    }

    @Bean(name = "step1")
    public Step createStep1() {
        return stepBuilderFactory
                .get("step1").<Employee, Employee>chunk(3)
                .reader(createReader()).writer(createWriter())
                .processor(createProcessor())
                .build();


    }

    @Bean(name = "job1")
    public Job createJob1() {
        return jobFactory.get("job1").incrementer(new RunIdIncrementer()).start(createStep1()).build();
    }

    // Method to define default transaction attributes
    public TransactionAttribute getDefaultTransactionAttribute() {
        DefaultTransactionAttribute attribute = new DefaultTransactionAttribute();
        attribute.setPropagationBehaviorName("PROPAGATION_REQUIRED");
        attribute.setIsolationLevelName("ISOLATION_READ_COMMITTED");
        // Set other attributes as needed
        return attribute;
    }


}
