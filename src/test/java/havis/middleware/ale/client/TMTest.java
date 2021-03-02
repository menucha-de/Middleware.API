package havis.middleware.ale.client;

import havis.middleware.ale.base.exception.DuplicateNameException;
import havis.middleware.ale.base.exception.ImplementationException;
import havis.middleware.ale.base.exception.InUseException;
import havis.middleware.ale.base.exception.NoSuchNameException;
import havis.middleware.ale.base.exception.SecurityException;
import havis.middleware.ale.base.exception.ValidationException;
import havis.middleware.ale.service.tm.ArrayOfString;
import havis.middleware.ale.service.tm.ALETMService;
import havis.middleware.ale.service.tm.ALETMServicePortType;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.ws.BindingProvider;

import mockit.Deencapsulation;
import mockit.Mocked;
import mockit.NonStrictExpectations;
import mockit.Verifications;

import org.junit.Assert;
import org.junit.Test;

public class TMTest {
	private TM createTM(final ALETMService service, final ALETMServicePortType client, final URL url) {
		new NonStrictExpectations() {
			{
				service.getALETMServicePort();
				result = client;
			}
		};
		if (url != null)
			return new TM(url);
		else
			return new TM();
	}
	
	@Test
	public void tm(final @Mocked ALETMService service, final @Mocked ALETMServicePortType client) throws Exception {
		TM tm = createTM(service, client, null);
		Assert.assertSame(client, Deencapsulation.getField(tm, "client"));
	}
	@Test
	public void tmWithUrl(final @Mocked ALETMService service, final @Mocked ALETMServicePortType client, final @Mocked URL url) throws Exception {
		TM tm = createTM(service, client, url);
		Assert.assertSame(client, Deencapsulation.getField(tm, "client"));
	}
	@Test
	public void tmWithClient(final @Mocked ALETMService service, final @Mocked ALETMServicePortType client) throws Exception {
		TM tm = new TM(client);
		Assert.assertSame(client, Deencapsulation.getField(tm, "client"));
	}
	
	@Test
	public <Client extends ALETMServicePortType & BindingProvider> void setEndpoint(final @Mocked Client client) {
		final Map<String, Object> requestContext = new HashMap<String, Object>();
		final String endpoint = "/test";
		new NonStrictExpectations() {
			{
				client.getRequestContext();
				result = requestContext;
			}
		};

		TM tm = new TM(client);
		tm.setEndpoint(endpoint);
		Assert.assertEquals(endpoint, requestContext.get(BindingProvider.ENDPOINT_ADDRESS_PROPERTY));
	}
	
	@Test
	public void define(final @Mocked ALETMService service, final @Mocked ALETMServicePortType client, final @Mocked TMSpec spec) throws Exception {
		TM tm = createTM(service, client, null);
		final String name = "tm";
		
		tm.define(name, spec);
		
		new Verifications() {
			{
				DefineTMSpec parms;
				client.defineTMSpec(parms = withCapture());
				
				Assert.assertSame(spec, parms.getSpec());
				Assert.assertEquals(name, parms.getSpecName());
			}
		};
	}
	
	private void defineWithException(final ALETMService service, final ALETMServicePortType client, Exception fault, final Exception response) throws Exception {
		TM tm = createTM(service, client, null);
		new NonStrictExpectations() {
			{
				client.defineTMSpec(this.<DefineTMSpec> withNotNull());
				result = response;
			}
		};

		try {
			tm.define(null, null);
			Assert.fail("Expected " + fault.getClass().getSimpleName());
		} catch (Exception e) {
			Assert.assertSame(fault, e);
		}
	}
	
	@Test
	public void defineWithImplementationException(final @Mocked ALETMService service, final @Mocked ALETMServicePortType client) throws Exception {
		ImplementationException exception = new ImplementationException();
		defineWithException(service, client, exception, new ImplementationExceptionResponse("Exception", exception));
	}
	@Test
	public void defineWithDuplicateNameException(final @Mocked ALETMService service, final @Mocked ALETMServicePortType client) throws Exception {
		DuplicateNameException exception = new DuplicateNameException();
		defineWithException(service, client, exception, new DuplicateNameExceptionResponse("Exception", exception));
	}
	@Test
	public void defineWithVSecurityException(final @Mocked ALETMService service, final @Mocked ALETMServicePortType client) throws Exception {
		SecurityException exception = new SecurityException();
		defineWithException(service, client, exception, new SecurityExceptionResponse("Exception", exception));
	}
	@Test
	public void defineWithTMSpecValidationException(final @Mocked ALETMService service, final @Mocked ALETMServicePortType client) throws Exception {
		ValidationException exception = new ValidationException();
		defineWithException(service, client, exception, new TMSpecValidationExceptionResponse("Exception", exception));
	}
	
