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
import java.util.ArrayList;
import java.util.Arrays;
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

public class LRTest {

	private LR createLR(final ALELRService service, final ALELRServicePortType client, final URL url) {
		new NonStrictExpectations() {
			{
				service.getALELRServicePort();
				result = client;
			}
		};
		if (url != null)
			return new LR(url);
		else
			return new LR();
	}
	
	@Test
	public void lr(final @Mocked ALELRService service, final @Mocked ALELRServicePortType client) throws Exception {
		LR lr = createLR(service, client, null);
		Assert.assertSame(client, Deencapsulation.getField(lr, "client"));
	}
	
	@Test
	public void lrWithUrl(final @Mocked ALELRService service, final @Mocked ALELRServicePortType client, final @Mocked URL url) throws Exception {
		LR lr = createLR(service, client, url);
		Assert.assertSame(client, Deencapsulation.getField(lr, "client"));
	}
	
	@Test
	public void lrWithClient(final @Mocked ALELRServicePortType client) throws Exception {
		LR lr = new LR(client);
		Assert.assertSame(client, Deencapsulation.getField(lr, "client"));
	}
	
	@Test
	public <Client extends ALELRServicePortType & BindingProvider> void setEndpoint(final @Mocked Client client) {
		final Map<String, Object> requestContext = new HashMap<String, Object>();
		final String endpoint = "/test";
		new NonStrictExpectations() {
			{
				client.getRequestContext();
				result = requestContext;
			}
		};

		LR lr = new LR(client);
		lr.setEndpoint(endpoint);
		Assert.assertEquals(endpoint, requestContext.get(BindingProvider.ENDPOINT_ADDRESS_PROPERTY));
	}
	
	@Test
	public void define(final @Mocked ALELRService service, final @Mocked ALELRServicePortType client) throws Exception {
		LR lr = createLR(service, client, null);
		final String name = "lr";
		final LRSpec spec = new LRSpec();
		
		lr.define(name, spec);
		
		new Verifications() {
			{
				Define parms;
				client.define(parms = withCapture());
				
				Assert.assertEquals(name, parms.getName());
				Assert.assertSame(spec, parms.getSpec());
			}
		};
	}
	
	private void defineWithException(final ALELRService service, final ALELRServicePortType client, Exception fault, final Exception response) throws Exception {
		LR lr = createLR(service, client, null);
		new NonStrictExpectations() {
			{
				client.define(this.<Define> withNotNull());
				result = response;
			}
		};

		try {
			lr.define(null, null);
			Assert.fail("Expected " + fault.getClass().getSimpleName());
		} catch (Exception e) {
			Assert.assertSame(fault, e);
		}
	}
	
	@Test
	public void defineWithDuplicateNameException(final @Mocked ALELRService service, final @Mocked ALELRServicePortType client) throws Exception {
		DuplicateNameException exception = new DuplicateNameException();
		defineWithException(service, client, exception, new DuplicateNameExceptionResponse("Exception", exception));
	}
	@Test
	public void defineWithVImplementationException(final @Mocked ALELRService service, final @Mocked ALELRServicePortType client) throws Exception {
		ImplementationException exception = new ImplementationException();
		defineWithException(service, client, exception, new ImplementationExceptionResponse("Exception", exception));
	}
	@Test
	public void defineWithSecurityException(final @Mocked ALELRService service, final @Mocked ALELRServicePortType client) throws Exception {
		SecurityException exception = new SecurityException();
		defineWithException(service, client, exception, new SecurityExceptionResponse("Exception", exception));
	}
	@Test
	public void defineWithValidationException(final @Mocked ALELRService service, final @Mocked ALELRServicePortType client) throws Exception {
		ValidationException exception = new ValidationException();
		defineWithException(service, client, exception, new ValidationExceptionResponse("Exception", exception));
	}	
	
	@Test
	public void undefine(final @Mocked ALELRService service, final @Mocked ALELRServicePortType client) throws Exception {
		LR lr = createLR(service, client, null);
		final String name = "lr";
		
		lr.undefine(name);
		
		new Verifications() {
			{
				Undefine parms;
				client.undefine(parms = withCapture());
				
				Assert.assertEquals(name, parms.getName());
			}
		};
	}
	
