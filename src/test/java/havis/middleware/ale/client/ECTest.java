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

public class ECTest {

	private EC createEC(final ALEService service, final ALEServicePortType client, final URL url) {
		new NonStrictExpectations() {
			{
				service.getALEServicePort();
				result = client;
			}
		};
		if (url != null)
			return new EC(url);
		else
			return new EC();
	}

	@Test
	public void ec(final @Mocked ALEService service, final @Mocked ALEServicePortType client) throws Exception {
		EC ec = createEC(service, client, null);
		Assert.assertSame(client, Deencapsulation.getField(ec, "client"));
	}

	@Test
	public void ecWithUrl(final @Mocked ALEService service, final @Mocked ALEServicePortType client, final @Mocked URL url) throws Exception {
		EC ec = createEC(service, client, url);
		Assert.assertSame(client, Deencapsulation.getField(ec, "client"));
	}

	@Test
	public void ecWithClient(final @Mocked ALEService service, final @Mocked ALEServicePortType client) throws Exception {
		EC ec = new EC(client);
		Assert.assertSame(client, Deencapsulation.getField(ec, "client"));
	}

	@Test
	public <Client extends ALEServicePortType & BindingProvider> void setEndpoint(final @Mocked Client client) {
		final Map<String, Object> requestContext = new HashMap<String, Object>();
		final String endpoint = "/test";
		new NonStrictExpectations() {
			{
				client.getRequestContext();
				result = requestContext;
			}
		};

		EC ec = new EC(client);
		ec.setEndpoint(endpoint);
		Assert.assertEquals(endpoint, requestContext.get(BindingProvider.ENDPOINT_ADDRESS_PROPERTY));
	}

	@Test
	public void define(final @Mocked ALEService service, final @Mocked ALEServicePortType client) throws Exception {
		EC ec = createEC(service, client, null);
		final String name = "ec";
		final ECSpec spec = new ECSpec();

		ec.define(name, spec);

		new Verifications() {
			{
				Define parms;
				client.define(parms = withCapture());
				times = 1;

				Assert.assertEquals(name, parms.getSpecName());
				Assert.assertSame(spec, parms.getSpec());
			}
		};
	}

	private void defineWithException(final ALEService service, final ALEServicePortType client, Exception fault, final Exception response) throws Exception {
		EC ec = createEC(service, client, null);
		new NonStrictExpectations() {
			{
				client.define(this.<Define> withNotNull());
				result = response;
			}
		};

		try {
			ec.define(null, null);
			Assert.fail("Expected " + fault.getClass().getSimpleName());
		} catch (Exception e) {
			Assert.assertSame(fault, e);
		}
	}

	@Test
	public void defineWithDuplicateNameException(final @Mocked ALEService service, final @Mocked ALEServicePortType client) throws Exception {
		DuplicateNameException exception = new DuplicateNameException();
		defineWithException(service, client, exception, new DuplicateNameExceptionResponse("Exception", exception));
	}

	@Test
	public void defineWithECSpecValidationExceptionResponse(final @Mocked ALEService service, final @Mocked ALEServicePortType client) throws Exception {
		ValidationException exception = new ValidationException();
		defineWithException(service, client, exception, new ECSpecValidationExceptionResponse("Exception", exception));
	}

	@Test
	public void defineWithImplementationException(final @Mocked ALEService service, final @Mocked ALEServicePortType client) throws Exception {
		ImplementationException exception = new ImplementationException();
		defineWithException(service, client, exception, new ImplementationExceptionResponse("Exception", exception));
	}

	@Test
	public void defineWithSecurityException(final @Mocked ALEService service, final @Mocked ALEServicePortType client) throws Exception {
		SecurityException exception = new SecurityException();
		defineWithException(service, client, exception, new SecurityExceptionResponse("Exception", exception));
	}

