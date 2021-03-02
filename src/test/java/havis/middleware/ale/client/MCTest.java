package havis.middleware.ale.client;

import havis.middleware.ale.base.exception.ImplementationException;
import havis.middleware.ale.base.exception.NoSuchIdException;
import havis.middleware.ale.base.exception.NoSuchPathException;
import havis.middleware.ale.base.exception.NoSuchPropertyException;
import havis.middleware.ale.base.exception.SecurityException;
import havis.middleware.ale.base.exception.ValidationException;
import havis.middleware.ale.config.service.mc.Path;
import havis.middleware.ale.service.IReports;
import havis.middleware.ale.service.cc.CCSpec;
import havis.middleware.ale.service.ec.ECSpec;
import havis.middleware.ale.service.mc.ALEMCService;
import havis.middleware.ale.service.mc.ALEMCServicePortType;
import havis.middleware.ale.service.mc.Add;
import havis.middleware.ale.service.mc.ArrayOfString;
import havis.middleware.ale.service.mc.EmptyParms;
import havis.middleware.ale.service.mc.Get;
import havis.middleware.ale.service.mc.GetProperties;
import havis.middleware.ale.service.mc.GetProperty;
import havis.middleware.ale.service.mc.ImplementationExceptionResponse;
import havis.middleware.ale.service.mc.List;
import havis.middleware.ale.service.mc.MCCommandCycleSpec;
import havis.middleware.ale.service.mc.MCEventCycleSpec;
import havis.middleware.ale.service.mc.MCPCOpSpecs;
import havis.middleware.ale.service.mc.MCPortCycleSpec;
import havis.middleware.ale.service.mc.MCProperties;
import havis.middleware.ale.service.mc.MCProperty;
import havis.middleware.ale.service.mc.MCSpec;
import havis.middleware.ale.service.mc.MCSpecValidationExceptionResponse;
import havis.middleware.ale.service.mc.NoSuchIdExceptionResponse;
import havis.middleware.ale.service.mc.NoSuchPathExceptionResponse;
import havis.middleware.ale.service.mc.NoSuchPropertyExceptionResponse;
import havis.middleware.ale.service.mc.Remove;
import havis.middleware.ale.service.mc.SecurityExceptionResponse;
import havis.middleware.ale.service.mc.SetProperty;
import havis.middleware.ale.service.mc.Update;
import havis.middleware.ale.service.pc.PCOpReport;
import havis.middleware.ale.service.pc.PCOpSpec;
import havis.middleware.ale.service.pc.PCOpSpecs;
import havis.middleware.ale.service.pc.PCSpec;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import javax.xml.ws.BindingProvider;

import mockit.Deencapsulation;
import mockit.Mocked;
import mockit.NonStrictExpectations;
import mockit.Verifications;

import org.junit.Assert;
import org.junit.Test;

public class MCTest {
	private MC createMC(final ALEMCService service, final ALEMCServicePortType client, final URL url) {
		new NonStrictExpectations() {
			{
				service.getALEMCServicePort();
				result = client;
			}
		};
		if (url != null)
			return new MC(url);
		else
			return new MC();
	}

	@Test
	public void mc(final @Mocked ALEMCService service, final @Mocked ALEMCServicePortType client) throws Exception {
		MC mc = createMC(service, client, null);
		Assert.assertSame(client, Deencapsulation.getField(mc, "mc"));
	}

	@Test
	public void mcWithURL(final @Mocked ALEMCService service, final @Mocked ALEMCServicePortType client, final @Mocked URL url) throws Exception {
		MC mc = createMC(service, client, url);
		Assert.assertSame(client, Deencapsulation.getField(mc, "mc"));
	}

	@Test
	public void mcWithClient(final @Mocked ALEMCService service, final @Mocked ALEMCServicePortType client) throws Exception {
		MC mc = new MC(client);
		Assert.assertSame(client, Deencapsulation.getField(mc, "mc"));
	}
	
	@Test
	public <Client extends ALEMCServicePortType & BindingProvider> void setEndpoint(final @Mocked Client client) {
		final Map<String, Object> requestContext = new HashMap<String, Object>();
		final String endpoint = "/test";
		new NonStrictExpectations() {
			{
				client.getRequestContext();
				result = requestContext;
			}
		};

		MC mc = new MC(client);
		mc.setEndpoint(endpoint);
		Assert.assertEquals(endpoint, requestContext.get(BindingProvider.ENDPOINT_ADDRESS_PROPERTY));
	}
	
	@Test
	public void addWithoutParent(final @Mocked ALEMCService service, final @Mocked ALEMCServicePortType client) throws Exception {
		MC mc = createMC(service, client, null);
		final String path = Path.Service.EC.EventCycle;
		final MCSpec spec = new MCSpec();

		mc.add(path, spec);

		new Verifications() {
			{
				Add parms;
				client.add(parms = withCapture());
				times = 1;

				Assert.assertEquals(path, parms.getPath());
				Assert.assertSame(spec, parms.getSpec());
				Assert.assertNull(parms.getParent());
			}
		};
	}
	
