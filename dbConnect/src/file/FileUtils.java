package file;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import main.Configuration;

public class FileUtils {
    static String fileName = "conf.dat";
    static String line = null;
    
    public static void writeConfFile() {
    	 try {
             FileWriter fileWriter = new FileWriter(fileName);
             BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
             bufferedWriter.write("LANG="+Configuration.lang);
             bufferedWriter.close();
         } catch (IOException ex) {
        	 
         }
    }
    
    public static void readConfFile() {
    	try {
    		ArrayList<String> lines = new ArrayList<>();
    		FileReader fileReader = new FileReader(fileName);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            while((line = bufferedReader.readLine()) != null) {
            	lines.add(line);
            }
            String [] lang = lines.get(0).split("=");
            Configuration.lang = Integer.parseInt(lang[1]);
            bufferedReader.close();            
        } catch (FileNotFoundException ex) {
                         
        } catch(IOException ex) {
            
        }
    }
}