	@Test
	public void undefine(final @Mocked ALEService service, final @Mocked ALEServicePortType client) throws Exception {
		EC ec = createEC(service, client, null);
		final String name = "ec";

		ec.undefine(name);

		new Verifications() {
			{
				Undefine parms;
				client.undefine(parms = withCapture());
				times = 1;

				Assert.assertEquals(name, parms.getSpecName());
			}
		};
	}

	private void undefineWithException(final ALEService service, final ALEServicePortType client, Exception fault, final Exception response) throws Exception {
		EC ec = createEC(service, client, null);
		new NonStrictExpectations() {
			{
				client.undefine(this.<Undefine> withNotNull());
				result = response;
			}
		};

		try {
			ec.undefine(null);
			Assert.fail("Expected " + fault.getClass().getSimpleName());
		} catch (Exception e) {
			Assert.assertSame(fault, e);
		}
	}

	@Test
	public void undefineWithImplementationException(final @Mocked ALEService service, final @Mocked ALEServicePortType client) throws Exception {
		ImplementationException exception = new ImplementationException();
		undefineWithException(service, client, exception, new ImplementationExceptionResponse("Exception", exception));
	}

	@Test
	public void undefineWithSecurityException(final @Mocked ALEService service, final @Mocked ALEServicePortType client) throws Exception {
		SecurityException exception = new SecurityException();
		undefineWithException(service, client, exception, new SecurityExceptionResponse("Exception", exception));
	}

	@Test
	public void undefineWithNoSuchNameException(final @Mocked ALEService service, final @Mocked ALEServicePortType client) throws Exception {
		NoSuchNameException exception = new NoSuchNameException();
		undefineWithException(service, client, exception, new NoSuchNameExceptionResponse("Exception", exception));
	}

	@Test
	public void getECSpec(final @Mocked ALEService service, final @Mocked ALEServicePortType client, final @Mocked ECSpec expected) throws Exception {
		new NonStrictExpectations() {
			{
				client.getECSpec(this.<GetECSpec>withNotNull());
				result = expected;
			}
		};
		EC ec = createEC(service, client, null);
		final String name = "ec";

		final ECSpec actual = ec.getSpec(name);

		new Verifications() {
			{
				GetECSpec parms;
				client.getECSpec(parms = withCapture());
				times = 1;

				Assert.assertEquals(name, parms.getSpecName());
				Assert.assertSame(expected, actual);
			}
		};
	}

	private void getECSpecWithException(final ALEService service, final ALEServicePortType client, Exception fault, final Exception response) throws Exception {
		EC ec = createEC(service, client, null);
		new NonStrictExpectations() {
			{
				client.getECSpec(this.<GetECSpec> withNotNull());
				result = response;
			}
		};

		try {
			ec.getSpec(null);
			Assert.fail("Expected " + fault.getClass().getSimpleName());
		} catch (Exception e) {
			Assert.assertSame(fault, e);
		}
	}

	@Test
	public void getSpecWithImplementationException(final @Mocked ALEService service, final @Mocked ALEServicePortType client) throws Exception {
		ImplementationException exception = new ImplementationException();
		getECSpecWithException(service, client, exception, new ImplementationExceptionResponse("Exception", exception));
	}

	@Test
	public void getSpecWithSecurityException(final @Mocked ALEService service, final @Mocked ALEServicePortType client) throws Exception {
		SecurityException exception = new SecurityException();
		getECSpecWithException(service, client, exception, new SecurityExceptionResponse("Exception", exception));
	}

	@Test
	public void getSpecWithNoSuchNameException(final @Mocked ALEService service, final @Mocked ALEServicePortType client) throws Exception {
		NoSuchNameException exception = new NoSuchNameException();
		getECSpecWithException(service, client, exception, new NoSuchNameExceptionResponse("Exception", exception));
	}

	@Test
	public void getNames(final @Mocked ALEService service, final @Mocked ALEServicePortType client, final @Mocked ArrayOfString string) throws Exception {
		new NonStrictExpectations() {
			{
				client.getECSpecNames(this.<EmptyParms> withNotNull());
				result = string;
			}
		};
		EC ec = createEC(service, client, null);

		final List<String> list = ec.getNames();

		new Verifications() {
			{
				client.getECSpecNames(this.<EmptyParms> withNotNull());
				times = 1;

				Assert.assertEquals(string.getString(), list);
			}
		};
	}

