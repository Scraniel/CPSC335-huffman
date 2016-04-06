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

		System.out.println("Compressing file '"+ inputFilename +"'...");
		double startTime = System.nanoTime();
		HashMap<Character, String> encodings = tree.compress(content);
		double endTime = System.nanoTime();
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
			String current;
			for(int i = 0; i < content.length(); i++)
			{
				current = encodings.get(content.charAt(i));
				encodedMessage.append(current);
				
			}
			float oldFileSize = content.getBytes(StandardCharsets.UTF_8).length;
			float newFileSize = (float)encodedMessage.toString().length()/8.f;
			
			writer.append(encodedMessage);
			writer.flush();
			
			System.out.println("Successfully compressed. See '" + outputFilename + "'.");
			
			System.out.println("\n******************* STATISTICS *******************");
			System.out.printf("Runtime: %1.1f ms\n", (endTime - startTime)/1000000);
			System.out.printf("Original file size (UTF-8): %1.1f bytes\n", oldFileSize);
			System.out.printf("New file size (Huffman): %1.1f bytes\n", newFileSize);
			System.out.printf("Compression rate: %1.1f%%\n", ((oldFileSize - newFileSize)/oldFileSize) * 100);
			System.out.printf("Height of generated Huffman Tree: %d\n", tree.height());
			System.out.printf("Number of character (leaf) nodes in generated Huffman Tree: %d\n", tree.numCharNodes);
			System.out.printf("Total number of  nodes in generated Huffman Tree: %d", tree.numNodes);
			
			
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