	@Test
	public void addWithParent(final @Mocked ALEMCService service, final @Mocked ALEMCServicePortType client) throws Exception {
		MC mc = createMC(service, client, null);
		final String path = Path.Service.EC.EventCycle;
		final MCSpec spec = new MCSpec();
		final String parent = "parent";

		mc.add(path, spec,parent);

		new Verifications() {
			{
				Add parms;
				client.add(parms = withCapture());
				times = 1;

				Assert.assertEquals(path, parms.getPath());
				Assert.assertSame(spec, parms.getSpec());
				Assert.assertEquals(parent, parms.getParent());
			}
		};
	}
	
	private void addWithException(final ALEMCService service, final ALEMCServicePortType client, Exception fault, final Exception response) throws Exception {
		MC mc = createMC(service, client, null);
		new NonStrictExpectations() {
			{
				client.add(this.<Add> withNotNull());
				result = response;
			}
		};

		try {
			mc.add(null, null,null);
			Assert.fail("Expected " + fault.getClass().getSimpleName());
		} catch (Exception e) {
			Assert.assertSame(fault, e);
		}
	}
	
	@Test
	public void addWithImplementationException(final @Mocked ALEMCService service, final @Mocked ALEMCServicePortType client) throws Exception {
		ImplementationException exception = new ImplementationException();
		addWithException(service, client, exception, new ImplementationExceptionResponse("Exception", exception));
	}
	@Test
	public void addWithValidationException(final @Mocked ALEMCService service, final @Mocked ALEMCServicePortType client) throws Exception {
		ValidationException exception = new ValidationException();
		addWithException(service, client, exception, new MCSpecValidationExceptionResponse("Exception", exception));
	}
	@Test
	public void addWithNoSuchPathException(final @Mocked ALEMCService service, final @Mocked ALEMCServicePortType client) throws Exception {
		NoSuchPathException exception = new NoSuchPathException();
		addWithException(service, client, exception, new NoSuchPathExceptionResponse("Exception", exception));
	}
	@Test
	public void addWithSecurityException(final @Mocked ALEMCService service, final @Mocked ALEMCServicePortType client) throws Exception {
		SecurityException exception = new SecurityException();
		addWithException(service, client, exception, new SecurityExceptionResponse("Exception", exception));
	}
	
	@Test
	public void removeWithParent(final @Mocked ALEMCService service, final @Mocked ALEMCServicePortType client) throws Exception {
		MC mc = createMC(service, client, null);
		final String path = Path.Service.EC.EventCycle;
		final String id = "id";
		final String parent = "parent";

		mc.remove(path, id,parent);

		new Verifications() {
			{
				Remove parms;
				client.remove(parms = withCapture());
				times = 1;

				Assert.assertEquals(path, parms.getPath());
				Assert.assertEquals(id, parms.getId());
				Assert.assertEquals(parent, parms.getParent());
			}
		};
	}
	
	@Test
	public void removeWithoutParent(final @Mocked ALEMCService service, final @Mocked ALEMCServicePortType client) throws Exception {
		MC mc = createMC(service, client, null);
		final String path = Path.Service.EC.EventCycle;
		final String id = "id";

		mc.remove(path, id);

		new Verifications() {
			{
				Remove parms;
				client.remove(parms = withCapture());
				times = 1;

				Assert.assertEquals(path, parms.getPath());
				Assert.assertEquals(id, parms.getId());
				Assert.assertNull(parms.getParent());
			}
		};
	}
	
	private void removeWithException(final ALEMCService service, final ALEMCServicePortType client, Exception fault, final Exception response) throws Exception {
		MC mc = createMC(service, client, null);
		new NonStrictExpectations() {
			{
				client.remove(this.<Remove> withNotNull());
				result = response;
			}
		};

		try {
			mc.remove(null, null,null);
			Assert.fail("Expected " + fault.getClass().getSimpleName());
		} catch (Exception e) {
			Assert.assertSame(fault, e);
		}
	}
	
