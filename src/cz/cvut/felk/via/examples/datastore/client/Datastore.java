package cz.cvut.felk.via.examples.datastore.client;

import java.util.Date;

import cz.cvut.felk.via.examples.datastore.client.events.DatastoreUpdateEvent;
import cz.cvut.felk.via.examples.datastore.client.events.EventBus;
import cz.cvut.felk.via.examples.datastore.client.widgets.CreateObjects;
import cz.cvut.felk.via.examples.datastore.client.widgets.CustomPopUp;
import cz.cvut.felk.via.examples.datastore.client.widgets.ViewObjects;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DecoratorPanel;
import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class Datastore implements EntryPoint, DatastoreUpdateEvent.Handler {

	/**
	 * Create a remote service proxy to talk to the server-side RPCservice.
	 */
	private final RPCServiceAsync rpcService = GWT.create(RPCService.class);

	final CustomPopUp custPopUp = new CustomPopUp("");

	final Timer timer = new Timer() {

		@Override
		public void run() {
			tryRefreshData();
		}
	};

	Date lastUpdate = new Date();

	final CreateObjects co = new CreateObjects();
	final ViewObjects vo = new ViewObjects();

	final Button refreshCO = new Button("<b>Refresh</b>");
	final Button refreshVO = new Button("<b>Refresh</b>");
	final Button addCO = new Button("<b>Add</b>");

	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {

		Grid grid = new Grid(1, 2);

		grid.setWidget(0, 0, createLeftSideWidget());
		grid.setWidget(0, 1, createRightSideWidget());

		grid.getCellFormatter().setHorizontalAlignment(0, 0,
				HasHorizontalAlignment.ALIGN_CENTER);
		grid.getCellFormatter().setHorizontalAlignment(0, 1,
				HasHorizontalAlignment.ALIGN_CENTER);

		DecoratorPanel dPanel = new DecoratorPanel();
		dPanel.add(grid);

		DockPanel dp = new DockPanel();
		dp.add(dPanel, DockPanel.CENTER);

		RootPanel.get("datastoreContainer").add(dp);

		refreshCO.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				co.refreshContent();
			}
		});

		addCO.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				co.createObject();
				// because of asynchronous call, there will be no data to
				// refresh :]
				// vo.refreshContent();
				// co.refreshContent();
			}
		});

		refreshVO.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				vo.refreshContent();
			}
		});

		custPopUp.setAnimationEnabled(true);
		custPopUp.setAutoHideEnabled(true);

		timer.scheduleRepeating(5000);

		// register this widget as a handler of DatastoreUpdateevent
		EventBus.get().addHandler(DatastoreUpdateEvent.TYPE, this);
		
		refreshContent();
	}

	private Widget createLeftSideWidget() {

		FlexTable flexTableCO = new FlexTable();
		HorizontalPanel hPanelCO = new HorizontalPanel();

		hPanelCO.add(refreshCO);
		hPanelCO.add(addCO);

		flexTableCO.setWidget(0, 0, co);
		flexTableCO.setWidget(1, 0, hPanelCO);

		flexTableCO.getCellFormatter().setHorizontalAlignment(1, 0,
				HasHorizontalAlignment.ALIGN_RIGHT);

		return flexTableCO;
	}

	private Widget createRightSideWidget() {

		FlexTable flexTableVO = new FlexTable();
		HorizontalPanel hPanelVO = new HorizontalPanel();

		hPanelVO.add(refreshVO);

		flexTableVO.setWidget(0, 0, vo);
		flexTableVO.setWidget(1, 0, hPanelVO);

		flexTableVO.getCellFormatter().setHorizontalAlignment(1, 0,
				HasHorizontalAlignment.ALIGN_RIGHT);

		return flexTableVO;
	}

	private void refreshContent() {
		System.out.println("Refreshing content");
		co.refreshContent();
		vo.refreshContent();
		lastUpdate = new Date();
	}

	private void tryRefreshData() {
		System.out.println("trying to refresh data");
		rpcService.dataChanged(lastUpdate, new AsyncCallback<Boolean>() {

			@Override
			public void onSuccess(Boolean result) {
				if (result) {
					custPopUp.setMessage(" Getting new data");
					custPopUp.show();
					refreshContent();
				} else {
					custPopUp.setMessage(" No new data");
					custPopUp.show();
				}

			}

			@Override
			public void onFailure(Throwable caught) {
			}
		});
	}

	@Override
	public void onDatastoreUpdate(DatastoreUpdateEvent p) {
		refreshContent();
	}
}
