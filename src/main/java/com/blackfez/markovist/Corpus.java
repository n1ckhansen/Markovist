/**
 * 
 */
package com.blackfez.markovist;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import edu.stanford.nlp.simple.*;

/**
 * @author nhansen
 *
 */
public class Corpus {

	String location;
	Document corpus;
	/**
	 * 
	 */
	public Corpus(String location) {
		this.location = location;
	}
	
	public void loadDocument() throws IOException {
		FileReader fr = new FileReader( this.location );
		byte[] bytes = Files.readAllBytes(Paths.get(this.location));
		this.corpus = new Document( new String( bytes, StandardCharsets.US_ASCII ) );
	}
	
	public void someOutput() {
		for( Sentence sentence : this.corpus.sentences() ) {
			System.out.println("");
			System.out.println( "The second word of the sentence is " + sentence.word(1));
//			System.out.println( "The third lemma of the sentence is " + sentence.lemma(2));
			System.out.println( "The parse of the sentence is " + sentence.parse());
			System.out.println("");
		}
	}
}
