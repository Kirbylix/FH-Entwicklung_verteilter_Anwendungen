
package client;
import java.text.DateFormat;
import java.text.*;
import java.util.*;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ByteArrayOutputStream; 

import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

import common.ParserException;
import common.StaxMessageParserUtility;
import common.StreckeI;
import server.StreckeStub;


public class ClientStub implements StreckeI // Stub
{


	private long seqNr = 1;

    public Boolean reserviere(Integer zugnummer, Boolean bRichtung, Integer iAbschnitt)
	{
        String strNachricht = buildMsg("reserviere", zugnummer, bRichtung, iAbschnitt);
        String strAntwort  = sendMsg(strNachricht);
        return (Boolean) parseResponse(strAntwort);

    }


    public String wechselnVon(Integer zugnummer, Boolean bRichtung, Integer iAbschnitt)
	{
    	String strNachricht = buildMsg("wechselnVon", zugnummer, bRichtung, iAbschnitt).toString();
        String strAntwort  = sendMsg(strNachricht);
        return (String) parseResponse(strAntwort);
    }


    public String wechselnNach(Integer zugnummer, Boolean bRichtung, Integer iAbschnitt)
	{
    	String strNachricht = buildMsg("wechselnNach", zugnummer, bRichtung, iAbschnitt).toString();
        String strAntwort  = sendMsg(strNachricht);
        return (String) parseResponse(strAntwort);
    }

    public Boolean freigeben(Integer zugnummer, Boolean bRichtung, Integer iAbschnitt)
	{
    	String strNachricht = buildMsg("freigeben", zugnummer, bRichtung, iAbschnitt);
        String strAntwort  = sendMsg(strNachricht);
    	return (Boolean) parseResponse(strAntwort);
    }


    public String verlassen(Integer zugnummer, Boolean bRichtung)
	{
    	String strNachricht = buildMsg("verlassen", zugnummer, bRichtung, -1);
        String strAntwort  = sendMsg(strNachricht);
        return (String) parseResponse(strAntwort);
    }

    public Integer getStreckenLaenge()
	{
    	String strNachricht = buildMsg("getStreckenLaenge", -1, false, -1);
        String strAntwort  = sendMsg(strNachricht);
    	return (Integer) parseResponse(strAntwort);
    }

    public Integer getAbschnitt(Boolean bRichtung, Integer iAbschnitt)
	{
    	String strNachricht = buildMsg("getAbschnitt", -1, false, iAbschnitt);
        String strAntwort  = sendMsg(strNachricht);
    	return (Integer) parseResponse(strAntwort);
    }

//----------------------------------------------------------------------------------------------------

    public Object parseResponse(String response){
    	Object res = null;
    	
    	StaxMessageParserUtility parser = new StaxMessageParserUtility();
    	parser.open(response);
    	try {
			parser.skipToken();
			parser.skipKnownStartElement("params");
			parser.skipKnownStartElement("param");
			parser.skipKnownStartElement("value");
			
			String type = parser.getStartElement();
			
			if(type.equals("int"))
				res = Integer.parseInt(parser.getCharacters());
			
			else if(type.equals("boolean"))
				res = Boolean.parseBoolean(parser.getCharacters());
			
			else if(type.equals("String"))
				res = parser.getCharacters();
			
		} catch (ParserException e) {

			e.printStackTrace();
		}
    	parser.close();
    	
    	return res;
    }
    
    
//----------------------------------------------------------------------------------------------------



    public String buildMsg(String action, int zugnummer, boolean bRichtung, int iAbschnitt)
	{

        String strRichtung;
        String strDatum = "";
        String strUhrzeit = "";

  	    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();


        XMLOutputFactory xof =  XMLOutputFactory.newInstance(); 
        XMLStreamWriter xtw = null; 

		if(bRichtung == true)
		{
			strRichtung = "true";
	    }
	    else
	    {
	    	    strRichtung = "false";
	   }

        Date now = new Date();
        DateFormat dfDate = DateFormat.getDateInstance(DateFormat.LONG, Locale.GERMAN);
        DateFormat dfTime = DateFormat.getTimeInstance(DateFormat.SHORT);

        strDatum   = dfDate.format(now);
        strUhrzeit = dfTime.format(now);


    try 
    { 
        xtw = xof.createXMLStreamWriter(outputStream, "UTF-8"); 
        xtw.writeStartDocument("UTF-8", "1.0"); 
        xtw.writeStartElement("methodCall");
        xtw.writeStartElement("methodName"); 
        xtw.writeCharacters(action);
        xtw.writeEndElement(); 

        xtw.writeStartElement("params"); 

        if (!action.equals("getStreckenLaenge") && !action.equals("getAbschnitt"))
        {   // Zugnummer ist Parameter
            xtw.writeStartElement("param"); 
            xtw.writeStartElement("value"); 
            xtw.writeStartElement("int"); 
            xtw.writeCharacters(""+zugnummer);
            xtw.writeEndElement(); 
            xtw.writeEndElement(); 
            xtw.writeEndElement(); 
        }  

        if (!action.equals("getStreckenLaenge"))
        {   // Richtung ist Parameter
            xtw.writeStartElement("param"); 
            xtw.writeStartElement("value"); 
            xtw.writeStartElement("boolean"); 
            xtw.writeCharacters(strRichtung);
            xtw.writeEndElement(); 
            xtw.writeEndElement(); 
            xtw.writeEndElement(); 
        }
        
        if (!action.equals("verlassen") && !action.equals("getStreckenlaenge"))
        {   // Abschnitt ist Parameter
            xtw.writeStartElement("param"); 
            xtw.writeStartElement("value"); 
            xtw.writeStartElement("int"); 
            xtw.writeCharacters(""+iAbschnitt);
            xtw.writeEndElement(); 
            xtw.writeEndElement(); 
            xtw.writeEndElement(); 
        }
        
        xtw.writeEndElement(); // params
        xtw.writeEndElement(); // methodCall
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

    String dieNachricht = new String(outputStream.toByteArray());

    System.out.println(dieNachricht);
    return dieNachricht;
	}
 

    
    public String sendMsg(String dieNachricht)
    {
    
    	ClientConnection theConnection = new ClientConnection(0);
    	String response = theConnection.sendMsg(dieNachricht);
        return response;
    }
   
}