	@Test
	public void removeWithImplementationException(final @Mocked ALEMCService service, final @Mocked ALEMCServicePortType client) throws Exception {
		ImplementationException exception = new ImplementationException();
		removeWithException(service, client, exception, new ImplementationExceptionResponse("Exception", exception));
	}
	@Test
	public void removeWithValidationException(final @Mocked ALEMCService service, final @Mocked ALEMCServicePortType client) throws Exception {
		ValidationException exception = new ValidationException();
		removeWithException(service, client, exception, new MCSpecValidationExceptionResponse("Exception", exception));
	}
	@Test
	public void removeWithNoSuchIdException(final @Mocked ALEMCService service, final @Mocked ALEMCServicePortType client) throws Exception {
		NoSuchIdException exception = new NoSuchIdException();
		removeWithException(service, client, exception, new NoSuchIdExceptionResponse("Exception", exception));
	}
	@Test
	public void removeWithNoSuchPathException(final @Mocked ALEMCService service, final @Mocked ALEMCServicePortType client) throws Exception {
		NoSuchPathException exception = new NoSuchPathException();
		removeWithException(service, client, exception, new NoSuchPathExceptionResponse("Exception", exception));
	}
	@Test
	public void removeWithSecurityException(final @Mocked ALEMCService service, final @Mocked ALEMCServicePortType client) throws Exception {
		SecurityException exception = new SecurityException();
		removeWithException(service, client, exception, new SecurityExceptionResponse("Exception", exception));
	}
	
	@Test
	public void updateWithParent(final @Mocked ALEMCService service, final @Mocked ALEMCServicePortType client) throws Exception {
		MC mc = createMC(service, client, null);
		final String path = Path.Service.EC.EventCycle;
		final String id = "id";
		final String parent = "parent";
		final MCSpec spec = new MCSpec();

		mc.update(path, id, spec, parent);

		new Verifications() {
			{
				Update parms;
				client.update(parms = withCapture());
				times = 1;

				Assert.assertEquals(path, parms.getPath());
				Assert.assertEquals(id, parms.getId());
				Assert.assertEquals(parent, parms.getParent());
				Assert.assertSame(spec, parms.getSpec());
			}
		};
	}
	
	@Test
	public void updateWithoutParent(final @Mocked ALEMCService service, final @Mocked ALEMCServicePortType client) throws Exception {
		MC mc = createMC(service, client, null);
		final String path = Path.Service.EC.EventCycle;
		final String id = "id";
		final MCSpec spec = new MCSpec();

		mc.update(path, id, spec);

		new Verifications() {
			{
				Update parms;
				client.update(parms = withCapture());
				times = 1;

				Assert.assertEquals(path, parms.getPath());
				Assert.assertEquals(id, parms.getId());
				Assert.assertSame(spec, parms.getSpec());
				Assert.assertNull(parms.getParent());
			}
		};
	}
	
	private void updateWithException(final ALEMCService service, final ALEMCServicePortType client, Exception fault, final Exception response) throws Exception {
		MC mc = createMC(service, client, null);
		new NonStrictExpectations() {
			{
				client.update(this.<Update> withNotNull());
				result = response;
			}
		};

		try {
			mc.update(null, null, null, null);
			Assert.fail("Expected " + fault.getClass().getSimpleName());
		} catch (Exception e) {
			Assert.assertSame(fault, e);
		}
	}
	
	@Test
	public void updateWithImplementationException(final @Mocked ALEMCService service, final @Mocked ALEMCServicePortType client) throws Exception {
		ImplementationException exception = new ImplementationException();
		updateWithException(service, client, exception, new ImplementationExceptionResponse("Exception", exception));
	}
	@Test
	public void updateWithValidationException(final @Mocked ALEMCService service, final @Mocked ALEMCServicePortType client) throws Exception {
		ValidationException exception = new ValidationException();
		updateWithException(service, client, exception, new MCSpecValidationExceptionResponse("Exception", exception));
	}
	@Test
	public void updateWithNoSuchIdException(final @Mocked ALEMCService service, final @Mocked ALEMCServicePortType client) throws Exception {
		NoSuchIdException exception = new NoSuchIdException();
		updateWithException(service, client, exception, new NoSuchIdExceptionResponse("Exception", exception));
	}
	@Test
	public void updateWithNoSuchPathException(final @Mocked ALEMCService service, final @Mocked ALEMCServicePortType client) throws Exception {
		NoSuchPathException exception = new NoSuchPathException();
		updateWithException(service, client, exception, new NoSuchPathExceptionResponse("Exception", exception));
	}
	@Test
	public void updateWithSecurityException(final @Mocked ALEMCService service, final @Mocked ALEMCServicePortType client) throws Exception {
		SecurityException exception = new SecurityException();
		updateWithException(service, client, exception, new SecurityExceptionResponse("Exception", exception));
	}
	
	@Test
	public void get(final @Mocked ALEMCService service, final @Mocked ALEMCServicePortType client, final @Mocked MCSpec expectedSpec) throws Exception {
		new NonStrictExpectations() {
			{
				client.get(this.<Get>withNotNull());
				result = expectedSpec;
			}
		};
		MC mc = createMC(service, client, null);
		final String path = Path.Service.EC.EventCycle;
		final String id = "id";
		final String parent = "parent";

		final MCSpec actualSpec = mc.get(path, id, parent);

		new Verifications() {
			{
				Get parms;
				client.get(parms = withCapture());
				times = 1;

				Assert.assertEquals(path, parms.getPath());
				Assert.assertEquals(id, parms.getId());
				Assert.assertEquals(parent, parms.getParent());
				Assert.assertEquals(expectedSpec, actualSpec);
			}
		};
	}
	
