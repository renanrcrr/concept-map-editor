package on_tool.io;

import java.util.Vector;

import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

public class ManipuladorXML2 extends DefaultHandler {
	
	Vector conceitos;
	
	public ManipuladorXML2() {
		super();
		conceitos = new Vector();
	}
	
	public void startElement(String uri,
			String localName,
			String qName,
			Attributes attributes) {
		if (qName == "conceito") {
			for (int i = 0; i < attributes.getLength(); i++)
				if (attributes.getQName(i) == "texto") {
					conceitos.add(attributes.getValue(i));
					break;
				}
		}
	}
	
}
