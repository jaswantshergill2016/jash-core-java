package file.io;

import java.io.*;

public class FilePrimeNumber {

    public static void main(String[] args) {

        String readLine="";
        FileReader fileReader = null;
        int primeNumber = -1;

        try {
            fileReader = new FileReader(new File("input.txt"));

            BufferedReader bufferedReader = new BufferedReader(fileReader);

            while((readLine = bufferedReader.readLine()) != null){
                System.out.println(readLine);
                //        prime-numbers-input=513


                if(readLine.indexOf("prime-numbers-input") >=0) {


                    int lastPrimeNumber = readLine.indexOf("513");

                    if (lastPrimeNumber >= 0) {
                        String factorialNumberStr = readLine.substring(lastPrimeNumber, lastPrimeNumber + 3);
                        primeNumber = Integer.parseInt(factorialNumberStr);
                        System.out.println("Last prime number "+ primeNumber);
                        printPrimeNumbers(primeNumber);
                    }

                }

            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }





    }


    public static void printPrimeNumbers(int lastPrimeNumber){
        int i =0;
        int num =0;
        String  primeNumbers = "";

        for (i = 1; i <= lastPrimeNumber; i++)
        {
            int counter=0;
            for(num =i; num>=1; num--)
            {
                if(i%num==0)
                {
                    counter = counter + 1;
                }
            }
            if (counter ==2)
            {
                primeNumbers = primeNumbers + i + " ";
            }
        }
        System.out.println("Prime numbers from 1 to "+ lastPrimeNumber+" are :");
        System.out.println(primeNumbers);
    }
    }


