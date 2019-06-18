package file.io;

import java.io.*;

public class FileComposite {


    //        composite-numbers-input=321

    public static void main(String[] args) {

        String readLine="";
        FileReader fileReader = null;
        int compositeNumber = -1;

        try {
            fileReader = new FileReader(new File("input.txt"));

            BufferedReader bufferedReader = new BufferedReader(fileReader);

            while((readLine = bufferedReader.readLine()) != null){
                System.out.println(readLine);
                //        composite-numbers-input=321


                if(readLine.indexOf("composite-numbers-input") >=0) {


                    int lastPrimeNumber = readLine.indexOf("321");

                    if (lastPrimeNumber >= 0) {
                        String compositeNumberStr = readLine.substring(lastPrimeNumber, lastPrimeNumber + 3);
                        compositeNumber = Integer.parseInt(compositeNumberStr);
                        System.out.println(" Is this number composite "+ compositeNumber);
                        isComposite(compositeNumber);
                    }

                }

            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }





    }

    public static void isComposite(int number)

    {
        int n=0;

        for(int i=1;i<=number;i++){

            if(number%i==0)

                n++;}

        if(n>2)

            System.out.println(number+" is a composite number");

        else

            System.out.println(number+" is a not composite number");

    }
}
