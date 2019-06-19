package file.io;

import java.io.*;

public class FileBubbleSort {

    public static void main(String[] args) {

        FileReader fileReader = null;
        String readLine="";
        try {
            fileReader = new FileReader(new File("input.txt"));

        BufferedReader bufferedReader = new BufferedReader(fileReader);

        while((readLine = bufferedReader.readLine()) != null) {
            //System.out.println(readLine);
            //bubble-sort-input=32,29,55,100,88,26,2,18,9,23
            if(readLine.indexOf("bubble-sort-input") >=0) {
                String strOfNumbers2Sort = readLine.substring(readLine.indexOf("3"));
                System.out.println("==========> " + strOfNumbers2Sort);

                String [] arrOfNumbers2Sort = strOfNumbers2Sort.split(",");
                int [] arrOfIntegers2Sort = new int [arrOfNumbers2Sort.length];

                for(int i=0; i< arrOfNumbers2Sort.length;i++){
                    arrOfIntegers2Sort[i] = Integer.parseInt(arrOfNumbers2Sort[i]);
                }

                //System.out.println(arrOfIntegers2Sort);

                sortArray(arrOfIntegers2Sort);

            }
        }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    public static void sortArray(int [] arr){
        for (int i=0;i<arr.length;i++){
            for(int j=i;j<arr.length;j++){
                if(arr[i] > arr[j] ){
                    int temp =arr[j];
                    arr[j] = arr[i];
                    arr[i] = temp;

                }
            }
        }


        for(int k =0; k< arr.length;k++){
            System.out.print(arr[k]+ " ");
        }
    }
}