	@Test
	public void getWithParent(final @Mocked ALEMCService service, final @Mocked ALEMCServicePortType client) throws Exception {
		MC mc = createMC(service, client, null);
		final String path = Path.Service.EC.EventCycle;
		final String id = "id";
		final String parent = "parent";

		mc.get(path, id, parent);

		new Verifications() {
			{
				Get parms;
				client.get(parms = withCapture());
				times = 1;

				Assert.assertEquals(path, parms.getPath());
				Assert.assertEquals(id, parms.getId());
				Assert.assertEquals(parent, parms.getParent());
			}
		};
	}
	
	@Test
	public void getWithoutParent(final @Mocked ALEMCService service, final @Mocked ALEMCServicePortType client) throws Exception {
		MC mc = createMC(service, client, null);
		final String path = "/test";
		final String id = "id";

		mc.get(path, id);

		new Verifications() {
			{
				Get parms;
				client.get(parms = withCapture());
				times = 1;

				Assert.assertEquals(path, parms.getPath());
				Assert.assertEquals(id, parms.getId());
				Assert.assertNull(parms.getParent());
			}
		};
	}
	
	@Test
	public void getWithPathOnly(final @Mocked ALEMCService service, final @Mocked ALEMCServicePortType client) throws Exception {
		MC mc = createMC(service, client, null);
		final String path = Path.Service.EC.EventCycle;

		mc.get(path);

		new Verifications() {
			{
				Get parms;
				client.get(parms = withCapture());
				times = 1;

				Assert.assertEquals(path, parms.getPath());
				Assert.assertNull(parms.getParent());
				Assert.assertNull(parms.getId());
			}
		};
	}
	
	private void getWithException(final ALEMCService service, final ALEMCServicePortType client, Exception fault, final Exception response) throws Exception {
		MC mc = createMC(service, client, null);
		new NonStrictExpectations() {
			{
				client.get(this.<Get> withNotNull());
				result = response;
			}
		};

		try {
			mc.get(null, null, null);
			Assert.fail("Expected " + fault.getClass().getSimpleName());
		} catch (Exception e) {
			Assert.assertSame(fault, e);
		}
	}
	
	@Test
	public void getWithImplementationException(final @Mocked ALEMCService service, final @Mocked ALEMCServicePortType client) throws Exception {
		ImplementationException exception = new ImplementationException();
		getWithException(service, client, exception, new ImplementationExceptionResponse("Exception", exception));
	}
	@Test
	public void getWithNoSuchIdException(final @Mocked ALEMCService service, final @Mocked ALEMCServicePortType client) throws Exception {
		NoSuchIdException exception = new NoSuchIdException();
		getWithException(service, client, exception, new NoSuchIdExceptionResponse("Exception", exception));
	}
	@Test
	public void getWithNoSuchPathException(final @Mocked ALEMCService service, final @Mocked ALEMCServicePortType client) throws Exception {
		NoSuchPathException exception = new NoSuchPathException();
		getWithException(service, client, exception, new NoSuchPathExceptionResponse("Exception", exception));
	}
	@Test
	public void getWithSecurityException(final @Mocked ALEMCService service, final @Mocked ALEMCServicePortType client) throws Exception {
		SecurityException exception = new SecurityException();
		getWithException(service, client, exception, new SecurityExceptionResponse("Exception", exception));
	}
	
	@Test
	public void list(final @Mocked ALEMCService service, final @Mocked ALEMCServicePortType client, final @Mocked ArrayOfString string) throws Exception {
		new NonStrictExpectations() {
			{
				client.list(this.<List>withNotNull());
				result = string.getString();
			}
		};
		MC mc = createMC(service, client, null);
		final String path = Path.Service.EC.EventCycle;
		final String parent = "parent";

		final java.util.List<String> actualList = mc.list(path, parent);

		new Verifications() {
			{
				List parms;
				client.list(parms = withCapture());
				times = 1;
				string.getString();
				times = 1;

				Assert.assertEquals(path, parms.getPath());
				Assert.assertEquals(parent, parms.getParent());
				Assert.assertEquals(string.getString(), actualList);
			}
		};
	}
	
