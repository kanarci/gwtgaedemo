package cz.cvut.felk.via.examples.datastore.client.widgets;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.DecoratedPopupPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Widget;

/**
 * Custom widget inherited from {@link DecoratedPopupPanel}. This widget uses
 * {@link UiBinder} to change text color and font.
 * 
 * @author Floatrix
 * 
 */
public class CustomPopUp extends DecoratedPopupPanel {

	private static CustomPopUpUiBinder uiBinder = GWT
			.create(CustomPopUpUiBinder.class);

	interface CustomPopUpUiBinder extends UiBinder<Widget, CustomPopUp> {
	}

	@UiField
	HTML message;

	public CustomPopUp(String message) {
		setWidget(uiBinder.createAndBindUi(this));
		this.message.setHTML(message);
	}

	public void setMessage(String message) {
		this.message.setHTML(message);
	}

}
