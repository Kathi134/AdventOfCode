package Base;

public class InputPrinter
{
	// zum testen
	public static void printStringList(String[] inputStringList)
	{
		for (int i = 0; i < inputStringList.length; i++)
		{
			System.err.println(inputStringList[i]);
		}
	}

	public static void printCharTable(char[][] inputCharTable)
	{
		for (int j = 0; j < inputCharTable.length; j++)
		{
			for (int i = 0; i < inputCharTable[0].length; i++)
			{
				System.err.print(inputCharTable[j][i]);
			}
			System.err.println();
		}
	}

	public static void printBooleanTable(boolean[][] inputBooleanTable)
	{
		for (int i = 0; i < inputBooleanTable.length; i++)
		{
			for (int j = 0; j < inputBooleanTable[i].length; j++)
			{
				if (inputBooleanTable[i][j])
				{
					System.err.print("#");
				} else
				{
					System.err.print('.');
				}
			}
			System.err.println();
		}
	}

	public static void printStringTable(String[][] inputStringTable)
	{

		for (int j = 0; j < inputStringTable.length; j++)
		{
			for (int i = 0; i < inputStringTable[j].length; i++)
			{
				System.err.print(inputStringTable[j][i] + " ");
			}
			System.err.println();
		}
	}

	public static void printIntTable(int[][] inputIntTable)
	{

		for (int j = 0; j < inputIntTable.length; j++)
		{
			for (int i = 0; i < inputIntTable[0].length; i++)
			{
				System.err.printf(inputIntTable[j][i] + " ");
			}
			System.err.println();
		}
		System.err.println();
	}

	public static void printIntList(int[] inputIntList)
	{
		for (int i = 0; i < inputIntList.length; i++)
		{
			System.err.print(inputIntList[i] + ",");
		}
		System.err.println();
	}
}
