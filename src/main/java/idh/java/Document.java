package idh.java;


import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.StringTokenizer;

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
		String retValue = "Anzahl der Wörter,"
				+ "Anzahl der Types,"
				+ "Anzahl der Wörter die kürzer sind als 5 Zeichen,"
				+ "Häufigkeit des Wortes \"blood\","
				+ "Am häufigsten vorkommendes Wort,"
				+ "Am häufigsten vorkommendes großgeschriebenes Wort\n";
		int[] numericalStats = new int[] {0,0,0,0};
		HashMap<String,Integer> frequencyMap = new HashMap<String,Integer>();
		int mfwFrequency = 0;
		int mfcwFrequency = 0;
		String mostFrequentWord = new String();
		String mostFrequentCapitalizedWord = new String();
		for (String word : this) {
			numericalStats[0]++;
			if (!frequencyMap.containsKey(word))  {
				numericalStats[1]++;
				frequencyMap.put(word, 1);
			}
			else {
				frequencyMap.put(word, frequencyMap.get(word)+1);
			}
			if (word.length() < 5) numericalStats[2]++;
			if (word.equals("blood")) numericalStats[3]++;
		}
		for (int i : numericalStats) {
			retValue += i + ",";
		}
		for (Entry<String, Integer> entry : frequencyMap.entrySet()) {
			if (entry.getValue() > mfwFrequency) {
				mfwFrequency = entry.getValue();
				mostFrequentWord = entry.getKey();
			}
			if (Character.isUpperCase(entry.getKey().charAt(0)) && entry.getValue() > mfcwFrequency) {
				mfcwFrequency = entry.getValue();
				mostFrequentCapitalizedWord = entry.getKey();
			}
		}
		retValue += mostFrequentWord + "," + mostFrequentCapitalizedWord + ",";
		System.out.println(retValue);
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
