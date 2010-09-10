package cz.cvut.felk.via.examples.datastore.client.widgets;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.ListBox;

import cz.cvut.felk.via.examples.datastore.client.RPCService;
import cz.cvut.felk.via.examples.datastore.client.RPCServiceAsync;
import cz.cvut.felk.via.examples.datastore.client.rpcobjects.SubjectRPC;

/**
 * Custom {@link ListBox} Widget with Map<String, SubjectRPC> of subjects inside
 * 
 * @author Floatrix
 * 
 */
public class TeachersListBox extends Composite {

	/**
	 * Create a remote service proxy to talk to the server-side RPCservice.
	 */
	private final RPCServiceAsync rpcService = GWT.create(RPCService.class);

	private final ListBox listBox = new ListBox();
	Map<String, SubjectRPC> subjects = new HashMap<String, SubjectRPC>();

	public TeachersListBox() {
		super();
		refreshContent();

		initWidget(listBox);
	}

	public SubjectRPC getSelectedItem() {
		String text = null;
		try {
			text = listBox.getValue(listBox.getSelectedIndex());
		} catch (Exception e) {
			return null;
		}
		return subjects.get(text);
	}

	private void addItems(List<SubjectRPC> subs) {
		subjects.clear();
		listBox.clear();
		for (SubjectRPC s : subs) {
			addItem(s);
		}
	}

	private void addItem(SubjectRPC sub) {
		subjects.put(subTotext(sub), sub);
		listBox.addItem(subTotext(sub));
	}

	private String subTotext(SubjectRPC sub) {
		String ret = sub.getCode() + " : " + sub.getName();
		if (ret.length() > 28) {
			ret = ret.substring(0, 28);
		}
		return ret;
	}

	public void refreshContent() {
		rpcService.getSubjectObjectList(new AsyncCallback<List<SubjectRPC>>() {

			@Override
			public void onSuccess(List<SubjectRPC> result) {
				addItems(result);

			}

			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub

			}
		});
	}

}