	@Test
	public void listWithParent(final @Mocked ALEMCService service, final @Mocked ALEMCServicePortType client, final @Mocked ArrayOfString string) throws Exception {
		MC mc = createMC(service, client, null);
		final String path = Path.Service.EC.EventCycle;
		final String parent = "parent";

		mc.list(path, parent);

		new Verifications() {
			{
				List parms;
				client.list(parms = withCapture());
				times = 1;
				string.getString();
				times = 1;

				Assert.assertEquals(path, parms.getPath());
				Assert.assertEquals(parent, parms.getParent());
			}
		};
	}
	
	@Test
	public void listWithPathOnly(final @Mocked ALEMCService service, final @Mocked ALEMCServicePortType client, final @Mocked ArrayOfString string) throws Exception {
		MC mc = createMC(service, client, null);
		final String path = Path.Service.EC.EventCycle;

		final java.util.List<String> result = mc.list(path);

		new Verifications() {
			{
				List parms;
				client.list(parms = withCapture());
				times = 1;
				string.getString();
				times = 1;

				Assert.assertEquals(path, parms.getPath());
				Assert.assertNull(parms.getParent());
				Assert.assertEquals(string.getString(), result);
			}
		};
	}
	
	@Test
	public void listWithNull(final @Mocked ALEMCService service, final @Mocked ALEMCServicePortType client, final @Mocked ArrayOfString string) throws Exception {
		new NonStrictExpectations() {
			{
				client.list(this.<List>withNotNull());
				result = null;
			}
		};
		
		MC mc = createMC(service, client, null);
		final String path = Path.Service.EC.EventCycle;

		final java.util.List<String> result = mc.list(path);

		new Verifications() {
			{
				client.list(this.<List>withNotNull());
				times = 1;
				string.getString();
				times = 0;

				Assert.assertNull(result);
			}
		};
	}
	
	private void listWithException(final ALEMCService service, final ALEMCServicePortType client, Exception fault, final Exception response) throws Exception {
		MC mc = createMC(service, client, null);
		new NonStrictExpectations() {
			{
				client.list(this.<List> withNotNull());
				result = response;
			}
		};

		try {
			mc.list(null, null);
			Assert.fail("Expected " + fault.getClass().getSimpleName());
		} catch (Exception e) {
			Assert.assertSame(fault, e);
		}
	}
	
	@Test
	public void listWithImplementationException(final @Mocked ALEMCService service, final @Mocked ALEMCServicePortType client) throws Exception {
		ImplementationException exception = new ImplementationException();
		listWithException(service, client, exception, new ImplementationExceptionResponse("Exception", exception));
	}
	@Test
	public void listWithNoSuchIdException(final @Mocked ALEMCService service, final @Mocked ALEMCServicePortType client) throws Exception {
		NoSuchIdException exception = new NoSuchIdException();
		listWithException(service, client, exception, new NoSuchIdExceptionResponse("Exception", exception));
	}
	@Test
	public void listWithNoSuchPathException(final @Mocked ALEMCService service, final @Mocked ALEMCServicePortType client) throws Exception {
		NoSuchPathException exception = new NoSuchPathException();
		listWithException(service, client, exception, new NoSuchPathExceptionResponse("Exception", exception));
	}
	@Test
	public void listWithSecurityException(final @Mocked ALEMCService service, final @Mocked ALEMCServicePortType client) throws Exception {
		SecurityException exception = new SecurityException();
		listWithException(service, client, exception, new SecurityExceptionResponse("Exception", exception));
	}
	
	@Test
	public void getProperty(final @Mocked ALEMCService service, final @Mocked ALEMCServicePortType client) throws Exception {
		final String expected = "porperty";
		new NonStrictExpectations() {
			{
				client.getProperty(this.<GetProperty>withNotNull());
				result = expected;
			}
		};
		MC mc = createMC(service, client, null);
		final String name = "name";

		final String actual = mc.getProperty(name);

		new Verifications() {
			{
				GetProperty parms;
				client.getProperty(parms = withCapture());
				times = 1;

				Assert.assertEquals(name, parms.getName());
				Assert.assertEquals(expected, actual);
			}
		};
	}
	
	private void getPropertyWithException(final ALEMCService service, final ALEMCServicePortType client, Exception fault, final Exception response) throws Exception {
		MC mc = createMC(service, client, null);
		new NonStrictExpectations() {
			{
				client.getProperty(this.<GetProperty> withNotNull());
				result = response;
			}
		};

		try {
			mc.getProperty(null);
			Assert.fail("Expected " + fault.getClass().getSimpleName());
		} catch (Exception e) {
			Assert.assertSame(fault, e);
		}
	}
	
