package cz.cvut.felk.via.examples.datastore.client;

import cz.cvut.felk.via.examples.datastore.client.widgets.CreateObjects;
import cz.cvut.felk.via.examples.datastore.client.widgets.ViewObjects;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
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
public class Datastore implements EntryPoint {

	final CreateObjects co = new CreateObjects();
	final ViewObjects vo = new ViewObjects();

	final Button refreshCO = new Button("<b>Refresh</b>");
	final Button refreshVO = new Button("<b>Refresh</b>");
	final Button addCO = new Button("<b>Add</b>");

	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {

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
}
