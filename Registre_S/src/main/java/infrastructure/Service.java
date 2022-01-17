package infrastructure;

import org.glassfish.jersey.internal.inject.AbstractBinder;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.logging.LoggingFeature;
import org.glassfish.jersey.server.ResourceConfig;

import infrastructure.jaxrs.AjouterVersionAuxReponses;
import infrastructure.jaxrs.Cacher;
import infrastructure.jaxrs.CompterReponses;
import infrastructure.jaxrs.CompterRequetes;
import infrastructure.jaxrs.InteragirAtomiquement;
import infrastructure.jaxrs.RealiserEcritureOptimiste;
import modele.Registre;
import modele.Ressource;
import modele.Versionneur;



public class Service extends ResourceConfig {
	public Service(){
		System.out.println("* Chargement de " + this.getClass() + " ...");
		System.out.println("** Configuration");
		this.register(LoggingFeature.class); 
		this.property(LoggingFeature.LOGGING_FEATURE_LOGGER_LEVEL_SERVER, "INFO");
		this.register(JacksonFeature.class);
		
		
		System.out.println("** Ressource");
		// Initialisation de la ressource
		Ressource r = Ressource.SINGLETON;
		
		Versionneur v = new Versionneur(r);
		
		this.register(new AbstractBinder() {
			@Override
			protected void configure() {
				bind(v).to(Versionneur.class);
			}
		});
		
		this.register(CompterRequetes.class);
		this.register(CompterReponses.class);
		this.register(new InteragirAtomiquement());
		this.register(Cacher.class);
		this.register(RealiserEcritureOptimiste.class);
		this.register(AjouterVersionAuxReponses.class);
		
		System.out.println("** Registre ");
		// Initialisation et enregistrement du service
		Registre registre = new Registre(r);
		this.register(registre);
		
	}
}