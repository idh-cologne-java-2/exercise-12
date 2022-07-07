package idh.java;


import java.io.*;
import java.util.*;
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
	
	public void printStats(File f) {
		this.getDocumentText();
		this.iterator();
		ArrayList<String> words = new ArrayList<String>();
		
		for (String token : this) {
			words.add(token);
		}
		
		long laenge = words.stream().count();
		long worte = words.stream().distinct().count();
		long kurz = words.stream().filter(s -> (s.length()<5)).count();
		long blood = words.stream().filter(s -> s.contains("blood")).count();
		String oft = words.stream().collect(Collectors.groupingBy(Function.identity(), Collectors.counting())).entrySet().stream()
						.max(Map.Entry.comparingByValue()).map(Map.Entry::getKey).orElse("");
		String oftGross = words.stream().filter(u -> u.toCharArray()[0] == u.toUpperCase().toCharArray()[0])
							.collect(Collectors.groupingBy(Function.identity(), Collectors.counting())).entrySet().stream()
							.max(Map.Entry.comparingByValue()).map(Map.Entry::getKey).orElse("");
				
		try (CSVPrinter printer = new CSVPrinter(new FileWriter(f), CSVFormat.EXCEL)) {
		     printer.printRecord("Anzahl der W�rter", "Anzahl unterschiedlicher Worte", "Worte k�rzer als 5 Zeichen", "H�ufigkeit von 'Blood'", "H�ufigstes Wort", "H�ufigstes gro�geschriebenes Wort");
		     printer.printRecord(laenge, worte, kurz, blood, oft, oftGross);
		 } catch (IOException ex) {
		     ex.printStackTrace();
		 }
		
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
