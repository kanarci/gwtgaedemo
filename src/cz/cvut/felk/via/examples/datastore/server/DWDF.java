package cz.cvut.felk.via.examples.datastore.server;

public final class DWDF {

	/**
	 * Instance of Persistence Manager Factory
	 */
	private static final DatastoreWatchDog dwdInstance = new DatastoreWatchDog();

	/**
	 * Default constructor
	 */
	private DWDF() {
	}

	/**
	 * 
	 * @return Instance of Persistence Manager Factory
	 */
	public static DatastoreWatchDog get() {
		return dwdInstance;
	}
}
