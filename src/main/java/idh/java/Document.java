package idh.java;

/**
 * @author UntoastedToast
 *
 */

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

		f.createNewFile();
		FileWriter fileWriter = new FileWriter(f);
		fileWriter.append("Counted words," + "Number of types," + "Number of words shorter than 5 characters, "
				+ "Frequency of the word \"blood\"," + "The most common word," + "The most common capitalized word"
				+ "\n");
		
		ArrayList<String> arrayList = new ArrayList<String>();

		for (String token : this) {
			arrayList.add(token);
		}

		// Number of words
		long totalWords = arrayList.stream().count();

		// Number of types
		long types = arrayList.stream().distinct().count();

		// Number of words shorter than 5 characters
		long shortWords = arrayList.stream().filter(w -> w.length() < 5).count();

		// Frequency of the word "blood"
		long blood = arrayList.stream().filter(b -> b.equals("blood")).count();

		// The most common word
		String mostCommonWord = arrayList.stream()
				.collect(Collectors.groupingBy(Function.identity(), Collectors.counting())).entrySet().stream()
				.max(Map.Entry.comparingByValue()).map(Map.Entry::getKey).orElse("");

		// The most common capitalized word
		String mostCommonCapitalizedWord = arrayList.stream()
				.filter(u -> u.toCharArray()[0] == u.toUpperCase().toCharArray()[0])
				.collect(Collectors.groupingBy(Function.identity(), Collectors.counting())).entrySet().stream()
				.max(Map.Entry.comparingByValue()).map(Map.Entry::getKey).orElse("");

		fileWriter.append(totalWords + "," + types + "," + shortWords + "," + blood + "," + mostCommonWord + ","
				+ mostCommonCapitalizedWord + "\n");
		fileWriter.flush();
		fileWriter.close();

		System.out.println("Counted words: " + totalWords);
		System.out.println("Number of types: " + types);
		System.out.println("Number of words shorter than 5 characters: " + shortWords);
		System.out.println("Frequency of the word \"blood\": " + blood);
		System.out.println("The most common word: " + mostCommonWord);
		System.out.println("The most common capitalized word: " + mostCommonCapitalizedWord);
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
