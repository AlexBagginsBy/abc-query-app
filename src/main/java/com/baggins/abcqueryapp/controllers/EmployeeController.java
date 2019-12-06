package com.baggins.abcqueryapp.controllers;

import com.baggins.abcqueryapp.dao.Employee;
import com.baggins.abcqueryapp.exceptions.EmployeeNotFoundException;
import com.baggins.abcqueryapp.repositories.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
public class EmployeeController {

    private final EmployeeRepository repository;

    public EmployeeController(@Autowired EmployeeRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/employees")
    List<Employee> getAll() {
        return repository.findAll();
    }

    @GetMapping("/employees/hired")
    List<Employee> getAllByHireDateRange(@Param("hireDateFrom") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate hiredFrom,
                                         @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @Param("hireDateBefore") LocalDate hiredBefore){

        return repository.findAllByHireDateRange(hiredFrom, hiredBefore);
    }

    @PostMapping("/employees")
    Employee create(@RequestBody Employee newEmployee) {
        return repository.save(newEmployee);
    }

    @GetMapping("/employees/{id}")
    Employee getEmployee(@PathVariable Long id) throws EmployeeNotFoundException {
        return repository.findById(id)
                .orElseThrow(() -> new EmployeeNotFoundException(id));
    }

    @PutMapping("/employees/{id}")
    Employee updateEmployee(@RequestBody Employee newEmployee, @PathVariable Long id) {
        return repository.findById(id)
                .map(employee -> {
                    employee.setFirstName(newEmployee.getFirstName());
                    employee.setLastName(newEmployee.getLastName());
                    return repository.save(employee);
                })
                .orElseGet(() -> {
                    newEmployee.setId(id);
                    return repository.save(newEmployee);
                });
    }

    @DeleteMapping("/employees/{id}")
    void deleteEmployee(@PathVariable Long id) {
        repository.deleteById(id);
    }
}
