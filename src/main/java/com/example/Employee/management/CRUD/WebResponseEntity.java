package com.example.Employee.management.CRUD;


import lombok.Data;
import org.json.simple.JSONObject;

import java.util.List;

@Data
public class WebResponseEntity<T> {

    private long totalRecords;
    private int status;
    private boolean flag;
    private String message;
    private Object response;
    private JSONObject otherInfo;

    public WebResponseEntity() {
        super();
    }

    public WebResponseEntity(int status, String message) {
        super();
        this.status = status;
        this.message = message;
    }

    public WebResponseEntity(boolean flag, Object response, JSONObject otherInfo) {
        super();
        this.flag = flag;
        this.response = response;
        this.otherInfo = otherInfo;
    }

    public WebResponseEntity(boolean flag, String message) {
        super();
        this.flag = flag;
        this.message = message;
    }

    public WebResponseEntity(boolean flag, Object response) {
        super();
        this.flag = flag;
        this.response = response;
    }

    public WebResponseEntity(boolean flag, String message, Object response) {
        super();
        this.flag = flag;
        this.message = message;
        this.response = response;
    }

    public WebResponseEntity(int status, boolean flag, String message, JSONObject otherInfo) {
        super();
        this.status = status;
        this.flag = flag;
        this.message = message;
        this.otherInfo = otherInfo;
    }

    public WebResponseEntity(int status, boolean flag, String message, Object response, JSONObject otherInfo) {
        super();
        this.status = status;
        this.flag = flag;
        this.message = message;
        this.response = response;
        this.otherInfo = otherInfo;
    }

    public WebResponseEntity(int status, boolean flag, Object response, JSONObject otherInfo) {
        super();
        this.status = status;
        this.flag = flag;
        this.response = response;
        this.otherInfo = otherInfo;
    }

    public WebResponseEntity(boolean flag, int status, Object response, String message) {
        super();
        this.flag = flag;
        this.status = status;
        this.response = response;
        this.message = message;
    }

    public WebResponseEntity(int status, boolean flag, String message) {
        super();
        this.status = status;
        this.flag = flag;
        this.message = message;
    }

    public WebResponseEntity(int status, boolean flag, Object response) {
        super();
        this.status = status;
        this.flag = flag;
        this.response = response;

    }

    public WebResponseEntity(JSONObject otherInfo) {
        super();
        this.otherInfo = otherInfo;
    }

    public WebResponseEntity(Object response) {
        super();
        this.response = response;
    }

    public WebResponseEntity(boolean success, String message, long totalCount, List<T> response) {
        super();
        this.message = message;
        this.totalRecords = totalCount;
        this.response = response;
    }
}
