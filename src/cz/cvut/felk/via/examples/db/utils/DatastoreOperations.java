package cz.cvut.felk.via.examples.db.utils;

import java.util.ArrayList;
import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;

import cz.cvut.felk.via.examples.datastore.client.events.DatastoreUpdateEvent;
import cz.cvut.felk.via.examples.datastore.client.events.EventBus;
import cz.cvut.felk.via.examples.datastore.client.rpcobjects.SubjectRPC;
import cz.cvut.felk.via.examples.db.objects.Student;
import cz.cvut.felk.via.examples.db.objects.Subject;
import cz.cvut.felk.via.examples.db.objects.Teacher;

/**
 * DatastoreOperation class encapsules all the datastore operations
 * 
 * @author Floatrix
 * 
 */
public class DatastoreOperations {

	// **************** Persisting objects ****************

	/**
	 * Save current object to datastore
	 * 
	 * @param o
	 *            {@link Class} of saved object
	 */
	private static <T> void makeObjectPersistent(T o) {
		PersistenceManager pm = PMF.get().getPersistenceManager();

		try {
			pm.makePersistent(o);
			System.out.println("Object " + o.getClass().getSimpleName()
					+ " saved in datastore " + o.toString());
		} finally {
			pm.close();
		}
	}

	// **************** Deleting objects ******************

	/**
	 * Delete all objects of a given {@link Class} from the datastore
	 * 
	 * @param o
	 *            {@link Class} of deleted objects
	 */
	@SuppressWarnings("unused")
	private static void deleteAllPersistentObjects(Class<?> o) {
		PersistenceManager pm = PMF.get().getPersistenceManager();

		try {
			Query q = pm.newQuery(o);
			q.deletePersistentAll();
//			System.out.println("All " + o.getClass().getSimpleName()
//					+ " objects wiped out from datastore");
		} finally {
			pm.close();
		}
	}

	// **************** Retrieving objects ****************

	/**
	 * Return a list with all objects of a given {@link Class} from the
	 * datastore Returned obects must have {@code @PersistenceCapable(detachable
	 * = "true")} in their annotation
	 * 
	 * @param o
	 *            {@link Class} of returned objects
	 */
	@SuppressWarnings( { "unchecked" })
	private static <T> List<T> getAllObjects(Class<T> o) {

		List<T> ret;

		PersistenceManager pm = PMF.get().getPersistenceManager();

		pm.getFetchPlan().setMaxFetchDepth(4);

		try {
			Query q = pm.newQuery(o);
			List<T> qret = (List<T>) q.execute();
			ret = new ArrayList<T>(pm.detachCopyAll(qret));
		} finally {
			pm.close();
		}
		return ret;
	}

	/**
	 * Return na Object of a given {@link Class} with a {@code key} from the
	 * datastore
	 */
	private static <T> T getObjectForKey(Class<T> o, Key key) {

		if (key == null) {
//			System.out
//					.println("getObjectForKey(class<T> o,key) - key was null");
			return null;
		}

		PersistenceManager pm = PMF.get().getPersistenceManager();

		pm.getFetchPlan().setMaxFetchDepth(4);

		T obj;
		try {
			obj = (T) pm.getObjectById(o, key);
			obj = pm.detachCopy(obj);
		} finally {
			pm.close();
		}

		return obj;
	}

	// **************** Datastore operations **************

	/**
	 * Returns a list with all {@link Teacher} objects from the datastore with
	 * {@code toString()} casted on them
	 */
	public static List<String> getTeacherList() {
		List<Teacher> teachers = getAllObjects(Teacher.class);
		List<String> ret = new ArrayList<String>();
		for (Teacher t : teachers) {
			String subject = getObjectForKey(Subject.class, t.getSubject())
					.toString();
			if (subject == null) {
				ret.add(t.toString());
			} else {
				ret.add(t.toString() + " - " + subject);
			}
		}
		return ret;
	}

	/**
	 * Returns a list with all {@link Student} objects from the datastore with
	 * {@code toString()} casted on them
	 */
	public static List<String> getStudentList() {
		List<Student> students = getAllObjects(Student.class);
		List<String> ret = new ArrayList<String>();
		for (Student s : students) {
			ret.add(s.toString());
		}
		return ret;
	}

	/**
	 * Returns a list with all {@link Subject} objects from the datastore with
	 * {@code toString()} casted on them
	 */
	public static List<String> getSubjectList() {
		List<Subject> subjects = getAllObjects(Subject.class);
		List<String> ret = new ArrayList<String>();
		for (Subject s : subjects) {
			ret.add(s.toString());
		}
		return ret;
	}

	/**
	 * Returns a list with all {@link Subject} objects from the datastore
	 */
	public static List<SubjectRPC> getSubjectObjectList() {
		List<Subject> subjects = getAllObjects(Subject.class);
		return ObjectsConversion.subjectsToRPC(subjects);
	}

