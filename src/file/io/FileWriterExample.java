package file.io;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FileWriterExample {

    public static void main(String[] args) {
        try {
            FileWriter fileWriter = new FileWriter(new File("writerout.txt"));
            SimpleDateFormat format = new SimpleDateFormat("MMM dd, yyyy");

            String dateString = format.format( new Date()   );

            for (int i=1; i <=2000; i++){
                String line2Write = "line "+i +", "+ dateString +"\n";
                fileWriter.write(line2Write);
            }

            fileWriter.flush();
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
