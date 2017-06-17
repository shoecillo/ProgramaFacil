import java.util.Scanner;

public class Ejercicio1 {
	
	public static void main(String[] args)
	{
		
		
		// Saca los pares de 0 a 100
		
		int contador = 0;
		double max = 3.5;
		String saludo = "texto entero decimal";
		String teclado = escribirTexto();
		while(contador < 100)
		{
			if(contador%2 == 0)
			{
				System.out.println(saludo + " "+contador);
			}
			contador++;
		}		System.out.println("fin");
	}
	
	public static String escribirTexto()
	{
		String input;
		Scanner scanIn = new Scanner(System.in);
	    input = scanIn.nextLine();
	    scanIn.close();
	    return input;
	}
	
	public static int escribirEntero()
	{
		String input;
		int res = 0;
		Scanner scanIn = new Scanner(System.in);
		while(scanIn.hasNextLine())
		{
			input = scanIn.nextLine();
			try 
			{	
				res= Integer.parseInt(input);
				break;
			} 
			catch (NumberFormatException e) 
			{
				System.out.println("No es un numero entero!!");
			}
		}
		scanIn.close();
		return res;
	}
	
	public static double escribirDecimal()
	{
		String input;
		double res = 0;
		Scanner scanIn = new Scanner(System.in);
		while(scanIn.hasNextLine())
		{
			input = scanIn.nextLine();
			try 
			{	
				if(input.indexOf(".")==-1)
				{
					throw new NumberFormatException("No tiene decimales");
				}
				res= Double.parseDouble(input);
				break;
			} 
			catch (NumberFormatException e) 
			{
				System.out.println("No es un numero decimal!!");
			}
		}
		scanIn.close();
		return res;
		
	}
	
}
