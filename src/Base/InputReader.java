package Base;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class InputReader
{
	private File f;
	private BufferedReader br = null;

	public int lines = 0;
	public int columns = 0; // anzahl chars in einer Zeile
	public int records = 0; // wenn ein datensatz über mehrere zeilen geht, ist records die anzahl
							// datensätze

	public InputReader(File f)
	{
		this.f = f;

		prepare();
	}

	/**
	 * prepare erstellt einen FileReader über die aktuelle InputDatei und legt die
	 * Anzahl Zeilen und Spalten fest
	 */
	public void prepare()
	{
		try
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
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * resetReader bringt den Reader an den Anfang des Dokuments
	 */
	public void resetReader()
	{
		// der br1 ist schon mit fr ans ende des files durchgelaufen, reset() doesnt
		// work, also neue Reader anlegen
		try
		{
			FileReader fr2 = new FileReader(f);
			br = new BufferedReader(fr2);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	
	public String readString()
	{
		String inputString = null;
		try
		{
			inputString = br.readLine();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		return inputString;
	}

	
	public int[][] readDividedIntTable(String divisor)
	{
		int[][] inputIntTable = null;

		try
		{
			inputIntTable = new int[lines][];
			for (int i = 0; i < inputIntTable.length; i++)
			{
				String line = br.readLine();
				line.trim();
				int[] columns = splitToIntegerArr(line, divisor);
				inputIntTable[i] = columns;
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		return inputIntTable;
	}

	
	public int[][] readIntTable()
	{
		int[][] inputIntTable = null;

		try
		{
			inputIntTable = new int[lines][columns];
			for (int l = 0; l < lines; l++)
			{
				String line = br.readLine();
				for (int c = 0; c < columns; c++)
				{
					inputIntTable[l][c] = Character.getNumericValue(line.charAt(c));
				}
			}
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}

		return inputIntTable;
	}

	
	public int[] readDividedIntList(String divisor)
	{
		int[] inputIntList = null;

		try
		{
			String all = "";
			String line = br.readLine();
			while (line != null)
			{
				all += line;
				line = br.readLine();
			}
			inputIntList = splitToIntegerArr(all, ",");
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}

		return inputIntList;
	}

	
	public String[][] readDividedStringTable(String divisor) 
	{
		String[][] inputStringTable = null; 
		
		try 
		{
			inputStringTable = new String[lines][];
			for (int i = 0; i < inputStringTable.length; i++)
			{
				String line = br.readLine();
				String[] columns = line.split(divisor);
				inputStringTable[i] = columns;
			}		
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
		
		return inputStringTable;
	}

	
	public String[] readStringList()
	{
		String[] inputStringList = null;
		
		try
		{
			inputStringList = new String[lines];
			for (int l = 0; l < lines; l++)
			{
				inputStringList[l] = br.readLine();
			}
		}
		catch (IOException e) 
		{
			e.printStackTrace();
		}
		
		return inputStringList;
	}
	

	public String[] readSeperatedStringList()
	{
		String[] inputStringList = null;
		
		try
		{
			for (int i = 0; i < lines; i++)
			{
				if (br.readLine().isBlank())
				{
					records++;
				}
			}
			resetReader();

			inputStringList = new String[records];
			// String currentRecord = ""; //aktueller Datensatz wird in einem String
			// zsmgefasst
			for (int i = 0; i < records; i++)
			{
				String zeile = br.readLine();
				String currentRecord = "";

				while (!zeile.isBlank())
				{
					currentRecord += zeile + " ";
					zeile = br.readLine();
					if (zeile == null)
						break;
				}
				inputStringList[i] = currentRecord;
			}
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}

		return inputStringList;
	}

	
	public String[][] readSeperatedStringTable()
	{
		String[] tmp = readSeperatedStringList();
		
		String[][] table = new String[tmp.length][];
		
		for(int i=0; i<tmp.length; i++)
		{
			table[i] = tmp[i].split(" ");
		}
		
		return table;
	}
	
	public int[][] readSeperatedIntTable()
	{
		String[] tmp = readSeperatedStringList();
		
		int[][] table = new int[tmp.length][];
		
		for(int i=0; i<tmp.length; i++)
		{
			table[i] = splitToIntegerArr(tmp[i], " ");
		}
		
		return table;
	}
	
	public int[] readIntList()
	{
		int[] inputIntList = null;

		try
		{
			inputIntList = new int[lines];
			for (int l = 0; l < lines; l++)
			{
				inputIntList[l] = Integer.parseInt(br.readLine());
			}
		}
		catch (IOException e) 
		{
			e.printStackTrace();
		}
		
		return inputIntList;
	}

	
	public char[][] readCharTable()
	{
		char[][] inputCharTable = null;
		
//		columns = 16;
		try
		{
			inputCharTable = new char[lines][columns];
			for (int l = 0; l < lines; l++)
			{
				String line = br.readLine();
				for (int c = 0; c < line.length(); c++)
				{
					char curr = ' ';
					if(c < line.length())
					{
						curr = line.charAt(c);
					}
					inputCharTable[l][c] = curr;
				}
			}
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
		
		return inputCharTable;
	}

	
	public boolean[][] readBooleanTable()
	{
		boolean[][] inputBooleanTable = null;
		
		try
		{
			inputBooleanTable = new boolean[lines][columns];
			for (int l = 0; l < lines; l++)
			{
				String line = br.readLine();
				for (int c = 0; c < columns; c++)
				{
					inputBooleanTable[l][c] = (line.charAt(c) == '#');
				}
			}
		}
		catch (IOException e) 
		{
			e.printStackTrace();
		}

		return inputBooleanTable;
	}
	

	/**
	 * trennt ein zeilen-String beim divisor und macht daraus ein integer-array
	 * 
	 * @param line:    der zu splittende String
	 * @param divisor: das Trennzeichen
	 * @return ein IntegerArray aus dem gesplitteten String
	 */
	public static int[] splitToIntegerArr(String line, String divisor)
	{
		String[] tmp = line.split(divisor);
		int[] arr = new int[tmp.length];
		for (int i = 0; i < tmp.length; i++)
		{
			arr[i] = Integer.parseInt(tmp[i]);
		}
		return arr;
	}
}