	private void undefineWithException(final ALELRService service, final ALELRServicePortType client, Exception fault, final Exception response) throws Exception {
		LR lr = createLR(service, client, null);
		new NonStrictExpectations() {
			{
				client.undefine(this.<Undefine> withNotNull());
				result = response;
			}
		};

		try {
			lr.undefine( null);
			Assert.fail("Expected " + fault.getClass().getSimpleName());
		} catch (Exception e) {
			Assert.assertSame(fault, e);
		}
	}
	
	@Test
	public void undefineWithImmutableReaderException(final @Mocked ALELRService service, final @Mocked ALELRServicePortType client) throws Exception {
		ImmutableReaderException exception = new ImmutableReaderException();
		undefineWithException(service, client, exception, new ImmutableReaderExceptionResponse("Exception", exception));
	}
	@Test
	public void undefineWithImplementationException(final @Mocked ALELRService service, final @Mocked ALELRServicePortType client) throws Exception {
		ImplementationException exception = new ImplementationException();
		undefineWithException(service, client, exception, new ImplementationExceptionResponse("Exception", exception));
	}
	@Test
	public void undefineWithSecurityException(final @Mocked ALELRService service, final @Mocked ALELRServicePortType client) throws Exception {
		SecurityException exception = new SecurityException();
		undefineWithException(service, client, exception, new SecurityExceptionResponse("Exception", exception));
	}
	@Test
	public void undefineWithInUseException(final @Mocked ALELRService service, final @Mocked ALELRServicePortType client) throws Exception {
		InUseException exception = new InUseException();
		undefineWithException(service, client, exception, new InUseExceptionResponse("Exception", exception));
	}
	@Test
	public void undefineWithNoSuchNameException(final @Mocked ALELRService service, final @Mocked ALELRServicePortType client) throws Exception {
		NoSuchNameException exception = new NoSuchNameException();
		undefineWithException(service, client, exception, new NoSuchNameExceptionResponse("Exception", exception));
	}
	
	@Test
	public void getSpec(final @Mocked ALELRService service, final @Mocked ALELRServicePortType client, final @Mocked LRSpec expected) throws Exception {
		new NonStrictExpectations() {
			{
				client.getLRSpec(this.<GetLRSpec>withNotNull());
				result = expected;
			}
		};
		LR lr = createLR(service, client, null);
		final String name = "spec";
		
		final LRSpec actual = lr.getSpec(name);
		
		new Verifications() {
			{
				GetLRSpec parms;
				client.getLRSpec(parms = withCapture());
				
				Assert.assertEquals(name, parms.getName());
				Assert.assertSame(expected, actual);
			}
		};
	}
	
	private void getSpecWithException(final ALELRService service, final ALELRServicePortType client, Exception fault, final Exception response) throws Exception {
		LR lr = createLR(service, client, null);
		new NonStrictExpectations() {
			{
				client.getLRSpec(this.<GetLRSpec> withNotNull());
				result = response;
			}
		};

		try {
			lr.getSpec(null);
			Assert.fail("Expected " + fault.getClass().getSimpleName());
		} catch (Exception e) {
			Assert.assertSame(fault, e);
		}
	}
	
	@Test
	public void getSpecWithImplementationException(final @Mocked ALELRService service, final @Mocked ALELRServicePortType client) throws Exception {
		ImplementationException exception = new ImplementationException();
		getSpecWithException(service, client, exception, new ImplementationExceptionResponse("Exception", exception));
	}
	@Test
	public void getSpecWithSecurityException(final @Mocked ALELRService service, final @Mocked ALELRServicePortType client) throws Exception {
		SecurityException exception = new SecurityException();
		getSpecWithException(service, client, exception, new SecurityExceptionResponse("Exception", exception));
	}
	@Test
	public void getSpecWithNoSuchNameException(final @Mocked ALELRService service, final @Mocked ALELRServicePortType client) throws Exception {
		NoSuchNameException exception = new NoSuchNameException();
		getSpecWithException(service, client, exception, new NoSuchNameExceptionResponse("Exception", exception));
	}
	
