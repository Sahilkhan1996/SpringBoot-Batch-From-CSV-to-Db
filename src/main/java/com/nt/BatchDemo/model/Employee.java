package com.nt.BatchDemo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Employee {

    private Integer empno;
    private String empname;
    private Float salary;
    private String eadd;
    private Float grosssalary;
    private Float netsalary;
}
