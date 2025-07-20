package com.yousef.project.EmployeeManagementSystem.controller;

import com.yousef.project.EmployeeManagementSystem.dto.*;
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
    public List<?> getEmployee(@RequestParam(required = false) Boolean lookup) {
        return employeeService.getEmployeeDetails(lookup);

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
    public ResponseEntity<List<DepartmentResponse>>getDepartments() {
        List<DepartmentResponse> departmentList = employeeService.getAllDepartments();
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
    @PatchMapping("/update-employeedept/{id}")
    public ResponseEntity<String> updateEmployeeDepartment(
            @PathVariable("id") int id,@RequestParam("DeptId") int deptId) {
        String response =employeeService.updateDepartmentId(id,deptId);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }
    @GetMapping("/getDepartment-empList/{id}")
    public ResponseEntity<DepartmentExpandResponse> getEmployeeByDeptId(@PathVariable String id, @RequestParam String expand) {
        DepartmentExpandResponse response = null;
        try {
            response = employeeService.getDepartmentEmployeeList(id,expand);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(response,HttpStatus.OK);
    }
    @DeleteMapping("/delete-department/{id}")
        public ResponseEntity<String> deleteDepartment(@PathVariable int id){
            String resp = employeeService.deleteDepartment(id);
            return new ResponseEntity<>("Department deleted successfully", HttpStatus.OK);
        }
}
