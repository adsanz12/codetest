package org.as.scad.main;

import java.io.IOException;

import org.as.scad.exception.InvalidFileNameException;
import org.as.scad.util.Controller;

public class Main {

	public static void main(String[] args) throws IOException, InvalidFileNameException {		
		Controller c = new Controller();
		for( String fileName : args ) {
			c.run( fileName );
		}
	}

}
