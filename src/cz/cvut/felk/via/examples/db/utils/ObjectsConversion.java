package cz.cvut.felk.via.examples.db.utils;

import java.util.ArrayList;
import java.util.List;

import com.google.appengine.api.datastore.KeyFactory;

import cz.cvut.felk.via.examples.datastore.client.rpcobjects.SubjectRPC;
import cz.cvut.felk.via.examples.db.objects.Subject;

/**
 * ObjectsConversion class contains static methods, which can be used to convert
 * server-side objects from/to client-side objects.
 * 
 * @author Floatrix
 * 
 */
public class ObjectsConversion {

	/**
	 * Converts {@link Subject} to {@link SubjectRPC}
	 * 
	 * @param s
	 *            subject to be converted
	 * @return converted subject
	 */
	public static SubjectRPC subjectToRPC(Subject s) {

		return new SubjectRPC(KeyFactory.keyToString(s.getKey()), s.getName(),
				s.getCode());
	}

	/**
	 * Converts {@link Subject} to {@link SubjectRPC}
	 * 
	 * @param s
	 *            subject to be converted
	 * @return converted subject
	 */
	public static Subject subjectFromRPC(SubjectRPC s) {

		return new Subject(s.getName(), s.getCode());
	}

	/**
	 * Converts list of {@link Subject} to list of {@link SubjectRPC}
	 * 
	 * @param s
	 *            subject to be converted
	 * @return converted subjects
	 */
	public static List<SubjectRPC> subjectsToRPC(List<Subject> sub) {

		List<SubjectRPC> ret = new ArrayList<SubjectRPC>(sub.size());
		for (Subject s : sub) {
			ret.add(subjectToRPC(s));
		}
		return ret;
	}

}
