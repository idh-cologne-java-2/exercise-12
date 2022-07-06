package idh.java;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.csv.CSVFormat;
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

		List<String> tokens = new ArrayList<String>();
		for (String token : this)
			tokens.add(token);
		CSVPrinter p = new CSVPrinter(new FileWriter(f), CSVFormat.EXCEL);
		p.printRecord("Anzahl der W�rter", "Anzahl der Types", "Anzahl der W�rter die k�rzer sind als 5 Zeichen", "H�ufigkeit des Wortes 'blood'", "Das am h�ufigsten vorkommende Wort",
				"Das am h�ufigsten vorkommende gro�geschriebene Wort");
		p.print(tokens.stream().count());
		p.print(tokens.stream().distinct().count());
		p.print(tokens.stream().filter(s -> s.length() < 5).count());
		p.print(tokens.stream().filter(s -> s.equals("blood")).count());

		
		String mft = null;
		long frequency = 0;
		Map<String, Long> ft = tokens.stream()
				.collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
		for (String token : ft.keySet()) {
			if (ft.get(token) > frequency) {
				frequency = ft.get(token);
				mft = token;
			}
		}

		p.print(mft);

		// Das am häufigsten vorkommende großgeschriebene Wort
		// Auch aus der Musterlösung übernommen; Nur dass '.isUpperCase' benutzt werden muss, war mir klar.
		mft = null;
		frequency = 0;
		ft = tokens.stream().filter(s -> Character.isUpperCase(s.charAt(0)))
				.collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
		
		for (String token : ft.keySet()) {
			if (ft.get(token) > frequency) {
				frequency = ft.get(token);
				mft = token;
			}
		}

		p.print(mft);
		p.close();
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