package havis.middleware.ale.client;

import havis.middleware.ale.base.exception.AssocTableValidationException;
import havis.middleware.ale.base.exception.DuplicateNameException;
import havis.middleware.ale.base.exception.DuplicateSubscriptionException;
import havis.middleware.ale.base.exception.EPCCacheSpecValidationException;
import havis.middleware.ale.base.exception.ImplementationException;
import havis.middleware.ale.base.exception.InUseException;
import havis.middleware.ale.base.exception.InvalidAssocTableEntryException;
import havis.middleware.ale.base.exception.InvalidEPCException;
import havis.middleware.ale.base.exception.InvalidPatternException;
import havis.middleware.ale.base.exception.InvalidURIException;
import havis.middleware.ale.base.exception.NoSuchNameException;
import havis.middleware.ale.base.exception.NoSuchSubscriberException;
import havis.middleware.ale.base.exception.ParameterException;
import havis.middleware.ale.base.exception.ParameterForbiddenException;
import havis.middleware.ale.base.exception.RNGValidationException;
import havis.middleware.ale.base.exception.SecurityException;
import havis.middleware.ale.base.exception.ValidationException;
import havis.middleware.ale.service.ECTime;
import havis.middleware.ale.service.cc.ALECCService;
import havis.middleware.ale.service.cc.ALECCServicePortType;
import havis.middleware.ale.service.cc.ArrayOfString;
import havis.middleware.ale.service.cc.AssocTableEntry;
import havis.middleware.ale.service.cc.AssocTableEntryList;
import havis.middleware.ale.service.cc.AssocTableSpec;
import havis.middleware.ale.service.cc.AssocTableValidationExceptionResponse;
import havis.middleware.ale.service.cc.CCBoundarySpec;
import havis.middleware.ale.service.cc.CCParameterListEntry;
import havis.middleware.ale.service.cc.CCReports;
import havis.middleware.ale.service.cc.CCSpec;
import havis.middleware.ale.service.cc.CCSpecValidationExceptionResponse;
import havis.middleware.ale.service.cc.Define;
import havis.middleware.ale.service.cc.DefineAssocTable;
import havis.middleware.ale.service.cc.DefineEPCCache;
import havis.middleware.ale.service.cc.DefineRNG;
import havis.middleware.ale.service.cc.DepleteEPCCache;
import havis.middleware.ale.service.cc.DuplicateNameExceptionResponse;
import havis.middleware.ale.service.cc.DuplicateSubscriptionExceptionResponse;
import havis.middleware.ale.service.cc.EPCCacheSpec;
import havis.middleware.ale.service.cc.EPCCacheSpecValidationExceptionResponse;
import havis.middleware.ale.service.cc.EPCPatternList;
import havis.middleware.ale.service.cc.EPCPatternList.Patterns;
import havis.middleware.ale.service.cc.EmptyParms;
import havis.middleware.ale.service.cc.GetAssocTable;
import havis.middleware.ale.service.cc.GetAssocTableEntries;
import havis.middleware.ale.service.cc.GetAssocTableValue;
import havis.middleware.ale.service.cc.GetCCSpec;
import havis.middleware.ale.service.cc.GetEPCCache;
import havis.middleware.ale.service.cc.GetEPCCacheContents;
import havis.middleware.ale.service.cc.GetRNG;
import havis.middleware.ale.service.cc.GetSubscribers;
import havis.middleware.ale.service.cc.Immediate;
import havis.middleware.ale.service.cc.ImplementationExceptionResponse;
import havis.middleware.ale.service.cc.InUseExceptionResponse;
import havis.middleware.ale.service.cc.InvalidAssocTableEntryExceptionResponse;
import havis.middleware.ale.service.cc.InvalidEPCExceptionResponse;
import havis.middleware.ale.service.cc.InvalidPatternExceptionResponse;
import havis.middleware.ale.service.cc.InvalidURIExceptionResponse;
import havis.middleware.ale.service.cc.NoSuchNameExceptionResponse;
import havis.middleware.ale.service.cc.NoSuchSubscriberExceptionResponse;
import havis.middleware.ale.service.cc.ParameterExceptionResponse;
import havis.middleware.ale.service.cc.ParameterForbiddenExceptionResponse;
import havis.middleware.ale.service.cc.Poll;
import havis.middleware.ale.service.cc.PutAssocTableEntries;
import havis.middleware.ale.service.cc.RNGSpec;
import havis.middleware.ale.service.cc.RNGValidationExceptionResponse;
import havis.middleware.ale.service.cc.RemoveAssocTableEntries;
import havis.middleware.ale.service.cc.RemoveAssocTableEntry;
import havis.middleware.ale.service.cc.ReplenishEPCCache;
import havis.middleware.ale.service.cc.SecurityExceptionResponse;
import havis.middleware.ale.service.cc.Subscribe;
import havis.middleware.ale.service.cc.Undefine;
import havis.middleware.ale.service.cc.UndefineAssocTable;
import havis.middleware.ale.service.cc.UndefineEPCCache;
import havis.middleware.ale.service.cc.UndefineRNG;
import havis.middleware.ale.service.cc.Unsubscribe;

import java.math.BigDecimal;
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

public class CCTest {

	@Test
	public void cc(final @Mocked ALECCService service, final @Mocked ALECCServicePortType client) throws Exception {
		CC cc = createCC(service, client, null);
		Assert.assertSame(client, Deencapsulation.getField(cc, "client"));
	}

	@Test
	public void ccWithUrl(final @Mocked ALECCService service, final @Mocked ALECCServicePortType client, final @Mocked URL url) throws Exception {
		CC cc = createCC(service, client, url);
		Assert.assertSame(client, Deencapsulation.getField(cc, "client"));
	}

	@Test
	public void ccWithClient(final @Mocked ALECCServicePortType client) throws Exception {
		CC cc = new CC(client);
		Assert.assertSame(client, Deencapsulation.getField(cc, "client"));
	}

	@Test
	public <Client extends ALECCServicePortType & BindingProvider> void setEndpoint(final @Mocked Client client) {
		final Map<String, Object> requestContext = new HashMap<String, Object>();
		new NonStrictExpectations() {
			{
				client.getRequestContext();
				result = requestContext;
			}
		};

		CC cc = new CC(client);
		cc.setEndpoint("/test");
		Assert.assertEquals("/test", requestContext.get(BindingProvider.ENDPOINT_ADDRESS_PROPERTY));
	}

	private CC createCC(final ALECCService service, final ALECCServicePortType client, final URL url) {
		new NonStrictExpectations() {
			{
				service.getALECCServicePort();
				result = client;
			}
		};
		if (url != null)
			return new CC(url);
		else
			return new CC();
	}

	private CCSpec createCCSpecWithDuration() {
		CCSpec ccSpec = new CCSpec();

		CCBoundarySpec boundarySpec = new CCBoundarySpec();
		ECTime ecTime = new ECTime();
		ecTime.setValue(1000);
		ccSpec.setBoundarySpec(boundarySpec);
		ccSpec.getBoundarySpec().setDuration(ecTime);

		return ccSpec;
	}

	@Test
	public void define(final @Mocked ALECCService service, final @Mocked ALECCServicePortType client) throws Exception {
		CC cc = createCC(service, client, null);
		final String specName = "Spec";
		final CCSpec ccSpec = createCCSpecWithDuration();

		cc.define(specName, ccSpec);

		new Verifications() {
			{
				Define define;
				client.define(define = withCapture());
				times = 1;

				Assert.assertEquals(specName, define.getSpecName());
				Assert.assertSame(ccSpec, define.getSpec());
			}
		};
	}

	private void defineWithException(final ALECCService service, final ALECCServicePortType client, Exception fault, final Exception response) throws Exception {
		CC cc = createCC(service, client, null);
		new NonStrictExpectations() {
			{
				client.define(this.<Define> withNotNull());
				result = response;
			}
		};

		try {
			cc.define(null, null);
			Assert.fail("Expected " + fault.getClass().getSimpleName());
		} catch (Exception e) {
			Assert.assertSame(fault, e);
		}
	}

	@Test
	public void defineWithValidationException(final @Mocked ALECCService service, final @Mocked ALECCServicePortType client) throws Exception {
		ValidationException exception = new ValidationException();
		defineWithException(service, client, exception, new CCSpecValidationExceptionResponse("Exception", exception));
	}

	@Test
	public void defineWithDuplicateNameException(final @Mocked ALECCService service, final @Mocked ALECCServicePortType client) throws Exception {
		DuplicateNameException exception = new DuplicateNameException();
		defineWithException(service, client, exception, new DuplicateNameExceptionResponse("Exception", exception));
	}

	@Test
	public void defineWithImplementationException(final @Mocked ALECCService service, final @Mocked ALECCServicePortType client) throws Exception {
		ImplementationException exception = new ImplementationException();
		defineWithException(service, client, exception, new ImplementationExceptionResponse("Exception", exception));
	}

	@Test
	public void defineWithSecurityException(final @Mocked ALECCService service, final @Mocked ALECCServicePortType client) throws Exception {
		SecurityException exception = new SecurityException();
		defineWithException(service, client, exception, new SecurityExceptionResponse("Exception", exception));
	}

	@Test
	public void undefine(final @Mocked ALECCService service, final @Mocked ALECCServicePortType client) throws Exception {
		CC cc = createCC(service, client, null);
		final String specName = "Spec";
		
		cc.undefine(specName);

		new Verifications() {
			{
				Undefine undefine;
				client.undefine(undefine = withCapture());
				times = 1;

				Assert.assertEquals(specName, undefine.getSpecName());
			}
		};
	}

	private void undefineWithException(final ALECCService service, final ALECCServicePortType client, Exception fault, final Exception response)
			throws Exception {
		CC cc = createCC(service, client, null);
		new NonStrictExpectations() {
			{
				client.undefine(this.<Undefine> withNotNull());
				result = response;
			}
		};

		try {
			cc.undefine(null);
			Assert.fail("Expected " + fault.getClass().getSimpleName());
		} catch (Exception e) {
			Assert.assertSame(fault, e);
		}
	}

	@Test
	public void undefineWithImplementationException(final @Mocked ALECCService service, final @Mocked ALECCServicePortType client) throws Exception {
		ImplementationException exception = new ImplementationException();
		undefineWithException(service, client, exception, new ImplementationExceptionResponse("Exception", exception));
	}

	@Test
	public void undefineWithNoSuchNameException(final @Mocked ALECCService service, final @Mocked ALECCServicePortType client) throws Exception {
		NoSuchNameException exception = new NoSuchNameException();
		undefineWithException(service, client, exception, new NoSuchNameExceptionResponse("Exception", exception));
	}

