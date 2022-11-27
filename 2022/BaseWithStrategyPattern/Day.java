package BaseWithStrategyPattern;

import java.io.File;
import java.io.IOException;

@SuppressWarnings("unused")
public class Day
{
	public final File f;
	protected DataBaseStrategy<?> data;
	
	public Day(int day) 
	{
		f = new File("2021\\Raetsel2021\\inputs\\Dec" + day);
	}
	
	
}
