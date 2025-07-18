package com.yousef.project.EmployeeManagementSystem.controller;

import com.yousef.project.EmployeeManagementSystem.dto.EmployeeRequest;
import com.yousef.project.EmployeeManagementSystem.model.Employee;
import com.yousef.project.EmployeeManagementSystem.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/EMS")
public class HomeController {

    private final EmployeeService employeeService;

    @GetMapping("/Employee-details")
    public List<Employee> getEmployee() {
        return employeeService.getEmployeeDetails();
//    return nul?l;
    }
    @PostMapping("/add-employee")
    public ResponseEntity<String> addEmployee(@RequestBody EmployeeRequest request) {
        String response = employeeService.addEmployee(request);
        if (response.equalsIgnoreCase("Employee added successfully"))
            return new ResponseEntity<>(response,HttpStatus.OK);
        else
            return new ResponseEntity<>(response,HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
