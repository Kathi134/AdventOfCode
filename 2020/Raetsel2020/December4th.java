package Raetsel2020;

import java.io.File;
import java.io.IOException;

public class December4th extends Puzzle
{
	private static int validKeys = 0;
	private static int validKeys1 = 0;
	
	public static void main(String[] args) throws IOException
	{
		f = new File("src\\Raetsel2020\\inputs\\December4th_Input"); 
		prepare();
		readInput("seperatedStringList");
				
		solve();		
		System.out.println("valid Keys in task1: "+validKeys1);
		System.out.println("valid Keys in task2: "+validKeys);
	}
	
	public static void solve()
	{
		for (int i=0; i<inputStringList.length; i++) //für jeden Datensatz - PassPort
		{
			String[] singleStrings = inputStringList[i].split(" ");
			isValid(singleStrings);
		}
	}
	
	public static void isValid(String[] singleStrings)
	{
		if(singleStrings.length == 8) //wenn es 8 einträge gibt -> valid
		{
			validKeys1++; //for task 1
			isValidTask2(singleStrings);
		}
		else if(singleStrings.length == 7) //wenn es 7 einträge gibt und cid fehlt -> valid
		{
			boolean flagCid = false;
			for(int j=0; j<singleStrings.length; j++)
			{
				if (singleStrings[j].contains("cid"))
				{
					flagCid = true;
				}
			}
			
			if(!flagCid) 
			{
				validKeys1++; //for task 1
				isValidTask2(singleStrings);
			}
		}
	}
	
	public static void isValidTask2(String[] passport)
	{
		boolean valid = true;
		for(int i=0; i<passport.length; i++)
		{
			String category = passport[i].substring(0,3);
			String value = passport[i].substring(4);
			
			boolean validCategory = checkCategory(category, value);
			if(!validCategory)
			{
				valid = false;
			}
		}
		if(valid)
		{
			validKeys++;
		}
	}
	
	public static boolean checkCategory(String c, String v)
	{
		boolean valid = false;
		
		switch(c)
		{
		case "byr": int vInt = Integer.parseInt(v);
					if(v.length() == 4 && (vInt>=1920 && vInt<=2002)) 
					{
						valid = true;
					}
					break;
		case "iyr": int vInti = Integer.parseInt(v);
					if(v.length() == 4 && (vInti>=2010 && vInti<=2020)) 
					{
						valid = true;
					}
					break;
		case "eyr": int vInte = Integer.parseInt(v);
					if(v.length() == 4 && (vInte>=2020 && vInte<=2030)) 
					{
						valid = true;
					}
					break;
		case "hgt": int vMeasure = Integer.parseInt(v.substring(0, v.length()-2));
					String vUnit = v.substring(v.length()-2);
					if(vUnit.equals("cm") && (vMeasure>=150 && vMeasure<=193))
					{
						valid = true;
					}
					else if(vUnit.equals("in") && (vMeasure>=59 && vMeasure<=76))
					{
						valid = true;
					}
					break;
		case "hcl": if(v.startsWith("#") && hasOnlyRequired(v.substring(1)))
					{
						valid = true;
					}
					break;
		case "ecl": if(elementOf(v))
					{
						valid = true;
					}
					break;
		case "pid": if(v.length()==9)
					{
						valid = true;
					}
					break;
		case "cid": valid = true; 
					break;
		}
		return valid;
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
	
	public static boolean elementOf(String s)
	{
		if(s.equals("amb") || s.equals("blu") || s.equals("brn") || s.equals("gry") || s.equals("grn") || s.equals("hzl") || s.equals("oth"))
		{
			return true;
		}
		return false;
	}
	
}
