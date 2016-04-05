package cpsc335a4;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Scanner;

public class a4 {

	public static void main(String[] args) {
		if(args.length != 2)
		{
			System.err.println("Usage: a4 <input_file> <output_file>");
			return;
		}
		
		String inputFilename = args[0];
		String outputFilename = args[1];
		HuffmanTree tree = new HuffmanTree();
		String content;
		
		// Read in input
		try {
			Scanner s = new Scanner(new File(inputFilename));
			s.useDelimiter("\\Z");
			content = s.next();
			
			s.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return;
		}
		
		
		HashMap<Character, String> encodings = tree.compress("Hello!");
		
		PrintWriter writer;
		try {
			writer = new PrintWriter(outputFilename, "UTF-8");
			
			for(char c : encodings.keySet())
			{
				writer.print("Character: ");
				if(c == '\n')
					writer.println("\\n");
				else if(c == ' ')
					writer.println("Space");
				else
					writer.println(c);
					
				
				writer.println("ASCII Value: " + (int)c);
				writer.println("Frequency: " + tree.occurances.get(c));
				writer.println("Encoded Bit Representation: " + encodings.get(c) + "\n");
			}
			
			writer.println("Encoded message:");
			
			for(int i = 0; i < "Hello!".length(); i++)
			{
				writer.print(encodings.get(content.charAt(i)));
			}
			
			writer.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		
		
		

	}

}
