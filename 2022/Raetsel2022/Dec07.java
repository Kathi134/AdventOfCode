package Raetsel2022;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Stack;

import Base.InputKind;

public class Dec07 extends Puzzle2022
{
	public Dec07()
	{
		super(7);
		readInput(InputKind.StringList);
		
		computeSolution(1);
		computeSolution(2);

//		printSolution();
		printFormattedSolution("No Space Left on Device", "direcories smaller than 100000", "smallest directory to be deleted");
	}
	
	Stack<String> path = new Stack<>();
	HashMap<String, Integer> size = new HashMap<>();
	
	@Override
	public void solveTask1()
	{
		executeFileOps();
		
		int res=0;
		for(String k: size.keySet())
		{
			if(size.get(k) < 100000)
			{
				res += size.get(k);
			}
		}
		
		erg1 = res;	
	}
	
	public void executeFileOps()
	{
		for(int i=0; i<lines; i++)
		{
			String s = inputStringList[i];
//			System.err.println(s);
			String[] tmp = s.split(" ");
			if(tmp[1].equals("cd"))
			{
				if(tmp[2].equals(".."))
				{
					path.pop();
				}
				else
				{
					path.push(tmp[2]);
					size.put(absolutePath(path.size()), 0);
				}
			}
			else if(tmp[1].equals("ls"))
			{}
			else if(tmp[0].equals("dir"))
			{}
			else
			{
				int fileSize = Integer.parseInt(tmp[0]);
				updateMap(fileSize);
			}
		}
	}
	
	public String absolutePath(int limit)
	{
		StringBuilder p = new StringBuilder();
		for(int i=0; i<limit; i++)
		{
			p.append(path.get(i)+"/");
		}
		return p.toString();
	}
	
	public void updateMap(int fileSize)
	{
		// for each substring on buildpath -> everything on the stack, add curr dirs size 
		for(int i=0; i<path.size(); i++)
		{
			String k = absolutePath(path.size()-i);
			int oldDirSize = size.get(k);
//			System.out.println(k + ": " + (oldDirSize+fileSize));
			size.put(k, oldDirSize+fileSize);
		}
	}
	
	@Override
	public void solveTask2()
	{
		int totalUsedSpace = size.get("//");
		int freeSpace = 70000000 - totalUsedSpace;
		int requiredDelete = 30000000 - freeSpace;
		
		ArrayList<Integer> bigEnough = new ArrayList<>();
		for(String k: size.keySet())
		{
			if(size.get(k) > requiredDelete)
			{
				bigEnough.add(size.get(k));
			}
		}
		
		Collections.sort(bigEnough);
		erg2 = bigEnough.get(0);
	}
	
	public static void main(String[] args)
	{
		new Dec07();
	}
}



