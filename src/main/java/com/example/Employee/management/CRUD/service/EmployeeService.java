package com.example.Employee.management.CRUD.service;

import com.example.Employee.management.CRUD.entity.EmployeeDTO;

public interface EmployeeService {

    void saveEmployee(EmployeeDTO employeeDTO);

    void updateEmployee(EmployeeDTO employeeDTO);

    <T> T getAllEmployees(Integer pageNumber, Integer pageSize, String searchValue);

    void deleteEmployee(String employeeId);

    EmployeeDTO getEmployeeView(String employeeId);
}
