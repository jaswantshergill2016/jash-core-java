package file.io;

import java.io.*;

public class FileReaderExample {

    public static void main(String[] args) {
        FileReader fileReader;
        int readInt =0;
        String readLine="";
        int countOfLinesStartingWithTwo = 0;
        {
            try {
                fileReader = new FileReader(new File("writerout.txt"));
                BufferedReader bufferedReader = new BufferedReader(fileReader);
                while((readLine = bufferedReader.readLine()) != null){
                    System.out.println(readLine);
                    if(readLine.startsWith("line 2")){
                        countOfLinesStartingWithTwo++;
                    }
                }
//                while((readInt =fileReader.read())!=-1)
//                    System.out.print((char)readInt);
                System.out.println("count Of Lines Starting With Two "+countOfLinesStartingWithTwo);
                fileReader.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


}
