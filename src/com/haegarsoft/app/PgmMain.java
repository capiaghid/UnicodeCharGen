package com.haegarsoft.app;

import java.io.*;
import java.lang.reflect.*;
import java.nio.charset.*;
import java.text.*;
import java.util.*;

public class PgmMain
{
    private static ArrayList<Method> TestsToPerform;
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws FileNotFoundException, NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, IOException
    {        
        PrintWriter textFile = null;
        char tmpChar = '\0';
        TestsToPerform = null;              
        
        System.out.println("Schreibt den gesammten UTF16 Zeichensatz in eine Datei");
        if(args.length == 0)
        {        	
        	System.out.println("Ussage java -jar UnicodeCharGen.jar <PathToFile>");
        	return;
        }
        
        try
        {        
        	textFile = new PrintWriter(args[0], StandardCharsets.UTF_16);
        	
        	TestsToPerform = new ArrayList<>();
        	
	        TestsToPerform.add(MethodByName(Character.class, "isDefined"));
	        TestsToPerform.add(MethodByName(Character.class, "isDigit"));
	        TestsToPerform.add(MethodByName(Character.class, "isHighSurrogate"));
	        TestsToPerform.add(MethodByName(Character.class, "isIdentifierIgnorable"));
	        TestsToPerform.add(MethodByName(Character.class, "isISOControl"));
	        TestsToPerform.add(MethodByName(Character.class, "isJavaIdentifierPart"));
	        TestsToPerform.add(MethodByName(Character.class, "isJavaIdentifierStart"));
	        TestsToPerform.add(MethodByName(Character.class, "isLetterOrDigit"));
	        TestsToPerform.add(MethodByName(Character.class, "isLowerCase"));
	        TestsToPerform.add(MethodByName(Character.class, "isLowSurrogate"));
	        TestsToPerform.add(MethodByName(Character.class, "isMirrored"));
	        TestsToPerform.add(MethodByName(Character.class, "isSpaceChar"));
	        TestsToPerform.add(MethodByName(Character.class, "isSurrogate"));
	        TestsToPerform.add(MethodByName(Character.class, "isTitleCase"));
	        TestsToPerform.add(MethodByName(Character.class, "isUnicodeIdentifierPart"));
	        TestsToPerform.add(MethodByName(Character.class, "isUnicodeIdentifierStart"));
	        TestsToPerform.add(MethodByName(Character.class, "isUpperCase"));
	        TestsToPerform.add(MethodByName(Character.class, "isWhitespace"));
	
	        String tmpText = "";
	        boolean ValidChar = false;
	        for(int Counter = 0; Counter < Math.pow(2, 16); Counter++)
	        {            
	            try
	            {
	                tmpChar =  (char)Counter;
	                ValidChar = true;
	            }
	            catch(Exception e)
	            {
	                ValidChar = false;
	            }
	                                                        
	            tmpText = String.format("No: %05d Char: ", Counter);
	            
	            if(Character.isDefined(tmpChar))
	            {                           
	                if(ValidChar)
	                {
	                    String tmpStr = Normalizer.normalize(Character.toString(tmpChar), Normalizer.Form.NFKC);
	                    tmpText += String.format("\"%s\"\t", tmpStr);
	                }
	                else
	                {
	                    tmpText += "<NA>";
	                }                     
	
	                for(Method fptr: TestsToPerform)
	                {
	                    tmpText += String.format(" %s", PrintParameter(fptr.getName(), (Boolean)fptr.invoke(null, tmpChar)));
	                }                          
	            }
	            else
	            {
	                tmpText += "<UNDEFINED>";
	            }
	            
	            textFile.println(tmpText);
	        }
	        System.out.println("Programm komplett");
        }
        finally
        {
        	if(textFile != null)
        	{
        		textFile.close();
        	}	        
        }
    }
    
    private static String PrintParameter(String aParameterName, boolean aParameterValue)
    {        
        return(String.format("%s: %s", aParameterName, aParameterValue ? "true" : "false"));
    }         
    
    @SuppressWarnings("rawtypes")
    private static Method MethodByName(Class aType, String aMethodName)
    {            
        Method Result = null;        
        
        for(Method tmpMethod: aType.getMethods())
        {
            if(tmpMethod.getName() == aMethodName)
            {
                Result = tmpMethod;
                break;
            }
        }        
        
        return(Result);
    }            
}