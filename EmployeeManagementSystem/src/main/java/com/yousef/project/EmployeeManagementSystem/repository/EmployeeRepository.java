package com.yousef.project.EmployeeManagementSystem.repository;

import com.yousef.project.EmployeeManagementSystem.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee,Integer> {

}
