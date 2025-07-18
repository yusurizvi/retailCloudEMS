package com.yousef.project.EmployeeManagementSystem.dto;

import com.yousef.project.EmployeeManagementSystem.model.Department;
import com.yousef.project.EmployeeManagementSystem.model.Employee;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Transient;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
@Data
public class EmployeeRequest {

    private String id;
    private  String name;
    private  String salary;
    private String dept;
    private String address;
    private String dateOfBirth;
    private String joiningDate;
    private String role;
    private String reportingManager;
    private String bonusPercentage;
}
