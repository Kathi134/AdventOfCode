package Raetsel2021;

import java.io.IOException;
import java.util.ArrayList;

//solved by hand -> incomplete code
public class Dec23 extends Puzzle
{
	ArrayList<Amphipod> amphipods = new ArrayList<Amphipod>();
	int leastCost = Integer.MAX_VALUE;
	
	public void format()
	{
		inputStringList[3] = inputStringList[3].replace("  ", "##") +  "##";
		inputStringList[4] = inputStringList[4].replace("  ", "##") + "##";
		
		inputCharTable = new char[lines][inputStringList[0].length()];
		for (int i=0; i<lines; i++)
		{
			inputCharTable[i] = inputStringList[i].toCharArray();
		}
		
		for(int i=2; i<=3; i++)
		{
			for(int j=3; j<=9; j+=2)
			{
				char type = inputCharTable[i][j];
				switch (type)
				{
				case 'A': amphipods.add(new A(i,j)); break;
				case 'B': amphipods.add(new B(i,j)); break;
				case 'C': amphipods.add(new C(i,j)); break;
				case 'D': amphipods.add(new D(i,j)); break;
				}
			}
		}
		
		for(int i=0; i<amphipods.size(); i++)
		{
			Amphipod a = amphipods.get(i);
			if(a.aimedRoom == a.position[1] && a.position[0] == 0)
			{
				a.roomFound = true;
			}
		}
	}
	
	public Dec23() throws IOException
	{
		super(23);
		readInput("StringList");
		format();
		printStringList();
		
		computeSolution(1);
		erg1 = leastCost;
		computeSolution(2);
		printSolution();
		
		System.err.println("\n--incomplete code, since solved by hand--");
	}
	
	public void solveTask1()
	{
		/*für jeden Stein: bewege zu allen möglichen freien Feldern 
		  bewege jeden anderen Stein zu allen möglcihen freien Feldern
		  regeln: (nicht die türen blockieren), in raum nur wenn eigener und kein fremder drin
		  sobald einmal in room, setze auf room found*/
		findAllPossibilities(0);		
	}
	
	public void findAllPossibilities(int costs) 
	{
		printCharTable();
		if(everyRoomFound())
		{
			leastCost = Integer.min(leastCost, costs);
		}
		
		for(int a=0; a<amphipods.size(); a++) //besuche alle knoten 
		{
			Amphipod amph = amphipods.get(a);
			
			for(int i=1; i<=3; i++)
			{
				for(int j=1; j<=11; j++)
				{
					if(amph.moved<3)
					{
						if(inputCharTable[i][j] == '.')
						{
							inputCharTable[amph.position[0]][amph.position[1]] = '.';
							int newCosts = amph.move(i, j) + costs;
							inputCharTable[amph.position[0]][amph.position[1]] = 'F';
							if(newCosts < leastCost)
							{
								findAllPossibilities(newCosts);											
							}
						}
					}
				}
			}
		}
		
		
	}
	
	public boolean everyRoomFound()
	{
		for(int i=0; i<amphipods.size(); i++)
		{
			Amphipod a = amphipods.get(i);
			if(!(a.aimedRoom == a.position[1] && (a.position[0] == 2 || a.position[0] == 3)))
			{
				return false;
			}
		}
		return true;
	}
	
	public void solveTask2()
	{
		
	}
	
	public static void main(String[] args) throws IOException
	{
		new Dec23();
	}
}

class Amphipod
{
	String name;
	int aimedRoom; //index der spalte
	int moveCost;
	int moved = 0; //wenn schon in hallway gemoved dann false
	boolean roomFound; //wenn schon in zimmer nie mehr raus
	int[] position = new int[2];
	
	public int move(int y, int x)
	{
		int hor = Math.abs(y-position[0]);
		int ver = Math.abs(x-position[1]);
		position[0] = y;
		position[1] = x;
		moved++;
		return moveCost * (hor+ver);
	}
}

class A extends Amphipod
{
	public A(int y, int x)
	{
		name = "Amber";
		aimedRoom = 3;
		moveCost = 1;
		position = new int[] {y, x};
	}
	
}

class B extends Amphipod
{
	public B(int y, int x)
	{
		name = "Bronze";
		aimedRoom = 5;
		moveCost = 10;
		position = new int[] {y, x};
	}
	
}

class C extends Amphipod
{
	public C(int y, int x)
	{
		name = "Copper";
		aimedRoom = 7;
		moveCost = 100;
		position = new int[] {y, x};
	}
	
}

class D extends Amphipod
{
	public D(int y, int x)
	{
		name = "Desert";
		aimedRoom = 9;
		moveCost = 1000;
		position = new int[] {y, x};
	}
	
}