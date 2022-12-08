package Raetsel2022;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

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

		printSolution();
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
				if(checkLeft(i, j).containsKey(true) || checkRight(i,j).containsKey(true) || checkUp(i,j).containsKey(true) || checkDown(i,j).containsKey(true))
				{
					res++;
				}
			}
		}
		
		res += lines + lines;
		res += (columns-2) + (columns-2);
		
		return res;
	}
	
	public HashMap<Boolean, Integer> checkLeft(int posY, int posX)
	{
		int countSteps = 0;
		int originTree = inputIntTable[posY][posX];
		for (int col=0; col<posX; col++)
		{
			countSteps++;
			int currTree = inputIntTable[posY][col];
			if(currTree>=originTree)
			{
				HashMap<Boolean, Integer> tmp = new HashMap<>();
				tmp.put(false, countSteps);
				return tmp;
			}
		}
		HashMap<Boolean, Integer> tmp = new HashMap<>();
		tmp.put(true, countSteps);
		return tmp;
	}
	
	public HashMap<Boolean, Integer> checkRight(int posY, int posX)
	{
		int countSteps = 0;
		int originTree = inputIntTable[posY][posX];
		for (int col=posX+1; col<columns; col++)
		{
			countSteps++;
			int currTree = inputIntTable[posY][col];
			if(currTree>=originTree)
			{
				HashMap<Boolean, Integer> tmp = new HashMap<>();
				tmp.put(false, countSteps);
				return tmp;
			}
		}
		HashMap<Boolean, Integer> tmp = new HashMap<>();
		tmp.put(true, countSteps);
		return tmp;
	}
	
	public  HashMap<Boolean, Integer> checkUp(int posY, int posX)
	{
		int countSteps = 0;
		int originTree = inputIntTable[posY][posX];
		for (int row=0; row<posY; row++)
		{
			countSteps++;
			int currTree = inputIntTable[row][posX];
			if(currTree>=originTree)
			{
				HashMap<Boolean, Integer> tmp = new HashMap<>();
				tmp.put(false, countSteps);
				return tmp;
			}
		}
		HashMap<Boolean, Integer> tmp = new HashMap<>();
		tmp.put(true, countSteps);
		return tmp;
	}
	
	public HashMap<Boolean, Integer> checkDown(int posY, int posX)
	{
		int countSteps = 0;
		int originTree = inputIntTable[posY][posX];
		for (int row=posY+1; row<lines; row++)
		{
			countSteps++;
			int currTree = inputIntTable[row][posX];
			if(currTree>=originTree)
			{
				HashMap<Boolean, Integer> tmp = new HashMap<>();
				tmp.put(false, countSteps);
				return tmp;
			}
		}
		HashMap<Boolean, Integer> tmp = new HashMap<>();
		tmp.put(true, countSteps);
		return tmp;
	}
	
	
	public int bruteForce2()
	{
		int max = Integer.MIN_VALUE;
		
		for (int i=1; i<lines-1; i++)
		{
			for (int j=1; j<columns-1; j++)
			{
				Map <Boolean, Integer> ml = checkLeft(i, j);
				Set<Boolean> key = ml.keySet();
				int scenicScore = ml.get(key.toArray()[0]);
				
				Map <Boolean, Integer> mr = checkRight(i, j);
				key = mr.keySet();
				scenicScore *= mr.get(key.toArray()[0]);
				
				Map <Boolean, Integer> mu = checkUp(i, j);
				key = mu.keySet();
				scenicScore *= mu.get(key.toArray()[0]);
				
				Map <Boolean, Integer> md = checkDown(i, j);
				key = md.keySet();
				scenicScore *= md.get(key.toArray()[0]);
				
				if(scenicScore > max)
					max = scenicScore;
			}
		}
		
		return max;
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
