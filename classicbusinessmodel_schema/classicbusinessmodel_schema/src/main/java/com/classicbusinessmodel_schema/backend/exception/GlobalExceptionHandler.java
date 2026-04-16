package com.classicbusinessmodel_schema.backend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    //  Resource Not Found (404)
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleNotFound(ResourceNotFoundException ex) {

        return buildResponse(ex.getMessage(), "Not Found", HttpStatus.NOT_FOUND);
    }

    // Bad Request (400)
    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<Map<String, Object>> handleBadRequest(BadRequestException ex) {

        return buildResponse(ex.getMessage(), "Bad Request", HttpStatus.BAD_REQUEST);
    }

    //  Invalid Data (400)
    @ExceptionHandler(InvalidDataException.class)
    public ResponseEntity<Map<String, Object>> handleInvalidData(InvalidDataException ex) {

        return buildResponse(ex.getMessage(), "Invalid Data", HttpStatus.BAD_REQUEST);
    }

    //  Resource Already Exists (409)
    @ExceptionHandler(ResourceAlreadyExistsException.class)
    public ResponseEntity<Map<String, Object>> handleAlreadyExists(ResourceAlreadyExistsException ex) {

        return buildResponse(ex.getMessage(), "Conflict", HttpStatus.CONFLICT);
    }

    //  Order Processing Error (400)
    @ExceptionHandler(OrderProcessingException.class)
    public ResponseEntity<Map<String, Object>> handleOrderProcessing(OrderProcessingException ex) {

        return buildResponse(ex.getMessage(), "Order Processing Error", HttpStatus.BAD_REQUEST);
    }

    //  Payment Failed (400)
    @ExceptionHandler(PaymentFailedException.class)
    public ResponseEntity<Map<String, Object>> handlePayment(PaymentFailedException ex) {

        return buildResponse(ex.getMessage(), "Payment Failed", HttpStatus.BAD_REQUEST);
    }

    //  Insufficient Stock (400)
    @ExceptionHandler(InsufficientStockException.class)
    public ResponseEntity<Map<String, Object>> handleStock(InsufficientStockException ex) {

        return buildResponse(ex.getMessage(), "Insufficient Stock", HttpStatus.BAD_REQUEST);
    }

    //  Database Error (500)
    @ExceptionHandler(DatabaseException.class)
    public ResponseEntity<Map<String, Object>> handleDatabase(DatabaseException ex) {

        return buildResponse(ex.getMessage(), "Database Error", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    //  General Exception (fallback - 500)
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleGeneral(Exception ex) {

        return buildResponse(ex.getMessage(), "Internal Server Error", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    //  COMMON METHOD (Cleaner Code)
    private ResponseEntity<Map<String, Object>> buildResponse(String message, String error, HttpStatus status) {

        Map<String, Object> response = new HashMap<>();
        response.put("timestamp", LocalDateTime.now());
        response.put("status", status.value());
        response.put("error", error);
        response.put("message", message);

        return new ResponseEntity<>(response, status);
    }
}