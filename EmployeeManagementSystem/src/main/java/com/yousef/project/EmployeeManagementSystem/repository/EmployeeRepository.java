package com.yousef.project.EmployeeManagementSystem.repository;

import com.yousef.project.EmployeeManagementSystem.dto.EmployeeLookUpResponse;
import com.yousef.project.EmployeeManagementSystem.model.Department;
import com.yousef.project.EmployeeManagementSystem.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<Employee,Integer> {

    @Query("SELECT  e.id,e.name from Employee e" )
    public List<EmployeeLookUpResponse> getLookUpData();

    Optional<List<Employee>> findAllByDept(Department dept);

    void deleteByDept(Department dept);

//    List<Employee> id(int id);
}
