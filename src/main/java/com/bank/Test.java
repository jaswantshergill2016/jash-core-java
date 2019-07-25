package com.bank;

interface Validate{
    void doValidate(String stringArg);
    void doValidate(Integer integerArg);
    void doValidate(Double doubleArg);
}

class Validator implements Validate{

    @Override
    public void doValidate(String stringArg) {

    }

    @Override
    public void doValidate(Integer integerArg) {

    }

    @Override
    public void doValidate(Double doubleArg) {

    }
}

public class Test {
    public static void main(String[] args) {
        String str = String.format("%05d", 1);
        System.out.println(str);
    }
}
