package stefaninifood.dao;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class JPAUtil {

	private JPAUtil() {
		throw new IllegalStateException("Utility class");
	}

	private static EntityManagerFactory emf = Persistence.createEntityManagerFactory("stefaninifoodPU");

	public static EntityManager getEntityManager() {
		return emf.createEntityManager();
	}

	public static void close(EntityManager em) {
		em.close();
	}

}