	@Test
	public void getNames(final @Mocked ALELRService service, final @Mocked ALELRServicePortType client, final @Mocked ArrayOfString string) throws Exception {
		new NonStrictExpectations() {
			{
				client.getLogicalReaderNames(this.<EmptyParms>withNotNull());
				result = string;
			}
		};
		LR lr = createLR(service, client, null);
		
		final List<String> list = lr.getNames();
		
		new Verifications() {
			{
				client.getLogicalReaderNames(this.<EmptyParms>withNotNull());
				times = 1;
				string.getString();
				times = 1;
				
				Assert.assertEquals(string.getString(), list);
			}
		};
	}
	
	@Test
	public void getNamesWithNull(final @Mocked ALELRService service, final @Mocked ALELRServicePortType client, final @Mocked ArrayOfString string) throws Exception {
		new NonStrictExpectations() {
			{
				client.getLogicalReaderNames(this.<EmptyParms>withNotNull());
				result = null;
			}
		};
		LR lr = createLR(service, client, null);
		
		final List<String> list = lr.getNames();
		
		new Verifications() {
			{
				client.getLogicalReaderNames(this.<EmptyParms>withNotNull());
				times = 1;
				string.getString();
				times = 0;
				
				Assert.assertNull(list);
			}
		};
	}
	
	private void getNamesWithException(final ALELRService service, final ALELRServicePortType client, Exception fault, final Exception response) throws Exception {
		LR lr = createLR(service, client, null);
		new NonStrictExpectations() {
			{
				client.getLogicalReaderNames(this.<EmptyParms> withNotNull());
				result = response;
			}
		};

		try {
			lr.getNames();
			Assert.fail("Expected " + fault.getClass().getSimpleName());
		} catch (Exception e) {
			Assert.assertSame(fault, e);
		}
	}
	
	@Test
	public void getNamesWithImplementationException(final @Mocked ALELRService service, final @Mocked ALELRServicePortType client) throws Exception {
		ImplementationException exception = new ImplementationException();
		getNamesWithException(service, client, exception, new ImplementationExceptionResponse("Exception", exception));
	}
	@Test
	public void getNamesWithSecurityException(final @Mocked ALELRService service, final @Mocked ALELRServicePortType client) throws Exception {
		SecurityException exception = new SecurityException();
		getNamesWithException(service, client, exception, new SecurityExceptionResponse("Exception", exception));
	}
	
	@Test
	public void addReaders(final @Mocked ALELRService service, final @Mocked ALELRServicePortType client) throws Exception {
		LR lr = createLR(service, client, null);
		final String name = "reader";
		final List<String> list = new ArrayList<String>(
				Arrays.asList(name)); 
		
		lr.addReaders(name, list);
		
		new Verifications() {
			{
				AddReaders parms;
				client.addReaders(parms = withCapture());
				times = 1;
				
				
				Assert.assertEquals(name, parms.getName());
				Assert.assertEquals(list, parms.getReaders().getReader());
				Assert.assertEquals(name, parms.getReaders().getReader().get(0));
			}
		};
	}
	
	private void addReadersWithException(final ALELRService service, final ALELRServicePortType client, Exception fault, final Exception response) throws Exception {
		LR lr = createLR(service, client, null);
		new NonStrictExpectations() {
			{
				client.addReaders(this.<AddReaders> withNotNull());
				result = response;
			}
		};

		try {
			lr.addReaders(null,null);
			Assert.fail("Expected " + fault.getClass().getSimpleName());
		} catch (Exception e) {
			Assert.assertSame(fault, e);
		}
	}
	
