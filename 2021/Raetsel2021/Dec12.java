package Raetsel2021;

import java.io.IOException;

public class Dec12 extends Puzzle
{
	int[][] matrix;
	Knoten[] knoten;
	int anzahlKnoten;
	
	boolean[] multipleTimesVisitable;
	boolean[] besucht;
	
	int anzahlPfade = 0;
	
	boolean allowTwice[];
	String[] paths = {" "};
	int numberPaths2 = 0;
	
	public Dec12() throws IOException
	{
		super(12);
		readInput("StringTable divBy -");
		//printStringTable();
		
		System.err.println("--takes about 1 minute to compute--");
		computeSolution(1);
		computeSolution(2);
		//printSolution();
		
		printFormattedSolution("Passage Pathing", 
				"number of distinct paths", 
				"number of paths allowing a double visit on one small cave");
	}
	
	public void readAdjacentMatrix()
	{
		//zur übersicht, dass ich weiß, welcher knoten wo ist
		knoten = new Knoten[20];
		anzahlKnoten=0;
		for(int i=0; i<inputStringTable.length; i++) //für jeden knoten
		{
			if(!isInKnoten(inputStringTable[i][0], knoten, anzahlKnoten))
			{
				knoten[anzahlKnoten] = new Knoten(inputStringTable[i][0]);
				anzahlKnoten++;
			}
			if(!isInKnoten(inputStringTable[i][1], knoten, anzahlKnoten))
			{
				knoten[anzahlKnoten] = new Knoten(inputStringTable[i][1]);
				anzahlKnoten++;
			}
		} 
		
		//tatsächlihces einlesen der matrix
		matrix = new int[anzahlKnoten][anzahlKnoten];
		for(int i=0; i<matrix.length; i++) //jede zeile
		{
			for(int j=0; j<matrix[i].length; j++) //jede spalte
			{
				if(knoten[i].hasConnection(knoten[j])) //prüfe für jede zeile jede spalte
				{
					matrix[i][j] = 1;
				}
			}
		}
	}
	
	public boolean isInKnoten(String k, Knoten[] bisherige, int länge)
	{
		for(int i=0; i<länge; i++)
		{
			if(bisherige[i].name.equals(k))
			{
				return true;
			}
		}
		return false;
	}
	
	public int getMatrixIndexOfKnoten(Knoten k)
	{
		for(int i=0; i<knoten.length; i++)
		{
			if(knoten[i].name.equals(k.name))
			{
				return i;
			}
		}
		return -1;
	}
	
	
	public void solveTask1()
	{
		readAdjacentMatrix();
		
		Knoten start = new Knoten("start");
		Knoten ziel = new Knoten("end");
		
		besucht = new boolean[matrix.length];
		besucht[getMatrixIndexOfKnoten(start)] = true;
		anzahlPfade = 0;
		
		initializeMultipleTimesVisitable();
				
		findAllPaths(start, ziel, "start,"); 
		erg1 = anzahlPfade;
	}
	
	public void initializeMultipleTimesVisitable()
	{
		multipleTimesVisitable = new boolean[matrix.length];
				
		for(int i=0; i<matrix.length; i++)
		{
			String currNodName = knoten[i].name;
			if(currNodName.toLowerCase().equals(currNodName))
			{
				multipleTimesVisitable[i] = false;
			}
			else if(currNodName.toUpperCase().equals(currNodName))
			{
				multipleTimesVisitable[i] = true;
			}
		}
	
	}
	
	public void findAllPaths(Knoten start, Knoten ziel, String pfad) 
	{
		int index = getMatrixIndexOfKnoten(start); //index des aktuellen knotens in der matrix 
		if(!multipleTimesVisitable[index]) //wenn er mehrfach besuchbar ist, 
		{
			besucht[index] = true; //lasse seine markierung im besucht feld auf besuchbar
		}
				
		if(start.name.equals(ziel.name)) //falls das ende erreicht ist, 
		{
			//System.err.println(pfad); //gib den pfad aus
			anzahlPfade++; //und inkrementiere die anzahl an pfaden
		}
		for(int i=0; i<matrix[index].length; i++) //besuche alle knoten 
		{
			if(matrix[index][i] == 1 && !besucht[i]) //die nachbarn sind & noch besuchbar
			{
				String neuerPfad = pfad + knoten[i].name; //hänge den knotenNamen an den pfad
				if(!knoten[i].name.equals("end")) //falls weitere namen folgen werden
				{
					neuerPfad += ","; //füge ein komma hinzu
				}
				findAllPaths(knoten[i], ziel, neuerPfad); //gib die pfade aller nachbarknoten aus, die zum ende führen
			}
		}
		
		besucht[index] = false; //wenn dieser knoten abgearbeitet -> setze auf unbesucht
	}
	

