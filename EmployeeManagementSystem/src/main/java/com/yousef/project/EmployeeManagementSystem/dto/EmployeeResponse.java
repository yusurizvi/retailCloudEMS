package com.yousef.project.EmployeeManagementSystem.dto;

import com.yousef.project.EmployeeManagementSystem.model.Department;
import com.yousef.project.EmployeeManagementSystem.model.Employee;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeResponse extends EmployeeLookUpResponse {
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
