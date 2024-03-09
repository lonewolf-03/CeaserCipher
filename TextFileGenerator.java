package tools;

import java.io.FileWriter;
import java.io.IOException;

//generate a text file with the given name and Size in kilo bytes
public class TextFileGenerator {
    public enum Size {
        B,
        KB, 
        MB, 
    }
    public static void generateFile(String fileName, int size , Size unit) {
        try {
            FileWriter writer = new FileWriter(fileName);
            for (long i = 0; i < size * (unit == Size.B ? 1 : (unit == Size.KB)? 1024 : (unit == Size.MB)? 1024 * 1024 : 0) ; i++) {
            	if(i % 50 == 0) {
            		writer.write("\n");
            	}
                writer.write(Character.toString((char) (Math.random() * 26 + 'a')));
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}