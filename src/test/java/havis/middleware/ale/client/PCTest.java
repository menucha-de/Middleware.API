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
import havis.middleware.ale.service.pc.PCReports;
import havis.middleware.ale.service.pc.PCSpec;
import havis.middleware.ale.service.pc.PCSpecValidationExceptionResponse;
import havis.middleware.ale.service.pc.Poll;
import havis.middleware.ale.service.pc.SecurityExceptionResponse;
import havis.middleware.ale.service.pc.Subscribe;
import havis.middleware.ale.service.pc.Undefine;
import havis.middleware.ale.service.pc.Unsubscribe;

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

public class PCTest {
	private PC createPC(final ALEPCService service, final ALEPCServicePortType client, final URL url) {
		new NonStrictExpectations() {
			{
				service.getALEPCServicePort();
				result = client;
			}
		};
		if (url != null)
			return new PC(url);
		else
			return new PC();
	}
	
	@Test
	public void pc(final @Mocked ALEPCService service, final @Mocked ALEPCServicePortType client) throws Exception {
		PC pc = createPC(service, client, null);
		Assert.assertSame(client, Deencapsulation.getField(pc, "client"));
	}
	
	@Test
	public void pcWithUrl(final @Mocked ALEPCService service, final @Mocked ALEPCServicePortType client, final @Mocked URL url) throws Exception {
		PC pc = createPC(service, client, url);
		Assert.assertSame(client, Deencapsulation.getField(pc, "client"));
	}
	
	@Test
	public void pc(final @Mocked ALEPCServicePortType client) throws Exception {
		PC pc = new PC(client);
		Assert.assertSame(client, Deencapsulation.getField(pc, "client"));
	}
	
	@Test
	public <Client extends ALEPCServicePortType & BindingProvider> void setEndpoint(final @Mocked Client client) {
		final Map<String, Object> requestContext = new HashMap<String, Object>();
		final String endpoint = "/test";
		new NonStrictExpectations() {
			{
				client.getRequestContext();
				result = requestContext;
			}
		};

		PC pc = new PC(client);
		pc.setEndpoint(endpoint);
		Assert.assertEquals(endpoint, requestContext.get(BindingProvider.ENDPOINT_ADDRESS_PROPERTY));
	}
	
	@Test
	public void define(final @Mocked ALEPCService service, final @Mocked ALEPCServicePortType client) throws Exception {
		PC pc = createPC(service, client, null);
		final String name = "pc";
		final PCSpec spec = new PCSpec();
		
		pc.define(name, spec);
		
		new Verifications() {
			{
				Define parms;
				client.define(parms = withCapture());
				
				Assert.assertEquals(name, parms.getSpecName());
				Assert.assertSame(spec, parms.getSpec());
			}
		};
	}
	
	private void defineWithException(final ALEPCService service, final ALEPCServicePortType client, Exception fault, final Exception response) throws Exception {
		PC pc = createPC(service, client, null);
		new NonStrictExpectations() {
			{
				client.define(this.<Define> withNotNull());
				result = response;
			}
		};

		try {
			pc.define(null, null);
			Assert.fail("Expected " + fault.getClass().getSimpleName());
		} catch (Exception e) {
			Assert.assertSame(fault, e);
		}
	}
	
	@Test
	public void defineWithDuplicateNameException(final @Mocked ALEPCService service, final @Mocked ALEPCServicePortType client) throws Exception {
		DuplicateNameException exception = new DuplicateNameException();
		defineWithException(service, client, exception, new DuplicateNameExceptionResponse("Exception", exception));
	}
	@Test
	public void defineWithImplementationException(final @Mocked ALEPCService service, final @Mocked ALEPCServicePortType client) throws Exception {
		ImplementationException exception = new ImplementationException();
		defineWithException(service, client, exception, new ImplementationExceptionResponse("Exception", exception));
	}
	@Test
	public void defineWithPCSpecValidationException(final @Mocked ALEPCService service, final @Mocked ALEPCServicePortType client) throws Exception {
		ValidationException exception = new ValidationException();
		defineWithException(service, client, exception, new PCSpecValidationExceptionResponse("Exception", exception));
	}
	@Test
	public void defineWithSecurityException(final @Mocked ALEPCService service, final @Mocked ALEPCServicePortType client) throws Exception {
		SecurityException exception = new SecurityException();
		defineWithException(service, client, exception, new SecurityExceptionResponse("Exception", exception));
	}
	
