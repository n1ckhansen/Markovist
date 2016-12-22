/**
 * 
 */
package com.blackfez.markovist;
import java.io.File;
import java.io.IOException;

import org.apache.commons.cli.*;

/**
 * @author nhansen
 *
 */
public class Markovist {
	
	private CommandLine cmd;
	private Integer states;
	private File corpusLocation;
	private Corpus corpus;
	/**
	 * 
	 */
	public Markovist( String[] argv) {
		try {
			Options opts = constructOptions();
			try {
				cmd = parseCommandLine( opts, argv );
			}
			catch( MissingOptionException e ) {
				this.printHelp();
			}
			if( cmd.hasOption('?') ) {
				this.printHelp();
			}
			if( cmd.hasOption( 's' ) ) {
				try {
					this.states = Integer.parseInt( cmd.getOptionValue('s'));
				}
				catch( NumberFormatException e ) {
					System.out.println("states argument must be parseable as an integer." );
					System.out.println( String.format("'%s' cannot be parsed as an integer", cmd.getOptionValue('s')));
					this.printHelp(true);
				}
			}
			else {
				this.states = 2;
			}
			if( cmd.getArgs().length != 1 ) {
				if( cmd.getArgs().length == 0 )
					System.out.println( "Markovist requires a directory to a collection of text file[s].");
				if( cmd.getArgs().length > 1 )
					System.out.println( "Markovist only takes one argument--a directory containing text file[s].");
				this.printHelp(true);
				
			}
			this.corpusLocation = new File( cmd.getArgs()[0]);
			if( !this.corpusLocation.exists()) {
					System.out.println("Corpus must be a text file or a directory containing text files" );
					this.printHelp(true);
			}
			if( this.corpusLocation.isFile()) {
				System.out.println("corpus is a single text file" );
			}
			else if ( this.corpusLocation.isDirectory() ) {
				System.out.println( "corpus is a directory.  Load files...");
			}
			else {
				System.out.println("Corpus must be a text file or a directory containing text files" );
				this.printHelp(true);
			}
		}
		catch( ParseException e ) {
			System.out.println( "Encountered an error parsing the commandline.");
			System.err.println( String.format("Parse error %s", e ));
			this.printHelp(true);
		}
	}
	
	private CommandLine parseCommandLine( Options options, String[] argv ) throws ParseException {
		Parser parser = new PosixParser();
		return parser.parse( options, argv );
	}
	
	private Options constructOptions() {
		Options opts = new Options();
		opts.addOption(new Option( "?", "help", false, "print this message" ));
		opts.addOption(new Option( "s", "states", true, "number of states"));
		return opts;
	}
	
	private void printHelp( Boolean isError ) {
		this.printHelpText();
		System.exit( 1 );
	}
	
	private void printHelp() {
		this.printHelpText();
		System.exit( 0 );
	}
	private void printHelpText() {
		System.out.println( "" );
		System.out.println("$PATH/java -jar $PATH_TO_Markovist-all.jar [OPTIONS] CORPUS" );
		System.out.println( "\t -? --help:\t\tprint this message");
		System.out.println( "\t -s --states Integer\tthe number of Markov states.  Default is 2.");
		System.out.println( "" );
		System.out.println( "CORPUS:\ttext file or directory containing text file[s] to serve as\n\t training input");
		System.out.println( "" );
		System.out.println( "" );
	}
	
	private void makeCorpus()  {
		this.corpus = new Corpus(this.corpusLocation.getAbsolutePath());
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Markovist app = new Markovist( args );
		app.makeCorpus();
		try {
			app.corpus.loadDocument();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		app.corpus.generateNgrams( app.states );
		//app.corpus.someOutput();
		String word = app.corpus.startSentence();
		System.out.print( word + " " );
		for( int i = 0; i < 50; i++) {
			Ngram ngram = app.corpus.getNgramForWord( word );
			System.out.print( ngram.getTokensByState(app.states) + " " );
			word = ngram.getTokens().get( app.states - 1 );
			if( word == "." )
				word = app.corpus.startSentence();
		}

	}

}
