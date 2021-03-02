package havis.middleware.ale.base;

import java.io.OutputStream;
import java.io.Writer;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

/**
 * XML stream writer which corrects the prefixes of namespaces and sorts them by
 * their URI
 */
public class NamespaceXMLStreamWriter extends DelegatingXMLStreamWriter {

	private static final XMLOutputFactory factory = XMLOutputFactory.newInstance();

	/**
	 * Creates a new instance for the specified writer
	 * 
	 * @param writer
	 *            the writer to write the XML to
	 * @return a new instance
	 */
	public static XMLStreamWriter create(Writer writer) {
		XMLStreamWriter streamWriter;
		try {
			streamWriter = factory.createXMLStreamWriter(writer);
		} catch (XMLStreamException e) {
			throw new IllegalStateException(e);
		}
		return new NamespaceXMLStreamWriter(streamWriter);
	}

	/**
	 * Creates a new instance for the specified stream
	 * 
	 * @param stream
	 *            the stream to write the XML to
	 * @return a new instance
	 */
	public static XMLStreamWriter create(OutputStream stream) {
		XMLStreamWriter streamWriter;
		try {
			streamWriter = factory.createXMLStreamWriter(stream);
		} catch (XMLStreamException e) {
			throw new IllegalStateException(e);
		}
		return new NamespaceXMLStreamWriter(streamWriter);
	}

	private NamespaceXMLStreamWriter(XMLStreamWriter delegate) {
		super(delegate);
	}

	// sorted
	private Map<String, String> cachedNamespaces = new TreeMap<>();
	private Map<String, String> prefixReplacements = new HashMap<>();

	private String getPrefix(String uri, String currentPrefix) {
		String newPrefix = null;
		switch (uri) {
		case "urn:epcglobal:xsd:1":
			newPrefix = "base";
			break;
		case "urn:epcglobal:ale:xsd:1":
			newPrefix = "ale";
			break;
		case "urn:epcglobal:ale:wsdl:1":
			newPrefix = "ws";
			break;
		case "urn:epcglobal:aleac:wsdl:1":
			newPrefix = "ac";
			break;
		case "urn:epcglobal:alecc:wsdl:1":
			newPrefix = "cc";
			break;
		case "urn:epcglobal:alelr:wsdl:1":
			newPrefix = "lr";
			break;
		case "urn:epcglobal:aletm:wsdl:1":
			newPrefix = "tm";
			break;
		case "urn:havis:ale:xsd:1":
			newPrefix = "impl";
			break;
		case "urn:havis:alemc:wsdl:1":
			newPrefix = "mc";
			break;
		case "urn:havis:alepc:wsdl:1":
			newPrefix = "pc";
			break;
		case "urn:havis:alerc:wsdl:1":
			newPrefix = "rc";
			break;
		}
		if (newPrefix != null) {
			if (!prefixReplacements.containsKey(currentPrefix)) {
				prefixReplacements.put(currentPrefix, newPrefix);
			}
			return newPrefix;
		}
		return currentPrefix;
	}

	@Override
	public void writeStartDocument() throws XMLStreamException {
		super.writeStartDocument(Charset.defaultCharset().name(), "1.0");
	}

	@Override
	public void writeStartDocument(String ver) throws XMLStreamException {
		super.writeStartDocument(Charset.defaultCharset().name(), ver);
	}

	@Override
	public String getPrefix(String uri) throws XMLStreamException {
		return getPrefix(uri, super.getPrefix(uri));
	}

	@Override
	public void writeNamespace(String prefix, String uri) throws XMLStreamException {
		this.cachedNamespaces.put(uri, getPrefix(uri, prefix));
	}

	private void writeCachedNamespaces() throws XMLStreamException {
		for (Entry<String, String> ns : this.cachedNamespaces.entrySet()) {
			super.writeNamespace(ns.getValue(), ns.getKey());
		}
		this.cachedNamespaces.clear();
	}

	@Override
	public void writeStartElement(String prefix, String local, String uri) throws XMLStreamException {
		writeCachedNamespaces();
		super.writeStartElement(getPrefix(uri, prefix), local, uri);
	}

	@Override
	public void writeEndElement() throws XMLStreamException {
		writeCachedNamespaces();
		super.writeEndElement();
	}

	@Override
	public void writeAttribute(String prefix, String uri, String local, String value) throws XMLStreamException {
		String p = getPrefix(uri, prefix);
		super.writeAttribute(p, uri, local, processValue(p, local, value));
	}

	private String processValue(String prefix, String local, String value) {
		int index;
		if ("xsi".equals(prefix) && "type".equals(local) && value != null && (index = value.indexOf(':')) > -1) {
			String valuePrefix = value.substring(0, index);
			if (this.prefixReplacements.containsKey(valuePrefix)) {
				return this.prefixReplacements.get(valuePrefix) + value.substring(index);
			}
		}
		return value;
	}
}
