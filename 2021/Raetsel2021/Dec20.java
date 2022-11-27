package Raetsel2021;

import java.io.IOException;
import java.util.ArrayList;

//probelm: wenn alles ... sind dann trd # weil das erste zecihen im inputString ein # ist
//wenn alles ### was alle zwei runden im infinite space (rand) passiert dann binary 111111111 dann . weil zeichen an stelle 511 (das letzte) ein .
public class Dec20 extends Puzzle
{
	ArrayList<ArrayList<Character>> output = new ArrayList<ArrayList<Character>>();
	int numberLits1 = 0;
	int numberLits2 = 0;
	
	public void prepareRead() throws IOException
	{
		br.readLine(); //überspringe die leere Zeile
		lines -= 2;
		columns = br.readLine().length();
		resetReader();
		br.readLine(); 
		br.readLine();
	}
	
	public int power(int basis, int exponent)
	{
		int erg = 1;
		for(int i=0; i<exponent; i++)
		{
			erg = erg * basis;
		}
		return erg;
	}

	public Dec20() throws IOException
	{
		super(20);
		readInput("String"); //inputString als enhancement anweisungen - erste zeile
		prepareRead();
		readInput("booleanTable"); //'#' als true, '.' als false
		
		computeSolution(1);
		erg1 = numberLits1;
		computeSolution(2);
		erg2 = numberLits2;
		//printSolution();
		
		printFormattedSolution("Trench Map", 
				"number of lit points after  2 enhancements", 
				"number of lit points after 50 enhancements");
	}
	
	public int getDecimal(String umzuwandeln)
	{
		int system = 2;
		int dezimaleZahl = 0;
				
		for(int i=0; i<umzuwandeln.length(); i++)
		{
			int tmp = -1;
			int exp = umzuwandeln.length()-i-1;
			tmp = Character.getNumericValue(umzuwandeln.charAt(i));		
			int dieserWert = tmp * power(system, exp);
			dezimaleZahl = dezimaleZahl + dieserWert;  
		}
		
		return dezimaleZahl;
	}
		
	//gibt die nachbarn in form {x,y}
	public int[][] getNinelet(int i, int j)
	{
		int a = i-1;
		int b = i+1;
		int l = j-1;
		int r = j+1;
		
		int[][] ad = new int[9][2];
		ad[0] = new int[] {a, l}; //above left
		ad[1] = new int[] {a, j}; //above mid
		ad[2] = new int[] {a, r}; //above right
		ad[3] = new int[] {i, l}; //mid left
		ad[4] = new int[] {i, j}; //diese
		ad[5] = new int[] {i, r}; //mid right
		ad[6] = new int[] {b, l}; //below left
		ad[7] = new int[] {b, j}; //below mid
		ad[8] = new int[] {b, r}; //below right
		
		return ad;
	}
	
	public void solveTask1()
	{
		numberLits1 = solve(2);
	}
	
	public int solve(int rounds)
	{
		for(int round=0; round<rounds; round++)
		{
			//alle zwei runden füge überall einen 4 breiten rand aus lights hinzu
			if(round%2 == 0) 
			{
				boolean[][] newImage = new boolean[inputBooleanTable.length+8][inputBooleanTable[0].length+8]; //auf beiden seiten 4 -> insg 8 länger
				for(int i=0; i<lines; i++) //alten kern übertragen
				{
					for(int j=0; j<columns; j++)
					{
						newImage[i+4][j+4] = inputBooleanTable[i][j]; //alles um die vier hinzugefügten ränder verschieben
					}
				}
				inputBooleanTable = newImage;
				lines = inputBooleanTable.length;
				columns = inputBooleanTable[0].length;
			}
			
			enhance();
		}
		
		return getLits();
	}
	
	public void enhance()
	{
		boolean[][] newImage = new boolean[inputBooleanTable.length-2][inputBooleanTable[0].length-2]; //schneide einen rand wieder weg
		
		for(int i=1; i<lines-1; i++) //nur den kern
		{
			for(int j=1; j<columns-1; j++) //nur den kern
			{
				int[][] currNinelet = getNinelet(i, j);
				String currBinary = getBinaryCode(currNinelet);
				int decimal = getDecimal(currBinary);
				char finalChar = inputString.charAt(decimal);
				newImage[i-1][j-1] = (finalChar == '#'); //verschiebe korrket zu dem gecutteten rand			
			}
		}
		
		inputBooleanTable = newImage;
		lines = inputBooleanTable.length; //schneide ränder ab, siehe oben
		columns = inputBooleanTable[0].length; 
	}
	
	public int getLits()
	{
		int cnt = 0;
		for(int i=0; i<inputBooleanTable.length; i++)
		{
			for(int j=0; j<inputBooleanTable[i].length; j++)
			{
				cnt += inputBooleanTable[i][j] ? 1 : 0;
			}
		}
		return cnt;
	}
	
	public String getBinaryCode(int[][] ninelet)
	{
		String binary = "";
		
		for(int[] p: ninelet)
		{
			String currChar = "";
			if(p[0] < 0 || p[0] >= lines || p[1] < 0 || p[1] >= columns)
			{
				currChar = "0";
			}
			else if(inputBooleanTable[p[0]][p[1]])
			{
				currChar = "1";
			}
			else if(!inputBooleanTable[p[0]][p[1]])
			{
				currChar = "0";
			}
			binary += currChar;
		}
		
		return binary;
	}
	
	public void solveTask2()
	{
		//alles resetten
		try
		{
			prepare();
			readInput("String"); //inputString als enhancement anweisungen - erste zeile
			prepareRead();
			readInput("booleanTable"); //'#' als true, '.' als false
		} 
		catch (IOException e)
		{
			e.printStackTrace();
		}
		
		numberLits2 = solve(50);
	}
	
	public static void main(String[] args) throws IOException
	{
		new Dec20();
	}
}
