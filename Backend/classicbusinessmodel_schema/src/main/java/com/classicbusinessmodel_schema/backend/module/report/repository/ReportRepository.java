package com.classicbusinessmodel_schema.backend.module.report.repository;

import com.classicbusinessmodel_schema.backend.entity.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;

public interface ReportRepository extends JpaRepository<Customer, Integer> {

    interface CustomerExposureProjection {
        Integer getCustomerNumber();
        String getCustomerName();
        BigDecimal getCreditLimit();
        BigDecimal getTotalOrderValue();
    }

    interface SalesByCountryProjection {
        String getCountry();
        BigDecimal getTotalSales();
    }

    interface SalesByEmployeeProjection {
        Integer getEmployeeNumber();
        String getEmployeeName();
        BigDecimal getTotalSales();
    }

    interface MonthlyRevenueProjection {
        Integer getYear();
        Integer getMonth();
        BigDecimal getRevenue();
    }

    @Query("SELECT c.customerNumber as customerNumber, " +
           "c.customerName as customerName, " +
           "c.creditLimit as creditLimit, " +
           "COALESCE(SUM(od.quantityOrdered * od.priceEach), 0.0) as totalOrderValue " +
           "FROM Customer c " +
           "LEFT JOIN c.orders o " +
           "LEFT JOIN o.orderDetails od " +
           "GROUP BY c.customerNumber, c.customerName, c.creditLimit")
    Page<CustomerExposureProjection> getCustomerExposure(Pageable pageable);

    @Query("SELECT c.customerNumber as customerNumber, " +
           "c.customerName as customerName, " +
           "c.creditLimit as creditLimit, " +
           "COALESCE(SUM(od.quantityOrdered * od.priceEach), 0.0) as totalOrderValue " +
           "FROM Customer c " +
           "JOIN c.orders o " +
           "JOIN o.orderDetails od " +
           "WHERE c.creditLimit > 0 " +
           "GROUP BY c.customerNumber, c.customerName, c.creditLimit " +
           "HAVING SUM(od.quantityOrdered * od.priceEach) > c.creditLimit")
    Page<CustomerExposureProjection> getHighRiskCustomers(Pageable pageable);

    @Query("SELECT COALESCE(SUM(od.quantityOrdered * od.priceEach), 0.0) " +
           "FROM OrderDetails od " +
           "WHERE od.order.orderNumber = :orderNumber")
    BigDecimal getOrderValue(@Param("orderNumber") Integer orderNumber);

    @Query("SELECT c.country as country, " +
           "COALESCE(SUM(od.quantityOrdered * od.priceEach), 0.0) as totalSales " +
           "FROM Customer c " +
           "JOIN c.orders o " +
           "JOIN o.orderDetails od " +
           "GROUP BY c.country")
    Page<SalesByCountryProjection> getSalesByCountry(Pageable pageable);

    @Query("SELECT e.employeeNumber as employeeNumber, " +
           "CONCAT(e.firstName, ' ', e.lastName) as employeeName, " +
           "COALESCE(SUM(od.quantityOrdered * od.priceEach), 0.0) as totalSales " +
           "FROM Customer c " +
           "JOIN c.salesRep e " +
           "JOIN c.orders o " +
           "JOIN o.orderDetails od " +
           "GROUP BY e.employeeNumber, e.firstName, e.lastName")
    Page<SalesByEmployeeProjection> getSalesByEmployee(Pageable pageable);

    @Query("SELECT YEAR(o.orderDate) as year, " +
           "MONTH(o.orderDate) as month, " +
           "COALESCE(SUM(od.quantityOrdered * od.priceEach), 0.0) as revenue " +
           "FROM Orders o " +
           "JOIN o.orderDetails od " +
           "WHERE o.orderDate IS NOT NULL " +
           "GROUP BY YEAR(o.orderDate), MONTH(o.orderDate)")
    Page<MonthlyRevenueProjection> getMonthlyRevenue(Pageable pageable);
}