	@Test
	public void undefine(final @Mocked ALETMService service, final @Mocked ALETMServicePortType client) throws Exception {
		TM tm = createTM(service, client, null);
		final String name = "tm";
		
		tm.undefine(name);
		
		new Verifications() {
			{
				UndefineTMSpec parms;
				client.undefineTMSpec(parms = withCapture());
				
				Assert.assertEquals(name, parms.getSpecName());
			}
		};
	}
	
	private void undefineWithException(final ALETMService service, final ALETMServicePortType client, Exception fault, final Exception response) throws Exception {
		TM tm = createTM(service, client, null);
		new NonStrictExpectations() {
			{
				client.undefineTMSpec(this.<UndefineTMSpec> withNotNull());
				result = response;
			}
		};

		try {
			tm.undefine(null);
			Assert.fail("Expected " + fault.getClass().getSimpleName());
		} catch (Exception e) {
			Assert.assertSame(fault, e);
		}
	}
	
	@Test
	public void undefineWithImplementationException(final @Mocked ALETMService service, final @Mocked ALETMServicePortType client) throws Exception {
		ImplementationException exception = new ImplementationException();
		undefineWithException(service, client, exception, new ImplementationExceptionResponse("Exception", exception));
	}
	@Test
	public void undefineWithInUseException(final @Mocked ALETMService service, final @Mocked ALETMServicePortType client) throws Exception {
		InUseException exception = new InUseException();
		undefineWithException(service, client, exception, new InUseExceptionResponse("Exception", exception));
	}
	@Test
	public void undefineWithNoSuchNameException(final @Mocked ALETMService service, final @Mocked ALETMServicePortType client) throws Exception {
		NoSuchNameException exception = new NoSuchNameException();
		undefineWithException(service, client, exception, new NoSuchNameExceptionResponse("Exception", exception));
	}
	@Test
	public void undefineWithSecurityException(final @Mocked ALETMService service, final @Mocked ALETMServicePortType client) throws Exception {
		SecurityException exception = new SecurityException();
		undefineWithException(service, client, exception, new SecurityExceptionResponse("Exception", exception));
	}
	
	@Test
	public void getSpec(final @Mocked ALETMService service, final @Mocked ALETMServicePortType client, final @Mocked TMSpec expected) throws Exception {
		new NonStrictExpectations() {
			{
				client.getTMSpec(this.<GetTMSpec>withNotNull());
				result = expected;
			}
		};
		TM tm = createTM(service, client, null);
		final String name = "tm";
		
		final TMSpec actual = tm.getSpec(name);
		
		new Verifications() {
			{
				GetTMSpec parms;
				client.getTMSpec(parms = withCapture());
				
				Assert.assertEquals(name, parms.getSpecName());
				Assert.assertSame(expected, actual);
			}
		};
	}
	
	private void getSpecWithException(final ALETMService service, final ALETMServicePortType client, Exception fault, final Exception response) throws Exception {
		TM tm = createTM(service, client, null);
		new NonStrictExpectations() {
			{
				client.getTMSpec(this.<GetTMSpec> withNotNull());
				result = response;
			}
		};

		try {
			tm.getSpec(null);
			Assert.fail("Expected " + fault.getClass().getSimpleName());
		} catch (Exception e) {
			Assert.assertSame(fault, e);
		}
	}
	
	@Test
	public void getSpecWithImplementationException(final @Mocked ALETMService service, final @Mocked ALETMServicePortType client) throws Exception {
		ImplementationException exception = new ImplementationException();
		getSpecWithException(service, client, exception, new ImplementationExceptionResponse("Exception", exception));
	}
	@Test
	public void getSpecWithNoSuchNameException(final @Mocked ALETMService service, final @Mocked ALETMServicePortType client) throws Exception {
		NoSuchNameException exception = new NoSuchNameException();
		getSpecWithException(service, client, exception, new NoSuchNameExceptionResponse("Exception", exception));
	}
	@Test
	public void getSpecWithSecurityException(final @Mocked ALETMService service, final @Mocked ALETMServicePortType client) throws Exception {
		SecurityException exception = new SecurityException();
		getSpecWithException(service, client, exception, new SecurityExceptionResponse("Exception", exception));
	}
	
	@Test
	public void getNames(final @Mocked ALETMService service, final @Mocked ALETMServicePortType client, final @Mocked ArrayOfString string) throws Exception {
		new NonStrictExpectations() {
			{
				client.getTMSpecNames(this.<EmptyParms>withNotNull());
				result = string;
			}
		};
		TM tm = createTM(service, client, null);
		
		final List<String> list = tm.getNames();
		
		new Verifications() {
			{
				client.getTMSpecNames(this.<EmptyParms>withNotNull());
				times = 1;
				string.getString();
				times = 1;
				
				Assert.assertEquals(string.getString(), list);

			}
		};
	}
	
