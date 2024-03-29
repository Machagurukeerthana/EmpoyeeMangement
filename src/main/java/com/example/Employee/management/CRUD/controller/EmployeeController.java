package com.example.Employee.management.CRUD.controller;

import com.example.Employee.management.CRUD.WebResponseEntity;
import com.example.Employee.management.CRUD.entity.EmployeeDTO;
import com.example.Employee.management.CRUD.repository.EmployeeRepository;
import com.example.Employee.management.CRUD.service.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

import static com.example.Employee.management.CRUD.EndpointConstants.*;

@RestController
@Slf4j
@CrossOrigin
public class EmployeeController {

    private final EmployeeService employeeService;
    private final EmployeeRepository employeeRepository;

    @Autowired
    public EmployeeController(EmployeeService employeeService, EmployeeRepository employeeRepository) {
        this.employeeService = employeeService;
        this.employeeRepository = employeeRepository;
    }

    /**
     * Retrieves a list of employees.
     *
     * @param pageNumber  The page number for pagination (optional).
     * @param pageSize    The page size for pagination (optional).
     * @param searchValue The search value to filter employees by (optional).
     * @return A ResponseEntity containing the list of employees.
     */
    @GetMapping(MASTER_EMPLOYEES_API)
    public <T> ResponseEntity<WebResponseEntity<T>> getEmployee(@RequestParam(required = false, name = "pageNumber") Integer pageNumber,
                                                                @RequestParam(required = false, name = "pageSize") Integer pageSize,
                                                                @RequestParam(required = false, name = "searchValue") String searchValue) {
        log.info("Getting Employees List ::");
        try {
            WebResponseEntity<T> response = new WebResponseEntity<>(0, true,
                    (T) employeeService.getAllEmployees(pageNumber, pageSize, searchValue));
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error While Getting Employees List ::", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Saves the details of an employee.
     *
     * @param employeeDTO The DTO containing the details of the employee to be saved.
     * @return A ResponseEntity indicating the result of the operation.
     */
    @PostMapping(MASTER_EMPLOYEES_API)
    public ResponseEntity<WebResponseEntity<EmployeeDTO>> saveEmployee(@RequestBody EmployeeDTO employeeDTO) {
        log.info("Saving Employee Details :");
        try {
            employeeDTO.setModifiedAt(new Date());
            employeeService.saveEmployee(employeeDTO);
            WebResponseEntity<EmployeeDTO> response = new WebResponseEntity<>(1, true,
                    "Employee Details Saved Successfully");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error While Saving Employee Details ::", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Updates the details of an existing employee.
     *
     * @param employeeDTO The DTO containing the updated details of the employee.
     * @return A ResponseEntity indicating the result of the operation.
     */
    @PutMapping(MASTER_EMPLOYEE_UPDATE)
    public ResponseEntity<WebResponseEntity<EmployeeDTO>> updateEmployee(@RequestBody EmployeeDTO employeeDTO) {
        log.info("Update Employee Details :");
        try {
            employeeDTO.setModifiedAt(new Date());
            employeeService.updateEmployee(employeeDTO);
            WebResponseEntity<EmployeeDTO> response = new WebResponseEntity<>(2, true,
                    "Employee Details Updated Successfully");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error While Updating Employee Details ::", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Deletes an employee from the employee list.
     *
     * @param employeeId The ID of the employee to be deleted (optional).
     * @return A ResponseEntity indicating the result of the operation.
     */
    @DeleteMapping(MASTER_EMPLOYEE_DELETE)
    public ResponseEntity<WebResponseEntity<String>> deleteEmployee(@RequestParam(required = false) String employeeId) {
        log.info("Deleting employee from employeeList: {}", employeeId);
        try {
            employeeService.deleteEmployee(employeeId);
            WebResponseEntity<String> response = new WebResponseEntity<>(true, "Employee Removed from the Employee list");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("Error while deleting employee from employeeList: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new WebResponseEntity<>(false, "Failed to delete employee from the employee list"));
        }
    }

}
