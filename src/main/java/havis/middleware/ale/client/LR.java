package havis.middleware.ale.client;

import havis.middleware.ale.base.exception.DuplicateNameException;
import havis.middleware.ale.base.exception.ImmutableReaderException;
import havis.middleware.ale.base.exception.ImplementationException;
import havis.middleware.ale.base.exception.InUseException;
import havis.middleware.ale.base.exception.NoSuchNameException;
import havis.middleware.ale.base.exception.NonCompositeReaderException;
import havis.middleware.ale.base.exception.ReaderLoopException;
import havis.middleware.ale.base.exception.SecurityException;
import havis.middleware.ale.base.exception.ValidationException;
import havis.middleware.ale.service.lr.ALELRService;
import havis.middleware.ale.service.lr.ALELRServicePortType;
import havis.middleware.ale.service.lr.AddReaders;
import havis.middleware.ale.service.lr.ArrayOfString;
import havis.middleware.ale.service.lr.Define;
import havis.middleware.ale.service.lr.DuplicateNameExceptionResponse;
import havis.middleware.ale.service.lr.EmptyParms;
import havis.middleware.ale.service.lr.GetLRSpec;
import havis.middleware.ale.service.lr.GetPropertyValue;
import havis.middleware.ale.service.lr.ImmutableReaderExceptionResponse;
import havis.middleware.ale.service.lr.ImplementationExceptionResponse;
import havis.middleware.ale.service.lr.InUseExceptionResponse;
import havis.middleware.ale.service.lr.LRProperty;
import havis.middleware.ale.service.lr.LRSpec;
import havis.middleware.ale.service.lr.NoSuchNameExceptionResponse;
import havis.middleware.ale.service.lr.NonCompositeReaderExceptionResponse;
import havis.middleware.ale.service.lr.ReaderLoopExceptionResponse;
import havis.middleware.ale.service.lr.RemoveReaders;
import havis.middleware.ale.service.lr.SecurityExceptionResponse;
import havis.middleware.ale.service.lr.SetProperties;
import havis.middleware.ale.service.lr.SetReaders;
import havis.middleware.ale.service.lr.Undefine;
import havis.middleware.ale.service.lr.Update;
import havis.middleware.ale.service.lr.ValidationExceptionResponse;

import java.net.URL;
import java.util.List;

import javax.xml.ws.BindingProvider;
import javax.xml.ws.WebServiceClient;

public class LR {

	private ALELRServicePortType client;

	public LR() {
        ALELRService service = new ALELRService(ALELRService.class.getClassLoader().getResource(
                ALELRService.class.getAnnotation(WebServiceClient.class).wsdlLocation()));
		client = service.getALELRServicePort();
	}

	public LR(URL wsdlLocation) {
		ALELRService service = new ALELRService(wsdlLocation);
		client = service.getALELRServicePort();
	}

	public LR(ALELRServicePortType client) {
		this.client = client;
	}

	public void setEndpoint(String endpoint) {
		((BindingProvider) client).getRequestContext().put(
				BindingProvider.ENDPOINT_ADDRESS_PROPERTY, endpoint);
	}

	public void define(final String _name, final LRSpec _spec)
			throws DuplicateNameException, ImplementationException,
			SecurityException, ValidationException {
		try {
			client.define(new Define() {
				{
					name = _name;
					spec = _spec;
				}
			});
		} catch (DuplicateNameExceptionResponse e) {
			throw e.getFaultInfo();
		} catch (ImplementationExceptionResponse e) {
			throw e.getFaultInfo();
		} catch (SecurityExceptionResponse e) {
			throw e.getFaultInfo();
		} catch (ValidationExceptionResponse e) {
			throw e.getFaultInfo();
		}
	}

	public void undefine(final String _name) throws ImmutableReaderException,
			ImplementationException, InUseException, NoSuchNameException,
			SecurityException {
		try {
			client.undefine(new Undefine() {
				{
					name = _name;
				}
			});
		} catch (ImmutableReaderExceptionResponse e) {
			throw e.getFaultInfo();
		} catch (ImplementationExceptionResponse e) {
			throw e.getFaultInfo();
		} catch (InUseExceptionResponse e) {
			throw e.getFaultInfo();
		} catch (NoSuchNameExceptionResponse e) {
			throw e.getFaultInfo();
		} catch (SecurityExceptionResponse e) {
			throw e.getFaultInfo();
		}
	}

	public LRSpec getSpec(final String _name) throws ImplementationException,
			NoSuchNameException, SecurityException {
		try {
			return client.getLRSpec(new GetLRSpec() {
				{
					name = _name;
				}
			});
		} catch (ImplementationExceptionResponse e) {
			throw e.getFaultInfo();
		} catch (NoSuchNameExceptionResponse e) {
			throw e.getFaultInfo();
		} catch (SecurityExceptionResponse e) {
			throw e.getFaultInfo();
		}
	}

	public List<String> getNames() throws ImplementationException,
			SecurityException {
		try {
			ArrayOfString string = client
					.getLogicalReaderNames(new EmptyParms());
			return string != null ? string.getString() : null;
		} catch (ImplementationExceptionResponse e) {
			throw e.getFaultInfo();
		} catch (SecurityExceptionResponse e) {
			throw e.getFaultInfo();
		}
	}