	@Test
	public void getNamesWithNull(final @Mocked ALETMService service, final @Mocked ALETMServicePortType client, final @Mocked ArrayOfString string) throws Exception {
		new NonStrictExpectations() {
			{
				client.getTMSpecNames(this.<EmptyParms>withNotNull());
				result = null;
			}
		};
		TM tm = createTM(service, client, null);
		
		final List<String> list = tm.getNames();
		
		new Verifications() {
			{
				client.getTMSpecNames(this.<EmptyParms>withNotNull());
				times = 1;
				string.getString();
				times = 0;
				
				Assert.assertNull(list);

			}
		};
	}
	
	private void getNamesWithException(final ALETMService service, final ALETMServicePortType client, Exception fault, final Exception response) throws Exception {
		TM tm = createTM(service, client, null);
		new NonStrictExpectations() {
			{
				client.getTMSpecNames(this.<EmptyParms> withNotNull());
				result = response;
			}
		};

		try {
			tm.getNames();
			Assert.fail("Expected " + fault.getClass().getSimpleName());
		} catch (Exception e) {
			Assert.assertSame(fault, e);
		}
	}
	
	@Test
	public void getNamesWithImplementationException(final @Mocked ALETMService service, final @Mocked ALETMServicePortType client) throws Exception {
		ImplementationException exception = new ImplementationException();
		getNamesWithException(service, client, exception, new ImplementationExceptionResponse("Exception", exception));
	}
	@Test
	public void getNamesWithSecurityException(final @Mocked ALETMService service, final @Mocked ALETMServicePortType client) throws Exception {
		SecurityException exception = new SecurityException();
		getNamesWithException(service, client, exception, new SecurityExceptionResponse("Exception", exception));
	}
	
	@Test
	public void getStandardVersion(final @Mocked ALETMService service, final @Mocked ALETMServicePortType client, final @Mocked String version) throws Exception {
		new NonStrictExpectations() {
			{
				client.getStandardVersion(this.<EmptyParms>withNotNull());
				result = version;
			}
		};
		TM tm = createTM(service, client, null);
		
		final String result = tm.getStandardVersion();
		
		new Verifications() {
			{
				client.getStandardVersion(this.<EmptyParms>withNotNull());
				times = 1;
				
				Assert.assertEquals(version,result);

			}
		};
	}
	
	private void getStandardVersionWithException(final ALETMService service, final ALETMServicePortType client, Exception fault, final Exception response) throws Exception {
		TM tm = createTM(service, client, null);
		new NonStrictExpectations() {
			{
				client.getStandardVersion(this.<EmptyParms> withNotNull());
				result = response;
			}
		};

		try {
			tm.getStandardVersion();
			Assert.fail("Expected " + fault.getClass().getSimpleName());
		} catch (Exception e) {
			Assert.assertSame(fault, e);
		}
	}
	
	@Test
	public void getStandardVersionWithImplementationException(final @Mocked ALETMService service, final @Mocked ALETMServicePortType client) throws Exception {
		ImplementationException exception = new ImplementationException();
		getStandardVersionWithException(service, client, exception, new ImplementationExceptionResponse("Exception", exception));
	}
	
	@Test
	public void  getVendorVersion(final @Mocked ALETMService service, final @Mocked ALETMServicePortType client, final @Mocked String version) throws Exception {
		new NonStrictExpectations() {
			{
				client. getVendorVersion(this.<EmptyParms>withNotNull());
				result = version;
			}
		};
		TM tm = createTM(service, client, null);
		
		final String result = tm. getVendorVersion();
		
		new Verifications() {
			{
				client. getVendorVersion(this.<EmptyParms>withNotNull());
				times = 1;
				
				Assert.assertEquals(version,result);

			}
		};
	}
	
	private void getVendorVersionWithException(final ALETMService service, final ALETMServicePortType client, Exception fault, final Exception response) throws Exception {
		TM tm = createTM(service, client, null);
		new NonStrictExpectations() {
			{
				client.getVendorVersion(this.<EmptyParms> withNotNull());
				result = response;
			}
		};

		try {
			tm.getVendorVersion();
			Assert.fail("Expected " + fault.getClass().getSimpleName());
		} catch (Exception e) {
			Assert.assertSame(fault, e);
		}
	}
	
	@Test
	public void getVendorVersionWithImplementationException(final @Mocked ALETMService service, final @Mocked ALETMServicePortType client) throws Exception {
		ImplementationException exception = new ImplementationException();
		getVendorVersionWithException(service, client, exception, new ImplementationExceptionResponse("Exception", exception));
	}
}