	@Test
	public void getPropertyWithImplementationException(final @Mocked ALEMCService service, final @Mocked ALEMCServicePortType client) throws Exception {
		ImplementationException exception = new ImplementationException();
		getPropertyWithException(service, client, exception, new ImplementationExceptionResponse("Exception", exception));
	}
	@Test
	public void getPropertyWithNoSuchPropertyException(final @Mocked ALEMCService service, final @Mocked ALEMCServicePortType client) throws Exception {
		NoSuchPropertyException exception = new NoSuchPropertyException();
		getPropertyWithException(service, client, exception, new NoSuchPropertyExceptionResponse("Exception", exception));
	}
	@Test
	public void getPropertyWithSecurityException(final @Mocked ALEMCService service, final @Mocked ALEMCServicePortType client) throws Exception {
		SecurityException exception = new SecurityException();
		getPropertyWithException(service, client, exception, new SecurityExceptionResponse("Exception", exception));
	}
	
	@Test
	public void getProperties(final @Mocked ALEMCService service, final @Mocked ALEMCServicePortType client) throws Exception {
		MC mc = createMC(service, client, null);
		String property = "property";
		final java.util.List<String> properties = new ArrayList<String>(Arrays.asList(property)); 
		final MCProperties props = new MCProperties();
		new NonStrictExpectations() {
			{
				client.getProperties(this.<GetProperties>withNotNull());
				result = props;
			}
		};
		final java.util.List<MCProperty> actual = mc.getProperties(properties);

		new Verifications() {
			{
				GetProperties parms;
				client.getProperties(parms = withCapture());
				times = 1;

				Assert.assertSame(properties, parms.getName());
				Assert.assertEquals(props.getProperty(), actual);
			}
		};
	}
		
	private void getPropertiesWithException(final ALEMCService service, final ALEMCServicePortType client, Exception fault, final Exception response) throws Exception {
		MC mc = createMC(service, client, null);
		new NonStrictExpectations() {
			{
				client.getProperties(this.<GetProperties> withNotNull());
				result = response;
			}
		};

		try {
			mc.getProperties(null);
			Assert.fail("Expected " + fault.getClass().getSimpleName());
		} catch (Exception e) {
			Assert.assertSame(fault, e);
		}
	}
	
	@Test
	public void getPropertiesWithImplementationException(final @Mocked ALEMCService service, final @Mocked ALEMCServicePortType client) throws Exception {
		ImplementationException exception = new ImplementationException();
		getPropertiesWithException(service, client, exception, new ImplementationExceptionResponse("Exception", exception));
	}
	@Test
	public void getPropertiesWithNoSuchPropertyException(final @Mocked ALEMCService service, final @Mocked ALEMCServicePortType client) throws Exception {
		NoSuchPropertyException exception = new NoSuchPropertyException();
		getPropertiesWithException(service, client, exception, new NoSuchPropertyExceptionResponse("Exception", exception));
	}
	@Test
	public void getPropertiesWithSecurityException(final @Mocked ALEMCService service, final @Mocked ALEMCServicePortType client) throws Exception {
		SecurityException exception = new SecurityException();
		getPropertiesWithException(service, client, exception, new SecurityExceptionResponse("Exception", exception));
	}

	@Test
	public void setProperty(final @Mocked ALEMCService service, final @Mocked ALEMCServicePortType client) throws Exception {
		MC mc = createMC(service, client, null);
		final String name = "name";
		final String value = "value";

		mc.setProperty(name, value);

		new Verifications() {
			{
				SetProperty parms;
				client.setProperty(parms = withCapture());
				times = 1;

				Assert.assertEquals(name, parms.getName());
				Assert.assertEquals(value, parms.getValue());
			}
		};
	}
	
	private void setPropertyWithException(final ALEMCService service, final ALEMCServicePortType client, Exception fault, final Exception response) throws Exception {
		MC mc = createMC(service, client, null);
		new NonStrictExpectations() {
			{
				client.setProperty(this.<SetProperty> withNotNull());
				result = response;
			}
		};

		try {
			mc.setProperty(null, null);
			Assert.fail("Expected " + fault.getClass().getSimpleName());
		} catch (Exception e) {
			Assert.assertSame(fault, e);
		}
	}
	
	@Test
	public void setPropertyWithImplementationException(final @Mocked ALEMCService service, final @Mocked ALEMCServicePortType client) throws Exception {
		ImplementationException exception = new ImplementationException();
		setPropertyWithException(service, client, exception, new ImplementationExceptionResponse("Exception", exception));
	}
	@Test
	public void setPropertyWithNoSuchPropertyException(final @Mocked ALEMCService service, final @Mocked ALEMCServicePortType client) throws Exception {
		NoSuchPropertyException exception = new NoSuchPropertyException();
		setPropertyWithException(service, client, exception, new NoSuchPropertyExceptionResponse("Exception", exception));
	}
	@Test
	public void setPropertyWithSecurityException(final @Mocked ALEMCService service, final @Mocked ALEMCServicePortType client) throws Exception {
		SecurityException exception = new SecurityException();
		setPropertyWithException(service, client, exception, new SecurityExceptionResponse("Exception", exception));
	}

