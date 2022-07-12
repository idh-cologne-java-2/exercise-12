package idh.java;


import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.function.Function;
import java.util.stream.Collectors;

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
	
	public void printStats(File f) throws IOException{
		f.createNewFile();
		FileWriter file = new FileWriter(f);
		Document d = Document.readFromFile(new File("src/main/resources/dracula.txt"));
		ArrayList<String> fl = new ArrayList<String>();
		
		for(String token : d) {
			fl.add(token);
		}
		
		long allWords = fl.stream().count();
		long types = fl.stream().distinct().count();
		long shortTokens = fl.stream().filter(s -> s.length() < 5).count();
		long blood = fl.stream().filter(b -> b.contains("blood")).count();
		String mostCommonWord = fl.stream().collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
				.entrySet().stream().max(Map.Entry.comparingByValue()).map(Map.Entry::getKey).orElse("");
		String mostCapitalizedWord = fl.stream().filter(u -> u.toCharArray()[0] == u.toUpperCase().toCharArray()[0])
				.collect(Collectors.groupingBy(Function.identity(),
				Collectors.counting())).entrySet().stream().max(Map.Entry.comparingByValue()).map(Map.Entry::getKey).orElse("");
		
		
		file.append(allWords + "," + types + "," + shortTokens + "," + blood + "," + mostCommonWord + "," + mostCapitalizedWord + "\n");
		
		file.flush();
		file.close();
		
		System.out.println("Total words: " + allWords);
		System.out.println("Types of words: " + types);
		System.out.println("Words under five signs: " + shortTokens);
		System.out.println("Frequency of the word blood: " + blood);
		System.out.println("Most common word: " + mostCommonWord);
		System.out.println("Most frequently capitalized word " + mostCapitalizedWord);
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
