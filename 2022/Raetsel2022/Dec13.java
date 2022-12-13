package Raetsel2022;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

import Base.InputKind;

public class Dec13 extends Puzzle2022
{
	public Dec13()
	{
		super(13);
		readInput(InputKind.StringList);
		parse();
		
		computeSolution(1);
		computeSolution(2);

//		printSolution();
		printFormattedSolution("Distress Signal", "sum of correct orders", "decoder key");
	}

	List<Package> packages = new ArrayList<>();

	public void parse()
	{
		for (int i=0; i<inputStringList.length; i+=3)
		{
			String line = inputStringList[i];
			PList pLeft = parseList(line); 
			
			line = inputStringList[i+1];
			PList pRight = parseList(line);
			
//			System.out.println(pLeft + "\n" + pRight + "\n");
			packages.add(new Package(pLeft));
			packages.add(new Package(pRight));
		}
	}
	
	/*
	 * öffnende Klammer: wirf auf neue PList auf stack
	 * ziffer: speichere ziffer in Stringbuffer
	 * komma: parse aktuellen stringbuffer in integer + adde zu oberster PList auf dem Stack
	 * schließende Klammer: hole letzte PList vom Stack + füge sie dem obersten hinzu
	 */
	public PList parseList(String line)
	{
		Stack<PList> stack = new Stack<>();
		PList res = null;
		
		String currNumber = "";
		
		for(int cursor=0; cursor<line.length(); cursor++)
		{
			char c = line.charAt(cursor);
			if(c == '[')
			{
				stack.add(new PList());
			}
			else if (Character.isDigit(c))
			{
				currNumber += c;
			}
			else if (c == ',')
			{
				if(currNumber.length() != 0)
				{
					int number = Integer.parseInt(currNumber);
					currNumber = "";
					stack.peek().contents.add(new PNumber(number));
				}
			}
			else if(c == ']')
			{
				PList top = stack.pop();
				
				if(currNumber.length() != 0)
				{
					int number = Integer.parseInt(currNumber);
					currNumber = "";
					top.contents.add(new PNumber(number));
				}
				
				if(cursor != line.length()-1) //es ist noch was auf dem stack
				{
					stack.peek().contents.add(top);				
				}
				else
				{
					res = top;
				}
			}
		}
		return res;
	}

	@Override
	public void solveTask1()
	{
		int res = 0;
		
		for(int i=0; i<packages.size(); i+=2)
		{
			Package left = packages.get(i);
			Package right = packages.get(i+1);
			
			if(left.compareTo(right)<0)
			{
				res += (i/2 +1); //the pair Index
			}
		}
		
		erg1 = res;
	}

	@Override
	public void solveTask2()
	{
		Package div1 = new Package(parseList("[[2]]"));
		Package div2 = new Package(parseList("[[6]]"));
		packages.add(div1);
		packages.add(div2);
		
		Collections.sort(packages);
		erg2 = (packages.indexOf(div1)+1)*(packages.indexOf(div2)+1);
	}

	public static void main(String[] args)
	{
		new Dec13();
	}
	
	class Package implements Comparable<Package>
	{
		PList content;
		
		public Package(PList content)
		{
			this.content = content;
		}
		
		@Override
		public String toString()
		{
			return content.toString();
		}

		@Override
		public int compareTo(Package o)
		{
			PList myContent = content;
			PList othContent = o.content;

			return myContent.compareToList(othContent);
		}
	}

	abstract class Content
	{
		public int compareTo(Content oth)
		{
			int resComp;

			if (this.getClass() == oth.getClass())
			{
				if (this.getClass() == PNumber.class)
				{
					resComp = ((PNumber) this).compareToNumber((PNumber) oth);
				} 
				else 
				{
					resComp = ((PList) this).compareToList((PList) oth);
				}
			} 
			else
			{
				if (this.getClass() == PNumber.class)
				{
					resComp = ((PNumber) this).convertToPList().compareToList((PList) oth);
				} 
				else
				{
					resComp = ((PList) this).compareToList(((PNumber) oth).convertToPList());
				}
			}

			return resComp;
		}
	}

	class PList extends Content
	{
		ArrayList<Content> contents = new ArrayList<>();

		public int compareToList(PList o)
		{
			int comp = 0;
			for (int i=0; i<contents.size(); i++)
			{
				if(i>=o.contents.size()) //other ran out of items
				{
					comp = 1;
					break;
				}
				
				int currComp = this.contents.get(i).compareTo(o.contents.get(i));
				if (currComp != 0)
				{
					comp = currComp;
					break;
				}
			}
			
			if(comp == 0 && contents.size() < o.contents.size())
			{
				comp = -1;
			}
			
			return comp;
		}
		
		@Override
		public String toString()
		{
			return contents.toString();
		}
	}

	class PNumber extends Content
	{
		int number;
		
		public PNumber(int n)
		{
			this.number = n;
		}

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
		
		@Override
		public String toString()
		{
			return number + "";
		}
	}

	enum Type
	{
		PList, PNumber;
	}
}

