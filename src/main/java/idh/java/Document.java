package idh.java;



import org.apache.commons.csv.CSVFormat; 
import org.apache.commons.csv.CSVPrinter;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.Stack;
import java.util.StringTokenizer;

import org.apache.commons.io.FileUtils;

public class Document implements Iterable<String> {
	String documentText;

	public static Document readFromFile(File f) throws IOException {
		
		Document doc = new Document();
		doc.documentText = FileUtils.readFileToString(f, "UTF-8");
		
		return doc;
	}
	
	public String getDocumentText() {
		return documentText;
	}

	public void setDocumentText(String documentText) {
		this.documentText = documentText;
	}
	
	public void printStats(File f) {
			try {
				
				BufferedWriter writer = Files.newBufferedWriter(Paths.get(f.getAbsolutePath()));
				ArrayList<String> wortliste = new ArrayList<String>();
				Set<String> differentwords = new HashSet<String>();
				this.getDocumentText();
				this.iterator();
				HashMap<String,Stack<String>> mostUsed = new HashMap<String,Stack<String>>();
				int g = 0;
				for(String token : this) {
					wortliste.add(token);
					differentwords.add(token);
					if(!(mostUsed.containsKey(token))) {
						mostUsed.put(token, new Stack<String>());
						Stack<String> i = mostUsed.get(token);
						
						i.push(token);
					}else {
						
						Stack<String> i = mostUsed.get(token);
						i.push(token);
					}
					
				}
				int l = 0; 
				int mostWord = 0;
				String mostUsedWord  = ""; 
			for(Stack<String> value : mostUsed.values()) {
				l = value.size();
				
				if(l > mostWord) {
					mostWord = l; 
					mostUsedWord = value.get(0);
				}
				
			}
				
				System.out.println(mostUsedWord);
				int size = wortliste.size();				
			 	long  smallerThan5 = wortliste.stream().filter(s -> s.length() < 5).count();
			 	long blood =  wortliste.stream().filter(s -> s.contains("blood")).count();
			 	int differentw = differentwords.size();
			 	
			 	
			 	
			 	
			 	CSVPrinter csvprinter = new CSVPrinter(writer,CSVFormat.DEFAULT.withHeader(
			 			"AnzahlDerWorte", "Worte < 5", "VerschiedeneWorte", "Am MeistenBenutzt", "wie oft blood vorkommt",
			 			"das am häufigsten vorkommende großgeschriebene Wort")
			 			);
			 	
			 	csvprinter.printRecord(size,smallerThan5, differentw,blood,mostUsedWord);
			 	csvprinter.flush();
			 	csvprinter.close();
			 	
			}catch(IOException e ) {
				e.printStackTrace();
			}
		
		return;
		
		// TODO: Implement
	}
	
	public static final void main(String[] args) throws IOException {
		Document d = Document.readFromFile(new File("src/main/resources/dracula.txt"));
		d.printStats(new File("bin/target/stats.csv"));
	}

	public Iterator<String> iterator() {
		return new Iterator<String>() {

			StringTokenizer tokenizer = new StringTokenizer(documentText);
			
			@Override
			public boolean hasNext() {
				return tokenizer.hasMoreTokens();
			}

			@Override
			public String next() {
				return tokenizer.nextToken();
			}
			
		};
	}
	
	
}
