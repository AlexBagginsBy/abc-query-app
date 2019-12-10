package com.baggins.abcqueryapp.repositories.implementations;

import com.baggins.abcqueryapp.dao.Employee;
import com.baggins.abcqueryapp.exceptions.IncorrectDateRangeParametersException;
import com.baggins.abcqueryapp.exceptions.UnrecognizedQueryVersionException;
import com.baggins.abcqueryapp.repositories.EmployeeCustomRepository;
import com.baggins.abcqueryapp.repositories.config.FindEmployeesByHireDateRangeConfig;
import com.baggins.abcqueryapp.utils.ResourceFileReader;
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

    private static final double V1_0 = 1.0;
    private static final double V1_1 = 1.1;

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private FindEmployeesByHireDateRangeConfig config;

    @Autowired
    private ResourceFileReader fileReader;

    @Override
    public List<Employee> findAllByHireDateRange(LocalDate hiredFrom, LocalDate hiredBefore) {
        hiredFrom = setHiredFromIfEmpty(hiredFrom);
        hiredBefore = setHiredBeforeIfEmpty(hiredBefore);

        validateDateRange(hiredFrom, hiredBefore);

        return getEmployeesByHireDateRange(hiredFrom, hiredBefore);

    }

    private List<Employee> getEmployeesByHireDateRange(LocalDate hiredFrom, LocalDate hiredBefore) {
        String queryStringFilePath = getQueryFilePath();
        String queryString = fileReader.getQueryStringFromFile(queryStringFilePath);
        Query query = entityManager.createNativeQuery(queryString, Employee.class);
        query.setParameter(1, hiredFrom + "%");
        query.setParameter(2, hiredBefore + "%");

        return query.getResultList();
    }

    private String getQueryFilePath() {
        String queryStringFilePath = null;

        if (config.getVersion() == V1_0) {
            queryStringFilePath = "queries/find-employees-by-hire-date-range-v10.sql";
        } else if (config.getVersion() == V1_1) {
            queryStringFilePath = "queries/find-employees-by-hire-date-range-v11.sql";
        } else {
            throw new UnrecognizedQueryVersionException("Cannot find 'findAllByHireDateRange' method SQL query with version number: '" + config.getVersion() + "';");
        }

        return queryStringFilePath;
    }

    private void validateDateRange(LocalDate hiredFrom, LocalDate hiredBefore) {
        if (hiredFrom != null && hiredBefore != null && hiredBefore.isBefore(hiredFrom)) {
            throw new IncorrectDateRangeParametersException("Invalid parameters. Range end date cannot not be greater then the start one.");
        }
    }

    private LocalDate setHiredFromIfEmpty(LocalDate hiredFrom) {
        LocalDate defaultHiredFromDate = LocalDate.of(config.getHiredFromDefaultYear(), config.getHiredFromDefaultMonth(), config.getHiredFromDefaultDay());
        return setDateIfEmpty(hiredFrom, defaultHiredFromDate);
    }

    private LocalDate setHiredBeforeIfEmpty(LocalDate hiredBefore) {
        return setDateIfEmpty(hiredBefore, LocalDate.now());
    }

    private LocalDate setDateIfEmpty(LocalDate hireDate, LocalDate defaultValue) {
        return hireDate != null ? hireDate : defaultValue;
    }


}
