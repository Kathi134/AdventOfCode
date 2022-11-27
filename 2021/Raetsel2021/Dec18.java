package Raetsel2021;

import java.io.IOException;
import java.util.ArrayList;

public class Dec18 extends Puzzle
{
	String ergStr; 
	String line;
	ArrayList<String> input = new ArrayList<String>();
	
	String summarized = "";
	int finalMagnitude = 0;
	
	int highestMagnitude = Integer.MIN_VALUE;
	String wayToHighest = "";
	
	public void read()
	{
		for(int i=0; i<inputStringList.length; i++)
		{
			input.add(inputStringList[i]);
		}
	}
	
	
	public Dec18() throws IOException
	{
		super(18);
		readInput("StringList");
		read();
		
		computeSolution(1);
		computeSolution(2);
		erg2 = highestMagnitude;
		//printSolution();
		
		printFormattedSolution("Snailfish", 
				ergStr + " with magnitude",
				wayToHighest + " with highest magnitude");
	}
	
	
	public void solveTask1()
	{
		while(input.size()>1)
		{
			doCycle(0, 1);
		}
		ergStr = input.get(0);
		computeFinalAnswer();
		erg1 = finalMagnitude;
	}
	
	public void computeFinalAnswer()
	{
		/* finde "echte paare", also [x,y]
		 * 		-> schaue nach jedem '[' ob <digit> ',' <digit> ']'
		 * ersetz echte paare mit ihrem wert
		 * 		-> 3*x + 2*y
		 */
		summarized = ergStr; 
		
		ArrayList<String> realPairs = findRealPairs();
		while(realPairs.size()!=0)
		{			
			for(int i=0; i<realPairs.size(); i++)
			{
				String currPair = realPairs.get(i);
			//	int leftNumber = Character.getNumericValue(currPair.charAt(1));
				int leftNumber = Integer.parseInt(currPair.substring(1, currPair.indexOf(",")));
				int rightNumber = Integer.parseInt(currPair.substring(currPair.indexOf(",")+1, currPair.length()-1));
				int currMagnitude = 3*leftNumber + 2*rightNumber;
				summarized = summarized.replace(currPair, currMagnitude+"");
			}
			realPairs = findRealPairs();
		}
		
		String removeBrackets = summarized.replace("[", "").replace("]", "");
		finalMagnitude = Integer.parseInt(removeBrackets);
	}
	
	public ArrayList<String> findRealPairs()
	{
		ArrayList<String> pairs = new ArrayList<String>();
		
		for(int cursor=0; cursor<summarized.length(); cursor++)
		{
			String currPair = "";
			if(summarized.charAt(cursor)=='[')
			{
				currPair += summarized.charAt(cursor);
				cursor++;
				while(Character.isDigit(summarized.charAt(cursor)))
				{
					currPair += summarized.charAt(cursor);
					cursor++;
				}
				if(summarized.charAt(cursor)==',')
				{
					currPair += summarized.charAt(cursor);
					cursor++;
					while(Character.isDigit(summarized.charAt(cursor)))
					{
						currPair += summarized.charAt(cursor);
						cursor++;
					}
					if(summarized.charAt(cursor)==']')
					{
						currPair += summarized.charAt(cursor);
						pairs.add(currPair);
					}
				}
				cursor--;
			}

		}
		
		return pairs;
	}
	
	public void doCycle(int line1, int line2)
	{
		//add first two lines together
		//repeat the rules priorisized explode > split
		line = add(input.get(line1), input.get(line2));
		
		//line = inputStringList[indexSummand1]; //zum debuggen
		
		while(explodable() || splittable())
		{
			if(explodable())
			{
				explode();
				//System.err.println(line);
				continue;
			}
			
			if (splittable())
			{
				split();
				//System.err.println(line);
			} 
		}
		
		//System.err.println(line);
		input.remove(0);
		input.remove(0);
		input.add(0, line);
	}
	
	public boolean splittable()
	{
		for(int i=0; i<line.length(); i++)
		{
			if(Character.isDigit(line.charAt(i)))
			{
				if(Character.isDigit(line.charAt(i+1)))
				{
					return true;
				}
			}
		}
		return false; 
	}
	
	public boolean explodable()
	{
		Stack s = new Stack(5);
		for(int i=0; i<line.length(); i++)
		{
			if(s.isFull())
			{
				return true;
			}
			
			if(line.charAt(i)=='[')
			{
				s.push(line.charAt(i));
			}
			else if(line.charAt(i)==']')
			{
				s.pull();
			}
		}
		return false;
	}
	
	public String add(String firstLine, String secondLine)
	{
		String addedLine = "[" + firstLine + "," + secondLine + "]";
		//System.err.println(addedLine);
		return addedLine;
	}
	
