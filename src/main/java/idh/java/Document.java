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
				fw.append("Anzahl der Wörter,"
						+ "Anzahl der Types,"
						+ "Anzahl der Wörter die kürzer sind als 5, "
						+ "Häufigkeit des Wortes \"blood\","
						+ "Das am häufigsten vorkommende Wort,"
						+ "Das am häufigsten vorkommende großgeschriebene Wort"
						+ "\n");
				String[] words = this.documentText.split(" ");
				ArrayList<String> al = new ArrayList<String>();
				
				for(String token : this) {
					al.add(token);
				}
				
				long word = al.stream().count();
				long types = al.stream().distinct().count();
				long shortwords = al.stream().filter(w -> w.length() < 5).count();
				long blood = al.stream().filter(b -> b.equals("blood")).count();
				String mostFrequent = al.stream()
						.collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
						.entrySet()
						.stream()
						.max(Map.Entry.comparingByValue())
						.map(Map.Entry::getKey).orElse("");
				String mostFrequentUppercase = al.stream().filter(u -> u.toCharArray()[0] == u.toUpperCase().toCharArray()[0])
						.collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
						.entrySet()
						.stream()
						.max(Map.Entry.comparingByValue())
						.map(Map.Entry::getKey).orElse("");
				
				fw.append(word + ","
						+ types + ","
						+ shortwords + ","
						+ blood + ","
						+ mostFrequent + ","
						+ mostFrequentUppercase
						+ "\n");
				fw.flush();
				fw.close();
				System.out.println(word);
				System.out.println(types);
				System.out.println(shortwords);
				System.out.println(blood);
				System.out.println(mostFrequent);
				System.out.println(mostFrequentUppercase);
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