	@Test
	public void undefine(final @Mocked ALEPCService service, final @Mocked ALEPCServicePortType client) throws Exception {
		PC pc = createPC(service, client, null);
		final String name = "pc";
		
		pc.undefine(name);
		
		new Verifications() {
			{
				Undefine parms;
				client.undefine(parms = withCapture());
				
				Assert.assertEquals(name, parms.getSpecName());
			}
		};
	}
	
	private void undefineWithException(final ALEPCService service, final ALEPCServicePortType client, Exception fault, final Exception response) throws Exception {
		PC pc = createPC(service, client, null);
		new NonStrictExpectations() {
			{
				client.undefine(this.<Undefine> withNotNull());
				result = response;
			}
		};

		try {
			pc.undefine(null);
			Assert.fail("Expected " + fault.getClass().getSimpleName());
		} catch (Exception e) {
			Assert.assertSame(fault, e);
		}
	}
	
	@Test
	public void undefineWithImplementationException(final @Mocked ALEPCService service, final @Mocked ALEPCServicePortType client) throws Exception {
		ImplementationException exception = new ImplementationException();
		undefineWithException(service, client, exception, new ImplementationExceptionResponse("Exception", exception));
	}
	@Test
	public void undefineWithNoSuchNameException(final @Mocked ALEPCService service, final @Mocked ALEPCServicePortType client) throws Exception {
		NoSuchNameException exception = new NoSuchNameException();
		undefineWithException(service, client, exception, new NoSuchNameExceptionResponse("Exception", exception));
	}
	@Test
	public void undefineWithSecurityException(final @Mocked ALEPCService service, final @Mocked ALEPCServicePortType client) throws Exception {
		SecurityException exception = new SecurityException();
		undefineWithException(service, client, exception, new SecurityExceptionResponse("Exception", exception));
	}
	
	@Test
	public void getSpec(final @Mocked ALEPCService service, final @Mocked ALEPCServicePortType client, final @Mocked PCSpec expected) throws Exception {
		new NonStrictExpectations() {
			{
				client.getPCSpec(this.<GetPCSpec>withNotNull());
				result = expected;
			}
		};
		PC pc = createPC(service, client, null);
		final String name = "pc";
		
		final PCSpec actual = pc.getSpec(name);
		
		new Verifications() {
			{
				GetPCSpec parms;
				client.getPCSpec(parms = withCapture());
				
				Assert.assertEquals(name, parms.getSpecName());
				Assert.assertSame(expected, actual);
			}
		};
	}
	
	private void getSpecWithException(final ALEPCService service, final ALEPCServicePortType client, Exception fault, final Exception response) throws Exception {
		PC pc = createPC(service, client, null);
		new NonStrictExpectations() {
			{
				client.getPCSpec(this.<GetPCSpec> withNotNull());
				result = response;
			}
		};

		try {
			pc.getSpec(null);
			Assert.fail("Expected " + fault.getClass().getSimpleName());
		} catch (Exception e) {
			Assert.assertSame(fault, e);
		}
	}
	
	@Test
	public void getSpecWithImplementationException(final @Mocked ALEPCService service, final @Mocked ALEPCServicePortType client) throws Exception {
		ImplementationException exception = new ImplementationException();
		getSpecWithException(service, client, exception, new ImplementationExceptionResponse("Exception", exception));
	}
	@Test
	public void getSpecWithNoSuchNameException(final @Mocked ALEPCService service, final @Mocked ALEPCServicePortType client) throws Exception {
		NoSuchNameException exception = new NoSuchNameException();
		getSpecWithException(service, client, exception, new NoSuchNameExceptionResponse("Exception", exception));
	}
	@Test
	public void getSpecWithSecurityException(final @Mocked ALEPCService service, final @Mocked ALEPCServicePortType client) throws Exception {
		SecurityException exception = new SecurityException();
		getSpecWithException(service, client, exception, new SecurityExceptionResponse("Exception", exception));
	}
	
