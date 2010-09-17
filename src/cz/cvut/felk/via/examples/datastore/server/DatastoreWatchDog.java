package cz.cvut.felk.via.examples.datastore.server;

import java.util.Date;

import cz.cvut.felk.via.examples.datastore.client.events.DatastoreUpdateEvent;
import cz.cvut.felk.via.examples.datastore.client.events.EventBus;

public class DatastoreWatchDog implements DatastoreUpdateEvent.Handler {

	private Date lastUpdateTime;

	private Boolean initialized = false;

	/**
	 * 
	 */
	DatastoreWatchDog() {
		super();
		if (!initialized) {
			// register this class as a handler of DatastoreUpdateEvent - Server-side
			EventBus.get().addHandler(DatastoreUpdateEvent.TYPE, this);
			initialized = true;
		}
	}

	@Override
	public void onDatastoreUpdate(DatastoreUpdateEvent p) {
		this.lastUpdateTime = new Date();
	}

	protected Date getLastUpdateTime() {
		if(lastUpdateTime == null){
			lastUpdateTime = new Date();
		}
		return lastUpdateTime;
	}

	protected void setLastUpdateTime(Date lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}

}
