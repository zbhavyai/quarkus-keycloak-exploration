package ml.qke;

import java.util.List;

import javax.inject.Inject;
import javax.security.auth.AuthPermission;
import javax.ws.rs.ForbiddenException;
import javax.ws.rs.GET;
import javax.ws.rs.Path;

import org.keycloak.authorization.client.AuthzClient;
import org.keycloak.representations.idm.authorization.Permission;

import io.quarkus.security.identity.SecurityIdentity;
import io.smallrye.mutiny.Uni;

@Path("/api/protected")
public class ProtectedResource {

    @Inject
    AuthzClient authzClient;

    @Inject
    SecurityIdentity identity;

    @GET
    public Uni<List<Permission>> get() {
        return identity.checkPermission(new AuthPermission("{resource_name}")).onItem()
                .transform(granted -> {
                    if (granted) {
                        return identity.getAttribute("permissions");
                    }
                    throw new ForbiddenException();
                });
    }

    @GET
    @Path("/test")
    public Uni<Boolean> test() {
        return identity.checkPermission(new AuthPermission("{resource_name}"));
        // authzClient.authorization();
    }
}
