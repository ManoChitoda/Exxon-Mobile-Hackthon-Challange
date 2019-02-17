import java.io.*;
import java.util.*;

public class DataRetrival
{
	public static int i = 0;
	
	public static void main(String[] args) throws IOException
	{
		System.out.print("Enter file name: ");
		Scanner in = new Scanner(System.in);
		String fileName = in.nextLine();
		String names[] = new String[1000];
		Scanner read = new Scanner(new FileReader(fileName));
		names = getNames(read);
		
		String data[][] = new String[90][57];
		data = getData(read);
		
		for (int row = 0; row < 90; row++)
		{
			for (int col = 2; col < 57; col++) 
			{
				System.out.print(data[row][col] + "  ");
			}
			System.out.println();
		}
		
		
	}
	
	public static String[] getNames(Scanner read) throws IOException
	{	
		String str[] = new String [1000];
		String name = "";
		String line = read.nextLine();
		char c = line.charAt(0);
		int loc = 0;
		boolean isBraket = false;
		while(loc < line.length())
		{
			if(c == '[')
			{
				isBraket = true;
				c = line.charAt(loc++);
			}
			else if (c == ']')
			{
				isBraket = false;
				str[i++] = name;
				name = "";
			}
			if(isBraket)
			{
				name = name + c;
			}
			c = line.charAt(loc++);
		}
		
		return str;
	}
	
	public static String[][] getData(Scanner read)
	{
		int loc = 0; char c = ' ';
		String data[][] = new String[90][57];
		int row = 0, col = 0; String field = "";
		
		while(read.hasNextLine())
		{
			loc = 0; col = 0;
			String line = read.nextLine();
			
			while(loc < line.length())
			{
				c = line.charAt(loc++);
				if(c != ',' && loc != line.length())
				{
					field = field + c;
				}
				else
				{
					data[row][col++] = field;
					field = "";
				}
			}
			row++;							
		}
		return data;
	}
}