package com.example.application.views.helloworld;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.router.Menu;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.vaadin.lineawesome.LineAwesomeIconUrl;

import java.util.UUID;
import java.util.stream.Stream;

@PageTitle("Hello World") @Route("") @Menu(order = 0, icon = LineAwesomeIconUrl.GLOBE_SOLID) public class HelloWorldView
		extends VerticalLayout {

	private Grid<DataElement> grid;
/**
	 * Enter like 500 to the 'column count' field then press 'Generate'
	 * Then enter like 501 to the 'column count' field and press 'Generate' again
	 */
	public HelloWorldView() {
		setSizeFull();
		grid = createGrid();
		final IntegerField countField = new IntegerField("column count");
		countField.setValue(500);
		updateGrid(grid, 500);

		Checkbox checkbox = new Checkbox("Re-create grid");

		final Button generateColumns = new Button("Generate Columns");

		generateColumns.addClickListener(e -> {
			if (checkbox.getValue()) {
				remove(grid);
				grid = createGrid();
				add(grid);
			}
			updateGrid(grid, countField.getValue());
		});
		setMargin(true);

		HorizontalLayout panel = new HorizontalLayout(countField, generateColumns, checkbox);
		panel.setVerticalComponentAlignment(Alignment.BASELINE, countField, generateColumns, checkbox);
		add(panel, grid);
	}

	private Grid<DataElement> createGrid() {
		final Grid<DataElement> grid = new Grid<>();
		grid.setItems(new ListDataProvider<>(DataElement.generate(50).toList()));
		grid.setSizeFull();
		grid.addComponentColumn(dataElement -> new Span(dataElement.value)).setFrozen(true);
		return grid;
	}

	private void updateGrid(Grid<DataElement> grid, Integer columnCount) {
		grid.removeAllColumns();
		grid.addComponentColumn(dataElement -> new Span(dataElement.value)).setFrozen(true);
		int count = (columnCount > 0 ? columnCount : 1);
		ColumnSpec.generate(count).forEach(
				columnSpec -> grid.addComponentColumn(pDataElement -> new Span(columnSpec.getPropertyName()))
						.setHeader(columnSpec.getPropertyName()));
	}

	public static final class ColumnSpec {
		static Stream<ColumnSpec> generate(int count) {
			return Stream.generate(ColumnSpec::new).limit(count);
		}

		private String value = UUID.randomUUID().toString();

		public String getPropertyName() {
			return value;
		}
	}


	public static final class DataElement {
		private String value = UUID.randomUUID().toString();

		static Stream<DataElement> generate(int count) {
			return Stream.generate(DataElement::new).limit(count);
		}

		public String getValue() {
			return value;
		}
	}
}