	@Test
	public void getStandardVersion(final @Mocked ALEMCService service, final @Mocked ALEMCServicePortType client, final @Mocked String version) throws Exception {
		new NonStrictExpectations() {
			{
				client.getStandardVersion(this.<EmptyParms>withNotNull());
				result = version;
			}
		};
		MC mc = createMC(service, client, null);

		final String result = mc.getStandardVersion();

		new Verifications() {
			{
				client.getStandardVersion(this.<EmptyParms>withNotNull());
				times = 1;

				Assert.assertSame(version, result);
			}
		};
	}
	
	private void getStandardVersionWithException(final ALEMCService service, final ALEMCServicePortType client, Exception fault, final Exception response) throws Exception {
		MC mc = createMC(service, client, null);
		new NonStrictExpectations() {
			{
				client.getStandardVersion(this.<EmptyParms> withNotNull());
				result = response;
			}
		};

		try {
			mc.getStandardVersion();
			Assert.fail("Expected " + fault.getClass().getSimpleName());
		} catch (Exception e) {
			Assert.assertSame(fault, e);
		}
	}
	
	@Test
	public void getStandardVersionWithImplementationException(final @Mocked ALEMCService service, final @Mocked ALEMCServicePortType client) throws Exception {
		ImplementationException exception = new ImplementationException();
		getStandardVersionWithException(service, client, exception, new ImplementationExceptionResponse("Exception", exception));
	}
	
	@Test
	public void getVendorVersion(final @Mocked ALEMCService service, final @Mocked ALEMCServicePortType client, final @Mocked String version) throws Exception {
		new NonStrictExpectations() {
			{
				client.getVendorVersion(this.<EmptyParms>withNotNull());
				result = version;
			}
		};
		MC mc = createMC(service, client, null);

		final String result = mc.getVendorVersion();

		new Verifications() {
			{
				client.getVendorVersion(this.<EmptyParms>withNotNull());
				times = 1;

				Assert.assertSame(version, result);
			}
		};
	}
	
	private void getVendorVersionWithException(final ALEMCService service, final ALEMCServicePortType client, Exception fault, final Exception response) throws Exception {
		MC mc = createMC(service, client, null);
		new NonStrictExpectations() {
			{
				client.getVendorVersion(this.<EmptyParms> withNotNull());
				result = response;
			}
		};

		try {
			mc.getVendorVersion();
			Assert.fail("Expected " + fault.getClass().getSimpleName());
		} catch (Exception e) {
			Assert.assertSame(fault, e);
		}
	}
	
	@Test
	public void getVendorVersionWithImplementationException(final @Mocked ALEMCService service, final @Mocked ALEMCServicePortType client) throws Exception {
		ImplementationException exception = new ImplementationException();
		getVendorVersionWithException(service, client, exception, new ImplementationExceptionResponse("Exception", exception));
	}
	
	@Test
	public void executeWithECAndEnabledSpec(final @Mocked ALEMCService service, final @Mocked ALEMCServicePortType client, final @Mocked EC ec, final @Mocked MCEventCycleSpec spec) throws Exception {
		new NonStrictExpectations() {
			{
				client.get(this.<Get>withNotNull());
				result = spec;
				
				spec.isEnable();
				result = Boolean.TRUE;
				
				spec.getName();
				result = "spec";
			}
		};
		final MC mc = createMC(service, client, null);
		final String path = Path.Service.EC.EventCycle;
		final String id = "id";
		
		final IReports actualReport =  mc.execute(path, id);
		
		new Verifications() {
			{
				ec.poll(this.<String>withNotNull());
				times = 1;
			
				Assert.assertEquals(ec.poll("spec"), actualReport);
			}
		};
	}
	
	@Test
	public void executeWithECAndDisabledSpec(final @Mocked ALEMCService service, final @Mocked ALEMCServicePortType client, final @Mocked EC ec, final @Mocked ECSpec ecSpec, final @Mocked MCEventCycleSpec spec) throws Exception {
		new NonStrictExpectations() {
			{
				client.get(this.<Get>withNotNull());
				result = spec;
				
				spec.isEnable();
				result = Boolean.FALSE;
				
				spec.getSpec();
				result = ecSpec;
			}
		};
		final MC mc = createMC(service, client, null);
		final String path = Path.Service.EC.EventCycle;
		final String id = "id";
		
		final IReports actualReport =  mc.execute(path, id);
		
		new Verifications() {
			{
				ec.immediate(this.<ECSpec>withNotNull());
				times = 1;
			
				Assert.assertEquals(ec.immediate(ecSpec), actualReport);
			}
		};
	}
	
