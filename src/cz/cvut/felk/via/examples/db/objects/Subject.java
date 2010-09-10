package cz.cvut.felk.via.examples.db.objects;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.api.datastore.Key;

//enables detaching this object in the future
@PersistenceCapable(detachable = "true")
public class Subject {

	// Tell JDO to use this field ad a primary key
	@PrimaryKey
	// Automatic key generation when saving it in to the datastore
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private Key key;

	@Persistent
	private String name;

	@Persistent
	private String code;

	public Subject(String name, String code) {
		super();
		this.name = name;
		this.code = code;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the code
	 */
	public String getCode() {
		return code;
	}

	/**
	 * @param code
	 *            the code to set
	 */
	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * @return the key
	 */
	public Key getKey() {
		return key;
	}

	@Override
	public String toString() {
		return name + " : " + code;
	}

}
