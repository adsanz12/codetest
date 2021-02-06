package org.as.scad.util;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.as.scad.dto.Report;
import org.as.scad.exception.InvalidFileNameException;

public class Controller {

	public int run( String fileName ) throws IOException, InvalidFileNameException {
		int dronNumber = Service.getDronNumber( fileName );
		if( dronNumber != -1 ) {
			String fileContent = getFile( fileName );
			if( Service.fileIsValid( fileContent ) ) {
				List<Report> reports = new ArrayList<Report>();
				for( String instruction : fileContent.split( "\n" ) ) {
					reports.add( Service.getReport( instruction ) );
				}
				String reportName = Service.getReportName( dronNumber );
				String fullReport = Service.getFullReport( reports );
				saveFile( reportName, fullReport );
				return 0;
			} else {
				return 1;
			}
		} 
		return 2;
	}

	private String getFile( String fileName ) throws IOException {
		String content = null;
		content = new String( Files.readAllBytes( Paths.get(fileName) ) );
		return content;
	}

	private void saveFile( String fileName, String fullReport ) throws IOException {
		BufferedWriter writer = new BufferedWriter( new FileWriter(fileName) );
		writer.write( fullReport );
		writer.close();
	}

}