	/**
	 * Add new {@link Subject} object to the datastore
	 * 
	 * @param name
	 * @param code
	 */
	public static void addSubject(String name, String code) {
		makeObjectPersistent(new Subject(name, code));
		EventBus.get().fireEvent(new DatastoreUpdateEvent());
	}

	/**
	 * Add new {@link Teacher} object to the datastore
	 * 
	 * @param name
	 * @param surname
	 * @param title
	 * @param subjectkey
	 *            {@link Key} of the subject, the teacher teaches
	 */
	public static void addTeacher(String name, String surname, String title,
			String subjectkey) {
		makeObjectPersistent(new Teacher(name, surname, title, KeyFactory
				.stringToKey(subjectkey)));
		EventBus.get().fireEvent(new DatastoreUpdateEvent());
	}

	/**
	 * Add new {@link Student} object to the datastore
	 * 
	 * @param name
	 * @param surname
	 * @param grade
	 */
	public static void addStudent(String name, String surname, int grade) {
		makeObjectPersistent(new Student(name, surname, grade));
//		System.out.println(" Handler count " + EventBus.get().getHandlerCount(DatastoreUpdateEvent.TYPE));
		EventBus.get().fireEvent(new DatastoreUpdateEvent());
	}

	// ********* Unused methods, check it out :] **********

	// Example call :
	// Key key = Key makeObjectPersistentRetKey(Teacher.class);

	// private <T> Key makeObjectPersistentRetKey(T o) {
	//
	// Key ret = null;
	// PersistenceManager pm = PMF.get().getPersistenceManager();
	//
	// try {
	// pm.makePersistent(o);
	// System.out.println("Object " + o.getClass().getSimpleName()
	// + " saved in datastore");
	// // System.out.println("ObjectId is " + pm.getObjectId(o));
	// ObjectIdentity oi = (ObjectIdentity) pm.getObjectId(o);
	// ret = (Key) oi.getKey();
	// } finally {
	// pm.close();
	// }
	// return ret;
	// }

	// Example call :
	// deletePersistentObject(Teacher.class, currentTeacher.getKey());

	// @SuppressWarnings("unchecked")
	// private <T> void deletePersistentObject(T o, Key key) {
	// System.out.println("Deleting object " + o.getClass().getSimpleName()
	// + " with key " + KeyFactory.keyToString(key));
	// PersistenceManager pm = PMF.get().getPersistenceManager();
	//
	// try {
	// T obj = (T) pm.getObjectById(o.getClass(), key);
	// pm.deletePersistent(obj);
	// System.out.println(" Object wiped out from datastore");
	// } catch (JDOUserException ex) {
	// System.out.println(" Object was not deleted from datastore");
	// } finally {
	// pm.close();
	// }
	//
	// }

	// Example call :
	// List<Subject> orderedList = getAllObjectsOrderedBy(Subject.class,
	// "name code");
	// Return List<T> with objects sorted at first by name , then by code

	// @SuppressWarnings( { "unchecked" })
	// private <T> List<T> getAllObjectsOrderedBy(Class<T> o, String orderValue)
	// {
	//
	// List<T> ret;
	//
	// StringTokenizer st = new StringTokenizer(orderValue);
	// String orderString = "";
	// while (st.hasMoreElements()) {
	// orderString = orderString.concat(st.nextElement().toString()
	// + " asc, ");
	// }
	// orderString = orderString.substring(0, orderString.length() - 2);
	// System.out.println("order string : " + orderString);
	//
	// PersistenceManager pm = PMF.get().getPersistenceManager();
	//
	// pm.getFetchPlan().setMaxFetchDepth(4);
	//
	// try {
	// Query q = pm.newQuery(o);
	// q.setOrdering(orderString);
	// // q.setOrdering(sortValue+" desc");
	// List<T> qret = (List<T>) q.execute();
	// ret = new ArrayList<T>(pm.detachCopyAll(qret));
	// } finally {
	// pm.close();
	// }
	// return ret;
	// }

	// Example call :
	// Key teachersKey = currentSubject.getKey();
	// Teacher currentTeacher = getObjectForKey(Teacher.class, teachersKey);

	// @SuppressWarnings("unchecked")
	// private <T> List<T> getObjectsForKey(Class<T> o, List<Key>
	// objectsInCategory) {
	//
	// List<T> ret = new ArrayList<T>();
	//
	// PersistenceManager pm = PMF.get().getPersistenceManager();
	//
	// pm.getFetchPlan().setMaxFetchDepth(4);
	//
	// try {
	// Query query = pm.newQuery(o, ":p.contains(key)");
	// ret = (List<T>) query.execute(objectsInCategory);
	//
	// ret = new ArrayList<T>(pm.detachCopyAll(ret));
	// } finally {
	// pm.close();
	// }
	//
	// return ret;
	// }

}
