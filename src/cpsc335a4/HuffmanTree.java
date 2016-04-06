package cpsc335a4;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.PriorityQueue;

public class HuffmanTree 
{
	private PriorityQueue<HuffmanNode> trees;
	private ArrayList<String> pathToLeaves;
	public HashMap<Character, Integer> occurances;
	public int numNodes = 0;
	public int numCharNodes = 0;
	
	public HuffmanTree()
	{
		trees = new PriorityQueue<>();
		pathToLeaves = new ArrayList<String>();
	}
	
	// Creates a new huffman tree using the inputted string
	// NOTE: deletes the old tree if one exists.
	public HashMap<Character, String> compress(String toCompress)
	{
		trees.clear();
		occurances = new HashMap<>();
		numNodes = 0;
		numCharNodes = 0; 
		
		// Getting character counts
		for(int i = 0; i < toCompress.length(); i++)
		{
			char current = toCompress.charAt(i);
			
			if(occurances.containsKey(current))
				occurances.put(current, occurances.get(current) + 1);
			else
				occurances.put(current, 1);
		}
		
		// Creating initial nodes
		for(char c : occurances.keySet())
		{
			trees.add(new HuffmanNode(c, occurances.get(c)));
			numNodes++;
			numCharNodes++;
		}
		
		buildTree();		
		return findEncodings();
	}
	
	// After calling buildTree, the trees heap will contain 1 value, the head
	// of the huffman tree.
	private void buildTree()
	{
		while(trees.size() > 1)
		{
			HuffmanNode firstMin = trees.remove();
			HuffmanNode secondMin = trees.remove();
			
			trees.add(new HuffmanNode(firstMin, secondMin));
			numNodes++;
		}
	}
	
	// Should only ever be called after calling buildTree
	private HashMap<Character, String> findEncodings()
	{
		HashMap<Character, String> encodings = new HashMap<>();
		
		HuffmanNode head = trees.peek();
		getLeaves(head, "");
		
		for(String s : pathToLeaves)
		{
			char currentChar = s.charAt(s.length() - 1);
			String encoding = s.substring(0, s.length() - 1);
			encodings.put(currentChar, encoding);
		}
		
		return encodings;
	}
	
	private void getLeaves(HuffmanNode toSearch, String currentPath)
	{
		if(toSearch == null)
			return;
		if(toSearch.value != null)
		{
			pathToLeaves.add(currentPath + toSearch.value);
			return;
		}
		else
		{
			getLeaves(toSearch.left, currentPath + '1');
			getLeaves(toSearch.right, currentPath + '0');
		}
			
	}
	
	public int height()
	{
		return height(trees.peek());
	}
	private int height(HuffmanNode node)
	{
	    if (node == null)
	    {
	        return 0;
	    }
	    else
	    {
	        return 1 + Math.max(height(node.left), height(node.right));
	    }
	}
}
