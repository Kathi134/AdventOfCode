package Base;

import java.io.File;
import java.util.ArrayList;



public abstract class Puzzle
{
	// Scanner, Datei und Größe/Format des Inputs
	protected int records = 1; // wenn ein datensatz über mehrere zeilen geht, ist records die anzahl datensätze
	protected int lines = 0;
	protected int columns = 0; // anzahl chars in einer Zeile
	protected InputReader inputReader;
	protected File f;

	// Speicherort des entsprechend konvertierten Inputs
	protected InputKind inputKind;
	protected int[] inputIntList;
	protected String[] inputStringList;
	protected char[][] inputCharTable;
	protected String[][] inputStringTable;
	protected int[][] inputIntTable;
	protected String inputString;
	protected boolean[][] inputBooleanTable;

	// Ausgabe der (formatierten) Lösung
	private long timerStart;
	private String duration1;
	private String duration2;
	protected long erg1;
	protected long erg2;
	protected String ergStr1 = "";
	protected String ergStr2 = "";

	// Nützlichkeiten für AoC
	protected int[] adjacentsParameters;

	public Puzzle(int day, int year)
	{
		f = new File(year + "\\Raetsel" + year +"\\inputs\\Dec" + day);
		inputReader = new InputReader(f);
		inputReader.prepare();
		
		lines = inputReader.lines;
		columns = inputReader.columns;
	}
	
	/**
	 * liest den Input des heutigen Tages als inputKind ein
	 * @param inputKind: die Art, wie der Input geparst werden soll
	 */
	protected void readInput(InputKind inputKind) 
	{
		this.inputKind = inputKind;
		switch (inputKind)
		{
			case String:
				inputString = inputReader.readString();
				break;
			case charTable:
				inputCharTable = inputReader.readCharTable();
				break;
			case intList:
				inputIntList = inputReader.readIntList();
				break;
			case intTable:
				inputIntTable = inputReader.readIntTable();
				break;
			case StringList:
				inputStringList = inputReader.readStringList();
				break;
			case seperatedStringList:
				inputStringList = inputReader.readSeperatedStringList();
				records = inputReader.records;
				break;
			case seperatedStringTable:
				inputStringTable = inputReader.readSeperatedStringTable();
				records = inputReader.records;
				break;
			case seperatedIntTable: 
				inputIntTable = inputReader.readSeperatedIntTable();
				records = inputReader.records;
				break;
			case booleanTable:
				inputBooleanTable = inputReader.readBooleanTable();
				break;
			default:
				break;
		}
	}

	/**
	 * liest den Input in Spalten getrennt durch das divisor Zeichen ein
	 * @param inputKind: die Art, wie der Input geparst werden soll
	 * @param divisor: das Trennzeichen
	 */
	protected void readInputDivided(InputKind inputKind, String divisor)
	{
		this.inputKind = inputKind;
		switch (inputKind)
		{
			case StringTable:
				inputStringTable = inputReader.readDividedStringTable(divisor);
				break;
			case intTable:
				inputIntTable = inputReader.readDividedIntTable(divisor);
				break;
			case intList:
				inputIntList = inputReader.readDividedIntList(divisor);
				break;
			default:
				break;
		}
	}
	
	/**
	 * gibt den heutigen input auf der Konsole aus
	 */
	protected void printInput()
	{
		switch(inputKind)
		{
			case StringList:
			case seperatedStringList:
				InputPrinter.printStringList(inputStringList);
				break;
			case StringTable:
				InputPrinter.printStringTable(inputStringTable);
				break;
			case booleanTable:
				InputPrinter.printBooleanTable(inputBooleanTable);
				break;
			case charTable:
				InputPrinter.printCharTable(inputCharTable);
				break;
			case intList:
				InputPrinter.printIntList(inputIntList);
				break;
			case intTable:
				InputPrinter.printIntTable(inputIntTable);
				break;
			default:
				break;
		}
	}


	// timer
	private void startTimer()
	{
		timerStart = System.nanoTime();
	}

	private String endTimer()
	{
		long timeSpent = (System.nanoTime() - timerStart) / 1000;
		String toString = "";

		if (timeSpent < 1000)
		{
			toString = timeSpent + "µs";
		} else if (timeSpent < 1000000)
		{
			toString = (timeSpent / 1000.0) + "ms";
		} else
		{
			toString = (timeSpent / 1000000.0) + "s";
		}

		return toString;
	}

	// jeder Tag implementiert entsprechend der Aufgabe
	public abstract void solveTask1();

	public abstract void solveTask2();

	// ruft die solve-Methode der übergebenen aufgabe auf und berechnet die laufzeit
	public void computeSolution(int task)
	{
		if (task == 1)
		{
			startTimer();
			solveTask1();
			duration1 = endTimer();
		} else if (task == 2)
		{
			startTimer();
			solveTask2();
			duration2 = endTimer();
		}
	}

