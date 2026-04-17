package com.classicbusinessmodel_schema.backend.module.employee.dto.officedto;

public class OfficeResponseDTO {

    private String officeCode;
    private String city;
    private String phone;
    private String country;

    public OfficeResponseDTO() {
    }

    public OfficeResponseDTO(String officeCode, String city, String phone, String country) {
        this.officeCode = officeCode;
        this.city = city;
        this.phone = phone;
        this.country = country;
    }

    public String getOfficeCode() {
        return officeCode;
    }

    public void setOfficeCode(String officeCode) {
        this.officeCode = officeCode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    @Override
    public String toString() {
        return "OfficeResponseDTO{" +
                "officeCode='" + officeCode + '\'' +
                ", city='" + city + '\'' +
                ", phone='" + phone + '\'' +
                ", country='" + country + '\'' +
                '}';
    }
}