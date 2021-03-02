package havis.middleware.ale.client;

import havis.middleware.ale.base.exception.ImplementationException;
import havis.middleware.ale.base.exception.NoSuchIdException;
import havis.middleware.ale.base.exception.NoSuchNameException;
import havis.middleware.ale.base.exception.NoSuchPathException;
import havis.middleware.ale.base.exception.NoSuchPropertyException;
import havis.middleware.ale.base.exception.ParameterException;
import havis.middleware.ale.base.exception.ParameterForbiddenException;
import havis.middleware.ale.base.exception.SecurityException;
import havis.middleware.ale.base.exception.ValidationException;
import havis.middleware.ale.config.service.mc.Path;
import havis.middleware.ale.service.IReports;
import havis.middleware.ale.service.cc.CCParameterListEntry;
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

import java.net.URL;

import javax.xml.ws.BindingProvider;
import javax.xml.ws.WebServiceClient;

public class MC implements havis.middleware.ale.service.mc.MC {

	private ALEMCServicePortType mc;

    public MC() {
        ALEMCService service = new ALEMCService(ALEMCService.class.getClassLoader().getResource(
                ALEMCService.class.getAnnotation(WebServiceClient.class).wsdlLocation()));
		mc = service.getALEMCServicePort();
	}

	public MC(URL wsdlLocation) {
		ALEMCService service = new ALEMCService(wsdlLocation);
		mc = service.getALEMCServicePort();
	}

	public MC(ALEMCServicePortType client) {
		this.mc = client;
	}

	public void setEndpoint(String endpoint) {
		((BindingProvider) mc).getRequestContext().put(
				BindingProvider.ENDPOINT_ADDRESS_PROPERTY, endpoint);
	}

	@Override
    public String add(final String _path, final MCSpec _spec,
			final String _parent) throws ImplementationException,
			ValidationException, NoSuchPathException, SecurityException {
		try {
			return mc.add(new Add() {
				{
					path = _path;
					spec = _spec;
					parent = _parent;
				}
			});
		} catch (ImplementationExceptionResponse e) {
			throw e.getFaultInfo();
		} catch (MCSpecValidationExceptionResponse e) {
			throw e.getFaultInfo();
		} catch (NoSuchPathExceptionResponse e) {
			throw e.getFaultInfo();
		} catch (SecurityExceptionResponse e) {
			throw e.getFaultInfo();
		}
	}

	@Override
    public String add(String path, MCSpec spec) throws ImplementationException,
			ValidationException, NoSuchPathException, SecurityException {
		return add(path, spec, null);
	}

	@Override
    public void remove(final String _path, final String _id,
			final String _parent) throws ImplementationException,
			ValidationException, NoSuchIdException, NoSuchPathException,
			SecurityException {
		try {
			mc.remove(new Remove() {
				{
					path = _path;
					id = _id;
					parent = _parent;
				}
			});
		} catch (ImplementationExceptionResponse e) {
			throw e.getFaultInfo();
		} catch (MCSpecValidationExceptionResponse e) {
			throw e.getFaultInfo();
		} catch (NoSuchIdExceptionResponse e) {
			throw e.getFaultInfo();
		} catch (NoSuchPathExceptionResponse e) {
			throw e.getFaultInfo();
		} catch (SecurityExceptionResponse e) {
			throw e.getFaultInfo();
		}
	}

	@Override
    public void remove(String path, String id) throws ImplementationException,
			ValidationException, NoSuchIdException, NoSuchPathException,
			SecurityException {
		remove(path, id, null);
	}

	@Override
    public void update(final String _path, final String _id,
			final MCSpec _spec, final String _parent)
			throws ImplementationException, ValidationException,
			NoSuchIdException, NoSuchPathException, SecurityException {
		try {
			mc.update(new Update() {
				{
					path = _path;
					id = _id;
					spec = _spec;
					parent = _parent;
				}
			});
		} catch (ImplementationExceptionResponse e) {
			throw e.getFaultInfo();
		} catch (MCSpecValidationExceptionResponse e) {
			throw e.getFaultInfo();
		} catch (NoSuchIdExceptionResponse e) {
			throw e.getFaultInfo();
		} catch (NoSuchPathExceptionResponse e) {
			throw e.getFaultInfo();
		} catch (SecurityExceptionResponse e) {
			throw e.getFaultInfo();
		}
	}

	@Override
    public void update(String path, String id, MCSpec spec)
			throws ImplementationException, ValidationException,
			NoSuchIdException, NoSuchPathException, SecurityException {
		update(path, id, spec, null);
	}

	@Override
    public MCSpec get(final String _path, final String _id, final String _parent)
			throws ImplementationException, NoSuchIdException,
			NoSuchPathException, SecurityException {
		try {
			return mc.get(new Get() {
				{
					path = _path;
					id = _id;
					parent = _parent;
				}
			});
		} catch (ImplementationExceptionResponse e) {
			throw e.getFaultInfo();
		} catch (NoSuchIdExceptionResponse e) {
			throw e.getFaultInfo();
		} catch (NoSuchPathExceptionResponse e) {
			throw e.getFaultInfo();
		} catch (SecurityExceptionResponse e) {
			throw e.getFaultInfo();
		}
	}

	@Override
    public MCSpec get(String path, String id) throws ImplementationException,
			NoSuchIdException, NoSuchPathException, SecurityException {
		return get(path, id, null);
	}

