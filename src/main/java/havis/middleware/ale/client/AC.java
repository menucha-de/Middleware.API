package havis.middleware.ale.client;

import havis.middleware.ale.base.exception.ClientIdentityValidationException;
import havis.middleware.ale.base.exception.DuplicateClientIdentityException;
import havis.middleware.ale.base.exception.DuplicateNameException;
import havis.middleware.ale.base.exception.DuplicatePermissionException;
import havis.middleware.ale.base.exception.DuplicateRoleException;
import havis.middleware.ale.base.exception.ImplementationException;
import havis.middleware.ale.base.exception.NoSuchClientIdentityException;
import havis.middleware.ale.base.exception.NoSuchPermissionException;
import havis.middleware.ale.base.exception.NoSuchRoleException;
import havis.middleware.ale.base.exception.PermissionValidationException;
import havis.middleware.ale.base.exception.RoleValidationException;
import havis.middleware.ale.base.exception.SecurityException;
import havis.middleware.ale.base.exception.UnsupportedOperationException;
import havis.middleware.ale.service.ac.ACClientIdentity;
import havis.middleware.ale.service.ac.ACPermission;
import havis.middleware.ale.service.ac.ACRole;
import havis.middleware.ale.service.ac.ALEACService;
import havis.middleware.ale.service.ac.ALEACServicePortType;
import havis.middleware.ale.service.ac.AddPermissions;
import havis.middleware.ale.service.ac.AddRoles;
import havis.middleware.ale.service.ac.ArrayOfString;
import havis.middleware.ale.service.ac.ClientIdentityValidationExceptionResponse;
import havis.middleware.ale.service.ac.DefineClientIdentity;
import havis.middleware.ale.service.ac.DefinePermission;
import havis.middleware.ale.service.ac.DefineRole;
import havis.middleware.ale.service.ac.DuplicateClientIdentityExceptionResponse;
import havis.middleware.ale.service.ac.DuplicatePermissionExceptionResponse;
import havis.middleware.ale.service.ac.DuplicateRoleExceptionResponse;
import havis.middleware.ale.service.ac.EmptyParms;
import havis.middleware.ale.service.ac.GetClientIdentity;
import havis.middleware.ale.service.ac.GetClientPermissionNames;
import havis.middleware.ale.service.ac.GetPermission;
import havis.middleware.ale.service.ac.GetRole;
import havis.middleware.ale.service.ac.ImplementationExceptionResponse;
import havis.middleware.ale.service.ac.NoSuchClientIdentityExceptionResponse;
import havis.middleware.ale.service.ac.NoSuchPermissionExceptionResponse;
import havis.middleware.ale.service.ac.NoSuchRoleExceptionResponse;
import havis.middleware.ale.service.ac.PermissionValidationExceptionResponse;
import havis.middleware.ale.service.ac.RemovePermissions;
import havis.middleware.ale.service.ac.RemoveRoles;
import havis.middleware.ale.service.ac.RoleValidationExceptionResponse;
import havis.middleware.ale.service.ac.SecurityExceptionResponse;
import havis.middleware.ale.service.ac.SetPermissions;
import havis.middleware.ale.service.ac.SetRoles;
import havis.middleware.ale.service.ac.UndefineClientIdentity;
import havis.middleware.ale.service.ac.UndefinePermission;
import havis.middleware.ale.service.ac.UndefineRole;
import havis.middleware.ale.service.ac.UnsupportedOperationExceptionResponse;
import havis.middleware.ale.service.ac.UpdateClientIdentity;
import havis.middleware.ale.service.ac.UpdatePermission;
import havis.middleware.ale.service.ac.UpdateRole;

import java.net.URL;
import java.util.List;

import javax.xml.ws.BindingProvider;
import javax.xml.ws.WebServiceClient;

public class AC {

	private ALEACServicePortType client;

	public AC() {
        ALEACService service = new ALEACService(ALEACService.class.getClassLoader().getResource(
                ALEACService.class.getAnnotation(WebServiceClient.class).wsdlLocation()));
        client = service.getALEACServicePort();
	}

	public AC(URL wsdlLocation) {
		ALEACService service = new ALEACService(wsdlLocation);
		client = service.getALEACServicePort();
	}

