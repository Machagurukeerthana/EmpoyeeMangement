package com.example.Employee.management.CRUD;

public class EndpointConstants {

    /**
     * API Prefix for Both WEB
     */
    public static final String API_VERSION_1 = "v1";
    public static final String WEB_API_PREFIX = "/web/api/" + API_VERSION_1;

    /**
     * COMMON PATHS
     */
    public static final String DELETE = "/delete";
    public static final String UPDATE = "/update";
    public static final String VIEW = "/view";

    /**
     * Employee Related APIs
     */
    public static final String EMPLOYEES_API = "/employees";
    public static final String MASTER_EMPLOYEES_API = WEB_API_PREFIX + EMPLOYEES_API;
    public static final String MASTER_EMPLOYEE_UPDATE = MASTER_EMPLOYEES_API + UPDATE;
    public static final String MASTER_EMPLOYEE_DELETE = MASTER_EMPLOYEES_API + DELETE;
    public static final String MASTER_EMPLOYEE_VIEW = MASTER_EMPLOYEES_API + VIEW;
    public static final String EMP = "EMP";

}