	public void solveTask2()
	{
		readAdjacentMatrix();
		
		Knoten start = new Knoten("start");
		Knoten ziel = new Knoten("end");
		
		besucht = new boolean[matrix.length];
		besucht[getMatrixIndexOfKnoten(start)] = true;
		allowTwice = new boolean[matrix.length];
		numberPaths2 = 0;
		
		//für jede small cave einmal alle durchgehen und doppelt besuhcbar machen
		for(int i=0; i<allowTwice.length; i++)
		{
			for(int j=0; j<allowTwice.length; j++)
			{
				allowTwice[j] = false;
			}
			
			allowTwice[i] = true;
			findAllPaths2(start, ziel, "start,", i);
		}
		for(int j=0; j<allowTwice.length; j++)
		{
			allowTwice[j] = false;
		}
		findAllPaths2(start, ziel, "start,", 0);
		
		erg2 = numberPaths2;
	}
	
	public boolean containsTriplets(String s)
	{
		//zähle für jeden knoten namen, ob er mehr als drei mal vorkommt
		for(int i=0; i<anzahlKnoten; i++)
		{
			String regex = knoten[i].name;
			if(regex.toUpperCase().equals(regex)) //nicht für die big caves
			{
				continue;
			}
			
			int cntOccurs = countRegex(s, regex);
			if(cntOccurs>2)
			{
				return true;
			}
		}
		return false;
	}
	
	public int countRegex(String s, String regex)
	{
		int cnt = 0;
		String[] sArr = s.split(",");
		for(int i=0; i<sArr.length; i++)
		{
			if(sArr[i].equals(regex))
			{
				cnt++;
			}
		}
		return cnt;
	}
	
	public boolean isUnique(String search)
	{
		for(int i=0; i<paths.length; i++)
		{
			if(paths[i].equals(search))
			{
				return false;
			}
		}
		return true;
	}
	
	public void findAllPaths2(Knoten start, Knoten ziel, String pfad, int currTwiceAllowed) 
	{
		int index = getMatrixIndexOfKnoten(start); //index des aktuellen knotens in der matrix
		
		if(multipleTimesVisitable[index]) //wenn er mehrfach besuchbar ist, 
		{
			besucht[index] = false; //lasse seine markierung im besucht feld auf besuchbar
		}
		else //wenn es eine smallCave ist
		{
			if(allowTwice[index] && !start.name.equals("start") && !start.name.equals("end")) //wenn noch zweifach besuchbar
			{
				allowTwice[index] = false;
			}
			else
			{
				besucht[index] = true;
			}
		}
				
		if(start.name.equals(ziel.name)) //falls das ende erreicht ist, 
		{
			if(isUnique(pfad))
			{
				String[] clone = new String[paths.length+1];
				for(int i=0; i<paths.length; i++)
				{
					clone[i]=paths[i];
				}
				clone[clone.length-1] = pfad;
				paths = clone.clone();
				//System.err.println(pfad); //gib den pfad aus
				numberPaths2++; //und inkrementiere die anzahl an pfaden	
			}
			
		}
		else
		{
			for(int i=0; i<matrix[index].length; i++) //besuche alle knoten 
			{
				if(matrix[index][i] == 1 && !besucht[i]) //die nachbarn sind & noch besuchbar
				{
					String neuerPfad = pfad + knoten[i].name; //hänge den knotenNamen an den pfad
					if(!knoten[i].name.equals("end")) //falls weitere namen folgen werden
					{
						neuerPfad += ","; //füge ein komma hinzu
					}
					if(!containsTriplets(neuerPfad))
					{
						findAllPaths2(knoten[i], ziel, neuerPfad, currTwiceAllowed); //gib die pfade aller nachbarknoten aus, die zum ende führen
					}
				}
			}
		}
		
		
		allowTwice[currTwiceAllowed] = true;//thisAllow = true;
		besucht[index] = false; //wenn dieser knoten abgearbeitet -> setze auf unbesucht für den nächsten
	}

	public boolean[] reverse(boolean[] array)
	{
		boolean[] reversedClone = new boolean[array.length];
		
		for(int i=0; i<reversedClone.length; i++)
		{
			reversedClone[i] = !array[i];
		}
		return reversedClone;
	}
	
	
	public static void main(String[] args) throws IOException
	{
		@SuppressWarnings("unused")
		Dec12 d = new Dec12();
	}
	
	public class Knoten
	{
		public String name;
		
		public Knoten(String bez)
		{
			name = bez;
		}
		
		boolean hasConnection(Knoten k)
		{
			for(int i=0; i<inputStringTable.length; i++)
			{
				if(inputStringTable[i][0].equals(name))
				{
					if(inputStringTable[i][1].equals(k.name))
					{
						return true;
					}
				}
				if(inputStringTable[i][1].equals(name))
				{
					if(inputStringTable[i][0].equals(k.name))
					{
						return true;
					}
				}
			}
			return false;
		}
	}
}