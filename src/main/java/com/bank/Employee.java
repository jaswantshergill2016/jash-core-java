package com.bank;

public class Employee implements  IEmployee{

    private String employeeId;
    private String name;
    private String address;
    private String phone;
    private EmployeeRole employeeRole;
    private String reportingTo;

    @Override
    public int hashCode() {
        return this.getEmployeeId().hashCode();
    }

    @Override
    public boolean equals(Object o) {
        if(this==o)return true;
        if(!(o instanceof  Employee)){
            return false;
        }

        return this.getEmployeeId().equals(((Employee)o).getEmployeeId());

    }

    @Override
    public String toString() {

        return "Employee Id "+ this.getEmployeeId()+"" +
                "name "+ this.getName()+"" +
                "address "+this.getAddress()+"" +
                "phone "+this.getPhone()+"" +
                "Employee Role "+ this.getEmployeeRole()+"" +
                "Reporting to "+ this.getReportingTo();

    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public EmployeeRole getEmployeeRole() {
        return employeeRole;
    }

    public void setEmployeeRole(EmployeeRole employeeRole) {
        this.employeeRole = employeeRole;
    }

    public String getReportingTo() {
        return reportingTo;
    }

    public void setReportingTo(String reportingTo) {
        this.reportingTo = reportingTo;
    }
}
