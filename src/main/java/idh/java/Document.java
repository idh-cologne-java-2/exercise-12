package idh.java;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;
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
		// TODO: Implement types,häufigste wörter(normal + groß)
		
		
		this.getDocumentText();
		this.iterator();
		ArrayList<String> words = new ArrayList<String>();
		
		//double vergl = 0;
		for (String token : this) {
			//vergl++;
			words.add(token);
		}
		//System.out.println(words);
		//System.out.println(words.size());
		
		//Statistische Daten
				
				//Anzahl der Wörter
				String s1 = "Anzahl der Wörter";
				//words.stream().filter(s -> s.);
				//.filter(d -> d.getWords());
				long z1 = words.size(); //eigentlich 161.962 words?
				
				//Anzahl der Types (also verschiedener Wörter)
				String s2 = "Anzahl der Types (also verschiedener Wörter)";
			//idee:types?
				Map<ArrayList<String>, Long> map = Stream.of(words).collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
				//map.
				//System.out.println(map.size());
				long z2 = words.stream().filter(s -> s.isEmpty()).count();
				
				//Anzahl der Wörter die kürzer sind als 5 Zeichen
				String s3 = "Anzahl der Wörter die kürzer sind als 5 Zeichen";
				
				long z3 = words.stream().filter(s -> (s.length()<5)).count();
				
				//Häufigkeit des Wortes "blood"
				String s4 = "Häufigkeit des Wortes \"blood\"";
				
				long z4 = words.stream().filter(s -> s.contains("blood")).count();
				
				//Das am häufigsten vorkommende Wort
				String s5 = "Das am häufigsten vorkommende Wort";
			//idee: häufigste?
				Stream.of(words).collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
				String s51 = words.stream().filter(s -> s.isEmpty()).toString();
				
				//Das am häufigsten vorkommende großgeschriebene Wort
				String s6 = "Das am häufigsten vorkommende großgeschriebene Wort";
				
			//idee: charAt(0)==uppercase and häufigste
				String s61 = words.stream().filter(s -> s.isEmpty()).toString();

		
		//CSV anlegen
		try (CSVPrinter printer = new CSVPrinter(new FileWriter(f), CSVFormat.EXCEL)) {
		     printer.printRecord(s1, s2, s3, s4, s5, s6);
		     printer.printRecord(z1, z2, z3, z4, s51, s61);
		 } catch (IOException ex) {
		     ex.printStackTrace();
		 }
		
		
		
		//output -nur beispielhaft, weil nicht gefordert
		Reader in = null;
		try {
			in = new FileReader(f);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Iterable<CSVRecord> records = null;
		
		try {
			records = CSVFormat.EXCEL.parse(in);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		for (CSVRecord record : records) {
			for(int i=0;i<6;i++) {
				System.out.println(record.get(i));
				}
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
