package com.classicbusinessmodel_schema.backend.module.report.controller;

import com.classicbusinessmodel_schema.backend.exception.ResourceNotFoundException;
import com.classicbusinessmodel_schema.backend.module.report.dto.response.OrderValueResponseDTO;
import com.classicbusinessmodel_schema.backend.module.report.service.ReportService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ReportController.class)
@AutoConfigureMockMvc(addFilters = false) // Disable security for unit testing controller logic
class ReportControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ReportService reportService;

    @Test
    void testGetOrderValue_Success() throws Exception {
        OrderValueResponseDTO mockResponse = OrderValueResponseDTO.builder()
                .orderNumber(1001)
                .totalValue(BigDecimal.valueOf(1500.00))
                .build();

        when(reportService.getOrderValue(1001)).thenReturn(mockResponse);

        mockMvc.perform(get("/api/reports/order-value/1001")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(200))
                .andExpect(jsonPath("$.message").value("Order value fetched successfully"))
                .andExpect(jsonPath("$.data.orderNumber").value(1001))
                .andExpect(jsonPath("$.data.totalValue").value(1500.00));
    }

    @Test
    void testGetOrderValue_NotFoundException() throws Exception {
        when(reportService.getOrderValue(9999))
                .thenThrow(new ResourceNotFoundException("Order with order number 9999 not found"));

        mockMvc.perform(get("/api/reports/order-value/9999")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.message").value("Order with order number 9999 not found"))
                .andExpect(jsonPath("$.data").isEmpty());
    }

    @Test
    void testGetOrderValue_TypeMismatchException() throws Exception {
        // Passing a string 'abc' instead of an Integer should throw MethodArgumentTypeMismatchException
        mockMvc.perform(get("/api/reports/order-value/abc")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.message").value("Invalid parameter type"))
                .andExpect(jsonPath("$.data").isEmpty());
    }
}
