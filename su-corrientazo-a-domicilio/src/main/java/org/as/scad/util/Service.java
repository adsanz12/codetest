package org.as.scad.util;

import java.util.List;

import org.as.scad.constants.Constants;
import org.as.scad.dto.Report;
import org.as.scad.exception.InvalidFileNameException;

public class Service {

	public static boolean fileIsValid( String file ) {
		return fileIsValid( file, 3 );
	}

	public static boolean fileIsValid( String file, int maxRequest ) {
		String instruction = null;
		int count = 0;
		for( String s : file.split( "\n" ) ) {
			instruction = s.replaceAll("\\s+","");
			if( instruction.length() > 0 ) {
				if( count == maxRequest  ) return false;
				else count++;
			}
			if( !instructionIsValid( instruction ) ) return false;
		}
		return true;
	}

	public static int getDronNumber( String fileName ) throws InvalidFileNameException {
		try {
			String dronNumberStr =
					fileName.replace( "in" , "" ).replace( ".txt" , "" );
			Integer.parseInt( dronNumberStr );
			dronNumberStr =
					fileName.substring( 2 ).substring( 0, fileName.length() - 6 );
			return Integer.parseInt( dronNumberStr );
		} catch( NumberFormatException e ) {
			throw new InvalidFileNameException( "Invalid filename: " + fileName );
		}
	}

	public static Report getReport( String instruction ) {
		int x = 0;
		int y = 0;
		String direction = Constants.NORTH;
		//System.out.println("A: " +  x + "," + y + ": " + direction );
		for( char c : instruction.toCharArray() ) {
			switch( c ) {
				case 'A':
					switch( direction ) {
						case Constants.NORTH:
							y++;
							break;
						case Constants.WEST:
							x--;
							break;
						case Constants.SOUTH:
							y--;
							break;
						case Constants.EAST:
							x++;
							break;
					}
					break;
				case 'I':
					switch( direction ) {
						case Constants.NORTH:
							direction = Constants.WEST;
							break;
						case Constants.WEST:
							direction = Constants.SOUTH;
							break;
						case Constants.SOUTH:
							direction = Constants.EAST;
							break;
						case Constants.EAST:
							direction = Constants.NORTH;
							break;
					}
					break;
				case 'D':
					switch( direction ) {
						case Constants.NORTH:
							direction = Constants.EAST;
							break;
						case Constants.EAST:
							direction = Constants.SOUTH;
							break;
						case Constants.SOUTH:
							direction = Constants.WEST;
							break;
						case Constants.WEST:
							direction = Constants.NORTH;
							break;
					}
					break;
			}
			//System.out.println(c + ": " +  x + "," + y + ": " + direction );
		}
		return new Report( x, y, direction );
	}

	public static String getFullReport( List< Report > reports ) {
		String fullReport = "== Reporte de entregas ==\n";
		for( Report report : reports ) {
			fullReport += "\n" + report.toString() + "\n";
		}
		return fullReport;
	}

	public static String getReportName(int dronNumber) {
		return "out0" + dronNumber + ".txt";
	}

	private static boolean instructionIsValid( String line ) {
		for( char c : line.toUpperCase().toCharArray() ) {
			if( c != 'A' && c != 'I' && c != 'D' ) {
				return false;
			}
		}
		return true;
	}

}