	public AC(ALEACServicePortType client) {
		this.client = client;
	}

	public void setEndpoint(String endpoint) {
		((BindingProvider) client).getRequestContext().put(
				BindingProvider.ENDPOINT_ADDRESS_PROPERTY, endpoint);
	}

	public List<String> getPermissionNames() throws ImplementationException,
			SecurityException, UnsupportedOperationException {
		try {
			ArrayOfString string = client.getPermissionNames(new EmptyParms());
			return string != null ? string.getString() : null;
		} catch (ImplementationExceptionResponse e) {
			throw e.getFaultInfo();
		} catch (SecurityExceptionResponse e) {
			throw e.getFaultInfo();
		} catch (UnsupportedOperationExceptionResponse e) {
			throw e.getFaultInfo();
		}
	}

	public void definePermission(final String _name,
			final ACPermission _perimission)
			throws DuplicatePermissionException, ImplementationException,
			PermissionValidationException, SecurityException,
			UnsupportedOperationException {
		try {
			client.definePermission(new DefinePermission() {
				{
					permName = _name;
					perm = _perimission;
				}
			});
		} catch (DuplicatePermissionExceptionResponse e) {
			throw e.getFaultInfo();
		} catch (ImplementationExceptionResponse e) {
			throw e.getFaultInfo();
		} catch (PermissionValidationExceptionResponse e) {
			throw e.getFaultInfo();
		} catch (SecurityExceptionResponse e) {
			throw e.getFaultInfo();
		} catch (UnsupportedOperationExceptionResponse e) {
			throw e.getFaultInfo();
		}
	}

	public void updatePermission(final String _name,
			final ACPermission _perimission) throws ImplementationException,
			NoSuchPermissionException, PermissionValidationException,
			SecurityException, UnsupportedOperationException {
		try {
			client.updatePermission(new UpdatePermission() {
				{
					permName = _name;
					perm = _perimission;
				}
			});
		} catch (ImplementationExceptionResponse e) {
			throw e.getFaultInfo();
		} catch (NoSuchPermissionExceptionResponse e) {
			throw e.getFaultInfo();
		} catch (PermissionValidationExceptionResponse e) {
			throw e.getFaultInfo();
		} catch (SecurityExceptionResponse e) {
			throw e.getFaultInfo();
		} catch (UnsupportedOperationExceptionResponse e) {
			throw e.getFaultInfo();
		}
	}

	public ACPermission getPermission(final String _name)
			throws ImplementationException, NoSuchPermissionException,
			SecurityException, UnsupportedOperationException {
		try {
			return client.getPermission(new GetPermission() {
				{
					permName = _name;
				}
			});
		} catch (ImplementationExceptionResponse e) {
			throw e.getFaultInfo();
		} catch (NoSuchPermissionExceptionResponse e) {
			throw e.getFaultInfo();
		} catch (SecurityExceptionResponse e) {
			throw e.getFaultInfo();
		} catch (UnsupportedOperationExceptionResponse e) {
			throw e.getFaultInfo();
		}
	}

	public void undefinePermission(final String _name)
			throws ImplementationException, NoSuchPermissionException,
			SecurityException, UnsupportedOperationException {
		try {
			client.undefinePermission(new UndefinePermission() {
				{
					permName = _name;
				}
			});
		} catch (ImplementationExceptionResponse e) {
			throw e.getFaultInfo();
		} catch (NoSuchPermissionExceptionResponse e) {
			throw e.getFaultInfo();
		} catch (SecurityExceptionResponse e) {
			throw e.getFaultInfo();
		} catch (UnsupportedOperationExceptionResponse e) {
			throw e.getFaultInfo();
		}
	}

	public List<String> getRoleNames() throws ImplementationException,
			SecurityException, UnsupportedOperationException {
		try {
			ArrayOfString string = client.getRoleNames(new EmptyParms());
			return string != null ? string.getString() : null;
		} catch (ImplementationExceptionResponse e) {
			throw e.getFaultInfo();
		} catch (SecurityExceptionResponse e) {
			throw e.getFaultInfo();
		} catch (UnsupportedOperationExceptionResponse e) {
			throw e.getFaultInfo();
		}
	}

