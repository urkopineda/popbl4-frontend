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
    public static void writeConfFile() {
    	 try {
             FileWriter fileWriter = new FileWriter(Configuration.confFile);
             BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
             bufferedWriter.write("LANG="+Configuration.lang);
             bufferedWriter.close();
         } catch (IOException ex) {
        	 
         }
    }
    
    public static void readConfFile() {
    	String line = null;
    	try {
    		ArrayList<String> lines = new ArrayList<>();
    		FileReader fileReader = new FileReader(Configuration.confFile);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            while((line = bufferedReader.readLine()) != null) {
            	lines.add(line);
            }
            String [] lang = lines.get(0).split("=");
            System.out.println(lang[1]);
            Configuration.lang = Integer.parseInt(lang[1]);
            bufferedReader.close();            
        } catch (FileNotFoundException ex) {
                         
        } catch(IOException ex) {
            
        }
    }
}
