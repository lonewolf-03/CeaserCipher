package tools;
import java.io.File;


public class Test{
	public static int shift = 5;
	public static FileHandler handler = new FileHandler();
	public static SequentialCeaserCipher seqCipher = new SequentialCeaserCipher(shift);
	public static ConcurrentCeaserCipher conCipher = new ConcurrentCeaserCipher(shift);
	
	public static String unitToString(TextFileGenerator.Size u) {
		if(u == TextFileGenerator.Size.B) {
			return "B";
		}else if(u == TextFileGenerator.Size.KB) {
			return "KB";
		}else {
			return "MB";
		}
	}
	
	public static void main(String[] args) throws Exception{
		try {
			// one thread
			// change the base
			String base = System.getProperty("user.dir");
			System.out.println(base);
			String[] inputFiles = {base+"\\file1", base+"\\file2", base+"\\file3", base+"\\file4", base+"\\file5", base+"\\file6", base+"\\file7", base+"\\file8"};
			int[] inputSizes = {500, 1, 100, 500, 10, 100, 300, 500};
			TextFileGenerator.Size [] inputSizeUnits = {TextFileGenerator.Size.B, TextFileGenerator.Size.KB, TextFileGenerator.Size.KB, TextFileGenerator.Size.KB, TextFileGenerator.Size.MB, TextFileGenerator.Size.MB, TextFileGenerator.Size.MB, TextFileGenerator.Size.MB};
			
			
			String[] encryptedFiles = new String[inputFiles.length];
			String[] decryptedFiles = new String[inputFiles.length];
			float[]  sequentialEncIntervals = new float[inputFiles.length];
			float[]  sequentialDecIntervals = new float[inputFiles.length];
			Boolean[] sequentialComparisonResults = new Boolean[inputFiles.length];
			// two threads
			String[] encrypted2Files = new String[inputFiles.length];
			String[] decrypted2Files = new String[inputFiles.length];
			float[]  concurrent2EncIntervals = new float[inputFiles.length];
			float[]  concurrent2DecIntervals = new float[inputFiles.length];
			Boolean[] concurrent2ComparisonResults = new Boolean[inputFiles.length];
			
			// five threads
			String[] encrypted5Files = new String[inputFiles.length];
			String[] decrypted5Files = new String[inputFiles.length];
			float[]  concurrent5EncIntervals = new float[inputFiles.length];
			float[]  concurrent5DecIntervals = new float[inputFiles.length];
			Boolean[] concurrent5ComparisonResults = new Boolean[inputFiles.length];
			
			// seven threads
			String[] encrypted7Files = new String[inputFiles.length];
			String[] decrypted7Files = new String[inputFiles.length];
			float[]  concurrent7EncIntervals = new float[inputFiles.length];
			float[]  concurrent7DecIntervals = new float[inputFiles.length];
			Boolean[] concurrent7ComparisonResults = new Boolean[inputFiles.length];
			
			// ten threads
			String[] encrypted10Files = new String[inputFiles.length];
			String[] decrypted10Files = new String[inputFiles.length];
			float[]  concurrent10EncIntervals = new float[inputFiles.length];
			float[]  concurrent10DecIntervals = new float[inputFiles.length];
			Boolean[] concurrent10ComparisonResults = new Boolean[inputFiles.length];
			
		
			System.out.println("Creating test files and processing them...");
			for(int i = 0; i < inputFiles.length; i++) {
				TextFileGenerator.generateFile(inputFiles[i], inputSizes[i], inputSizeUnits[i]);
				seqCipher.setInputPath(inputFiles[i]);
				seqCipher.setOutputPath(inputFiles[i]  + "-1.enc");
				encryptedFiles[i] =  inputFiles[i]  + "-1.enc";
				seqCipher.encrypt();
				sequentialEncIntervals[i] = seqCipher.getLastCalculatedInterval();
				seqCipher.setInputPath(inputFiles[i]  + "-1.enc");
				seqCipher.setOutputPath(inputFiles[i]  + "-1.dec");
				decryptedFiles[i] = inputFiles[i]  + "-1.dec";
				seqCipher.decrypt();
				sequentialDecIntervals[i] = seqCipher.getLastCalculatedInterval();
				sequentialComparisonResults[i] = handler.compare(decryptedFiles[i], inputFiles[i]);
				
				conCipher.setInputPath(inputFiles[i]);
				conCipher.setOutputPath(inputFiles[i]  + "-2.enc");
				encrypted2Files[i] =  inputFiles[i]  + "-2.enc";
				conCipher.encrypt();
				concurrent2EncIntervals[i] = conCipher.getLastCalculatedInterval();
				conCipher.setInputPath(inputFiles[i]  + "-2.enc");
				conCipher.setOutputPath(inputFiles[i]  + "-2.dec");
				decrypted2Files[i] = inputFiles[i]  + "-2.dec";
				conCipher.decrypt();
				concurrent2DecIntervals[i] = conCipher.getLastCalculatedInterval();
				concurrent2ComparisonResults[i] = handler.compare(decrypted2Files[i], inputFiles[i]);
				
				conCipher.setInputPath(inputFiles[i]);
				conCipher.setOutputPath(inputFiles[i]  + "-5.enc");
				encrypted5Files[i] =  inputFiles[i]  + "-5.enc";
				conCipher.encrypt();
				concurrent5EncIntervals[i] = conCipher.getLastCalculatedInterval();
				conCipher.setInputPath(inputFiles[i]  + "-5.enc");
				conCipher.setOutputPath(inputFiles[i]  + "-5.dec");
				decrypted5Files[i] = inputFiles[i]  + "-5.dec";
				conCipher.decrypt();
				concurrent5DecIntervals[i] = conCipher.getLastCalculatedInterval();
				concurrent5ComparisonResults[i] = handler.compare(decrypted5Files[i], inputFiles[i]);
				
				conCipher.setInputPath(inputFiles[i]);
				conCipher.setOutputPath(inputFiles[i]  + "-7.enc");
				encrypted7Files[i] =  inputFiles[i]  + "-7.enc";
				conCipher.encrypt();
				concurrent7EncIntervals[i] = conCipher.getLastCalculatedInterval();
				conCipher.setInputPath(inputFiles[i]  + "-7.enc");
				conCipher.setOutputPath(inputFiles[i]  + "-7.dec");
				decrypted7Files[i] = inputFiles[i]  + "-7.dec";
				conCipher.decrypt();
				concurrent7DecIntervals[i] = conCipher.getLastCalculatedInterval();
				concurrent7ComparisonResults[i] = handler.compare(decrypted7Files[i], inputFiles[i]);
				
				conCipher.setInputPath(inputFiles[i]);
				conCipher.setOutputPath(inputFiles[i]  + "-10.enc");
				encrypted10Files[i] =  inputFiles[i]  + "-10.enc";
				conCipher.encrypt();
				concurrent10EncIntervals[i] = conCipher.getLastCalculatedInterval();
				conCipher.setInputPath(inputFiles[i]  + "-10.enc");
				conCipher.setOutputPath(inputFiles[i]  + "-10.dec");
				decrypted10Files[i] = inputFiles[i] + "-10.dec";
				conCipher.decrypt();
				concurrent10DecIntervals[i] = conCipher.getLastCalculatedInterval();
				concurrent10ComparisonResults[i] = handler.compare(decrypted10Files[i], inputFiles[i]);
			}
			
			
			handler.setPath(base + "\\statistics");
			handler.AddToBuffer("Test Results: ");
			for(int i = 0; i < inputFiles.length; i++) {
				handler.AddToBuffer("Filename: " + inputFiles[i]);
				handler.AddToBuffer("Filesize: " + String.valueOf(inputSizes[i]) + unitToString(inputSizeUnits[i]));
				handler.AddToBuffer(String.format("%25s%25s%25s%25s%25s%25s", " ", "One thread", "Two threads", "Five threads", "Seven threads", "Ten threads"));
				handler.AddToBuffer(String.format("%25s%25s%25s%25s%25s%25s", "Encryption time(ns)", String.valueOf(sequentialEncIntervals[i]), String.valueOf(concurrent2EncIntervals[i]), String.valueOf(concurrent5EncIntervals[i]), String.valueOf(concurrent7EncIntervals[i]), String.valueOf(concurrent10EncIntervals[i])));
				handler.AddToBuffer(String.format("%25s%25s%25s%25s%25s%25s", "Deccryption time(ns)", String.valueOf(sequentialDecIntervals[i]), String.valueOf(concurrent2DecIntervals[i]), String.valueOf(concurrent5DecIntervals[i]), String.valueOf(concurrent7DecIntervals[i]), String.valueOf(concurrent10DecIntervals[i])));
				handler.AddToBuffer(String.format("%25s%25s%25s%25s%25s%25s", "Comparison result", String.valueOf(sequentialComparisonResults[i]), String.valueOf(concurrent2ComparisonResults[i]), String.valueOf(concurrent5ComparisonResults[i]), String.valueOf(concurrent7ComparisonResults[i]), String.valueOf(concurrent10ComparisonResults[i])));
			}
			handler.WriteToFile();

			System.out.println("Result files and statistics file created successfully at " + base);
		    
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}