	@Test
	public void undefineWithSecurityException(final @Mocked ALECCService service, final @Mocked ALECCServicePortType client) throws Exception {
		SecurityException exception = new SecurityException();
		undefineWithException(service, client, exception, new SecurityExceptionResponse("Exception", exception));
	}

	@Test
	public void getSpec(final @Mocked ALECCService service, final @Mocked ALECCServicePortType client, final @Mocked CCSpec expected) throws Exception {
		new NonStrictExpectations() {
			{
				client.getCCSpec(this.<GetCCSpec>withNotNull());
				result = expected;
			}
		};
		CC cc = createCC(service, client, null);
		final String specName = "Spec";

		final CCSpec actual = cc.getSpec(specName);

		new Verifications() {
			{
				GetCCSpec spec;
				client.getCCSpec(spec = withCapture());
				times = 1;

				Assert.assertEquals(specName, spec.getSpecName());
				Assert.assertSame(expected, actual);
			}
		};
	}

	private void getSpecWithException(final ALECCService service, final ALECCServicePortType client, Exception fault, final Exception response)
			throws Exception {
		CC cc = createCC(service, client, null);
		new NonStrictExpectations() {
			{
				client.getCCSpec(this.<GetCCSpec> withNotNull());
				result = response;
			}
		};

		try {
			cc.getSpec(null);
			Assert.fail("Expected " + fault.getClass().getSimpleName());
		} catch (Exception e) {
			Assert.assertSame(fault, e);
		}
	}

	@Test
	public void getSpecWithImplementationException(final @Mocked ALECCService service, final @Mocked ALECCServicePortType client) throws Exception {
		ImplementationException exception = new ImplementationException();
		getSpecWithException(service, client, exception, new ImplementationExceptionResponse("Exception", exception));
	}

	@Test
	public void getSpecWithNoSuchNameException(final @Mocked ALECCService service, final @Mocked ALECCServicePortType client) throws Exception {
		NoSuchNameException exception = new NoSuchNameException();
		getSpecWithException(service, client, exception, new NoSuchNameExceptionResponse("Exception", exception));
	}

	@Test
	public void getSpecWithSecurityException(final @Mocked ALECCService service, final @Mocked ALECCServicePortType client) throws Exception {
		SecurityException exception = new SecurityException();
		getSpecWithException(service, client, exception, new SecurityExceptionResponse("Exception", exception));
	}
	
	@Test
	public void getNames(final @Mocked ALECCService service, final @Mocked ALECCServicePortType client, final @Mocked ArrayOfString string) throws Exception {
		new NonStrictExpectations() {
			{
				client.getCCSpecNames(this.<EmptyParms>withNotNull());
				result = string;
			}
		};
		CC cc = createCC(service, client, null);

		final List<String> list = cc.getNames();

		new Verifications() {
			{
				client.getCCSpecNames(this.<EmptyParms>withNotNull());
				times = 1;
				string.getString();
				times = 1;
				
				Assert.assertEquals(string.getString(), list);
			}
		};
	}
	
	@Test
	public void getNamesWithNull(final @Mocked ALECCService service, final @Mocked ALECCServicePortType client, final @Mocked ArrayOfString string) throws Exception {
		new NonStrictExpectations() {
			{
				client.getCCSpecNames(this.<EmptyParms>withNotNull());
				result = null;
			}
		};
		CC cc = createCC(service, client, null);

		final List<String> list = cc.getNames();

		new Verifications() {
			{
				client.getCCSpecNames(this.<EmptyParms>withNotNull());
				times = 1;
				string.getString();
				times = 0;
				
				Assert.assertNull(list);
			}
		};
	}
	
	private void getNamesWithException(final ALECCService service, final ALECCServicePortType client, Exception fault, final Exception response)
			throws Exception {
		CC cc = createCC(service, client, null);
		new NonStrictExpectations() {
			{
				client.getCCSpecNames(this.<EmptyParms>withNotNull());
				result = response;
			}
		};

		try {
			cc.getNames();
			Assert.fail("Expected " + fault.getClass().getSimpleName());
		} catch (Exception e) {
			Assert.assertSame(fault, e);
		}
	}
	
	@Test
	public void getNamesWithImplementationException(final @Mocked ALECCService service, final @Mocked ALECCServicePortType client) throws Exception {
		ImplementationException exception = new ImplementationException();
		getNamesWithException(service, client, exception, new ImplementationExceptionResponse("Exception", exception));
	}
	@Test
	public void getNamesWithSecurityException(final @Mocked ALECCService service, final @Mocked ALECCServicePortType client) throws Exception {
		SecurityException exception = new SecurityException();
		getNamesWithException(service, client, exception, new SecurityExceptionResponse("Exception", exception));
	}

	@Test
	public void subscribe(final @Mocked ALECCService service, final @Mocked ALECCServicePortType client) throws Exception {
		CC cc = createCC(service, client, null);
		final String subscriberName = "Subscriber";
		final String uri = "10.10.10.10";

		cc.subscribe(subscriberName, uri);

		new Verifications() {
			{
				Subscribe subscribe;
				client.subscribe(subscribe = withCapture());
				times = 1;

				Assert.assertEquals(subscriberName, subscribe.getSpecName());
				Assert.assertEquals(uri, subscribe.getNotificationURI());
			}
		};
	}

	private void subscribeWithException(final ALECCService service, final ALECCServicePortType client, Exception fault, final Exception response)
			throws Exception {
		CC cc = createCC(service, client, null);
		new NonStrictExpectations() {
			{
				client.subscribe(this.<Subscribe> withNotNull());
				result = response;
			}
		};

		try {
			cc.subscribe(null, null);
			Assert.fail("Expected " + fault.getClass().getSimpleName());
		} catch (Exception e) {
			Assert.assertSame(fault, e);
		}
	}

	@Test
	public void subscribeWithDuplicateSubscriptionException(final @Mocked ALECCService service, final @Mocked ALECCServicePortType client) throws Exception {
		DuplicateSubscriptionException exception = new DuplicateSubscriptionException();
		subscribeWithException(service, client, exception, new DuplicateSubscriptionExceptionResponse("Exception", exception));
	}

	@Test
	public void subscribeWithImplementationException(final @Mocked ALECCService service, final @Mocked ALECCServicePortType client) throws Exception {
		ImplementationException exception = new ImplementationException();
		subscribeWithException(service, client, exception, new ImplementationExceptionResponse("Exception", exception));
	}

	@Test
	public void subscribeWithInvalidURIException(final @Mocked ALECCService service, final @Mocked ALECCServicePortType client) throws Exception {
		InvalidURIException exception = new InvalidURIException();
		subscribeWithException(service, client, exception, new InvalidURIExceptionResponse("Exception", exception));
	}

	@Test
	public void subscribeWithNoSuchNameException(final @Mocked ALECCService service, final @Mocked ALECCServicePortType client) throws Exception {
		NoSuchNameException exception = new NoSuchNameException();
		subscribeWithException(service, client, exception, new NoSuchNameExceptionResponse("Exception", exception));
	}

	@Test
	public void subscribeWithParameterForbiddenException(final @Mocked ALECCService service, final @Mocked ALECCServicePortType client) throws Exception {
		ParameterForbiddenException exception = new ParameterForbiddenException();
		subscribeWithException(service, client, exception, new ParameterForbiddenExceptionResponse("Exception", exception));
	}

	@Test
	public void subscribeWithSecurityException(final @Mocked ALECCService service, final @Mocked ALECCServicePortType client) throws Exception {
		SecurityException exception = new SecurityException();
		subscribeWithException(service, client, exception, new SecurityExceptionResponse("Exception", exception));
	}

	@Test
	public void unsubscribe(final @Mocked ALECCService service, final @Mocked ALECCServicePortType client) throws Exception {
		CC cc = createCC(service, client, null);
		final String subscriberName = "Subscriber";
		final String uri = "10.10.10.10";

		cc.unsubscribe(subscriberName, uri);

		new Verifications() {
			{
				Unsubscribe unsubscribe;
				client.unsubscribe(unsubscribe = withCapture());
				times = 1;

				Assert.assertEquals(uri, unsubscribe.getNotificationURI());
				Assert.assertEquals(subscriberName, unsubscribe.getSpecName());
			}
		};
	}

	private void unsubscribeWithException(final ALECCService service, final ALECCServicePortType client, Exception fault, final Exception response)
			throws Exception {
		CC cc = createCC(service, client, null);
		new NonStrictExpectations() {
			{
				client.unsubscribe(this.<Unsubscribe> withNotNull());
				result = response;
			}
		};

		try {
			cc.unsubscribe(null, null);
			Assert.fail("Expected " + fault.getClass().getSimpleName());
		} catch (Exception e) {
			Assert.assertSame(fault, e);
		}
	}

	@Test
	public void unsubscribeWithImplementationException(final @Mocked ALECCService service, final @Mocked ALECCServicePortType client) throws Exception {
		ImplementationException exception = new ImplementationException();
		unsubscribeWithException(service, client, exception, new ImplementationExceptionResponse("Exception", exception));
	}

	@Test
	public void unsubscribeWithInvalidURIException(final @Mocked ALECCService service, final @Mocked ALECCServicePortType client) throws Exception {
		InvalidURIException exception = new InvalidURIException();
		unsubscribeWithException(service, client, exception, new InvalidURIExceptionResponse("Exception", exception));
	}

	@Test
	public void unsubscribeWithNoSuchNameException(final @Mocked ALECCService service, final @Mocked ALECCServicePortType client) throws Exception {
		NoSuchNameException exception = new NoSuchNameException();
		unsubscribeWithException(service, client, exception, new NoSuchNameExceptionResponse("Exception", exception));
	}

	@Test
	public void unsubscribeWithSecurityException(final @Mocked ALECCService service, final @Mocked ALECCServicePortType client) throws Exception {
		SecurityException exception = new SecurityException();
		unsubscribeWithException(service, client, exception, new SecurityExceptionResponse("Exception", exception));
	}

	@Test
	public void unsubscribeWithNoSuchSubscriberException(final @Mocked ALECCService service, final @Mocked ALECCServicePortType client) throws Exception {
		NoSuchSubscriberException exception = new NoSuchSubscriberException();
		unsubscribeWithException(service, client, exception, new NoSuchSubscriberExceptionResponse("Exception", exception));
	}
	
