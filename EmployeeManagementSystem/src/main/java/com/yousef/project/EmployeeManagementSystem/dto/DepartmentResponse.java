package com.yousef.project.EmployeeManagementSystem.dto;

import com.yousef.project.EmployeeManagementSystem.model.Employee;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DepartmentResponse {

    private String name;
    private String creationDate;
    private String hod;
}
