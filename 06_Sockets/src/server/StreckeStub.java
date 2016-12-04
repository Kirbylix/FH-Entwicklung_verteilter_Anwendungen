package server;
import java.lang.reflect.*;
import java.text.DateFormat;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.stream.*;
import javax.xml.stream.events.*;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

import common.ParserException;
import common.StaxMessageParserUtility;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.Locale;

public class StreckeStub
{
	
	private final boolean debug = true;
	
	private String[] getParam(StaxMessageParserUtility parser)throws ParserException {
		
		String[] param = new String[2];
		
		parser.skipKnownStartElement("param");
		parser.skipKnownStartElement("value");
		param[0] = parser.getStartElement();
		param[1] = parser.getCharacters();
		parser.skipToken();
		parser.skipKnownEndElement("value");
		parser.skipKnownEndElement("param");
		return param;
	}
		
	public Response Execute(String className, String methodeName, Object[] parameterObjekte){
		
		Object res =  new Object();
		
		Class klasse;
		Class[] paramTypes;
		Object objekt;
		Method[] methods;
		Method methode;
		
		MethodResponse theResponse = null;

		Fault  theFault =new Fault();
		theFault.setFaultCode("1");
		theFault.setReason("no such methode in class " + className);
		
		try {
			klasse = Class.forName("server."+className);
			objekt = klasse.newInstance();
			methods = klasse.getMethods();
			for(int ii= 0; ii<methods.length;ii++){
				if(methods[ii].getName().equals(methodeName)){
					res = methods[ii].invoke(objekt, parameterObjekte);
			
					
					theResponse = new MethodResponse(); 
					theResponse.setValueType(res.getClass().toString());
					theResponse.setValue(res.toString());
					break;
				}
			}	
			
		} catch (ClassNotFoundException e) {
			theFault.setFaultCode("2");
			theFault.setReason(e.getMessage());
		} catch (InstantiationException e) {
			theFault.setFaultCode("3");
			theFault.setReason(e.getMessage());
		} catch (IllegalAccessException e) {
			theFault.setFaultCode("4");
			theFault.setReason(e.getMessage());
		} catch (IllegalArgumentException e) {
			theFault.setFaultCode("5");
			theFault.setReason(e.getMessage());
		} catch (SecurityException e) {
			theFault.setFaultCode("6");
			theFault.setReason(e.getMessage());
		} catch (InvocationTargetException e) {
			theFault.setFaultCode("7");
			theFault.setReason(e.getMessage());
		}
		
		if(res!=null) return theResponse;
		else return theFault;
	}
	
	public String createFaultXMLMessage(Fault theFault){
		String responseMessage = "";
		
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        XMLOutputFactory xof =  XMLOutputFactory.newInstance(); 
        XMLStreamWriter xtw = null;
        
        
        try 
        {
        	
        		xtw = xof.createXMLStreamWriter(outputStream, "UTF-8"); 
	            xtw.writeStartDocument("UTF-8", "1.0"); 
	            	xtw.writeStartElement("methodResponse");
	            		xtw.writeStartElement("fault");
	            			xtw.writeStartElement("value");
	            				xtw.writeStartElement("struct");
	            					xtw.writeStartElement("member");
	            						xtw.writeStartElement("name");	            
	            							xtw.writeCharacters("fault code");	          
	            						xtw.writeEndElement(); // ende name  
	            						xtw.writeStartElement("value");
	            							xtw.writeStartElement("String");	            
	            								xtw.writeCharacters(theFault.getFaultCode());	            
	            							xtw.writeEndElement(); //ende String
	            						xtw.writeEndElement(); // ende value         
	            					xtw.writeEndElement(); // ende member
	            					xtw.writeStartElement("member");
	            						xtw.writeStartElement("name");	            
	            							xtw.writeCharacters("fault string");	          
	            						xtw.writeEndElement(); // ende name  
	            						xtw.writeStartElement("value");
	            							xtw.writeStartElement("String");	            
	            								xtw.writeCharacters(theFault.getReason());	            
	            							xtw.writeEndElement(); //ende String
	            						xtw.writeEndElement(); // ende value         
	            					xtw.writeEndElement(); // ende member
	            				xtw.writeEndElement(); // ende struct
	            			xtw.writeEndElement(); //end value
	            		xtw.writeEndElement(); //end fault
	            	xtw.writeEndElement(); //end methodResponse
        	
        }
        catch (XMLStreamException e) 
        { 
            e.printStackTrace(); 
        }
        
        finally 
        { 
            if (xtw != null) 
            { 
                try 
                { 
                    xtw.close(); 
                } 
                catch (XMLStreamException e) 
                { 
                    e.printStackTrace(); 
                } 
            }
        }
        
        responseMessage = new String(outputStream.toByteArray());        
		return responseMessage;
	}


