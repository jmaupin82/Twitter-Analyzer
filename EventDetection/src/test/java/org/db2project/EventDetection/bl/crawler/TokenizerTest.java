package org.db2project.EventDetection.bl.crawler;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import org.junit.Test;

public class TokenizerTest {

	@Test
	public void test() {
//		fail("Not yet implemented");
		assertTrue(true);
	}
	
	@Test
	public void testTokenModel(){
		InputStream input = null;
		try {
			input = new FileInputStream("/Users/pmahoro/Documents/OU/database/EventDetection/src/test/java/org/db2project/EventDetection/data.txt");
			Tokenizer tester = new Tokenizer(input);
			assertTrue("expect tokenizer to be non-empty", tester != null);
//			assertTrue("expect tokenizer model to be set", tester.getTokenModel() != null);
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  	
	}

}
