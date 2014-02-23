package org.db2project.EventDetection.bl.crawler;

import java.io.IOException;
import java.io.InputStream;

import opennlp.tools.namefind.TokenNameFinderModel;

public class Tokenizer {
	private TokenNameFinderModel tokenModel;
	
	public Tokenizer(InputStream in){
		if (in != null){
			try {
				TokenNameFinderModel model = new TokenNameFinderModel(in);
				tokenModel = model;
			}
			catch (IOException e) {
				e.printStackTrace();
			}
			finally {
				try {
					in.close();
				}
				catch (IOException e){	
				}
			}
		}
	}
	
	
	public TokenNameFinderModel getTokenModel(){
		return tokenModel;
	}
	
	public void setTokenModel(TokenNameFinderModel aModel){
		tokenModel = aModel;
	}
}
