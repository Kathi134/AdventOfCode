package Raetsel2022;

import java.util.ArrayList;
import java.util.Arrays;
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
		parse();
		
		int res = 0;
		
		int pairIndex = 1;
		for(int i=0; i<packages.size(); i+=2)
		{
			Package left = packages.get(i);
			Package right = packages.get(i+1);
			
			if(left.isSmallerThan(right))
			{
				res += pairIndex;
			}
			pairIndex++;
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
		
		List<Package> sorted = bubbleSortPackages(packages);
		
		erg2 = (sorted.indexOf(div1)+1)*(sorted.indexOf(div2)+1);
		
	}
	
	public List<Package> bubbleSortPackages(List<Package> packages)
	{
		boolean changed = true;
		
		Package[] packs = new Package[packages.size()];
		for(int i=0; i<packages.size(); i++)
		{
			packs[i] = packages.get(i);
		}
		
		for(int i=0; i<packs.length; i++)
		{
			if(!changed)
			{
				break;
			}
			
			changed = false;
			for(int j=0; j<packs.length-1; j++)
			{
				if(!packs[j].isSmallerThan(packs[j+1]))
				{
					Package tmp = packs[j];
					packs[j] = packs[j+1];
					packs[j+1] = tmp;
					changed = true;
				}
			}
		}
		
		return Arrays.asList(packs);
	}

	public static void main(String[] args)
	{
		new Dec13();
	}
	
	class Package
	{
		PList content;
		
		public Package(PList content)
		{
			this.content = content;
		}

		public boolean isSmallerThan(Package o)
		{
			PList myContent = content;
			PList othContent = o.content;

			int compIndex = myContent.compareToList(othContent);

			if (compIndex == 0)
				System.err.println(compIndex);
			return compIndex < 0;
		}
		
		@Override
		public String toString()
		{
			return content.toString();
		}
	}

	abstract class Content
	{
		protected Type type;

		public int compareTo(Content oth)
		{
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
		public PList()
		{
			type = Type.PList;
		}

		ArrayList<Content> contents = new ArrayList<>();

		public int compareToList(PList o)
		{
			int comp = 0;
			for (int i=0; i < contents.size(); i++)
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
			type = Type.PNumber;
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

