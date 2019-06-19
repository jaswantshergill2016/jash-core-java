package com.bank;

import java.util.Date;
import java.util.List;

public class BusinessCustomer extends AbstractCustomer {

    private String businessName;
    private List<String> directors;
    private Double annualRevenue;
    private Date incorporationDate;

    public BusinessCustomer(){
        this.customerType = CustomerType.BUSINESS_CUSTOMER;
    }

    @Override
    public String printCustomerDetails() {

        String listOfDirectors ="";
        for (String director: this.getDirectors()) {
            listOfDirectors+= director;
        }
        String printDetails = "Business Name "+this.getBusinessName()+"" +
                "annual Revenue "+this.getAnnualRevenue()+"" +
                "incorporation date "+this.getIncorporationDate()+"" +
                "List of directors "+listOfDirectors +
                super.toString();

        return printDetails;
    }



    @Override
    public int hashCode() {
        return this.getName().hashCode() + this.getAddress().hashCode()
                +this.getBusinessName().hashCode();
    }

    @Override
    public boolean equals(Object o) {

        if (o == this) return true;
        if (!(o instanceof BusinessCustomer)) {
            return false;
        }

        BusinessCustomer businessCustomer = (BusinessCustomer) o;

        return businessCustomer.getName().equals(this.getName()) &&
                businessCustomer.getAddress().equals(this.getAddress()) &&
                businessCustomer.getBusinessName().equals(this.getBusinessName());
    }

    public String getBusinessName() {
        return businessName;
    }

    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }

    public List<String> getDirectors() {
        return directors;
    }

    public void setDirectors(List<String> directors) {
        this.directors = directors;
    }

    public Double getAnnualRevenue() {
        return annualRevenue;
    }

    public void setAnnualRevenue(Double annualRevenue) {
        this.annualRevenue = annualRevenue;
    }

    public Date getIncorporationDate() {
        return incorporationDate;
    }

    public void setIncorporationDate(Date incorporationDate) {
        this.incorporationDate = incorporationDate;
    }
}
