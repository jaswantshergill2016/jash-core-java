package assignment;

import java.util.Scanner;

public class Factorial {

    public static void main(String[] args) {
        Scanner myObj = new Scanner(System.in);
        System.out.println("please enter factorial number");

        int input = myObj.nextInt();  // Read user input
        System.out.println("Input is: " + input);


        int factorial=1;
        for(int i=input; i >=1;i--){
            factorial=factorial*i;
        }

        System.out.println("factorial of the number is "+factorial);
    }
}