	@Test
	public void poll(final @Mocked ALECCService service, final @Mocked ALECCServicePortType client, final @Mocked CCReports expected) throws Exception {
		new NonStrictExpectations() {
			{
				client.poll(this.<Poll>withNotNull());
				result = expected;
			}
		};
		CC cc = createCC(service, client, null);
		final String name = "spec";
		final CCParameterListEntry entry = new CCParameterListEntry();
		final ArrayList<CCParameterListEntry> entries = new ArrayList<CCParameterListEntry>(
				Arrays.asList(entry)); 

		final CCReports actual = cc.poll(name, entries);

		new Verifications() {
			{
				Poll parms;
				client.poll(parms = withCapture());
				times = 1;

				Assert.assertEquals(entry, parms.getParams().getEntries().getEntry().get(0));
				Assert.assertEquals(entries, parms.getParams().getEntries().getEntry());
				Assert.assertEquals(name, parms.getSpecName());
				Assert.assertSame(expected, actual);
			}
		};
	}
	
	private void pollWithException(final ALECCService service, final ALECCServicePortType client, Exception fault, final Exception response)
			throws Exception {
		CC cc = createCC(service, client, null);
		new NonStrictExpectations() {
			{
				client.poll(this.<Poll> withNotNull());
				result = response;
			}
		};

		try {
			cc.poll(null,null);
			Assert.fail("Expected " + fault.getClass().getSimpleName());
		} catch (Exception e) {
			Assert.assertSame(fault, e);
		}
	}
	
	@Test
	public void pollWithImplementationException(final @Mocked ALECCService service, final @Mocked ALECCServicePortType client) throws Exception {
		ImplementationException exception = new ImplementationException();
		pollWithException(service, client, exception, new ImplementationExceptionResponse("Exception", exception));
	}

	@Test
	public void pollWithNoSuchNameException(final @Mocked ALECCService service, final @Mocked ALECCServicePortType client) throws Exception {
		NoSuchNameException exception = new NoSuchNameException();
		pollWithException(service, client, exception, new NoSuchNameExceptionResponse("Exception", exception));
	}

	@Test
	public void pollWithSecurityException(final @Mocked ALECCService service, final @Mocked ALECCServicePortType client) throws Exception {
		SecurityException exception = new SecurityException();
		pollWithException(service, client, exception, new SecurityExceptionResponse("Exception", exception));
	}

	@Test
	public void pollWithParameterException(final @Mocked ALECCService service, final @Mocked ALECCServicePortType client) throws Exception {
		ParameterException exception = new ParameterException();
		pollWithException(service, client, exception, new ParameterExceptionResponse("Exception", exception));
	}
	
	@Test
	public void immediate(final @Mocked ALECCService service, final @Mocked ALECCServicePortType client, final @Mocked CCReports expected) throws Exception {
		new NonStrictExpectations() {
			{
				client.immediate(this.<Immediate>withNotNull());
				result = expected;
			}
		};
		CC cc = createCC(service, client, null);
		final CCSpec _spec = createCCSpecWithDuration();

		final CCReports actual = cc.immediate(_spec);

		new Verifications() {
			{
				Immediate immediate;
				client.immediate(immediate = withCapture());
				times = 1;

				Assert.assertSame(_spec, immediate.getSpec());
				Assert.assertSame(expected, actual);
			}
		};
	}

	private void immediateWithException(final ALECCService service, final ALECCServicePortType client, Exception fault, final Exception response)
			throws Exception {
		CC cc = createCC(service, client, null);
		new NonStrictExpectations() {
			{
				client.immediate(this.<Immediate> withNotNull());
				result = response;
			}
		};

		try {
			cc.immediate(null);
			Assert.fail("Expected " + fault.getClass().getSimpleName());
		} catch (Exception e) {
			Assert.assertSame(fault, e);
		}
	}

	@Test
	public void immediateWithNoSuchSubscriberException(final @Mocked ALECCService service, final @Mocked ALECCServicePortType client) throws Exception {
		ValidationException exception = new ValidationException();
		immediateWithException(service, client, exception, new CCSpecValidationExceptionResponse("Exception", exception));
	}

	@Test
	public void immediateWithImplementationException(final @Mocked ALECCService service, final @Mocked ALECCServicePortType client) throws Exception {
		ImplementationException exception = new ImplementationException();
		immediateWithException(service, client, exception, new ImplementationExceptionResponse("Exception", exception));
	}

	@Test
	public void immediateWithParameterForbiddenException(final @Mocked ALECCService service, final @Mocked ALECCServicePortType client) throws Exception {
		ParameterForbiddenException exception = new ParameterForbiddenException();
		immediateWithException(service, client, exception, new ParameterForbiddenExceptionResponse("Exception", exception));
	}

	@Test
	public void immediateWithSecurityException(final @Mocked ALECCService service, final @Mocked ALECCServicePortType client) throws Exception {
		SecurityException exception = new SecurityException();
		immediateWithException(service, client, exception, new SecurityExceptionResponse("Exception", exception));
	}

	@Test
	public void getSubscribers(final @Mocked ALECCService service, final @Mocked ALECCServicePortType client, final @Mocked ArrayOfString string) throws Exception {
		new NonStrictExpectations() {
			{
				client.getSubscribers(this.<GetSubscribers>withNotNull());
				result = string;
			}
		};
		CC cc = createCC(service, client, null);
		final String subscriberName = "Test";

		final List<String> list = cc.getSubscribers(subscriberName);

		new Verifications() {
			{
				GetSubscribers parms;
				client.getSubscribers(parms = withCapture());
				times = 1;

				Assert.assertEquals(subscriberName, parms.getSpecName());
				Assert.assertEquals(string.getString(), list);
			}
		};
	}

	@Test
	public void getSubscribersWithNull(final @Mocked ALECCService service, final @Mocked ALECCServicePortType client, final @Mocked ArrayOfString string)throws Exception {
		CC cc = createCC(service, client, null);
		new NonStrictExpectations() {
			{
				client.getSubscribers(this.<GetSubscribers> withNotNull());
				result = null;
				times = 1;
			}
		};

		final List<String> subscriberList = cc.getSubscribers("Test");

		new Verifications() {
			{
				client.getSubscribers(this.<GetSubscribers> withNotNull());
				times = 1;
				string.getString();
				times = 0;

				Assert.assertNull(subscriberList);
			}
		};
	}

	private void getSubscribersWithException(final ALECCService service, final ALECCServicePortType client, Exception fault, final Exception response)
			throws Exception {
		CC cc = createCC(service, client, null);
		new NonStrictExpectations() {
			{
				client.getSubscribers(this.<GetSubscribers> withNotNull());
				result = response;
			}
		};

		try {
			cc.getSubscribers(null);
			Assert.fail("Expected " + fault.getClass().getSimpleName());
		} catch (Exception e) {
			Assert.assertSame(fault, e);
		}
	}

	@Test
	public void getSubscriberseWithImplementationException(final @Mocked ALECCService service, final @Mocked ALECCServicePortType client) throws Exception {
		ImplementationException exception = new ImplementationException();
		getSubscribersWithException(service, client, exception, new ImplementationExceptionResponse("Exception", exception));
	}

	@Test
	public void getSubscriberseWithNoSuchNameException(final @Mocked ALECCService service, final @Mocked ALECCServicePortType client) throws Exception {
		NoSuchNameException exception = new NoSuchNameException();
		getSubscribersWithException(service, client, exception, new NoSuchNameExceptionResponse("Exception", exception));
	}

	@Test
	public void getSubscriberseWithSecurityException(final @Mocked ALECCService service, final @Mocked ALECCServicePortType client) throws Exception {
		SecurityException exception = new SecurityException();
		getSubscribersWithException(service, client, exception, new SecurityExceptionResponse("Exception", exception));
	}

	@Test
	public void getStandardVersion(final @Mocked ALECCService service, final @Mocked ALECCServicePortType client) throws Exception {
		CC cc = createCC(service, client, null);

		final String standardVersion = cc.getStandardVersion();

		new Verifications() {
			{
				client.getStandardVersion(this.<EmptyParms> withNotNull());
				times = 1;

				Assert.assertNull(standardVersion);
			}
		};
	}

	private void getStandardVersionWithException(final ALECCService service, final ALECCServicePortType client, Exception fault, final Exception response)
			throws Exception {
		CC cc = createCC(service, client, null);
		new NonStrictExpectations() {
			{
				client.getStandardVersion(this.<EmptyParms> withNotNull());
				result = response;
			}
		};

		try {
			cc.getStandardVersion();
			Assert.fail("Expected " + fault.getClass().getSimpleName());
		} catch (Exception e) {
			Assert.assertSame(fault, e);
		}
	}

	@Test
	public void getStandardVersionWithImplementationException(final @Mocked ALECCService service, final @Mocked ALECCServicePortType client) throws Exception {
		ImplementationException exception = new ImplementationException();
		getStandardVersionWithException(service, client, exception, new ImplementationExceptionResponse("Exception", exception));
	}

	@Test
	public void getVendorVersion(final @Mocked ALECCService service, final @Mocked ALECCServicePortType client) throws Exception {
		CC cc = createCC(service, client, null);

		final String standardVersion = cc.getVendorVersion();

		new Verifications() {
			{
				client.getVendorVersion(this.<EmptyParms> withNotNull());
				times = 1;

				Assert.assertNull(standardVersion);
			}
		};
	}

	private void getVendorVersionWithException(final ALECCService service, final ALECCServicePortType client, Exception fault, final Exception response)
			throws Exception {
		CC cc = createCC(service, client, null);
		new NonStrictExpectations() {
			{
				client.getVendorVersion(this.<EmptyParms> withNotNull());
				result = response;
			}
		};

		try {
			cc.getVendorVersion();
			Assert.fail("Expected " + fault.getClass().getSimpleName());
		} catch (Exception e) {
			Assert.assertSame(fault, e);
		}
	}

	@Test
	public void getVendorVersionImplementationException(final @Mocked ALECCService service, final @Mocked ALECCServicePortType client) throws Exception {
		ImplementationException exception = new ImplementationException();
		getVendorVersionWithException(service, client, exception, new ImplementationExceptionResponse("Exception", exception));
	}

	@Test
	public void defineEPCCache(final @Mocked ALECCService service, final @Mocked ALECCServicePortType client) throws Exception {
		CC cc = createCC(service, client, null);
		final EPCCacheSpec epcCacheSpec = new EPCCacheSpec();
		final String epcCacheName = "EPCCache";
		final ArrayList<String> expectedPattern = new ArrayList<String>(
				Arrays.asList("Test"));

		epcCacheSpec.setSchemaVersion(new BigDecimal(9999));
		cc.defineEPCCache(epcCacheName, epcCacheSpec, expectedPattern);

		new Verifications() {
			{
				DefineEPCCache parms;
				client.defineEPCCache(parms = withCapture());
				times = 1;

				Assert.assertEquals(epcCacheName, parms.getCacheName());
				Assert.assertSame(epcCacheSpec, parms.getSpec());
				
				List<String> actualPattern = parms.getReplenishment().getPatterns().getPattern();
				Assert.assertEquals(expectedPattern,actualPattern);
			}
		};
	}
	
