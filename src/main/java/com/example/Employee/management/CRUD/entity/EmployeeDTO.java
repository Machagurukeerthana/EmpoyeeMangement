package com.example.Employee.management.CRUD.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class EmployeeDTO implements Serializable {
    private String employeeId;

    private String firstName;

    private String lastName;

    private Date dateOfBirth;

    private String gender;

    private String email;

    private String phoneNumber;

    private String address;

    private String department;

    private String position;

    private Double salary;

    private Date modifiedAt;
}
