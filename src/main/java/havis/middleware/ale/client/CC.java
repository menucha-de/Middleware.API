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
import havis.middleware.ale.service.cc.ALECCService;
import havis.middleware.ale.service.cc.ALECCServicePortType;
import havis.middleware.ale.service.cc.ArrayOfString;
import havis.middleware.ale.service.cc.AssocTableEntry;
import havis.middleware.ale.service.cc.AssocTableEntryList;
import havis.middleware.ale.service.cc.AssocTableSpec;
import havis.middleware.ale.service.cc.AssocTableValidationExceptionResponse;
import havis.middleware.ale.service.cc.CCParameterList;
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

import java.net.URL;
import java.util.List;

import javax.xml.ws.BindingProvider;
import javax.xml.ws.WebServiceClient;

public class CC {

	private ALECCServicePortType client;

    public CC() {
        ALECCService service = new ALECCService(ALECCService.class.getClassLoader().getResource(
                ALECCService.class.getAnnotation(WebServiceClient.class).wsdlLocation()));
        client = service.getALECCServicePort();
    }

	public CC(URL wsdlLocation) {
		ALECCService service = new ALECCService(wsdlLocation);
		client = service.getALECCServicePort();
	}

	public CC(ALECCServicePortType client) {
		this.client = client;
	}

	public void setEndpoint(String endpoint) {
		((BindingProvider) client).getRequestContext().put(
				BindingProvider.ENDPOINT_ADDRESS_PROPERTY, endpoint);
	}

	public void define(final String name, final CCSpec _spec)
			throws ValidationException, DuplicateNameException,
			ImplementationException, SecurityException {
		try {
			client.define(new Define() {
				{
					specName = name;
					spec = _spec;
				}
			});
		} catch (CCSpecValidationExceptionResponse e) {
			throw e.getFaultInfo();
		} catch (DuplicateNameExceptionResponse e) {
			throw e.getFaultInfo();
		} catch (ImplementationExceptionResponse e) {
			throw e.getFaultInfo();
		} catch (SecurityExceptionResponse e) {
			throw e.getFaultInfo();
		}
	}

