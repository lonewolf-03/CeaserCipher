package tools;

import java.util.List;
import java.lang.Runnable;

public class ConcurrentCeaserCipher{
	private FileConcurrentHandler handler;
	private String inputPath;
	private String outputPath;
	private int shift;
	private int numOfThreads;
	private Thread[] encryptionThreads;
	private Thread[] decryptionThreads;
	private List<String> linesArr;
	private float lastCalculatedInterval;
	
	ConcurrentCeaserCipher(int s) throws IllegalArgumentException{
		if(s <= 0) {
			throw new IllegalArgumentException();
		}
		this.numOfThreads = 2; // default
		this.handler = new FileConcurrentHandler();
		this.shift = s;
		this.encryptionThreads = new Thread[this.numOfThreads + 1];
		this.decryptionThreads = new Thread[this.numOfThreads + 1];
	}
	
	public float getLastCalculatedInterval() {
		return this.lastCalculatedInterval;
	}
	
	public void encrypt() {
		try {
			this.handler.setPath(this.inputPath);
			this.handler.ReadFile();
			this.linesArr = this.handler.getArray();
			final int arraySize = this.linesArr.size();
			int counter = 0;
			int i;
			for(i = 0; i < arraySize; i+=(arraySize / this.numOfThreads) + ((arraySize < this.numOfThreads) ? 1: 0)) {
				final int curr = i;
				Runnable partialEncrypt = () -> {
					final int n = this.numOfThreads;
					final List<String> arr = this.linesArr;
					final int s = this.shift;
					int begin = curr;
					int end = curr + (arraySize / n);
					if(end >= arraySize) {
						end = arraySize;
					}
				    for(int j = begin; j < end; j++) {
				    	String line = arr.get(j);
						char[] newline = new char[line.length()];
						for(int k = 0; k < line.length(); k++) {
							char c = line.charAt(k);
							if(!Character.isWhitespace(c)) {
								newline[k] = (char) (c + s);
							}else {
								newline[k] = c;
							}
						}
						arr.set(j, String.copyValueOf(newline));
				    }
				};
				this.encryptionThreads[counter] = new Thread(partialEncrypt);
				counter++;
			}
			
			float start;
			float end;
			start = System.nanoTime();
			for(int j = 0; j < this.numOfThreads; j++) {
			    if(this.encryptionThreads[j] != null) { // this condition is only important when the array of lines contain only one line 
				    this.encryptionThreads[j].start();
				}
			}
			if(this.encryptionThreads[this.numOfThreads] != null) {
				this.encryptionThreads[this.numOfThreads].start();
			}
			
			for(int j = 0; j < this.numOfThreads; j++) {
				if(this.encryptionThreads[j] != null) {
				    this.encryptionThreads[j].join();
				}
			}
			if(this.encryptionThreads[this.numOfThreads] != null) {
				this.encryptionThreads[this.numOfThreads].join();
			}
			end = System.nanoTime();
			
			this.lastCalculatedInterval = end - start;
			for(int j = 0; j < this.numOfThreads + 1; j++) {
				this.encryptionThreads[j] = null;
			}
			
			
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
			this.linesArr = this.handler.getArray();
			final int arraySize = this.linesArr.size();
			int counter = 0;
			int i;
			for(i = 0; i < arraySize; i+=(arraySize / this.numOfThreads) + ((arraySize < this.numOfThreads) ? 1: 0)) {
				final int curr = i;
				Runnable partialDecrypt = () -> {
					final int n = this.numOfThreads;
					final List<String> arr = this.linesArr;
					final int s = this.shift;
					int begin = curr;
					int end = curr + (arraySize / n);
					if(end >= arraySize) {
						end = arraySize;
					}
				    for(int j = begin; j < end; j++) {
				    	String line = arr.get(j);
						char[] newline = new char[line.length()];
						for(int k = 0; k < line.length(); k++) {
							char c = line.charAt(k);
							if(!Character.isWhitespace(c)) {
								newline[k] = (char) (c - s);
							}else {
								newline[k] = c;
							}
						}
						arr.set(j, String.copyValueOf(newline));
				    }
				};
				this.decryptionThreads[counter] = new Thread(partialDecrypt);
				counter++;
			}
			
			float start, end;
			start = System.nanoTime();
			for(int j = 0; j < this.numOfThreads; j++) {
				if(this.decryptionThreads[j] != null) {
				    this.decryptionThreads[j].start();
				}
			}
			if(this.decryptionThreads[this.numOfThreads] != null) {
				this.decryptionThreads[this.numOfThreads].start();
			}
			
			for(int j = 0; j < this.numOfThreads; j++) {
				if(this.decryptionThreads[j] != null) {
				    this.decryptionThreads[j].join();
				}
			}
			if(this.decryptionThreads[this.numOfThreads] != null) {
				this.decryptionThreads[this.numOfThreads].join();
			}
			end = System.nanoTime();
			
			this.lastCalculatedInterval = end - start;
			
			for(int j = 0; j < this.numOfThreads + 1; j++) {
				this.decryptionThreads[j] = null;
			}
			
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
	
	public void setNumberOfThreads(int t) throws IllegalArgumentException{
		if(t <= 1) {
			throw new IllegalArgumentException();
		}
		
		this.numOfThreads = t;
	}
}