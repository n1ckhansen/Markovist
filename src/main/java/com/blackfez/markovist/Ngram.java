/**
 * 
 */
package com.blackfez.markovist;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author nhans
 *
 */
public class Ngram {

	private List<String> tokens;
	private Boolean sentenceEnd;
	private String word;
	
	public Ngram( String word ) {
		this.setWord( word );
		this.tokens = new ArrayList<String>();
	}
	public Ngram( String word, String[] tokens ) {
		this( word, tokens, false );
	}
	
	public Ngram( String word, String[] tokens, Boolean isEnd ) {
		this.setWord( word );
		this.setTokens( Arrays.stream( tokens ).collect(Collectors.<String>toList()));
		this.setSentenceEnd(isEnd);
	}
	
	public Ngram( String word, List<String> tokens ) {
		this( word, tokens, false );
	}
	
	public Ngram( String word, List<String> tokens, Boolean isEnd ) {
		this.tokens = new ArrayList<String>();
		this.setWord( word );
		for( String token : tokens ) {
			this.addToken( token );
		}
		this.setSentenceEnd(isEnd);
	}
	
	public void addToken( String token ) {
		this.getTokens().add( token );
	}
	
	public void addTokens( List<String> tokens ) {
		this.getTokens().addAll( tokens );
	}
	
	public List<String> getTokens() {
		return this.tokens;
	}
	
	public String getTokensByState( int count ) {
		if( this.getTokens().size() >= count ) {
			return String.join(" ", this.getTokens().subList(0, count ) );
		}
		return String.join(" ", this.getTokens() );
	}
	
	public String getWord() {
		return this.word;
	}
	
	public Boolean isSentenceEnd() {
		return this.sentenceEnd;
	}
	
	public final void setSentenceEnd( Boolean isEnd ) {
		this.sentenceEnd = isEnd;
	}

	public final void setTokens( List<String> tokens ) {
		this.tokens = tokens;
	}
	
	public final void setWord( String word ) {
		this.word = word;
	}
}