	@Test
	public void getNamesWithNull(final @Mocked ALEService service, final @Mocked ALEServicePortType client, final @Mocked ArrayOfString string)
			throws Exception {
		new NonStrictExpectations() {
			{
				client.getECSpecNames(this.<EmptyParms> withNotNull());
				result = null;
			}
		};
		EC ec = createEC(service, client, null);

		final List<String> list = ec.getNames();

		new Verifications() {
			{
				client.getECSpecNames(this.<EmptyParms> withNotNull());
				times = 1;

				Assert.assertNull(list);
			}
		};
	}

	private void getNamesWithException(final ALEService service, final ALEServicePortType client, Exception fault, final Exception response) throws Exception {
		EC ec = createEC(service, client, null);
		new NonStrictExpectations() {
			{
				client.getECSpecNames(this.<EmptyParms> withNotNull());
				result = response;
			}
		};

		try {
			ec.getNames();
			Assert.fail("Expected " + fault.getClass().getSimpleName());
		} catch (Exception e) {
			Assert.assertSame(fault, e);
		}
	}

	@Test
	public void getNamesWithImplementationException(final @Mocked ALEService service, final @Mocked ALEServicePortType client) throws Exception {
		ImplementationException exception = new ImplementationException();
		getNamesWithException(service, client, exception, new ImplementationExceptionResponse("Exception", exception));
	}

	@Test
	public void getNamesWithSecurityException(final @Mocked ALEService service, final @Mocked ALEServicePortType client) throws Exception {
		SecurityException exception = new SecurityException();
		getNamesWithException(service, client, exception, new SecurityExceptionResponse("Exception", exception));
	}