	private void defineEPCCacheWithException(final ALECCService service, final ALECCServicePortType client, Exception fault, final Exception response)throws Exception {
		CC cc = createCC(service, client, null);
		new NonStrictExpectations() {
			{
				client.defineEPCCache(this.<DefineEPCCache> withNotNull());
				result = response;
			}
		};

		try {
			cc.defineEPCCache(null, null, null);
			Assert.fail("Expected " + fault.getClass().getSimpleName());
		} catch (Exception e) {
			Assert.assertSame(fault, e);
		}
	}
	
	@Test
	public void defineEPCCacheWithDuplicateNameException(final @Mocked ALECCService service, final @Mocked ALECCServicePortType client) throws Exception {
		DuplicateNameException exception = new DuplicateNameException();
		defineEPCCacheWithException(service, client, exception, new DuplicateNameExceptionResponse("Exception", exception));
	}
	@Test
	public void defineEPCCacheWithValidationException(final @Mocked ALECCService service, final @Mocked ALECCServicePortType client) throws Exception {
		EPCCacheSpecValidationException exception = new EPCCacheSpecValidationException();
		defineEPCCacheWithException(service, client, exception, new EPCCacheSpecValidationExceptionResponse("Exception", exception));
	}
	@Test
	public void defineEPCCacheWithImplementationException(final @Mocked ALECCService service, final @Mocked ALECCServicePortType client) throws Exception {
		ImplementationException exception = new ImplementationException();
		defineEPCCacheWithException(service, client, exception, new ImplementationExceptionResponse("Exception", exception));
	}
	@Test
	public void defineEPCCacheWithInvalidPatternException(final @Mocked ALECCService service, final @Mocked ALECCServicePortType client) throws Exception {
		InvalidPatternException exception = new InvalidPatternException();
		defineEPCCacheWithException(service, client, exception, new InvalidPatternExceptionResponse("Exception", exception));
	}
	@Test
	public void defineEPCCacheWithSecurityException(final @Mocked ALECCService service, final @Mocked ALECCServicePortType client) throws Exception {
		SecurityException exception = new SecurityException();
		defineEPCCacheWithException(service, client, exception, new SecurityExceptionResponse("Exception", exception));
	}

	@Test
	public void undefineEPCCache(final @Mocked ALECCService service, final @Mocked ALECCServicePortType client, final @Mocked List<String> expected, final @Mocked EPCPatternList list, final @Mocked Patterns patterns) throws Exception {
		new NonStrictExpectations() {
			{
				patterns.getPattern();
				result = expected;
			}
		};
		CC cc = createCC(service, client, null);
		final String epcCacheName = "EPCCache";
		
		final List<String> actual = cc.undefineEPCCache(epcCacheName);

		new Verifications() {
			{
				UndefineEPCCache parms;
				client.undefineEPCCache(parms = withCapture());
				times = 1;
				list.getPatterns();
				times = 1;

				Assert.assertEquals(epcCacheName, parms.getCacheName());
				Assert.assertSame(expected, actual);
			}
		};
	}
	
	@Test
	public void undefineEPCCacheWithNull(final @Mocked ALECCService service, final @Mocked ALECCServicePortType client, final @Mocked EPCPatternList patternList) throws Exception {
		new NonStrictExpectations() {
			{
				client.undefineEPCCache(this.<UndefineEPCCache>withNotNull());
				result = null;
				times = 1;
			}
		};
		CC cc = createCC(service, client, null);
		final String epcCacheName = "EPCCache";
		
		final List<String> list = cc.undefineEPCCache(epcCacheName);

		new Verifications() {
			{
				client.undefineEPCCache(this.<UndefineEPCCache>withNotNull());
				times = 1;
				patternList.getPatterns();
				times = 0;
				

				Assert.assertNull(list);
			}
		};
	}
	
	private void undefineEPCCacheWithException(final ALECCService service, final ALECCServicePortType client, Exception fault, final Exception response)throws Exception {
		CC cc = createCC(service, client, null);
		new NonStrictExpectations() {
			{
				client.undefineEPCCache(this.<UndefineEPCCache>withNotNull());
				result = response;
			}
		};

		try {
			cc.undefineEPCCache(null);
			Assert.fail("Expected " + fault.getClass().getSimpleName());
		} catch (Exception e) {
			Assert.assertSame(fault, e);
		}
	}
	
	@Test
	public void undefineEPCCacheWithImplementationException(final @Mocked ALECCService service, final @Mocked ALECCServicePortType client) throws Exception {
		ImplementationException exception = new ImplementationException();
		undefineEPCCacheWithException(service, client, exception, new ImplementationExceptionResponse("Exception", exception));
	}
	@Test
	public void undefineEPCCacheWithInUseException(final @Mocked ALECCService service, final @Mocked ALECCServicePortType client) throws Exception {
		InUseException exception = new InUseException();
		undefineEPCCacheWithException(service, client, exception, new InUseExceptionResponse("Exception", exception));
	}
	@Test
	public void undefineEPCCacheWithNoSuchNameException(final @Mocked ALECCService service, final @Mocked ALECCServicePortType client) throws Exception {
		NoSuchNameException exception = new NoSuchNameException();
		undefineEPCCacheWithException(service, client, exception, new NoSuchNameExceptionResponse("Exception", exception));
	}
	@Test
	public void undefineEPCCacheWithSecurityException(final @Mocked ALECCService service, final @Mocked ALECCServicePortType client) throws Exception {
		SecurityException exception = new SecurityException();
		undefineEPCCacheWithException(service, client, exception, new SecurityExceptionResponse("Exception", exception));
	}
	
	@Test
	public void getEPCCache(final @Mocked ALECCService service, final @Mocked ALECCServicePortType client, final @Mocked EPCCacheSpec expected) throws Exception {
	    new NonStrictExpectations() {
	    	{
	    		client.getEPCCache(this.<GetEPCCache>withNotNull());
	    		result = expected;
	    	}
		};
		CC cc = createCC(service, client, null);
		final String epcCacheName = "EPCCache";
		
		final EPCCacheSpec actual = cc.getEPCCache(epcCacheName);

		new Verifications() {
			{
				GetEPCCache parms;
				client.getEPCCache(parms = withCapture());
				times = 1;

				Assert.assertEquals(epcCacheName, parms.getCacheName());
				Assert.assertEquals(expected, actual);
			}
		};
	}
	
	private void getEPCCacheWithException(final ALECCService service, final ALECCServicePortType client, Exception fault, final Exception response)throws Exception {
		CC cc = createCC(service, client, null);
		new NonStrictExpectations() {
			{
				client.getEPCCache(this.<GetEPCCache>withNotNull());
				result = response;
			}
		};

		try {
			cc.getEPCCache(null);
			Assert.fail("Expected " + fault.getClass().getSimpleName());
		} catch (Exception e) {
			Assert.assertSame(fault, e);
		}
	}
	
	@Test
	public void getEPCCacheWithImplementationException(final @Mocked ALECCService service, final @Mocked ALECCServicePortType client) throws Exception {
		ImplementationException exception = new ImplementationException();
		getEPCCacheWithException(service, client, exception, new ImplementationExceptionResponse("Exception", exception));
	}
	@Test
	public void getEPCCacheWithNoSuchNameException(final @Mocked ALECCService service, final @Mocked ALECCServicePortType client) throws Exception {
		NoSuchNameException exception = new NoSuchNameException();
		getEPCCacheWithException(service, client, exception, new NoSuchNameExceptionResponse("Exception", exception));
	}
	@Test
	public void getEPCCacheWithSecurityException(final @Mocked ALECCService service, final @Mocked ALECCServicePortType client) throws Exception {
		SecurityException exception = new SecurityException();
		getEPCCacheWithException(service, client, exception, new SecurityExceptionResponse("Exception", exception));
	}
	
	@Test
	public void getEPCCacheNames(final @Mocked ALECCService service, final @Mocked ALECCServicePortType client, final @Mocked ArrayOfString string) throws Exception {
		new NonStrictExpectations() {
			{
				client.getEPCCacheNames(this.<EmptyParms>withNotNull());
				result = string;
			}
		};
		CC cc = createCC(service, client, null);
		
		final List<String> list = cc.getEPCCacheNames();

		new Verifications() {
			{
				client.getEPCCacheNames(this.<EmptyParms>withNotNull());
				times = 1;
				string.getString();
				times = 1;

				Assert.assertEquals(string.getString(), list);
			}
		};
	}
	
	@Test
	public void getEPCCacheNamesWithNull(final @Mocked ALECCService service, final @Mocked ALECCServicePortType client, final @Mocked ArrayOfString string ) throws Exception {
		new NonStrictExpectations() {
			{
				client.getEPCCacheNames(this.<EmptyParms>withNotNull());
				result = null;
				times = 1;
			}
		};
		CC cc = createCC(service, client, null);
		
		final List<String> list = cc.getEPCCacheNames();

		new Verifications() {
			{
				client.getEPCCacheNames(this.<EmptyParms>withNotNull());
				times = 1;
				string.getString();
				times = 0;

				Assert.assertNull(list);
			}
		};
	}
	
	private void getEPCCacheNamesWithException(final ALECCService service, final ALECCServicePortType client, Exception fault, final Exception response)throws Exception {
		CC cc = createCC(service, client, null);
		new NonStrictExpectations() {
			{
				client.getEPCCacheNames(this.<EmptyParms>withNotNull());
				result = response;
			}
		};

		try {
			cc.getEPCCacheNames();
			Assert.fail("Expected " + fault.getClass().getSimpleName());
		} catch (Exception e) {
			Assert.assertSame(fault, e);
		}
	}
	
	@Test
	public void getEPCCacheNamesWithSecurityException(final @Mocked ALECCService service, final @Mocked ALECCServicePortType client) throws Exception {
		SecurityException exception = new SecurityException();
		getEPCCacheNamesWithException(service, client, exception, new SecurityExceptionResponse("Exception", exception));
	}
	@Test
	public void getEPCCacheNamesWithImplementationException(final @Mocked ALECCService service, final @Mocked ALECCServicePortType client) throws Exception {
		ImplementationException exception = new ImplementationException();
		getEPCCacheNamesWithException(service, client, exception, new ImplementationExceptionResponse("Exception", exception));
	}
	
