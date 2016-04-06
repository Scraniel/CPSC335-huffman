package cpsc335a4;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
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
		PrintWriter writer = null;
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

		System.out.println("Compressing string...");
		HashMap<Character, String> encodings = tree.compress(content);
		System.out.println("Writing to file '" + outputFilename +"'...");
		
		try {
			writer = new PrintWriter(outputFilename, "UTF-8");
			
			for(char c : encodings.keySet())
			{
				writer.print("Character: ");
				if(c == '\n')
					writer.println("\\n");
				else if(c == ' ')
					writer.println("Space");
				else if(c == '\r')
					writer.println("\\r");
				else
					writer.println(c);
				writer.flush();
					
				
				writer.println("ASCII Value: " + (int)c);
				writer.flush();
				writer.println("Frequency: " + tree.occurances.get(c));
				writer.flush();
				writer.println("Encoded Bit Representation: " + encodings.get(c) + "\n");
				writer.flush();
			}
			
			writer.println("Encoded message:");
			StringBuilder encodedMessage = new StringBuilder();
			
			for(int i = 0; i < content.length(); i++)
			{
				String current = encodings.get(content.charAt(i));
				encodedMessage.append(current);
				writer.append(current);
				writer.flush();
			}
			float oldFileSize = content.getBytes(StandardCharsets.UTF_8).length;
			float newFileSize = (float)encodedMessage.toString().length()/8.f;
			
			System.out.println("Successfully compressed. See '" + outputFilename + "'.");
			System.out.printf("\nOriginal file size (UTF-8): %1.1f bytes\n", oldFileSize);
			System.out.printf("New file size (Huffman): %1.1f bytes\n", newFileSize);
			System.out.printf("Compression: %1.1f%%", ((oldFileSize - newFileSize)/oldFileSize) * 100);
			
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally{
			if(writer != null)
			{
				writer.flush();
				writer.close();
			}
		}
		
	}

}