	// gibt die rohen antworten aus
	public void printSolution()
	{
		String sol1 = erg1 == 0 ? ergStr1 : erg1 + "";
		System.out.println("Task 1 -- " + sol1);

		String sol2 = erg2 == 0 ? ergStr2 : erg2 + "";
		System.out.println("Task 2 -- " + sol2);
	}

	// gibt die antworten formatiert aus (mit name des rätsels, erklärung, laufzeit)
	// nur kleines schönheits-extra - nicht notwendig zur tatsächlichen bearbeitung
	// von AoC
	public void printFormattedSolution(String name, String t1text, String t2text)
	{
		System.out.println("/*\\ " + name + " \\*/");

		System.out.println("Task 1 -- " + t1text + ": " + (erg1 == 0 ? ergStr1 : erg1));
		System.out.println("       -- duration: " + duration1);

		System.out.println("Task 2 -- " + t2text + ": " + (erg2 == 0 ? ergStr2 : erg2));
		System.out.println("       -- duration: " + duration2);
	}

	// erzeugt tiefe kopien von mehrdimensionalen arrays
	public char[][] clone(char[][] original)
	{
		char[][] copy = new char[original.length][];
		for (int i = 0; i < copy.length; i++)
		{
			copy[i] = new char[original[i].length];
			for (int j = 0; j < original[i].length; j++)
			{
				copy[i][j] = original[i][j];
			}
		}
		return copy;
	}

	public int[][] clone(int[][] original)
	{
		int[][] copy = new int[original.length][];
		for (int i = 0; i < copy.length; i++)
		{
			copy[i] = new int[original[i].length];
			for (int j = 0; j < original[i].length; j++)
			{
				copy[i][j] = original[i][j];
			}
		}
		return copy;
	}

	public long[] clone(long[] original)
	{
		long[] copy = new long[original.length];
		for (int i = 0; i < original.length; i++)
		{
			copy[i] = original[i];
		}
		return copy;
	}
	
	public int[] clone(int[] original)
	{
		int[] copy = new int[original.length];
		for (int i = 0; i < original.length; i++)
		{
			copy[i] = original[i];
		}
		return copy;
	}

	// erwartet eine pos fürs zentrum und die angabe des inputTable-datentyps
	// gibt ein Feld mit den Parametern zurück: [0: rowAbove][1: rowBelow][2:
	// columnLeft][3: columnRight]
	public void getAdjacentParameters(int i, int j, String inputKind)
	{
		adjacentsParameters = new int[4];

		int rowAbove = i;
		int rowBelow = i;
		int columnRight = j;
		int columnLeft = j;

		if (inputKind.equals("intTable"))
		{
			if (i != 0)
				rowAbove = i - 1;
			if (i != inputIntTable.length - 1)
				rowBelow = i + 1;
			if (j != 0)
				columnLeft = j - 1;
			if (j != inputIntTable[i].length - 1)
				columnRight = j + 1;
		} else if (inputKind.equals("charTable"))
		{
			if (i != 0)
				rowAbove = i - 1;
			if (i != inputCharTable.length - 1)
				rowBelow = i + 1;
			if (j != 0)
				columnLeft = j - 1;
			if (j != inputCharTable[i].length - 1)
				columnRight = j + 1;
		}

		int[] parameters = { rowAbove, rowBelow, columnLeft, columnRight };
		adjacentsParameters = parameters;
	}

	// speichert in der form y,x
	public ArrayList<int[]> getDiagonalAdjacents(int i, int j, String inputKind)
	{
		getAdjacentParameters(i, j, inputKind);
		int a = adjacentsParameters[0];
		int b = adjacentsParameters[1];
		int l = adjacentsParameters[2];
		int r = adjacentsParameters[3];

		int[][] ad = new int[8][2];
		ad[0] = new int[] { a, l }; // above left
		ad[1] = new int[] { a, j }; // above mid
		ad[2] = new int[] { a, r }; // above right
		ad[3] = new int[] { i, l }; // mid left
		ad[4] = new int[] { i, r }; // mid right
		ad[5] = new int[] { b, l }; // below left
		ad[6] = new int[] { b, j }; // below mid
		ad[7] = new int[] { b, r };

		ArrayList<int[]> neighbours = new ArrayList<>();
		for (int cnt = 0; cnt < ad.length; cnt++)
		{
			if (ad[cnt][0] != i || ad[cnt][1] != j)
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
		ad[0] = new int[] { adjacentsParameters[0], j }; // above
		ad[1] = new int[] { i, adjacentsParameters[2] }; // left
		ad[2] = new int[] { i, adjacentsParameters[3] }; // right
		ad[3] = new int[] { adjacentsParameters[1], j }; // below

		ArrayList<int[]> neighbours = new ArrayList<>();
		for (int cnt = 0; cnt < ad.length; cnt++)
		{
			if (ad[cnt][0] != i || ad[cnt][1] != j)
			{
				neighbours.add(ad[cnt]);
			}
		}

		return neighbours;
	}

}

