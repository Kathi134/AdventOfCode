package Raetsel2021;

public class Stack
{
	char[] stack;
	int top = 0;
	int openBrackets = 0;
	
	public Stack(int maxLength)
	{
		stack = new char[maxLength];
	}
	
	public void push(char c)
	{
		stack[top] = c;
		top++;
	}
	
	public char pull()
	{
		top--;
		return stack[top];
	}
	
	public boolean isEmpty()
	{
		return (top == 0);
	}
	
	public boolean isFull()
	{
		return (top == stack.length);
	}
}