	@Test
	public void getNames(final @Mocked ALEPCService service, final @Mocked ALEPCServicePortType client, final @Mocked ArrayOfString string) throws Exception {
		new NonStrictExpectations() {
			{
				client.getPCSpecNames(this.<EmptyParms>withNotNull());
				result = string;
			}
		};
		PC pc = createPC(service, client, null);
		
		final List<String> list = pc.getNames();
		
		new Verifications() {
			{
				client.getPCSpecNames(this.<EmptyParms>withNotNull());
				times = 1;
				string.getString();
				times = 1;
				
				Assert.assertEquals(string.getString(), list);
			}
		};
	}
	
	@Test
	public void getNamesWithNull(final @Mocked ALEPCService service, final @Mocked ALEPCServicePortType client, final @Mocked ArrayOfString string) throws Exception {
		new NonStrictExpectations() {
			{
				client.getPCSpecNames(this.<EmptyParms>withNotNull());
				result = null;
			}
		};
		PC pc = createPC(service, client, null);
		
		final List<String> list = pc.getNames();
		
		new Verifications() {
			{
				client.getPCSpecNames(this.<EmptyParms>withNotNull());
				times = 1;
				string.getString();
				times = 0;
				
				Assert.assertNull(list);
			}
		};
	}
	
	private void getNamesWithException(final ALEPCService service, final ALEPCServicePortType client, Exception fault, final Exception response) throws Exception {
		PC pc = createPC(service, client, null);
		new NonStrictExpectations() {
			{
				client.getPCSpecNames(this.<EmptyParms> withNotNull());
				result = response;
			}
		};

		try {
			pc.getNames();
			Assert.fail("Expected " + fault.getClass().getSimpleName());
		} catch (Exception e) {
			Assert.assertSame(fault, e);
		}
	}
	
	@Test
	public void getNamesWithImplementationException(final @Mocked ALEPCService service, final @Mocked ALEPCServicePortType client) throws Exception {
		ImplementationException exception = new ImplementationException();
		getNamesWithException(service, client, exception, new ImplementationExceptionResponse("Exception", exception));
	}
	@Test
	public void getNamesWithSecurityException(final @Mocked ALEPCService service, final @Mocked ALEPCServicePortType client) throws Exception {
		SecurityException exception = new SecurityException();
		getNamesWithException(service, client, exception, new SecurityExceptionResponse("Exception", exception));
	}
	
	@Test
	public void subscribe(final @Mocked ALEPCService service, final @Mocked ALEPCServicePortType client) throws Exception {
		PC pc = createPC(service, client, null);
		final String name = "pc";
		final String uri = "uri";
		
		
		pc.subscribe(name, uri);
		
		new Verifications() {
			{
				Subscribe parms;
				client.subscribe(parms = withCapture());
				times = 1;
				
				Assert.assertEquals(name, parms.getSpecName());
				Assert.assertEquals(uri, parms.getNotificationURI());
			}
		};
	}
	
	private void subscribeWithException(final ALEPCService service, final ALEPCServicePortType client, Exception fault, final Exception response) throws Exception {
		PC pc = createPC(service, client, null);
		new NonStrictExpectations() {
			{
				client.subscribe(this.<Subscribe> withNotNull());
				result = response;
			}
		};

		try {
			pc.subscribe(null, null);
			Assert.fail("Expected " + fault.getClass().getSimpleName());
		} catch (Exception e) {
			Assert.assertSame(fault, e);
		}
	}
	
