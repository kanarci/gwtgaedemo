package cz.cvut.felk.via.examples.datastore.client;

import java.util.List;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import cz.cvut.felk.via.examples.datastore.client.rpcobjects.SubjectRPC;

/**
 * The client side stub for the RPC service.
 */
@RemoteServiceRelativePath("RPC")
public interface RPCService extends RemoteService {

	List<String> getTeacherList();

	List<String> getStudentList();

	List<String> getSubjectList();

	void addSubject(String text, String code);

	void addTeacher(String name, String surname, String title, String subjectkey);

	List<SubjectRPC> getSubjectObjectList();

	void addStudent(String name, String surname, int grade);
}
