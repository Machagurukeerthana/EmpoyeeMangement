package com.example.Employee.management.CRUD;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class PaginationResponse<T> implements Serializable {

    private List<T> data;
    private long totalRecords;
}