	@Test
	public void replenishEPCCache(final @Mocked ALECCService service, final @Mocked ALECCServicePortType client) throws Exception {
		CC cc = createCC(service, client, null);
		final String epcCacheName = "epcCache";
		final ArrayList<String> expectedPattern = new ArrayList<String>(
				Arrays.asList("Test"));
		
		cc.replenishEPCCache(epcCacheName, expectedPattern);

		new Verifications() {
			{
				ReplenishEPCCache parms; 
				client.replenishEPCCache(parms = withCapture());
				times = 1;
				
				Assert.assertEquals(epcCacheName, parms.getCacheName());
				
				List<String> actualPattern = parms.getReplenishment().getPatterns().getPattern();
				Assert.assertEquals(expectedPattern,actualPattern);
			}
		};
	}
	
	private void replenishEPCCacheWithException(final ALECCService service, final ALECCServicePortType client, Exception fault, final Exception response)throws Exception {
		CC cc = createCC(service, client, null);
		new NonStrictExpectations() {
			{
				client.replenishEPCCache(this.<ReplenishEPCCache>withNotNull());
				result = response;
				times = 1;
			}
		};

		try {
			cc.replenishEPCCache(null,null);
			Assert.fail("Expected " + fault.getClass().getSimpleName());
		} catch (Exception e) {
			Assert.assertSame(fault, e);
		}
	}
	
	@Test
	public void replenishEPCCacheWithImplementationException(final @Mocked ALECCService service, final @Mocked ALECCServicePortType client) throws Exception {
		ImplementationException exception = new ImplementationException();
		replenishEPCCacheWithException(service, client, exception, new ImplementationExceptionResponse("Exception", exception));
	}
	@Test
	public void replenishEPCCacheWithInvalidPatternException(final @Mocked ALECCService service, final @Mocked ALECCServicePortType client) throws Exception {
		InvalidPatternException exception = new InvalidPatternException();
		replenishEPCCacheWithException(service, client, exception, new InvalidPatternExceptionResponse("Exception", exception));
	}
	@Test
	public void replenishEPCCacheWithNoSuchNameException(final @Mocked ALECCService service, final @Mocked ALECCServicePortType client) throws Exception {
		NoSuchNameException exception = new NoSuchNameException();
		replenishEPCCacheWithException(service, client, exception, new NoSuchNameExceptionResponse("Exception", exception));
	}
	@Test
	public void replenishEPCCacheWithSecurityException(final @Mocked ALECCService service, final @Mocked ALECCServicePortType client) throws Exception {
		SecurityException exception = new SecurityException();
		replenishEPCCacheWithException(service, client, exception, new SecurityExceptionResponse("Exception", exception));
	}
	
	@Test
	public void depleteEPCCache(final @Mocked ALECCService service, final @Mocked ALECCServicePortType client, final @Mocked EPCPatternList patternList) throws Exception {
		new NonStrictExpectations() {
			{
				client.depleteEPCCache(this.<DepleteEPCCache>withNotNull());
				result = patternList;
			}
		};
		CC cc = createCC(service, client, null);
		final String epcCacheName = "epcCache";
		
		final List<String> list = cc.depleteEPCCache(epcCacheName);
 
		new Verifications() {
			{
				DepleteEPCCache parms; 
				client.depleteEPCCache(parms = withCapture());
				times = 1;
				patternList.getPatterns();
				times = 1;
				
				Assert.assertEquals(epcCacheName, parms.getCacheName());
				Assert.assertEquals(patternList.getPatterns().getPattern(), list);
			}
		};
	}
	
	@Test
	public void depleteEPCCacheWithNull(final @Mocked ALECCService service, final @Mocked ALECCServicePortType client, final @Mocked EPCPatternList patternList) throws Exception {
		new NonStrictExpectations() {
			{
				client.depleteEPCCache(this.<DepleteEPCCache>withNotNull());
				result = null;
			}
		};
		CC cc = createCC(service, client, null);
		final String epcCacheName = "epcCache";
		
		final List<String> list = cc.depleteEPCCache(epcCacheName);
 
		new Verifications() {
			{
				DepleteEPCCache parms; 
				client.depleteEPCCache(parms = withCapture());
				times = 1;
				patternList.getPatterns();
				times = 0;
				
				Assert.assertEquals(epcCacheName, parms.getCacheName());
				Assert.assertNull(list);
			}
		};
	}
	
	private void depleteEPCCacheWithException(final ALECCService service, final ALECCServicePortType client, Exception fault, final Exception response)throws Exception {
		CC cc = createCC(service, client, null);
		new NonStrictExpectations() {
			{
				client.depleteEPCCache(this.<DepleteEPCCache>withNotNull());
				result = response;
				times = 1;
			}
		};

		try {
			cc.depleteEPCCache(null);
			Assert.fail("Expected " + fault.getClass().getSimpleName());
		} catch (Exception e) {
			Assert.assertSame(fault, e);
		}
	}
	
	@Test
	public void depleteEPCCacheWithImplementationException(final @Mocked ALECCService service, final @Mocked ALECCServicePortType client) throws Exception {
		ImplementationException exception = new ImplementationException();
		depleteEPCCacheWithException(service, client, exception, new ImplementationExceptionResponse("Exception", exception));
	}@Test
	public void depleteEPCCacheWithNoSuchNameException(final @Mocked ALECCService service, final @Mocked ALECCServicePortType client) throws Exception {
		NoSuchNameException exception = new NoSuchNameException();
		depleteEPCCacheWithException(service, client, exception, new NoSuchNameExceptionResponse("Exception", exception));
	}
	@Test
	public void depleteEPCCacheWithSecurityException(final @Mocked ALECCService service, final @Mocked ALECCServicePortType client) throws Exception {
		SecurityException exception = new SecurityException();
		depleteEPCCacheWithException(service, client, exception, new SecurityExceptionResponse("Exception", exception));
	}
	
	@Test
	public void getEPCCacheContents(final @Mocked ALECCService service, final @Mocked ALECCServicePortType client, final @Mocked EPCPatternList patternList) throws Exception {
		new NonStrictExpectations() {
			{
				client.getEPCCacheContents(this.<GetEPCCacheContents>withNotNull());
				result = patternList;
			}
		};
		CC cc = createCC(service, client, null);
		final String epcCacheName = "epcCache";
		
		final List<String> list = cc.getEPCCacheContents(epcCacheName);
 
		new Verifications() {
			{
				GetEPCCacheContents parms; 
				client.getEPCCacheContents(parms = withCapture());
				times = 1;
				patternList.getPatterns();
				times = 1;
				
				Assert.assertEquals(epcCacheName, parms.getCacheName());
				Assert.assertEquals(patternList.getPatterns().getPattern(), list);
			}
		};
	}
	
	@Test
	public void getEPCCacheContentsWithNull(final @Mocked ALECCService service, final @Mocked ALECCServicePortType client, final @Mocked EPCPatternList patternList) throws Exception {
		new NonStrictExpectations() {
			{
				client.getEPCCacheContents(this.<GetEPCCacheContents>withNotNull());
				result = null;
			}
		};
		CC cc = createCC(service, client, null);
		final String epcCacheName = "epcCache";
		
		final List<String> list = cc.getEPCCacheContents(epcCacheName);
 
		new Verifications() {
			{
				GetEPCCacheContents parms; 
				client.getEPCCacheContents(parms = withCapture());
				times = 1;
				patternList.getPatterns();
				times = 0;
				
				Assert.assertEquals(epcCacheName, parms.getCacheName());
				Assert.assertNull(list);
			}
		};
	}
	
	private void getEPCCacheContentsWithException(final ALECCService service, final ALECCServicePortType client, Exception fault, final Exception response)throws Exception {
		CC cc = createCC(service, client, null);
		new NonStrictExpectations() {
			{
				client.getEPCCacheContents(this.<GetEPCCacheContents>withNotNull());
				result = response;
				times = 1;
			}
		};

		try {
			cc.getEPCCacheContents(null);
			Assert.fail("Expected " + fault.getClass().getSimpleName());
		} catch (Exception e) {
			Assert.assertSame(fault, e);
		}
	}
	
	@Test
	public void getEPCCacheContentsWithImplementationException(final @Mocked ALECCService service, final @Mocked ALECCServicePortType client) throws Exception {
		ImplementationException exception = new ImplementationException();
		getEPCCacheContentsWithException(service, client, exception, new ImplementationExceptionResponse("Exception", exception));
	}
	@Test
	public void getEPCCacheContentsWithNoSuchNameException(final @Mocked ALECCService service, final @Mocked ALECCServicePortType client) throws Exception {
		NoSuchNameException exception = new NoSuchNameException();
		getEPCCacheContentsWithException(service, client, exception, new NoSuchNameExceptionResponse("Exception", exception));
	}
	@Test
	public void getEPCCacheContentsWithSecurityException(final @Mocked ALECCService service, final @Mocked ALECCServicePortType client) throws Exception {
		SecurityException exception = new SecurityException();
		getEPCCacheContentsWithException(service, client, exception, new SecurityExceptionResponse("Exception", exception));
	}
	
	@Test
	public void defineAssocTable(final @Mocked ALECCService service, final @Mocked ALECCServicePortType client) throws Exception {
		CC cc = createCC(service, client, null);
		final String name = "assoc";
		final AssocTableSpec assocSpec = new AssocTableSpec();
		final AssocTableEntry assocEntry = new AssocTableEntry();
		final ArrayList<AssocTableEntry> assocEntries = new ArrayList<AssocTableEntry>(
				Arrays.asList(assocEntry));
		
		cc.defineAssocTable(name,assocSpec,assocEntries);
 
		new Verifications() {
			{
				DefineAssocTable parms; 
				client.defineAssocTable(parms = withCapture());
				times = 1;
				
				Assert.assertEquals(name, parms.getTableName());
				Assert.assertSame(assocSpec, parms.getSpec());
				Assert.assertEquals(assocEntries, parms.getEntries().getEntries().getEntry());
			}
		};
	}
	
	private void defineAssocTableWithException(final ALECCService service, final ALECCServicePortType client, Exception fault, final Exception response)throws Exception {
		CC cc = createCC(service, client, null);
		new NonStrictExpectations() {
			{
				client.defineAssocTable(this.<DefineAssocTable>withNotNull());
				result = response;
				times = 1;
			}
		};

		try {
			cc.defineAssocTable(null,null,null);
			Assert.fail("Expected " + fault.getClass().getSimpleName());
		} catch (Exception e) {
			Assert.assertSame(fault, e);
		}
	}
	
