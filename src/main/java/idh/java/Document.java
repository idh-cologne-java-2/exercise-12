package idh.java;


import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.apache.commons.csv.*;
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
		
		int length = 0;
		int typeNumber = 0;
		int shortWords = 0;
		int identicalWords = 0;
		String frequentWord;
		String frequentCapitalWord;
		
		String text = this.getDocumentText();
		StringTokenizer st = new StringTokenizer(text, " ");
		List<String> tokens = new ArrayList<String>();
		while(st.hasMoreTokens()) {
			tokens.add(st.nextToken());
		}
		
		length = tokens.size();
		
		HashSet<String> hs = new HashSet<String>();
		
		for(String token : tokens) {
			//Für die Zahl der gleichen Wörter
			hs.add(token);
			
			//Für die Zahl der Wörter mit weniger als 5 Buchstaben
			if(token.length() < 5) {
				shortWords++;
			}
			
			//Für die Häufigkeit des Wortes "blood"
			if(token.equals("blood")) {
				identicalWords++;
			}
		}
		
		typeNumber = hs.size();
		
		frequentWord = tokens.stream()
				.collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
				.entrySet()
				.stream()
				.max(Map.Entry.comparingByValue())
				.map(Map.Entry::getKey)
				.orElse("");
		
		frequentCapitalWord = tokens.stream()
				.filter(u -> u.toCharArray()[0] == u.toUpperCase().toCharArray()[0])
				.collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
				.entrySet()
				.stream()
				.max(Map.Entry.comparingByValue())
				.map(Map.Entry::getKey)
				.orElse("");
		
		f.createNewFile();
		FileWriter fw = new FileWriter(f);
		fw.append("Wörterzahl, " + "Zahl unterschiedlicher Wörter, " + "Wörter mit weniger als 5 Buchstaben, " + "Frequenz von \"blood\", " + "Häufigste Wort, " + "Häufigste groß geschrieben Wort" + "\n");
		fw.append(length + ", " + typeNumber + ", " + shortWords + ", " + identicalWords + ", " + frequentWord + ", " + frequentCapitalWord);
		
		fw.flush();
		fw.close();
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