	public boolean explode()
	{
		Stack s = new Stack(5);
		int breakPoint = -1;
		String affectedPair = ""; //position der 5. klammer auf '['
		for(int i=0; i<line.length(); i++)
		{
			if(s.isFull())
			{
				breakPoint = i-1;
				
				int cnt = breakPoint;
				while(line.charAt(cnt)!=']')
				{
					affectedPair += line.charAt(cnt);
					cnt++;
				}
				affectedPair += ']';
				
				break;
			}
			
			if(line.charAt(i)=='[')
			{
				s.push(line.charAt(i));
			}
			else if(line.charAt(i)==']')
			{
				s.pull();
			}
		}
		
		if(!s.isFull())
		{
			return false;
		}
		
		//links addieren, rechts addieren, auf 0 setzen
		String tmp = affectedPair.replace("[", "").replace("]", "");
		int[] numbersOfPair = splitToIntegerArr(tmp, ",");
		
		int leftNIndex = getNeighbour(line, "left", breakPoint);
		if(leftNIndex != -1)
		{
			String nStr = line.charAt(leftNIndex) + "";
			int tmpIndex = leftNIndex;
			while(Character.isDigit(line.charAt(tmpIndex+1)))
			{
				nStr += line.charAt(tmpIndex+1);
				tmpIndex++;
			}
			int nInt = Integer.parseInt(nStr);
			nInt += numbersOfPair[0];
			String newNumber = nInt + "";
			
			breakPoint += newNumber.length() - nStr.length();
			line = replaceAt(line, leftNIndex, nStr, newNumber);
		}
		
		int rightNIndex = getNeighbour(line, "right", breakPoint+affectedPair.length());
		if(rightNIndex != -1)
		{
			String nStr = line.charAt(rightNIndex) + "";
			int tmpIndex = rightNIndex;
			while(Character.isDigit(line.charAt(tmpIndex+1)))
			{
				nStr += line.charAt(tmpIndex+1);
				tmpIndex++;
			}
			int nInt = Integer.parseInt(nStr);
			nInt += numbersOfPair[1];
			String newNumber = nInt + "";
			
			line = replaceAt(line, rightNIndex, nStr, newNumber);
		}
		
		line = replaceAt(line, breakPoint, affectedPair, "0");
		return true;
	}
	
	public String replaceAt(String s, int index, String oldString, String replaceWith)
	{
		String part1 = s.substring(0, index);
		String part2 = s.substring(index+oldString.length());
		String replaced = part1 + replaceWith + part2;
		return replaced;
	}
	
	public int getNeighbour(String s, String direction, int index)
	{
		if(direction.equals("left"))
		{
			//breakpoint ist index der fünften klammer also abzug von ',['
			for(int cursor = index-2; cursor>=0; cursor--)
			{
				if(!Character.isDigit(s.charAt(cursor)))
				{
					continue;
				}
				
				while(Character.isDigit(s.charAt(cursor)))
				{
					cursor--;
				}
				return cursor+1;
			}
		}
		else if(direction.equals("right"))
		{
			//breakPoint + affected.length gibt index des zeichens nach ']'
			for(int cursor = index; cursor<s.length(); cursor++)
			{
				if(!Character.isDigit(s.charAt(cursor)))
				{
					continue;
				}
				
				return cursor;
			}
		}
		return -1;
	}
	
	public boolean split()
	{
		for(int i=0; i<line.length(); i++)
		{
			if(Character.isDigit(line.charAt(i)))
			{
				if(Character.isDigit(line.charAt(i+1)))
				{
					String splitNumberStr = line.charAt(i) + "" + line.charAt(i+1);
					int splitNumber = Integer.parseInt(splitNumberStr);
					int leftSplit = splitNumber / 2;
					int rightSplit = (splitNumber / 2) + (splitNumber % 2);
					
					String split = "[" + leftSplit + "," + rightSplit + "]";
					
					line = line.replaceFirst(splitNumberStr, split);
					return true;
				}
			}
		}
		return false;
	}
		
	
	public void solveTask2()
	{
		//für jede zeile addiere alle anderen zeilen ein mal
		//wenn neuer rekord -> speichern
		//string list enthält alle summanden unverändert
		for(int i=0; i<inputStringList.length; i++)
		{			
			for(int j=0; j<inputStringList.length; j++)
			{
				input.clear();
				input.add(inputStringList[j]);
				input.add(inputStringList[i]);
				
				doCycle(0, 1);
				ergStr = input.get(0);
				computeFinalAnswer();

				if(finalMagnitude>highestMagnitude)
				{
					wayToHighest = ergStr;
					highestMagnitude = finalMagnitude;
				}
			}
		}
	}
	
	public static void main(String[] args) throws IOException
	{
		new Dec18();
	}
}