	@Test
	public void defineAssocTableWithDuplicateNameException(final @Mocked ALECCService service, final @Mocked ALECCServicePortType client) throws Exception {
		DuplicateNameException exception = new DuplicateNameException();
		defineAssocTableWithException(service, client, exception, new DuplicateNameExceptionResponse("Exception", exception));
	}
	@Test
	public void defineAssocTableWithAssocTableValidationException(final @Mocked ALECCService service, final @Mocked ALECCServicePortType client) throws Exception {
		AssocTableValidationException exception = new AssocTableValidationException();
		defineAssocTableWithException(service, client, exception, new AssocTableValidationExceptionResponse("Exception", exception));
	}
	@Test
	public void defineAssocTableWithImplementationException(final @Mocked ALECCService service, final @Mocked ALECCServicePortType client) throws Exception {
		ImplementationException exception = new ImplementationException();
		defineAssocTableWithException(service, client, exception, new ImplementationExceptionResponse("Exception", exception));
	}
	@Test
	public void defineAssocTableWithInvalidAssocTableEntryException(final @Mocked ALECCService service, final @Mocked ALECCServicePortType client) throws Exception {
		InvalidAssocTableEntryException exception = new InvalidAssocTableEntryException();
		defineAssocTableWithException(service, client, exception, new InvalidAssocTableEntryExceptionResponse("Exception", exception));
	}
	@Test
	public void defineAssocTableWithSecurityException(final @Mocked ALECCService service, final @Mocked ALECCServicePortType client) throws Exception {
		SecurityException exception = new SecurityException();
		defineAssocTableWithException(service, client, exception, new SecurityExceptionResponse("Exception", exception));
	}
	
	@Test
	public void undefineAssocTable(final @Mocked ALECCService service, final @Mocked ALECCServicePortType client) throws Exception {
		CC cc = createCC(service, client, null);
		final String name = "assoc";
		
		
		cc.undefineAssocTable(name);
 
		new Verifications() {
			{
				UndefineAssocTable parms;
				client.undefineAssocTable(parms = withCapture());
				times = 1;
				
				Assert.assertEquals(name, parms.getTableName());
			}
		};
	}
	
	private void undefineAssocTableWithException(final ALECCService service, final ALECCServicePortType client, Exception fault, final Exception response)throws Exception {
		CC cc = createCC(service, client, null);
		new NonStrictExpectations() {
			{
				client.undefineAssocTable(this.<UndefineAssocTable>withNotNull());
				result = response;
				times = 1;
			}
		};

		try {
			cc.undefineAssocTable(null);
			Assert.fail("Expected " + fault.getClass().getSimpleName());
		} catch (Exception e) {
			Assert.assertSame(fault, e);
		}
	}
	
	@Test
	public void undefineAssocTableWithImplementationException(final @Mocked ALECCService service, final @Mocked ALECCServicePortType client) throws Exception {
		ImplementationException exception = new ImplementationException();
		undefineAssocTableWithException(service, client, exception, new ImplementationExceptionResponse("Exception", exception));
	}
	@Test
	public void undefineAssocTableWithInUseException(final @Mocked ALECCService service, final @Mocked ALECCServicePortType client) throws Exception {
		InUseException exception = new InUseException();
		undefineAssocTableWithException(service, client, exception, new InUseExceptionResponse("Exception", exception));
	}
	@Test
	public void undefineAssocTableWithNoSuchNameException(final @Mocked ALECCService service, final @Mocked ALECCServicePortType client) throws Exception {
		NoSuchNameException exception = new NoSuchNameException();
		undefineAssocTableWithException(service, client, exception, new NoSuchNameExceptionResponse("Exception", exception));
	}
	@Test
	public void undefineAssocTableWithSecurityException(final @Mocked ALECCService service, final @Mocked ALECCServicePortType client) throws Exception {
		SecurityException exception = new SecurityException();
		undefineAssocTableWithException(service, client, exception, new SecurityExceptionResponse("Exception", exception));
	}
	
	@Test
	public void getAssocTableNames(final @Mocked ALECCService service, final @Mocked ALECCServicePortType client, final @Mocked ArrayOfString string) throws Exception {
		new NonStrictExpectations() {
			{
				client.getAssocTableNames(this.<EmptyParms>withNotNull());
				result = string;
			}
		};
		CC cc = createCC(service, client, null);
		
		final List<String> list = cc.getAssocTableNames();
 
		new Verifications() {
			{
				client.getAssocTableNames(this.<EmptyParms>withNotNull());
				times = 1;
				string.getString();
				times = 1;
				
				Assert.assertEquals(string.getString(), list);
			}
		};
	}
	
	@Test
	public void getAssocTableNamesWithNull(final @Mocked ALECCService service, final @Mocked ALECCServicePortType client, final @Mocked ArrayOfString string) throws Exception {
		new NonStrictExpectations() {
			{
				client.getAssocTableNames(this.<EmptyParms>withNotNull());
				result = null;
			}
		};
		CC cc = createCC(service, client, null);
		
		final List<String> list = cc.getAssocTableNames();
 
		new Verifications() {
			{
				client.getAssocTableNames(this.<EmptyParms>withNotNull());
				times = 1;
				string.getString();
				times = 0;
				
				Assert.assertNull(list);
			}
		};
	}
	
	private void getAssocTableNamesWithException(final ALECCService service, final ALECCServicePortType client, Exception fault, final Exception response)throws Exception {
		CC cc = createCC(service, client, null);
		new NonStrictExpectations() {
			{
				client.getAssocTableNames(this.<EmptyParms>withNotNull());
				result = response;
				times = 1;
			}
		};

		try {
			cc.getAssocTableNames();
			Assert.fail("Expected " + fault.getClass().getSimpleName());
		} catch (Exception e) {
			Assert.assertSame(fault, e);
		}
	}
	
	@Test
	public void getAssocTableNamesWithImplementationException(final @Mocked ALECCService service, final @Mocked ALECCServicePortType client) throws Exception {
		ImplementationException exception = new ImplementationException();
		getAssocTableNamesWithException(service, client, exception, new ImplementationExceptionResponse("Exception", exception));
	}
	@Test
	public void getAssocTableNamesWithSecurityException(final @Mocked ALECCService service, final @Mocked ALECCServicePortType client) throws Exception {
		SecurityException exception = new SecurityException();
		getAssocTableNamesWithException(service, client, exception, new SecurityExceptionResponse("Exception", exception));
	}
	
	@Test
	public void getAssocTable(final @Mocked ALECCService service, final @Mocked ALECCServicePortType client, final @Mocked AssocTableSpec expected) throws Exception {
		new NonStrictExpectations() {
			{
				client.getAssocTable(this.<GetAssocTable>withNotNull());
				result = expected;
			}
		};
		CC cc = createCC(service, client, null);
		final String name = "assoc";
		
		final AssocTableSpec actual = cc.getAssocTable(name);
 
		new Verifications() {
			{
				GetAssocTable parms;
				client.getAssocTable(parms = withCapture());
				times = 1;
				
				Assert.assertEquals(name, parms.getTableName());
				Assert.assertSame(expected, actual);
			}
		};
	}
	
	private void getAssocTableWithException(final ALECCService service, final ALECCServicePortType client, Exception fault, final Exception response)throws Exception {
		CC cc = createCC(service, client, null);
		new NonStrictExpectations() {
			{
				client.getAssocTable(this.<GetAssocTable>withNotNull());
				result = response;
				times = 1;
			}
		};

		try {
			cc.getAssocTable(null);
			Assert.fail("Expected " + fault.getClass().getSimpleName());
		} catch (Exception e) {
			Assert.assertSame(fault, e);
		}
	}
	
	@Test
	public void getAssocTableWithImplementationException(final @Mocked ALECCService service, final @Mocked ALECCServicePortType client) throws Exception {
		ImplementationException exception = new ImplementationException();
		getAssocTableWithException(service, client, exception, new ImplementationExceptionResponse("Exception", exception));
	}
	@Test
	public void getAssocTableWithNoSuchNameException(final @Mocked ALECCService service, final @Mocked ALECCServicePortType client) throws Exception {
		NoSuchNameException exception = new NoSuchNameException();
		getAssocTableWithException(service, client, exception, new NoSuchNameExceptionResponse("Exception", exception));
	}
	@Test
	public void getAssocTableWithSecurityException(final @Mocked ALECCService service, final @Mocked ALECCServicePortType client) throws Exception {
		SecurityException exception = new SecurityException();
		getAssocTableWithException(service, client, exception, new SecurityExceptionResponse("Exception", exception));
	}
	
	@Test
	public void putAssocTableEntries(final @Mocked ALECCService service, final @Mocked ALECCServicePortType client) throws Exception {
		CC cc = createCC(service, client, null);
		final String name = "assoc";
		final AssocTableEntry assocEntry = new AssocTableEntry();
		final ArrayList<AssocTableEntry> assocEntries = new ArrayList<AssocTableEntry>(
				Arrays.asList(assocEntry));
		
		cc.putAssocTableEntries(name,assocEntries);
 
		new Verifications() {
			{
				PutAssocTableEntries parms;
				client.putAssocTableEntries(parms = withCapture());
				times = 1;
				
				Assert.assertEquals(name, parms.getTableName());
				Assert.assertEquals(assocEntries, parms.getEntries().getEntries().getEntry());
			}
		};
	}
	
	private void putAssocTableEntriesWithException(final ALECCService service, final ALECCServicePortType client, Exception fault, final Exception response)throws Exception {
		CC cc = createCC(service, client, null);
		new NonStrictExpectations() {
			{
				client.putAssocTableEntries(this.<PutAssocTableEntries>withNotNull());
				result = response;
				times = 1;
			}
		};

		try {
			cc.putAssocTableEntries(null,null);
			Assert.fail("Expected " + fault.getClass().getSimpleName());
		} catch (Exception e) {
			Assert.assertSame(fault, e);
		}
	}
	
