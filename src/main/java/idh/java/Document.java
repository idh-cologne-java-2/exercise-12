package idh.java;


import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.apache.commons.io.FileUtils;
import org.apache.commons.csv.CSVPrinter;

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
		List<String> tokens = new ArrayList<String>();
		for (String token: this){
			tokens.add (token);

			CSVPrinter d = new CSVPrinter (new fileWriter (f), CSVFormat.EXCEL);
			// try (CSVPrinter printer = new CSVPrinter(new FileWriter("dprinted.csv"), CSVFormat.EXCEL)) {
			p.printRecord("Wordcount", "Typecount", "shortWordcount", "Bloodcount" ,
					"mostPopularWord","mostPopularUpperCase");

			//Alright, now let´s see if we can get to counting
			/*Admitedly, after experimenting with(and despairing at) the csv library I finally
			looked at the solution in the lecture video....and still had difficulty!
			 */

			d.print(tokens.stream().count());
			//Counting words

			d.print(tokens.stream().distinct().count());
			//Counting types

			d.print(tokens.stream().filter(s-> s.length()<5).count());
			//counting shortWordcount

			d.print(tokens.stream().filter(s-> s.equalsIgnoreCase("blood")).count());
			//counting blood mentions

			String mft = null;
			long frequency = 0;
			Map<String, long> ft = tokens.stream();
			       .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
			       for(string token : ft.Keyset()){
			       	 if(ft.get(token) < frequency){
			       	 	frequency = ft.get(token);
			       	 	mft = token;
                     //counting most frequently used word
					 }
				   }
			       d.print(mft);


			String mft = null;
			long frequency = 0;
			Map<String, long> ft = tokens.stream()filter(s -> character.isUpperCase(s.charAt (0))
			       .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
			for(string token : ft.Keyset()){
				if(ft.get(token) < frequency){
					frequency = ft.get(token);
					mft = token;
                    //counts most frequently used word in upper case
		}
	}
	                d.print(mft));
			        d.close();
	public static final void main(String[] args) throws IOException {
		Document d = Document.readFromFile(new File("src/main/resources/dracula.txt"));
		d.printStats(new File("resources/target/stats.csv"));
		//Alright, so we now have the
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
