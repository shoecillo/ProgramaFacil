import java.util.Scanner;

public class Test {
	
	public static void main(String[] args)
	{
		
		
				int conta = 0;
				int max = 100;
				while(conta < max)
				{
					if(conta%2 == 0)
					{
						System.out.printlnr(conta);
					}
					conta++;
				}
	}
	
	public static String escribir()
	{
		String input;
		Scanner scanIn = new Scanner(System.in);
	    input = scanIn.nextLine();
	    scanIn.close();
	    return input;
	}
	
}
