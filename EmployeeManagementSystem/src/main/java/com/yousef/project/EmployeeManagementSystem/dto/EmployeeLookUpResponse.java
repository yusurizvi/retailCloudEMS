package com.yousef.project.EmployeeManagementSystem.dto;

import com.yousef.project.EmployeeManagementSystem.model.Employee;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
public class EmployeeLookUpResponse{

    private int id;
    private String name;
}
