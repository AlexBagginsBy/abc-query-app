package com.baggins.abcqueryapp.repositories.implementations;

import com.baggins.abcqueryapp.dao.Employee;
import com.baggins.abcqueryapp.exceptions.IncorrectDateRangeParametersException;
import com.baggins.abcqueryapp.exceptions.UnrecognizedQueryVersionException;
import com.baggins.abcqueryapp.repositories.EmployeeCustomRepository;
import com.baggins.abcqueryapp.repositories.config.FindEmployeesByHireDateRangeConfig;
import org.springframework.beans.factory.annotation.Autowired;
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

    private static final int HIRED_FROM_DEFAULT_YEAR = 1995;
    private static final int HIRED_FROM_DEFAULT_MONTH = 1;
    private static final int HIRED_FROM_DEFAULT_DAY = 1;
    private static final double V1_0 = 1.0;
    private static final double V1_1 = 1.1;

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private FindEmployeesByHireDateRangeConfig config;

    @Override
    public List<Employee> findAllByHireDateRange(LocalDate hiredFrom, LocalDate hiredBefore) {
        hiredFrom = setHiredFromIfEmpty(hiredFrom);
        hiredBefore = setHiredBeforeIfEmpty(hiredBefore);

        validateDateRange(hiredFrom, hiredBefore);

        if (config.getVersion() == V1_0) {
            return getEmployeesByHireDateRangeV10(hiredFrom, hiredBefore);
        } else if (config.getVersion() == V1_1) {
            return getEmployeesByHireDateRangeV11(hiredFrom, hiredBefore);
        }

        throw new UnrecognizedQueryVersionException("Cannot find 'findAllByHireDateRange' method SQL query with version number: '" + config.getVersion() + "';");
    }

    private List<Employee> getEmployeesByHireDateRangeV10(LocalDate hiredFrom, LocalDate hiredBefore) {
        Query query = entityManager.createNativeQuery("SELECT * FROM employees WHERE hire_date BETWEEN ? AND ?", Employee.class);
        query.setParameter(1, hiredFrom + "%");
        query.setParameter(2, hiredBefore + "%");

        return query.getResultList();
    }

    private List<Employee> getEmployeesByHireDateRangeV11(LocalDate hiredFrom, LocalDate hiredBefore) {
        Query query = entityManager.createNativeQuery("SELECT * FROM employees WHERE hire_date >= ? AND hire_date <= ?", Employee.class);
        query.setParameter(1, hiredFrom + "%");
        query.setParameter(2, hiredBefore + "%");

        return query.getResultList();
    }

    private void validateDateRange(LocalDate hiredFrom, LocalDate hiredBefore) {
        if (hiredFrom != null && hiredBefore != null && hiredBefore.isBefore(hiredFrom)) {
            throw new IncorrectDateRangeParametersException("Invalid parameters. Range end date cannot not be greater then the start one.");
        }
    }

    private LocalDate setHiredFromIfEmpty(LocalDate hiredFrom) {
        return setDateIfEmpty(hiredFrom, LocalDate.of(HIRED_FROM_DEFAULT_YEAR, HIRED_FROM_DEFAULT_MONTH, HIRED_FROM_DEFAULT_DAY));
    }

    private LocalDate setHiredBeforeIfEmpty(LocalDate hiredBefore) {
        return setDateIfEmpty(hiredBefore, LocalDate.now());
    }

    private LocalDate setDateIfEmpty(LocalDate hireDate, LocalDate defaultValue) {
        return hireDate != null ? hireDate : defaultValue;
    }


}
