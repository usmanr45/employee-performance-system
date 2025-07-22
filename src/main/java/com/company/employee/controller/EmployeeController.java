package com.company.employee.controller;

import com.company.employee.dto.EmployeeDetailDTO;
import com.company.employee.dto.EmployeeSummaryDTO;
import com.company.employee.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/employees")
@RequiredArgsConstructor
public class EmployeeController {

    private static final Logger logger = LoggerFactory.getLogger(EmployeeController.class);

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
        logger.info("GET /api/employees - Filtering with score={}, reviewDate={}, departments={}, projects={}",
                score, reviewDate, departments, projects);

        List<EmployeeSummaryDTO> result = employeeService.getFilteredEmployees(score, reviewDate, departments, projects);

        logger.info("Filtered employees count: {}", result.size());
        return result;
    }

    /**
     * GET /api/employees/{id}
     * Full employee details with department, projects, and last 3 performance reviews
     */
    @GetMapping("/{id}")
    public EmployeeDetailDTO getEmployeeById(@PathVariable Long id) {
        logger.info("GET /api/employees/{} - Fetching employee detail", id);

        EmployeeDetailDTO dto = employeeService.getEmployeeDetailsById(id);

        logger.info("Employee '{}' found with {} projects and {} reviews",
                dto.getName(),
                dto.getProjects() != null ? dto.getProjects().size() : 0,
                dto.getLast3Reviews() != null ? dto.getLast3Reviews().size() : 0);

        return dto;
    }
}
