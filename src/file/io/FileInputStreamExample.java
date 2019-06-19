package file.io;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FileInputStreamExample {

    public static void main(String[] args) {

        try {
            FileInputStream fileInputStream = new FileInputStream(new File("writerout.txt"));

            BufferedInputStream bufferedInputStream = new BufferedInputStream(fileInputStream);
            int one = 0;
            int two = 0;
            int three = 0;
            int four = 0;
            int five = 0;
            int six = 0;
            int seven = 0;
            int eight = 0;
            int nine = 0;
            byte [] byteArr = new byte[100];
            char readChar = 'a';
            //while(bufferedInputStream.read(byteArr) != -1) {
                while(fileInputStream.read(byteArr) != -1) {
                //char readChar = (char) bufferedInputStream.read(byteArr);

                //System.out.println(byteArr);

                for(int i=0;i<byteArr.length; i++){
                    readChar = (char)byteArr[i];
                    System.out.print(readChar);

                    switch(readChar){
                        case '1': one++;break;
                        case '2': two++;break;
                        case '3': three++;break;
                        case '4': four++;break;
                        case '5': five++;break;
                        case '6': six++;break;
                        case '7': seven++;break;
                        case '8': eight++;break;
                        case '9': nine++;break;
                    }
                }


                //System.out.print(readChar);


            }
            bufferedInputStream.close();

            System.out.println(" 1s "+one+
                            " 2s "+two+
                            " 3s "+three+
                            " 4s "+four+
                            " 5s "+five+
                            " 6s "+six+
                            " 7s "+seven+
                            " 8s "+eight+
                            " 9s "+nine
                    );
        }catch(FileNotFoundException ex){
            ex.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
