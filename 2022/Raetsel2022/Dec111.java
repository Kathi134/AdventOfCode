package Raetsel2022;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import Base.InputKind;

public class Dec111 extends Puzzle2022
{
	public Dec111()
	{
		super(111);
		readInput(InputKind.StringList);

		computeSolution(1);
		computeSolution(2);

		printSolution();
	}

	List<Monkey> monkeys = new ArrayList<>();

	public void read()
	{
		int j = 0;
		for (int i = 0; i <= 3; i++)
		{
			String currLine = inputStringList[j++];
			int id = Integer.parseInt(currLine.replace(":", "").split(" ")[1]);

			currLine = inputStringList[j++];
			String[] tmp = currLine.replace(",", "").trim().split(" ");
			List<Long> items = new ArrayList<>();
			for (int t = 2; t < tmp.length; t++)
			{
				items.add(Long.parseLong(tmp[t]));
			}

			j++; // skip operation line

			currLine = inputStringList[j++];
			tmp = currLine.split(" ");
			int divisor = Integer.parseInt(tmp[tmp.length - 1]);

			currLine = inputStringList[j++];
			tmp = currLine.split(" ");
			int tmtmp = Integer.parseInt(tmp[tmp.length - 1]);

			currLine = inputStringList[j++];
			tmp = currLine.split(" ");
			int fmtmp = Integer.parseInt(tmp[tmp.length - 1]);

			monkeys.add(new Monkey(id, items, divisor, tmtmp, fmtmp));

			j++; // skip empty line
		}

		for (Monkey m : monkeys)
		{
			m.setMonkeys(monkeys.get(m.trueMonkeyTmp), monkeys.get(m.falseMonkeyTmp));
		}

	}

	@Override
	public void solveTask1()
	{
		read();

		for (int round = 1; round <= 10000; round++)
		{
			for (Monkey m : monkeys)
			{
				m.executeTurn();
			}
			if(round == 1 || round == 20 || round % 1000 == 0)
			{
				System.err.println("== After round " + round + " ==");
				System.err.println(monkeys);
			}
		}

		System.err.println(monkeys);
		List<Monkey> tmp = new ArrayList<>(monkeys);
		
		Collections.sort(tmp);

		Monkey mostBusy = tmp.get(tmp.size() - 1);
		Monkey scndBusy = tmp.get(tmp.size() - 2);
		erg1 = mostBusy.inspections * scndBusy.inspections;
		System.out.println( mostBusy.inspections + " * " + scndBusy.inspections);
	}

	@Override
	public void solveTask2()
	{

	}

	public static void main(String[] args)
	{
		new Dec111();
	}
	
	
	class Monkey implements Comparable<Monkey>
	{
		public int id;
//		public List<Long> actualItems;
		public int divisor;
		public int trueMonkeyTmp;
		public int falseMonkeyTmp;
		public Monkey trueMonkey;
		public Monkey falseMonkey;
		public long inspections = 0;
		public List<RestClasses> items; // modulo

		public Monkey(int id, List<Long> items, int divisor, int tmtmp, int fmtmp)
		{
			this.id = id;
			this.divisor = divisor;
			this.trueMonkeyTmp = tmtmp;
			this.falseMonkeyTmp = fmtmp;

			this.items = new ArrayList<>();
			for (long l : items)
			{
				this.items.add(new RestClasses(l));
			}
		}

		public void setMonkeys(Monkey trueMonkey, Monkey falseMonkey)
		{
			this.trueMonkey = trueMonkey;
			this.falseMonkey = falseMonkey;
		}

		public static void operation(int id, RestClasses currItem)
		{
			switch (id)
			{
				case 0:
//					newItem = currItem * 19;
					currItem.addFactor(19);
					break;
				case 1:
//					newItem = currItem + 6;
					currItem.addition(6);
					break;
				case 2:
//					newItem = currItem * currItem;
					break;
				case 3:
//					newItem = currItem + 3;
					currItem.addition(3);
					break;
			}
		}

		public void executeTurn()
		{
			while (!items.isEmpty())
			{
				RestClasses currItem = items.remove(0);
				inspections++;
				throwItem(currItem);
			}
		}

		public void throwItem(RestClasses item)
		{
			Monkey.operation(id, item);
			
			boolean test = item.isDividable(divisor);
			if (test)
				trueMonkey.catchItem(item);
			else
				falseMonkey.catchItem(item);
		}

		public void catchItem(RestClasses item)
		{
			items.add(item);
		}

		@Override
		public int compareTo(Monkey m)
		{
			long div = (inspections - m.inspections);
			return (int) div;
		}
		
		@Override
		public String toString()
		{
			return "Monkey " + id+ " inspected items " + inspections + " times.\n";
		}
	}
	

	class RestClasses
	{
		public final int[] keys = new int[] {2,3,5,7,11,13,17,19,23};
		public long[] rests = new long[9];
		
		public RestClasses(long l)
		{
			for(int i=0; i<keys.length; i++)
			{
				rests[i] = l%keys[i];
			}
		}
		
		public void addFactor(int prime)
		{
			for(int i=0; i<keys.length; i++)
			{
				if(keys[i] == prime)
				{
					rests[i] = 0L;
				}
			}
		}

		public void addition(int summand)
		{
			for(int i=0; i<rests.length; i++)
			{
				int mod = keys[i];
				rests[i] = (rests[i] + summand) % mod;
			}
		}
		
		public boolean isDividable(int d)
		{
			for(int i=0; i<keys.length; i++)
			{
				if(keys[i] == d)
				{
					return rests[i] == 0;
				}
			}
			return false;
		}
	}	
}


	
	
//	public PrimeFactor(long number)
//	{
//		for (int i=2; i<number; i++)
//		{
//			while (number%i == 0)
//			{
//				factors.add(i);
//				number = number/i;
//			}
//		}
//		if (number>2)
//		{
//			System.out.println(number);
//		}
//	}
//	
//	public void addFactor(int i)
//	{
//		factors.add(i);
//	}

