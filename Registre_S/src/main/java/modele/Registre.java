package modele;

import javax.inject.Singleton;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;

@Singleton
@Path("optimiste")
public class Registre implements ServiceRegistre {

	private Ressource n;
	
	public Registre(Ressource i) {
		System.out.println("Initialisation du registre " + this + " : " + this.getClass());
		System.out.println("- Ressource " + i + " : " + i.getClass());
		n = i;
	}

	@Override
	public Ressource set(Ressource n) {
		this.n.setI(n.getI());
		return this.n;
	}

	@Override
	public Ressource get() {
		return new Ressource(this.n.getI());
	}

}



