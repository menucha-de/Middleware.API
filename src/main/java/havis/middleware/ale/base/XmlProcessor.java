package havis.middleware.ale.base;

import havis.transport.DelegatingMarshaller;

import java.io.OutputStream;
import java.io.Writer;
import java.lang.annotation.Annotation;

import javax.ws.rs.core.MediaType;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import org.jboss.resteasy.annotations.DecorateTypes;
import org.jboss.resteasy.spi.interception.DecoratorProcessor;

/**
 * Processor to pre-process XML of reports
 */
@DecorateTypes({ "text/*+xml", "application/*+xml" })
public class XmlProcessor implements DecoratorProcessor<Marshaller, ProcessXml> {

	@Override
	public Marshaller decorate(Marshaller target, ProcessXml annotation, @SuppressWarnings("rawtypes") Class type, Annotation[] annotations, MediaType mediaType) {
		return new DelegatingMarshaller(target) {
			@Override
			public void marshal(Object jaxbElement, OutputStream os) throws JAXBException {
				super.marshal(jaxbElement, NamespaceXMLStreamWriter.create(os));
			}

			@Override
			public void marshal(Object jaxbElement, Writer writer) throws JAXBException {
				super.marshal(jaxbElement, NamespaceXMLStreamWriter.create(writer));
			}
		};
	}

}
