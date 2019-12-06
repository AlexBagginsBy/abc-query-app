package com.baggins.abcqueryapp.repositories;

import com.baggins.abcqueryapp.dao.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, Long>, EmployeeCustomRepository {

}
