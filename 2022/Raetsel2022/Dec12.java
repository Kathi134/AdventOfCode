package Raetsel2022;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import Base.InputKind;
import Base.Position;

public class Dec12 extends Puzzle2022
{
	ArrayList<Node> information = new ArrayList<>();
	private Position endPos;
	public int min;
	
//	private int[][][] mapComponents;
//	private int[][] filledMap;
	private int erg;
	
	private Node[][] nodes;
	
	public Dec12()
	{
		super(12);
		readInput(InputKind.charTable);
		formatInput();
		
		System.err.println("takes about 4 minutes to compute");
		computeSolution(1);
		computeSolution(2);

		printFormattedSolution("Hill Climbing Algorithm", "steps from start", "steps from best position");
	}
	
	public void formatInput()
	{
		inputIntTable = new int[lines][columns];
		for(int i=0; i<inputCharTable.length; i++)
		{
			for(int j=0; j<inputCharTable[i].length; j++)
			{
				inputIntTable[i][j] = (char) inputCharTable[i][j];
			}
		}
		inputKind = InputKind.intTable;
	}

	public void initializeList(Position start) 
	{
		nodes = new Node[inputIntTable.length][inputIntTable[0].length];
		for(int i=0; i<inputIntTable.length; i++)
		{
			for(int j=0; j<inputIntTable[i].length; j++)
			{
				Node n = new Node(i, j);
				nodes[i][j] = n;
				information.add(n);
				
				if(inputIntTable[i][j] == (int)'S')
				{
					inputIntTable[i][j] = (int)'a';
				}
				if(inputIntTable[i][j] == (int)'E')
				{
					endPos = new Position(i, j);
					inputIntTable[i][j] = (int)'z';
				}
				
				if(new Position(i, j).equals(start))
				{
					n.setGesamtDistanz(0);
				}
			}
		}
		Collections.sort(information);
//		System.out.println(information);
	}
	
	public boolean dijkstra()
	{
		Node currNode = information.remove(0); //entferne knoten mit niedrigster gesdis aus der liste
		currNode.setVisited(true);
//		System.err.println(currNode.pos);
		
		if(currNode.pos.equals(endPos))
		{
			erg = currNode.getGesamtDistanz();			
		}
		else
		{
			ArrayList<Position> neighbours = getDirectAdjacents(currNode.pos.row, currNode.pos.col);
			
			
			for(Position p: neighbours) // p hat [y][x] -> convert to Position
			{
				int nRow = p.row;
				int nCol = p.col;
//				System.err.println(n);
				
				//not reachable
				if(inputIntTable[currNode.pos.row][currNode.pos.col]+1 < inputIntTable[nRow][nCol])
				{
					continue;
				}
				
//				Position n = new Position(p[0], p[1]);
				
				if(!nodes[nRow][nCol].isVisited()) //falls zum nachbar-knoten noch nicht der kleinste weg gefunden wurde
				{
					Node currNeighbour = nodes[nRow][nCol];
//					int newGesDis = currNode.getGesamtDistanz() + inputIntTable[p[0]][p[1]];
					int newGesDis = currNode.getGesamtDistanz()+1;
					
					if(newGesDis > min)
					{
						erg = Integer.MAX_VALUE;
						return false;
					}
					
					if(currNeighbour.getGesamtDistanz() > newGesDis)
					{
						currNeighbour.setGesamtDistanz(newGesDis);
					}
				}
			}
		}
		Collections.sort(information);
		return true;
//		System.out.println(information);
	}
	
	public void getDistance()
	{
		List<Integer> dis = new ArrayList<>();
		for(int i=0; i<nodes.length; i++)
		{
			for(int j=0; j<nodes[i].length; j++)
			{
				dis.add(nodes[i][j].getGesamtDistanz());
			}
		}
		Collections.sort(dis);
//		System.out.println(dis);
	}
	
	public Position findS()
	{
		for(int i=0; i<inputIntTable.length; i++)
		{
			for(int j=0; j<inputIntTable[i].length; j++)
			{
				if(inputIntTable[i][j] == (char)'S')
				{
					return new Position(i, j);
				}
			}
		}
		return null;
	}
	
	@Override
	public void solveTask1()
	{
		min = Integer.MAX_VALUE;
		erg = 0;
		initializeList(findS());
		
		while(information.size()!=0)  //es ist ncoh was drin
		{
			dijkstra();
		}
		
		erg1 = erg;
//		System.out.println(Arrays.deepToString(nodes));
		getDistance();
	}


	@Override
	public void solveTask2()
	{
		List<Position> possibleStarts = new ArrayList<>();
		for(int i=0; i<inputIntTable.length; i++)
		{
			for(int j=0; j<inputIntTable[i].length; j++)
			{
				if(inputIntTable[i][j] == (char)'a')
				{
					possibleStarts.add(new Position(i, j));
				}
			}
		}

		min = (int) erg1;
		
		for(Position p: possibleStarts)
		{
			erg = 0;
			initializeList(p);
			boolean cont;
			while(information.size()!=0)  //es ist ncoh was drin
			{
				cont = dijkstra();
				if(!cont)
					break;
			}
			if(erg >= 0 && erg < min)
			{
				min = erg;
			}
		}
		erg2 = min;
	}
	
	public static void main(String[] args)
	{
		new Dec12();
	}
}

class Node implements Comparable<Node>
{
	Position pos;
	private int gesamtDistanz; 
	private boolean visited;
	
 	public Node(int yPos, int xPos)
	{
		pos = new Position(yPos, xPos);
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

	
	public String toString()
	{
		return gesamtDistanz+"";
	}

	@Override
	public int compareTo(Node o)
	{
		Integer thisGesDis = gesamtDistanz;
		Integer nextGesDis = o.gesamtDistanz;
		return thisGesDis.compareTo(nextGesDis);
	}
}


