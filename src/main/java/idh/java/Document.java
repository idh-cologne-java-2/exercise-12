package idh.java;


import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
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
	
	public void printStats(File f) throws IOException {
		// TODO: Implement
		f.createNewFile();
		FileWriter fw = new FileWriter(f);
		//ben�tigtes Dokument
		Document d = Document.readFromFile(new File("src/main/resources/dracula.txt"));
		//Liste die gefilterte Dinge speichert
		ArrayList<String> filtered = new ArrayList<String>();
		
		for (String token : d) {
			filtered.add(token);
		}
		
		long allWords = filtered.stream().count();
		long types = filtered.stream().distinct().count();
		long shortTokens = filtered.stream().filter(s -> s.length() < 5).count();
		long blood = filtered.stream().filter(b -> b.contains("blood")).count();
		String mostFrequentWord = filtered.stream().collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
			      .entrySet()
			      .stream()
			      .max(Map.Entry.comparingByValue())
			      .map(Map.Entry::getKey).orElse("");
		String mostUppercaseWord = filtered.stream().filter(u -> u.toCharArray()[0] == u.toUpperCase().toCharArray()[0])
				.collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
				.entrySet()
				.stream()
				.max(Map.Entry.comparingByValue())
				.map(Map.Entry::getKey).orElse("");
		
		
		fw.append(allWords + ","+ types + ","+ shortTokens + "," + blood + "," + mostFrequentWord + "," + mostUppercaseWord + "\n");
		
		fw.flush();
		fw.close();
		System.out.println("Gesamtw�rter: " + allWords);
		System.out.println("Anzahl der Types: " + types);
		System.out.println("W�rter unter 5 Zeichen: " + shortTokens);
		System.out.println("H�ufigkeit des Wortes Blut: " + blood);
		System.out.println("H�ufigstes Wort: " + mostFrequentWord);
		System.out.println("H�ufigstes gro�geschriebenes Wort: " + mostUppercaseWord);
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
