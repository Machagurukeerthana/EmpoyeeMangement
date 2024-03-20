package com.example.Employee.management.CRUD.service;

import com.example.Employee.management.CRUD.PaginationResponse;
import com.example.Employee.management.CRUD.StringUtil;
import com.example.Employee.management.CRUD.entity.EmployeeDTO;
import com.example.Employee.management.CRUD.entity.EmployeeEntity;
import com.example.Employee.management.CRUD.repository.EmployeeRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.example.Employee.management.CRUD.EndpointConstants.EMP;


@Service("EmployeeServiceImpl")
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final EntityManager entityManager;
    private final SequenceService sequenceService;


    @Autowired
    public EmployeeServiceImpl(EmployeeRepository employeeRepository, EntityManager entityManager, SequenceService sequenceService) {
        this.employeeRepository = employeeRepository;
        this.entityManager = entityManager;
        this.sequenceService = sequenceService;

    }


    @Override
    @Transactional
    public void saveEmployee(EmployeeDTO employeeDTO) {
        String sequenceName = "employee_sequence";
        if (!sequenceService.sequenceExists(sequenceName)) {
            sequenceService.createEmployeeSequence();
        }

        long nextSequenceValue = sequenceService.getNextSequenceValue(sequenceName);
        String employeeId = EMP + nextSequenceValue;
        employeeDTO.setEmployeeId(employeeId);

        EmployeeEntity employeeEntity = new EmployeeEntity();
        BeanUtils.copyProperties(employeeDTO, employeeEntity);

        employeeRepository.save(employeeEntity);
    }

    @Override
    @Transactional
    public void updateEmployee(EmployeeDTO employeeDTO) {
        Optional<EmployeeEntity> optionalEmployeeEntity = employeeRepository.findById(employeeDTO.getEmployeeId());
        if (optionalEmployeeEntity.isPresent()) {
            EmployeeEntity existingEmployee = optionalEmployeeEntity.get();
            EmployeeEntity employeeEntity = EmployeeEntity.builder()
                    .employeeId(existingEmployee.getEmployeeId())
                    .firstName(employeeDTO.getFirstName() != null ? employeeDTO.getFirstName() : existingEmployee.getFirstName())
                    .lastName(employeeDTO.getLastName() != null ? employeeDTO.getLastName() : existingEmployee.getLastName())
                    .dateOfBirth(employeeDTO.getDateOfBirth() != null ? employeeDTO.getDateOfBirth() : existingEmployee.getDateOfBirth())
                    .department(employeeDTO.getDepartment() != null ? employeeDTO.getDepartment() : existingEmployee.getDepartment())
                    .phoneNumber(employeeDTO.getPhoneNumber() != null ? employeeDTO.getPhoneNumber() : existingEmployee.getPhoneNumber())
                    .email(employeeDTO.getEmail() != null ? employeeDTO.getEmail() : existingEmployee.getEmail())
                    .address(employeeDTO.getAddress() != null ? employeeDTO.getAddress() : existingEmployee.getAddress())
                    .gender(employeeDTO.getGender() != null ? employeeDTO.getGender() : existingEmployee.getGender())
                    .salary(employeeDTO.getSalary() != null ? employeeDTO.getSalary() : existingEmployee.getSalary())
                    .modifiedAt(employeeDTO.getModifiedAt())
                    .build();
            employeeRepository.save(employeeEntity);
        } else {
            // Handle case where employee with given ID does not exist
            throw new RuntimeException("Employee with ID " + employeeDTO.getEmployeeId() + " not found");
        }
    }

    @Override
    public <T> T getAllEmployees(Integer pageNumber, Integer pageSize, String searchValue) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<EmployeeDTO> criteriaQuery = criteriaBuilder.createQuery(EmployeeDTO.class);
        Root<EmployeeEntity> queryRoot = criteriaQuery.from(EmployeeEntity.class);
        CriteriaQuery<Long> countQuery = criteriaBuilder.createQuery(Long.class);
        Root<EmployeeEntity> countRoot = countQuery.from(EmployeeEntity.class);
        List<Predicate> dataPredicates = new ArrayList<>();
        List<Predicate> countPredicates = new ArrayList<>();
        if (!StringUtil.isNullOrEmpty(searchValue)) {
            searchValue = searchValue.toLowerCase();
            Predicate[] dataSearchPredicates = new Predicate[4]; // Array to store search predicates for data
            Predicate[] countSearchPredicates = new Predicate[4]; // Array to store search predicates for count

            // Construct search predicates for dataPredicates
            dataSearchPredicates[0] = criteriaBuilder.like(criteriaBuilder.lower(queryRoot.get("department")), '%' + searchValue + '%');
            dataSearchPredicates[1] = criteriaBuilder.like(criteriaBuilder.lower(queryRoot.get("firstName")), '%' + searchValue + '%');
            dataSearchPredicates[2] = criteriaBuilder.like(criteriaBuilder.lower(queryRoot.get("phoneNumber")), '%' + searchValue + '%');
            dataSearchPredicates[3] = criteriaBuilder.like(criteriaBuilder.lower(queryRoot.get("employeeId")), '%' + searchValue + '%');

            // Construct search predicates for countPredicates
            countSearchPredicates[0] = criteriaBuilder.like(criteriaBuilder.lower(countRoot.get("department")), '%' + searchValue + '%');
            countSearchPredicates[1] = criteriaBuilder.like(criteriaBuilder.lower(countRoot.get("firstName")), '%' + searchValue + '%');
            countSearchPredicates[2] = criteriaBuilder.like(criteriaBuilder.lower(countRoot.get("phoneNumber")), '%' + searchValue + '%');
            countSearchPredicates[3] = criteriaBuilder.like(criteriaBuilder.lower(countRoot.get("employeeId")), '%' + searchValue + '%');

            // Add search predicates to dataPredicates
            dataPredicates.add(criteriaBuilder.or(dataSearchPredicates));

            // Add search predicates to countPredicates
            countPredicates.add(criteriaBuilder.or(countSearchPredicates));
        }

        criteriaQuery.select(criteriaBuilder.construct(EmployeeDTO.class,
                        queryRoot.get("employeeId").alias("employeeId"),
                        queryRoot.get("department").alias("department"),
                        queryRoot.get("firstName").alias("firstName"),
                        queryRoot.get("dateOfBirth").alias("dateOfBirth"),
                        queryRoot.get("gender").alias("gender"),
                        queryRoot.get("lastName").alias("lastName"),
                        queryRoot.get("phoneNumber").alias("phoneNumber"),
                        queryRoot.get("email").alias("email"),
                        queryRoot.get("address").alias("address"),
                        queryRoot.get("salary").alias("salary"),
                        queryRoot.get("modifiedAt").alias("modifiedAt")))
                .where(dataPredicates.toArray(new Predicate[]{}))
                .orderBy(criteriaBuilder.desc(queryRoot.get("modifiedAt")));
        Query dataQuery = entityManager.createQuery(criteriaQuery);
        countQuery.select(criteriaBuilder.count(countRoot));
        countQuery.where(countPredicates.toArray(new Predicate[]{}));
        Query count = entityManager.createQuery(countQuery);
        PaginationResponse paginationResponse = new PaginationResponse();
        if (pageSize != null && pageNumber != null) {
            dataQuery.setMaxResults(pageSize);
            dataQuery.setFirstResult((pageNumber) * pageSize);
            paginationResponse.setTotalRecords((Long) count.getSingleResult());
        }
        paginationResponse.setData(dataQuery.getResultList());
        return (T) paginationResponse;
    }

    @Override
    public void deleteEmployee(String employeeId) {
        employeeRepository.deleteEmployee(employeeId);
    }
}

