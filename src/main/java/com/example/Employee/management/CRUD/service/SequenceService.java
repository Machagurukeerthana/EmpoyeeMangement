package com.example.Employee.management.CRUD.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class SequenceService {
    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public void createEmployeeSequence() {
        String sequenceName = "employee_sequence";
        String sql = "CREATE SEQUENCE " + sequenceName;
        entityManager.createNativeQuery(sql).executeUpdate();
    }

    @Transactional
    public long getNextSequenceValue(String sequenceName) {
        String sql = "SELECT nextval('" + sequenceName + "')";
        return ((Number) entityManager.createNativeQuery(sql).getSingleResult()).longValue();
    }

    @Transactional()
    public boolean sequenceExists(String sequenceName) {
        String sql = "SELECT EXISTS (SELECT 1 FROM information_schema.sequences WHERE sequence_name = :sequenceName)";
        return entityManager.createNativeQuery(sql)
                .setParameter("sequenceName", sequenceName)
                .getSingleResult()
                .equals(Boolean.TRUE);
    }
}
