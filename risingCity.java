import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

// Class risingCity
public class risingCity {
	public static Integer counter=0;
	public static String fileName = "" ;
	File file = new File(fileName); 
	public static String line = "" ;
	public static BufferedReader reader;
	public static List<Integer> li ;
	
	// Increase the global counter	
	public static void increaseGlobalTimer(List<Integer> li , minheap heap , RBT red_black)
	{
		counter++;
		if( line != null)
		{
			execute( li , heap , red_black) ;
		}
	}
	
	// Executes after getting an input from file
	public static void execute( List<Integer> li , minheap heap , RBT red_black)
	{
		int firstElement = li.get(0) ;
		if(firstElement != counter)
			return ;
		
		if(li.get(1)==1) {
			building b=new building(li.get(2),0,li.get(3));
			heap.insert(b);
			Node n=new Node(b);
			try {
				
				red_black.insertValue(n);
			} catch (MyException e) {
				e.printStackTrace();
			}
		}
		else if(li.get(1)==2) {
			red_black.printBuilding(li.get(2));
		}
		else {
			String s=red_black.printBuilding(red_black.nodeRoot,li.get(2), li.get(3));
			
			if(s.length() > 0)
			{
				System.out.println((s.substring(0, s.length() - 1)));
				
			}
			else
			{
				System.out.println("(0,0,0)");
			}
		}
		
		try {
			
			line = reader.readLine();
			if( line != null)
			{
				risingCity.li=funParse(line);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// main function
	public static void main(String[] args) throws MyException {
		
		PrintStream fileOut = null;
		try {
			// Output file to write the output into
			fileOut = new PrintStream("./output_file.txt");
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		System.setOut(fileOut);
		
		if( args.length == 0)
		{	
			// If no input file is given in the argument
			System.out.println("Argument required: input file");
			return ;
		}
		else
		{	
			// Take file name from the argument
			fileName = args[0] ;
		}

		try {
			// initializing new buffer reader
			reader = new BufferedReader(new FileReader(fileName));
			line = reader.readLine();
			li=funParse(line);

			// initializing new bed black tree
			RBT red_black=new RBT();
			
			// initializing new minheap 
			minheap heap = new minheap();

			// building
			building remember=new building(0,0,0);
			boolean flag=false;
			int track=0;
			
			execute( li , heap , red_black) ;
			while (line != null || heap.size != 0) {

					if(heap.size!=0) {

						// removing the top element from heap
						remember = heap.removetop();

						// incrementing the top by 5 			
						track += 5 ;

						// checking if track exceeds the (total time - executed time)
						if ( track > (remember.total_time-remember.executed_time) )
						{
							track = remember.total_time-remember.executed_time ;
						}
						
						// do this while track is greater than zero
						while( track > 0 )
						{
							remember.executed_time ++ ;
							increaseGlobalTimer( li , heap , red_black) ;
							track-- ;
						}
						
						// When (total time - executed time) equals zero, 
						// print and remove from red black tree,
						// else insert in the heap and track equals 0
						if(remember.total_time-remember.executed_time==0) {							
							System.out.println("("+ remember.buildingNum + "," + counter + ")");
							red_black.deleteValue(remember.buildingNum);
						}else  {
							heap.insert(remember);
							track=0;
						}
						
					}
					else			
					{	
						// Increase the global timer
						increaseGlobalTimer( li , heap , red_black) ;
					}
			}

			reader.close();

		} catch (IOException e) {

			e.printStackTrace();

		}
	}
	
	// Parsing the input file
	// Get the values separately from this type of input:
	// Example: 908000: Insert(7496,3700)
	//	    910000: PrintBuilding(1533)
	private static List<Integer> funParse(String line) 
	{
		int colon = line.indexOf(":");

		int startArgs = line.indexOf("(");

		int lastArgs = line.indexOf(")");

		List<Integer> li=new ArrayList<Integer>();

		li.add(Integer.valueOf(line.substring(0, colon)));

		String s=line.substring(colon+1, startArgs).trim();
		if(s.equals("Insert")) {
			li.add(1);
		}
		else if(s.equals("PrintBuilding")) {
			li.add(2);
		}
		
		String args[] = line.substring(startArgs+1, lastArgs).split(",");
		
		if(args.length==2) {
			li.add(Integer.valueOf(args[0]));
			li.add(Integer.valueOf(args[1]));
			if(li.get(1)==2)
				li.set(1,3);
		}
		else
			li.add(Integer.valueOf(args[0]));

		return li;
	}	
}

    