	@Override
    public MCSpec get(String path) throws ImplementationException,
			NoSuchIdException, NoSuchPathException, SecurityException {
		return get(path, null, null);
	}

	@Override
    public java.util.List<String> list(final String _path, final String _parent)
			throws ImplementationException, NoSuchIdException,
			NoSuchPathException, SecurityException {
		try {
			ArrayOfString string = mc.list(new List() {
				{
					path = _path;
					parent = _parent;
				}
			});
			if (string != null)
				return string.getString();
			else
				return null;
		} catch (ImplementationExceptionResponse e) {
			throw e.getFaultInfo();
		} catch (NoSuchIdExceptionResponse e) {
			throw e.getFaultInfo();
		} catch (NoSuchPathExceptionResponse e) {
			throw e.getFaultInfo();
		} catch (SecurityExceptionResponse e) {
			throw e.getFaultInfo();
		}
	}

	@Override
    public java.util.List<String> list(String path)
			throws ImplementationException, NoSuchIdException,
			NoSuchPathException, SecurityException {
		return list(path, null);
	}

	@Override
    public String getProperty(final String _name)
			throws ImplementationException, NoSuchPropertyException,
			SecurityException {
		try {
			return mc.getProperty(new GetProperty() {
				{
					name = _name;
				}
			});
		} catch (ImplementationExceptionResponse e) {
			throw e.getFaultInfo();
		} catch (NoSuchPropertyExceptionResponse e) {
			throw e.getFaultInfo();
		} catch (SecurityExceptionResponse e) {
			throw e.getFaultInfo();
		}
	}

	@Override
	public java.util.List<MCProperty> getProperties(final java.util.List<String> _properties)
			throws ImplementationException, NoSuchPropertyException,
			SecurityException {
		try {
			return mc.getProperties(new GetProperties() {
				{
					name = _properties;
				}
			}).getProperty();
		} catch (ImplementationExceptionResponse e) {
			throw e.getFaultInfo();
		} catch (NoSuchPropertyExceptionResponse e) {
			throw e.getFaultInfo();
		} catch (SecurityExceptionResponse e) {
			throw e.getFaultInfo();
		}
	}

	@Override
    public void setProperty(final String _name, final String _value)
			throws ImplementationException, NoSuchPropertyException,
			SecurityException {
		try {
			mc.setProperty(new SetProperty() {
				{
					name = _name;
					value = _value;
				}
			});
		} catch (ImplementationExceptionResponse e) {
			throw e.getFaultInfo();
		} catch (NoSuchPropertyExceptionResponse e) {
			throw e.getFaultInfo();
		} catch (SecurityExceptionResponse e) {
			throw e.getFaultInfo();
		}
	}

	@Override
    public String getStandardVersion() throws ImplementationException {
		try {
			return mc.getStandardVersion(new EmptyParms());
		} catch (ImplementationExceptionResponse e) {
			throw e.getFaultInfo();
		}
	}

	@Override
    public String getVendorVersion() throws ImplementationException {
		try {
			return mc.getVendorVersion(new EmptyParms());
		} catch (ImplementationExceptionResponse e) {
			throw e.getFaultInfo();
		}
	}

	@Override
    public IReports execute(String path, String id)
			throws ImplementationException, NoSuchIdException,
			NoSuchPathException, SecurityException, NoSuchNameException,
			ValidationException, ParameterException,
			ParameterForbiddenException {
		MCSpec spec = get(path, id);
		switch (path) {
		case Path.Service.EC.EventCycle:
			EC ec = new EC();
			if (spec.isEnable()) {
				return ec.poll(spec.getName());
			} else {
				if (spec instanceof MCEventCycleSpec) {
					return ec.immediate(((MCEventCycleSpec) spec).getSpec());
				}
			}
			break;
		case Path.Service.CC.CommandCycle:
			CC cc = new CC();
			if (spec.isEnable()) {
				return cc.poll(spec.getName(), null);
			} else {
				if (spec instanceof MCCommandCycleSpec) {
					return cc.immediate(((MCCommandCycleSpec) spec).getSpec());
				}
			}
			break;
		case Path.Service.PC.PortCycle:
			PC pc = new PC();
			if (spec.isEnable()) {
				return pc.poll(spec.getName());
			} else {
				if (spec instanceof MCPortCycleSpec) {
					return pc.immediate(((MCPortCycleSpec) spec).getSpec());
				}
			}
			break;
		}
		return null;
	}

	@Override
	public java.util.List<PCOpReport> execute(MCPCOpSpecs specs) throws ImplementationException, SecurityException, ValidationException {
		PC pc = new PC();
		return pc.execute(specs.getSpecs().getOpSpec());
	}

	@Override
	public IReports execute(String path, String id, java.util.List<CCParameterListEntry> parameters)
			throws ImplementationException, NoSuchIdException, NoSuchPathException, SecurityException,
			NoSuchNameException, ValidationException, ParameterException, ParameterForbiddenException {
		MCSpec spec = get(path, id);
		CC cc = new CC();
		if (spec.isEnable()) {
			return cc.poll(spec.getName(), parameters);
		} else {
			if (spec instanceof MCCommandCycleSpec) {
				return cc.immediate(((MCCommandCycleSpec) spec).getSpec());
			}
		}
		return null;
	}
}