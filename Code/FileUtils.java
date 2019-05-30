import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FileUtils {
	//read a file, then put it is a string list
	public static List<String> readFile(String fileName){ 
		File file = new File(fileName);
		if(!file.exists()){
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
        BufferedReader reader = null;
        List<String> res = new ArrayList<String>();
        try {
            reader = new BufferedReader(new FileReader(file));
            String tempString = null;
            while ((tempString = reader.readLine()) != null) {
            	res.add(tempString);
            } //adding the string
            reader.close();
        } catch (IOException e) { //catch exception
            e.printStackTrace();
        } finally {
            if (reader != null) { 
                try {
                    reader.close();
                } catch (IOException e1) {
                }
            }
        }
		return res;
	}
//write a file	
	public static void writeFile(String fileName, List<String> contents, boolean isAddToTail){
		try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, isAddToTail)); //decide which one to write
            for(String string : contents){
            	writer.write(string);
            	writer.newLine();
            }
            writer.close();
        } catch (IOException e) { //catch exception
            e.printStackTrace();
        }
	}
}
