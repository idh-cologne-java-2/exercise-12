package idh.java;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.io.FileUtils;

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
	
	public void printStats(File f) {
		try {

			BufferedWriter bufferedWriter = Files.newBufferedWriter(Paths.get(f.getAbsolutePath()));
			ArrayList<String> arrayList = new ArrayList();

			Set<String> stringSet = new HashSet();

			this.getDocumentText();
			this.iterator();

			HashMap<String, Stack<String>> stackHashMap = new HashMap<>();

			int h = 0;

			for(String token : this) {
				arrayList.add(token);
				stringSet.add(token);

				if(!(stackHashMap.containsKey(token))) {
					stackHashMap.put(token, new Stack<>());
					Stack<String> i = stackHashMap.get(token);

					i.push(token);
				}else {

					Stack<String> i = stackHashMap.get(token);
					i.push(token);
				}

			}
			int m = 0;
			int mostWord = 0;
			String mostUsedWord  = "";

			for(Stack<String> value : stackHashMap.values()) {
				m = value.size();

				if(m > mostWord) {
					mostWord = m;
					mostUsedWord = value.get(0);
				}

			}

			System.out.println("Das meist benutze Wort ist: " + mostUsedWord);
			int size = arrayList.size();

			float count = arrayList.stream().filter(s -> s.length() < 5).count();
			float blood =  arrayList.stream().filter(s -> s.contains("blood")).count();
			int differentw = stringSet.size();

			CSVPrinter csvprinter = new CSVPrinter(bufferedWriter, CSVFormat.DEFAULT.withHeader(
					"AnzahlDerWorte", "Worte < 5", "VerschiedeneWorte", "Am MeistenBenutzt", "wie oft blood vorkommt",
					"das am häufigsten vorkommende großgeschriebene Wort")
			);

			csvprinter.printRecord(size,count, differentw,blood,mostUsedWord);
			csvprinter.flush();
			csvprinter.close();

		}catch(IOException e ) {
			e.printStackTrace();
		}

	}

	public static void main(String[] args) throws IOException {
		Document d = Document.readFromFile(new File("src/main/resources/dracula.txt"));
		d.printStats(new File("target/stats.csv"));
	}

	public Iterator<String> iterator() {
		return new Iterator<>() {

			final StringTokenizer tokenizer = new StringTokenizer(documentText);
			
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
