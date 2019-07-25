package com.bank;

public class PersonalCustomer extends AbstractCustomer {

    private LockBoxType lockBoxType;
    private Integer noOfMissedBillPayments;

    public PersonalCustomer(){
        this.customerType = CustomerType.PERSONAL_CUSTOMER;
    }

    @Override
    public String printCustomerDetails() {
        String printDetails = "Lock Box Type "+ this.lockBoxType+"" +
                "No Of Missed bill payments "+ this.noOfMissedBillPayments+"" +
                super.toString();
        return printDetails;
    }

    @Override
    public boolean equals(Object o) {

        if (o == this) return true;
        if (!(o instanceof PersonalCustomer)) {
            return false;
        }

        PersonalCustomer personalCustomer = (PersonalCustomer) o;

        return personalCustomer.getName().equals(this.getName()) &&
                personalCustomer.getAddress().equals(this.getAddress()) &&
                personalCustomer.getLockBoxType().equals(this.getLockBoxType());
    }

    @Override
    public int hashCode() {
        return this.getName().hashCode() + this.getAddress().hashCode()
                +this.getLockBoxType().hashCode();
    }

    @Override
    public String toString() {
        return "Lock box Type " + this.lockBoxType +"" +
                "No of missed Bill Payments "+ this.noOfMissedBillPayments+"" +
                super.toString();
    }

    public LockBoxType getLockBoxType() {
        return lockBoxType;
    }

    public void setLockBoxType(LockBoxType lockBoxType) {
        this.lockBoxType = lockBoxType;
    }

    public Integer getNoOfMissedBillPayments() {
        return noOfMissedBillPayments;
    }

    public void setNoOfMissedBillPayments(Integer noOfMissedBillPayments) {
        this.noOfMissedBillPayments = noOfMissedBillPayments;
    }
}
