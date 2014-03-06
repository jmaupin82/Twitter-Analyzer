package org.db2project.EventDetection;

import org.db2project.EventDetection.bl.crawler.Crawler;
import org.db2project.EventDetection.bl.crawler.DatabaseConnection;

/**
 * New Event detection using twitter streams. 
 *
 */
public class EventDetectorApp 
{	
    public static void main( String[] args )
    {
        System.out.println( "Event Detector started!" );
        DatabaseConnection data = new DatabaseConnection();
        System.exit(0);
        try{
        	Crawler.oauth(args[0], args[1], args[2], args[3]);
        } catch(Exception e){
        	System.out.println(e);
        }
    }
}
