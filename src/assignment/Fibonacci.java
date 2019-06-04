package assignment;

import java.util.Scanner;

public class Fibonacci {


    public static void main(String[] args) {
        Scanner myObj = new Scanner(System.in);
        System.out.println("please enter Fibonacci number");

        int input = myObj.nextInt();  // Read user input
        System.out.println("Input is: " + input);


        int previous = 0;
        int next = 1;
        for(int i=1; i <=input;i++){
            System.out.print(previous+" ");
            int sum = previous + next;
            previous = next;
            next = sum;

        }


    }
}