	public void defineRole(final String _name, final ACRole _role)
			throws DuplicateRoleException, ImplementationException,
			RoleValidationException, SecurityException,

			UnsupportedOperationException {
		try {
			client.defineRole(new DefineRole() {
				{
					roleName = _name;
					role = _role;
				}
			});
		} catch (DuplicateRoleExceptionResponse e) {
			throw e.getFaultInfo();
		} catch (ImplementationExceptionResponse e) {
			throw e.getFaultInfo();
		} catch (RoleValidationExceptionResponse e) {
			throw e.getFaultInfo();
		} catch (SecurityExceptionResponse e) {
			throw e.getFaultInfo();
		} catch (UnsupportedOperationExceptionResponse e) {
			throw e.getFaultInfo();
		}
	}

	public void updateRole(final String _name, final ACRole _role)
			throws ImplementationException, DuplicateNameException,
			NoSuchRoleException, RoleValidationException, SecurityException,
			UnsupportedOperationException {
		try {
			client.updateRole(new UpdateRole() {
				{
					roleName = _name;
					role = _role;
				}
			});
		} catch (ImplementationExceptionResponse e) {
			throw e.getFaultInfo();
		} catch (NoSuchRoleExceptionResponse e) {
			throw e.getFaultInfo();
		} catch (RoleValidationExceptionResponse e) {
			throw e.getFaultInfo();
		} catch (SecurityExceptionResponse e) {
			throw e.getFaultInfo();
		} catch (UnsupportedOperationExceptionResponse e) {
			throw e.getFaultInfo();
		}
	}

	public ACRole getRole(final String _name) throws ImplementationException,
			NoSuchRoleException, SecurityException,
			UnsupportedOperationException {
		try {
			return client.getRole(new GetRole() {
				{
					roleName = _name;
				}
			});
		} catch (ImplementationExceptionResponse e) {
			throw e.getFaultInfo();
		} catch (NoSuchRoleExceptionResponse e) {
			throw e.getFaultInfo();
		} catch (SecurityExceptionResponse e) {
			throw e.getFaultInfo();
		} catch (UnsupportedOperationExceptionResponse e) {
			throw e.getFaultInfo();
		}
	}

	public void undefineRole(final String _name)
			throws ImplementationException, NoSuchRoleException,
			SecurityException, UnsupportedOperationException {
		try {
			client.undefineRole(new UndefineRole() {
				{
					roleName = _name;
				}
			});
		} catch (ImplementationExceptionResponse e) {
			throw e.getFaultInfo();
		} catch (NoSuchRoleExceptionResponse e) {
			throw e.getFaultInfo();
		} catch (SecurityExceptionResponse e) {
			throw e.getFaultInfo();
		} catch (UnsupportedOperationExceptionResponse e) {
			throw e.getFaultInfo();
		}
	}

	public void addPermissions(final String _name,
			final List<String> _permissions) throws ImplementationException,
			NoSuchPermissionException, NoSuchRoleException, SecurityException,
			UnsupportedOperationException {
		try {
			client.addPermissions(new AddPermissions() {
				{
					roleName = _name;
					permissionNames = new PermissionNames() {
						{
							permissionName = _permissions;
						}
					};
				}
			});
		} catch (ImplementationExceptionResponse e) {
			throw e.getFaultInfo();
		} catch (NoSuchPermissionExceptionResponse e) {
			throw e.getFaultInfo();
		} catch (NoSuchRoleExceptionResponse e) {
			throw e.getFaultInfo();
		} catch (SecurityExceptionResponse e) {
			throw e.getFaultInfo();
		} catch (UnsupportedOperationExceptionResponse e) {
			throw e.getFaultInfo();
		}
	}