	@Test
	public void putAssocTableEntriesWithImplementationException(final @Mocked ALECCService service, final @Mocked ALECCServicePortType client) throws Exception {
		ImplementationException exception = new ImplementationException();
		putAssocTableEntriesWithException(service, client, exception, new ImplementationExceptionResponse("Exception", exception));
	}
	@Test
	public void putAssocTableEntriesWithInvalidAssocTableEntryException(final @Mocked ALECCService service, final @Mocked ALECCServicePortType client) throws Exception {
		InvalidAssocTableEntryException exception = new InvalidAssocTableEntryException();
		putAssocTableEntriesWithException(service, client, exception, new InvalidAssocTableEntryExceptionResponse("Exception", exception));
	}
	@Test
	public void putAssocTableEntriesWithNoSuchNameException(final @Mocked ALECCService service, final @Mocked ALECCServicePortType client) throws Exception {
		NoSuchNameException exception = new NoSuchNameException();
		putAssocTableEntriesWithException(service, client, exception, new NoSuchNameExceptionResponse("Exception", exception));
	}
	@Test
	public void putAssocTableEntriesWithSecurityException(final @Mocked ALECCService service, final @Mocked ALECCServicePortType client) throws Exception {
		SecurityException exception = new SecurityException();
		putAssocTableEntriesWithException(service, client, exception, new SecurityExceptionResponse("Exception", exception));
	}
	
	@Test
	public void getAssocTableEntries(final @Mocked ALECCService service, final @Mocked ALECCServicePortType client, final @Mocked AssocTableEntryList entryList) throws Exception {
		new NonStrictExpectations() {
			{
				client.getAssocTableEntries(this.<GetAssocTableEntries>withNotNull());
				result = entryList;
			}
		};
		CC cc = createCC(service, client, null);
		final String name = "assoc";
		final ArrayList<String> list = new ArrayList<String>(
				Arrays.asList("Test"));
		
		final List<AssocTableEntry> actual = cc.getAssocTableEntries(name, list);
 
		new Verifications() {
			{
				GetAssocTableEntries parms;
				client.getAssocTableEntries(parms = withCapture());
				times = 1;
				entryList.getEntries();
				times = 1;
				
				Assert.assertEquals(name, parms.getTableName());
				Assert.assertEquals(list, parms.getPatList().getPatterns().getPattern());
				Assert.assertEquals(entryList.getEntries().getEntry(), actual);
			}
		};
	}
	
	@Test
	public void getAssocTableEntriesWithNull(final @Mocked ALECCService service, final @Mocked ALECCServicePortType client, final @Mocked AssocTableEntryList entryList) throws Exception {
		new NonStrictExpectations() {
			{
				client.getAssocTableEntries(this.<GetAssocTableEntries>withNotNull());
				result = null;
			}
		};
		CC cc = createCC(service, client, null);
		final String name = "assoc";
		final ArrayList<String> list = new ArrayList<String>(
				Arrays.asList("Test"));
		
		final List<AssocTableEntry> resultList = cc.getAssocTableEntries(name, list);
 
		new Verifications() {
			{
				GetAssocTableEntries parms;
				client.getAssocTableEntries(parms = withCapture());
				times = 1;
				entryList.getEntries();
				times = 0;
				
				Assert.assertEquals(name, parms.getTableName());
				Assert.assertNull(resultList);
			}
		};
	}
	
	private void getAssocTableEntriesWithException(final ALECCService service, final ALECCServicePortType client, Exception fault, final Exception response)throws Exception {
		CC cc = createCC(service, client, null);
		new NonStrictExpectations() {
			{
				client.getAssocTableEntries(this.<GetAssocTableEntries>withNotNull());
				result = response;
				times = 1;
			}
		};

		try {
			cc.getAssocTableEntries(null,null);
			Assert.fail("Expected " + fault.getClass().getSimpleName());
		} catch (Exception e) {
			Assert.assertSame(fault, e);
		}
	}
	
	@Test
	public void getAssocTableEntriesWithImplementationException(final @Mocked ALECCService service, final @Mocked ALECCServicePortType client) throws Exception {
		ImplementationException exception = new ImplementationException();
		getAssocTableEntriesWithException(service, client, exception, new ImplementationExceptionResponse("Exception", exception));
	}
	@Test
	public void getAssocTableEntriesWithInvalidPatternException(final @Mocked ALECCService service, final @Mocked ALECCServicePortType client) throws Exception {
		InvalidPatternException exception = new InvalidPatternException();
		getAssocTableEntriesWithException(service, client, exception, new InvalidPatternExceptionResponse("Exception", exception));
	}
	@Test
	public void getAssocTableEntriesWithNoSuchNameException(final @Mocked ALECCService service, final @Mocked ALECCServicePortType client) throws Exception {
		NoSuchNameException exception = new NoSuchNameException();
		getAssocTableEntriesWithException(service, client, exception, new NoSuchNameExceptionResponse("Exception", exception));
	}
	@Test
	public void getAssocTableEntriesWithSecurityException(final @Mocked ALECCService service, final @Mocked ALECCServicePortType client) throws Exception {
		SecurityException exception = new SecurityException();
		getAssocTableEntriesWithException(service, client, exception, new SecurityExceptionResponse("Exception", exception));
	}

	@Test
	public void getAssocTableValue(final @Mocked ALECCService service, final @Mocked ALECCServicePortType client, final @Mocked String expected) throws Exception {
		new NonStrictExpectations() {
			{
				client.getAssocTableValue(this.<GetAssocTableValue>withNotNull());
				result = expected;
			}
		};
		CC cc = createCC(service, client, null);
		final String name = "assoc";
		final String epc = "epcString";
		
		final String actual = cc.getAssocTableValue(name, epc);
 
		new Verifications() {
			{
				GetAssocTableValue parms;
				client.getAssocTableValue(parms = withCapture());
				times = 1;
				
				Assert.assertEquals(name, parms.getTableName());
				Assert.assertEquals(epc, parms.getEpc());
				Assert.assertEquals(expected, actual);
			}
		};
	}
	
	private void getAssocTableValueWithException(final ALECCService service, final ALECCServicePortType client, Exception fault, final Exception response)throws Exception {
		CC cc = createCC(service, client, null);
		new NonStrictExpectations() {
			{
				client.getAssocTableValue(this.<GetAssocTableValue>withNotNull());
				result = response;
				times = 1;
			}
		};

		try {
			cc.getAssocTableValue(null,null);
			Assert.fail("Expected " + fault.getClass().getSimpleName());
		} catch (Exception e) {
			Assert.assertSame(fault, e);
		}
	}
	
	@Test
	public void getAssocTableValueWithImplementationException(final @Mocked ALECCService service, final @Mocked ALECCServicePortType client) throws Exception {
		ImplementationException exception = new ImplementationException();
		getAssocTableValueWithException(service, client, exception, new ImplementationExceptionResponse("Exception", exception));
	}
	@Test
	public void getAssocTableValueWithInvalidEPCException(final @Mocked ALECCService service, final @Mocked ALECCServicePortType client) throws Exception {
		InvalidEPCException exception = new InvalidEPCException();
		getAssocTableValueWithException(service, client, exception, new InvalidEPCExceptionResponse("Exception", exception));
	}
	@Test
	public void getAssocTableValueWithNoSuchNameException(final @Mocked ALECCService service, final @Mocked ALECCServicePortType client) throws Exception {
		NoSuchNameException exception = new NoSuchNameException();
		getAssocTableValueWithException(service, client, exception, new NoSuchNameExceptionResponse("Exception", exception));
	}
	@Test
	public void getAssocTableValueWithSecurityException(final @Mocked ALECCService service, final @Mocked ALECCServicePortType client) throws Exception {
		SecurityException exception = new SecurityException();
		getAssocTableValueWithException(service, client, exception, new SecurityExceptionResponse("Exception", exception));
	}
	
	@Test
	public void removeAssocTableEntry(final @Mocked ALECCService service, final @Mocked ALECCServicePortType client) throws Exception {
		CC cc = createCC(service, client, null);
		final String name = "assoc";
		final String epc = "epcString";
		
		cc.removeAssocTableEntry(name, epc);
 
		new Verifications() {
			{
				RemoveAssocTableEntry parms;
				client.removeAssocTableEntry(parms = withCapture());
				times = 1;
				
				Assert.assertEquals(name, parms.getTableName());
				Assert.assertEquals(epc, parms.getEpc());
			}
		};
	}
	
	private void removeAssocTableEntryWithException(final ALECCService service, final ALECCServicePortType client, Exception fault, final Exception response)throws Exception {
		CC cc = createCC(service, client, null);
		new NonStrictExpectations() {
			{
				client.removeAssocTableEntry(this.<RemoveAssocTableEntry>withNotNull());
				result = response;
				times = 1;
			}
		};

		try {
			cc.removeAssocTableEntry(null,null);
			Assert.fail("Expected " + fault.getClass().getSimpleName());
		} catch (Exception e) {
			Assert.assertSame(fault, e);
		}
	}
	
	@Test
	public void removeAssocTableEntryWithImplementationException(final @Mocked ALECCService service, final @Mocked ALECCServicePortType client) throws Exception {
		ImplementationException exception = new ImplementationException();
		removeAssocTableEntryWithException(service, client, exception, new ImplementationExceptionResponse("Exception", exception));
	}
	@Test
	public void removeAssocTableEntryWithInvalidEPCException(final @Mocked ALECCService service, final @Mocked ALECCServicePortType client) throws Exception {
		InvalidEPCException exception = new InvalidEPCException();
		removeAssocTableEntryWithException(service, client, exception, new InvalidEPCExceptionResponse("Exception", exception));
	}
	@Test
	public void removeAssocTableEntryWithNoSuchNameException(final @Mocked ALECCService service, final @Mocked ALECCServicePortType client) throws Exception {
		NoSuchNameException exception = new NoSuchNameException();
		removeAssocTableEntryWithException(service, client, exception, new NoSuchNameExceptionResponse("Exception", exception));
	}
	@Test
	public void removeAssocTableEntryWithSecurityException(final @Mocked ALECCService service, final @Mocked ALECCServicePortType client) throws Exception {
		SecurityException exception = new SecurityException();
		removeAssocTableEntryWithException(service, client, exception, new SecurityExceptionResponse("Exception", exception));
	}
	
	@Test
	public void removeAssocTableEntries(final @Mocked ALECCService service, final @Mocked ALECCServicePortType client) throws Exception {
		CC cc = createCC(service, client, null);
		final String name = "assoc";
		final ArrayList<String> list = new ArrayList<String>(
				Arrays.asList("Test"));
		
		cc.removeAssocTableEntries(name, list);
 
		new Verifications() {
			{
				RemoveAssocTableEntries parms;
				client.removeAssocTableEntries(parms = withCapture());
				times = 1;
				
				Assert.assertEquals(name, parms.getTableName());
				Assert.assertEquals(list, parms.getPatList().getPatterns().getPattern());
			}
		};
	}
	
