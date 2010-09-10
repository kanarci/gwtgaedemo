package cz.cvut.felk.via.examples.db.objects;

import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;

//enables detaching this object in the future
@PersistenceCapable(detachable = "true")
public class Student extends Person {

	/*
	 * Because this class extends Person class, there is no need to have
	 * "Key key"
	 */

	@Persistent
	private int grade;

	public Student(String name, String sureName, int grade) {
		super(name, sureName);
		this.grade = grade;
	}

	public int getGrade() {
		return grade;
	}

	public void setGrade(int grade) {
		this.grade = grade;
	}

	@Override
	public String toString() {
		return super.toString() + ", grade : " + this.grade;
	}

}
