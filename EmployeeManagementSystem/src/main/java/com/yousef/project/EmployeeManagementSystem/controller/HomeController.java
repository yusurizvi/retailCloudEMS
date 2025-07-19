package com.yousef.project.EmployeeManagementSystem.controller;

import com.yousef.project.EmployeeManagementSystem.dto.DepartmentRequest;
import com.yousef.project.EmployeeManagementSystem.dto.EmployeeRequest;
import com.yousef.project.EmployeeManagementSystem.model.Department;
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

    }
    @PostMapping("/add-employee")
    public ResponseEntity<String> addEmployee(@RequestBody EmployeeRequest request) {
        String response = employeeService.addEmployee(request);
        if (response.equalsIgnoreCase("Employee added successfully"))
            return new ResponseEntity<>(response,HttpStatus.OK);
        else
            return new ResponseEntity<>(response,HttpStatus.INTERNAL_SERVER_ERROR);
    }
    @PostMapping("/add_department")
    public ResponseEntity<String> addDepartment(@RequestBody DepartmentRequest request) {
        boolean result = employeeService.addDepartment(request);
        return result?new ResponseEntity<>("Department added successfully", HttpStatus.OK)
                :new ResponseEntity<>("Invalid Reporting Manager",HttpStatus.INTERNAL_SERVER_ERROR);
    }
    @GetMapping("/get-Departments")
    public ResponseEntity<List<Department>>getDepartments() {
        List<Department> departmentList = employeeService.getAllDepartments();
        return new ResponseEntity<>(departmentList, HttpStatus.OK);
    }
    @PutMapping("/update-emp/{id}")
    public ResponseEntity<String> updateEmployee(@RequestBody EmployeeRequest request, @PathVariable String id) {
        String response = employeeService.updateEmployee(request,id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @PutMapping("/update-dept/{id}")
    public ResponseEntity<String> updateDepartment(@RequestBody DepartmentRequest request, @PathVariable String id) {
        String response = employeeService.updateDepartment(request,id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
