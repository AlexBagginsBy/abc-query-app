package com.baggins.abcqueryapp.repositories.implementations;

import com.baggins.abcqueryapp.dao.Employee;
import com.baggins.abcqueryapp.repositories.EmployeeCustomRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.time.LocalDate;
import java.util.List;

@Repository
@Transactional(readOnly = true)
public class EmployeeRepositoryImpl implements EmployeeCustomRepository {

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public List<Employee> findAllByHireDate(LocalDate hiredFrom, LocalDate hiredBefore) {
        if (hiredFrom == null) {
            hiredFrom = LocalDate.of(2019, 01, 01);
        }

        if (hiredBefore == null) {
            hiredBefore = LocalDate.now();
        }

        if (hiredBefore.isBefore(hiredFrom)) {
            throw new IllegalArgumentException("Invalid parameters. Param 'hiredBefore' should be not greater then 'hiredFrom' ");
        }

        Query query = entityManager.createNativeQuery("SELECT * FROM employees WHERE hire_date BETWEEN ? AND ?", Employee.class);
        query.setParameter(1, hiredFrom + "%");
        query.setParameter(2, hiredBefore + "%");

        return query.getResultList();
    }
}