	public void addReaders(final String _name, final List<String> _list)
			throws ImmutableReaderException, ImplementationException,
			InUseException, NoSuchNameException, NonCompositeReaderException,
			ReaderLoopException, SecurityException, ValidationException {
		try {
			client.addReaders(new AddReaders() {
				{
					name = _name;
					readers = new Readers() {
						{
							reader = _list;
						}
					};
				}
			});
		} catch (ImmutableReaderExceptionResponse e) {
			throw e.getFaultInfo();
		} catch (ImplementationExceptionResponse e) {
			throw e.getFaultInfo();
		} catch (InUseExceptionResponse e) {
			throw e.getFaultInfo();
		} catch (NoSuchNameExceptionResponse e) {
			throw e.getFaultInfo();
		} catch (NonCompositeReaderExceptionResponse e) {
			throw e.getFaultInfo();
		} catch (ReaderLoopExceptionResponse e) {
			throw e.getFaultInfo();
		} catch (SecurityExceptionResponse e) {
			throw e.getFaultInfo();
		} catch (ValidationExceptionResponse e) {
			throw e.getFaultInfo();
		}
	}

	public void setReaders(final String _name, final List<String> _list)
			throws ImmutableReaderException, ImplementationException,
			InUseException, NoSuchNameException, NonCompositeReaderException,
			ReaderLoopException, SecurityException, ValidationException {
		try {
			client.setReaders(new SetReaders() {
				{
					name = _name;
					readers = new Readers() {
						{
							reader = _list;
						}
					};
				}
			});
		} catch (ImmutableReaderExceptionResponse e) {
			throw e.getFaultInfo();
		} catch (ImplementationExceptionResponse e) {
			throw e.getFaultInfo();
		} catch (InUseExceptionResponse e) {
			throw e.getFaultInfo();
		} catch (NoSuchNameExceptionResponse e) {
			throw e.getFaultInfo();
		} catch (NonCompositeReaderExceptionResponse e) {
			throw e.getFaultInfo();
		} catch (ReaderLoopExceptionResponse e) {
			throw e.getFaultInfo();
		} catch (SecurityExceptionResponse e) {
			throw e.getFaultInfo();
		} catch (ValidationExceptionResponse e) {
			throw e.getFaultInfo();
		}
	}

	public void removeReaders(final String _name, final List<String> _list)
			throws ImmutableReaderException, ImplementationException,
			InUseException, NoSuchNameException, NonCompositeReaderException,
			SecurityException {
		try {
			client.removeReaders(new RemoveReaders() {
				{
					name = _name;
					readers = new Readers() {
						{
							reader = _list;
						}
					};
				}
			});
		} catch (ImmutableReaderExceptionResponse e) {
			throw e.getFaultInfo();
		} catch (ImplementationExceptionResponse e) {
			throw e.getFaultInfo();
		} catch (InUseExceptionResponse e) {
			throw e.getFaultInfo();
		} catch (NoSuchNameExceptionResponse e) {
			throw e.getFaultInfo();
		} catch (NonCompositeReaderExceptionResponse e) {
			throw e.getFaultInfo();
		} catch (SecurityExceptionResponse e) {
			throw e.getFaultInfo();
		}
	}

	public void setProperties(final String _name,
			final List<LRProperty> _property) throws ImmutableReaderException,
			ImplementationException, InUseException, NoSuchNameException,
			SecurityException, ValidationException {
		try {
			client.setProperties(new SetProperties() {
				{
					name = _name;
					properties = new Properties() {
						{
							property = _property;
						}
					};
				}
			});
		} catch (ImmutableReaderExceptionResponse e) {
			throw e.getFaultInfo();
		} catch (ImplementationExceptionResponse e) {
			throw e.getFaultInfo();
		} catch (InUseExceptionResponse e) {
			throw e.getFaultInfo();
		} catch (NoSuchNameExceptionResponse e) {
			throw e.getFaultInfo();
		} catch (SecurityExceptionResponse e) {
			throw e.getFaultInfo();
		} catch (ValidationExceptionResponse e) {
			throw e.getFaultInfo();
		}
	}

	public String getPropertyValue(final String _name, final String _property)
			throws ImplementationException, NoSuchNameException,
			SecurityException {
		try {
			return client.getPropertyValue(new GetPropertyValue() {
				{
					name = _name;
					propertyName = _property;
				}
			});
		} catch (ImplementationExceptionResponse e) {
			throw e.getFaultInfo();
		} catch (NoSuchNameExceptionResponse e) {
			throw e.getFaultInfo();
		} catch (SecurityExceptionResponse e) {
			throw e.getFaultInfo();
		}
	}

	public void update(final String _name, final LRSpec _spec)
			throws ImmutableReaderException, ImplementationException,
			InUseException, NoSuchNameException, ReaderLoopException,
			SecurityException, ValidationException {
		try {
			client.update(new Update() {
				{
					name = _name;
					spec = _spec;
				}
			});
		} catch (ImmutableReaderExceptionResponse e) {
			throw e.getFaultInfo();
		} catch (ImplementationExceptionResponse e) {
			throw e.getFaultInfo();
		} catch (InUseExceptionResponse e) {
			throw e.getFaultInfo();
		} catch (NoSuchNameExceptionResponse e) {
			throw e.getFaultInfo();
		} catch (ReaderLoopExceptionResponse e) {
			throw e.getFaultInfo();
		} catch (SecurityExceptionResponse e) {
			throw e.getFaultInfo();
		} catch (ValidationExceptionResponse e) {
			throw e.getFaultInfo();
		}
	}

	public String getStandardVersion() throws ImplementationException {
		try {
			return client.getStandardVersion(new EmptyParms());
		} catch (ImplementationExceptionResponse e) {
			throw e.getFaultInfo();
		}
	}

	public String getVendorVersion() throws ImplementationException {
		try {
			return client.getVendorVersion(new EmptyParms());
		} catch (ImplementationExceptionResponse e) {
			throw e.getFaultInfo();
		}
	}
}