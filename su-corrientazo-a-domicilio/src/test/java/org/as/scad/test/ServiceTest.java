package org.as.scad.test;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

import java.util.ArrayList;
import java.util.List;

import org.as.scad.constants.Constants;
import org.as.scad.dto.Report;
import org.as.scad.exception.InvalidFileNameException;
import org.as.scad.util.Service;

@DisplayName("Service")
public class ServiceTest {

	@Test
    @DisplayName("Archivo válido")
    public void file_is_valid() {
		String file = " AAAAIAA\n DDDAIAD   \nAAIADAD\t\n\n";
		boolean isValid = Service.fileIsValid( file );
		Assertions.assertTrue( isValid );
    }

	@Test
    @DisplayName("Archivo con comandos inválidos")
	public void file_has_invalid_commands() {
		String file = "AAXIAAX";
		boolean isValid = Service.fileIsValid( file );
		Assertions.assertFalse( isValid );
    }

	@Test
    @DisplayName("Cantidad de pedidos máxima superada")
	public void file_has_more_than_3_requests() {
		String file = "AAAAIAA\nDDDAIAD\nAAIADAD\nAAAAIAA";
		int maxRequest = 3;
		boolean isValid = Service.fileIsValid( file, maxRequest );
		Assertions.assertFalse( isValid );
    }

	@Test
    @DisplayName("El nombre del archivo es válido")
	public void file_has_valid_name() {
		String fileName = "in02.txt";
		try {
			int dronNumber = Service.getDronNumber( fileName );
			Assertions.assertEquals( 2, dronNumber );
		} catch (InvalidFileNameException e) {
			Assertions.assertTrue( false );
		}
    }

	@Test
    @DisplayName("El nombre del archivo es inválido")
	public void file_has_invalid_name_1() {
		String fileName = "inaa02.txt";
		Assertions.assertThrows(
			InvalidFileNameException.class,
			() -> Service.getDronNumber( fileName )
		);
    }

	@Test
    @DisplayName("El nombre del archivo es inválido")
	public void file_has_invalid_name_2() {
		String fileName = ".txt02out";
		Assertions.assertThrows(
			InvalidFileNameException.class,
			() -> Service.getDronNumber( fileName )
		);
    }

	@Test
	@DisplayName("Generar nombre de archivo de reportes")
	public void generate_report_file_name() {
		int dronNumber = 1;
		String reportName = Service.getReportName( dronNumber );
		Assertions.assertEquals( reportName, "out01.txt" );
	}

	@Test
    @DisplayName("Reporte es válido")
	public void is_valid_report() {
		String instruction = "AAAAIAA";
		Report report = Service.getReport( instruction );
		Report expectedReport = new Report( -2, 4, Constants.WEST );
		Assertions.assertEquals( report, expectedReport );
    }

	@Test
    @DisplayName("Inválido S4N: AAAAIAA (-2, 4) dirección Norte")
	public void is_invalid_report1() {
		String instruction = "AAAAIAA";
		Report report = Service.getReport( instruction );
		Report expectedReport = new Report( -2, 4, Constants.NORTH );
		Assertions.assertNotEquals( report, expectedReport );
    }

	@Test
    @DisplayName("Inválido S4N: DDDAIAD (-3, 3) dirección Sur")
	public void is_invalid_report2() {
		String instruction = "DDDAIAD";
		Report report = Service.getReport( instruction );
		Report expectedReport = new Report( -2, 4, Constants.SOUTH );
		Assertions.assertNotEquals( report, expectedReport );
    }

	@Test
    @DisplayName("Inválido S4N: AAIADAD (-4, 2) dirección Oriente")
	public void is_invalid_report3() {
		String instruction = "AAIADAD";
		Report report = Service.getReport( instruction );
		Report expectedReport = new Report( -4, 2, Constants.EAST );
		Assertions.assertNotEquals( report, expectedReport );
    }

	@Test
    @DisplayName("Imprimir reporte final válido")
	public void print_full_report() {
		Report r0 = new Report( -2, 4, Constants.NORTH );
		Report r1 = new Report( -3, 3, Constants.SOUTH );
		Report r2 = new Report( -4, 2, Constants.EAST );
		List<Report> reports = new ArrayList<Report>();
		reports.add( r0 );
		reports.add( r1 );
		reports.add( r2 );
		String fullReport = Service.getFullReport( reports );
		String expectedFullReport =
			"== Reporte de entregas ==\n" + 
			"\n" + 
			"(-2,4) dirección Norte\n" + 
			"\n" + 
			"(-3,3) dirección Sur\n" + 
			"\n" + 
			"(-4,2) dirección Oriente\n";
		Assertions.assertEquals( fullReport, expectedFullReport );
    }

}
