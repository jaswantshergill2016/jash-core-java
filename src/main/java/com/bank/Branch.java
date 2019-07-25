package com.bank;

import java.util.List;

public class Branch implements IBranch{

    private String branchId;
    private String name;
    private String address;
    private List<BranchServices> servicesProvided;
    private List<Department> departments;

    @Override
    public int hashCode() {
        return this.getBranchId().hashCode();
    }

    @Override
    public boolean equals(Object o) {
        if(this == o )return true;
        if (!(o instanceof Branch))
            return false;
        return this.getBranchId().equals(((Branch)o).getBranchId());

    }

    @Override
    public String toString() {

        String departmentNames="";
        for (Department department: this.getDepartments()) {
            departmentNames += " "+department.getName();
        }
        String servicesProvidedLst="";
        for (BranchServices branchServices: this.getServicesProvided()) {
            servicesProvidedLst += " "+branchServices;
        }
        return "branch Id "+ this.getBranchId()+"" +
                "name "+this.getName()+"" +
                "address "+this.getAddress()+"" +
                "depatement names "+ departmentNames +"" +
                "services Provided "+servicesProvidedLst;

    }

    public String getBranchId() {
        return branchId;
    }

    public void setBranchId(String branchId) {
        this.branchId = branchId;
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

    public List<BranchServices> getServicesProvided() {
        return servicesProvided;
    }

    public void setServicesProvided(List<BranchServices> servicesProvided) {
        this.servicesProvided = servicesProvided;
    }

    public List<Department> getDepartments() {
        return departments;
    }

    public void setDepartments(List<Department> departments) {
        this.departments = departments;
    }
}
