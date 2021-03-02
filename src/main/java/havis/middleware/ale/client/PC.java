package havis.middleware.ale.client;

import havis.middleware.ale.base.exception.DuplicateNameException;
import havis.middleware.ale.base.exception.DuplicateSubscriptionException;
import havis.middleware.ale.base.exception.ImplementationException;
import havis.middleware.ale.base.exception.InvalidURIException;
import havis.middleware.ale.base.exception.NoSuchNameException;
import havis.middleware.ale.base.exception.NoSuchSubscriberException;
import havis.middleware.ale.base.exception.SecurityException;
import havis.middleware.ale.base.exception.ValidationException;
import havis.middleware.ale.service.pc.ALEPCService;
import havis.middleware.ale.service.pc.ALEPCServicePortType;
import havis.middleware.ale.service.pc.ArrayOfString;
import havis.middleware.ale.service.pc.Define;
import havis.middleware.ale.service.pc.DuplicateNameExceptionResponse;
import havis.middleware.ale.service.pc.DuplicateSubscriptionExceptionResponse;
import havis.middleware.ale.service.pc.EmptyParms;
import havis.middleware.ale.service.pc.Execute;
import havis.middleware.ale.service.pc.ExecuteResult;
import havis.middleware.ale.service.pc.GetPCSpec;
import havis.middleware.ale.service.pc.GetSubscribers;
import havis.middleware.ale.service.pc.Immediate;
import havis.middleware.ale.service.pc.ImplementationExceptionResponse;
import havis.middleware.ale.service.pc.InvalidURIExceptionResponse;
import havis.middleware.ale.service.pc.NoSuchNameExceptionResponse;
import havis.middleware.ale.service.pc.NoSuchSubscriberExceptionResponse;
import havis.middleware.ale.service.pc.PCOpReport;
import havis.middleware.ale.service.pc.PCOpSpec;
import havis.middleware.ale.service.pc.PCOpSpecs;
import havis.middleware.ale.service.pc.PCReports;
import havis.middleware.ale.service.pc.PCSpec;
import havis.middleware.ale.service.pc.PCSpecValidationExceptionResponse;
import havis.middleware.ale.service.pc.Poll;
import havis.middleware.ale.service.pc.SecurityExceptionResponse;
import havis.middleware.ale.service.pc.Subscribe;
import havis.middleware.ale.service.pc.Undefine;
import havis.middleware.ale.service.pc.Unsubscribe;

import java.net.URL;
import java.util.List;

import javax.xml.ws.BindingProvider;
import javax.xml.ws.WebServiceClient;

public class PC {

	private ALEPCServicePortType client;

	public PC() {
        ALEPCService service = new ALEPCService(ALEPCService.class.getClassLoader().getResource(
                ALEPCService.class.getAnnotation(WebServiceClient.class).wsdlLocation()));
		client = service.getALEPCServicePort();
	}

	public PC(URL wsdlLocation) {
		ALEPCService service = new ALEPCService(wsdlLocation);
		client = service.getALEPCServicePort();
	}

	public PC(ALEPCServicePortType client) {
		this.client = client;
	}

	public void setEndpoint(String endpoint) {
		((BindingProvider) client).getRequestContext().put(
				BindingProvider.ENDPOINT_ADDRESS_PROPERTY, endpoint);
	}

	public void define(final String _name, final PCSpec _spec)
			throws DuplicateNameException, ImplementationException,
			ValidationException, SecurityException {
		try {
			client.define(new Define() {
				{
					specName = _name;
					spec = _spec;
				}
			});
		} catch (DuplicateNameExceptionResponse e) {
			throw e.getFaultInfo();
		} catch (ImplementationExceptionResponse e) {
			throw e.getFaultInfo();
		} catch (PCSpecValidationExceptionResponse e) {
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

	public PCSpec getSpec(final String _name) throws ImplementationException,
			NoSuchNameException, SecurityException {
		try {
			return client.getPCSpec(new GetPCSpec() {
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
			ArrayOfString string = client.getPCSpecNames(new EmptyParms());
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

	public PCReports poll(final String _name) throws ImplementationException,
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

	public PCReports immediate(final PCSpec _spec)
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
		} catch (PCSpecValidationExceptionResponse e) {
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

	public List<PCOpReport> execute(final List<PCOpSpec> _list)
			throws ImplementationException, ValidationException,
			SecurityException {
		try {
			ExecuteResult result = client.execute(new Execute() {
				{
					opSpecs = new PCOpSpecs() {
						{
							opSpec = _list;
						}
					};
				}
			});
			return result != null ? result.getOpReports().getOpReport() : null;
		} catch (ImplementationExceptionResponse e) {
			throw e.getFaultInfo();
		} catch (PCSpecValidationExceptionResponse e) {
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