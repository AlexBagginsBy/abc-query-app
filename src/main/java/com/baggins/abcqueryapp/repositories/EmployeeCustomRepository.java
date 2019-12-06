package com.baggins.abcqueryapp.repositories;

import com.baggins.abcqueryapp.dao.Employee;

import java.time.LocalDate;
import java.util.List;

public interface EmployeeCustomRepository {

    List<Employee> findAllByHireDate(LocalDate hiredFrom, LocalDate hiredBefore);

}
