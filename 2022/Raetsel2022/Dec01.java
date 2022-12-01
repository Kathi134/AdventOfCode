package Raetsel2022;

import java.util.ArrayList;
import java.util.Collections;

import Base.InputKind;

public class Dec01 extends Puzzle2022
{
	ArrayList<Integer> cals = new ArrayList<>();
	
	public Dec01()
	{
		super(1);
		readInput(InputKind.seperatedIntTable);
		
		computeSolution(1);
		computeSolution(2);

//		printSolution();
		printFormattedSolution("Calorie Counting", "most Calories carried", "three most calories");
	}

	
	@Override
	public void solveTask1()
	{
		for(int i=0; i<inputIntTable.length; i++)
		{
			int sum = 0;
			for(int j=0; j<inputIntTable[i].length; j++)
			{
				sum += inputIntTable[i][j];
			}
			cals.add(sum);
		}
		Collections.sort(cals);
		
		erg1 = cals.get(cals.size()-1);
	}

	
	@Override
	public void solveTask2()
	{
		erg2 = cals.get(cals.size()-1) + cals.get(cals.size()-2) + cals.get(cals.size()-3);
	}
	
	
	public static void main(String[] args)
	{
		new Dec01();
	}
}

//int[][] data;
//
//public Dec01()
//{
//	super(1);
//	readInput(InputKind.seperatedStringList);
//	
//	computeSolution(1);
//	computeSolution(2);
//
//	printSolution();
//}
//
//public void read()
//{
//	data = new int[inputStringList.length][];
//	
//	for(int i=0; i<data.length; i++)
//	{
//		data[i] = InputReader.splitToIntegerArr(inputStringList[i], " ");
//	}
//}
//
//@Override
//public void solveTask1()
//{
//	read();
//	System.out.println(Arrays.deepToString(data));
//	int maX = Integer.MIN_VALUE;
//	int max2 = Integer.MIN_VALUE;
//	int max3 = Integer.MIN_VALUE;
//	
//	int[] skipId = new int[] {-1,-1,1};
//	int c=0;
//	
//	for(int i=0; i<data.length; i++)
//	{
//		int l = compute(i);
//		if(maX<l) {
//			maX = l;
//			skipId[c] = i;
//		}
//	}
//	c++;
//	
//	for(int i=0; i<data.length; i++)
//	{
//		if(i == skipId[0])
//		{
//			continue;
//		}
//		int l = compute(i);
//		if(max2<l) {
//			max2 = l;
//			skipId[c] = i;
//		}
//	}
//	c++;
//	for(int i=0; i<data.length; i++)
//	{
//		if(i == skipId[0] || i == skipId[1])
//		{
//			continue;
//		}
//		int l = compute(i);
//		if(max3<l) {
//			max3 = l;
//			skipId[c] = i;
//		}
//	}
//	
//	erg1 = maX+max2+max3;
//}
//
//public int compute(int arg)
//{
//	int sum = 0;
//	for(int j=0; j<data[arg].length; j++)
//	{
//		sum += data[arg][j];
//	}
//	return sum;
//}
