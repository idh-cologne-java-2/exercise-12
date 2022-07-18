package idh.java;


import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.StringTokenizer;
import java.util.ArrayList;
import java.util.Map;
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
	
	public void printStats(File f) throws IOException {
		
		Document d = Document.readFromFile(f);
		ArrayList<String> al = new ArrayList<String>();
		
		for(String token : d) {
			al.add(token);
		}
		
		long allwords = al.stream().count();
		long types = al.stream().distinct().count();
		long shorterthan5 = al.stream().filter(s -> s.length() < 5).count();
		long blood = al.stream().filter(b -> b.contains("blood")).count();
		String mostfrequent = al.stream().collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
				.entrySet().stream().max(Map.Entry.comparingByValue()).map(Map.Entry::getKey).orElse("");
		String mostfrequentuc = al.stream().filter(u -> u.toCharArray()[0] == u.toUpperCase().toCharArray()[0])
				.collect(Collectors.groupingBy(Function.identity(),
				Collectors.counting())).entrySet().stream().max(Map.Entry.comparingByValue()).map(Map.Entry::getKey).orElse("");
		
		System.out.println("Anzahl der Wörter: " + allwords);
		System.out.println("Anzahl der Types: " + types);
		System.out.println("Anzahl der Wörter, die kürzer sind als 5 Zeichen: " + shorterthan5);
		System.out.println("Am häufigsten vorkommendes Wort: " + mostfrequent);
		System.out.println("Am häufigsten vorkommendes großgeschriebenes Wort: " + mostfrequentuc);
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