	public void undefine(final String name) throws ImplementationException,
			NoSuchNameException, SecurityException {
		try {
			client.undefine(new Undefine() {
				{
					specName = name;
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

	public CCSpec getSpec(final String name) throws ImplementationException,
			NoSuchNameException, SecurityException {
		try {
			return client.getCCSpec(new GetCCSpec() {
				{
					specName = name;
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
			ArrayOfString string = client.getCCSpecNames(new EmptyParms());
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

	public void subscribe(final String name, final String uri)
			throws DuplicateSubscriptionException, ImplementationException,
			InvalidURIException, NoSuchNameException,
			ParameterForbiddenException, SecurityException {
		try {
			client.subscribe(new Subscribe() {
				{
					specName = name;
					notificationURI = uri;
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
		} catch (ParameterForbiddenExceptionResponse e) {
			throw e.getFaultInfo();
		} catch (SecurityExceptionResponse e) {
			throw e.getFaultInfo();
		}
	}

	public void unsubscribe(final String name, final String uri)
			throws ImplementationException, InvalidURIException,
			NoSuchNameException, SecurityException, NoSuchSubscriberException {
		try {
			client.unsubscribe(new Unsubscribe() {
				{
					specName = name;
					notificationURI = uri;
				}
			});
		} catch (ImplementationExceptionResponse e) {
			throw e.getFaultInfo();
		} catch (InvalidURIExceptionResponse e) {
			throw e.getFaultInfo();
		} catch (NoSuchNameExceptionResponse e) {
			throw e.getFaultInfo();
		} catch (SecurityExceptionResponse e) {
			throw e.getFaultInfo();
		} catch (NoSuchSubscriberExceptionResponse e) {
			throw e.getFaultInfo();
		}
	}

	public CCReports poll(final String name,
			final List<CCParameterListEntry> _entries)
			throws ImplementationException, NoSuchNameException,
			ParameterException, SecurityException {
		try {
			return client.poll(new Poll() {
				{
					specName = name;
					params = new CCParameterList() {
						{
							entries = new Entries() {
								{
									entry = _entries;
								}
							};
						}
					};
				}
			});
		} catch (ImplementationExceptionResponse e) {
			throw e.getFaultInfo();
		} catch (NoSuchNameExceptionResponse e) {
			throw e.getFaultInfo();
		} catch (ParameterExceptionResponse e) {
			throw e.getFaultInfo();
		} catch (SecurityExceptionResponse e) {
			throw e.getFaultInfo();
		}
	}

	public CCReports immediate(final CCSpec _spec) throws ValidationException,
			ImplementationException, ParameterForbiddenException,
			SecurityException {
		try {
			return client.immediate(new Immediate() {
				{
					spec = _spec;
				}
			});
		} catch (CCSpecValidationExceptionResponse e) {
			throw e.getFaultInfo();
		} catch (ImplementationExceptionResponse e) {
			throw e.getFaultInfo();
		} catch (ParameterForbiddenExceptionResponse e) {
			throw e.getFaultInfo();
		} catch (SecurityExceptionResponse e) {
			throw e.getFaultInfo();
		}
	}

	public List<String> getSubscribers(final String name)
			throws ImplementationException, NoSuchNameException,
			SecurityException {

		try {
			ArrayOfString string = client.getSubscribers(new GetSubscribers() {
				{
					specName = name;
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

	public void defineEPCCache(final String name, final EPCCacheSpec _spec,
			final List<String> _replenishment) throws DuplicateNameException,
			EPCCacheSpecValidationException, ImplementationException,
			InvalidPatternException, SecurityException {
		try {
			client.defineEPCCache(new DefineEPCCache() {
				{
					cacheName = name;
					spec = _spec;
					replenishment = new EPCPatternList(_replenishment);
				}
			});
		} catch (DuplicateNameExceptionResponse e) {
			throw e.getFaultInfo();
		} catch (EPCCacheSpecValidationExceptionResponse e) {
			throw e.getFaultInfo();
		} catch (ImplementationExceptionResponse e) {
			throw e.getFaultInfo();
		} catch (InvalidPatternExceptionResponse e) {
			throw e.getFaultInfo();
		} catch (SecurityExceptionResponse e) {
			throw e.getFaultInfo();
		}
	}

	public List<String> undefineEPCCache(final String name)
			throws ImplementationException, InUseException,
			NoSuchNameException, SecurityException {
		try {
			EPCPatternList list = client
					.undefineEPCCache(new UndefineEPCCache() {
						{
							cacheName = name;
						}
					});
			if (list != null) {
				Patterns patterns = list.getPatterns();
				if (patterns != null)
					return patterns.getPattern();
			}
			return null;
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

	public EPCCacheSpec getEPCCache(final String name)
			throws ImplementationException, NoSuchNameException,
			SecurityException {
		try {
			return client.getEPCCache(new GetEPCCache() {
				{
					cacheName = name;
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

	public List<String> getEPCCacheNames() throws ImplementationException,
			SecurityException {
		try {
			ArrayOfString string = client.getEPCCacheNames(new EmptyParms());
			return string != null ? string.getString() : null;
		} catch (ImplementationExceptionResponse e) {
			throw e.getFaultInfo();
		} catch (SecurityExceptionResponse e) {
			throw e.getFaultInfo();
		}
	}

	public void replenishEPCCache(final String _name,
			final List<String> _replenishment) throws ImplementationException,
			InvalidPatternException, NoSuchNameException, SecurityException {
		try {
			client.replenishEPCCache(new ReplenishEPCCache() {
				{
					cacheName = _name;
					replenishment = new EPCPatternList(_replenishment);
				}
			});
		} catch (ImplementationExceptionResponse e) {
			throw e.getFaultInfo();
		} catch (InvalidPatternExceptionResponse e) {
			throw e.getFaultInfo();
		} catch (NoSuchNameExceptionResponse e) {
			throw e.getFaultInfo();
		} catch (SecurityExceptionResponse e) {
			throw e.getFaultInfo();
		}
	}

	public List<String> depleteEPCCache(final String name)
			throws ImplementationException, NoSuchNameException,
			SecurityException {
		try {
			EPCPatternList list = client.depleteEPCCache(new DepleteEPCCache() {
				{
					cacheName = name;
				}
			});
			if (list != null) {
				Patterns patterns = list.getPatterns();
				if (patterns != null)
					return patterns.getPattern();
			}
			return null;
		} catch (ImplementationExceptionResponse e) {
			throw e.getFaultInfo();
		} catch (NoSuchNameExceptionResponse e) {
			throw e.getFaultInfo();
		} catch (SecurityExceptionResponse e) {
			throw e.getFaultInfo();
		}
	}

	public List<String> getEPCCacheContents(final String name)
			throws ImplementationException, NoSuchNameException,
			SecurityException {
		try {
			EPCPatternList list = client
					.getEPCCacheContents(new GetEPCCacheContents() {
						{
							cacheName = name;
						}
					});
			if (list != null) {
				Patterns patterns = list.getPatterns();
				if (patterns != null)
					return patterns.getPattern();
			}
			return null;
		} catch (ImplementationExceptionResponse e) {
			throw e.getFaultInfo();
		} catch (NoSuchNameExceptionResponse e) {
			throw e.getFaultInfo();
		} catch (SecurityExceptionResponse e) {
			throw e.getFaultInfo();
		}
	}

	public void defineAssocTable(final String name, final AssocTableSpec _spec,
			final List<AssocTableEntry> _entries)
			throws AssocTableValidationException, DuplicateNameException,
			ImplementationException, InvalidAssocTableEntryException,
			SecurityException {
		try {
			client.defineAssocTable(new DefineAssocTable() {
				{
					tableName = name;
					spec = _spec;
					entries = new AssocTableEntryList(_entries);
				}
			});
		} catch (AssocTableValidationExceptionResponse e) {
			throw e.getFaultInfo();
		} catch (DuplicateNameExceptionResponse e) {
			throw e.getFaultInfo();
		} catch (ImplementationExceptionResponse e) {
			throw e.getFaultInfo();
		} catch (InvalidAssocTableEntryExceptionResponse e) {
			throw e.getFaultInfo();
		} catch (SecurityExceptionResponse e) {
			throw e.getFaultInfo();
		}
	}

	public void undefineAssocTable(final String name)
			throws ImplementationException, InUseException,
			NoSuchNameException, SecurityException {
		try {
			client.undefineAssocTable(new UndefineAssocTable() {
				{
					tableName = name;
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

	public List<String> getAssocTableNames() throws ImplementationException,
			SecurityException {
		try {
			ArrayOfString string = client.getAssocTableNames(new EmptyParms());
			return string != null ? string.getString() : null;
		} catch (ImplementationExceptionResponse e) {
			throw e.getFaultInfo();
		} catch (SecurityExceptionResponse e) {
			throw e.getFaultInfo();
		}
	}

	public AssocTableSpec getAssocTable(final String name)
			throws ImplementationException, NoSuchNameException,
			SecurityException {
		try {
			return client.getAssocTable(new GetAssocTable() {
				{
					tableName = name;
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

	public void putAssocTableEntries(final String name,
			final List<AssocTableEntry> _entries)
			throws ImplementationException, InvalidAssocTableEntryException,
			NoSuchNameException, SecurityException {
		try {
			client.putAssocTableEntries(new PutAssocTableEntries() {
				{
					tableName = name;
					entries = new AssocTableEntryList(_entries);
				}
			});
		} catch (ImplementationExceptionResponse e) {
			throw e.getFaultInfo();
		} catch (InvalidAssocTableEntryExceptionResponse e) {
			throw e.getFaultInfo();
		} catch (NoSuchNameExceptionResponse e) {
			throw e.getFaultInfo();
		} catch (SecurityExceptionResponse e) {
			throw e.getFaultInfo();
		}
	}

	public String getAssocTableValue(final String name, final String _epc)
			throws ImplementationException, InvalidEPCException,
			NoSuchNameException, SecurityException {
		try {
			return client.getAssocTableValue(new GetAssocTableValue() {
				{
					tableName = name;
					epc = _epc;
				}
			});
		} catch (ImplementationExceptionResponse e) {
			throw e.getFaultInfo();
		} catch (InvalidEPCExceptionResponse e) {
			throw e.getFaultInfo();
		} catch (NoSuchNameExceptionResponse e) {
			throw e.getFaultInfo();
		} catch (SecurityExceptionResponse e) {
			throw e.getFaultInfo();
		}
	}

	public List<AssocTableEntry> getAssocTableEntries(final String name,
			final List<String> pattern) throws ImplementationException,
			InvalidPatternException, NoSuchNameException, SecurityException {
		try {
			AssocTableEntryList list = client
					.getAssocTableEntries(new GetAssocTableEntries() {
						{
							tableName = name;
							patList = new EPCPatternList(pattern);
						}
					});
			return list != null ? list.getEntries().getEntry() : null;
		} catch (ImplementationExceptionResponse e) {
			throw e.getFaultInfo();
		} catch (InvalidPatternExceptionResponse e) {
			throw e.getFaultInfo();
		} catch (NoSuchNameExceptionResponse e) {
			throw e.getFaultInfo();
		} catch (SecurityExceptionResponse e) {
			throw e.getFaultInfo();
		}
	}

	public void removeAssocTableEntry(final String name, final String _epc)
			throws ImplementationException, InvalidEPCException,
			NoSuchNameException, SecurityException {
		try {
			client.removeAssocTableEntry(new RemoveAssocTableEntry() {
				{
					tableName = name;
					epc = _epc;
				}
			});
		} catch (ImplementationExceptionResponse e) {
			throw e.getFaultInfo();
		} catch (InvalidEPCExceptionResponse e) {
			throw e.getFaultInfo();
		} catch (NoSuchNameExceptionResponse e) {
			throw e.getFaultInfo();
		} catch (SecurityExceptionResponse e) {
			throw e.getFaultInfo();
		}
	}

	public void removeAssocTableEntries(final String name,
			final List<String> pattern) throws ImplementationException,
			InvalidPatternException, NoSuchNameException, SecurityException {
		try {
			client.removeAssocTableEntries(new RemoveAssocTableEntries() {
				{
					tableName = name;
					patList = new EPCPatternList(pattern);
				}
			});
		} catch (ImplementationExceptionResponse e) {
			throw e.getFaultInfo();
		} catch (InvalidPatternExceptionResponse e) {
			throw e.getFaultInfo();
		} catch (NoSuchNameExceptionResponse e) {
			throw e.getFaultInfo();
		} catch (SecurityExceptionResponse e) {
			throw e.getFaultInfo();
		}
	}

	public void defineRNG(final String name, final RNGSpec spec)
			throws DuplicateNameException, ImplementationException,
			RNGValidationException, SecurityException {
		try {
			client.defineRNG(new DefineRNG() {
				{
					rngName = name;
					rngSpec = spec;
				}
			});
		} catch (DuplicateNameExceptionResponse e) {
			throw e.getFaultInfo();
		} catch (ImplementationExceptionResponse e) {
			throw e.getFaultInfo();
		} catch (RNGValidationExceptionResponse e) {
			throw e.getFaultInfo();
		} catch (SecurityExceptionResponse e) {
			throw e.getFaultInfo();
		}
	}

	public void undefineRNG(final String name) throws ImplementationException,
			InUseException, NoSuchNameException, SecurityException {
		try {
			client.undefineRNG(new UndefineRNG() {
				{
					rngName = name;
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

	public List<String> getRNGNames() throws ImplementationException,
			SecurityException {
		try {
			ArrayOfString string = client.getRNGNames(new EmptyParms());
			return string != null ? string.getString() : null;
		} catch (ImplementationExceptionResponse e) {
			throw e.getFaultInfo();
		} catch (SecurityExceptionResponse e) {
			throw e.getFaultInfo();
		}
	}

	public RNGSpec getRNG(final String name) throws ImplementationException,
			NoSuchNameException, SecurityException {
		try {
			return client.getRNG(new GetRNG() {
				{
					rngName = name;
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
}