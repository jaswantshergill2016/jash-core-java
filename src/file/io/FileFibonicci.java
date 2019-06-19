package file.io;

import java.io.*;

public class FileFibonicci {

    public static void main(String[] args) {

        String readLine="";
        FileReader fileReader = null;
        int fibnocciNumber = -1;

        try {
            fileReader = new FileReader(new File("input.txt"));

            BufferedReader bufferedReader = new BufferedReader(fileReader);

            while((readLine = bufferedReader.readLine()) != null){
                System.out.println(readLine);
                //fibonicci-input=95it’s a not

                if(readLine.indexOf("fibonicci-input") >=0) {


                    int indexOfFibnocciNumber = readLine.indexOf("95");

                    if (indexOfFibnocciNumber >= 0) {
                        System.out.println("===> " + indexOfFibnocciNumber);
                        String fibnocciNumberStr = readLine.substring(indexOfFibnocciNumber, indexOfFibnocciNumber + 2);
                        fibnocciNumber = Integer.parseInt(fibnocciNumberStr);
                    }

                }

            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        int input = fibnocciNumber;
        System.out.println("Input is: " + input);


        long previous = 0;
        long next = 1;
        for(int i=1; i <=input;i++){
            System.out.print(previous+" ");
            long sum = previous + next;
            previous = next;
            next = sum;

        }


    }
}
