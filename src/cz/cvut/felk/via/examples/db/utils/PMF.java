package cz.cvut.felk.via.examples.db.utils;

import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManagerFactory;

/**
 * 
 * @author Floatrix Singleton of Persistence manager factory
 */
public final class PMF {

	/**
	 * Instance of Persistence Manager Factory
	 */
	private static final PersistenceManagerFactory pmfInstance = JDOHelper
			.getPersistenceManagerFactory("transactions-optional");

	/**
	 * Default constructor
	 */
	private PMF() {
	}

	/**
	 * 
	 * @return Instance of Persistence Manager Factory
	 */
	public static PersistenceManagerFactory get() {
		return pmfInstance;
	}
}