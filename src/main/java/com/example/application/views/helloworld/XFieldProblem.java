package com.example.application.views.helloworld;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.radiobutton.RadioButtonGroup;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.Menu;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.vaadin.lineawesome.LineAwesomeIconUrl;

@PageTitle("X Field Problem") @Route("xfield") @Menu(order = 0, icon = LineAwesomeIconUrl.GLOBE_SOLID)
public class XFieldProblem
		extends VerticalLayout {

	public XFieldProblem() {
		setSizeFull();
		NotifyingIntegerField textField = new NotifyingIntegerField();

		Button b = new Button("Try in dialog", event -> {
			Dialog d = new Dialog();
			NotifyingIntegerField notifyingIntegerField = new NotifyingIntegerField();
			d.add(notifyingIntegerField, notifyingIntegerField.getChangeModeSwitch());
			d.setCloseOnOutsideClick(false);
			d.open();
		});
		add(textField, b, textField.getChangeModeSwitch());
	}

	private class NotifyingIntegerField
			extends TextField {
		private final RadioButtonGroup<ValueChangeMode> changeModeSwitch;
		NotifyingIntegerField() {
			super("Watch notifications");
			setClearButtonVisible(true);
			changeModeSwitch = new RadioButtonGroup<>(
					event -> setValueChangeMode(event.getValue()));
			changeModeSwitch.setItems(ValueChangeMode.values());
			changeModeSwitch.setValue(getValueChangeMode());
		}
		private RadioButtonGroup<ValueChangeMode> getChangeModeSwitch() {
			return changeModeSwitch;
		}

		@Override protected void setModelValue(String newModelValue, boolean fromClient) {
			super.setModelValue(newModelValue, fromClient);
			if (fromClient) {
				Notification.show("New model value: " + newModelValue);
			}
		}
	}
}
