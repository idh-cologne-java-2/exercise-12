package idh.java;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
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
		// TODO: Implement
		
		
		
		BufferedReader bWriter = Files.newBufferedReader(Paths.get(f.getAbsolutePath()));
		ArrayList<String> List = new ArrayList<String>();
		Set<String> DifferentWords = new HashSet<>();
		this.getDocumentText();
		this.iterator();
		HashMap<String, Stack> MostUsed = new HashMap<String, Stack>();
		int c = 0;
		
		
		for(String token : this) {
			List.add(token);
			DifferentWords.add(token);
			
		if(!MostUsed.containsKey(token))
			MostUsed.put(token, new Stack<>());
		    Stack<String> a = MostUsed.get(token);
		    a.push(token);
		} else {
			
			String token;
			Stack<String> a = MostUsed.get(token);
			a.push(token);
		}
		
		int L = 0;
		int MostWords = 0;
		String MostUsedWords = " ";
		
		for(Stack<String> value : MostUsed.values()) {
			L = value.size();
			
			if(L > MostWords) {
				MostWords = L;
				MostUsedWords= value.get(0);
			}
		}
		
		System.out.println(MostUsedWords);
		int size = List.size();
		long WordsUnder5Letters = List.stream()
				.filter(s -> s.length() < 5).count();
		long SpecificWord = List.stream()
				.filter(s -> s.contains("blood")).count();
		int DifferentWords = DifferentWords.size();
		
		CSVPrinter csvP = new CSVPrinter(writer,CSVFormat.DEFAULT.withHeader(
			"Number of Words", "Number of Types", 
			"Number of Words which are shorter than 5 letters",
			"Frequency of the Word blood", "The most common Word",
			"The most common capitalized Word") );
		
		csvP.printRecord(size, WordsUnder5Letters, DifferentWords, SpecificWord, MostUsedWords);
		csvP.flush();
		csvP.close();
		}
		
		
	
	
	
	public static final void main(String[] args) throws IOException {
		Document d = Document.readFromFile(new File("src/main/resources/dracula.txt"));
		d.printStats(new File("target/stats.csv"));
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
