package havis.middleware.ale.client;

import havis.middleware.ale.base.exception.DuplicateNameException;
import havis.middleware.ale.base.exception.DuplicateSubscriptionException;
import havis.middleware.ale.base.exception.ImplementationException;
import havis.middleware.ale.base.exception.InvalidURIException;
import havis.middleware.ale.base.exception.NoSuchNameException;
import havis.middleware.ale.base.exception.NoSuchSubscriberException;
import havis.middleware.ale.base.exception.SecurityException;
import havis.middleware.ale.base.exception.ValidationException;
import havis.middleware.ale.service.ec.ALEService;
import havis.middleware.ale.service.ec.ALEServicePortType;
import havis.middleware.ale.service.ec.ArrayOfString;
import havis.middleware.ale.service.ec.Define;
import havis.middleware.ale.service.ec.DuplicateNameExceptionResponse;
import havis.middleware.ale.service.ec.DuplicateSubscriptionExceptionResponse;
import havis.middleware.ale.service.ec.ECReports;
import havis.middleware.ale.service.ec.ECSpec;
import havis.middleware.ale.service.ec.ECSpecValidationExceptionResponse;
import havis.middleware.ale.service.ec.EmptyParms;
import havis.middleware.ale.service.ec.GetECSpec;
import havis.middleware.ale.service.ec.GetSubscribers;
import havis.middleware.ale.service.ec.Immediate;
import havis.middleware.ale.service.ec.ImplementationExceptionResponse;
import havis.middleware.ale.service.ec.InvalidURIExceptionResponse;
import havis.middleware.ale.service.ec.NoSuchNameExceptionResponse;
import havis.middleware.ale.service.ec.NoSuchSubscriberExceptionResponse;
import havis.middleware.ale.service.ec.Poll;
import havis.middleware.ale.service.ec.SecurityExceptionResponse;
import havis.middleware.ale.service.ec.Subscribe;
import havis.middleware.ale.service.ec.Undefine;
import havis.middleware.ale.service.ec.Unsubscribe;

import java.net.URL;
import java.util.List;

import javax.xml.ws.BindingProvider;
import javax.xml.ws.WebServiceClient;

public class EC {

	private ALEServicePortType client;

	public EC() {
        ALEService service = new ALEService(ALEService.class.getClassLoader()
                .getResource(ALEService.class.getAnnotation(WebServiceClient.class).wsdlLocation()));
        client = service.getALEServicePort();
	}

	public EC(URL wsdlLocation) {
		ALEService service = new ALEService(wsdlLocation);
		client = service.getALEServicePort();
	}

	public EC(ALEServicePortType client) {
		this.client = client;
	}

	public void setEndpoint(String endpoint) {
		((BindingProvider) client).getRequestContext().put(
				BindingProvider.ENDPOINT_ADDRESS_PROPERTY, endpoint);
	}

	public void define(final String _name, final ECSpec _spec)
			throws DuplicateNameException, ValidationException,
			ImplementationException, SecurityException {
		try {
			client.define(new Define() {
				{
					specName = _name;
					spec = _spec;
				}
			});
		} catch (DuplicateNameExceptionResponse e) {
			throw e.getFaultInfo();
		} catch (ECSpecValidationExceptionResponse e) {
			throw e.getFaultInfo();
		} catch (ImplementationExceptionResponse e) {
			throw e.getFaultInfo();
		} catch (SecurityExceptionResponse e) {
			throw e.getFaultInfo();
		}
	}

	public void undefine(final String _name) throws ImplementationException,
			NoSuchNameException, SecurityException {
		try {
			client.undefine(new Undefine() {
				{
					specName = _name;
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

	public ECSpec getSpec(final String _name) throws ImplementationException,
			NoSuchNameException, SecurityException {
		try {
			return client.getECSpec(new GetECSpec() {
				{
					specName = _name;
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
			ArrayOfString string = client.getECSpecNames(new EmptyParms());
			if (string != null)
				return string.getString();
			else
				return null;
		} catch (ImplementationExceptionResponse e) {
			throw e.getFaultInfo();
		} catch (SecurityExceptionResponse e) {
			throw e.getFaultInfo();
		}
	}

	public void subscribe(final String _name, final String _uri)
			throws DuplicateSubscriptionException, ImplementationException,
			InvalidURIException, NoSuchNameException, SecurityException {
		try {
			client.subscribe(new Subscribe() {
				{
					specName = _name;
					notificationURI = _uri;
				}
			});
		} catch (DuplicateSubscriptionExceptionResponse e) {
			throw e.getFaultInfo();
		} catch (ImplementationExceptionResponse e) {
			throw e.getFaultInfo();
		} catch (InvalidURIExceptionResponse e) {
			throw e.getFaultInfo();
		} catch (NoSuchNameExceptionResponse e) {
			throw e.getFaultInfo();
		} catch (SecurityExceptionResponse e) {
			throw e.getFaultInfo();
		}
	}

	public void unsubscribe(final String _name, final String _uri)
			throws ImplementationException, InvalidURIException,
			NoSuchNameException, NoSuchSubscriberException, SecurityException {
		try {
			client.unsubscribe(new Unsubscribe() {
				{
					specName = _name;
					notificationURI = _uri;
				}
			});
		} catch (ImplementationExceptionResponse e) {
			throw e.getFaultInfo();
		} catch (InvalidURIExceptionResponse e) {
			throw e.getFaultInfo();
		} catch (NoSuchNameExceptionResponse e) {
			throw e.getFaultInfo();
		} catch (NoSuchSubscriberExceptionResponse e) {
			throw e.getFaultInfo();
		} catch (SecurityExceptionResponse e) {
			throw e.getFaultInfo();
		}
	}

	public ECReports poll(final String _name) throws ImplementationException,
			NoSuchNameException, SecurityException {
		try {
			return client.poll(new Poll() {
				{
					specName = _name;
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

	public ECReports immediate(final ECSpec _spec)
			throws ImplementationException, ValidationException,
			SecurityException {
		try {
			return client.immediate(new Immediate() {
				{
					spec = _spec;
				}
			});
		} catch (ImplementationExceptionResponse e) {
			throw e.getFaultInfo();
		} catch (ECSpecValidationExceptionResponse e) {
			throw e.getFaultInfo();
		} catch (SecurityExceptionResponse e) {
			throw e.getFaultInfo();
		}
	}

	public List<String> getSubscribers(final String _name)
			throws ImplementationException, NoSuchNameException,
			SecurityException {

		try {
			ArrayOfString string = client.getSubscribers(new GetSubscribers() {
				{
					specName = _name;
				}
			});
			return string != null ? string.getString() : null;
		} catch (ImplementationExceptionResponse e) {
			throw e.getFaultInfo();
		} catch (NoSuchNameExceptionResponse e) {
			throw e.getFaultInfo();
		} catch (SecurityExceptionResponse e) {
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