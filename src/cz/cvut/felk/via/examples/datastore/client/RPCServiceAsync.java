package cz.cvut.felk.via.examples.datastore.client;

import java.util.Date;
import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;

import cz.cvut.felk.via.examples.datastore.client.rpcobjects.SubjectRPC;

/**
 * The async counterpart of <code>RPCService</code>.
 */
public interface RPCServiceAsync {

	void getTeacherList(AsyncCallback<List<String>> asyncCallback);

	void getStudentList(AsyncCallback<List<String>> asyncCallback);

	void getSubjectList(AsyncCallback<List<String>> asyncCallback);

	void addSubject(String name, String code, AsyncCallback<Void> asyncCallback);

	void addTeacher(String name, String surname, String title,
			String subjectkey, AsyncCallback<Void> asyncCallback);

	void getSubjectObjectList(AsyncCallback<List<SubjectRPC>> asyncCallback);

	void addStudent(String name, String surname, int grade,
			AsyncCallback<Void> asyncCallback);

	void dataChanged(Date date, AsyncCallback<Boolean> callback);


}
