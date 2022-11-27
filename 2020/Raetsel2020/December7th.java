package Raetsel2020;

import java.io.File;
import java.io.IOException;

public class December7th extends Puzzle
{
	//task 1
	private static String[][] bags;
	private static String erg = "shiny gold";
	private static int numberBags = 0;
	private static String[] numBags;
	private static int sumBags = 0;
	
	public static void solveTask1()
	{
		for(int i=0; i<bags.length; i++) //erste zeile fehlt, weil nur subBags hinzugefügt werden
		{
			if(!bags[i][0].contains("shiny gold")) //für alle außer für die shiny gold bag
			{
				//System.err.println(bags[i][0]);
				checkArgs(i);
			}
		}
		checkFirsts();
		numBags = erg.split("-");
		numberBags = numBags.length -1; //shiny gold bag zählt selbst nicht mit
	}
	
	public static void checkFirsts()
	{
		for(int lineIndex=0; lineIndex<lines; lineIndex++)
		{
			int indexBag = bags[lineIndex][0].indexOf(" bags");
			String name = bags[lineIndex][0].substring(0, indexBag);
			
			for(int i=1; i<bags[lineIndex].length; i++)
			{
				String currSubBag = getNameOutArg(bags[lineIndex][i]);
				if(!currSubBag.contains("other"))
				{
					boolean b = glanceNext(currSubBag);
					if(b)
					{
						if(!erg.contains(name))
						{
							erg += " - " + name;
							//System.err.println(erg);
						}
					}	
				}
			}
		}
	}
	
	public static void checkArgs(int lineIndex)
	{
		for(int arg=1; arg<bags[lineIndex].length; arg++)
		{
			String currSubBag = getNameOutArg(bags[lineIndex][arg]);
			if(erg.contains(currSubBag))
			{
				
			}
			else if(currSubBag.contains("other"))
			{
				
			}
			else
			{
				int index = findLineOfName(currSubBag);
				boolean b = glanceNext(currSubBag);
				if(b)
				{
					if(!erg.contains(currSubBag))
					{
						erg += " - " +currSubBag;
					}
				}	
				else
				{
					checkArgs(index);							
				}
				
			}			
		}	
		
	}
	
	public static boolean glanceNext(String name)
	{
		//hat die nächste zeile ein element in erg?
		int lineIndex = findLineOfName(name);
		for(int i=1; i<bags[lineIndex].length; i++)
		{
			if(erg.contains(getNameOutArg(bags[lineIndex][i])))
			{
				return true;
			}
		}
		return false;
	}
	
	public static int findLineOfName(String name)
	{
		for(int i=0; i<bags.length; i++)
		{
			if(bags[i][0].contains(name))
			{
				return i;
			}
		}
		return -2;
	}
	
	public static void read()
	{
		for (int i=0; i<bags.length; i++) //für jede zeile:
		{
			String line = inputStringList[i];
			bags[i] = new String[getNumberSubBags(line)+1]; //jede zeile ist eine neue tabelle mit einem feld für den namen und die einzelnen Subbags
			bags[i][0] = getName(line); //spalte 0 trägt den namen der bag
			fillSubBags(i);			
		}
	}
	
	public static void fillSubBags(int lineIndex)
	{
		String line = inputStringList[lineIndex];
		int divisor = line.indexOf("contain"); //index von c in der aktuellen bag
		String subBags = line.substring(divisor+8);
		
		for (int i=1; i<bags[lineIndex].length; i++)
		{
			if(i<bags[lineIndex].length-1)
			{
				int div = subBags.indexOf(",");
				String currSubBag = subBags.substring(0, div);
				bags[lineIndex][i] = currSubBag;
				subBags = subBags.substring(div+1);
			}
			else
			{
				bags[lineIndex][i] = subBags;
			}
		}
	}
	
	public static String getName(String s)
	{
		int divisor = s.indexOf("contain"); //index von c
		String name = s.substring(0, divisor);
		return name;
	}

	public static String getNameOutArg(String arg)
	{
		if(arg.startsWith(" "))
		{
			arg = arg.replaceFirst(" ", "");
		}
		String[] tmp = arg.split(" ");
		String rtn = tmp[1] + " " + tmp[2];
		return rtn;
	}
	
	public static int getNumberSubBags(String contains)
	{
		int num = 1;
		for(int i=0; i<contains.length(); i++)
		{
			if(contains.charAt(i)==',')
			{
				num++;
			}
		}
		return num;
	}
	
	public static void solveTask2()
	{
		int index = findLineOfName("shiny gold");
		sumBags = sumCarriedBagsLine(1, index);
	}
	
	public static int sumCarriedBagsLine(int präfix, int lineIndex)
	{
		int sumLine = 0;
		for(int i=1; i<bags[lineIndex].length; i++) //gehe die einzelnen spalten der zeile durch
		{
			String arg = bags[lineIndex][i]; 
			int carriedBagsArg = getCarriedBags(arg);
			sumLine += präfix * (carriedBagsArg);
			
		}
		return sumLine;
	}
	
	public static int getCarriedBags(String arg)
	{
		while(arg.startsWith(" "))
		{
			arg=arg.substring(1);
		}
		String name = getNameOutArg(arg);
		
		if(!arg.contains("other")) //wenn es ein echtes arg ist, zähle dessen sub-taschen
		{
			String firstChar = arg.charAt(0)+"";
			int präfix = Integer.parseInt(firstChar);
			int sumNextLine = sumCarriedBagsLine(präfix, findLineOfName(name)); 
			if(sumNextLine!=präfix) sumNextLine+=präfix;
			return sumNextLine;
		}
		else
		{
			return 1;
		}		
	}
	
	public static void main(String[] args) throws IOException
	{
		f = new File("src\\Raetsel2020\\inputs\\December" + 7 + "th_Input");
		prepare();
		readInput("StringList");
		
		bags = new String[lines][];
		read();
		testRead();//test zur read-Methode:
		//testErg(numBags);//test zur solve-Methode
		
		solveTask1();
		solveTask2();
		System.out.println("Task 1 -- Number of different Bags (in)directly carrying shiny gold: " + numberBags);
		System.out.println("Task 2 -- Number of all Bags carried inside shiny gold: " + sumBags);
	}

	public static void testRead()
	{
		read();
		for(int i=0; i<bags.length; i++)
		{
			for(int j=0; j<bags[i].length; j++)
			{
				System.out.print(bags[i][j]+"\t");
			}
			System.out.println();
		}
		System.err.println(findLineOfName("shiny gold"));
	}

	public static void testErg(String[] numBags)
	{
		for(int i=0; i<numBags.length; i++)
		{
			if(!numBags[i].contains("shiny gold"))
			{
				System.err.println(numBags[i]);
			}
		}
	}
	
}