	@Test
	public void addReadersWithImmutableReaderException(final @Mocked ALELRService service, final @Mocked ALELRServicePortType client) throws Exception {
		ImmutableReaderException exception = new ImmutableReaderException();
		addReadersWithException(service, client, exception, new ImmutableReaderExceptionResponse("Exception", exception));
	}
	@Test
	public void addReadersWithImplementationException(final @Mocked ALELRService service, final @Mocked ALELRServicePortType client) throws Exception {
		ImplementationException exception = new ImplementationException();
		addReadersWithException(service, client, exception, new ImplementationExceptionResponse("Exception", exception));
	}
	@Test
	public void addReadersWithInUseException(final @Mocked ALELRService service, final @Mocked ALELRServicePortType client) throws Exception {
		InUseException exception = new InUseException();
		addReadersWithException(service, client, exception, new InUseExceptionResponse("Exception", exception));
	}
	@Test
	public void addReadersWithNoSuchNameException(final @Mocked ALELRService service, final @Mocked ALELRServicePortType client) throws Exception {
		NoSuchNameException exception = new NoSuchNameException();
		addReadersWithException(service, client, exception, new NoSuchNameExceptionResponse("Exception", exception));
	}
	@Test
	public void addReadersWithNonCompositeReaderException(final @Mocked ALELRService service, final @Mocked ALELRServicePortType client) throws Exception {
		NonCompositeReaderException exception = new NonCompositeReaderException();
		addReadersWithException(service, client, exception, new NonCompositeReaderExceptionResponse("Exception", exception));
	}
	@Test
	public void addReadersWithReaderLoopException(final @Mocked ALELRService service, final @Mocked ALELRServicePortType client) throws Exception {
		ReaderLoopException exception = new ReaderLoopException();
		addReadersWithException(service, client, exception, new ReaderLoopExceptionResponse("Exception", exception));
	}
	@Test
	public void addReadersWithSecurityException(final @Mocked ALELRService service, final @Mocked ALELRServicePortType client) throws Exception {
		SecurityException exception = new SecurityException();
		addReadersWithException(service, client, exception, new SecurityExceptionResponse("Exception", exception));
	}
	@Test
	public void addReadersWithValidationException(final @Mocked ALELRService service, final @Mocked ALELRServicePortType client) throws Exception {
		ValidationException exception = new ValidationException();
		addReadersWithException(service, client, exception, new ValidationExceptionResponse("Exception", exception));
	}
	
	@Test
	public void setReaders(final @Mocked ALELRService service, final @Mocked ALELRServicePortType client) throws Exception {
		LR lr = createLR(service, client, null);
		final String name = "reader";
		final List<String> list = new ArrayList<String>(
				Arrays.asList(name)); 
		
		lr.setReaders(name, list);
		
		new Verifications() {
			{
				SetReaders parms;
				client.setReaders(parms = withCapture());
				times = 1;
				
				
				Assert.assertEquals(name, parms.getName());
				Assert.assertEquals(list, parms.getReaders().getReader());
				Assert.assertEquals(name, parms.getReaders().getReader().get(0));
			}
		};
	}
	
	
	private void setReadersWithException(final ALELRService service, final ALELRServicePortType client, Exception fault, final Exception response) throws Exception {
		LR lr = createLR(service, client, null);
		new NonStrictExpectations() {
			{
				client.setReaders(this.<SetReaders> withNotNull());
				result = response;
			}
		};

		try {
			lr.setReaders(null,null);
			Assert.fail("Expected " + fault.getClass().getSimpleName());
		} catch (Exception e) {
			Assert.assertSame(fault, e);
		}
	}
	
