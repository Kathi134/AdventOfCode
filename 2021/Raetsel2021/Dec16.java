package Raetsel2021;

import java.io.IOException;
import java.util.ArrayList;

public class Dec16 extends Puzzle
{
	private int cursor; //aktueller index des nächsten zeichens, aktualisiert sich im hintergrund automatisch
	private long packetVersionSum;
	
	public Dec16() throws IOException
	{
		super(16);
		readInput("String");
		
		computeSolution(1);
		erg1 = packetVersionSum;
		//printSolution();
		
		printFormattedSolution("Packet Decoder", 
				"sum of all packetVersion-Numbers",
				"final value of the outermost packet");
	}
	
	public static long convertDecimal (String umzuwandeln)
	{
		long dezimaleZahl = 0;
		long system = 2;
				
		for(int i=0; i<umzuwandeln.length(); i++)
		{
			int tmp = -1;
			int exp = umzuwandeln.length()-i-1;
			tmp = Character.getNumericValue(umzuwandeln.charAt(i));		
			long dieserWert = tmp * potenz(system, exp);
			dezimaleZahl = dezimaleZahl + dieserWert;  
		}
		
		return dezimaleZahl;
	}
	
	public static long potenz(long basis, int exponent)
	{
		long erg = 1;
		for(int i=0; i<exponent; i++)
		{
			erg = erg * basis;
		}
		return erg;
	}
	
	public void convertBinary()
	{
		String binary = "";
		for(int i=0; i<inputString.length(); i++)
		{
			String b = "";
			switch (inputString.charAt(i))
			{
			case '0': b = "0000"; break;
			case '1': b = "0001"; break;
			case '2': b = "0010"; break;
			case '3': b = "0011"; break;
			case '4': b = "0100"; break;
			case '5': b = "0101"; break;
			case '6': b = "0110"; break;
			case '7': b = "0111"; break;
			case '8': b = "1000"; break;
			case '9': b = "1001"; break;
			case 'A': b = "1010"; break;
			case 'B': b = "1011"; break;
			case 'C': b = "1100"; break;
			case 'D': b = "1101"; break;
			case 'E': b = "1110"; break;
			case 'F': b = "1111"; break;
			}
			binary += b;
		}
		inputString = binary;
	}
	

	public void solve()
	{
		//System.err.println("HexDec: " + inputString);
		convertBinary();
		System.err.println("Binary: " + inputString);
		
		cursor = 0;
		packetVersionSum = 0;
		erg2 = identifyPacket();
	}
	
	public long identifyPacket()
	{
		int packetVersion = (int) readPartInt(3); //lies die ersten drei bits als dezimalzahl
		packetVersionSum += packetVersion; //erg für task 1

		int packetType = (int) readPartInt(3); //lies die zweiten drei bits als dezimalzahl
		switch (packetType)
		{
		case 4: return literalValue(); 
		case 0: return sum();
		case 1: return product();
		case 2: return minimum();
		case 3: return maximum();
		case 5: return greaterThan();
		case 6: return lessThan();
		case 7: return equalTo();
		}
		return -1; 
	}
	
	public long equalTo()
	{
		ArrayList<String> subpacketValues = operator();
		long firstValue = Long.parseLong(subpacketValues.get(0));
		long secondValue = Long.parseLong(subpacketValues.get(1));
		
		if(firstValue==secondValue) return 1;
		return 0;
	}
	
	public long lessThan()
	{
		ArrayList<String> subpacketValues = operator();
		long firstValue = Long.parseLong(subpacketValues.get(0));
		long secondValue = Long.parseLong(subpacketValues.get(1));
		
		if(firstValue<secondValue) return 1;
		return 0;
	}
	
	public long greaterThan()
	{
		ArrayList<String> subpacketValues = operator();
		long firstValue = Long.parseLong(subpacketValues.get(0));
		long secondValue = Long.parseLong(subpacketValues.get(1));
		
		if(firstValue>secondValue) return 1;
		return 0;
	}
	
	public long maximum()
	{
		ArrayList<String> subpacketValues = operator();
		long value = Long.parseLong(subpacketValues.get(0));
		
		for(int i=0; i<subpacketValues.size(); i++)
		{
			long thisValue = Long.parseLong(subpacketValues.get(i));
			if(thisValue > value)
			{
				value = thisValue;
			}
		}
		return value;
	}
	
	public long minimum()
	{
		ArrayList<String> subpacketValues = operator();
		long value = Long.parseLong(subpacketValues.get(0));
		
		for(int i=1; i<subpacketValues.size(); i++)
		{
			long thisValue = Long.parseLong(subpacketValues.get(i));
			if(thisValue < value)
			{
				value = thisValue;
			}
		}
		return value;
	}
	
	public long product()
	{
		ArrayList<String> subpacketValues = operator();
		long value = 1;
		for(int i=0; i<subpacketValues.size(); i++)
		{
			String valueString = subpacketValues.get(i);
			value *= Long.parseLong(valueString);
		}
		return value;
	}
	
	public long sum()
	{
		ArrayList<String> subpacketValues = operator();
		long value = 0;
		for(int i=0; i<subpacketValues.size(); i++)
		{
			String valueString = subpacketValues.get(i);
			value += Long.parseLong(valueString);
		}
		return value;
	}
	
	public ArrayList<String> operator()
	{
		ArrayList<String> thisOperatorsSubpackets = new ArrayList<String>();

		int lengthType = (int) readPartInt(1);
		if(lengthType == 0) 
		{
			int bitNumberSubpacketString = (int) readPartInt(15); //gibt dezimale anzahl der bits, die subpackets repräsentieren
			int endCursor = cursor + bitNumberSubpacketString; //merke dir das ende
			
			while(cursor < endCursor) //solange das ende der subpacket-bits nicht erreicht
			{
				String thisSubpacketValue = identifyPacket() + "";
				thisOperatorsSubpackets.add(thisSubpacketValue);
			}
		}
		else if(lengthType == 1)
		{
			int numberSubpackets = (int) readPartInt(11); //gibt die dezimale Anzahl subpackete
			for(int p=0; p<numberSubpackets; p++)
			{
				String thisSubpacketValue = identifyPacket() + "";
				thisOperatorsSubpackets.add(thisSubpacketValue);
			}
		}
		
		return thisOperatorsSubpackets;
	}
	
	public long literalValue()
	{
		int identifierLast = (int) readPartInt(1); //erstes bit -> identifikator fürs letzte
		String actualBinaryNumber = ""; //speichert die entstehenden bytes
		
		while(identifierLast == 1) //solange es nicht das letzte ist
		{			
			actualBinaryNumber += readPart(4); //füge das byte ran
			identifierLast = (int) readPartInt(1); //nächstes 1. bit s
		}
		actualBinaryNumber += readPart(4); 
		
		return convertDecimal(actualBinaryNumber); //gib den literal Value zurück
	}
	
	
	public String readPart(int length)
	{
		String s = inputString.substring(cursor, cursor+length); //ausgelesener wert vom aktuellen cursorstand bis übergebenem ende
		cursor += length; //cursor aktualisieren
		return s;
	}

	public long readPartInt(int length)
	{
		return convertDecimal(readPart(length));
	}
	
	
	public static void main(String[] args) throws IOException
	{
		new Dec16();
	}

	public void solveTask2()
	{
		solve();
	}
	
	public void solveTask1()
	{	
		solve();
	}
	
}
