package Raetsel2021;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

public class Dec15 extends Puzzle
{
	ArrayList<Node> information = new ArrayList<>();
	private int endPosX;
	private int endPosY;
	
	private int[][][] mapComponents;
	private int[][] filledMap;
	private int erg;
	
	private Node[][] nodes;
	
	public Dec15() throws IOException
	{
		super(15);
		readInput("intTable");
		mapComponents = new int[9][lines][columns];
		
		System.err.println("--takes about 2 minutes to compute--");
		computeSolution(1);
		erg1 = erg;
		computeSolution(2);
		erg2 = erg;
		//printSolution();
		
		printFormattedSolution("Chiton", 
				"lowest riskLevel", 
				"lowest riskLevel on extended Map");
	}
	
	public void solveTask1()
	{
		erg = 0;
		endPosX = inputIntTable[0].length-1;
		endPosY = inputIntTable.length-1;
		initializeList();
		
		while(information.size()!=0)  //es ist ncoh was drin
		{
			dijkstra();
		}
	}
	
	public void initializeList() 
	{
		nodes = new Node[inputIntTable.length][inputIntTable[0].length];
		for(int i=0; i<inputIntTable.length; i++)
		{
			for(int j=0; j<inputIntTable[i].length; j++)
			{
				Node n = new Node(i, j);
				nodes[i][j] = n;
				information.add(n);
			}
		}
		information.get(0).setGesamtDistanz(0);
	}
	
	public void dijkstra()
	{
		Node currNode = information.remove(0); //entferne knoten mit niedrigster gesdis aus der liste
		currNode.setVisited(true);
		//System.err.println(currNode.yPos + "|" + currNode.xPos);
		
		if(currNode.xPos == endPosX && currNode.yPos == endPosY)
		{
			erg = currNode.getGesamtDistanz();
		}
		else
		{
			ArrayList<int[]> neighbours = getDirectAdjacents(currNode.yPos, currNode.xPos, "intTable");
			
			for(int[] p: neighbours) // p hat [y][x]
			{
				if(!nodes[p[0]][p[1]].isVisited()) //falls zum nachbar-knoten noch nicht der kleinste weg gefunden wurde
				{
					Node currNeighbour = nodes[p[0]][p[1]];
					int newGesDis = currNode.getGesamtDistanz() + inputIntTable[p[0]][p[1]];
					if(currNeighbour.getGesamtDistanz() > newGesDis)
					{
						currNeighbour.setGesamtDistanz(newGesDis);
					}
				}
			}
		}
		Collections.sort(information);
	}
	
	
	public void solveTask2()
	{
		extendInput();
		//pseudocode: inputIntTable = filledMap
		inputIntTable = clone(filledMap);
		solveTask1();
		//print(inputIntTable);
	}
	
	/* gibt die komponenten für die neue erweiterte map
	   00 01 02 03 04 
	   01 02 03 04 05 
	   02 03 04 05 06 
	   03 04 05 06 07
	   04 05 06 07 08 */
	public void extendInput()
	{
		mapComponents[0] = inputIntTable;
		for (int i=1; i<=8; i++)
		{
			mapComponents[i] = computeIncrement(mapComponents[i-1]);
		}
		fillMap();
	}
	
	/* baut die neue erweiterte map
	   00 01 02 03 04 
	   05 06 07 08 09 
	   10 11 12 13 14 
	   15 16 17 18 19
	   20 21 22 23 24 */
	public void fillMap()
	{
		filledMap = new int[lines*5][columns*5];
		
		for(int cntC=0; cntC<5; cntC++)
		{
			for(int cntL=0; cntL<5; cntL++)
			{
				for(int i=lines*cntL; i<lines*(cntL+1); i++)
				{
					for(int j=columns*cntC; j<columns*(cntC+1); j++)
					{
						filledMap[i][j] = mapComponents[cntL+cntC][i%lines][j%columns];
					}
				}
			}
		}

		//print(filledMap);
	}
	
	public int[][] computeIncrement(int[][] predecessor)
	{
		int[][] incremented = new int[predecessor.length][predecessor[0].length];
		
		for(int i=0; i<incremented.length; i++)
		{
			for(int j=0; j<incremented.length; j++)
			{
				int value = predecessor[i][j] + 1;
				if (value==10)
				{
					value=1;
				}
				incremented[i][j] = value;
			}
		}
		return incremented;
	}
	
	public void print(int[][] arr)
	{

		for(int j=0; j<arr.length; j++)
		{
			for (int i=0; i<arr[0].length; i++)
			{
				System.err.printf(arr[j][i] + "");
			}
			System.err.println();
		}
		System.err.println();
	}
	
	public static void main(String[] args) throws IOException
	{
		@SuppressWarnings("unused")
		Dec15 d = new Dec15();
	}
}

class Node implements Comparable<Node>
{
	public final int xPos;
	public final int yPos;
	private int gesamtDistanz; 
	private boolean visited;
	
 	public Node(int yPos, int xPos)
	{
		this.xPos = xPos;
		this.yPos = yPos;
		gesamtDistanz = Integer.MAX_VALUE;
	}
 	

	public boolean isVisited()
	{
		return visited;
	}

	public void setVisited(boolean visited)
	{
		this.visited = visited;
	}

	public int getGesamtDistanz()
	{
		return gesamtDistanz;
	}

	public void setGesamtDistanz(int gesamtDistanz)
	{
		this.gesamtDistanz = gesamtDistanz;
	}


	@Override
	public int compareTo(Node o)
	{
		Integer thisGesDis = gesamtDistanz;
		Integer nextGesDis = o.gesamtDistanz;
		return thisGesDis.compareTo(nextGesDis);
	}
}
