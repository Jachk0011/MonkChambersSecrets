import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;

/***********************************************************************************
* 
*WARNING: ALL THE METHODS IS BASED IN THE POSITION OF THE QUEUE NOT IN THE INDEX
* 
***********************************************************************************/

public class Queue {
	private Node head = null;
		
	public Node getHead()
	{
		return this.head;
	}
	
	public Node getTail()
	{
		Node temp = this.getHead();
		while(temp.next != null)
			temp = temp.next;
		
		return temp;
	}
	
	// Tell us if the Queue is empty
	public boolean emptyQueue(){
		return (this.head!=null) ? false : true;
	}
	
	// return the size of he Queue as a integer
	public int sizeQueue()
	{
		int size = 1;
		if(emptyQueue())
			return 0;
		else
		{
			Node temp = this.getHead();
			while(temp.next != null)
			{
				temp = temp.next;
				size++;
			}				
			return size;
		}
	}
	
	// Let print all nodes in the Queue using Buffered
	public void printQueue() throws IOException
	{
		if(this.emptyQueue())
		{
			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));	
			bw.write("The list is empty");
			bw.flush();		
		}			
		else
		{
			Node temp = head;
			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
			
			while(temp != null)
			{
				bw.write(temp.toString());
				temp = temp.next;
			} 
			bw.flush();		
		}
		
	}

	//return a new node with the index and data of the maximum node
	public Node getMaxValue()
	{
		int max = Integer.MIN_VALUE;
		int index = -1, id = 0;
		Node tmp = this.getHead();
		while(tmp.next != null)
		{
			if(max < tmp.data)
			{
				max = tmp.data;
				index = tmp.index;
				id = tmp.id;
			}
				
			tmp = tmp.next;
		}
		
		if(max >= this.getTail().data)
			return new Node(id, index, max);
		else
			return this.getTail();
	}
	
	// add to Queue the new node get as parameter, according with FIFO 
	public void enqueue(Node n)
	{
		if(emptyQueue())
		{
			this.head = n;
			n.before = null;
		}
		else
		{
			Node tmp = this.getHead();
			while(tmp.next != null)		
				tmp = tmp.next;			
			tmp.next = n;
			n.before = tmp;
			
			//n.next = null;
		}				
	}

	//delete the head of Queue according with FIFO
	public Node dequeue(){
		if(this.emptyQueue())
			return null;
		else if(this.sizeQueue() >= 2)
		{
			Node tmp = this.getHead();
			Node second = this.getHead().next;
			second.before = null;
			this.head = second;			
			tmp.next = null;					
			return tmp;
		}
		else
		{
			Node tmp = this.getHead();
			this.head = null;
			return tmp;
		}
		
		//System.gc();
	}

	
	public Node getNode(int index)
	{
		if(index < this.sizeQueue())
		{
			Node temp = this.getHead();
			for(int i=1; i<index; i++)//we should modified i=0 or i=1 depend of the requeriments
				temp = temp.next;
			
			return temp;
		}
		else
			return null;
	}
	
	//
	public void deleteIndex(int index) throws IOException
	{
		Node tmp = this.getHead();
		 
		while(tmp.next != null)
		{		
			if(tmp.index == index)
			{
				this.head = this.getHead().next;
				tmp.next = null;
			}
			else if(tmp.next.index != index)// here we stop before Node who has the index
				tmp = tmp.next; 
			else
			{
				//System.out.println("\nBEFORE delete maximun index INSIDE METHOD:");
				//this.printQueue();
				
				Node tmp2 = tmp.next;
				tmp.next = tmp.next.next;
				tmp2.next = null;
				
				//System.out.println("\nAFTER delete maximun index INSIDE METHOD:");
				//this.printQueue();		
				/*
				 Node aux1 = tmp.before, aux2 = tmp.next;					
				aux1.next = tmp.next;
				aux2.before = aux1;
				tmp.next = null;
				tmp.before = null;*/
				
			}							
		}		
	}
	
	public Node binarySearch(int value, int lowerBound, int upperBound)
	{
		int middleElement = (lowerBound+upperBound)/2;
		
		if(lowerBound == upperBound)
		{
			if(getNode(middleElement).data == value)
				return getNode(middleElement);
			else
				return null; //the element isn't in the list
		}
		else
		{
			if(getNode(middleElement).data == value)
				return getNode(middleElement);
			else
				if( value < getNode(middleElement).data)
					return binarySearch(value, lowerBound, middleElement);
				else
					return binarySearch(value, middleElement+1, upperBound);
		}	
	}
	
	/*//it's not working: QUICK SORT - Only work for some kind of arrays
	public Queue quickSort(Queue unsorted)
	{
		if(unsorted.sizeQueue() <= 1)
			return unsorted;
		else
		{
			Node pivot = unsorted.getHead();
			Queue lessSublist = new Queue(), greaterSublist = new Queue();
			
			Node tmp = pivot.next;
			
			while(tmp != null)
			{
				if(tmp.data < pivot.data)
					lessSublist.enqueue(tmp.clone());
				else
					greaterSublist.enqueue(tmp.clone());
				
				tmp = tmp.next;
			}
			lessSublist = quickSort(lessSublist);
			greaterSublist = quickSort(greaterSublist);
			
			pivot.next = greaterSublist.getHead();
			lessSublist.enqueue(pivot);
			
			return lessSublist;					
		}
	}
	*/
	
	
	/*//it's not working : buble sort - it's keeping in a infinite bucle 
	public Queue bubbleSort(Queue unsorted) throws IOException
	{
		Queue aux = new Queue();//auxiliary queue for save deleted nodes
		Node tmp, pivote = unsorted.getHead();		
		int n = 2;
		if(unsorted.sizeQueue() <= 1)
			return unsorted; //it's already sorted
		else
		{
			while(pivote.next != null)
			{
				if(unsorted.sizeQueue() <= 1)
					break;
				if(pivote.data > pivote.next.data)
				{
					Node t1 = unsorted.deleteAnyPosition(n); 
					aux.enqueue(t1);//add the deleted node to auxiliary Queue
					System.out.println(t1.data); //proving the elimination
				}
					
				else
					n++; // in case where the actual value been lower which next								
			}
			
			tmp = aux.getHead();			
			System.out.println("printing all values inside auxiliary Queue");
			aux.printQueue();
			System.out.println("printing all values inside real Queue");
			unsorted.printQueue();
			
			while(tmp.next != null)// for again put all values deleted to the original Queue
			{
				Node t2 = aux.dequeue();
				unsorted.push(t2);
				System.out.println("unsorted after add deleted value from aux: ");
				unsorted.printQueue();
				tmp = tmp.next;
				System.out.println("Value deleted from aux "+t2.data);
				System.out.println("next value " + tmp.data);
			}
				
			System.out.println("Starting the recursion");
			unsorted = unsorted.bubbleSort(unsorted);
			return unsorted;
		}
		
		
	}
	*/
	
	public Node deleteAnyPosition(int position)
	{
		Node temp = getHead(), garbage;
		for(int i=1; i<position-1; i++)
			temp = temp.next;
		garbage = temp.next;
		temp.next = temp.next.next;
		garbage.next = null;
		return garbage;		
	}
	
	
	
	public void push(Node n)
	{
		if(emptyQueue())
			this.head = n;
		else
		{
			n.next = this.getHead();
			head = n;			
		}
	}
	
	public Node pop()
	{
		Node tmp = this.getHead();
		this.head = head.next;
		tmp = null;
		System.gc();
		return tmp;		
	}
	
	//print if the data value exist or not  
	public void existDataNodeQueue(int data) throws IOException
	{
		if(this.emptyQueue())
			System.out.println("The Queue is empty");
		else
		{
			Node temp = head;
			int position = 1;
			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
			while(temp.data != data && temp.next != null)
			{
				temp = temp.next;
				position++;
			}			
			if(temp.data == data)	
			{
				bw.write("The value " +"\"" +data+ "\"" + " exist and it's in the position: "+position + "\n");
				bw.flush();
				//bw.close();				
			}				
			else
			{
				bw.write("The value doesn't exist!");
				bw.flush();
				//bw.close();				
			}				
		}		
	}

	//return -1 if the data value doesn't exist, otherwise, return the position of the value.
	public int searchDataNodeQueue(int data)
	{
		if(this.emptyQueue())
			return -1;
		else
		{
			Node temp = getHead();
			int position = 1;			
			while(temp.data != data && temp.next != null)
			{
				temp = temp.next;
				position++;
			}
			
			if(temp.data == data)
				return position;			
			else 
				return -1;				
		}
	}
}
