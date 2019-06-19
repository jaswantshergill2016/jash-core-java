package file.io;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;

public class FileOutputStreamExample {

    public static void main(String[] args) {

        try {
            FileOutputStream fileOutputStream = new FileOutputStream(new File("streanout.txt"));
            BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(fileOutputStream);

            SimpleDateFormat format = new SimpleDateFormat("MMM dd, yyyy");

            String dateString = format.format( new Date()   );

            for (int i=1; i <=2000; i++){
                String line2Write = "line "+i +", "+ dateString +"\n";
                bufferedOutputStream.write(line2Write.getBytes());
            }

            bufferedOutputStream.flush();
            bufferedOutputStream.close();
        }catch(FileNotFoundException ex){
            ex.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