	private void removeAssocTableEntriesWithException(final ALECCService service, final ALECCServicePortType client, Exception fault, final Exception response)throws Exception {
		CC cc = createCC(service, client, null);
		new NonStrictExpectations() {
			{
				client.removeAssocTableEntries(this.<RemoveAssocTableEntries>withNotNull());
				result = response;
				times = 1;
			}
		};

		try {
			cc.removeAssocTableEntries(null,null);
			Assert.fail("Expected " + fault.getClass().getSimpleName());
		} catch (Exception e) {
			Assert.assertSame(fault, e);
		}
	}
	
	@Test
	public void removeAssocTableEntriesWithImplementationException(final @Mocked ALECCService service, final @Mocked ALECCServicePortType client) throws Exception {
		ImplementationException exception = new ImplementationException();
		removeAssocTableEntriesWithException(service, client, exception, new ImplementationExceptionResponse("Exception", exception));
	}
	@Test
	public void removeAssocTableEntriesWithInvalidPatternException(final @Mocked ALECCService service, final @Mocked ALECCServicePortType client) throws Exception {
		InvalidPatternException exception = new InvalidPatternException();
		removeAssocTableEntriesWithException(service, client, exception, new InvalidPatternExceptionResponse("Exception", exception));
	}
	@Test
	public void removeAssocTableEntriesWithNoSuchNameException(final @Mocked ALECCService service, final @Mocked ALECCServicePortType client) throws Exception {
		NoSuchNameException exception = new NoSuchNameException();
		removeAssocTableEntriesWithException(service, client, exception, new NoSuchNameExceptionResponse("Exception", exception));
	}
	@Test
	public void removeAssocTableEntriesWithSecurityException(final @Mocked ALECCService service, final @Mocked ALECCServicePortType client) throws Exception {
		SecurityException exception = new SecurityException();
		removeAssocTableEntriesWithException(service, client, exception, new SecurityExceptionResponse("Exception", exception));
	}
	
	@Test
	public void defineRNG(final @Mocked ALECCService service, final @Mocked ALECCServicePortType client) throws Exception {
		CC cc = createCC(service, client, null);
		final String name = "rng";
		final RNGSpec spec = new RNGSpec();
		
		cc.defineRNG(name, spec);
 
		new Verifications() {
			{
				DefineRNG parms;
				client.defineRNG(parms = withCapture());
				times = 1;
				
				Assert.assertEquals(name, parms.getRngName());
				Assert.assertSame(spec, parms.getRngSpec());
			}
		};
	}
	
	private void defineRNGWithException(final ALECCService service, final ALECCServicePortType client, Exception fault, final Exception response)throws Exception {
		CC cc = createCC(service, client, null);
		new NonStrictExpectations() {
			{
				client.defineRNG(this.<DefineRNG>withNotNull());
				result = response;
				times = 1;
			}
		};

		try {
			cc.defineRNG(null,null);
			Assert.fail("Expected " + fault.getClass().getSimpleName());
		} catch (Exception e) {
			Assert.assertSame(fault, e);
		}
	}
	
	@Test
	public void defineRNGWithDuplicateNameException(final @Mocked ALECCService service, final @Mocked ALECCServicePortType client) throws Exception {
		DuplicateNameException exception = new DuplicateNameException();
		defineRNGWithException(service, client, exception, new DuplicateNameExceptionResponse("Exception", exception));
	}@Test
	public void defineRNGWithImplementationException(final @Mocked ALECCService service, final @Mocked ALECCServicePortType client) throws Exception {
		ImplementationException exception = new ImplementationException();
		defineRNGWithException(service, client, exception, new ImplementationExceptionResponse("Exception", exception));
	}
	@Test
	public void defineRNGWithRNGValidationException(final @Mocked ALECCService service, final @Mocked ALECCServicePortType client) throws Exception {
		RNGValidationException exception = new RNGValidationException();
		defineRNGWithException(service, client, exception, new RNGValidationExceptionResponse("Exception", exception));
	}
	@Test
	public void defineRNGWithSecurityException(final @Mocked ALECCService service, final @Mocked ALECCServicePortType client) throws Exception {
		SecurityException exception = new SecurityException();
		defineRNGWithException(service, client, exception, new SecurityExceptionResponse("Exception", exception));
	}
	
	@Test
	public void undefineRNG(final @Mocked ALECCService service, final @Mocked ALECCServicePortType client) throws Exception {
		CC cc = createCC(service, client, null);
		final String name = "rng";
		
		cc.undefineRNG(name);
 
		new Verifications() {
			{
				UndefineRNG parms;
				client.undefineRNG(parms = withCapture());
				times = 1;
				
				Assert.assertEquals(name, parms.getRngName());
			}
		};
	}
	
	private void undefineRNGWithException(final ALECCService service, final ALECCServicePortType client, Exception fault, final Exception response)throws Exception {
		CC cc = createCC(service, client, null);
		new NonStrictExpectations() {
			{
				client.undefineRNG(this.<UndefineRNG>withNotNull());
				result = response;
				times = 1;
			}
		};

		try {
			cc.undefineRNG(null);
			Assert.fail("Expected " + fault.getClass().getSimpleName());
		} catch (Exception e) {
			Assert.assertSame(fault, e);
		}
	}
	
	@Test
	public void undefineRNGWithImplementationException(final @Mocked ALECCService service, final @Mocked ALECCServicePortType client) throws Exception {
		ImplementationException exception = new ImplementationException();
		undefineRNGWithException(service, client, exception, new ImplementationExceptionResponse("Exception", exception));
	}
	@Test
	public void undefineRNGWithInUseException(final @Mocked ALECCService service, final @Mocked ALECCServicePortType client) throws Exception {
		InUseException exception = new InUseException();
		undefineRNGWithException(service, client, exception, new InUseExceptionResponse("Exception", exception));
	}
	@Test
	public void undefineRNGWithNoSuchNameException(final @Mocked ALECCService service, final @Mocked ALECCServicePortType client) throws Exception {
		NoSuchNameException exception = new NoSuchNameException();
		undefineRNGWithException(service, client, exception, new NoSuchNameExceptionResponse("Exception", exception));
	}
	@Test
	public void undefineRNGWithSecurityException(final @Mocked ALECCService service, final @Mocked ALECCServicePortType client) throws Exception {
		SecurityException exception = new SecurityException();
		undefineRNGWithException(service, client, exception, new SecurityExceptionResponse("Exception", exception));
	}

	@Test
	public void getRNGNames(final @Mocked ALECCService service, final @Mocked ALECCServicePortType client,final @Mocked ArrayOfString string) throws Exception {
		new NonStrictExpectations() {
			{
				client.getRNGNames(this.<EmptyParms>withNotNull());
				result = string;
			}
		};
		CC cc = createCC(service, client, null);
		
		final List<String> list = cc.getRNGNames();
 
		new Verifications() {
			{
				client.getRNGNames(this.<EmptyParms>withNotNull());
				times = 1;
				string.getString();
				times = 1;
				
				Assert.assertEquals(string.getString(), list);
			}
		};
	}
	
	@Test
	public void getRNGNamesWithNull(final @Mocked ALECCService service, final @Mocked ALECCServicePortType client, final @Mocked ArrayOfString string) throws Exception {
		new NonStrictExpectations() {
			{
				client.getRNGNames(this.<EmptyParms>withNotNull());
				result = null;
			}
		};
		CC cc = createCC(service, client, null);
		
		final List<String> list = cc.getRNGNames();
 
		new Verifications() {
			{
				client.getRNGNames(this.<EmptyParms>withNotNull());
				times = 1;
				string.getString();
				times = 0;
				
				Assert.assertNull(list);
			}
		};
	}
	
	private void getRNGNamesWithException(final ALECCService service, final ALECCServicePortType client, Exception fault, final Exception response)throws Exception {
		CC cc = createCC(service, client, null);
		new NonStrictExpectations() {
			{
				client.getRNGNames(this.<EmptyParms>withNotNull());
				result = response;
				times = 1;
			}
		};

		try {
			cc.getRNGNames();
			Assert.fail("Expected " + fault.getClass().getSimpleName());
		} catch (Exception e) {
			Assert.assertSame(fault, e);
		}
	}
	
	@Test
	public void getRNGNamesWithImplementationException(final @Mocked ALECCService service, final @Mocked ALECCServicePortType client) throws Exception {
		ImplementationException exception = new ImplementationException();
		getRNGNamesWithException(service, client, exception, new ImplementationExceptionResponse("Exception", exception));
	}
	@Test
	public void getRNGNamesWithSecurityException(final @Mocked ALECCService service, final @Mocked ALECCServicePortType client) throws Exception {
		SecurityException exception = new SecurityException();
		getRNGNamesWithException(service, client, exception, new SecurityExceptionResponse("Exception", exception));
	}
	
	@Test
	public void getRNG(final @Mocked ALECCService service, final @Mocked ALECCServicePortType client, final @Mocked RNGSpec expected) throws Exception {
		new NonStrictExpectations() {
			{
				client.getRNG(this.<GetRNG>withNotNull());
				result = expected;
			}
		};
		CC cc = createCC(service, client, null);
		final String name = "rng";
		
		final RNGSpec actual = cc.getRNG(name);
 
		new Verifications() {
			{
				GetRNG parms;
				client.getRNG(parms = withCapture());
				times = 1;
			
				Assert.assertEquals(name, parms.getRngName());
				Assert.assertSame(expected, actual);
			}
		};
	}
	
	private void getRNGWithException(final ALECCService service, final ALECCServicePortType client, Exception fault, final Exception response)throws Exception {
		CC cc = createCC(service, client, null);
		new NonStrictExpectations() {
			{
				client.getRNG(this.<GetRNG>withNotNull());
				result = response;
				times = 1;
			}
		};

		try {
			cc.getRNG(null);
			Assert.fail("Expected " + fault.getClass().getSimpleName());
		} catch (Exception e) {
			Assert.assertSame(fault, e);
		}
	}
	
	@Test
	public void getRNGWithImplementationException(final @Mocked ALECCService service, final @Mocked ALECCServicePortType client) throws Exception {
		ImplementationException exception = new ImplementationException();
		getRNGWithException(service, client, exception, new ImplementationExceptionResponse("Exception", exception));
	}
	@Test
	public void getRNGWithNoSuchNameException(final @Mocked ALECCService service, final @Mocked ALECCServicePortType client) throws Exception {
		NoSuchNameException exception = new NoSuchNameException();
		getRNGWithException(service, client, exception, new NoSuchNameExceptionResponse("Exception", exception));
	}
	@Test
	public void getRNGWithSecurityException(final @Mocked ALECCService service, final @Mocked ALECCServicePortType client) throws Exception {
		SecurityException exception = new SecurityException();
		getRNGWithException(service, client, exception, new SecurityExceptionResponse("Exception", exception));
	}
}
