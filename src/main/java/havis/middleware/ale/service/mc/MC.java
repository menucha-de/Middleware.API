package havis.middleware.ale.service.mc;

import havis.middleware.ale.base.exception.ImplementationException;
import havis.middleware.ale.base.exception.NoSuchIdException;
import havis.middleware.ale.base.exception.NoSuchNameException;
import havis.middleware.ale.base.exception.NoSuchPathException;
import havis.middleware.ale.base.exception.NoSuchPropertyException;
import havis.middleware.ale.base.exception.ParameterException;
import havis.middleware.ale.base.exception.ParameterForbiddenException;
import havis.middleware.ale.base.exception.SecurityException;
import havis.middleware.ale.base.exception.ValidationException;
import havis.middleware.ale.service.IReports;
import havis.middleware.ale.service.cc.CCParameterListEntry;
import havis.middleware.ale.service.pc.PCOpReport;

import java.util.List;

public interface MC {

	String add(String path, MCSpec spec, String parent)
			throws ImplementationException, ValidationException,
			NoSuchPathException, SecurityException;

	String add(String path, MCSpec spec) throws ImplementationException,
			ValidationException, NoSuchPathException, SecurityException;

	void remove(String path, String id, String parent)
			throws ImplementationException, ValidationException,
			NoSuchIdException, NoSuchPathException, SecurityException;

	void remove(String path, String id) throws ImplementationException,
			ValidationException, NoSuchIdException, NoSuchPathException,
			SecurityException;

	void update(String path, String id, MCSpec spec, final String _parent)
			throws ImplementationException, ValidationException,
			NoSuchIdException, NoSuchPathException, SecurityException;

	public void update(String path, String id, MCSpec spec)
			throws ImplementationException, ValidationException,
			NoSuchIdException, NoSuchPathException, SecurityException;

	MCSpec get(String path, String id, String parent)
			throws ImplementationException, NoSuchIdException,
			NoSuchPathException, SecurityException;

	MCSpec get(String path, String id) throws ImplementationException,
			NoSuchIdException, NoSuchPathException, SecurityException;

	MCSpec get(String path) throws ImplementationException, NoSuchIdException,
			NoSuchPathException, SecurityException;

	List<String> list(String path, String parent)
			throws ImplementationException, NoSuchIdException,
			NoSuchPathException, SecurityException;

	List<String> list(String path) throws ImplementationException,
			NoSuchIdException, NoSuchPathException, SecurityException;

	String getProperty(String name) throws ImplementationException,
			NoSuchPropertyException, SecurityException;

	List<MCProperty> getProperties(List<String> properties) throws ImplementationException,
			NoSuchPropertyException, SecurityException;

	void setProperty(String name, String value) throws ImplementationException,
			NoSuchPropertyException, SecurityException;

	String getStandardVersion() throws ImplementationException;

	String getVendorVersion() throws ImplementationException;

	IReports execute(String path, String id) throws ImplementationException,
			NoSuchIdException, NoSuchPathException, SecurityException,
			NoSuchNameException, ValidationException, ParameterException,
			ParameterForbiddenException;

	List<PCOpReport> execute(MCPCOpSpecs specs) throws ImplementationException,
			SecurityException, ValidationException;

	IReports execute(String path, String id, List<CCParameterListEntry> parameters) throws ImplementationException,
		NoSuchIdException, NoSuchPathException, SecurityException,
		NoSuchNameException, ValidationException, ParameterException,
		ParameterForbiddenException;
}