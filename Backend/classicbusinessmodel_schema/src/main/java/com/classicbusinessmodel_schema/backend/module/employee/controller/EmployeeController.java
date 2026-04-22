package com.classicbusinessmodel_schema.backend.module.employee.controller;

import com.classicbusinessmodel_schema.backend.common.ApiResponse;
import com.classicbusinessmodel_schema.backend.module.employee.dto.requestDto.EmployeeRequestDTO;
import com.classicbusinessmodel_schema.backend.module.employee.dto.responseDto.EmployeeResponseDTO;
import com.classicbusinessmodel_schema.backend.module.employee.service.EmployeeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
import java.util.List;

@Tag(name = "Employee", description = "APIs for managing employees")
@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @Operation(summary = "Create a new employee", description = "Adds a new employee record to the system")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "201", description = "Employee created successfully"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Invalid input data")
    })
    @PostMapping
    public ApiResponse<EmployeeResponseDTO> createEmployee(@Valid @RequestBody EmployeeRequestDTO dto) {
        return new ApiResponse<>(

                201,
                "Employee created successfully",
                employeeService.createEmployee(dto)
        );
    }

    @Operation(summary = "Get all employees", description = "Retrieves a list of all employees")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Employees fetched successfully")
    })
    @GetMapping
    public ApiResponse<Page<EmployeeResponseDTO>> getAllEmployees(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return new ApiResponse<>(
                200,
                "Employees fetched successfully",
                employeeService.getAllEmployees(page, size)
        );
    }

    @Operation(summary = "Get employee by ID", description = "Retrieves a single employee by their employee number")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Employee fetched successfully"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Employee not found")
    })
    @GetMapping("/{employeeNumber}")
    public ApiResponse<EmployeeResponseDTO> getEmployee(
            @Parameter(description = "Employee number", required = true)
            @PathVariable Integer employeeNumber) {
        return new ApiResponse<>(
                200,
                "Employee fetched successfully",
                employeeService.getEmployeeById(employeeNumber)
        );
    }

    @Operation(summary = "Update an employee", description = "Updates the details of an existing employee")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Employee updated successfully"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Invalid input data"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Employee not found")
    })
    @PutMapping("/{employeeNumber}")
    public ApiResponse<EmployeeResponseDTO> updateEmployee(
            @Parameter(description = "Employee number", required = true)
            @PathVariable Integer employeeNumber,
            @Valid @RequestBody EmployeeRequestDTO dto) {
        return new ApiResponse<>(
                200,
                "Employee updated successfully",
                employeeService.updateEmployee(employeeNumber, dto)
        );
    }

    @Operation(summary = "Get manager of an employee", description = "Retrieves the manager of the specified employee")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Manager fetched successfully"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Employee or manager not found")
    })
    @GetMapping("/{employeeNumber}/manager")
    public ApiResponse<EmployeeResponseDTO> getManager(
            @Parameter(description = "Employee number", required = true)
            @PathVariable Integer employeeNumber) {
        return new ApiResponse<>(
                200,
                "Manager fetched successfully",
                employeeService.getManager(employeeNumber)
        );
    }

    @Operation(summary = "Get subordinates of an employee", description = "Retrieves all employees who report to the specified employee")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Subordinates fetched successfully"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "No subordinates found")
    })
    @GetMapping("/{employeeNumber}/subordinates")
    public ApiResponse<List<EmployeeResponseDTO>> getSubordinates(
            @Parameter(description = "Employee number", required = true)
            @PathVariable Integer employeeNumber) {
        return new ApiResponse<>(
                200,
                "Subordinates fetched successfully",
                employeeService.getSubordinates(employeeNumber)
        );
    }
}