	@Test
	public void subscribeWithDuplicateSubscriptionException(final @Mocked ALEPCService service, final @Mocked ALEPCServicePortType client) throws Exception {
		DuplicateSubscriptionException exception = new DuplicateSubscriptionException();
		subscribeWithException(service, client, exception, new DuplicateSubscriptionExceptionResponse("Exception", exception));
	}
	@Test
	public void subscribeWithImplementationException(final @Mocked ALEPCService service, final @Mocked ALEPCServicePortType client) throws Exception {
		ImplementationException exception = new ImplementationException();
		subscribeWithException(service, client, exception, new ImplementationExceptionResponse("Exception", exception));
	}
	@Test
	public void subscribeWithInvalidURIException(final @Mocked ALEPCService service, final @Mocked ALEPCServicePortType client) throws Exception {
		InvalidURIException exception = new InvalidURIException();
		subscribeWithException(service, client, exception, new InvalidURIExceptionResponse("Exception", exception));
	}
	@Test
	public void subscribeWithNoSuchNameException(final @Mocked ALEPCService service, final @Mocked ALEPCServicePortType client) throws Exception {
		NoSuchNameException exception = new NoSuchNameException();
		subscribeWithException(service, client, exception, new NoSuchNameExceptionResponse("Exception", exception));
	}
	@Test
	public void subscribeWithSecurityException(final @Mocked ALEPCService service, final @Mocked ALEPCServicePortType client) throws Exception {
		SecurityException exception = new SecurityException();
		subscribeWithException(service, client, exception, new SecurityExceptionResponse("Exception", exception));
	}
	
	@Test
	public void unsubscribe(final @Mocked ALEPCService service, final @Mocked ALEPCServicePortType client) throws Exception {
		PC pc = createPC(service, client, null);
		final String name = "pc";
		final String uri = "uri";
		
		
		pc.unsubscribe(name, uri);
		
		new Verifications() {
			{
				Unsubscribe parms;
				client.unsubscribe(parms = withCapture());
				times = 1;
				
				Assert.assertEquals(name, parms.getSpecName());
				Assert.assertEquals(uri, parms.getNotificationURI());
			}
		};
	}
	
	private void unsubscribeWithException(final ALEPCService service, final ALEPCServicePortType client, Exception fault, final Exception response) throws Exception {
		PC pc = createPC(service, client, null);
		new NonStrictExpectations() {
			{
				client.unsubscribe(this.<Unsubscribe> withNotNull());
				result = response;
			}
		};

		try {
			pc.unsubscribe(null, null);
			Assert.fail("Expected " + fault.getClass().getSimpleName());
		} catch (Exception e) {
			Assert.assertSame(fault, e);
		}
	}
	
	@Test
	public void unsubscribeWithNoSuchSubscriberException(final @Mocked ALEPCService service, final @Mocked ALEPCServicePortType client) throws Exception {
		NoSuchSubscriberException exception = new NoSuchSubscriberException();
		unsubscribeWithException(service, client, exception, new NoSuchSubscriberExceptionResponse("Exception", exception));
	}
	@Test
	public void unsubscribeWithImplementationException(final @Mocked ALEPCService service, final @Mocked ALEPCServicePortType client) throws Exception {
		ImplementationException exception = new ImplementationException();
		unsubscribeWithException(service, client, exception, new ImplementationExceptionResponse("Exception", exception));
	}
	@Test
	public void unsubscribeWithInvalidURIException(final @Mocked ALEPCService service, final @Mocked ALEPCServicePortType client) throws Exception {
		InvalidURIException exception = new InvalidURIException();
		unsubscribeWithException(service, client, exception, new InvalidURIExceptionResponse("Exception", exception));
	}
	@Test
	public void unsubscribeWithNoSuchNameException(final @Mocked ALEPCService service, final @Mocked ALEPCServicePortType client) throws Exception {
		NoSuchNameException exception = new NoSuchNameException();
		unsubscribeWithException(service, client, exception, new NoSuchNameExceptionResponse("Exception", exception));
	}
	@Test
	public void unsubscribeWithSecurityException(final @Mocked ALEPCService service, final @Mocked ALEPCServicePortType client) throws Exception {
		SecurityException exception = new SecurityException();
		unsubscribeWithException(service, client, exception, new SecurityExceptionResponse("Exception", exception));
	}
	
	@Test
	public void poll(final @Mocked ALEPCService service, final @Mocked ALEPCServicePortType client, final @Mocked PCReports expected) throws Exception {
		PC pc = createPC(service, client, null);
		final String name = "pc";
		
		final PCReports actual = pc.poll(name);
		
		new Verifications() {
			{
				Poll parms;
				client.poll(parms = withCapture());
				times = 1;
				
				Assert.assertEquals(name, parms.getSpecName());
				Assert.assertSame(expected, actual);
			}
		};
	}
	
