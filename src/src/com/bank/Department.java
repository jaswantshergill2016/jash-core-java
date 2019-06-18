package com.bank;

import java.util.List;

public class Department implements  IDepartment {
    private String departmentId;
    private String name;
    private List<String> responsibilities;
    private List<Employee> employees;
    private String managerId;
    private String branchId;

    @Override
    public int hashCode() {
        return this.getDepartmentId().hashCode();
    }

    @Override
    public boolean equals(Object o) {
        if(this == o )return true;
        if (!(o instanceof Department))
            return false;
        return this.getDepartmentId().equals(((Department)o).getDepartmentId());

    }

    @Override
    public String toString() {
        String responsibilitiesStr = "";
        for (String responsibility: this.getResponsibilities()) {
            responsibilitiesStr+=" "+responsibility;
        }
        String employeesLst = "";
        for (Employee employee: this.getEmployees()) {
            employeesLst+=" "+employee.getName();
        }
        return "name "+ this.getName()+"" +
                "departmentId "+this.getDepartmentId()+"" +
                "responsibilities "+responsibilitiesStr+"" +
                "employees "+employeesLst+"" +
                "managerId "+this.getManagerId()+"" +
                "branchId "+this.getBranchId();

    }

    public String getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(String departmentId) {
        this.departmentId = departmentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getResponsibilities() {
        return responsibilities;
    }

    public void setResponsibilities(List<String> responsibilities) {
        this.responsibilities = responsibilities;
    }

    public List<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }

    public String getManagerId() {
        return managerId;
    }

    public void setManagerId(String managerId) {
        this.managerId = managerId;
    }

    public String getBranchId() {
        return branchId;
    }

    public void setBranchId(String branchId) {
        this.branchId = branchId;
    }
}