	@Test
	public void setReadersWithImmutableReaderException(final @Mocked ALELRService service, final @Mocked ALELRServicePortType client) throws Exception {
		ImmutableReaderException exception = new ImmutableReaderException();
		setReadersWithException(service, client, exception, new ImmutableReaderExceptionResponse("Exception", exception));
	}
	@Test
	public void setReadersWithImplementationException(final @Mocked ALELRService service, final @Mocked ALELRServicePortType client) throws Exception {
		ImplementationException exception = new ImplementationException();
		setReadersWithException(service, client, exception, new ImplementationExceptionResponse("Exception", exception));
	}
	@Test
	public void setReadersWithInUseException(final @Mocked ALELRService service, final @Mocked ALELRServicePortType client) throws Exception {
		InUseException exception = new InUseException();
		setReadersWithException(service, client, exception, new InUseExceptionResponse("Exception", exception));
	}
	@Test
	public void setReadersWithNoSuchNameException(final @Mocked ALELRService service, final @Mocked ALELRServicePortType client) throws Exception {
		NoSuchNameException exception = new NoSuchNameException();
		setReadersWithException(service, client, exception, new NoSuchNameExceptionResponse("Exception", exception));
	}
	@Test
	public void setReadersWithNonCompositeReaderException(final @Mocked ALELRService service, final @Mocked ALELRServicePortType client) throws Exception {
		NonCompositeReaderException exception = new NonCompositeReaderException();
		setReadersWithException(service, client, exception, new NonCompositeReaderExceptionResponse("Exception", exception));
	}
	@Test
	public void setReadersWithReaderLoopException(final @Mocked ALELRService service, final @Mocked ALELRServicePortType client) throws Exception {
		ReaderLoopException exception = new ReaderLoopException();
		setReadersWithException(service, client, exception, new ReaderLoopExceptionResponse("Exception", exception));
	}
	@Test
	public void setReadersWithSecurityException(final @Mocked ALELRService service, final @Mocked ALELRServicePortType client) throws Exception {
		SecurityException exception = new SecurityException();
		setReadersWithException(service, client, exception, new SecurityExceptionResponse("Exception", exception));
	}
	@Test
	public void setReadersWithValidationException(final @Mocked ALELRService service, final @Mocked ALELRServicePortType client) throws Exception {
		ValidationException exception = new ValidationException();
		setReadersWithException(service, client, exception, new ValidationExceptionResponse("Exception", exception));
	}
	
	@Test
	public void removeReaders(final @Mocked ALELRService service, final @Mocked ALELRServicePortType client) throws Exception {
		LR lr = createLR(service, client, null);
		final String name = "reader";
		final List<String> list = new ArrayList<String>(
				Arrays.asList(name)); 
		
		lr.removeReaders(name, list);
		
		new Verifications() {
			{
				RemoveReaders parms;
				client.removeReaders(parms = withCapture());
				times = 1;
				
				
				Assert.assertEquals(name, parms.getName());
				Assert.assertEquals(list, parms.getReaders().getReader());
				Assert.assertEquals(name, parms.getReaders().getReader().get(0));
			}
		};
	}
	
	private void removeReadersWithException(final ALELRService service, final ALELRServicePortType client, Exception fault, final Exception response) throws Exception {
		LR lr = createLR(service, client, null);
		new NonStrictExpectations() {
			{
				client.removeReaders(this.<RemoveReaders> withNotNull());
				result = response;
			}
		};

		try {
			lr.removeReaders(null,null);
			Assert.fail("Expected " + fault.getClass().getSimpleName());
		} catch (Exception e) {
			Assert.assertSame(fault, e);
		}
	}
	
	@Test
	public void removeReadersWithImmutableReaderException(final @Mocked ALELRService service, final @Mocked ALELRServicePortType client) throws Exception {
		ImmutableReaderException exception = new ImmutableReaderException();
		removeReadersWithException(service, client, exception, new ImmutableReaderExceptionResponse("Exception", exception));
	}
	@Test
	public void removeReadersWithImplementationException(final @Mocked ALELRService service, final @Mocked ALELRServicePortType client) throws Exception {
		ImplementationException exception = new ImplementationException();
		removeReadersWithException(service, client, exception, new ImplementationExceptionResponse("Exception", exception));
	}
	@Test
	public void removeReadersWithInUseException(final @Mocked ALELRService service, final @Mocked ALELRServicePortType client) throws Exception {
		InUseException exception = new InUseException();
		removeReadersWithException(service, client, exception, new InUseExceptionResponse("Exception", exception));
	}
	@Test
	public void removeReadersWithNoSuchNameException(final @Mocked ALELRService service, final @Mocked ALELRServicePortType client) throws Exception {
		NoSuchNameException exception = new NoSuchNameException();
		removeReadersWithException(service, client, exception, new NoSuchNameExceptionResponse("Exception", exception));
	}
	@Test
	public void removeReadersWithNonCompositeReaderException(final @Mocked ALELRService service, final @Mocked ALELRServicePortType client) throws Exception {
		NonCompositeReaderException exception = new NonCompositeReaderException();
		removeReadersWithException(service, client, exception, new NonCompositeReaderExceptionResponse("Exception", exception));
	}
	@Test
	public void removeReadersWithSecurityException(final @Mocked ALELRService service, final @Mocked ALELRServicePortType client) throws Exception {
		SecurityException exception = new SecurityException();
		removeReadersWithException(service, client, exception, new SecurityExceptionResponse("Exception", exception));
	}

