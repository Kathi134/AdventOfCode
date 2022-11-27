package Raetsel2020;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public abstract class Puzzle 
{
	protected static int records = 1; //wenn ein datensatz über mehrere zeilen geht, ist records die anzahl datensätze
	protected static int lines = 0;
	protected static int columns = 0;
	protected static BufferedReader br = null;
	protected static File f;
	
	protected static int[] inputIntList;
	protected static long[] inputLongList;
	protected static String[] inputStringList;
	protected static char[][] inputCharTable;	
	protected static String[][] inputStringTable;
	
	
	//prepare erstellt einen FileReader über die aktuelle InputDatei und legt die Anzahl Zeilen und Spalten fest
	public static void prepare() throws IOException 
	{
		FileReader fr = new FileReader(f);
		br = new BufferedReader(fr);
		
		while (br.readLine() != null)
		{
			lines++;
		}
		resetReader();
		
		columns = br.readLine().length();
		resetReader();
	}
	
	//resetReader bringt den Reader an den Anfang des Dokuments
	public static void resetReader() throws IOException
	{
		//der br1 ist schon mit fr ans ende des files durchgelaufen, reset() doesnt work, also neue Reader anlegen
		FileReader fr2 = new FileReader(f);
		br = new BufferedReader(fr2);	
	}

	//liest den input in ein Feld, je nach String eingabe, der Sorte<inputKind> ein
	public static void readInput(String inputKind) throws IOException
	{
		switch(inputKind)
		{
		case "charTable": readCharTable(); break;
		case "intList": readIntList(); break;
		case "longList": readLongList(); break;
		case "StringList": readStringList(); break;
		case "seperatedStringList": readSeperatedStringList(); break;
		}
		if(inputKind.contains("StringTable divBy "))
		{
			String divisor = inputKind.replace("StringTable divBy ", "");
			readStringTable(divisor);		
		}
	}
	
	public static void readLongList() throws IOException
	{
		inputLongList = new long[lines]; 
		for(int l=0; l<lines; l++)
		{
			inputLongList[l] = Long.parseLong(br.readLine());
		}
	}
	
	public static void readStringTable(String divisor) throws IOException
	{
		inputStringTable = new String[lines][];
		for(int i=0; i<inputStringTable.length; i++)
		{
			String line = br.readLine();
			String[] columns = line.split(divisor);
			inputStringTable[i] = columns;
		}
	}
		
 	public static void readStringList() throws IOException
	{
		inputStringList = new String[lines]; 
		for(int l=0; l<lines; l++)
		{
			inputStringList[l] = br.readLine();
		}
	}
 	
	public static void readSeperatedStringList() throws IOException
	{
		for(int i=0; i<lines; i++)
		{
			if (br.readLine().isBlank())
			{
				records++;
			}
		}
		resetReader();
		
		inputStringList = new String[records];
		//String currentRecord = ""; //aktueller Datensatz wird in einem String zsmgefasst
		for(int i=0; i<records; i++)
		{
			String zeile = br.readLine();
			String currentRecord = "";
			
			while (!zeile.isBlank())
			{
				currentRecord += zeile + " ";
				zeile = br.readLine();
				if(zeile == null)break;
			}
			inputStringList[i] = currentRecord;
		}
		
	}
	
	public static void readIntList() throws IOException
	{
		inputIntList = new int[lines]; 
		for(int l=0; l<lines; l++)
		{
			inputIntList[l] = Integer.parseInt(br.readLine());
		}
		
	}
	
	public static void readCharTable() throws IOException
	{
		inputCharTable = new char[lines][columns]; 
		for(int l=0; l<lines; l++)
		{
			String line = br.readLine();
			for (int c=0; c<columns; c++)
			{
				inputCharTable[l][c] = line.charAt(c);
			}
		}
	}

	public static void printCharTable()
	{
		for(int j=0; j<inputCharTable.length; j++)
		{
			for (int i=0; i<inputCharTable[0].length; i++)
			{
				System.err.print(inputCharTable[j][i]);
			}
			System.err.println();
		}
		
	}
	
	public static void printStringTable()
	{
		for(int i=0; i<inputStringTable.length; i++)
		{
			for(int j=0; j<inputStringTable[i].length; j++)
			{
				System.err.print(inputStringTable[i][j]+"\t");
			}
			System.err.println();
		}
	}
}
