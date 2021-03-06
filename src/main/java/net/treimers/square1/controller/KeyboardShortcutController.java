package net.treimers.square1.controller;

import java.util.ArrayList;
import java.util.List;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

/**
 * The controller for the keyboard shortcut help screen.
 * 
 * Iterates over all Hotkey enums and all menu entries
 * in order to create the shortcut help screen. The grid pane
 * from the view is filled with the shortcuts and
 * the corresponding text.
 */
public class KeyboardShortcutController {
	/** The short cut header text. */
	private static final String SHORTCUT = "Shortcut";
	/** The description header text. */
	private static final String DESCRIPTION = "Description";
	/** The grid pane with the short cuts. */
	@FXML private GridPane gridPane;
	/** The menu bar with all menu and accelerator keys. */
	private MenuBar menuBar;

	/**
	 * Sets the menu bar in order to scan all accelerator keys.
	 *  
	 * @param menuBar the menu bar with accelerator keys.
	 */
	public void setMenuBar(MenuBar menuBar) {
		this.menuBar = menuBar;
	}

	/**
	 * Creates content on dialog showing event.
	 */
	public void handleShowing() {
		// set column width 15%, 35%, 15% and 35%
		ColumnConstraints c1 = new ColumnConstraints();
		c1.setPercentWidth(10);
		ColumnConstraints c2 = new ColumnConstraints();
		c2.setPercentWidth(20);
		ColumnConstraints c3 = new ColumnConstraints();
		c3.setPercentWidth(10);
		ColumnConstraints c4 = new ColumnConstraints();
		c4.setPercentWidth(20);
		ColumnConstraints c5 = new ColumnConstraints();
		c5.setPercentWidth(10);
		ColumnConstraints c6 = new ColumnConstraints();
		c6.setPercentWidth(20);
		ObservableList<ColumnConstraints> columnConstraints = gridPane.getColumnConstraints();
		columnConstraints.clear();
		columnConstraints.addAll(c1, c2, c3, c4, c5, c6);
		// get grid pane children
		ObservableList<Node> children = gridPane.getChildren();
		children.clear();
		int row = 0;
		// add title
		Label windowLabel = new Label("Shortcuts");
		windowLabel.setFont(Font.font(20));
		children.add(windowLabel);
		GridPane.setRowIndex(windowLabel, row);
		GridPane.setColumnIndex(windowLabel, 0);
		GridPane.setColumnSpan(windowLabel, 6);
		GridPane.setMargin(windowLabel, new Insets(10));
		GridPane.setHalignment(windowLabel, HPos.CENTER);
		gridPane.getRowConstraints().add(new RowConstraints(30));
		row++;
		// add heading 1
		Font font = Font.font("System", FontWeight.BOLD, 13);
		Label shortcut = new Label(SHORTCUT);
		shortcut.setFont(font);
		children.add(shortcut);
		GridPane.setRowIndex(shortcut, row);
		GridPane.setColumnIndex(shortcut, 0);
		GridPane.setMargin(shortcut, new Insets(10));
		GridPane.setHgrow(shortcut, Priority.SOMETIMES);
		Label description = new Label(DESCRIPTION);
		description.setFont(font);
		children.add(description);
		GridPane.setRowIndex(description, row);
		GridPane.setColumnIndex(description, 1);
		GridPane.setMargin(description, new Insets(10));
		GridPane.setHgrow(description, Priority.ALWAYS);
		gridPane.getRowConstraints().add(new RowConstraints(23));
		// add heading 2
		shortcut = new Label(SHORTCUT);
		shortcut.setFont(font);
		children.add(shortcut);
		GridPane.setRowIndex(shortcut, row);
		GridPane.setColumnIndex(shortcut, 2);
		GridPane.setMargin(shortcut, new Insets(10));
		GridPane.setHgrow(shortcut, Priority.SOMETIMES);
		description = new Label(DESCRIPTION);
		description.setFont(font);
		children.add(description);
		GridPane.setRowIndex(description, row);
		GridPane.setColumnIndex(description, 3);
		GridPane.setMargin(description, new Insets(10));
		GridPane.setHgrow(description, Priority.ALWAYS);
		// add heading 2
		shortcut = new Label(SHORTCUT);
		shortcut.setFont(font);
		children.add(shortcut);
		GridPane.setRowIndex(shortcut, row);
		GridPane.setColumnIndex(shortcut, 4);
		GridPane.setMargin(shortcut, new Insets(10));
		GridPane.setHgrow(shortcut, Priority.SOMETIMES);
		description = new Label(DESCRIPTION);
		description.setFont(font);
		children.add(description);
		GridPane.setRowIndex(description, row);
		GridPane.setColumnIndex(description, 5);
		GridPane.setMargin(description, new Insets(10));
		GridPane.setHgrow(description, Priority.ALWAYS);
		row++;
		// add all menu shortcuts
		ObservableList<Menu> menus = menuBar.getMenus();
		List<MenuItem> allMenuItems = new ArrayList<>();
		for (Menu menu : menus)
			addMenuItems(allMenuItems, menu);
		int column = 0;
		for (int i = 0; i < allMenuItems.size(); i++) {
			MenuItem menuItem = allMenuItems.get(i);
			KeyCombination accelerator = menuItem.getAccelerator();
			// ignore all disabled menu items
			if (menuItem.isDisable() || accelerator == null)
				continue;
			String text = menuItem.getText();
			String keyString = accelerator.getDisplayText();
			Label keyLabel = new Label(keyString);
			children.add(keyLabel);
			GridPane.setRowIndex(keyLabel, row);
			GridPane.setColumnIndex(keyLabel, 2 * column);
			GridPane.setMargin(keyLabel, new Insets(10));
			Label textLabel = new Label(text);
			children.add(textLabel);
			GridPane.setRowIndex(textLabel, row);
			GridPane.setColumnIndex(textLabel, 2 * column + 1);
			GridPane.setMargin(textLabel, new Insets(10));
			column++;
			if (i % 3 == 2) {
				column = 0;
				row++;
			}
		}
	}

	/**
	 * Adds all menu items from all visible menus that have an accelerator to a menu item list.
	 * 
	 * @param allMenuItems menu list.
	 * @param menu start menu.
	 */
	private void addMenuItems(List<MenuItem> allMenuItems, Menu menu) {
		if (!menu.isVisible())
			return;
		ObservableList<MenuItem> items = menu.getItems();
		for (int j = 0; j < items.size(); j++) {
			MenuItem menuItem = items.get(j);
			if (menuItem instanceof Menu) {
				Menu subMenu = (Menu) menuItem;
				addMenuItems(allMenuItems, subMenu);
			} else {
				KeyCombination accelerator = menuItem.getAccelerator();
				// ignore all disabled menu items
				if (menuItem.isDisable() || accelerator == null)
					continue;
				allMenuItems.add(menuItem);
			}
		}
	}
}
