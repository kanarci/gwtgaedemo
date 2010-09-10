package cz.cvut.felk.via.examples.datastore.client.widgets;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.TabLayoutPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.client.ui.FlexTable.FlexCellFormatter;

import cz.cvut.felk.via.examples.datastore.client.RPCService;
import cz.cvut.felk.via.examples.datastore.client.RPCServiceAsync;
import cz.cvut.felk.via.examples.datastore.shared.DatastoreUpdateEvent;
import cz.cvut.felk.via.examples.datastore.shared.EventBus;

public class CreateObjects extends Composite implements
		DatastoreUpdateEvent.Handler {

	/**
	 * Create a remote service proxy to talk to the server-side RPCservice.
	 */
	private final RPCServiceAsync rpcService = GWT.create(RPCService.class);

	private final int FLEX_TABLE_SIZE = 320;

	public Event dataSaved;

	final TabLayoutPanel tPanel = new TabLayoutPanel(20, Unit.PX);

	/**
	 * Custom widget, see {@link TeachersListBox} for details
	 */
	final TeachersListBox teacherListBox = new TeachersListBox();

	/**
	 * Custom widget, see {@link CustomPopUp} for details
	 */
	final CustomPopUp custPopUp = new CustomPopUp("");

	final TextBox subjectName = new TextBox();
	final TextBox subjectCode = new TextBox();

	final TextBox teacherName = new TextBox();
	final TextBox teacherSurName = new TextBox();
	final TextBox teacherTitle = new TextBox();

	final TextBox studentName = new TextBox();
	final TextBox studentSurName = new TextBox();
	final TextBox studentGrade = new TextBox();

	public CreateObjects() {
		super();

		tPanel.setSize(FLEX_TABLE_SIZE + "px", "300px");

		tPanel.add(createNewStudent(), "Student");
		tPanel.add(createNewSubject(), "Subject");
		tPanel.add(createNewTeacher(), "Teacher");

		custPopUp.setAnimationEnabled(true);
		custPopUp.setAutoHideEnabled(true);

		// register this widget as a handler of DatastoreUpdateevent
		EventBus.get().addHandler(DatastoreUpdateEvent.TYPE, this);

		initWidget(tPanel);
	}

	private Widget createNewStudent() {
		FlexTable flexTable = new FlexTable();
		flexTable.setWidget(0, 0, new HTML("<b> Create new student</b>"));
		flexTable.setWidget(1, 0, new HTML(" <b>Name</b>"));
		flexTable.setWidget(2, 0, new HTML(" <b>Surname</b>"));
		flexTable.setWidget(3, 0, new HTML(" <b>Grade</b>"));
		flexTable.setWidget(1, 1, studentName);
		flexTable.setWidget(2, 1, studentSurName);
		flexTable.setWidget(3, 1, studentGrade);

		flexTable.setCellPadding(5);
		flexTable.setWidth(FLEX_TABLE_SIZE - 20 + "px");

		FlexCellFormatter flexCellFormater = flexTable.getFlexCellFormatter();
		flexCellFormater.setColSpan(0, 0, 2);

		DockPanel dPanel = new DockPanel();
		dPanel.add(flexTable, DockPanel.NORTH);

		return dPanel;
	}

	private Widget createNewSubject() {
		FlexTable flexTable = new FlexTable();
		flexTable.setWidget(0, 0, new HTML("<b> Create new subject</b>"));
		flexTable.setWidget(1, 0, new HTML(" <b>Name</b>"));
		flexTable.setWidget(2, 0, new HTML(" <b>Code</b>"));
		flexTable.setWidget(1, 1, subjectName);
		flexTable.setWidget(2, 1, subjectCode);

		flexTable.setCellPadding(5);
		flexTable.setWidth(FLEX_TABLE_SIZE - 20 + "px");

		FlexCellFormatter flexCellFormater = flexTable.getFlexCellFormatter();
		flexCellFormater.setColSpan(0, 0, 2);

		DockPanel dPanel = new DockPanel();
		dPanel.add(flexTable, DockPanel.NORTH);

		return dPanel;
	}

	private Widget createNewTeacher() {
		FlexTable flexTable = new FlexTable();
		flexTable.setWidget(0, 0, new HTML("<b> Create new teacher</b>"));
		flexTable.setWidget(1, 0, new HTML(" <b>Name</b>"));
		flexTable.setWidget(2, 0, new HTML(" <b>Surname</b>"));
		flexTable.setWidget(3, 0, new HTML(" <b>Title</b>"));
		flexTable.setWidget(4, 0, new HTML(" <b>Subject</b>"));
		flexTable.setWidget(1, 1, teacherName);
		flexTable.setWidget(2, 1, teacherSurName);
		flexTable.setWidget(3, 1, teacherTitle);
		flexTable.setWidget(4, 1, teacherListBox);

		flexTable.setCellPadding(5);
		flexTable.setWidth(FLEX_TABLE_SIZE - 20 + "px");

		FlexCellFormatter flexCellFormater = flexTable.getFlexCellFormatter();
		flexCellFormater.setColSpan(0, 0, 2);

		DockPanel dPanel = new DockPanel();
		dPanel.add(flexTable, DockPanel.NORTH);

		return dPanel;
	}

	public void createObject() {

		switch (tPanel.getSelectedIndex()) {
		case 0:

			// tests
			if (studentName.getText().equals("")) {
				custPopUp.setMessage(" Please enter name");
				int left = studentName.getAbsoluteLeft() + 50;
				int top = studentName.getAbsoluteTop() + 10;
				custPopUp.setPopupPosition(left, top);
				custPopUp.show();
				return;
			}
			if (studentSurName.getText().equals("")) {
				custPopUp.setMessage(" Please enter surname");
				int left = studentSurName.getAbsoluteLeft() + 50;
				int top = studentSurName.getAbsoluteTop() + 10;
				custPopUp.setPopupPosition(left, top);
				custPopUp.show();
				return;
			}
			if (studentGrade.getText().equals("")) {
				custPopUp.setMessage(" Please enter grade");
				int left = studentGrade.getAbsoluteLeft() + 50;
				int top = studentGrade.getAbsoluteTop() + 10;
				custPopUp.setPopupPosition(left, top);
				custPopUp.show();
				return;
			}

			int grade = 0;
			try {
				grade = Integer.parseInt(studentGrade.getText());
			} catch (Exception e) {
				custPopUp.setMessage(" Grade must be integer");
				int left = studentGrade.getAbsoluteLeft() + 50;
				int top = studentGrade.getAbsoluteTop() + 10;
				custPopUp.setPopupPosition(left, top);
				custPopUp.show();
				return;
			}

			// RPC call to save the student
			rpcService.addStudent(studentName.getText(), studentSurName
					.getText(), grade, new AsyncCallback<Void>() {

				@Override
				public void onFailure(Throwable caught) {
					Window.alert(" Ups :]");
				}

				@Override
				public void onSuccess(Void result) {
					custPopUp.setMessage(" Student added");
					custPopUp.center();
					EventBus.get().fireEvent(new DatastoreUpdateEvent());

				}
			});
			break;
		case 1:

			// tests
			if (subjectName.getText().equals("")) {
				custPopUp.setMessage(" Please enter name");
				int left = subjectName.getAbsoluteLeft() + 50;
				int top = subjectName.getAbsoluteTop() + 10;
				custPopUp.setPopupPosition(left, top);
				custPopUp.show();
				return;
			}
			if (subjectCode.getText().equals("")) {
				custPopUp.setMessage(" Please enter code");
				int left = subjectCode.getAbsoluteLeft() + 50;
				int top = subjectCode.getAbsoluteTop() + 10;
				custPopUp.setPopupPosition(left, top);
				custPopUp.show();
				return;
			}

			// RPC call to save the subjects
			rpcService.addSubject(subjectName.getText(), subjectCode.getText(),
					new AsyncCallback<Void>() {

						@Override
						public void onFailure(Throwable caught) {
							Window.alert(" Ups :]");
						}

						@Override
						public void onSuccess(Void result) {
							custPopUp.setMessage(" Subject added");
							custPopUp.center();
							EventBus.get()
									.fireEvent(new DatastoreUpdateEvent());

						}
					});
			break;
		case 2:

			// tests
			if (teacherName.getText().equals("")) {
				custPopUp.setMessage(" Please enter name");
				int left = teacherName.getAbsoluteLeft() + 50;
				int top = teacherName.getAbsoluteTop() + 10;
				custPopUp.setPopupPosition(left, top);
				custPopUp.show();
				return;
			}
			if (teacherSurName.getText().equals("")) {
				custPopUp.setMessage(" Please enter surname");
				int left = teacherSurName.getAbsoluteLeft() + 50;
				int top = teacherSurName.getAbsoluteTop() + 10;
				custPopUp.setPopupPosition(left, top);
				custPopUp.show();
				return;
			}
			if (teacherTitle.getText().equals("")) {
				custPopUp.setMessage(" Please enter title");
				int left = teacherTitle.getAbsoluteLeft() + 50;
				int top = teacherTitle.getAbsoluteTop() + 10;
				custPopUp.setPopupPosition(left, top);
				custPopUp.show();
				return;
			}
			if (teacherListBox.getSelectedItem() == null) {
				custPopUp.setMessage(" Please select subject");
				int left = teacherListBox.getAbsoluteLeft() + 50;
				int top = teacherListBox.getAbsoluteTop() + 10;
				custPopUp.setPopupPosition(left, top);
				custPopUp.show();
				return;
			}

			// RPC call to save the teacher
			rpcService.addTeacher(teacherName.getText(), teacherSurName
					.getText(), teacherTitle.getText(), teacherListBox
					.getSelectedItem().getKey(), new AsyncCallback<Void>() {

				@Override
				public void onFailure(Throwable caught) {
					Window.alert(" Ups :]");
				}

				@Override
				public void onSuccess(Void result) {
					custPopUp.setMessage(" Teacher added");
					int left = tPanel.getAbsoluteLeft()
							+ tPanel.getOffsetWidth() - 100;
					int top = tPanel.getAbsoluteTop()
							+ tPanel.getOffsetHeight() - 30;
					custPopUp.setPopupPosition(left, top);
					custPopUp.center();
					EventBus.get().fireEvent(new DatastoreUpdateEvent());
				}
			});
			break;

		}
	}

	public void refreshContent() {
		teacherListBox.refreshContent();
	}

	@Override
	public void onDatastoreUpdate(DatastoreUpdateEvent p) {
		System.out.println(" Event handler - CreateObjects");
		this.refreshContent();

	}
}
