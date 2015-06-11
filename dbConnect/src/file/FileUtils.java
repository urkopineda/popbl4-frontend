package file;

import java.io.BufferedReader;
import java.io.BufferedWriter;
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
             bufferedWriter.write("\r\n");
             bufferedWriter.write("USER="+Configuration.user);
             bufferedWriter.write("\r\n");
             bufferedWriter.write("PASS="+Configuration.password);
             bufferedWriter.write("\r\n");
             bufferedWriter.write("DBURL="+Configuration.dbUrl);
             bufferedWriter.write("\r\n");
             bufferedWriter.write("DBNAME="+Configuration.dbName);
             bufferedWriter.write("\r\n");
             bufferedWriter.write("DBPORT="+Configuration.port);
             bufferedWriter.write("\r\n");
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
            Configuration.lang = Integer.parseInt(lang[1]);
            if (lines.size() > 1) {
                String [] user = lines.get(1).split("=");
                String [] password = lines.get(2).split("=");
                String [] dbUrl = lines.get(3).split("=");
                String [] dbName = lines.get(4).split("=");
                String [] dbPort = lines.get(5).split("=");
                Configuration.user = user[1];
                Configuration.password = password[1];
                Configuration.dbUrl = dbUrl[1];
                Configuration.dbName = dbName[1];
                Configuration.port = Integer.parseInt(dbPort[1]);
            }
            bufferedReader.close();            
        } catch (Exception e) {}
    }
}
