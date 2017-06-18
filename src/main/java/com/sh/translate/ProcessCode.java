package com.sh.translate;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Observable;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

public class ProcessCode extends Observable {
	
	
	private static final String RUTA = "ejercicios";
	private static final String RUTA_SRC = "ejercicios/src";
	private static final String EXT_EJEC = ".ejerc";
	private static final String EXT_JAVA = ".java";
	
	
	 public ProcessCode()
	 {
		 super();
		 File src = new File(RUTA_SRC);
		 try {
			if(!src.exists())
			 {
				src.mkdirs();
			 }
			File batFile = new File(src.getAbsolutePath()+File.separator+"exec.bat");
			if(!batFile.exists())
			{
				ClassLoader classLoader = getClass().getClassLoader();
				
				byte[] b =IOUtils.toByteArray(classLoader.getResourceAsStream("exec.bat"));
				
				FileUtils.writeByteArrayToFile(batFile, b);
			}
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
	}

	public static void main(String[] args) 
	{
		int res = new ProcessCode().compile("Ejercicio1");
		if(res == 0)
			new ProcessCode().execute("Ejercicio1");
		else
			System.out.println("NO SE PUEDE EJECUTAR, ERROR DE COMPILACION");
	}
	
	public String loadFile(String path) throws IOException
	{
		File f = new File(path);
		String res = FileUtils.readFileToString(f);
		return res;
	}
	
	
	public void saveSrc(String src,String fileName) throws IOException
	{
		File f = new File(RUTA_SRC+"/"+fileName+EXT_JAVA);
		FileUtils.writeStringToFile(f, src, "UTF-8");
	}
	
	public void saveEjercicio(String src,String fileName) throws IOException
	{
		File f = new File(RUTA+"/"+fileName+EXT_EJEC);
		FileUtils.writeStringToFile(f, src, "UTF-8");
	}
	
	public Integer execute(String fileName)
	{
		 try 
		 {
			int res = compile(fileName);
			if(res == 0)
			{
			    File f = new File(RUTA_SRC);
			    String cmd = "cmd /c start "+f.getAbsolutePath() + File.separator+"exec.bat "+fileName+" "+f.getAbsolutePath();
			    setChanged();
		        notifyObservers("EJECUTANDO "+fileName);
				return runProcess(cmd,true);
			}
			else
			{
				return -1;
			}
		    
		 } 
		 catch (Exception e) 
		 {
		      e.printStackTrace();
		      return null;
		 }
	}
	
	public Integer compile(String fileName)
	{
		
		 try 
		 {
		    File f = new File(RUTA_SRC);
		    String classFile = f.getAbsolutePath()+File.pathSeparator+fileName+".class";
		    File cFile = new File(classFile);
		    if(cFile.exists())
		    	cFile.delete();
		    String path = f.getAbsolutePath()+File.separator+fileName+EXT_JAVA;
		    setChanged();
	        notifyObservers("COMPILANDO "+fileName);
			return runProcess("javac "+path,false);
		 } 
		 catch (Exception e) 
		 {
		      e.printStackTrace();
		      return null;
		 }
	}
	
	private void printLines(boolean exec, InputStream ins) throws Exception 
	{
	    String line = null;
	    BufferedReader in = new BufferedReader(new InputStreamReader(ins));
	    while ((line = in.readLine()) != null) 
	    {
	        System.out.println(line);
	        setChanged();
	        notifyObservers(line);
	    }
	  }

	  private int runProcess(String command,boolean exec) throws Exception 
	  {
	    Process pro = Runtime.getRuntime().exec(command);
	    printLines(exec, pro.getInputStream());
	    printLines(exec, pro.getErrorStream());
	    pro.waitFor();
	    //System.out.println(command + " exitValue() " + pro.exitValue());
	    if(exec)
	    {
	    	setChanged();
	    	notifyObservers(pro.exitValue()==0?"EJECUCION CORRECTA":"EJECUCION ERRONEA");
	    }
	    else
	    {
	    	setChanged();
	    	notifyObservers(pro.exitValue()==0?"COMPILACION CORRECTA":"COMPILACION ERRONEA");
	    }
	    return pro.exitValue();
	  }

	public static String getRuta() {
		return RUTA;
	}

	public static String getRutaSrc() {
		return RUTA_SRC;
	}

	public static String getExtEjec() {
		return EXT_EJEC;
	}

	public static String getExtJava() {
		return EXT_JAVA;
	}
	
	
	
	
	 
}
