package Raetsel2021;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public abstract class Puzzle 
{
	//Scanner, Datei und Größe/Format des Inputs
	protected int records = 1; //wenn ein datensatz über mehrere zeilen geht, ist records die anzahl datensätze
	protected int lines = 0;
	protected int columns = 0; //anzahl chars in einer Zeile
	protected BufferedReader br = null;
	protected File f;
	
	//Speicherort des entsprechend konvertierten Inputs
	protected int[] inputIntList;
	protected String[] inputStringList;
	protected char[][] inputCharTable;	
	protected String[][] inputStringTable;
	protected int[][] inputIntTable;
	protected String inputString;
	protected boolean[][] inputBooleanTable;
	
	//Ausgabe der (formatierten) Lösung
	private long timerStart;
	private String duration1;
	private String duration2;
	protected long erg1;
	protected long erg2;
	
	//Nützlichkeiten für AoC
	protected int[] adjacentsParameters; 
	
	public Puzzle(int day) 
	{
		f = new File("2021\\Raetsel2021\\inputs\\Dec" + day);
		try
		{
			prepare();			
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
	}
	
	//prepare erstellt einen FileReader über die aktuelle InputDatei und legt die Anzahl Zeilen und Spalten fest
	public void prepare() throws IOException 
	{
		FileReader fr = new FileReader(f);
		br = new BufferedReader(fr);
		lines = 0;
		
		while (br.readLine() != null)
		{
			lines++;
		}
		resetReader();
		
		columns = br.readLine().length();
		resetReader();
	}
	
	//resetReader bringt den Reader an den Anfang des Dokuments
	public void resetReader() throws IOException
	{
		//der br1 ist schon mit fr ans ende des files durchgelaufen, reset() doesnt work, also neue Reader anlegen
		FileReader fr2 = new FileReader(f);
		br = new BufferedReader(fr2);	
	}

	
	//liest den input in ein Feld, je nach String eingabe, der Sorte<inputKind> ein
	public void readInput(String inputKind) throws IOException
	{
		switch(inputKind)
		{
		case "String": readString(); break;
		case "charTable": readCharTable(); break;
		case "intList": readIntList(); break;
		case "intTable": readIntTable(); break;
		case "StringList": readStringList(); break;
		case "seperated StringList": readSeperatedStringList(); break;
		case "booleanTable": readBooleanTable(); break;
		}
		if(inputKind.contains("divBy "))
		{
			if(inputKind.contains("StringTable"))
			{
				String divisor = inputKind.replace("StringTable divBy ", "");
				readDividedStringTable(divisor);		
			}
			else if(inputKind.contains("intTable"))
			{
				String divisor = inputKind.replace("intTable divBy ", "");
				readDividedIntTable(divisor);
			}
			else if(inputKind.contains("intList"))
			{
				String divisor = inputKind.replace("intList divBy " , "");
				readDividedIntList(divisor);
			}
		}
	}
	
	public void readString() throws IOException
	{
		inputString = br.readLine(); 
	}
	
	public void readDividedIntTable(String divisor) throws IOException
	{
		inputIntTable = new int[lines][];
		for(int i=0; i<inputIntTable.length; i++)
		{
			String line = br.readLine();
			line.trim();
			int[] columns = splitToIntegerArr(line, divisor);
			inputIntTable[i] = columns;
		}
	}
	
	public void readIntTable() throws IOException
	{
		inputIntTable = new int[lines][columns]; 
		for(int l=0; l<lines; l++)
		{
			String line = br.readLine();
			for (int c=0; c<columns; c++)
			{
				inputIntTable[l][c] = Character.getNumericValue(line.charAt(c));
			}
		}
	}
	
	public void readDividedIntList(String divisor) throws IOException
	{
		String all = "";
		String line = br.readLine();
		while(line!=null)
		{
			all+=line;
			line = br.readLine();
		}
		inputIntList = splitToIntegerArr(all, ",");
	}
	
	public void readDividedStringTable(String divisor) throws IOException
	{
		inputStringTable = new String[lines][];
		for(int i=0; i<inputStringTable.length; i++)
		{
			String line = br.readLine();
			String[] columns = line.split(divisor);
			inputStringTable[i] = columns;
		}
	}
		
 	public void readStringList() throws IOException
	{
		inputStringList = new String[lines]; 
		for(int l=0; l<lines; l++)
		{
			inputStringList[l] = br.readLine();
		}
	}
 	
	public void readSeperatedStringList() throws IOException
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
	
	public void readIntList() throws IOException
	{
		inputIntList = new int[lines]; 
		for(int l=0; l<lines; l++)
		{
			inputIntList[l] = Integer.parseInt(br.readLine());
		}
		
	}
	
	public void readCharTable() throws IOException
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
	
	public void readBooleanTable() throws IOException
	{
		inputBooleanTable = new boolean[lines][columns]; 
		for(int l=0; l<lines; l++)
		{
			String line = br.readLine();
			for (int c=0; c<columns; c++)
			{
				inputBooleanTable[l][c] = (line.charAt(c)=='#');
			}
		}
	}

	//trennt ein zeilen-String beim divisor und macht daraus ein integer-array
	public int[] splitToIntegerArr(String line, String divisor)
	{
		String[] tmp = line.split(divisor);
		int[] arr = new int[tmp.length];
		for(int i=0; i<tmp.length; i++)
		{
			arr[i] = Integer.parseInt(tmp[i]);
		}
		return arr;
	}
	
	
	//zum testen
	public void printStringList()
	{
		for(int i=0; i<inputStringList.length; i++)
		{
			System.err.println(inputStringList[i]);
		}
	}
	
	public void printCharTable()
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
	
	public void printBooleanTable()
	{
		for(int i=0; i<inputBooleanTable.length; i++)
		{
			for (int j=0; j<inputBooleanTable[i].length; j++)
			{
				if(inputBooleanTable[i][j])
				{
					System.err.print("#");
				}
				else
				{
					System.err.print('.');
				}
			}
			System.err.println();
		}
	}
	
	public void printStringTable()
	{

		for(int j=0; j<inputStringTable.length; j++)
		{
			for (int i=0; i<inputStringTable[j].length; i++)
			{
				System.err.print(inputStringTable[j][i]+" ");
			}
			System.err.println();
		}
	}
	
	public void printIntTable()
	{

		for(int j=0; j<inputIntTable.length; j++)
		{
			for (int i=0; i<inputIntTable[0].length; i++)
			{
				System.err.printf(inputIntTable[j][i] + " ");
			}
			System.err.println();
		}
		System.err.println();
	}
	
	public void printIntList()
	{
		for(int i=0; i<inputIntList.length; i++)
		{
			System.err.print(inputIntList[i]+",");
		}
		System.err.println();
	}
	
	
	//timer
	public void startTimer()
	{
		timerStart = System.nanoTime();
	}
	
	public String endTimer()
	{
		long timeSpent = (System.nanoTime() - timerStart) / 1000;
		String toString = "";
		
		if(timeSpent < 1000)
		{
			toString = timeSpent + "µs";
		}
		else if(timeSpent < 1000000)
		{
			toString = (timeSpent / 1000.0) + "ms";	
		}
		else
		{
			toString = (timeSpent / 1000000.0) + "s";	
		}
		
		return toString;
	}
	
	
	//jeder Tag implementiert entsprechend der Aufgabe
	public abstract void solveTask1();
	public abstract void solveTask2();
		
	//ruft die solve-Methode der übergebenen aufgabe auf und berechnet die laufzeit
	public void computeSolution(int task)
	{
		if(task == 1)
		{
			startTimer();
			solveTask1();
			duration1 = endTimer();
		}
		else if(task == 2)
		{
			startTimer();
			solveTask2();
			duration2 = endTimer();
		}
	}
	
	//gibt die rohen antworten aus
	public void printSolution()
	{
		String sol1 = erg1 == 0 ? "" : erg1 + "";
		System.out.println("Task 1 -- " + sol1);
		
		String sol2 = erg2 == 0 ? "" : erg2 + "";
		System.out.println("Task 2 -- " + sol2);
	}
		
	//gibt die antworten formatiert aus (mit name des rätsels, erklärung, laufzeit)
	//nur kleines schönheits-extra - nicht notwendig zur tatsächlichen bearbeitung von AoC
	public void printFormattedSolution(String name, String t1text, String t2text)
	{
		System.out.println("/*\\ " + name + " \\*/");
		
		System.out.println("Task 1 -- " + t1text + ": " + erg1);
		System.out.println("       -- duration: " + duration1);
		
		System.out.println("Task 2 -- " + t2text + ": " + erg2);
		System.out.println("       -- duration: " + duration2);
	}
		
		
	//erzeugt tiefe kopien von mehrdimensionalen arrays
	public char[][] clone(char[][] original)
	{
		char[][] copy = new char[original.length][];
		for(int i=0; i<copy.length; i++)
		{
			copy[i] = new char[original[i].length];
			for(int j=0; j<original[i].length; j++)
			{
				copy[i][j] = original[i][j];
			}
		}
		return copy;
	}
	
	public int[][] clone(int[][] original)
	{
		int[][] copy = new int[original.length][];
		for(int i=0; i<copy.length; i++)
		{
			copy[i] = new int[original[i].length];
			for(int j=0; j<original[i].length; j++)
			{
				copy[i][j] = original[i][j];
			}
		}
		return copy;
	}
	
	public long[] clone(long[] original)
	{
		long[] copy = new long[original.length];
		for(int i=0; i<original.length; i++)
		{
			copy[i] = original[i];
		}
		return copy;
	}
	
	
	//erwartet eine pos fürs zentrum und die angabe des inputTable-datentyps
	//gibt ein Feld mit den Parametern zurück: [0: rowAbove][1: rowBelow][2: columnLeft][3: columnRight]
	public void getAdjacentParameters(int i, int j, String inputKind)
	{
		adjacentsParameters = new int[4];
		
		int rowAbove = i;
		int rowBelow = i;
		int columnRight = j;
		int columnLeft = j;
		
		if(inputKind.equals("intTable"))
		{
			if(i!=0) rowAbove = i-1; 
			if(i!=inputIntTable.length-1) rowBelow = i+1;
			if(j!=0) columnLeft = j-1;
			if(j!=inputIntTable[i].length-1) columnRight = j+1;
		}
		else if(inputKind.equals("charTable"))
		{
			if(i!=0) rowAbove = i-1; 
			if(i!=inputCharTable.length-1) rowBelow = i+1;
			if(j!=0) columnLeft = j-1;
			if(j!=inputCharTable[i].length-1) columnRight = j+1;
		}
		
		int[] parameters = {rowAbove, rowBelow, columnLeft, columnRight};
		adjacentsParameters = parameters;
	}	
	
	//speichert in der form y,x
	public ArrayList<int[]> getDiagonalAdjacents(int i, int j, String inputKind)
	{
		getAdjacentParameters(i, j, inputKind);
		int a = adjacentsParameters[0];
		int b = adjacentsParameters[1];
		int l = adjacentsParameters[2];
		int r = adjacentsParameters[3];
		
		int[][] ad = new int[8][2];
		ad[0] = new int[] {a, l}; //above left
		ad[1] = new int[] {a, j}; //above mid
		ad[2] = new int[] {a, r}; //above right
		ad[3] = new int[] {i, l}; //mid left
		ad[4] = new int[] {i, r}; //mid right
		ad[5] = new int[] {b, l}; //below left
		ad[6] = new int[] {b, j}; //below mid
		ad[7] = new int[] {b, r};

		ArrayList<int[]> neighbours = new ArrayList<>();
		for(int cnt=0; cnt<ad.length; cnt++)
		{
			if(ad[cnt][0] != i || ad[cnt][1] != j)
			{
				neighbours.add(ad[cnt]);
			}
		}		
		
		return neighbours;
	}
	
	public ArrayList<int[]> getDirectAdjacents(int i, int j, String inputKind)
	{
		getAdjacentParameters(i, j, inputKind);
		
		int[][] ad = new int[4][2];
		ad[0] = new int[] {adjacentsParameters[0], j}; //above
		ad[1] = new int[] {i, adjacentsParameters[2]}; //left
		ad[2] = new int[] {i, adjacentsParameters[3]}; //right
		ad[3] = new int[] {adjacentsParameters[1], j}; //below
		
		ArrayList<int[]> neighbours = new ArrayList<>();
		for(int cnt=0; cnt<ad.length; cnt++)
		{
			if(ad[cnt][0] != i || ad[cnt][1] != j)
			{
				neighbours.add(ad[cnt]);
			}
		}
		
		return neighbours;
	}
	
}
