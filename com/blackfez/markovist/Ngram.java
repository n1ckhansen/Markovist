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
	
	public Ngram() {
		this.tokens = new ArrayList<String>();
	}
	public Ngram( String[] tokens ) {
		this( tokens, false );
	}
	
	public Ngram( String[] tokens, Boolean isEnd ) {
		this.setTokens( Arrays.stream( tokens ).collect(Collectors.<String>toList()));
		this.setSentenceEnd(isEnd);
	}
	
	public Ngram( List<String> tokens ) {
		this( tokens, false );
	}
	
	public Ngram( List<String> tokens, Boolean isEnd ) {
		this.setTokens(tokens);
		this.setSentenceEnd(isEnd);
	}
	
	public void addToken( String token ) {
		this.tokens.add( token );
	}
	
	public String getTokens( int count ) {
		if( this.tokens.size() >= count ) {
			return String.join(" ", this.tokens.subList(0, count ) );
		}
		return String.join(" ", this.tokens );
	}
	
	public Boolean isSentenceEnd() {
		return this.sentenceEnd;
	}
	
	public void setSentenceEnd( Boolean isEnd ) {
		this.sentenceEnd = isEnd;
	}

	public void setTokens( List<String> tokens ) {
		this.tokens = tokens;
	}
}
