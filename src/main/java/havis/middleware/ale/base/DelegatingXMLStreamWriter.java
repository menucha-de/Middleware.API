package havis.middleware.ale.base;

import javax.xml.namespace.NamespaceContext;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

public class DelegatingXMLStreamWriter implements XMLStreamWriter {
	protected final XMLStreamWriter delegate;

	public DelegatingXMLStreamWriter(XMLStreamWriter delegate) {
		this.delegate = delegate;
	}

	@Override
	public void close() throws XMLStreamException {
		this.delegate.close();
	}

	@Override
	public void flush() throws XMLStreamException {
		this.delegate.flush();
	}

	@Override
	public NamespaceContext getNamespaceContext() {
		return this.delegate.getNamespaceContext();
	}

	@Override
	public String getPrefix(String uri) throws XMLStreamException {
		return this.delegate.getPrefix(uri);
	}

	@Override
	public Object getProperty(String name) throws IllegalArgumentException {
		return this.delegate.getProperty(name);
	}

	@Override
	public void setDefaultNamespace(String uri) throws XMLStreamException {
		this.delegate.setDefaultNamespace(uri);
	}

	@Override
	public void setNamespaceContext(NamespaceContext ctx) throws XMLStreamException {
		this.delegate.setNamespaceContext(ctx);
	}

	@Override
	public void setPrefix(String pfx, String uri) throws XMLStreamException {
		this.delegate.setPrefix(pfx, uri);
	}

	@Override
	public void writeAttribute(String prefix, String uri, String local, String value) throws XMLStreamException {
		this.delegate.writeAttribute(prefix, uri, local, value);
	}

	@Override
	public void writeAttribute(String uri, String local, String value) throws XMLStreamException {
		this.delegate.writeAttribute(uri, local, value);
	}

	@Override
	public void writeAttribute(String local, String value) throws XMLStreamException {
		this.delegate.writeAttribute(local, value);
	}

	@Override
	public void writeCData(String cdata) throws XMLStreamException {
		this.delegate.writeCData(cdata);
	}

	@Override
	public void writeCharacters(char[] arg0, int arg1, int arg2) throws XMLStreamException {
		this.delegate.writeCharacters(arg0, arg1, arg2);
	}

	@Override
	public void writeCharacters(String text) throws XMLStreamException {
		this.delegate.writeCharacters(text);
	}

	@Override
	public void writeComment(String text) throws XMLStreamException {
		this.delegate.writeComment(text);
	}

	@Override
	public void writeDefaultNamespace(String uri) throws XMLStreamException {
		this.delegate.writeDefaultNamespace(uri);
	}

	@Override
	public void writeDTD(String dtd) throws XMLStreamException {
		this.delegate.writeDTD(dtd);
	}

	@Override
	public void writeEmptyElement(String prefix, String local, String uri) throws XMLStreamException {
		this.delegate.writeEmptyElement(prefix, local, uri);
	}

	@Override
	public void writeEmptyElement(String uri, String local) throws XMLStreamException {
		this.delegate.writeEmptyElement(uri, local);
	}

	@Override
	public void writeEmptyElement(String localName) throws XMLStreamException {
		this.delegate.writeEmptyElement(localName);
	}

	@Override
	public void writeEndDocument() throws XMLStreamException {
		this.delegate.writeEndDocument();
	}

	@Override
	public void writeEndElement() throws XMLStreamException {
		this.delegate.writeEndElement();
	}

	@Override
	public void writeEntityRef(String ent) throws XMLStreamException {
		this.delegate.writeEntityRef(ent);
	}

	@Override
	public void writeNamespace(String prefix, String uri) throws XMLStreamException {
		this.delegate.writeNamespace(prefix, uri);
	}

	@Override
	public void writeProcessingInstruction(String target, String data) throws XMLStreamException {
		this.delegate.writeProcessingInstruction(target, data);
	}

	@Override
	public void writeProcessingInstruction(String target) throws XMLStreamException {
		this.delegate.writeProcessingInstruction(target);
	}

	@Override
	public void writeStartDocument() throws XMLStreamException {
		this.delegate.writeStartDocument();
	}

	@Override
	public void writeStartDocument(String encoding, String ver) throws XMLStreamException {
		this.delegate.writeStartDocument(encoding, ver);
	}

	@Override
	public void writeStartDocument(String ver) throws XMLStreamException {
		this.delegate.writeStartDocument(ver);
	}

	@Override
	public void writeStartElement(String prefix, String local, String uri) throws XMLStreamException {
		this.delegate.writeStartElement(prefix, local, uri);
	}

	@Override
	public void writeStartElement(String uri, String local) throws XMLStreamException {
		this.delegate.writeStartElement(uri, local);
	}

	@Override
	public void writeStartElement(String local) throws XMLStreamException {
		this.delegate.writeStartElement(local);
	}
}
