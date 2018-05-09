import java.io.IOException;

public class Spider
{
	static Queue result = new Queue();	
	
	public Queue fill(int[] arr)
	{
		Queue h = new Queue();		
		for(int i=0; i<arr.length; i++)
			h.enqueue(new Node(i, i+1, arr[i])); //fill the Queue with  data from array
		return h;
	}
	
	public Queue magic(Queue h, int n) throws IOException
	{
		Queue aux = new Queue();
		Node toDelete, headAux;		
		
		//System.out.println("INICIAL _h_");
		//h.printQueue();
		
		if(result.sizeQueue() != n)
		{
			if(n < h.sizeQueue())
			{				
				for(int i=0; i<n; i++) //get the n first elements in q
					aux.enqueue(h.dequeue());
				
				//System.out.println("\n_AUX_ primer sub array: creating since the IF");
				//aux.printQueue();
				
				toDelete = aux.getMaxValue();		//invoke getMaxValue() method to calculate the biggest
				result.enqueue(toDelete);			// save the biggest node in result Queue
				aux.deleteIndex(toDelete.index);	//deleting the node with max value inside Auxiliary Queue
				
				/*System.out.println("\n_RESULT_");
				result.printQueue();
				System.out.println("\n_AUX_ without BIGGEST sub array");
				aux.printQueue();*/
			}
			else
			{
				int m = h.sizeQueue();
				for(int i=0; i < m; i++)
					aux.enqueue(h.dequeue());		
				
				//System.out.println("\n_AUX_ primer sub array: creating since the ELSE");
				//aux.printQueue();
				
				toDelete = aux.getMaxValue();		//invoke getMaxValue() method to calculate the biggest
				result.enqueue(toDelete);			// save the biggest node in result Queue
				aux.deleteIndex(toDelete.index);	//deleting the node with max value inside Auxiliary Queue	
				
				/*System.out.println("\n_RESULT_");
				result.printQueue();
				System.out.println("\n_AUX_ without BIGGEST sub array");
				aux.printQueue();*/
			}
		
			headAux = aux.getHead();
			for(int i=0; i < aux.sizeQueue(); i++)			
			{
				if(headAux.data > 0)
					headAux.data--;//decrement all values in the aux Queue			
				headAux = headAux.next;
			}
			
			//System.out.println("\n_AUX_ decrementado");
			//aux.printQueue();
			
			//System.out.println("\n_H_ watching all queue before add the modificated nodes");
			//h.printQueue();
			
			int k = aux.sizeQueue();
			for(int i=0; i < k; i++)
				h.enqueue(aux.dequeue());//fill again main Queue
						
			
			//System.out.println("\n_h_ re inyectado");
			//h.printQueue();						
			return this.magic(h, n);
			
		}
		else
			return result;		
	}
	
	public void printSolution(Queue q, int n)
	{
		int[] solution = new int[n];
		
		for(int i=0; i < solution.length; i++)
		{
			Node tmp = q.dequeue();
			solution[i] = tmp.index;
		}
		
		//System.out.println("\nprinting only the array: ");
		for(int i=0; i < solution.length; i++)
			System.out.print(solution[i] + " ");

		//return solution;
	}
	
