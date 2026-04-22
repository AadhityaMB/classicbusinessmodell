import { ModuleDefinition } from '../models/module.models';

export const MODULES: ModuleDefinition[] = [
  {
    id: 'report',
    memberName: 'Aadhi',
    moduleName: 'Report',
    tagline: 'Reports & Analytics',
    description: 'Business reporting module.',
    theme: '#1f3a5f',
    gradient: 'linear-gradient(135deg, #f8f4ea 0%, #edf2f7 58%, #d6e1ef 100%)',
    credentials: { username: 'aadhi', password: '1234' },
    loginCheckEndpoint: '/api/reports/customer-exposure',
    resources: [
      {
        id: 'report',
        name: 'Report',
        summary: 'Business reports.',
        accent: '#14b8a6',
        actions: [
          {
            id: 'customer-exposure',
            label: 'Customer Exposure',
            description: 'View customer exposure report.',
            endpoint: 'GET /api/reports/customer-exposure',
            method: 'GET',
            tone: 'primary',
            autoLoad: true
          },
          {
            id: 'order-value',
            label: 'Order Value Report',
            description: 'Search order value by order number.',
            endpoint: 'GET /api/reports/order-value/{orderNumber}',
            method: 'GET',
            tone: 'neutral'
          },
          {
            id: 'sales-by-country',
            label: 'Sales By Country',
            description: 'View sales by country.',
            endpoint: 'GET /api/reports/sales-by-country',
            method: 'GET',
            tone: 'primary',
            autoLoad: true
          },
          {
            id: 'sales-by-employee',
            label: 'Sales By Employee',
            description: 'View sales by employee.',
            endpoint: 'GET /api/reports/sales-by-employee',
            method: 'GET',
            tone: 'neutral',
            autoLoad: true
          },
          {
            id: 'monthly-revenue',
            label: 'Monthly Revenue',
            description: 'View monthly revenue.',
            endpoint: 'GET /api/reports/monthly-revenue',
            method: 'GET',
            tone: 'success',
            autoLoad: true
          },
          {
            id: 'high-risk-customers',
            label: 'High Risk Customers',
            description: 'View high risk customers.',
            endpoint: 'GET /api/reports/high-risk-customers',
            method: 'GET',
            tone: 'warning',
            autoLoad: true
          }
        ]
      }
    ]
  },
  {
    id: 'customer',
    memberName: 'Priya',
    moduleName: 'Customer & Payments',
    tagline: 'Customer & Payments',
    description: 'Customer module.',
    theme: '#2c4a6b',
    gradient: 'linear-gradient(135deg, #fbf7ef 0%, #eef3f8 55%, #dae5f2 100%)',
    credentials: { username: 'priya', password: '1234' },
    loginCheckEndpoint: '/api/customers',
    resources: [
      {
        id: 'customer',
        name: 'Customer',
        summary: 'Customer records.',
        accent: '#3b82f6',
        actions: [
          {
            id: 'create-customer',
            label: 'Add Customer',
            description: 'Open the customer creation form.',
            endpoint: 'POST /api/customers',
            method: 'POST',
            tone: 'success',
            submitLabel: 'Add Customer',
            successMessage: 'Customer added successfully',
            formFields: [
              { key: 'customerNumber', label: 'Customer Number', type: 'number', required: true, placeholder: 'Enter customer number' },
              { key: 'customerName', label: 'Customer Name', type: 'text', required: true, placeholder: 'Enter customer name', validation: { notBlank: 'Customer name is required', maxLength: { value: 50 } } },
              { key: 'contactFirstName', label: 'Contact First Name', type: 'text', required: true, placeholder: 'Enter first name', validation: { notBlank: 'Contact first name is required', maxLength: { value: 50 } } },
              { key: 'contactLastName', label: 'Contact Last Name', type: 'text', required: true, placeholder: 'Enter last name', validation: { notBlank: 'Contact last name is required', maxLength: { value: 50 } } },
              { key: 'phone', label: 'Phone', type: 'text', required: true, placeholder: 'Enter phone', validation: { notBlank: 'Phone is required', maxLength: { value: 50 } } },
              { key: 'addressLine1', label: 'Address Line 1', type: 'text', required: true, placeholder: 'Enter address line 1', validation: { notBlank: 'Address line 1 is required', maxLength: { value: 50 } } },
              { key: 'addressLine2', label: 'Address Line 2', type: 'text', placeholder: 'Enter address line 2', validation: { maxLength: { value: 50 } } },
              { key: 'city', label: 'City', type: 'text', required: true, placeholder: 'Enter city', validation: { notBlank: 'City is required', maxLength: { value: 50 } } },
              { key: 'state', label: 'State', type: 'text', placeholder: 'Enter state', validation: { maxLength: { value: 50 } } },
              { key: 'postalCode', label: 'Postal Code', type: 'text', placeholder: 'Enter postal code', validation: { maxLength: { value: 50 } } },
              { key: 'country', label: 'Country', type: 'text', required: true, placeholder: 'Enter country', validation: { notBlank: 'Country is required', maxLength: { value: 50 } } },
              { key: 'creditLimit', label: 'Credit Limit', type: 'number', step: '0.01', placeholder: 'Enter credit limit', validation: { min: { value: 0, message: 'Credit limit cannot be negative' } } },
              { key: 'salesRepEmployeeNumber', label: 'Sales Rep Employee Number', type: 'number', placeholder: 'Enter employee number' }
            ]
          },
          {
            id: 'get-all-customers',
            label: 'Get All Customers',
            description: 'Open customer list.',
            endpoint: 'GET /api/customers',
            method: 'GET',
            tone: 'primary',
            submitLabel: 'Fetch Customers',
            queryFields: [
              { key: 'page', label: 'Page', type: 'number', required: true, placeholder: 'Enter page number', defaultValue: '0' },
              { key: 'size', label: 'Size', type: 'number', required: true, placeholder: 'Enter page size', defaultValue: '10' }
            ]
          },
          {
            id: 'get-customer-by-id',
            label: 'Customer Details',
            description: 'Search customer by number.',
            endpoint: 'GET /api/customers/{customerNumber}',
            method: 'GET',
            tone: 'neutral'
          },
          {
            id: 'update-customer',
            label: 'Update Customer',
            description: 'Open customer update form.',
            endpoint: 'PUT /api/customers/{customerNumber}',
            method: 'PUT',
            tone: 'warning',
            submitLabel: 'Update Customer',
            successMessage: 'Customer updated successfully',
            formFields: [
              { key: 'customerNumber', label: 'Customer Number', type: 'number', required: true, placeholder: 'Enter customer number' },
              { key: 'customerName', label: 'Customer Name', type: 'text', required: true, placeholder: 'Enter customer name', validation: { notBlank: 'Customer name is required', maxLength: { value: 50 } } },
              { key: 'contactFirstName', label: 'Contact First Name', type: 'text', required: true, placeholder: 'Enter first name', validation: { notBlank: 'Contact first name is required', maxLength: { value: 50 } } },
              { key: 'contactLastName', label: 'Contact Last Name', type: 'text', required: true, placeholder: 'Enter last name', validation: { notBlank: 'Contact last name is required', maxLength: { value: 50 } } },
              { key: 'phone', label: 'Phone', type: 'text', required: true, placeholder: 'Enter phone', validation: { notBlank: 'Phone is required', maxLength: { value: 50 } } },
              { key: 'addressLine1', label: 'Address Line 1', type: 'text', required: true, placeholder: 'Enter address line 1', validation: { notBlank: 'Address line 1 is required', maxLength: { value: 50 } } },
              { key: 'addressLine2', label: 'Address Line 2', type: 'text', placeholder: 'Enter address line 2', validation: { maxLength: { value: 50 } } },
              { key: 'city', label: 'City', type: 'text', required: true, placeholder: 'Enter city', validation: { notBlank: 'City is required', maxLength: { value: 50 } } },
              { key: 'state', label: 'State', type: 'text', placeholder: 'Enter state', validation: { maxLength: { value: 50 } } },
              { key: 'postalCode', label: 'Postal Code', type: 'text', placeholder: 'Enter postal code', validation: { maxLength: { value: 50 } } },
              { key: 'country', label: 'Country', type: 'text', required: true, placeholder: 'Enter country', validation: { notBlank: 'Country is required', maxLength: { value: 50 } } },
              { key: 'creditLimit', label: 'Credit Limit', type: 'number', step: '0.01', placeholder: 'Enter credit limit', validation: { min: { value: 0, message: 'Credit limit cannot be negative' } } },
              { key: 'salesRepEmployeeNumber', label: 'Sales Rep Employee Number', type: 'number', placeholder: 'Enter employee number' }
            ]
          },
          {
            id: 'delete-customer',
            label: 'Delete Customer',
            description: 'Delete customer after confirmation.',
            endpoint: 'DELETE /api/customers/{customerNumber}',
            method: 'DELETE',
            tone: 'warning',
            submitLabel: 'Delete Customer',
            successMessage: 'Customer deleted successfully'
          },
          {
            id: 'get-credit-limit',
            label: 'View Credit Limit',
            description: 'Search credit limit by customer number.',
            endpoint: 'GET /api/customers/{customerNumber}/credit-limit',
            method: 'GET',
            tone: 'neutral'
          },
          {
            id: 'update-credit-limit',
            label: 'Update Credit Limit',
            description: 'Open credit limit update form.',
            endpoint: 'PUT /api/customers/{customerNumber}/credit-limit',
            method: 'PUT',
            tone: 'success',
            submitLabel: 'Update Credit Limit',
            successMessage: 'Credit limit updated successfully',
            formFields: [
              { key: 'creditLimit', label: 'Credit Limit', type: 'number', required: true, step: '0.01', placeholder: 'Enter credit limit', validation: { min: { value: 0, message: 'Credit limit cannot be negative' } } }
            ]
          },
          {
            id: 'search-customers',
            label: 'Search Customers',
            description: 'Search customers by country or city.',
            endpoint: 'GET /api/customers/search',
            method: 'GET',
            tone: 'primary',
            queryFields: [
              { key: 'country', label: 'Country', type: 'text', placeholder: 'Enter country' },
              { key: 'city', label: 'City', type: 'text', placeholder: 'Enter city' }
            ]
          }
        ]
      },
      {
        id: 'payment',
        name: 'Payment',
        summary: 'Payment records.',
        accent: '#60a5fa',
        actions: [
          {
            id: 'get-all-payments',
            label: 'Get All Payments',
            description: 'Open payment list.',
            endpoint: 'GET /api/payments/all',
            method: 'GET',
            tone: 'primary',
            submitLabel: 'Fetch Payments',
            queryFields: [
              { key: 'page', label: 'Page', type: 'number', required: true, placeholder: 'Enter page number', defaultValue: '0' },
              { key: 'size', label: 'Size', type: 'number', required: true, placeholder: 'Enter page size', defaultValue: '10' }
            ]
          },
          {
            id: 'payments-by-customer',
            label: 'Payments By Customer',
            description: 'Search payments by customer number.',
            endpoint: 'GET /api/payments/customer/{customerNumber}',
            method: 'GET',
            tone: 'neutral'
          },
          {
            id: 'payment-by-check',
            label: 'Payment By Check Number',
            description: 'Search payment by check number.',
            endpoint: 'GET /api/payments/check/{checkNumber}',
            method: 'GET',
            tone: 'primary'
          }
        ]
      }
    ]
  },
  {
    id: 'employee',
    memberName: 'Harini',
    moduleName: 'Employee',
    tagline: 'Employee & Office',
    description: 'Employee module.',
    theme: '#4a5668',
    gradient: 'linear-gradient(135deg, #faf6f0 0%, #f2f0eb 56%, #e3e0db 100%)',
    credentials: { username: 'harini', password: '1234' },
    loginCheckEndpoint: '/api/employees',
    resources: [
      {
        id: 'employee',
        name: 'Employee',
        summary: 'Employee records.',
        accent: '#8b5cf6',
        actions: [
          {
            id: 'create-employee',
            label: 'Add Employee',
            description: 'Open employee creation form.',
            endpoint: 'POST /api/employees',
            method: 'POST',
            tone: 'success',
            submitLabel: 'Add Employee',
            successMessage: 'Employee added successfully',
            formFields: [
              { key: 'employeeNumber', label: 'Employee Number', type: 'number', required: true, placeholder: 'Enter employee number' },
              { key: 'firstName', label: 'First Name', type: 'text', required: true, placeholder: 'Enter first name', validation: { notBlank: 'First name is required', maxLength: { value: 50 } } },
              { key: 'lastName', label: 'Last Name', type: 'text', required: true, placeholder: 'Enter last name', validation: { notBlank: 'Last name is required', maxLength: { value: 50 } } },
              { key: 'extension', label: 'Extension', type: 'text', required: true, placeholder: 'Enter extension', validation: { notBlank: 'Extension is required', maxLength: { value: 10 } } },
              { key: 'email', label: 'Email', type: 'email', required: true, placeholder: 'Enter email', validation: { notBlank: 'Email is required', email: 'Invalid email format', maxLength: { value: 100 } } },
              { key: 'officeCode', label: 'Office Code', type: 'text', required: true, placeholder: 'Enter office code' },
              { key: 'managerId', label: 'Manager ID', type: 'number', placeholder: 'Enter manager id' },
              { key: 'jobTitle', label: 'Job Title', type: 'text', required: true, placeholder: 'Enter job title', validation: { notBlank: 'Job title is required', maxLength: { value: 50 } } }
            ]
          },
          {
            id: 'get-all-employees',
            label: 'Get All Employees',
            description: 'Open employee list.',
            endpoint: 'GET /api/employees',
            method: 'GET',
            tone: 'primary',
            submitLabel: 'Fetch Employees',
            queryFields: [
              { key: 'page', label: 'Page', type: 'number', required: true, placeholder: 'Enter page number', defaultValue: '0' },
              { key: 'size', label: 'Size', type: 'number', required: true, placeholder: 'Enter page size', defaultValue: '10' }
            ]
          },
          {
            id: 'get-employee-by-id',
            label: 'Employee Details',
            description: 'Search employee by number.',
            endpoint: 'GET /api/employees/{employeeNumber}',
            method: 'GET',
            tone: 'neutral'
          },
          {
            id: 'update-employee',
            label: 'Update Employee',
            description: 'Open employee update form.',
            endpoint: 'PUT /api/employees/{employeeNumber}',
            method: 'PUT',
            tone: 'warning',
            submitLabel: 'Update Employee',
            successMessage: 'Employee updated successfully',
            formFields: [
              { key: 'employeeNumber', label: 'Employee Number', type: 'number', required: true, placeholder: 'Enter employee number' },
              { key: 'firstName', label: 'First Name', type: 'text', required: true, placeholder: 'Enter first name', validation: { notBlank: 'First name is required', maxLength: { value: 50 } } },
              { key: 'lastName', label: 'Last Name', type: 'text', required: true, placeholder: 'Enter last name', validation: { notBlank: 'Last name is required', maxLength: { value: 50 } } },
              { key: 'extension', label: 'Extension', type: 'text', required: true, placeholder: 'Enter extension', validation: { notBlank: 'Extension is required', maxLength: { value: 10 } } },
              { key: 'email', label: 'Email', type: 'email', required: true, placeholder: 'Enter email', validation: { notBlank: 'Email is required', email: 'Invalid email format', maxLength: { value: 100 } } },
              { key: 'officeCode', label: 'Office Code', type: 'text', required: true, placeholder: 'Enter office code' },
              { key: 'managerId', label: 'Manager ID', type: 'number', placeholder: 'Enter manager id' },
              { key: 'jobTitle', label: 'Job Title', type: 'text', required: true, placeholder: 'Enter job title', validation: { notBlank: 'Job title is required', maxLength: { value: 50 } } }
            ]
          },
          {
            id: 'get-manager',
            label: 'View Manager',
            description: 'Search manager by employee number.',
            endpoint: 'GET /api/employees/{employeeNumber}/manager',
            method: 'GET',
            tone: 'neutral'
          },
          {
            id: 'get-subordinates',
            label: 'View Subordinates',
            description: 'Search subordinates by employee number.',
            endpoint: 'GET /api/employees/{employeeNumber}/subordinates',
            method: 'GET',
            tone: 'primary'
          }
        ]
      },
      {
        id: 'office',
        name: 'Office',
        summary: 'Office records.',
        accent: '#a78bfa',
        actions: [
          {
            id: 'create-office',
            label: 'Add Office',
            description: 'Open office creation form.',
            endpoint: 'POST /api/offices',
            method: 'POST',
            tone: 'success',
            submitLabel: 'Add Office',
            successMessage: 'Office added successfully',
            formFields: [
              { key: 'officeCode', label: 'Office Code', type: 'text', required: true, placeholder: 'Enter office code' },
              { key: 'city', label: 'City', type: 'text', required: true, placeholder: 'Enter city', validation: { notBlank: 'City is required', maxLength: { value: 50 } } },
              { key: 'phone', label: 'Phone', type: 'text', required: true, placeholder: 'Enter phone number' },
              { key: 'addressLine1', label: 'Address Line 1', type: 'text', required: true, placeholder: 'Enter address line 1' },
              { key: 'addressLine2', label: 'Address Line 2', type: 'text', placeholder: 'Enter address line 2' },
              { key: 'state', label: 'State', type: 'text', placeholder: 'Enter state' },
              { key: 'country', label: 'Country', type: 'text', required: true, placeholder: 'Enter country' },
              { key: 'postalCode', label: 'Postal Code', type: 'text', required: true, placeholder: 'Enter postal code' },
              { key: 'territory', label: 'Territory', type: 'text', required: true, placeholder: 'Enter territory' }
            ]
          },
          {
            id: 'get-all-offices',
            label: 'Get All Offices',
            description: 'Open office list.',
            endpoint: 'GET /api/offices',
            method: 'GET',
            tone: 'primary',
            submitLabel: 'Fetch Offices',
            queryFields: [
              { key: 'page', label: 'Page', type: 'number', required: true, placeholder: 'Enter page number', defaultValue: '0' },
              { key: 'size', label: 'Size', type: 'number', required: true, placeholder: 'Enter page size', defaultValue: '10' }
            ]
          },
          {
            id: 'get-office-by-code',
            label: 'Office Details',
            description: 'Search office by code.',
            endpoint: 'GET /api/offices/{officeCode}',
            method: 'GET',
            tone: 'neutral'
          },
          {
            id: 'get-employees-by-office',
            label: 'Employees By Office',
            description: 'Search employees by office code.',
            endpoint: 'GET /api/offices/{officeCode}/employees',
            method: 'GET',
            tone: 'primary'
          }
        ]
      }
    ]
  },
  {
    id: 'orders',
    memberName: 'Pritika',
    moduleName: 'Orders',
    tagline: 'Orders',
    description: 'Orders module.',
    theme: '#7b5a3a',
    gradient: 'linear-gradient(135deg, #fbf6ee 0%, #f5ede2 55%, #eadcc7 100%)',
    credentials: { username: 'pritika', password: '1234' },
    loginCheckEndpoint: '/api/orders',
    resources: [
      {
        id: 'orders',
        name: 'Orders',
        summary: 'Order records.',
        accent: '#f97316',
        actions: [
          {
            id: 'create-order',
            label: 'Add Order',
            description: 'Open order creation form.',
            endpoint: 'POST /api/orders',
            method: 'POST',
            tone: 'success',
            submitLabel: 'Create Order',
            successMessage: 'Order added successfully',
            formFields: [
              { key: 'customerNumber', label: 'Customer Number', type: 'number', required: true, placeholder: 'Enter customer number' },
              { key: 'orderDate', label: 'Order Date', type: 'date', required: true },
              { key: 'requiredDate', label: 'Required Date', type: 'date', required: true },
              { key: 'shippedDate', label: 'Shipped Date', type: 'date' },
              { key: 'status', label: 'Status', type: 'text', required: true, placeholder: 'Enter status', validation: { notBlank: 'Status is required', maxLength: { value: 15, message: 'Status must not exceed 15 characters' } } },
              { key: 'comments', label: 'Comments', type: 'textarea', placeholder: 'Enter comments' }
            ]
          },
          {
            id: 'get-all-orders',
            label: 'Get All Orders',
            description: 'Open order list.',
            endpoint: 'GET /api/orders',
            method: 'GET',
            tone: 'primary',
            submitLabel: 'Fetch Orders',
            queryFields: [
              { key: 'page', label: 'Page', type: 'number', required: true, placeholder: 'Enter page number', defaultValue: '0' },
              { key: 'size', label: 'Size', type: 'number', required: true, placeholder: 'Enter page size', defaultValue: '10' }
            ]
          },
          {
            id: 'get-order-by-id',
            label: 'Order Details',
            description: 'Search order by number.',
            endpoint: 'GET /api/orders/{orderNumber}',
            method: 'GET',
            tone: 'neutral'
          },
          {
            id: 'update-order',
            label: 'Update Order',
            description: 'Open order update form.',
            endpoint: 'PUT /api/orders/{orderNumber}',
            method: 'PUT',
            tone: 'warning',
            submitLabel: 'Update Order',
            successMessage: 'Order updated successfully',
            formFields: [
              { key: 'customerNumber', label: 'Customer Number', type: 'number', required: true, placeholder: 'Enter customer number' },
              { key: 'orderDate', label: 'Order Date', type: 'date', required: true },
              { key: 'requiredDate', label: 'Required Date', type: 'date', required: true },
              { key: 'shippedDate', label: 'Shipped Date', type: 'date' },
              { key: 'status', label: 'Status', type: 'text', required: true, placeholder: 'Enter status', validation: { notBlank: 'Status is required', maxLength: { value: 15, message: 'Status must not exceed 15 characters' } } },
              { key: 'comments', label: 'Comments', type: 'textarea', placeholder: 'Enter comments' }
            ]
          },
          {
            id: 'update-order-status',
            label: 'Update Order Status',
            description: 'Update only order status.',
            endpoint: 'PATCH /api/orders/{orderNumber}/status',
            method: 'PATCH',
            tone: 'success',
            submitLabel: 'Update Status',
            successMessage: 'Order status updated successfully',
            formFields: [
              { key: 'status', label: 'Status', type: 'text', required: true, placeholder: 'Enter status', validation: { notBlank: 'Status is required', maxLength: { value: 15, message: 'Status must not exceed 15 characters' } } }
            ]
          },
          {
            id: 'orders-by-customer',
            label: 'Orders By Customer',
            description: 'Search orders by customer number.',
            endpoint: 'GET /api/customers/{customerNumber}/orders',
            method: 'GET',
            tone: 'primary'
          },
          {
            id: 'search-orders',
            label: 'Search Orders',
            description: 'Search orders by filters.',
            endpoint: 'GET /api/orders/search',
            method: 'GET',
            tone: 'neutral',
            queryFields: [
              { key: 'status', label: 'Status', type: 'text', required: true, placeholder: 'Enter status' },
              { key: 'fromDate', label: 'From Date', type: 'date', required: true },
              { key: 'toDate', label: 'To Date', type: 'date', required: true }
            ]
          }
        ]
      }
    ]
  },
  {
    id: 'product',
    memberName: 'Meena',
    moduleName: 'Product',
    tagline: 'Products',
    description: 'Product module.',
    theme: '#415446',
    gradient: 'linear-gradient(135deg, #f8f5ed 0%, #edf2ee 55%, #dce5de 100%)',
    credentials: { username: 'meena', password: '1234' },
    loginCheckEndpoint: '/api/products',
    resources: [
      {
        id: 'product',
        name: 'Product',
        summary: 'Product records.',
        accent: '#f43f5e',
        actions: [
          {
            id: 'create-product',
            label: 'Add Product',
            description: 'Open product creation form.',
            endpoint: 'POST /api/products',
            method: 'POST',
            tone: 'success',
            submitLabel: 'Add Product',
            successMessage: 'Product added successfully',
            formFields: [
              { key: 'productCode', label: 'Product Code', type: 'text', required: true, placeholder: 'Enter product code', validation: { notBlank: 'Product code cannot be empty', maxLength: { value: 15 } } },
              { key: 'productName', label: 'Product Name', type: 'text', required: true, placeholder: 'Enter product name', validation: { notBlank: 'Product name is required', maxLength: { value: 70 } } },
              { key: 'productLine', label: 'Product Line', type: 'text', required: true, placeholder: 'Enter product line', validation: { notBlank: 'Product line is required', maxLength: { value: 50 } } },
              { key: 'productScale', label: 'Product Scale', type: 'text', required: true, placeholder: 'Enter product scale', validation: { notBlank: 'Product scale is required', maxLength: { value: 10 } } },
              { key: 'productVendor', label: 'Product Vendor', type: 'text', required: true, placeholder: 'Enter product vendor', validation: { notBlank: 'Product vendor is required', maxLength: { value: 50 } } },
              { key: 'productDescription', label: 'Product Description', type: 'textarea', required: true, placeholder: 'Enter description' },
              { key: 'quantityInStock', label: 'Quantity In Stock', type: 'number', required: true, placeholder: 'Enter stock quantity', validation: { min: { value: 0, message: 'Stock cannot be negative' } } },
              { key: 'buyPrice', label: 'Buy Price', type: 'number', required: true, step: '0.01', placeholder: 'Enter buy price', validation: { min: { value: 0.01, message: 'Buy price must be greater than 0' } } },
              { key: 'msrp', label: 'MSRP', type: 'number', required: true, step: '0.01', placeholder: 'Enter MSRP', validation: { min: { value: 0.01, message: 'MSRP must be greater than 0' } } }
            ]
          },
          {
            id: 'update-product',
            label: 'Update Product',
            description: 'Open product update form.',
            endpoint: 'PUT /api/products/{productCode}',
            method: 'PUT',
            tone: 'warning',
            submitLabel: 'Update Product',
            successMessage: 'Product updated successfully',
            formFields: [
              { key: 'productName', label: 'Product Name', type: 'text', placeholder: 'Enter product name', validation: { maxLength: { value: 70 } } },
              { key: 'productLine', label: 'Product Line', type: 'text', placeholder: 'Enter product line', validation: { maxLength: { value: 50 } } },
              { key: 'productScale', label: 'Product Scale', type: 'text', placeholder: 'Enter product scale', validation: { maxLength: { value: 10 } } },
              { key: 'productVendor', label: 'Product Vendor', type: 'text', placeholder: 'Enter product vendor', validation: { maxLength: { value: 50 } } },
              { key: 'productDescription', label: 'Product Description', type: 'textarea', placeholder: 'Enter description' },
              { key: 'quantityInStock', label: 'Quantity In Stock', type: 'number', placeholder: 'Enter stock quantity', validation: { min: { value: 0, message: 'Stock cannot be negative' } } },
              { key: 'buyPrice', label: 'Buy Price', type: 'number', step: '0.01', placeholder: 'Enter buy price', validation: { min: { value: 0.01, message: 'Price must be greater than 0' } } },
              { key: 'msrp', label: 'MSRP', type: 'number', step: '0.01', placeholder: 'Enter MSRP', validation: { min: { value: 0.01, message: 'MSRP must be greater than 0' } } }
            ]
          },
          {
            id: 'delete-product',
            label: 'Delete Product',
            description: 'Delete product after confirmation.',
            endpoint: 'DELETE /api/products/{productCode}',
            method: 'DELETE',
            tone: 'warning',
            submitLabel: 'Delete Product',
            successMessage: 'Product deleted successfully'
          },
          {
            id: 'get-all-products',
            label: 'Get All Products',
            description: 'Open product list.',
            endpoint: 'GET /api/products',
            method: 'GET',
            tone: 'primary',
            submitLabel: 'Fetch Products',
            queryFields: [
              { key: 'page', label: 'Page', type: 'number', required: true, placeholder: 'Enter page number', defaultValue: '0' },
              { key: 'size', label: 'Size', type: 'number', required: true, placeholder: 'Enter page size', defaultValue: '10' }
            ]
          },
          {
            id: 'get-product-by-id',
            label: 'Product Details',
            description: 'Search product by code.',
            endpoint: 'GET /api/products/{productCode}',
            method: 'GET',
            tone: 'neutral'
          }
        ]
      },
      {
        id: 'product-lines',
        name: 'ProductLines',
        summary: 'Product line records.',
        accent: '#fb7185',
        actions: [
          {
            id: 'get-all-product-lines',
            label: 'Get All Product Lines',
            description: 'Open product line list.',
            endpoint: 'GET /api/product-lines',
            method: 'GET',
            tone: 'primary',
            submitLabel: 'Fetch Product Lines',
            queryFields: [
              { key: 'page', label: 'Page', type: 'number', required: true, placeholder: 'Enter page number', defaultValue: '0' },
              { key: 'size', label: 'Size', type: 'number', required: true, placeholder: 'Enter page size', defaultValue: '10' }
            ]
          },
          {
            id: 'get-product-line-by-id',
            label: 'Product Line Details',
            description: 'Search product line by name.',
            endpoint: 'GET /api/product-lines/{productLine}',
            method: 'GET',
            tone: 'neutral'
          },
          {
            id: 'products-by-line',
            label: 'Products By Product Line',
            description: 'Search products by product line.',
            endpoint: 'GET /api/product-lines/{productLine}/products',
            method: 'GET',
            tone: 'primary'
          }
        ]
      },
      {
        id: 'order-details',
        name: 'OrderDetails',
        summary: 'Order detail records.',
        accent: '#fecdd3',
        actions: [
          {
            id: 'get-order-items',
            label: 'Get Order Items',
            description: 'Search items by order number.',
            endpoint: 'GET /api/orders/{orderNumber}/items',
            method: 'GET',
            tone: 'primary'
          },
          {
            id: 'add-order-item',
            label: 'Add Order Detail',
            description: 'Open order detail creation form.',
            endpoint: 'POST /api/orders/{orderNumber}/items',
            method: 'POST',
            tone: 'success',
            submitLabel: 'Add Order Detail',
            successMessage: 'Order detail added successfully',
            formFields: [
              { key: 'productCode', label: 'Product Code', type: 'text', required: true, placeholder: 'Enter product code', validation: { notBlank: 'Product code cannot be empty', maxLength: { value: 15 } } },
              { key: 'quantityOrdered', label: 'Quantity Ordered', type: 'number', required: true, placeholder: 'Enter quantity', validation: { min: { value: 1, message: 'Quantity must be at least 1' } } },
              { key: 'priceEach', label: 'Price Each', type: 'number', required: true, step: '0.01', placeholder: 'Enter price', validation: { min: { value: 0.01, message: 'Price each must be greater than 0' } } },
              { key: 'orderLineNumber', label: 'Order Line Number', type: 'number', required: true, placeholder: 'Enter line number', validation: { min: { value: 1, message: 'Order line number must be at least 1' } } }
            ]
          },
          {
            id: 'update-order-item',
            label: 'Update Order Detail',
            description: 'Open order detail update form.',
            endpoint: 'PUT /api/orders/{orderNumber}/items/{productCode}',
            method: 'PUT',
            tone: 'warning',
            submitLabel: 'Update Order Detail',
            successMessage: 'Order detail updated successfully',
            formFields: [
              { key: 'productCode', label: 'Product Code', type: 'text', required: true, placeholder: 'Enter product code', validation: { notBlank: 'Product code cannot be empty', maxLength: { value: 15 } } },
              { key: 'quantityOrdered', label: 'Quantity Ordered', type: 'number', required: true, placeholder: 'Enter quantity', validation: { min: { value: 1, message: 'Quantity must be at least 1' } } },
              { key: 'priceEach', label: 'Price Each', type: 'number', required: true, step: '0.01', placeholder: 'Enter price', validation: { min: { value: 0.01, message: 'Price each must be greater than 0' } } },
              { key: 'orderLineNumber', label: 'Order Line Number', type: 'number', required: true, placeholder: 'Enter line number', validation: { min: { value: 1, message: 'Order line number must be at least 1' } } }
            ]
          },
          {
            id: 'delete-order-item',
            label: 'Delete Order Detail',
            description: 'Delete order detail after confirmation.',
            endpoint: 'DELETE /api/orders/{orderNumber}/items/{productCode}',
            method: 'DELETE',
            tone: 'warning',
            submitLabel: 'Delete Order Detail',
            successMessage: 'Order detail deleted successfully'
          }
        ]
      }
    ]
  }
];

export function getModuleById(moduleId: string | null): ModuleDefinition | undefined {
  return MODULES.find((module) => module.id === moduleId);
}
