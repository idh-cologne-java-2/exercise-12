package idh.java;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVFormat.Builder;
import org.apache.commons.csv.CSVPrinter;
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
	
		//Anzahl der Wörter
		ArrayList<String> words = new ArrayList<String>();
		Iterator<String> iter = words.iterator();
		while(iter.hasNext()) {
			words.add(getDocumentText());
		}
		for(String s : this) {
			words.add(s);
		}
		
		long wordCount = words.size();
//		System.out.println(wordCount);

		//Anzahl Types
		//Lösung 1: HashSet
//		Set<String> types = new HashSet<String>();
//		
//		for (String s : words) {
//			types.add(s);
//		}
//		long anzahlTypes = types.size();
		
//		System.out.println(anzahlTypes);
		//Lösung 2: Stream
		
		Stream<String> stream = words.stream();
		long types = stream
		        .distinct()
		        .count();
//		System.out.println(types);
		
		//Anzahl Wörter < 5 Zeichen
		long wordsOverFive = words.stream()
				.filter(s -> s.length() > 5)
				.count();
		
//		System.out.println(wordsOverFive);
		
		//Häufigkeit des Wortes "blood"
		long bloodCount = words.stream()
				.filter(s -> s.equals("blood"))
				.count();
		
//		System.out.println(bloodCount);
		
//		Das am häufigsten vorkommende Wort

	  //Lösung 1: Via Hashmap
	  Map<String, Integer> mostUsedWordsMap = new HashMap<String, Integer>();

	    for (String s : words) {
	        if (mostUsedWordsMap.get(s) != null) {
	            Integer count = mostUsedWordsMap.get(s) + 1;
	            mostUsedWordsMap.put(s, count);
	        } else {
	            mostUsedWordsMap.put(s, 1);
	        }
	    }
	    Object maxEntry = Collections.max(mostUsedWordsMap.entrySet(), Map.Entry.comparingByValue()).getKey();
//	    System.out.println( maxEntry);
	    
	    //Lösung 2: via stream
	    String hw 
	    = words.stream()
	          .collect(Collectors.groupingBy(w -> w, Collectors.counting()))
	          .entrySet()
	          .stream()
	          .max(Comparator.comparing(Entry::getValue))
	          .get()
	          .getKey();
	    
//	    System.out.println( hw);

		
// 		Das am häufigsten vorkommende Großgeschriebene Wort (Surprise: I)	    
	    String hwG
	    = words.stream().filter(s -> containsUpperCase(s))
	    		 .collect(Collectors.groupingBy(w -> w, Collectors.counting()))
		          .entrySet()
		          .stream()
		          .max(Comparator.comparing(Entry::getValue))
		          .get()
		          .getKey();
	    
//	    System.out.println( hwG);
	    
	    
	    try (CSVPrinter printer = new CSVPrinter(new FileWriter(f), CSVFormat.EXCEL)) {
			printer.printRecord("Anzahl Wörter" + "Anzahl Types" + "Anzahl Wörter < 5 Zeichen" + "Häufigkeit des Wortes \"blood\""+ "Das am häufigsten vorkommende Wort" + "Das am häufigsten vorkommende großgeschriebene Wort");
			printer.printRecord(wordCount + "" + types + "" + wordsOverFive + hw + " " + hwG);
		    printer.close();

		} catch (IOException ex) {
			ex.printStackTrace();
		}
		
			        
		
	}
	
	public boolean containsUpperCase(String s) {
		return Character.isUpperCase(s.charAt(0));
	    
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
