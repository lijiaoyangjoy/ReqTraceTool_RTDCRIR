package org.soulsight.preprocess;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class EvalStater {
	
	public static void stat(String evalDir, String evalCountFile) throws IOException
	{
		BufferedWriter writer = new BufferedWriter(new FileWriter(evalCountFile));
		
		File dir = new File(evalDir);
		for(String filename : dir.list())
		{
			String line;
			int count = 0;
			BufferedReader reader = new BufferedReader(new FileReader(evalDir + filename));
			while((line = reader.readLine()) != null && line.trim().length() != 0)
			{
				count++;
			}
			reader.close();
			
			writer.write(filename + "\t" + count + "\n");
		}
		
		writer.close();
	}
	
	public static void main(String[] args) throws IOException
	{
		String input = "./data/eval_filter/";
		String output = "./data/text/eval_filter.stat";
		stat(input, output);
	}

}