	@Test
	public void executeWithCCAndEnabledSpec(final @Mocked ALEMCService service, final @Mocked ALEMCServicePortType client, final @Mocked CC cc, final @Mocked CCSpec ccSpec, final @Mocked MCCommandCycleSpec spec) throws Exception {
		final String name = "Name";
		new NonStrictExpectations() {
			{
				client.get(this.<Get>withNotNull());
				result = spec;
				
				spec.isEnable();
				result = Boolean.TRUE;
				
				spec.getName();
				result = name;
			}
		};
		final MC mc = createMC(service, client, null);
		final String path = Path.Service.CC.CommandCycle;
		final String id = "id";
		
		final IReports actualReport =  mc.execute(path, id);
		
		new Verifications() {
			{
				cc.poll(this.<String>withNotNull(),null);
				times = 1;
			
				Assert.assertEquals(cc.poll(name,null), actualReport);
			}
		};
	}
	
	@Test
	public void executeWithCCAndDisabledSpec(final @Mocked ALEMCService service, final @Mocked ALEMCServicePortType client, final @Mocked CC cc, final @Mocked CCSpec ccSpec, final @Mocked MCCommandCycleSpec spec) throws Exception {
		new NonStrictExpectations() {
			{
				client.get(this.<Get>withNotNull());
				result = spec;
				
				spec.isEnable();
				result = Boolean.FALSE;
				
				spec.getSpec();
				result = ccSpec;
			}
		};
		final MC mc = createMC(service, client, null);
		final String path = Path.Service.CC.CommandCycle;
		final String id = "id";
		
		final IReports actualReport =  mc.execute(path, id);
		
		new Verifications() {
			{
				cc.immediate(this.<CCSpec>withNotNull());
				times = 1;
			
				Assert.assertEquals(cc.immediate(ccSpec), actualReport);
			}
		};
	}
	
	@Test
	public void executeWithPCAndEnabledSpec(final @Mocked ALEMCService service, final @Mocked ALEMCServicePortType client, final @Mocked PC pc, final @Mocked PCSpec pcSpec, final @Mocked MCPortCycleSpec spec) throws Exception {
		final String name = "Name";
		new NonStrictExpectations() {
			{
				client.get(this.<Get>withNotNull());
				result = spec;
				
				spec.isEnable();
				result = Boolean.TRUE;
				
				spec.getName();
				result = name;
			}
		};
		final MC mc = createMC(service, client, null);
		final String path = Path.Service.PC.PortCycle;
		final String id = "id";
		
		final IReports actualReport =  mc.execute(path, id);
		
		new Verifications() {
			{
				pc.poll(this.<String>withNotNull());
				times = 1;
			
				Assert.assertEquals(pc.poll(name), actualReport);
			}
		};
	}
	
	@Test
	public void executeWithPCAndDisabledSpec(final @Mocked ALEMCService service, final @Mocked ALEMCServicePortType client, final @Mocked PC pc, final @Mocked PCSpec pcSpec, final @Mocked MCPortCycleSpec spec) throws Exception {
		new NonStrictExpectations() {
			{
				client.get(this.<Get>withNotNull());
				result = spec;
				
				spec.isEnable();
				result = Boolean.FALSE;
			}
		};
		final MC mc = createMC(service, client, null);
		final String path = Path.Service.PC.PortCycle;
		final String id = "id";
		
		final IReports actualReport =  mc.execute(path, id);
		
		new Verifications() {
			{
				pc.immediate(this.<PCSpec>withNotNull());
				times = 1;
			
				Assert.assertEquals(pc.immediate(pcSpec), actualReport);
			}
		};
	}

	@Test
	public void executeWithNull(final @Mocked ALEMCService service, final @Mocked ALEMCServicePortType client) throws Exception {
		MC mc = createMC(service, client, null);
		final String path = "wrongPath";
		final String id = "id";
		
		final IReports report =  mc.execute(path, id);
		
		new Verifications() {
			{
				Assert.assertNull(report);
			}
		};
	}

	@Test
	public void executePC(final @Mocked ALEMCService service, final @Mocked ALEMCServicePortType client, final @Mocked PC pc) throws Exception {
		final MCPCOpSpecs specs = new MCPCOpSpecs();
		specs.setSpecs(new PCOpSpecs());
		PCOpSpec op = new PCOpSpec();
		op.setOpName("Test");
		specs.getSpecs().getOpSpec().add(op);
		final java.util.List<PCOpReport> reports = new ArrayList<>();
		new NonStrictExpectations() {
			{
				pc.execute(this.<java.util.List<PCOpSpec>> withNotNull());
				result = reports;
			}
		};
		final MC mc = createMC(service, client, null);

		final java.util.List<PCOpReport> result = mc.execute(specs);
		Assert.assertSame(reports, result);

		new Verifications() {
			{
				pc.execute(withSameInstance(specs.getSpecs().getOpSpec()));
				times = 1;
			}
		};
	}
}
