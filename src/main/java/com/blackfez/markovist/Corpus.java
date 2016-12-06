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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

import edu.stanford.nlp.simple.*;

/**
 * @author nhansen
 *
 */
public class Corpus {

	String location;
	Document corpus;
	Integer corpusSentenceCount;
	List<String> sentenceStarters;
	Map<String,List<Ngram>> concordance;
	/**
	 * 
	 */
	public Corpus(String location) {
		this.location = location;
		this.sentenceStarters = new ArrayList<String>();
		this.concordance = new HashMap<String,List<Ngram>>();
	}
	
	public void loadDocument() throws IOException {
		FileReader fr = new FileReader( this.location );
		byte[] bytes = Files.readAllBytes(Paths.get(this.location));
		this.corpus = new Document( new String( bytes, StandardCharsets.US_ASCII ) );
		this.corpusSentenceCount = this.corpus.sentences().size();
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

	public void generateNgrams(Integer states) {
		for( Sentence sentence : this.corpus.sentences() ) {
			Boolean isFirstWord = true;
			LinkedList<String> comparer = new LinkedList( sentence.words() );
			while( !comparer.isEmpty() ) {
				String word = comparer.poll();
				if( isFirstWord ) {
					this.sentenceStarters.add(word);
					isFirstWord = false;
				}
				Ngram ngram = new Ngram( word, comparer,comparer.isEmpty() );
				if( !this.concordance.containsKey( word ) ) 
					this.concordance.put(word, new ArrayList<Ngram>() );
				this.concordance.get(word).add(ngram);
			}
		}
	}
	
	public Ngram getNgramForWord( String word ) {
		System.out.println("\tgetting ngram for '" + word + "'");
		List<Ngram> ngrams = this.concordance.get(word);
		System.out.println("\tthere are " + ngrams.size() + " ngrams for this word.");
		int i = ThreadLocalRandom.current().nextInt( 0, ngrams.size() );
		System.out.println("\twe've picked the ngram in position " + i +"'");
		return ngrams.get( i );
	}
	
	public String startSentence() {
		int i = ThreadLocalRandom.current().nextInt( 0, this.sentenceStarters.size() + 1 );
		return this.sentenceStarters.get( i );
	}
}