package modele;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("optimiste")
public interface ServiceRegistre {
	@PUT
	@Produces(MediaType.APPLICATION_XML)
	@Consumes(MediaType.APPLICATION_XML)
	public Ressource set(Ressource n);

	@GET
	@Produces(MediaType.APPLICATION_XML)
	public Ressource get();
}