	public void setPermissions(final String _name,
			final List<String> _permissions) throws ImplementationException,
			NoSuchPermissionException, NoSuchRoleException, SecurityException,
			UnsupportedOperationException {
		try {
			client.setPermissions(new SetPermissions() {
				{
					roleName = _name;
					permissionNames = new PermissionNames() {
						{
							permissionName = _permissions;
						}
					};
				}
			});
		} catch (ImplementationExceptionResponse e) {
			throw e.getFaultInfo();
		} catch (NoSuchPermissionExceptionResponse e) {
			throw e.getFaultInfo();
		} catch (NoSuchRoleExceptionResponse e) {
			throw e.getFaultInfo();
		} catch (SecurityExceptionResponse e) {
			throw e.getFaultInfo();
		} catch (UnsupportedOperationExceptionResponse e) {
			throw e.getFaultInfo();
		}
	}

	public void removePermissions(final String _name,
			final List<String> _permissions) throws ImplementationException,
			NoSuchRoleException, SecurityException,
			UnsupportedOperationException {
		try {
			client.removePermissions(new RemovePermissions() {
				{
					roleName = _name;
					permissionNames = new PermissionNames() {
						{
							permissionName = _permissions;
						}
					};
				}
			});
		} catch (ImplementationExceptionResponse e) {
			throw e.getFaultInfo();
		} catch (NoSuchRoleExceptionResponse e) {
			throw e.getFaultInfo();
		} catch (SecurityExceptionResponse e) {
			throw e.getFaultInfo();
		} catch (UnsupportedOperationExceptionResponse e) {
			throw e.getFaultInfo();
		}
	}

	public List<String> getClientIdentityNames()
			throws ImplementationException, SecurityException,
			UnsupportedOperationException {
		try {
			ArrayOfString string = client
					.getClientIdentityNames(new EmptyParms());
			return string != null ? string.getString() : null;
		} catch (ImplementationExceptionResponse e) {
			throw e.getFaultInfo();
		} catch (SecurityExceptionResponse e) {
			throw e.getFaultInfo();
		} catch (UnsupportedOperationExceptionResponse e) {
			throw e.getFaultInfo();
		}
	}

	public void defineClientIdentity(final String _name,
			final ACClientIdentity _id)
			throws ClientIdentityValidationException,
			DuplicateClientIdentityException, ImplementationException,
			SecurityException, UnsupportedOperationException {
		try {
			client.defineClientIdentity(new DefineClientIdentity() {
				{
					identityName = _name;
					id = _id;
				}
			});
		} catch (ClientIdentityValidationExceptionResponse e) {
			throw e.getFaultInfo();
		} catch (DuplicateClientIdentityExceptionResponse e) {
			throw e.getFaultInfo();
		} catch (ImplementationExceptionResponse e) {
			throw e.getFaultInfo();
		} catch (SecurityExceptionResponse e) {
			throw e.getFaultInfo();
		} catch (UnsupportedOperationExceptionResponse e) {
			throw e.getFaultInfo();
		}
	}

	public void updateClientIdentity(final String _name,
			final ACClientIdentity _id)
			throws ClientIdentityValidationException, ImplementationException,
			NoSuchClientIdentityException, SecurityException,
			UnsupportedOperationException {
		try {
			client.updateClientIdentity(new UpdateClientIdentity() {
				{
					identityName = _name;
					id = _id;
				}
			});
		} catch (ClientIdentityValidationExceptionResponse e) {
			throw e.getFaultInfo();
		} catch (ImplementationExceptionResponse e) {
			throw e.getFaultInfo();
		} catch (NoSuchClientIdentityExceptionResponse e) {
			throw e.getFaultInfo();
		} catch (SecurityExceptionResponse e) {
			throw e.getFaultInfo();
		} catch (UnsupportedOperationExceptionResponse e) {
			throw e.getFaultInfo();
		}
	}

	public ACClientIdentity getClientIdentity(final String _name)
			throws ImplementationException, NoSuchClientIdentityException,
			SecurityException, UnsupportedOperationException {
		try {
			return client.getClientIdentity(new GetClientIdentity() {
				{
					identityName = _name;
				}
			});
		} catch (ImplementationExceptionResponse e) {
			throw e.getFaultInfo();
		} catch (NoSuchClientIdentityExceptionResponse e) {
			throw e.getFaultInfo();
		} catch (SecurityExceptionResponse e) {
			throw e.getFaultInfo();
		} catch (UnsupportedOperationExceptionResponse e) {
			throw e.getFaultInfo();
		}
	}

