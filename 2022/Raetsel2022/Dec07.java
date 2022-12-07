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

package dev.theturkey.aoc22;

import java.util.ArrayList;
import java.util.List;

public class Day07 extends AOCPuzzle
{
	public Day07()
	{
		super("7");
	}

	@Override
	void solve(List<String> input)
	{
		Folder root = new Folder();
		root.name = "/";
		Folder currentFold = root;
		for(int i = 1; i < input.size(); i++)
		{
			String[] parts = input.get(i).split(" ");

			if(parts[0].equals("$"))
			{
				if(parts[1].equals("cd"))
				{
					if(parts[2].equals(".."))
					{
						currentFold = currentFold.parent;
					}
					else
					{
						for(Node n : currentFold.contents)
						{
							if(n.name.equals(parts[2]))
							{
								currentFold = (Folder) n; // Should check, but meh
								break;
							}
						}
					}
				}
			}
			else if(parts[0].equals("dir"))
			{
				Folder folder = new Folder();
				folder.parent = currentFold;
				folder.name = parts[1];
				currentFold.contents.add(folder);
			}
			else
			{
				File file = new File();
				file.parent = currentFold;
				file.name = parts[1];
				file.size = Integer.parseInt(parts[0]);
				currentFold.contents.add(file);
			}
		}

		List<Folder> smallerFolders = new ArrayList<>();
		List<Folder> toCheck = new ArrayList<>(root.getSubFolders());

		while(toCheck.size() > 0)
		{
			Folder fold = toCheck.remove(0);
			toCheck.addAll(fold.getSubFolders());
			if(fold.getSize() <= 100000)
				smallerFolders.add(fold);
		}

		long sum = 0;
		for(Folder f : smallerFolders)
			sum += f.getSize();
		erg1 = sum;

		long totalSpace = 70000000;
		long freeSpace = totalSpace - root.getSize();

		long smallestFree = Integer.MAX_VALUE;
		toCheck = new ArrayList<>(root.getSubFolders());

		while(toCheck.size() > 0)
		{
			Folder fold = toCheck.remove(0);
			toCheck.addAll(fold.getSubFolders());
			long size = fold.getSize();
			if(freeSpace + size > 30000000 && size < smallestFree)
				smallestFree = size;
		}
		lap(smallestFree);
	}

	public static abstract class Node
	{
		public Folder parent;
		public String name;

		public abstract long getSize();
	}

	public static class File extends Node
	{
		public int size;

		@Override
		public long getSize()
		{
			return size;
		}
	}

	public static class Folder extends Node
	{
		public List<Node> contents = new ArrayList<>();

		public List<Folder> getSubFolders()
		{
			List<Folder> folders = new ArrayList<>();
			for(Node n : contents)
				if(n instanceof Folder)
					folders.add((Folder) n);
			return folders;
		}

		public long getSize()
		{
			long size = 0;
			for(Node n : contents)
				size += n.getSize();
			return size;
		}
	}
}
