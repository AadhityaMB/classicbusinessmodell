package com.classicbusinessmodel_schema.backend.module.employee.controller;

import com.classicbusinessmodel_schema.backend.common.ApiResponse;
import com.classicbusinessmodel_schema.backend.module.employee.dto.requestDto.OfficeRequestDTO;
import com.classicbusinessmodel_schema.backend.module.employee.dto.responseDto.EmployeeResponseDTO;
import com.classicbusinessmodel_schema.backend.module.employee.dto.responseDto.OfficeResponseDTO;
import com.classicbusinessmodel_schema.backend.module.employee.service.OfficeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@Tag(name = "Office", description = "APIs for managing offices")
@RestController
@RequestMapping("/api/offices")
public class OfficeController {

    @Autowired
    private OfficeService officeService;

    @Operation(summary = "Create a new office", description = "Adds a new office record to the system")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "201", description = "Office created successfully"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Invalid input data")
    })
    @PostMapping
    public ApiResponse<OfficeResponseDTO> createOffice(@Valid @RequestBody OfficeRequestDTO dto) {
        return new ApiResponse<>(
                201,
                "Office created successfully",
                officeService.createOffice(dto)
        );
    }

    @Operation(summary = "Get all offices", description = "Retrieves a list of all offices")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Offices fetched successfully")
    })
    @GetMapping
    public ApiResponse<List<OfficeResponseDTO>> getAllOffices() {
        return new ApiResponse<>(

                200,
                "Offices fetched successfully",
                officeService.getAllOffices()
        );
    }

    @Operation(summary = "Get office by code", description = "Retrieves a single office by its office code")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Office fetched successfully"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Office not found")
    })
    @GetMapping("/{officeCode}")
    public ApiResponse<OfficeResponseDTO> getOffice(
            @Parameter(description = "Office code", required = true)
            @PathVariable String officeCode) {
        return new ApiResponse<>(

                200,
                "Office fetched successfully",
                officeService.getOfficeByCode(officeCode)
        );
    }

    @Operation(summary = "Get employees by office", description = "Retrieves all employees belonging to the specified office")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Employees fetched successfully"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Office not found or no employees in office")
    })
    @GetMapping("/{officeCode}/employees")
    public ApiResponse<List<EmployeeResponseDTO>> getEmployeesByOffice(
            @Parameter(description = "Office code", required = true)
            @PathVariable String officeCode) {
        return new ApiResponse<>(
                200,
                "Employees in office fetched successfully",
                officeService.getEmployeesByOffice(officeCode)
        );
    }
}