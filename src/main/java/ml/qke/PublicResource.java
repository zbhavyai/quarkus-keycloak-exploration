package ml.qke;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

import org.keycloak.representations.idm.authorization.Permission;

import io.smallrye.mutiny.Uni;

@Path("/api/public")
public class PublicResource {

    @GET
    public void serve() {
        // no-op
    }

    @GET
    @Path("permissions")
    public Uni<List<Permission>> get() {
        return new ProtectedResource().get();
    }
}
