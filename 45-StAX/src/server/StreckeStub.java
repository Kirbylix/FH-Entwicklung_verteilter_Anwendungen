package server;

import common.ParserException;
import common.StaxMessageParserUtility;

import java.io.ByteArrayOutputStream;
import java.lang.reflect.*;

import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

public class StreckeStub {
	private String methodeName;
	private String className = "server.Strecke";
	private Object dasObjekt = new Strecke();

	public String sendReceiveMsg(String dieNachricht) {

		/**
		 * 1. Schritt die empfangene XML-Nachricht wird geparst
		 *
		 * Ergebnis: Methodenname steht als String zur Verfügung Die Parameter
		 * stehen als Object-Array zur Verfügung (Object paramObjekte[])
		 */
		Object[] param = parseMessage(dieNachricht);

		/*
		 * 2. Schritt die Methode wird ausgeführt und liefert im Erfolgsfall ein
		 * Antwort-Objekt zurück.
		 */
		Response response = execute(param);
		/**
		 * 3. Schritt
		 *
		 * Die Antwort wird in ein XML-Antwort übertragen
		 *
		 */
		if (response instanceof Fault)
			return createFaultXMLMessage((Fault) response);
		else if (response instanceof MethodResponse)
			return createResponseXMLMessage((MethodResponse) response);
		else
			return null;
	}

	public Object[] parseMessage(String message) {
		Object[] paramObjekte = new Object[3];
		StaxMessageParserUtility parser = new StaxMessageParserUtility();
		parser.open(message);
		try {
			parser.skipKnownStartElement("methodCall");
			String type = parser.getStartElement();
			if (type.equals("methodName"))
				methodeName = parser.getCharacters();
			parser.skipKnownEndElement("methodName");
			parser.skipKnownStartElement("params");
			parser.skipKnownStartElement("param");
			parser.skipKnownStartElement("value");

			type = parser.getStartElement();
			if (type.equals("int")) {
				paramObjekte[0] = Integer.parseInt(parser.getCharacters());

				parser.skipKnownEndElement("int");
				parser.skipKnownEndElement("value");
				parser.skipKnownEndElement("param");
				parser.skipKnownStartElement("param");
				parser.skipKnownStartElement("value");
				type = parser.getStartElement();
			}

			if (type.equals("boolean")) {
				paramObjekte[1] = Boolean.parseBoolean(parser.getCharacters());

				parser.skipKnownEndElement("boolean");
				parser.skipKnownEndElement("value");
				parser.skipKnownEndElement("param");
				parser.skipKnownStartElement("param");
				parser.skipKnownStartElement("value");

				type = parser.getStartElement();
			}
			if (type.equals("int"))
				paramObjekte[2] = Integer.parseInt(parser.getCharacters());

		} catch (ParserException e) {

			e.printStackTrace();
		}
		parser.close();
		return paramObjekte;
	}

	public Response execute(Object[] parameterObjekte) {
		System.out.println("Aufruf als Zeichenkette:\n" + className + "." + methodeName + "(" + parameterObjekte[0]
				+ ", " + parameterObjekte[1] + ", " + parameterObjekte[2] + ");");
		Response response = null;
		try {
			Class dieKlasse = Class.forName(className);

			// erzeuge ein Objekt dieser Klasse

			Class[] dieParameterklassen = null;
			Object[] dieParameterObjekte = null;

			if (methodeName.equals("reserviere") || methodeName.equals("freigeben") || methodeName.equals("wechselnVon")
					|| methodeName.equals("wechselnNach")) {
				dieParameterklassen = new Class[3];
				dieParameterklassen[0] = Integer.class;
				dieParameterklassen[1] = Boolean.class;
				dieParameterklassen[2] = Integer.class;
				dieParameterObjekte = new Object[3];
				dieParameterObjekte[0] = (Integer) parameterObjekte[0];
				dieParameterObjekte[1] = (Boolean) parameterObjekte[1];
				dieParameterObjekte[2] = (Integer) parameterObjekte[2];
			} else if (methodeName.equals("getAbschnitt")) {
				dieParameterklassen = new Class[2];
				dieParameterklassen[0] = Boolean.class;
				dieParameterklassen[1] = Integer.class;
				dieParameterObjekte = new Object[2];
				dieParameterObjekte[0] = (Boolean) parameterObjekte[1];
				dieParameterObjekte[1] = (Integer) parameterObjekte[2];
			}
			Method mtd = dieKlasse.getMethod(methodeName, dieParameterklassen);
			Object res = mtd.invoke(dasObjekt, dieParameterObjekte);
			if (res instanceof Integer)
				response = new MethodResponse("int", String.valueOf(res));
			else if (res instanceof Boolean)
				response = new MethodResponse("boolean", String.valueOf(res));
			else if (res instanceof String)
				response = new MethodResponse("String", String.valueOf(res));
			else
				response = new Fault("1", "Methode lieferte nichts zurück");

		} catch (ClassNotFoundException e) {
			response = new Fault("2", e.getMessage());
			System.out.println(e);
		} catch (NoSuchMethodException e) {
			response = new Fault("3", e.getMessage());
			System.out.println(e);
		} catch (IllegalAccessException e) {
			response = new Fault("4", e.getMessage());
			System.out.println(e);
		} catch (InvocationTargetException e) {
			response = new Fault("5", e.getMessage());
			System.out.println(e);
		}
		return response;
	}

	public String createFaultXMLMessage(Fault theFault) {
		String responseMessage = "";
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		XMLOutputFactory xof = XMLOutputFactory.newInstance();
		XMLStreamWriter xtw = null;

		try {
			xtw = xof.createXMLStreamWriter(outputStream, "UTF-8");
			xtw.writeStartDocument("UTF-8", "1.0");
			xtw.writeStartElement("params");

			xtw.writeStartElement("param");
			xtw.writeStartElement("value");
			xtw.writeStartElement(theFault.getFaultCode());
			xtw.writeCharacters("" + theFault.getReason());
			xtw.writeEndElement();
			xtw.writeEndElement();
			xtw.writeEndElement();
			xtw.writeEndElement(); // params
		} catch (XMLStreamException e) {
			e.printStackTrace();
		}

		finally {
			if (xtw != null) {
				try {
					xtw.close();
				} catch (XMLStreamException e) {
					e.printStackTrace();
				}
			}
		}
		responseMessage = new String(outputStream.toByteArray());
		return responseMessage;
	}

	public String createResponseXMLMessage(MethodResponse theResponse) {
		String responseMessage = "";
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		XMLOutputFactory xof = XMLOutputFactory.newInstance();
		XMLStreamWriter xtw = null;

		try {
			xtw = xof.createXMLStreamWriter(outputStream, "UTF-8");
			xtw.writeStartDocument("UTF-8", "1.0");
			xtw.writeStartElement("methodResponse");
			xtw.writeStartElement("params");
			xtw.writeStartElement("param");
			xtw.writeStartElement("value");
			xtw.writeStartElement(theResponse.getValueType());
			xtw.writeCharacters("" + theResponse.getValue());
			xtw.writeEndElement();
			xtw.writeEndElement();
			xtw.writeEndElement();
			xtw.writeEndElement(); // params
			xtw.writeEndElement();
		} catch (XMLStreamException e) {
			e.printStackTrace();
		}

		finally {
			if (xtw != null) {
				try {
					xtw.close();
				} catch (XMLStreamException e) {
					e.printStackTrace();
				}
			}
		}
		responseMessage = new String(outputStream.toByteArray());
		System.out.println(responseMessage);
		return responseMessage;
	}
}