package tools;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FileConcurrentHandler{
	private List<String> lines;
	private String path;
	
	FileConcurrentHandler() {
		this.path = "";
		this.lines = new ArrayList<String>();
	}
	
	public void setPath(String path) {
		this.path = path;
	}
	
	public List<String> getArray(){
		return lines;
	}
	
	
	public void ReadFile() throws Exception{
		this.lines.clear();
		if(this.path.equals("")) {
			throw new Exception("Path not specified");
		}
		try {
		    BufferedReader reader = new BufferedReader(new FileReader(this.path.replace("\\", "\\\\")));
		    String line;
		    
		    while((line = reader.readLine()) != null) {
		    	this.lines.add(line);
		    }
		    
		    
		    
		    reader.close();
		}catch(IOException e) {
			throw e;
		}
	}
	
	public void WriteToFile() throws Exception{
		if(this.path.equals("")) {
			throw new Exception("Path not specified");
		}
		try {
		    BufferedWriter writer = new BufferedWriter(new FileWriter(this.path.replace("\\", "\\\\")));
		    
		    for(int i = 0; i < this.lines.size(); i++) {
		    	writer.write(lines.get(i));
		    	writer.newLine();
		    }
		    
		    
		    writer.close();
		}catch(IOException e) {
			throw e;
		}
	}
	
	
}