	public List<String> getClientPermissionNames(final String _name)
			throws ImplementationException, NoSuchClientIdentityException,
			SecurityException, UnsupportedOperationException {
		try {
			ArrayOfString string = client
					.getClientPermissionNames(new GetClientPermissionNames() {
						{
							identityName = _name;
						}
					});
			return string != null ? string.getString() : null;
		} catch (ImplementationExceptionResponse e) {
			throw e.getFaultInfo();
		} catch (NoSuchClientIdentityExceptionResponse e) {
			throw e.getFaultInfo();
		} catch (SecurityExceptionResponse e) {
			throw e.getFaultInfo();
		} catch (UnsupportedOperationExceptionResponse e) {
			throw e.getFaultInfo();
		}
	}

	public void undefineClientIdentity(final String _name)
			throws ImplementationException, NoSuchClientIdentityException,
			SecurityException, UnsupportedOperationException {
		try {
			client.undefineClientIdentity(new UndefineClientIdentity() {
				{
					identityName = _name;
				}
			});
		} catch (ImplementationExceptionResponse e) {
			throw e.getFaultInfo();
		} catch (NoSuchClientIdentityExceptionResponse e) {
			throw e.getFaultInfo();
		} catch (SecurityExceptionResponse e) {
			throw e.getFaultInfo();
		} catch (UnsupportedOperationExceptionResponse e) {
			throw e.getFaultInfo();
		}
	}

	public void addRoles(final String _name, final List<String> _roles)
			throws ImplementationException, NoSuchClientIdentityException,
			SecurityException, NoSuchRoleException,
			UnsupportedOperationException {
		try {
			client.addRoles(new AddRoles() {
				{
					identityName = _name;
					roleNames = new RoleNames() {
						{
							roleName = _roles;
						}
					};
				}
			});
		} catch (ImplementationExceptionResponse e) {
			throw e.getFaultInfo();
		} catch (NoSuchClientIdentityExceptionResponse e) {
			throw e.getFaultInfo();
		} catch (NoSuchRoleExceptionResponse e) {
			throw e.getFaultInfo();
		} catch (SecurityExceptionResponse e) {
			throw e.getFaultInfo();
		} catch (UnsupportedOperationExceptionResponse e) {
			throw e.getFaultInfo();
		}
	}

	public void setRoles(final String _name, final List<String> _roles)
			throws ImplementationException, NoSuchClientIdentityException,
			NoSuchRoleException, SecurityException,
			UnsupportedOperationException {
		try {
			client.setRoles(new SetRoles() {
				{
					identityName = _name;
					roleNames = new RoleNames() {
						{
							roleName = _roles;
						}
					};
				}
			});
		} catch (ImplementationExceptionResponse e) {
			throw e.getFaultInfo();
		} catch (NoSuchClientIdentityExceptionResponse e) {
			throw e.getFaultInfo();
		} catch (NoSuchRoleExceptionResponse e) {
			throw e.getFaultInfo();
		} catch (SecurityExceptionResponse e) {
			throw e.getFaultInfo();
		} catch (UnsupportedOperationExceptionResponse e) {
			throw e.getFaultInfo();
		}
	}

	public void removeRoles(final String _name, final List<String> _roles)
			throws ImplementationException, NoSuchClientIdentityException,
			SecurityException, UnsupportedOperationException {
		try {
			client.removeRoles(new RemoveRoles() {
				{
					identityName = _name;
					roleNames = new RoleNames() {
						{
							roleName = _roles;
						}
					};
				}
			});
		} catch (ImplementationExceptionResponse e) {
			throw e.getFaultInfo();
		} catch (NoSuchClientIdentityExceptionResponse e) {
			throw e.getFaultInfo();
		} catch (SecurityExceptionResponse e) {
			throw e.getFaultInfo();
		} catch (UnsupportedOperationExceptionResponse e) {
			throw e.getFaultInfo();
		}
	}

	public List<String> getSupportedOperations() throws ImplementationException {
		try {
			ArrayOfString string = client
					.getSupportedOperations(new EmptyParms());
			return string != null ? string.getString() : null;
		} catch (ImplementationExceptionResponse e) {
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