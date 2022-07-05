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
	
	public void printStats(File f) throws IOException {
		// TODO: Implement
		f.createNewFile();
		
		FileWriter fw = new FileWriter(f);
		
		Document d = Document.readFromFile(new File("src/main/resources/dracula.txt"));
		
		ArrayList<String> al = new ArrayList<String>();
		
		for (String token : d) {
			al.add(token);
		}
		
		long words = al.stream().count();
		long types = al.stream().distinct().count();
		long shortWords = al.stream().filter(a -> a.length() < 5).count();
		long blood = al.stream().filter(b -> b.equals("blood")).count();
		String mostFrequent = al.stream().collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
				.entrySet()
				.stream()
				.max(Map.Entry.comparingByValue())
				.map(Map.Entry::getKey).orElse("");
		String mostUppercase = al.stream().filter(c -> c.toCharArray()[0] == c.toUpperCase().toCharArray()[0])
				.collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
				.entrySet()
				.stream()
				.max(Map.Entry.comparingByValue())
				.map(Map.Entry::getKey).orElse("");
		
		fw.append(words + "," + types + "," + shortWords + "," + blood + "," + mostFrequent + "," + mostUppercase + "\n");
		
		fw.flush();
		fw.close();
		System.out.println("Anzahl der Wörter: " + words);
		System.out.println("Anzahl der Types: " + types);
		System.out.println("Anzahl der Wörter unter 5 Zeichen: " + shortWords);
		System.out.println("Häufigkeit des Wortes \"blood\": " + blood);
		System.out.println("Häufigstes Wort: " + mostFrequent);
		System.out.println("Häufigstes großgeschriebenes Wort: " + mostUppercase);
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
