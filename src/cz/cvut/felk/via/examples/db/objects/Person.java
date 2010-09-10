package cz.cvut.felk.via.examples.db.objects;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.Inheritance;
import javax.jdo.annotations.InheritanceStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.api.datastore.Key;

// enables detaching this object in the future
@PersistenceCapable(detachable = "true")
/*
 * inheritance strategy, see
 * http://code.google.com/appengine/docs/java/datastore
 * /dataclasses.html#Inheritance for more details
 */
@Inheritance(strategy = InheritanceStrategy.SUBCLASS_TABLE)
public class Person {

	// Tell JDO to use this field ad a primary key
	@PrimaryKey
	// Automatic key generation when saving it in to the datastore
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private Key key;

	/*
	 * Tell JDO to store this field to datastore with its owner (Person)
	 * Otherwise it won't be saved !!!
	 */
	@Persistent
	private String name;

	@Persistent
	private String surName;

	public Person(String name, String surName) {
		super();
		this.name = name;
		this.surName = surName;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSureName() {
		return surName;
	}

	public void setSureName(String surName) {
		this.surName = surName;
	}

	public Key getKey() {
		return key;
	}

	@Override
	public String toString() {
		return " " + name + " " + surName;
		// + ", key(String) " + KeyFactory.keyToString(key)
		// + ", key(Key) " + key.toString();
	}
}
