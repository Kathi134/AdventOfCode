package Raetsel2022;

import java.util.ArrayList;
import java.util.Arrays;

import Base.InputKind;

public class Dec20 extends Puzzle2022
{
	public Dec20()
	{
		super(20);
		readInput(InputKind.intList);

		computeSolution(1);
		computeSolution(2);

		printSolution();
	}

	@Override
	public void solveTask1()
	{
		ArrayList<Number> operations = new ArrayList<>();
		NumberStructure res = new NumberStructure();

		for (int i = 0; i < lines; i++)
		{
			Number n = new Number(inputIntList[i], i);
			operations.add(n);
			res.add(n);
		}

		for (int i=0; i<lines; i++)
		{
			Number numberToMove = operations.get(0);
			
			

//			int index = -1;
//			for (int j = 0; j < res.length; j++)
//			{
//				if (numberToMove == res[j])
//				{
//					index = j;
//					break;
//				}
//			}
//
//			int aim = index + numberToMove;
//			aim = aim > 0 ? aim % res.length : res.length - 1 + aim;
		}
	}

	@Override
	public void solveTask2()
	{

	}

	public static void main(String[] args)
	{
		new Dec20();
	}
	
	class NumberStructure
	{
		Number root;

		public void addEnd(Number n)
		{
			if(root != null)
			{	
				root.addEnd(n);
			}
			else
			{
				root = n;
			}
		}
		
		public Number numberAtIndex(int i)
		{
			if(root == null)
				return null;
			else 
				return root.numberAtIndex(i);
		}
		
		public void shift(boolean right, Number n)
		{
			if(root == null)
				root = n;
			else
			{
				root.shift(n);
			}
		}
		
		
		class Number
		{
			int n;
			int index;
			Number pre;
			Number post;

			public Number(int n, int index)
			{
				this.index = index;
				this.n = n;
			}

			public void addEnd(Number n)
			{
				if (post != root)
				{
					post.addEnd(n);
				}
				else // I am at the end
				{
					post = n;
					n.pre = this;
					n.post = root;
					root.pre = n;
				}
			}
			
			public Number numberAtIndex(int i)
			{
				if(this.index == i)
					return this;
				else 
				{
					if(i>lines/2)
						return post.numberAtIndex(n);
					else
						return pre.numberAtIndex(n);
				}
			}
			
			
			public void shift(Number n)
			{
				if(n == this)
				{
					if(n.n > 0) // go Right
					{
						Number newPre = goRight(n.n);
						
						// connect where i come from
						n.pre.post = n.post;
						n.post.pre = n.pre;
						
						// link in newly
						n.post = newPre.post;
						n.post.pre = n;
						n.pre = newPre;
						newPre.post = n;
					}
					else // go Left
					{
						Number newPost = goLeft(-n.n);
						
						// connect where i come from
						n.pre.post = n.post;
						n.post.pre = n.pre;
						
						// link in newly
						n.pre = newPost.pre;
						n.pre.post = n;
						n.post = newPost;
						newPost.pre = n;
					}
				}
				else
				{
					post.shift(n);
				}
			}
			
			public Number goRight(int times)
			{
				if(times == 0)
					return this;
				else
					return post.goRight(times-1);
			}
			
			public Number goLeft(int times)
			{
				if(times == 0)
					return this;
				else
					return pre.goRight(times-1);
			}
		}
	}
}

