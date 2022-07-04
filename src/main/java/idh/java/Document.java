package idh.java;


import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
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
	
	public FileWriter printStats(FileWriter f) throws IOException {
		
		Document d = Document.readFromFile(new File("src/main/resources/dracula.txt"));
		double tokens = 0;
		Set<String> tokensUnique = new HashSet<String>();
		double tokensKleinerFünf = 0;
		double blood = 0;

		for (String token : d) {
			
			tokens++;
			
			tokensUnique.add(token);
			
			if (token.length() < 5) {
				tokensKleinerFünf++;
			}
			
			if (token.equals("blood")) {
				blood++;
			}
			
			
		}
		
		f.append("Tokens");
		f.append(",");
		f.append("Types");
		f.append(",");
		f.append("Tokens < 5");
		f.append(",");
		f.append("'blood'");
		f.append(",");
		f.append("Meister Token");
		f.append(",");
		f.append("Meister Token (groß geschrieben)");
		f.append("\n");
		
		String token=Double.toString(tokens);
		f.append(token);
		f.append(",");
		
		tokensUnique.size();
		int type = tokensUnique.size();
		String Types=Integer.toString(type);
		f.append(Types);
		f.append(",");
		
		String tokenKleinerFünf=Double.toString(tokensKleinerFünf);
		f.append(tokenKleinerFünf);
		f.append(",");
		
		String blut=Double.toString(blood);
		f.append(blut);
		f.append(",");
		
		f.append("ToDo");
		f.append(",");
		
		f.append("ToDo");
		
		f.flush();
		f.close();

		return f;
	}
	
	public static final void main(String[] args) throws IOException {
		Document d = Document.readFromFile(new File("src/main/resources/dracula.txt"));
		d.printStats(new FileWriter("src/main/resources/stats.csv"));
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
