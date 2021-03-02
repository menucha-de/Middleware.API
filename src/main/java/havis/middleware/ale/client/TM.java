package havis.middleware.ale.client;

import havis.middleware.ale.base.exception.DuplicateNameException;
import havis.middleware.ale.base.exception.ImplementationException;
import havis.middleware.ale.base.exception.InUseException;
import havis.middleware.ale.base.exception.NoSuchNameException;
import havis.middleware.ale.base.exception.SecurityException;
import havis.middleware.ale.base.exception.ValidationException;
import havis.middleware.ale.service.tm.ALETMService;
import havis.middleware.ale.service.tm.ALETMServicePortType;
import havis.middleware.ale.service.tm.ArrayOfString;
import havis.middleware.ale.service.tm.DefineTMSpec;
import havis.middleware.ale.service.tm.DuplicateNameExceptionResponse;
import havis.middleware.ale.service.tm.EmptyParms;
import havis.middleware.ale.service.tm.GetTMSpec;
import havis.middleware.ale.service.tm.ImplementationExceptionResponse;
import havis.middleware.ale.service.tm.InUseExceptionResponse;
import havis.middleware.ale.service.tm.NoSuchNameExceptionResponse;
import havis.middleware.ale.service.tm.SecurityExceptionResponse;
import havis.middleware.ale.service.tm.TMSpec;
import havis.middleware.ale.service.tm.TMSpecValidationExceptionResponse;
import havis.middleware.ale.service.tm.UndefineTMSpec;

import java.net.URL;
import java.util.List;

import javax.xml.ws.BindingProvider;
import javax.xml.ws.WebServiceClient;

public class TM {

	private ALETMServicePortType client;

	public TM() {
	    ALETMService service = new ALETMService(ALETMService.class.getClassLoader().getResource(
	            ALETMService.class.getAnnotation(WebServiceClient.class).wsdlLocation()));
		client = service.getALETMServicePort();
	}

	public TM(URL wsdlLocation) {
		ALETMService service = new ALETMService(wsdlLocation);
		client = service.getALETMServicePort();
	}

	public TM(ALETMServicePortType client) {
		this.client = client;
	}

	public void setEndpoint(String endpoint) {
		((BindingProvider) client).getRequestContext().put(
				BindingProvider.ENDPOINT_ADDRESS_PROPERTY, endpoint);
	}

	public void define(final String _name, final TMSpec _spec)
			throws DuplicateNameException, ImplementationException,
			SecurityException, ValidationException {
		try {
			client.defineTMSpec(new DefineTMSpec() {
				{
					specName = _name;
					spec = _spec;
				}
			});
		} catch (DuplicateNameExceptionResponse e) {
			throw e.getFaultInfo();
		} catch (ImplementationExceptionResponse e) {
			throw e.getFaultInfo();
		} catch (SecurityExceptionResponse e) {
			throw e.getFaultInfo();
		} catch (TMSpecValidationExceptionResponse e) {
			throw e.getFaultInfo();
		}
	}

	public void undefine(final String _name) throws ImplementationException,
			InUseException, NoSuchNameException, SecurityException {
		try {
			client.undefineTMSpec(new UndefineTMSpec() {
				{
					specName = _name;
				}
			});
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

	public TMSpec getSpec(final String _name) throws ImplementationException,
			NoSuchNameException, SecurityException {
		try {
			return client.getTMSpec(new GetTMSpec() {
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
			ArrayOfString string = client.getTMSpecNames(new EmptyParms());
			return string != null ? string.getString() : null;
		} catch (ImplementationExceptionResponse e) {
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