package com.yousef.project.EmployeeManagementSystem.dto;

import com.yousef.project.EmployeeManagementSystem.model.Employee;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class EmployeeResponse extends Employee{

    private String id;
    private String name;

}