	@Test
	public void setProperties(final @Mocked ALELRService service, final @Mocked ALELRServicePortType client) throws Exception {
		LR lr = createLR(service, client, null);
		final String name = "reader";
		final LRProperty property = new LRProperty();
		final List<LRProperty> properties = new ArrayList<LRProperty>(
				Arrays.asList(property)); 
		
		lr.setProperties(name, properties);
		
		new Verifications() {
			{
				SetProperties parms;
				client.setProperties(parms = withCapture());
				times = 1;	
				
				Assert.assertEquals(name, parms.getName());
				Assert.assertEquals(properties, parms.getProperties().getProperty());
				Assert.assertEquals(property, parms.getProperties().getProperty().get(0));
			}
		};
	}
	
	private void setPropertiesWithException(final ALELRService service, final ALELRServicePortType client, Exception fault, final Exception response) throws Exception {
		LR lr = createLR(service, client, null);
		new NonStrictExpectations() {
			{
				client.setProperties(this.<SetProperties> withNotNull());
				result = response;
			}
		};

		try {
			lr.setProperties(null,null);
			Assert.fail("Expected " + fault.getClass().getSimpleName());
		} catch (Exception e) {
			Assert.assertSame(fault, e);
		}
	}
	
	@Test
	public void setPropertiesWithSecurityException(final @Mocked ALELRService service, final @Mocked ALELRServicePortType client) throws Exception {
		SecurityException exception = new SecurityException();
		setPropertiesWithException(service, client, exception, new SecurityExceptionResponse("Exception", exception));
	}
	@Test
	public void setPropertiesWithImmutableReaderException(final @Mocked ALELRService service, final @Mocked ALELRServicePortType client) throws Exception {
		ImmutableReaderException exception = new ImmutableReaderException();
		setPropertiesWithException(service, client, exception, new ImmutableReaderExceptionResponse("Exception", exception));
	}
	@Test
	public void setPropertiesWithImplementationException(final @Mocked ALELRService service, final @Mocked ALELRServicePortType client) throws Exception {
		ImplementationException exception = new ImplementationException();
		setPropertiesWithException(service, client, exception, new ImplementationExceptionResponse("Exception", exception));
	}
	@Test
	public void setPropertiesWithInUseException(final @Mocked ALELRService service, final @Mocked ALELRServicePortType client) throws Exception {
		InUseException exception = new InUseException();
		setPropertiesWithException(service, client, exception, new InUseExceptionResponse("Exception", exception));
	}
	@Test
	public void setPropertiesWithValidationException(final @Mocked ALELRService service, final @Mocked ALELRServicePortType client) throws Exception {
		ValidationException exception = new ValidationException();
		setPropertiesWithException(service, client, exception, new ValidationExceptionResponse("Exception", exception));
	}
	@Test
	public void setPropertiesWithNoSuchNameException(final @Mocked ALELRService service, final @Mocked ALELRServicePortType client) throws Exception {
		NoSuchNameException exception = new NoSuchNameException();
		setPropertiesWithException(service, client, exception, new NoSuchNameExceptionResponse("Exception", exception));
	}
	