	@Test
	public void subscribe(final @Mocked ALEService service, final @Mocked ALEServicePortType client) throws Exception {
		EC ec = createEC(service, client, null);
		final String name = "ec";
		final String uri = "uri";

		ec.subscribe(name, uri);

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

	private void subscribeWithException(final ALEService service, final ALEServicePortType client, Exception fault, final Exception response) throws Exception {
		EC ec = createEC(service, client, null);
		new NonStrictExpectations() {
			{
				client.subscribe(this.<Subscribe> withNotNull());
				result = response;
			}
		};

		try {
			ec.subscribe(null, null);
			Assert.fail("Expected " + fault.getClass().getSimpleName());
		} catch (Exception e) {
			Assert.assertSame(fault, e);
		}
	}

	@Test
	public void subscribeWithImplementationException(final @Mocked ALEService service, final @Mocked ALEServicePortType client) throws Exception {
		ImplementationException exception = new ImplementationException();
		subscribeWithException(service, client, exception, new ImplementationExceptionResponse("Exception", exception));
	}

	@Test
	public void subscribeWithDuplicateSubscriptionException(final @Mocked ALEService service, final @Mocked ALEServicePortType client) throws Exception {
		DuplicateSubscriptionException exception = new DuplicateSubscriptionException();
		subscribeWithException(service, client, exception, new DuplicateSubscriptionExceptionResponse("Exception", exception));
	}

	@Test
	public void subscribeWithInvalidURIException(final @Mocked ALEService service, final @Mocked ALEServicePortType client) throws Exception {
		InvalidURIException exception = new InvalidURIException();
		subscribeWithException(service, client, exception, new InvalidURIExceptionResponse("Exception", exception));
	}

	@Test
	public void subscribeWithNoSuchNameException(final @Mocked ALEService service, final @Mocked ALEServicePortType client) throws Exception {
		NoSuchNameException exception = new NoSuchNameException();
		subscribeWithException(service, client, exception, new NoSuchNameExceptionResponse("Exception", exception));
	}

	@Test
	public void subscribeWithSecurityException(final @Mocked ALEService service, final @Mocked ALEServicePortType client) throws Exception {
		SecurityException exception = new SecurityException();
		subscribeWithException(service, client, exception, new SecurityExceptionResponse("Exception", exception));
	}

	@Test
	public void unsubscribe(final @Mocked ALEService service, final @Mocked ALEServicePortType client) throws Exception {
		EC ec = createEC(service, client, null);
		final String name = "ec";
		final String uri = "uri";

		ec.unsubscribe(name, uri);

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

	private void unsubscribeWithException(final ALEService service, final ALEServicePortType client, Exception fault, final Exception response)
			throws Exception {
		EC ec = createEC(service, client, null);
		new NonStrictExpectations() {
			{
				client.unsubscribe(this.<Unsubscribe> withNotNull());
				result = response;
			}
		};

		try {
			ec.unsubscribe(null, null);
			Assert.fail("Expected " + fault.getClass().getSimpleName());
		} catch (Exception e) {
			Assert.assertSame(fault, e);
		}
	}

	@Test
	public void unsubscribeWithImplementationException(final @Mocked ALEService service, final @Mocked ALEServicePortType client) throws Exception {
		ImplementationException exception = new ImplementationException();
		unsubscribeWithException(service, client, exception, new ImplementationExceptionResponse("Exception", exception));
	}

	@Test
	public void unsubscribeWithNoSuchSubscriberException(final @Mocked ALEService service, final @Mocked ALEServicePortType client) throws Exception {
		NoSuchSubscriberException exception = new NoSuchSubscriberException();
		unsubscribeWithException(service, client, exception, new NoSuchSubscriberExceptionResponse("Exception", exception));
	}

	@Test
	public void unsubscribeWithInvalidURIException(final @Mocked ALEService service, final @Mocked ALEServicePortType client) throws Exception {
		InvalidURIException exception = new InvalidURIException();
		unsubscribeWithException(service, client, exception, new InvalidURIExceptionResponse("Exception", exception));
	}

	@Test
	public void unsubscribeWithNoSuchNameException(final @Mocked ALEService service, final @Mocked ALEServicePortType client) throws Exception {
		NoSuchNameException exception = new NoSuchNameException();
		unsubscribeWithException(service, client, exception, new NoSuchNameExceptionResponse("Exception", exception));
	}

	@Test
	public void unsubscribeWithSecurityException(final @Mocked ALEService service, final @Mocked ALEServicePortType client) throws Exception {
		SecurityException exception = new SecurityException();
		unsubscribeWithException(service, client, exception, new SecurityExceptionResponse("Exception", exception));
	}

	@Test
	public void poll(final @Mocked ALEService service, final @Mocked ALEServicePortType client, final @Mocked ECReports expected) throws Exception {
		new NonStrictExpectations() {
			{
				client.poll(this.<Poll>withNotNull());
				result = expected;
			}
		};
		EC ec = createEC(service, client, null);
		final String name = "ec";

		final ECReports actual = ec.poll(name);

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

	private void pollWithException(final ALEService service, final ALEServicePortType client, Exception fault, final Exception response) throws Exception {
		EC ec = createEC(service, client, null);
		new NonStrictExpectations() {
			{
				client.poll(this.<Poll> withNotNull());
				result = response;
			}
		};

		try {
			ec.poll(null);
			Assert.fail("Expected " + fault.getClass().getSimpleName());
		} catch (Exception e) {
			Assert.assertSame(fault, e);
		}
	}

	@Test
	public void pollWithNoSuchNameException(final @Mocked ALEService service, final @Mocked ALEServicePortType client) throws Exception {
		NoSuchNameException exception = new NoSuchNameException();
		pollWithException(service, client, exception, new NoSuchNameExceptionResponse("Exception", exception));
	}

	@Test
	public void pollWithSecurityException(final @Mocked ALEService service, final @Mocked ALEServicePortType client) throws Exception {
		SecurityException exception = new SecurityException();
		pollWithException(service, client, exception, new SecurityExceptionResponse("Exception", exception));
	}

	@Test
	public void pollWithImplementationException(final @Mocked ALEService service, final @Mocked ALEServicePortType client) throws Exception {
		ImplementationException exception = new ImplementationException();
		pollWithException(service, client, exception, new ImplementationExceptionResponse("Exception", exception));
	}

	@Test
	public void immediate(final @Mocked ALEService service, final @Mocked ALEServicePortType client, final @Mocked ECReports expected) throws Exception {
		new NonStrictExpectations() {
			{
				client.immediate(this.<Immediate>withNotNull());
				result = expected;
			}
		};
		EC ec = createEC(service, client, null);
		final ECSpec spec = new ECSpec();

		final ECReports actual = ec.immediate(spec);

		new Verifications() {
			{
				Immediate parms;
				client.immediate(parms = withCapture());
				times = 1;

				Assert.assertEquals(spec, parms.getSpec());
				Assert.assertSame(expected, actual);
			}
		};
	}

	private void immediateWithException(final ALEService service, final ALEServicePortType client, Exception fault, final Exception response) throws Exception {
		EC ec = createEC(service, client, null);
		new NonStrictExpectations() {
			{
				client.immediate(this.<Immediate> withNotNull());
				result = response;
			}
		};

		try {
			ec.immediate(null);
			Assert.fail("Expected " + fault.getClass().getSimpleName());
		} catch (Exception e) {
			Assert.assertSame(fault, e);
		}
	}

	@Test
	public void immediateWithECSpecValidationExceptionResponse(final @Mocked ALEService service, final @Mocked ALEServicePortType client) throws Exception {
		ValidationException exception = new ValidationException();
		immediateWithException(service, client, exception, new ECSpecValidationExceptionResponse("Exception", exception));
	}

	@Test
	public void immediateWithSecurityException(final @Mocked ALEService service, final @Mocked ALEServicePortType client) throws Exception {
		SecurityException exception = new SecurityException();
		immediateWithException(service, client, exception, new SecurityExceptionResponse("Exception", exception));
	}

	@Test
	public void immediateWithImplementationException(final @Mocked ALEService service, final @Mocked ALEServicePortType client) throws Exception {
		ImplementationException exception = new ImplementationException();
		immediateWithException(service, client, exception, new ImplementationExceptionResponse("Exception", exception));
	}

	@Test
	public void getSubscribers(final @Mocked ALEService service, final @Mocked ALEServicePortType client, final @Mocked ArrayOfString string) throws Exception {
		new NonStrictExpectations() {
			{
				client.getSubscribers(this.<GetSubscribers> withNotNull());
				result = string;
			}
		};
		EC ec = createEC(service, client, null);
		final String name = "ec";

		final List<String> list = ec.getSubscribers(name);

		new Verifications() {
			{
				client.getSubscribers(this.<GetSubscribers> withNotNull());
				times = 1;
				string.getString();
				times = 1;

				Assert.assertEquals(string.getString(), list);
			}
		};
	}

	@Test
	public void getSubscribersWithNull(final @Mocked ALEService service, final @Mocked ALEServicePortType client, final @Mocked ArrayOfString string)
			throws Exception {
		new NonStrictExpectations() {
			{
				client.getSubscribers(this.<GetSubscribers> withNotNull());
				result = null;
			}
		};
		EC ec = createEC(service, client, null);
		final String name = "ec";

		final List<String> list = ec.getSubscribers(name);

		new Verifications() {
			{
				client.getSubscribers(this.<GetSubscribers> withNotNull());
				times = 1;
				string.getString();
				times = 0;

				Assert.assertNull(list);
			}
		};
	}

	private void getSubscribersWithException(final ALEService service, final ALEServicePortType client, Exception fault, final Exception response)
			throws Exception {
		EC ec = createEC(service, client, null);
		new NonStrictExpectations() {
			{
				client.getSubscribers(this.<GetSubscribers> withNotNull());
				result = response;
			}
		};

		try {
			ec.getSubscribers(null);
			Assert.fail("Expected " + fault.getClass().getSimpleName());
		} catch (Exception e) {
			Assert.assertSame(fault, e);
		}
	}

	@Test
	public void getSubscribersSecurityException(final @Mocked ALEService service, final @Mocked ALEServicePortType client) throws Exception {
		SecurityException exception = new SecurityException();
		getSubscribersWithException(service, client, exception, new SecurityExceptionResponse("Exception", exception));
	}

	@Test
	public void getSubscribersImplementationException(final @Mocked ALEService service, final @Mocked ALEServicePortType client) throws Exception {
		ImplementationException exception = new ImplementationException();
		getSubscribersWithException(service, client, exception, new ImplementationExceptionResponse("Exception", exception));
	}

	@Test
	public void getSubscribersNoSuchNameException(final @Mocked ALEService service, final @Mocked ALEServicePortType client) throws Exception {
		NoSuchNameException exception = new NoSuchNameException();
		getSubscribersWithException(service, client, exception, new NoSuchNameExceptionResponse("Exception", exception));
	}

	@Test
	public void getStandardVersion(final @Mocked ALEService service, final @Mocked ALEServicePortType client, final @Mocked String version) throws Exception {
		new NonStrictExpectations() {
			{
				client.getStandardVersion(this.<EmptyParms> withNotNull());
				result = version;
			}
		};
		EC ec = createEC(service, client, null);

		final String result = ec.getStandardVersion();

		new Verifications() {
			{
				client.getStandardVersion(this.<EmptyParms> withNotNull());
				times = 1;

				Assert.assertEquals(version, result);
			}
		};
	}

	private void getStandardVersionWithException(final ALEService service, final ALEServicePortType client, Exception fault, final Exception response)
			throws Exception {
		EC ec = createEC(service, client, null);
		new NonStrictExpectations() {
			{
				client.getStandardVersion(this.<EmptyParms> withNotNull());
				result = response;
			}
		};

		try {
			ec.getStandardVersion();
			Assert.fail("Expected " + fault.getClass().getSimpleName());
		} catch (Exception e) {
			Assert.assertSame(fault, e);
		}
	}

	@Test
	public void getStandardVersionWithValidationException(final @Mocked ALEService service, final @Mocked ALEServicePortType client) throws Exception {
		ImplementationException exception = new ImplementationException();
		getStandardVersionWithException(service, client, exception, new ImplementationExceptionResponse("Exception", exception));
	}

	@Test
	public void getVendorVersion(final @Mocked ALEService service, final @Mocked ALEServicePortType client, final @Mocked String version) throws Exception {
		new NonStrictExpectations() {
			{
				client.getVendorVersion(this.<EmptyParms> withNotNull());
				result = version;
			}
		};
		EC ec = createEC(service, client, null);

		final String result = ec.getVendorVersion();

		new Verifications() {
			{
				client.getVendorVersion(this.<EmptyParms> withNotNull());
				times = 1;

				Assert.assertEquals(version, result);
			}
		};
	}

	private void getVendorVersionWithException(final ALEService service, final ALEServicePortType client, Exception fault, final Exception response)
			throws Exception {
		EC ec = createEC(service, client, null);
		new NonStrictExpectations() {
			{
				client.getVendorVersion(this.<EmptyParms> withNotNull());
				result = response;
			}
		};

		try {
			ec.getVendorVersion();
			Assert.fail("Expected " + fault.getClass().getSimpleName());
		} catch (Exception e) {
			Assert.assertSame(fault, e);
		}
	}

	@Test
	public void getVendorVersionWithImplementationException(final @Mocked ALEService service, final @Mocked ALEServicePortType client) throws Exception {
		ImplementationException exception = new ImplementationException();
		getVendorVersionWithException(service, client, exception, new ImplementationExceptionResponse("Exception", exception));
	}
}
