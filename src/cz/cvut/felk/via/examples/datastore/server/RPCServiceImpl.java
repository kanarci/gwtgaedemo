package cz.cvut.felk.via.examples.datastore.server;

import java.util.Date;
import java.util.List;

import cz.cvut.felk.via.examples.datastore.client.RPCService;
import cz.cvut.felk.via.examples.datastore.client.rpcobjects.SubjectRPC;
import cz.cvut.felk.via.examples.db.utils.DatastoreOperations;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

/**
 * The server side implementation of the RPC service.
 */
@SuppressWarnings("serial")
public class RPCServiceImpl extends RemoteServiceServlet implements RPCService {

	@Override
	public List<String> getTeacherList() {
		return DatastoreOperations.getTeacherList();
	}

	@Override
	public List<String> getStudentList() {
		return DatastoreOperations.getStudentList();
	}

	@Override
	public List<String> getSubjectList() {
		return DatastoreOperations.getSubjectList();
	}

	@Override
	public List<SubjectRPC> getSubjectObjectList() {
		return DatastoreOperations.getSubjectObjectList();
	}

	@Override
	public void addSubject(String name, String code) {
		DatastoreOperations.addSubject(name, code);

	}

	@Override
	public void addTeacher(String name, String surname, String title,
			String subjectkey) {
		DatastoreOperations.addTeacher(name, surname, title, subjectkey);

	}

	@Override
	public void addStudent(String name, String surname, int grade) {
		DatastoreOperations.addStudent(name, surname, grade);

	}

	@Override
	public Boolean dataChanged(Date date) {
		System.out.println("Server date is : " + DWDF.get().getLastUpdateTime() + " client date is : " + date);
		return DWDF.get().getLastUpdateTime().after(date);
	}


}
