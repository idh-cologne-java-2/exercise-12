package idh.java;


import org.apache.commons.io.FileUtils;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

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
		String outputFilePath = f.getPath();
		ArrayList<String> files = new ArrayList<>();
		ArrayList<TokenTool> frequency = new ArrayList<>();
		this.iterator();

		long alle = 0;
		long types = 0;
		long shorts = 0;
		long blood = 0;
		long star = 0;
		long bigstar = 0;


		for (String token : this) {
			if(!files.contains(token)){
				types++;
			}
			files.add(token);
		}

		HashMap <String, Long> wordMap = new HashMap<String, Long> ();
		String[] words = documentText.split(" ");
		String starword = " ";
		for(int i=0;i<words.length;++i){
			if(!wordMap.containsKey(words[i])){
				wordMap.put(words[i], 1L);
			}
			else{
				wordMap.put(words[i],wordMap.get(words[i])+1);
			}

		}
			star = wordMap.values().stream().max(Long::compareTo).get();

		alle = files.size();
		shorts = files.stream().filter(s -> (s.length()<5)).count();
		blood = files.stream().filter(s -> s.contains("blood")).count();

		//CSV Tabelle erstellen
		BufferedWriter bufferedWriter = Files.newBufferedWriter(Paths.get(outputFilePath));
		try (CSVPrinter csvPrinter = CSVFormat.DEFAULT
				.print(bufferedWriter)) {
			csvPrinter.printRecord("Anzahl der Wörter", "Anzahl der Types",
									"Anzahl der Wörter die kürzer sind als 5 Zeichen", "Häufigkeit des Wortes ´blood´"
									, "Das am häufigsten vorkommende Wort", "Das am häufigsten vorkommende großgeschriebene Wort");
			csvPrinter.printRecord(alle, types, shorts, blood, star, bigstar);
		} catch (IOException e) {
			throw new RuntimeException(e);
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
