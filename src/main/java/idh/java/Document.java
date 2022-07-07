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
	
	public void printStats(File f)throws IOException {

		f.createNewFile();
		FileWriter fileWriter = new FileWriter(f);
		fileWriter.append("Counted words," + "Number of types," + "Number of words shorter than 5 characters, "
				+ "Frequency of the word \"blood\"," + "The most common word," + "The most common capitalized word"
				+ "\n");
		
		ArrayList<String> arrayList = new ArrayList<String>();

		for (String token : this) {
			arrayList.add(token);
		}

		long numberofwords = arrayList.stream().count();
		long types = arrayList.stream().distinct().count();
		long shortWords = arrayList.stream().filter(w -> w.length() < 5).count();
		long blood = arrayList.stream().filter(b -> b.equals("blood")).count();

		String mostCommonWord = arrayList.stream()
				.collect(Collectors.groupingBy(Function.identity(), Collectors.counting())).entrySet().stream()
				.max(Map.Entry.comparingByValue()).map(Map.Entry::getKey).orElse("");

		String mostCommonCapitalizedWord = arrayList.stream()
				.filter(u -> u.toCharArray()[0] == u.toUpperCase().toCharArray()[0])
				.collect(Collectors.groupingBy(Function.identity(), Collectors.counting())).entrySet().stream()
				.max(Map.Entry.comparingByValue()).map(Map.Entry::getKey).orElse("");

		fileWriter.append(numberofwords + "," + types + "," + shortWords + "," + blood + "," + mostCommonWord + ","
				+ mostCommonCapitalizedWord + "\n");
		fileWriter.flush();
		fileWriter.close();

		System.out.println("Gezählte Wörter: " + numberofwords);
		System.out.println("Anzahl der Typen: " + types);
		System.out.println("Anzahl der Wörter mit weniger als 5 Buchstaben: " + shortWords);
		System.out.println("Häufigkeit des Wortes \"blood\": " + blood);
		System.out.println("Das am häufigsten großgeschriebene Wort: " + mostCommonCapitalizedWord);
		System.out.println("Das am meisten vorkommende Wort: " + mostCommonWord);

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
