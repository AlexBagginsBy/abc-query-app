package com.baggins.abcqueryapp.repositories;

import com.baggins.abcqueryapp.dao.Employee;

import java.time.LocalDate;
import java.util.List;

public interface EmployeeCustomRepository {

    List<Employee> findAllByHireDateRange(LocalDate hiredFrom, LocalDate hiredBefore);

}
