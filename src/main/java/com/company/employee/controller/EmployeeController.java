package com.company.employee.controller;

import com.company.employee.dto.EmployeeDetailDTO;
import com.company.employee.dto.EmployeeSummaryDTO;
import com.company.employee.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/employees")
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;

    /**
     * GET /api/employees/filter
     * Filters: score, reviewDate, departments (contains), projects (contains)
     */
    @GetMapping("/filter")
    public List<EmployeeSummaryDTO> getFilteredEmployees(
            @RequestParam(required = false) Double score,
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate reviewDate,
            @RequestParam(required = false) List<String> departments,
            @RequestParam(required = false) List<String> projects
    ) {
        return employeeService.getFilteredEmployees(score, reviewDate, departments, projects);
    }

    /**
     * GET /api/employees/{id}
     * Full employee details with department, projects, and last 3 performance reviews
     */
    @GetMapping("/{id}")
    public EmployeeDetailDTO getEmployeeById(@PathVariable Long id) {
        return employeeService.getEmployeeDetailsById(id);
    }
}