	public String createResponseXMLMessage(MethodResponse theResponse){
		String responseMessage = "";
		
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        XMLOutputFactory xof =  XMLOutputFactory.newInstance(); 
        XMLStreamWriter xtw = null;
        
        
        try 
        {
        	
 	            xtw = xof.createXMLStreamWriter(outputStream, "UTF-8"); 
	            xtw.writeStartDocument("UTF-8", "1.0"); 
	            xtw.writeStartElement("methodResponse");
	            xtw.writeStartElement("params");
	            xtw.writeStartElement("param"); 
	            xtw.writeStartElement("value");
	            
	                        
	            if(theResponse.getValueType().equals("class java.lang.Integer")){
	            	xtw.writeStartElement("int"); 
	                xtw.writeCharacters(theResponse.getValue());
	                xtw.writeEndElement();
	            }
	            
	            else if(theResponse.getValueType().equals("class java.lang.Boolean")){
	            	xtw.writeStartElement("boolean"); 
	                xtw.writeCharacters(theResponse.getValue());
	                xtw.writeEndElement();
	            }
	            
	            else if(theResponse.getValueType().equals("class java.lang.String")){
	            	xtw.writeStartElement("String"); 
	                xtw.writeCharacters(theResponse.getValue());
	                xtw.writeEndElement();
	            }
	              
	            xtw.writeEndElement(); //end value
	            xtw.writeEndElement(); //end param
	            xtw.writeEndElement(); //end params
	            xtw.writeEndElement(); //end methodResponse
        	}
        	
        	
        catch (XMLStreamException e) 
        { 
            e.printStackTrace(); 
        }
        
        finally 
        { 
            if (xtw != null) 
            { 
                try 
                { 
                    xtw.close(); 
                } 
                catch (XMLStreamException e) 
                { 
                    e.printStackTrace(); 
                } 
            }
        }
        
        responseMessage = new String(outputStream.toByteArray());        
		return responseMessage;
	}


    public String sendReceiveMsg(String dieNachricht)
    {

    	Response theResponse;   // MessageResponse oder Fault
    	String dieAntwort;
     	
    	String methodeName = "";
    	
    	ArrayList<String[]> parameter = new ArrayList<String[]>();

    	StaxMessageParserUtility parser = new StaxMessageParserUtility();
    	parser.open(dieNachricht);
    	
    	try {
    		parser.skipToken();
			methodeName = parser.getKnownLeafElement("methodName");
			parser.skipKnownStartElement("params");
    	
			while(parser.isStartElement()){
				parameter.add(getParam(parser));
			}
    		
		} catch (ParserException e) {
			e.printStackTrace();
		}
    	
    	parser.close();

    	Object[] paramObjekte = new Object[parameter.size()];
    	
    	for(int ii = 0; ii<parameter.size();ii++){
    		if(parameter.get(ii)[0].equals("int")){
    			paramObjekte[ii] = Integer.parseInt(parameter.get(ii)[1]);
    		}
    		else if(parameter.get(ii)[0].equals("boolean")){
    			paramObjekte[ii] = Boolean.parseBoolean(parameter.get(ii)[1]);
    		}
    		
    		else if(parameter.get(ii)[0].equals("double")){
    			paramObjekte[ii] = Double.parseDouble(parameter.get(ii)[1]);
    		}
    		
    		else if(parameter.get(ii)[0].equals("String")){
    			paramObjekte[ii] = parameter.get(ii)[1];
    		}
    	}
    	
    	theResponse = Execute("Strecke", methodeName, paramObjekte);
    	
    	if(theResponse.getClass().equals(Fault.class))
    	{
    		 dieAntwort = createFaultXMLMessage((Fault)theResponse);   		
    	}
    	else
    	{
        	dieAntwort = createResponseXMLMessage((MethodResponse)theResponse);   		
    	}	
    	
    	System.out.println(dieAntwort);
    	
        return dieAntwort;
    }
}