	private void pollWithException(final ALEPCService service, final ALEPCServicePortType client, Exception fault, final Exception response) throws Exception {
		PC pc = createPC(service, client, null);
		new NonStrictExpectations() {
			{
				client.poll(this.<Poll> withNotNull());
				result = response;
			}
		};

		try {
			pc.poll(null);
			Assert.fail("Expected " + fault.getClass().getSimpleName());
		} catch (Exception e) {
			Assert.assertSame(fault, e);
		}
	}
	
	@Test
	public void pollWithNoSuchNameException(final @Mocked ALEPCService service, final @Mocked ALEPCServicePortType client) throws Exception {
		NoSuchNameException exception = new NoSuchNameException();
		pollWithException(service, client, exception, new NoSuchNameExceptionResponse("Exception", exception));
	}
	@Test
	public void pollWithSecurityException(final @Mocked ALEPCService service, final @Mocked ALEPCServicePortType client) throws Exception {
		SecurityException exception = new SecurityException();
		pollWithException(service, client, exception, new SecurityExceptionResponse("Exception", exception));
	}
	@Test
	public void pollWithImplementationException(final @Mocked ALEPCService service, final @Mocked ALEPCServicePortType client) throws Exception {
		ImplementationException exception = new ImplementationException();
		pollWithException(service, client, exception, new ImplementationExceptionResponse("Exception", exception));
	}
	
	@Test
	public void immediate(final @Mocked ALEPCService service, final @Mocked ALEPCServicePortType client, final @Mocked PCReports expected) throws Exception {
		new NonStrictExpectations() {
			{
				client.immediate(this.<Immediate>withNotNull());
				result = expected;
			}
		};
		PC pc = createPC(service, client, null);
		final PCSpec spec = new PCSpec();
		
		final PCReports actual = pc.immediate(spec);
		
		new Verifications() {
			{
				Immediate parms;
				client.immediate(parms = withCapture());
				times = 1;
				
				Assert.assertSame(spec, parms.getSpec());
				Assert.assertSame(expected, actual);
			}
		};
	}
	
	private void immediateWithException(final ALEPCService service, final ALEPCServicePortType client, Exception fault, final Exception response) throws Exception {
		PC pc = createPC(service, client, null);
		new NonStrictExpectations() {
			{
				client.immediate(this.<Immediate> withNotNull());
				result = response;
			}
		};

		try {
			pc.immediate(null);
			Assert.fail("Expected " + fault.getClass().getSimpleName());
		} catch (Exception e) {
			Assert.assertSame(fault, e);
		}
	}
	
	@Test
	public void immediateWithSecurityException(final @Mocked ALEPCService service, final @Mocked ALEPCServicePortType client) throws Exception {
		SecurityException exception = new SecurityException();
		immediateWithException(service, client, exception, new SecurityExceptionResponse("Exception", exception));
	}
	@Test
	public void immediateWithImplementationException(final @Mocked ALEPCService service, final @Mocked ALEPCServicePortType client) throws Exception {
		ImplementationException exception = new ImplementationException();
		immediateWithException(service, client, exception, new ImplementationExceptionResponse("Exception", exception));
	}
	@Test
	public void immediateWithPCSpecValidationException(final @Mocked ALEPCService service, final @Mocked ALEPCServicePortType client) throws Exception {
		ValidationException exception = new ValidationException();
		immediateWithException(service, client, exception, new PCSpecValidationExceptionResponse("Exception", exception));
	}
	
	@Test
	public void getSubscribers(final @Mocked ALEPCService service, final @Mocked ALEPCServicePortType client, final @Mocked ArrayOfString string) throws Exception {
		new NonStrictExpectations() {
			{
				client.getSubscribers(this.<GetSubscribers>withNotNull());
				result = string;
			}
		};
		PC pc = createPC(service, client, null);
		final String name = "pc";
		
		final List<String> list = pc.getSubscribers(name);
		
		new Verifications() {
			{
				GetSubscribers parms;
				client.getSubscribers(parms = withCapture());
				times = 1;
				string.getString();
				times = 1;
				
				Assert.assertEquals(name, parms.getSpecName());
				Assert.assertEquals(string.getString(), list);
			}
		};
	}
	
