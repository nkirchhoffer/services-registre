package infrastructure.jaxrs;

import java.util.concurrent.atomic.AtomicInteger;

import infrastructure.jaxrs.annotations.AtomiciteRequeteReponseServeur;

import java.io.IOException;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.ext.Provider;
import javax.annotation.Priority;
import javax.ws.rs.Priorities;

@Provider
@AtomiciteRequeteReponseServeur
@Priority(Priorities.AUTHORIZATION + 10)
public class InteragirAtomiquement implements ContainerRequestFilter,
		ContainerResponseFilter {

	private ReadWriteLock verrou;
	private AtomicInteger nombreLecteurs = new AtomicInteger(0);
	private AtomicInteger nombreScribes = new AtomicInteger(0);

	/**
	 * Permet de maintenir un verrou pour protéger l'accès aux ressources
	 */
	public InteragirAtomiquement() {
		verrou = new ReentrantReadWriteLock();
		System.out.println("* Initialisation du filtre " + this + " : " + this.getClass());
	}

	@Override
	/**
	 * Ouverture du verrou pour les accès en lecture et écriture après la réponse du serveur
	 */
	public void filter(ContainerRequestContext requete,
			ContainerResponseContext reponse) throws IOException {
		if (requete.getMethod().equalsIgnoreCase("PUT")) {
			/**
			 * Décrémentation du nombre de scribes (= clients qui écrivent)
			 * Ouverture du verrou pour rendre l'écriture accessible à d'autres clients
			 */
			nombreScribes.decrementAndGet();
			verrou.writeLock().unlock();
			return;
		}
		if (requete.getMethod().equalsIgnoreCase("GET")) {
			/**
			 * Décrémentation du nombre de lecteurs (= clients qui accèdent à la ressource)
			 * Ouverture du verrou pour rendre la lecture accessible à d'autres clients
			 */
				nombreLecteurs.decrementAndGet();
				verrou.readLock().unlock();
				return;
		}
	}

	/**
	 * Mise en place d'un verrou sur les accès en lecture et écriture
	 */
	@Override
	public void filter(ContainerRequestContext requete) throws IOException {
		if (requete.getMethod().equalsIgnoreCase("PUT")) {
			/**
			 * Incrémentation du nombre de scribes (= clients qui écrivent)
			 * Mise en place du verrou sur l'accès en écriture pour empêcher la corruption de la donnée suite à la concurrence
			 */
			verrou.writeLock().lock();
			nombreScribes.incrementAndGet();
			System.out.println("Scribes : " + nombreScribes.get());
			return;
		}
		if (requete.getMethod().equalsIgnoreCase("GET")) {
			/**
			 * Incrémentation du nombre de lecteurs (= clients qui accèdent à la ressource)
			 * Mise en place du verrou sur l'accès en lecture pour empêcher un affichage d'une mauvaise valeur
			 */
				verrou.readLock().lock();
				nombreLecteurs.incrementAndGet();
				System.out.println("Lecteurs : " + nombreLecteurs.get());
			return;
		}
	}

}
