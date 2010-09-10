package cz.cvut.felk.via.examples.datastore.shared;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;

/**
 * Custom event which is fired when the datastore is updated by some widget
 * 
 * @author Floatrix
 *
 */
public class DatastoreUpdateEvent extends
		GwtEvent<DatastoreUpdateEvent.Handler> {

	/**
	 * Handler to the DatastoreUpdateEvent
	 * 
	 * @author Floatrix
	 *
	 */
	public interface Handler extends EventHandler {
		public void onDatastoreUpdate(DatastoreUpdateEvent p);
	}

	@Override
	protected void dispatch(DatastoreUpdateEvent.Handler handler) {
		handler.onDatastoreUpdate(this);
	}

	@Override
	public GwtEvent.Type<DatastoreUpdateEvent.Handler> getAssociatedType() {
		return TYPE;
	}

	public static final GwtEvent.Type<DatastoreUpdateEvent.Handler> TYPE = new GwtEvent.Type<DatastoreUpdateEvent.Handler>();

}
