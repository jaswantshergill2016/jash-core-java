package assignment;

import java.util.Scanner;

public class Composite{

    public static void main(String[] args) {

        Scanner myObj = new Scanner(System.in);
        System.out.println("please enter number to check composite or not ");

        int input = myObj.nextInt();  // Read user input
        System.out.println("Input is: " + input);

        Composite.isComposite(input);
    }

    public static void isComposite(int number)

    {int n=0;

        for(int i=1;i<=number;i++){

            if(number%i==0)

                n++;}

        if(n>2)

            System.out.println(number+" is a composite number");

        else

            System.out.println(number+" is a not composite number");

    }}