	@Test
	public void getSubscribersWithNull(final @Mocked ALEPCService service, final @Mocked ALEPCServicePortType client, final @Mocked ArrayOfString string) throws Exception {
		new NonStrictExpectations() {
			{
				client.getSubscribers(this.<GetSubscribers>withNotNull());
				result = null;
			}
		};
		PC pc = createPC(service, client, null);
		final String name = "pc";
		
		final List<String> list = pc.getSubscribers(name);
		
		new Verifications() {
			{
				GetSubscribers parms;
				client.getSubscribers(parms = withCapture());
				times = 1;
				string.getString();
				times = 0;
				
				Assert.assertEquals(name, parms.getSpecName());
				Assert.assertNull(list);
			}
		};
	}
	
	private void getSubscribersWithException(final ALEPCService service, final ALEPCServicePortType client, Exception fault, final Exception response) throws Exception {
		PC pc = createPC(service, client, null);
		new NonStrictExpectations() {
			{
				client.getSubscribers(this.<GetSubscribers> withNotNull());
				result = response;
			}
		};

		try {
			pc.getSubscribers(null);
			Assert.fail("Expected " + fault.getClass().getSimpleName());
		} catch (Exception e) {
			Assert.assertSame(fault, e);
		}
	}
	
	@Test
	public void getSubscribersWithSecurityException(final @Mocked ALEPCService service, final @Mocked ALEPCServicePortType client) throws Exception {
		SecurityException exception = new SecurityException();
		getSubscribersWithException(service, client, exception, new SecurityExceptionResponse("Exception", exception));
	}
	@Test
	public void getSubscribersWithImplementationException(final @Mocked ALEPCService service, final @Mocked ALEPCServicePortType client) throws Exception {
		ImplementationException exception = new ImplementationException();
		getSubscribersWithException(service, client, exception, new ImplementationExceptionResponse("Exception", exception));
	}
	@Test
	public void getSubscribersWithNoSuchNameException(final @Mocked ALEPCService service, final @Mocked ALEPCServicePortType client) throws Exception {
		NoSuchNameException exception = new NoSuchNameException();
		getSubscribersWithException(service, client, exception, new NoSuchNameExceptionResponse("Exception", exception));
	}

	@Test
	public void execute(final @Mocked ALEPCService service, final @Mocked ALEPCServicePortType client, final @Mocked ExecuteResult expected) throws Exception {
		new NonStrictExpectations() {
			{
				client.execute(this.<Execute>withNotNull());
				result = expected;
			}
		};
		PC pc = createPC(service, client, null);
		final PCOpSpec spec = new PCOpSpec();
		final List<PCOpSpec> specs = new ArrayList<PCOpSpec>(
				Arrays.asList(spec)); 
		
		final List<PCOpReport> actual = pc.execute(specs);
		
		new Verifications() {
			{
				Execute parms;
				client.execute(parms = withCapture());
				times = 1;

				Assert.assertSame(specs, parms.getOpSpecs().getOpSpec());
				Assert.assertSame(spec, parms.getOpSpecs().getOpSpec().get(0));
				Assert.assertEquals(expected.getOpReports().getOpReport(), actual);
			}
		};
	}
	
	@Test
	public void executeWithNull(final @Mocked ALEPCService service, final @Mocked ALEPCServicePortType client) throws Exception {
		new NonStrictExpectations() {
			{
				client.execute(this.<Execute>withNotNull());
				result = null;
			}
		};
		PC pc = createPC(service, client, null);
		final PCOpSpec spec = new PCOpSpec();
		final List<PCOpSpec> specs = new ArrayList<PCOpSpec>(
				Arrays.asList(spec)); 
		
		final List<PCOpReport> list = pc.execute(specs);
		
		new Verifications() {
			{
				client.execute(this.<Execute>withNotNull());
				times = 1;

				Assert.assertNull(list);
			}
		};
	}
	
