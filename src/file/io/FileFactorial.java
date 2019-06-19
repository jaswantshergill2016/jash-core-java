package file.io;

import java.io.*;
import java.math.BigInteger;

public class FileFactorial {

    public static void main(String[] args) {
        String readLine="";
        FileReader fileReader = null;
        int factorialNumber = -1;

        try {
            fileReader = new FileReader(new File("input.txt"));

            BufferedReader bufferedReader = new BufferedReader(fileReader);

            while((readLine = bufferedReader.readLine()) != null){
                System.out.println(readLine);
                //factorial-input=98


                if(readLine.indexOf("factorial-input") >=0) {


                    int indexOfFactorialNumber = readLine.indexOf("98");

                    if (indexOfFactorialNumber >= 0) {
                        //System.out.println("===> " + indexOfFactorialNumber);
                        String factorialNumberStr = readLine.substring(indexOfFactorialNumber, indexOfFactorialNumber + 2);
                        factorialNumber = Integer.parseInt(factorialNumberStr);
                    }

                }

            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        int input = factorialNumber;

        System.out.println("Input is: " + input);


        BigInteger factorial=new BigInteger("1");
        for(int i= input; i >=1;i--){
            factorial=factorial.multiply(new BigInteger(i+""));
        }

        System.out.println("factorial of the number is "+factorial);
    }
}
