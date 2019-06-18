package com.bank;

import java.util.HashSet;
import java.util.Set;

public abstract class AbstractCustomer implements Customer{

    private String customerId;
    private String name;
    private String address;
    private String phone;
    private MailPreference mailPreference;
    private Set<Account> customerAccounts = new HashSet<>();

    public abstract String printCustomerDetails();

    @Override
    public boolean equals(Object o) {

        if (o == this) return true;
        if (!(o instanceof AbstractCustomer)) {
            return false;
        }

        AbstractCustomer abstractCustomer = (AbstractCustomer) o;

        return abstractCustomer.getName().equals(this.name) &&
                abstractCustomer.getAddress().equals(this.address);
    }


    @Override
    public int hashCode() {
        return name.hashCode() + address.hashCode();

    }

    @Override
    public String toString() {
        return "Customer Id "+this.getCustomerId()+"" +
                "name "+ this.getName()+"" +
                "address "+ this.getAddress()+"" +
                "phone "+this.getPhone()+"" +
                "mail preference "+this.getMailPreference();
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
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

    public MailPreference getMailPreference() {
        return mailPreference;
    }

    public void setMailPreference(MailPreference mailPreference) {
        this.mailPreference = mailPreference;
    }

    public Set<Account> getCustomerAccounts() {
        return customerAccounts;
    }

    public void setCustomerAccounts(Set<Account> customerAccounts) {
        this.customerAccounts = customerAccounts;
    }
}
