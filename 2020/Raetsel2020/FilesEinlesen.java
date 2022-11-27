package Raetsel2020;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class FilesEinlesen 
{
	//throws IOException ist der nötige Zusatz damit Dateien erkannt werden
	@SuppressWarnings({ "resource", "unused" })
	public static void main(String[] args) throws IOException
	{
		//2 Möglichkeiten auf Dateien zuzugreifen:
			//Name des Files, das in Eclipse im Projektordner liegt
		File fileInProject = new File("December1st_Input"); 
			//Pfad des Files iwo im Windows-Dateisystem - \\wegen Escape nötig
		File fileInWindowsDirectory = new File("C:\\Users\\inge\\Documents\\uni\\Advent of Code\\2020\\Day 1\\day1_r1_input.txt");
		
		//2 Möglichkeiten Files einzulesen:
			//Scanner:
		Scanner scanner = new Scanner(fileInProject);
			//FileReader - BufferedReader, damit auch zeilenweises Lesen möglich
		FileReader fr = new FileReader(fileInWindowsDirectory);
		BufferedReader br = new BufferedReader(fr);
		
		//scanner.nextLine(); = br.readLine();
		
	}
	
	
}

