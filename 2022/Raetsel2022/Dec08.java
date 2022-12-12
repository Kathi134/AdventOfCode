package Raetsel2022;


import Base.InputKind;

public class Dec08 extends Puzzle2022
{
	boolean[][] counted = new boolean[lines][columns];
	
	public Dec08()
	{
		super(8);
		readInput(InputKind.intTable);
		
		computeSolution(1);
		computeSolution(2);

		printFormattedSolution("Treetop Tree House", "visible trees", "best tree house scenic score");
	}

	@Override
	public void solveTask1()
	{		
		erg1 = bruteForce1();
	}

	public int bruteForce1()
	{
		int res = 0;
		
		for (int i=1; i<lines-1; i++)
		{
			for (int j=1; j<columns-1; j++)
			{
				if(checkLeft(i, j) || checkRight(i,j) || checkUp(i,j) || checkDown(i,j))
				{
					res++;
				}
			}
		}
		
		res += lines + lines;
		res += (columns-2) + (columns-2);
		
		return res;
	}
	
	public boolean checkLeft(int posY, int posX)
	{
		int originTree = inputIntTable[posY][posX];
		for (int col=0; col<posX; col++)
		{
			int currTree = inputIntTable[posY][col];
			if(currTree>=originTree)
			{
				return false;
			}
		}
		return true;
	}
	
	public boolean checkRight(int posY, int posX)
	{
		int originTree = inputIntTable[posY][posX];
		for (int col=posX+1; col<columns; col++)
		{
			int currTree = inputIntTable[posY][col];
			if(currTree>=originTree)
			{
				return false;
			}
		}
		return true;
	}
	
	public boolean checkUp(int posY, int posX)
	{
		int originTree = inputIntTable[posY][posX];
		for (int row=0; row<posY; row++)
		{
			int currTree = inputIntTable[row][posX];
			if(currTree>=originTree)
			{
				return false;
			}
		}
		return true;
	}
	
	public boolean checkDown(int posY, int posX)
	{
		int originTree = inputIntTable[posY][posX];
		for (int row=posY+1; row<lines; row++)
		{
			int currTree = inputIntTable[row][posX];
			if(currTree>=originTree)
			{
				return false;
			}
		}
		return true;
	}
	
	
	public int bruteForce2()
	{
		int max = Integer.MIN_VALUE;
		
		for (int i=1; i<lines-1; i++)
		{
			for (int j=1; j<columns-1; j++)
			{
				int scenicScore = sightLeft(i, j);
				
				scenicScore *= sightRight(i, j);
				
				scenicScore *= sightUp(i, j);
				
				scenicScore *= sightDown(i, j);
				
				if(scenicScore > max)
					max = scenicScore;
			}
		}
		
		return max;
	}
	
	public int sightLeft(int posY, int posX)
	{
		int count = 0;
		int originTree = inputIntTable[posY][posX];
		while(posX > 0)
		{
			posX--;
			count++;
			
			int currTree = inputIntTable[posY][posX]; 
			if(currTree >= originTree)
			{
				break;
			}
		}
		return count;
	}
	
	public int sightRight(int posY, int posX)
	{
		int count = 0;
		int originTree = inputIntTable[posY][posX];
		while(posX < columns-1)
		{
			posX++;
			count++;
			
			int currTree = inputIntTable[posY][posX]; 
			if(currTree >= originTree)
			{
				break;
			}
		}
		return count;
	}
	
	public int sightUp(int posY, int posX)
	{
		int count = 0;
		int originTree = inputIntTable[posY][posX];
		while(posY > 0)
		{
			posY--;
			count++;
			
			int currTree = inputIntTable[posY][posX]; 
			if(currTree >= originTree)
			{
				break;
			}
		}
		return count;
	}
	
	public int sightDown(int posY, int posX)
	{
		int count = 0;
		int originTree = inputIntTable[posY][posX];
		while(posY < lines-1)
		{
			posY++;
			count++;
			
			int currTree = inputIntTable[posY][posX]; 
			if(currTree >= originTree)
			{
				break;
			}
		}
		return count;
	}
	
	@Override
	public void solveTask2()
	{
		erg2 = bruteForce2();
//		for(int i=0; i<lines; i++)
//		{
////			List<Integer> line = new ArrayList();
//			for(int j=0; j<columns; j++)
//			{
//				int currTree = inputIntTable[i][j];
//				checkLefts();
//				if(currTree > maxLeft)
//				{
//					
//				}
//				checkRights();
//				chcekUp();
//				checkDown();
//			}
//		}
	}
	
	public static void main(String[] args)
	{
		new Dec08();
	}
}

//int res = 0;
//
//for(int i=1; i<lines-1; i++)
//{
//	int maxTreeHorizontal = Integer.MIN_VALUE;
//	List<Integer> highestIndices = new ArrayList<>();
//	
//	for(int j=0; j<columns; j++)
//	{
//		if(inputIntTable[i][j] > maxTreeHorizontal)
//		{
//			maxTreeHorizontal = inputIntTable[i][j];
//			highestIndices.clear();
//			highestIndices.add(j);
//			res++;
//		}
//		else if(inputIntTable[i][j] == maxTreeHorizontal)
//		{
//			highestIndices.add(j);
//			res++;
//		}
//	}
//	
//	int leftMostHighest = highestIndices.get(0);
//	int rightMostHighest = highestIndices.get(highestIndices.size());
//	
//	counted[i][leftMostHighest] = true;
//	counted[i][rightMostHighest] = true;
//	res+=2;
//	
//	//spread to the left
//	int maxTreeLeft = Integer.MIN_VALUE;
//	for(int l=0; l<leftMostHighest; l++)
//	{
//		if(inputIntTable[i][l] > maxTreeLeft)
//		{
//			maxTreeLeft = inputIntTable[i][l];
//			counted[i][l] = true;
//			res++;
//		}
//	}
//	
//	List<Integer> clone = new ArrayList<>(highestIndices);
//	for(Integer h: clone)
//	{
//		//check if vertically visible 
//		for(int r=0; r<lines; r++)
//		{
//			if(inputIntTable[r][h] > maxTreeHorizontal)
//			{
//				highestIndices.remove(h);
//			}
//		}
//	}
//	
////	res += highestIndices.size();
//}

//res += lines + lines;
//res += (columns-2) + (columns-2);
//erg1 = res;
