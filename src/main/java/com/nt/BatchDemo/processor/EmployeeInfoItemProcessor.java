package com.nt.BatchDemo.processor;

import com.nt.BatchDemo.model.Employee;
import org.springframework.batch.item.ItemProcessor;

public class EmployeeInfoItemProcessor  implements ItemProcessor<Employee, Employee> {
    @Override
    public Employee process(Employee emp) throws Exception {

        return emp;
    }
}
