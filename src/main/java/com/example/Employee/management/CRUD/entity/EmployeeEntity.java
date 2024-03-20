package com.example.Employee.management.CRUD.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.util.Date;

@Entity
@Data
@SuperBuilder
@Table(name = "employees")
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "employeeid", nullable = false)
    private String employeeId;

    @Column(name = "firstname", length = 50, nullable = false)
    private String firstName;

    @Column(name = "lastname", length = 50, nullable = false)
    private String lastName;

    @Column(name = "dateofbirth", nullable = false)
    private Date dateOfBirth;

    @Column(name = "gender", length = 10, nullable = false)
    private String gender;

    @Email
    @Column(name = "email", length = 50, nullable = false)
    private String email;

    @Pattern(regexp = "[0-9]{10}", message = "Mobile number must be 10 digits")
    @Column(name = "phonenumber", length = 15, nullable = false)
    private String phoneNumber;

    @Column(name = "address", length = 255)
    private String address;

    @Column(name = "department", length = 50, nullable = false)
    private String department;

    @Column(name = "position", length = 50, nullable = false)
    private String position;

    @Column(name = "salary", nullable = false)
    private double salary;

    @Column(name = "modifiedat")
    private Date modifiedAt;

}