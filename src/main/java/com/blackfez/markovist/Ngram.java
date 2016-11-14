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
		System.out.println("\tSo we've hit the right constructor");
		this.setWord( word );
		System.out.println( "\tWe've put '" + this.getWord() + "' in this.word");
		this.setTokens(tokens);
		System.out.println( "\tWe've put '" + this.getTokens() + "' in this.tokens");
		System.out.println( "\tWhich would evaluate to '" + String.join( " ", this.getTokens() + "'" ) );
		this.setSentenceEnd(isEnd);
		System.out.println("\tand we set isEnd to '" + this.isSentenceEnd() + "'");
	}
	
	public void addToken( String token ) {
		System.out.println("Adding a token, '" + token + "'");
		System.out.println( "Before count is " + this.getTokens().size());
		this.getTokens().add( token );
		System.out.println( " and after is " + this.getTokens().size());
	}
	
	public void addTokens( List<String> tokens ) {
		System.out.println("Adding tokens, '" + tokens + "'");
		System.out.println( "Before count is " + this.getTokens().size());
		this.getTokens().addAll( tokens );
		System.out.println( " and after is " + this.getTokens().size());
	}
	
	public List<String> getTokens() {
		return this.tokens;
	}
	
	public String getTokensByState( int count ) {
		System.out.println("\tWe're getting " + count + " tokens");
		System.out.println("\tThis ngram has " + this.getTokens().size() + " tokens.");
		if( this.getTokens().size() >= count ) {
			System.out.println("\tthere are at least as many tokens as states we're asking for");
			System.out.println("\t we're returning '" + String.join("0",this.getTokens().subList(0, count) ) + "'");
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
