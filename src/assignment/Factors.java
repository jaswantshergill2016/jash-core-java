package assignment;

import java.util.Scanner;

public class Factors {

    public static void main(String[] args) {


        Scanner myObj = new Scanner(System.in);
        System.out.println("please number you want factors");

        int input = myObj.nextInt();  // Read user input
        System.out.println("Input is: " + input);


        for(int i=1; i <=input;i++){
            if(input % i==0){
                System.out.println(i);
            }
        }
    }
}
