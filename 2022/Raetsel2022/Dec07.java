package Raetsel2022;

import java.util.HashMap;
import java.util.Stack;

import Base.InputKind;
//
//public class Dec07 extends Puzzle2022
//{
//	public Dec07()
//	{
//		super(7);
//		readInput(InputKind.StringList);
//		
//		computeSolution(1);
//		computeSolution(2);
//
//		printSolution();
//	}
//	
//	
//	Stack<String> path = new Stack<>();
//	HashMap<String, Integer> size = new HashMap<>();
//	
//	@Override
//	public void solveTask1()
//	{
//		for(int i=0; i<lines; i++)
//		{
//			String s = inputStringList[i];
//			String[] tmp = s.split(" ");
//			if(tmp[1].equals("cd"))
//			{
//				if(tmp[2].equals(".."))
//				{
//					path.pop();
//				}
//				else
//				{
//					path.push(tmp[2]);
//					size.put(tmp[2], 0);
//				}
//			}
//			else if(tmp[1].equals("ls"))
//			{}
//			else if(tmp[0].equals("dir"))
//			{}
//			else
//			{
//				int fileSize = Integer.parseInt(tmp[0]);
//				updateMap(fileSize);
//			}
//		}
//		erg1 = result();
//		
//	}
//	
//	public void updateMap(int fileSize)
//	{
//		// for each substring on buildpath -> everythoing on the stack, add curr dirs size 
//		for(int i=0; i<path.size(); i++)
//		{
//			String k = path.get(i);
//			int oldDirSize = size.get(k);
//			size.put(k, oldDirSize+fileSize);
//		}
//	}
//	
//	public int result()
//	{
//		int res=0;
//		for(String k: size.keySet())
//		{
//			if(size.get(k) < 100000)
//			{
//				res += size.get(k);
//			}
//		}
//		return res;
//	}
//	
//	@Override
//	public void solveTask2()
//	{
//		
//	}
//	
//	public static void main(String[] args)
//	{
//		new Dec07();
//	}
//}
//
//
//
//