	@Test
	public void getPropertyValue(final @Mocked ALELRService service, final @Mocked ALELRServicePortType client, final @Mocked String expected) throws Exception {
		new NonStrictExpectations() {
			{
				client.getPropertyValue(this.<GetPropertyValue>withNotNull());
				result = expected;
			}
		};
		LR lr = createLR(service, client, null);
		final String name = "reader";
		final String property = "property";
		
		final String actual = lr.getPropertyValue(name, property);
		
		new Verifications() {
			{
				GetPropertyValue parms;
				client.getPropertyValue(parms = withCapture());
				times = 1;	
				
				Assert.assertEquals(name, parms.getName());
				Assert.assertEquals(property, parms.getPropertyName());
				Assert.assertEquals(expected, actual);
			}
		};
	}
	
	private void getPropertyValueWithException(final ALELRService service, final ALELRServicePortType client, Exception fault, final Exception response) throws Exception {
		LR lr = createLR(service, client, null);
		new NonStrictExpectations() {
			{
				client.getPropertyValue(this.<GetPropertyValue> withNotNull());
				result = response;
			}
		};

		try {
			lr.getPropertyValue(null,null);
			Assert.fail("Expected " + fault.getClass().getSimpleName());
		} catch (Exception e) {
			Assert.assertSame(fault, e);
		}
	}
	
	@Test
	public void getPropertyValueWithImplementationException(final @Mocked ALELRService service, final @Mocked ALELRServicePortType client) throws Exception {
		ImplementationException exception = new ImplementationException();
		getPropertyValueWithException(service, client, exception, new ImplementationExceptionResponse("Exception", exception));
	}
	@Test
	public void getPropertyValueWithNoSuchNameException(final @Mocked ALELRService service, final @Mocked ALELRServicePortType client) throws Exception {
		NoSuchNameException exception = new NoSuchNameException();
		getPropertyValueWithException(service, client, exception, new NoSuchNameExceptionResponse("Exception", exception));
	}
	@Test
	public void getPropertyValueWithSecurityException(final @Mocked ALELRService service, final @Mocked ALELRServicePortType client) throws Exception {
		SecurityException exception = new SecurityException();
		getPropertyValueWithException(service, client, exception, new SecurityExceptionResponse("Exception", exception));
	}

	@Test
	public void update(final @Mocked ALELRService service, final @Mocked ALELRServicePortType client) throws Exception {
		LR lr = createLR(service, client, null);
		final String name = "reader";
		final LRSpec spec = new LRSpec();
		
		lr.update(name, spec);
		
		new Verifications() {
			{
				Update parms;
				client.update(parms = withCapture());
				times = 1;	
				
				Assert.assertEquals(name, parms.getName());
				Assert.assertSame(spec, parms.getSpec());
			}
		};
	}
	
	private void updateWithException(final ALELRService service, final ALELRServicePortType client, Exception fault, final Exception response) throws Exception {
		LR lr = createLR(service, client, null);
		new NonStrictExpectations() {
			{
				client.update(this.<Update> withNotNull());
				result = response;
			}
		};

		try {
			lr.update(null,null);
			Assert.fail("Expected " + fault.getClass().getSimpleName());
		} catch (Exception e) {
			Assert.assertSame(fault, e);
		}
	}
	
