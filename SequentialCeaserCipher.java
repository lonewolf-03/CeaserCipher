package tools;

public class SequentialCeaserCipher{
	private FileHandler handler;
	private String inputPath;
	private String outputPath;
	private int shift;
	private float lastCalculatedInterval;
	
	SequentialCeaserCipher(int s) throws IllegalArgumentException{
		if(s <= 0) {
			throw new IllegalArgumentException();
		}
		this.handler = new FileHandler();
		this.shift = s;
	}
	
	public float getLastCalculatedInterval() {
		return this.lastCalculatedInterval;
	}
	
	public void encrypt() {
		try {
			this.handler.setPath(this.inputPath);
			this.handler.ReadFile();
			this.handler.setToBeginning();
			String line = handler.getCurrentLine();
			float start, end;
			start = System.nanoTime();
			while(line != null) {
				String line2;
				char[] newline = new char[line.length()];
				for(int i = 0; i < line.length(); i++) {
					char c = line.charAt(i);
					if(!Character.isWhitespace(c)) {
						newline[i] = (char) (c + this.shift);
					}else {
						newline[i] = c;
					}
				}
				line2 = String.copyValueOf(newline);
				this.handler.setCurrentLine(line2);
				this.handler.next();
				line = this.handler.getCurrentLine();
			}
			end = System.nanoTime();
			this.lastCalculatedInterval = end - start;
			this.handler.setPath(this.outputPath);
			this.handler.WriteToFile();
			
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void decrypt(){
		try {
			this.handler.setPath(this.inputPath);
			this.handler.ReadFile();
			this.handler.setToBeginning();
			String line = handler.getCurrentLine();
			float start, end;
			start = System.nanoTime();
			while(line != null) {
				String line2;
				char[] newline = new char[line.length()];
				for(int i = 0; i < line.length(); i++) {
					char c = line.charAt(i);
					if(!Character.isWhitespace(c)) {
						newline[i] = (char) (c - this.shift);
					}else {
						newline[i] = c;
					}
				}
				line2 = String.copyValueOf(newline);
				this.handler.setCurrentLine(line2);
				this.handler.next();
				line = this.handler.getCurrentLine();
			}
			end = System.nanoTime();
			
			this.lastCalculatedInterval = end - start;
			this.handler.setPath(this.outputPath);
			this.handler.WriteToFile();
			
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void setOutputPath(String path) {
		this.outputPath = path;
	}
	
	public void setInputPath(String path) {
		this.inputPath = path;
	}
}