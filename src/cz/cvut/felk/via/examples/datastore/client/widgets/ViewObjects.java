package cz.cvut.felk.via.examples.datastore.client.widgets;

import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.StackLayoutPanel;
import com.google.gwt.user.client.ui.Tree;
import com.google.gwt.user.client.ui.Widget;

import cz.cvut.felk.via.examples.datastore.client.RPCService;
import cz.cvut.felk.via.examples.datastore.client.RPCServiceAsync;

public class ViewObjects extends Composite {

	/**
	 * Create a remote service proxy to talk to the server-side RPCservice.
	 */
	private final RPCServiceAsync rpcService = GWT.create(RPCService.class);

	private final int MAX_STRING_LENGTH = 58;

	final Tree studentTree = new Tree();
	final Tree subjectTree = new Tree();
	final Tree teacherTree = new Tree();

	public ViewObjects() {
		super();

		StackLayoutPanel stackPanel = new StackLayoutPanel(Unit.PX);
		stackPanel.setSize("400px", "300px");

		stackPanel.add(createStudentList(), "Students", 25);
		stackPanel.add(createSubjectsList(), "Subjects", 25);
		stackPanel.add(createTeachersList(), "Teachers", 25);
		
		initWidget(stackPanel);
		
	}

	private Widget createSubjectsList() {
		ScrollPanel sPanel = new ScrollPanel();
		sPanel.add(subjectTree);
		return sPanel;
	}

	private Widget createStudentList() {
		ScrollPanel sPanel = new ScrollPanel();
		sPanel.add(studentTree);
		return sPanel;
	}

	private Widget createTeachersList() {
		ScrollPanel sPanel = new ScrollPanel();
		sPanel.add(teacherTree);
		return sPanel;
	}

	public void refreshContent() {
		refreshStudentTree();
		refreshSubjectTree();
		refreshTeacherTree();
	}

	void refreshStudentTree() {

		rpcService.getStudentList(new AsyncCallback<List<String>>() {

			@Override
			public void onFailure(Throwable caught) {
				// TODO :
			}

			@Override
			public void onSuccess(List<String> result) {
				studentTree.clear();

				for (String s : result) {
					if (s.length() > MAX_STRING_LENGTH) {
						s = s.substring(0, MAX_STRING_LENGTH);
					}
					studentTree.addItem(s);
				}

			}
		});
	}

	void refreshSubjectTree() {
		rpcService.getSubjectList(new AsyncCallback<List<String>>() {

			@Override
			public void onFailure(Throwable caught) {
				// TODO :

			}

			@Override
			public void onSuccess(List<String> result) {
				subjectTree.clear();

				for (String s : result) {
					if (s.length() > MAX_STRING_LENGTH) {
						s = s.substring(0, MAX_STRING_LENGTH);
					}
					subjectTree.addItem(s);
				}

			}
		});
	}

	void refreshTeacherTree() {

		rpcService.getTeacherList(new AsyncCallback<List<String>>() {

			@Override
			public void onFailure(Throwable caught) {
				// TODO :

			}

			@Override
			public void onSuccess(List<String> result) {
				teacherTree.clear();

				for (String s : result) {
					if (s.length() > MAX_STRING_LENGTH) {
						s = s.substring(0, MAX_STRING_LENGTH).concat("...");
					}
					teacherTree.addItem(s);
				}

			}
		});
	}


}