	@Test
	public void updateWithImmutableReaderExceptionn(final @Mocked ALELRService service, final @Mocked ALELRServicePortType client) throws Exception {
		ImmutableReaderException exception = new ImmutableReaderException();
		updateWithException(service, client, exception, new ImmutableReaderExceptionResponse("Exception", exception));
	}
	@Test
	public void updateWithImplementationException(final @Mocked ALELRService service, final @Mocked ALELRServicePortType client) throws Exception {
		ImplementationException exception = new ImplementationException();
		updateWithException(service, client, exception, new ImplementationExceptionResponse("Exception", exception));
	}
	@Test
	public void updateWithInUseException(final @Mocked ALELRService service, final @Mocked ALELRServicePortType client) throws Exception {
		InUseException exception = new InUseException();
		updateWithException(service, client, exception, new InUseExceptionResponse("Exception", exception));
	}
	@Test
	public void updateWithNoSuchNameException(final @Mocked ALELRService service, final @Mocked ALELRServicePortType client) throws Exception {
		NoSuchNameException exception = new NoSuchNameException();
		updateWithException(service, client, exception, new NoSuchNameExceptionResponse("Exception", exception));
	}
	@Test
	public void updateWithReaderLoopException(final @Mocked ALELRService service, final @Mocked ALELRServicePortType client) throws Exception {
		ReaderLoopException exception = new ReaderLoopException();
		updateWithException(service, client, exception, new ReaderLoopExceptionResponse("Exception", exception));
	}
	@Test
	public void updateWithSecurityException(final @Mocked ALELRService service, final @Mocked ALELRServicePortType client) throws Exception {
		SecurityException exception = new SecurityException();
		updateWithException(service, client, exception, new SecurityExceptionResponse("Exception", exception));
	}
	@Test
	public void updateWithValidationException(final @Mocked ALELRService service, final @Mocked ALELRServicePortType client) throws Exception {
		ValidationException exception = new ValidationException();
		updateWithException(service, client, exception, new ValidationExceptionResponse("Exception", exception));
	}
	
	@Test
	public void getStandardVersion(final @Mocked ALELRService service, final @Mocked ALELRServicePortType client, final @Mocked String version) throws Exception {
		new NonStrictExpectations() {
			{
				client.getStandardVersion(this.<EmptyParms>withNotNull());
				result = version;
			}
		};
		LR lr = createLR(service, client, null);
		
		final String result = lr.getStandardVersion();
		
		new Verifications() {
			{
				client.getStandardVersion(this.<EmptyParms>withNotNull());
				times = 1;	
				
				Assert.assertEquals(version, result);
			}
		};
	}
	
	private void getStandardVersionWithException(final ALELRService service, final ALELRServicePortType client, Exception fault, final Exception response) throws Exception {
		LR lr = createLR(service, client, null);
		new NonStrictExpectations() {
			{
				client.getStandardVersion(this.<EmptyParms> withNotNull());
				result = response;
			}
		};

		try {
			lr.getStandardVersion();
			Assert.fail("Expected " + fault.getClass().getSimpleName());
		} catch (Exception e) {
			Assert.assertSame(fault, e);
		}
	}
	
	@Test
	public void getStandardVersionWithValidationException(final @Mocked ALELRService service, final @Mocked ALELRServicePortType client) throws Exception {
		ImplementationException exception = new ImplementationException();
		getStandardVersionWithException(service, client, exception, new ImplementationExceptionResponse("Exception", exception));
	}
	
	@Test
	public void getVendorVersion(final @Mocked ALELRService service, final @Mocked ALELRServicePortType client, final @Mocked String version) throws Exception {
		new NonStrictExpectations() {
			{
				client.getVendorVersion(this.<EmptyParms>withNotNull());
				result = version;
			}
		};
		LR lr = createLR(service, client, null);
		
		final String result = lr.getVendorVersion();
		
		new Verifications() {
			{
				client.getVendorVersion(this.<EmptyParms>withNotNull());
				times = 1;	
				
				Assert.assertEquals(version, result);
			}
		};
	}
	
	private void getVendorVersionWithException(final ALELRService service, final ALELRServicePortType client, Exception fault, final Exception response) throws Exception {
		LR lr = createLR(service, client, null);
		new NonStrictExpectations() {
			{
				client.getVendorVersion(this.<EmptyParms> withNotNull());
				result = response;
			}
		};

		try {
			lr.getVendorVersion();
			Assert.fail("Expected " + fault.getClass().getSimpleName());
		} catch (Exception e) {
			Assert.assertSame(fault, e);
		}
	}
	
	@Test
	public void getVendorVersionWithImplementationException(final @Mocked ALELRService service, final @Mocked ALELRServicePortType client) throws Exception {
		ImplementationException exception = new ImplementationException();
		getVendorVersionWithException(service, client, exception, new ImplementationExceptionResponse("Exception", exception));
	}
}
