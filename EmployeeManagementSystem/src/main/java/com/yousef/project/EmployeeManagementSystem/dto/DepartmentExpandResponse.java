package com.yousef.project.EmployeeManagementSystem.dto;

import com.yousef.project.EmployeeManagementSystem.model.Department;
import com.yousef.project.EmployeeManagementSystem.model.Employee;
import lombok.Data;

import java.util.List;
@Data
public class DepartmentExpandResponse {

     private Department department;
     private List<Employee> employees;
}
