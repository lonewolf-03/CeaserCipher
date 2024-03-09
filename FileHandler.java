package tools;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FileHandler{
	private List<String> lines;
	private String path;
	private int currentIndex;
	
	FileHandler() {
		this.path = "";
		this.lines = new ArrayList<String>();
		currentIndex = -1;
	}
	
	public long calculateFileSize(String path) throws Exception{
		try {
			File file = new File(path.replace("\\", "\\\\"));
			return file.length();
		}catch(Exception e) {
			throw e;
		}
	}
	
	public Boolean compare(String path1, String path2) throws Exception{
		if(path1.equals("") || path1.equals("")) {
			throw new Exception("Path not specified");
		}
		try {
		    BufferedReader reader1 = new BufferedReader(new FileReader(path1.replace("\\", "\\\\")));
		    BufferedReader reader2 = new BufferedReader(new FileReader(path2.replace("\\", "\\\\")));
		    String line1;
		    String line2;
		    
		    while(((line1 = reader1.readLine()) != null) && ((line2 = reader2.readLine()) != null)) {
		    	if(!line1.contentEquals(line2)) {
		    		reader1.close();
				    reader2.close();
				    return false;
		    	}
		    }
		    

		    
		    if((reader1.readLine() == null) ^ (reader2.readLine() == null)) {
		    	// to account for the case when the files are identical but one of them is not complete
		    	reader1.close();
			    reader2.close();
		    	return false;
		    }else {
		    	reader1.close();
			    reader2.close();
		    	return true;	
		    }
		    
		}catch(IOException e) {
			throw e;
		}
	}
	
	public void setPath(String path) {
		this.path = path;
	}
	
	public int getSize() {
		return this.lines.size();
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
	
	public void setCurrentLine(String line){
		this.lines.set(this.currentIndex, line);
	}
	
	public void AddToBuffer(String line) {
		this.lines.add(line);
	}
	
	public String getCurrentLine() {
		if(this.currentIndex < 0 && this.lines.size() > 0) {
			this.currentIndex++;
		}
		if(this.currentIndex >= this.lines.size()) {
			return null;
		}
		return this.lines.get(this.currentIndex);
	}
	
	public Boolean next() {
		if(this.currentIndex < this.lines.size()) {
			this.currentIndex++;
			return true;
		}
		return false;
	}
	
	public int getCurrentIndex() {
		return this.currentIndex;
	}
	
	public void setToBeginning() {
		this.currentIndex = 0;
	}
	
	public void setToEnd() {
		this.currentIndex = this.lines.size() - 1;
	}
}