package cpsc335a4;

public class HuffmanNode implements Comparable<HuffmanNode> {

	public HuffmanNode left, right;
	public int occurances;
	public Character value;	
	
	public HuffmanNode(HuffmanNode left, HuffmanNode right)
	{
		this.left = left;
		this.right = right;
		occurances = 0;
		value = null;
		
		if(left != null)
			occurances += left.occurances;
		if(right != null)
			occurances += right.occurances;
	}
	
	public HuffmanNode(Character value, int occurances)
	{
		left = null;
		right = null;
		this.value = value;
		this.occurances = occurances;
	}
	
	// So we can use in PriorityQueue
	@Override
	public int compareTo(HuffmanNode other) 
	{
		return Integer.compare(occurances, other.occurances);
	}
}
