package com.sh.translate;

import java.io.IOException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.io.IOUtils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Translator {

	private static ProcessCode prc = new ProcessCode();
	
	public static void main(String[] args)
	{
		
		String nameEjer = "Ejercicio1";
		
		StringBuilder pgm = new StringBuilder();
		pgm.append("entero contador = 0;\n");
		pgm.append("decimal max = 3.5;\n");
		pgm.append("texto saludo = \"texto entero decimal\";\n");
		pgm.append("pinta(\"introduce un texto\");\n");
		pgm.append("texto teclado = escribir();\n");
		pgm.append("pinta(\"Has introducido: \"+teclado);\n");
		pgm.append("mientras(contador < 100)\n");
		pgm.append("{\n");
		pgm.append("\tpinta(saludo + \" \"+contador);\n");
		pgm.append("\tcontador++;\n");
		pgm.append("}");
		System.out.println(pgm.toString());
		System.out.println("--------------------------------");
		
		Translator tr = new Translator();
		tr.saveAll(nameEjer, pgm.toString());
	}
	
	public void saveAll(String nameEjer,String body)
	{
		try 
		{	
			
			prc.saveEjercicio(body, nameEjer);
			String res = replaceAll(body);
			res = generateJava(res,nameEjer);
			prc.saveSrc(res, nameEjer);
			
			
		} catch (IOException e) {
			
			e.printStackTrace();
		}
	}
	
	
	
	
	private List<JsonKeyVal> readRules()
	{
		
		List<JsonKeyVal> ls  = null;
		ClassLoader classLoader = getClass().getClassLoader();	
		try 
		{
			ObjectMapper mapper = new ObjectMapper();
			
			String file = IOUtils.toString(classLoader.getResourceAsStream("rules.json"));
			ls = mapper.readValue(file, new TypeReference<List<JsonKeyVal>>(){});
		}
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		
		return ls;
	}
	
	public String generateJava(String body,String name)
	{
		String res = "";
		try 
		{	
			ClassLoader classLoader = getClass().getClassLoader();	
			String template = IOUtils.toString(classLoader.getResourceAsStream("Template.templ"));
			res = template.replaceAll("##BODY##", body);
			res = res.replaceAll("##CLASSNAME##", name);
			
		} 
		catch (IOException e) 
		{	
			e.printStackTrace();
		}
		
		return res;
	}
	
	public String replaceAll(String pgm)
	{
		pgm = "\n"+pgm;
		String pat = "(\\n|^|\\t+)";
		List<JsonKeyVal> ls = readRules();
		for(JsonKeyVal reg : ls)
		{
			Pattern regex = Pattern.compile(pat+reg.getKey());
			Matcher match = regex.matcher(pgm);
			while(match.find())
			{
				String res = match.group();
				String spec = res.substring(0, res.indexOf(reg.getKey()));
				pgm = match.replaceAll(spec+reg.getValue());	
			}
		}
		pgm = pgm.replaceAll("\n", "\n\t\t");
		return pgm;
	}
	
	

}