	private void executeWithException(final ALEPCService service, final ALEPCServicePortType client, Exception fault, final Exception response) throws Exception {
		PC pc = createPC(service, client, null);
		new NonStrictExpectations() {
			{
				client.execute(this.<Execute> withNotNull());
				result = response;
			}
		};

		try {
			pc.execute(null);
			Assert.fail("Expected " + fault.getClass().getSimpleName());
		} catch (Exception e) {
			Assert.assertSame(fault, e);
		}
	}
	
	@Test
	public void executeWithSecurityException(final @Mocked ALEPCService service, final @Mocked ALEPCServicePortType client) throws Exception {
		SecurityException exception = new SecurityException();
		executeWithException(service, client, exception, new SecurityExceptionResponse("Exception", exception));
	}
	@Test
	public void executeWithPCSpecValidationExceptionResponse(final @Mocked ALEPCService service, final @Mocked ALEPCServicePortType client) throws Exception {
		ValidationException exception = new ValidationException();
		executeWithException(service, client, exception, new PCSpecValidationExceptionResponse("Exception", exception));
	}
	@Test
	public void executeWithImplementationException(final @Mocked ALEPCService service, final @Mocked ALEPCServicePortType client) throws Exception {
		ImplementationException exception = new ImplementationException();
		executeWithException(service, client, exception, new ImplementationExceptionResponse("Exception", exception));
	}
	
	@Test
	public void getStandardVersion(final @Mocked ALEPCService service, final @Mocked ALEPCServicePortType client, final @Mocked String version) throws Exception {
		new NonStrictExpectations() {
			{
				client.getStandardVersion(this.<EmptyParms>withNotNull());
				result = version;
			}
		};
		PC pc = createPC(service, client, null);
		
		final String result = pc.getStandardVersion();
		
		new Verifications() {
			{
				client.getStandardVersion(this.<EmptyParms>withNotNull());
				times = 1;	
				
				Assert.assertEquals(version, result);
			}
		};
	}
	
	private void getStandardVersionWithException(final ALEPCService service, final ALEPCServicePortType client, Exception fault, final Exception response) throws Exception {
		PC pc = createPC(service, client, null);
		new NonStrictExpectations() {
			{
				client.getStandardVersion(this.<EmptyParms> withNotNull());
				result = response;
			}
		};

		try {
			pc.getStandardVersion();
			Assert.fail("Expected " + fault.getClass().getSimpleName());
		} catch (Exception e) {
			Assert.assertSame(fault, e);
		}
	}
	
	@Test
	public void getStandardVersionWithValidationException(final @Mocked ALEPCService service, final @Mocked ALEPCServicePortType client) throws Exception {
		ImplementationException exception = new ImplementationException();
		getStandardVersionWithException(service, client, exception, new ImplementationExceptionResponse("Exception", exception));
	}
	
	@Test
	public void getVendorVersion(final @Mocked ALEPCService service, final @Mocked ALEPCServicePortType client, final @Mocked String version) throws Exception {
		new NonStrictExpectations() {
			{
				client.getVendorVersion(this.<EmptyParms>withNotNull());
				result = version;
			}
		};
		PC pc = createPC(service, client, null);
		
		final String result = pc.getVendorVersion();
		
		new Verifications() {
			{
				client.getVendorVersion(this.<EmptyParms>withNotNull());
				times = 1;	
				
				Assert.assertEquals(version, result);
			}
		};
	}
	
	private void getVendorVersionWithException(final ALEPCService service, final ALEPCServicePortType client, Exception fault, final Exception response) throws Exception {
		PC pc = createPC(service, client, null);
		new NonStrictExpectations() {
			{
				client.getVendorVersion(this.<EmptyParms> withNotNull());
				result = response;
			}
		};

		try {
			pc.getVendorVersion();
			Assert.fail("Expected " + fault.getClass().getSimpleName());
		} catch (Exception e) {
			Assert.assertSame(fault, e);
		}
	}
	
	@Test
	public void getVendorVersionWithImplementationException(final @Mocked ALEPCService service, final @Mocked ALEPCServicePortType client) throws Exception {
		ImplementationException exception = new ImplementationException();
		getVendorVersionWithException(service, client, exception, new ImplementationExceptionResponse("Exception", exception));
	}
}
