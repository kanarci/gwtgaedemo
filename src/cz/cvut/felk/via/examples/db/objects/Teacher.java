package cz.cvut.felk.via.examples.db.objects;

import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;

import com.google.appengine.api.datastore.Key;

// enables detaching this object in the future
@PersistenceCapable(detachable = "true")
public class Teacher extends Person {

	@Persistent
	private String title;

	@Persistent
	private Key subject;

	public Teacher(String name, String sureName, String title, Key subject) {
		super(name, sureName);
		this.title = title;
		this.subject = subject;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * @return the subject key
	 */
	public Key getSubject() {
		return subject;
	}

	/**
	 * @param subject
	 *            the key of a subject
	 */
	public void setSubject(Key subject) {
		this.subject = subject;
	}

	@Override
	public String toString() {
		return this.title + " " + super.toString();
	}

}
