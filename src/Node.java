
public class Node {
	public int id;
	public int index;
	public int data;
	public Node next;
	public Node before;
	
	//CONSTRUCTORS 
	public Node (){}
	
	public Node (int id, int index, int data)
	{
		this.id = id;
		this.data = data;
		this.index = index;
	}
	
	// NICE VIEW DATA
	public String toString()
	{
		return " " + this.index + " ";
	}
	
	public Node clone() 
	{
		Node temp = new Node(this.id,this.index, this.data);
		return temp;
	}
	
	
}
