package BaseWithStrategyPattern;

import java.io.IOException;
import java.util.ArrayList;



public class DataSingleString extends DataBaseStrategy<String>
{
	public DataSingleString()
	{
		data = new ArrayList<String>();
	}
	
	@Override
	public void read() throws IOException
	{
		String in = br.readLine();
		data.add(in);
	}
	
}