	public static void main(String[] args) throws IOException
	{
		Spider sp = new Spider();
		
		//test # 1: OK SAME RESULT
		/*int n1=6, s1 = 10;
		int[] arr = {3,1,5,3,3,5,5,3,4,3};
		//int[] output1 = {3,7,6,9,10,1};
		sp.magic(sp.fill(arr), n1);		
		sp.printSolution(result, n1);*/
		
		//test # 2: OK SAME RESULT
		/*int n2 = 4, s2 = 13;		
		int[] arr2 = {2,4,2,4,3,1,2,2,3,4,3,4,4};
		//int[] output2 = {2,5,10,13};
		sp.magic(sp.fill(arr2), n2);		
		sp.printSolution(result, n2);*/
		
		
		//test # 3: OK SAME RESULT		
		int n3 = 43, s3 = 1126;
		int[] arr3 = {37,40,32,15,1,2,27,13,21,3,42,25,35,15,12,24,11,21,34,22,37,39,42,19,37,7,30,33,10,10,17,39,6,5,10,42,42,36,3,19,30,1,36,21,7,4,37,17,16,27,30,9,22,28,28,7,35,14,32,36,23,5,23,21,2,24,11,35,9,13,10,38,5,2,16,11,41,9,19,13,27,6,22,40,25,41,3,8,3,26,1,25,23,15,37,16,39,4,7,4,16,16,41,20,10,5,22,42,13,41,11,31,38,24,27,19,21,22,27,23,39,19,40,18,33,33,33,20,29,39,23,36,4,13,13,5,17,26,3,22,23,5,1,17,29,28,28,41,41,11,21,36,21,9,11,2,33,35,22,18,23,36,11,18,5,15,22,14,40,16,27,12,20,27,20,5,3,4,38,43,6,15,36,26,15,38,28,4,21,41,14,43,33,24,9,30,30,30,35,18,37,10,29,14,36,41,10,39,1,4,30,42,10,14,16,24,43,35,20,13,32,33,12,14,13,13,35,34,34,26,8,28,35,29,33,19,26,34,6,18,30,28,16,39,41,32,12,33,23,23,2,4,12,5,17,16,17,8,6,8,25,13,27,8,41,16,18,15,41,16,33,27,43,5,15,32,28,26,21,8,5,22,11,8,19,19,23,35,18,28,34,42,32,17,41,22,24,15,36,22,30,17,5,21,22,19,10,6,36,30,5,32,1,7,39,19,25,18,2,42,37,36,32,18,9,29,31,33,36,15,11,14,32,7,35,2,26,1,7,10,22,4,42,22,10,29,32,35,39,34,33,24,26,22,41,26,42,20,7,34,35,9,40,15,16,31,16,33,23,14,42,36,17,32,15,27,18,38,18,13,28,42,36,2,12,26,28,11,2,26,36,28,35,32,42,42,11,6,31,25,20,21,18,36,10,24,11,19,18,20,31,3,19,15,39,22,32,15,32,34,41,17,10,24,40,1,22,8,6,1,24,25,13,41,10,14,21,12,32,31,32,11,25,42,18,20,20,6,35,1,31,32,9,41,4,5,41,25,4,38,17,28,12,21,17,13,35,38,24,15,25,12,18,6,2,27,17,22,32,8,14,12,31,22,9,34,18,41,7,22,27,15,6,38,36,14,7,19,8,23,33,24,34,42,21,28,25,30,6,6,37,11,17,17,24,17,7,41,14,6,19,32,20,16,27,4,30,25,22,29,4,4,2,30,37,14,14,19,43,11,24,37,21,32,2,1,5,8,41,10,5,9,41,25,24,16,28,10,41,42,31,36,37,32,22,30,2,35,5,2,37,20,30,14,8,31,6,12,30,4,13,35,4,11,16,27,26,35,29,15,25,16,8,18,4,29,5,5,13,1,41,6,13,27,12,12,14,17,16,1,12,28,27,15,38,42,34,13,25,19,27,42,34,34,16,29,12,12,25,16,5,23,13,17,41,24,28,4,33,43,39,36,20,22,43,14,12,33,18,28,8,2,26,33,27,34,10,30,2,34,2,6,13,15,14,3,30,34,6,11,25,1,39,1,22,38,7,33,27,24,17,26,25,43,7,9,33,16,38,26,6,32,24,11,38,29,13,16,19,18,27,1,18,14,1,31,8,42,20,26,23,28,8,4,27,6,4,8,21,34,26,18,22,41,28,8,26,32,23,2,6,41,37,15,11,29,2,10,28,21,35,7,41,34,2,24,39,6,24,8,31,41,26,9,38,2,8,20,34,22,13,31,20,6,3,22,35,4,24,11,17,15,17,14,41,18,29,28,23,1,28,2,41,2,10,35,3,9,4,28,31,16,16,42,14,18,12,5,13,35,15,21,42,23,26,39,40,12,15,12,4,42,13,2,43,15,28,38,15,23,22,2,39,29,35,1,3,4,5,8,30,11,20,28,25,3,15,21,14,22,24,9,20,29,2,12,35,22,6,6,1,19,8,31,5,34,31,42,37,27,41,24,37,18,43,18,20,7,31,25,28,3,33,4,31,35,7,22,13,4,28,5,23,27,36,19,9,23,17,3,7,15,18,35,32,9,2,43,15,24,24,42,26,13,38,6,39,1,19,43,40,38,5,19,21,40,29,22,19,2,24,17,8,33,1,31,41,2,30,5,25,10,3,42,15,32,4,10,25,23,10,21,17,14,31,30,2,16,8,12,9,23,29,9,4,29,39,36,22,26,40,3,27,35,36,41,15,40,43,39,11,9,8,19,14,30,40,7,37,4,18,38,18,3,3,13,23,41,6,1,15,37,38,42,20,31,39,35,19,38,22,21,38,30,39,43,8,36,6,2,31,24,39,6,18,33,18,33,30,15,33,2,9,20,35,20,42,30,11,9,17,33,29,11,11,24,3,10,16,8,11,39,31,41,1,41,30,10,30,9,17,11,2,17,22,36,36,20,14,4,28,30,28,13,33,30,29,35,39,36,34,42,31,14,31,31,11,10,33,32,18,41,42,11,14,13,38,6,32,8,1,9,30,28,13,11,6,41,2,37,34,35,35,21,40,14,1,7,23,25,38,32,22,29,34,35,41,28,32,21,36,33,29,14,9,34};
		//int[] output3 = {11,77,108,148,180,217,273,320,357,395,444,504,539,560,640,684,708,732,815,858,865,910,969,1006,1072,1083,23,37,86,149,192,245,292,343,371,396,434,488,549,601,645,673,724};
		sp.magic(sp.fill(arr3), n3);		
		sp.printSolution(result, n3);	
	}
}