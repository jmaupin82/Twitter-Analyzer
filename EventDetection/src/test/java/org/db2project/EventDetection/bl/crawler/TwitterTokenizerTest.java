package org.db2project.EventDetection.bl.crawler;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.*;

public class TwitterTokenizerTest {

	private TwitterTokenizer twTokenizer;
	
	@Before
	public void setUp(){
		twTokenizer = new TwitterTokenizer();
	}
	
	@After
	public void tearDown(){
		twTokenizer = null;
	}
	
	@Test
	public void testTokenizationofText(){
		String txt =  "this text is from a tweet";
		List<String> k = twTokenizer.tokenize(txt, false);
		
		assertEquals("token list should have a size of 6", k.size(), 6);
	}
	
	@Test
	public void testRemovalOfStopWords(){
		String txt = "this text is from a tweet";
		List<String> tokensWithStopWords = twTokenizer.tokenize( txt, false);
		List<String> tokensWithNoStopWords = twTokenizer.tokenize( txt, true);
		
		assertTrue("expect word in a list of tokens", tokensWithStopWords.contains("this"));
		assertFalse("does not expect word in a list of tokens", tokensWithNoStopWords.contains("this"));
	}
}
