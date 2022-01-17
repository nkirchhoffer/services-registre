package modele;

/**
 * Permet de stocker une ressource ainsi qu'une version associée
 * Permet de retracer les différents accès en écriture pour éviter la corruption de donnée
 *
 */
public class Versionneur {

	private int version;
	private Object ressource;
	
	public Versionneur(Object d) {
		System.out.println("Initialisation d'un versionneur " + this + " : " + this.getClass());
		System.out.println("- Version initiale : 0");
		System.out.println("- Ressource : " + d + " : " + d.getClass());
		this.version = 0;
		this.ressource = d;
	}

	public Object getRessourceMutable(){
		return this.ressource;
	}
	
	public int getVersion() {
		return this.version;
	}
	
	public void incrementerVersion(){
		this.version++;
	}

}



