package Raetsel2022;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Stack;

import Base.InputKind;

public class Dec13 extends Puzzle2022
{
	public Dec13()
	{
		super(13);
		readInput(InputKind.StringList);

		computeSolution(1);
		computeSolution(2);

		printSolution();
	}

	List<Package> packages = new ArrayList<>();

	public void parse()
	{
		/*
		 * öffnende Klammer: wirf auf neue PList auf stack
		 * ziffer: speichere ziffer in Stringbuffer
		 * komma: parse aktuellen stringbuffer in integer + adde zu oberster PList auf dem Stack
		 * schließende Klammer: hole letzte PList vom Stack + füge sie dem obersten hinzu
		 */
		Stack<Object> subPackages = new Stack<>();
		
		for (int i = 0; i < inputStringList.length; i++)
		{
			String line = inputStringList[i];
			if (!line.isBlank())
			{
				HashMap<Integer, Integer> map = getBracketPairs(line);
				
				PList p = parseList(line); //ohne die äußersten Klammern
				
			}
			
		}
			
	}
	
	
	public PList parseList(HashMap<Integer, Integer> map, String line)
	{
		PList p;
		
		int cursor = 0;
		
		if(line.charAt(cursor) == '[')
		{
			p = new PList();
			int closingBracket = map.get(cursor);
			String newSubline = line.substring(cursor, closingBracket);
			
			
			parseList(newSubline);
		}
		
		while(true)
		{
			int opening = line.indexOf("[");
			int closing = line.indexOf("]");
		}
	}
	
	public HashMap<Integer, Integer> getBracketPairs(String line)
	{
		HashMap<Integer, Integer> res = new HashMap<>();
		
		Stack<Integer> bracketIndices = new Stack<>();
		for(int i=0; i<line.length(); i++)
		{
			if(line.charAt(i) == '[')
			{
				bracketIndices.push(i);
			}
			else if(line.charAt(i) == ']') 
			{
				int openingIndex = bracketIndices.pop();
				if(res.keySet().contains(openingIndex))
					System.err.println("möp");
				res.put(openingIndex, i);
			}
		}
		
		return res;
	}

	@Override
	public void solveTask1()
	{
		parse();
	}

	@Override
	public void solveTask2()
	{

	}

	public static void main(String[] args)
	{
		new Dec13();
	}
}

//class PackagePair
//{
//	Package left;
//	Package right;
//	
//	public PackagePair(Package l, Package r)
//	{
//		left = l;
//		right = r;
//	}
//	
//	public boolean isInCorrectOrder()
//	{
//		return left.isOrderedWithOtherPackage(right);
//	}
//}

class Package
{
	PList content;

	public boolean isOrderedWithOtherPackage(Package o)
	{
		PList my = content;
		PList oth = o.content;

		int compIndex = my.compareToList(oth);

		if (compIndex == 0)
			System.out.println(compIndex);
		return compIndex > 0;
	}
}

abstract class Content
{
	Type type;

	public int compareTo(Content oth)
	{
		int compIndex = 0;

		int resComp;

		if (this.type == oth.type)
		{
			if (this.type == Type.PNumber)
			{
				resComp = ((PNumber) this).compareToNumber((PNumber) oth);
			} 
			else // if (this.type == Type.PList)
			{
				resComp = ((PList) this).compareToList((PList) oth);
			}
		} 
		else
		{
			if (this.type == Type.PNumber)
			{
				resComp = ((PNumber) this).convertToPList().compareToList((PList) oth);
			} 
			else // if(oth.type == Type.PNumber)
			{
				resComp = ((PList) this).compareToList(((PNumber) oth).convertToPList());
			}
		}

		return resComp;
	}
}

class PList extends Content
{
	Type type = Type.PList;

	ArrayList<Content> contents = new ArrayList<>();

	public int compareToList(PList o)
	{
		int comp = 0;
		if (this.contents.size() != o.contents.size())
		{
			comp = this.contents.size() - o.contents.size();
		} 
		else
		{
			for (int i = 0; i < contents.size(); i++)
			{
				int currComp = this.contents.get(i).compareTo(o.contents.get(i));
				if (currComp != 0)
				{
					comp = currComp;
					break;
				}
			}
		}
		return comp;
	}
}

class PNumber extends Content
{
	Type type = Type.PNumber;

	int number;

	public int compareToNumber(PNumber o)
	{
		return this.number - o.number;
	}

	public PList convertToPList()
	{
		PList p = new PList();
		p.contents.add(this);
		return p;
	}
}

enum Type
{
	PList, PNumber;
}
