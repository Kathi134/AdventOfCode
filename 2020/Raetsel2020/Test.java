package Raetsel2020;

public class Test 
{
	public static void main(String[] args) 
	{
		//testHasOnlyRequired();
		testStringDivide();
		//testNegativeIntegerParse();
	}
	
	public static void testHasOnlyRequired()
	{
		String str = "123f9p";
		
		System.out.println(str +": "  + hasOnlyRequired(str));
		str = "h";
		System.out.println(str +": "  + hasOnlyRequired(str));
		str = "123abc";
		System.out.println(str +": "  + hasOnlyRequired(str));
		str = "´##";
		System.out.println(str +": "  + hasOnlyRequired(str));
	}
	
	public static void testStringDivide()
	{
		String line = "light red bags contain 1 bright white bag, 2 muted yellow bags.";
		int divisor = line.indexOf("contain"); //index von c
		String name = line.substring(0, divisor);
		String contain = line.substring(divisor+8);
		System.out.println("name: " + name + "\ncontain: " + contain);
		
		String test = "dark olive - shiny gold - vibrant plum";
		String[] erg = test.split(" - ");
		System.err.println(erg[0] +"\t"+erg[1]+"\t"+erg[2]+"\tLänge:"+erg.length) ;
	}
	
	public static boolean hasOnlyRequired(String s)
	{
		
		for (int i=0; i<s.length(); i++)
		{
			boolean valid = false;
			char c = s.charAt(i);
			if(Character.isDigit(c)|| c=='a' || c=='b' || c=='c' || c=='d' || c=='e' || c=='f')
			{
				valid = true;
			}
			else
			{
				return valid;
			}
		}
		
		return true;
		
	}

	public static void testNegativeIntegerParse()
	{
		String s = "" + "-1";
		int i = Integer.parseInt(s);
		System.out.println(i);
	}
	
}
