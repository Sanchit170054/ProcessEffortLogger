package actions;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import mainline.UserInterface.TabNames;

/*******
* <p> Title: ListItem Class. </p>
* 
* <p> Description: A JavaFX application: This controller class established the user interface for
* list item objects.  This Tab provides the user with the capability of establish a list of named
* item kinds in a specified order with a description for each.  When an effort logger needs to
* log effort against a new item, this list is used to help classify the new item into one of
* the kinds of items in this list.  By providing a list of kinds, we make it possible to 
* distinguish between different kinds of items and thereby provide a way of grouping similar 
* items together so effort estimation can be more accurate.
* 
* This class is used directly by Artifacts, Plans, Interruptions, and Defects tabs.  This class is
* inherited by the Life Cycles, Steps, Tasks, Conditions, and Effort Categories tabs and additional
* attributes and user interface elements are added to this base to support those tab specific needs
* of those tabs.</p>
* 
* <p> Copyright: Lynn Robert Carter © 2019-05-11 </p>
* 
* @author Lynn Robert Carter
* 
* @version 1.01	2019-06-05 Baseline
* 
*/
public class ListItem {
	
	/**********************************************************************************************

	Class Attributes
	
	**********************************************************************************************/
	
	//---------------------------------------------------------------------------------------------
	// These attributes enable us to hide the details of the tab height and the height and width of
	// of the window decorations for the code that implements this user interface
	private int xOffset = 0;
	private int yOffset = 0;
	
	//---------------------------------------------------------------------------------------------
	// The following are the GUI objects that make up the ListItem user interface	
	private String name;
	private String capName;
	private String capNames;
	private Label theTitle;
	private Label theName;
	private String theItemName = "";
	private Label theDescription;
	private String theItemDescription = "";
	private TableView <ListItemEntry> theListItemList = new TableView <ListItemEntry>();
	private TableColumn <ListItemEntry, Integer> seqColumn = new TableColumn <>("Seq");
	private TableColumn <ListItemEntry, String> nameColumn;
	public TextField theNameField = new TextField();
	private TextArea theDescriptionArea = new TextArea();
	private Button btnEdit = new Button("Edit \u21e8");
	private Button btnSave = new Button("\u21e6 Save");
	private Button btnAddBottom = new Button("\u21b2 Add");
	private Button btnAddUp = new Button("\u2196 Add");
	private Button btnAddDn = new Button("\u2199 Add");
	private Button btnMoveUp = new Button("Move Up");
	private Button btnMoveDn = new Button("Move Dn");
	private Button btnDelete = new Button("Delete");
	private Button btnClear = new Button("Clear the above Name and Description");
	
	
	// Change and saved lines
	private Label changeMessage = new Label("Content has changed and has not been saved!");
	private Line line = new Line(); 
	private Line line2 = new Line(); 
	private Line line3 = new Line(); 

	
	//---------------------------------------------------------------------------------------------
	// The following is used to simplify those cases where there are tab specific data
	protected int tabSpecificIndex = -1;
	
	/**********
	 * This ListItem constructor places each of the GUI elements into a group that can be shown when
	 * the tab is activated and hidden when leaving the tab.
	 * 
	 * @param g		This is the group that is used for the specific Tab
	 * @param x		This is the x axis offset and the
	 * @param y		y axis offset to be used to hide the details of the context from this code
	 */
	public ListItem(Group g, int x, int y, TabNames t) {
		// Save the offset values
		xOffset = x;
		yOffset = y;
		
		// Save the item name
		switch (t) {
		case LIFECYCLES: 
			name = "life cycle";
			capName = "Life Cycle";
			capNames = "Life Cycles";
			setupChangeMessage(340, 520, 970);
			break;
		case STEPS:
			name = "step";
			capName = "Step";
			capNames = "Steps";
			setupChangeMessage(340, 520, 970);
			break;
		case TASKS:
			name = "task";
			capName = "Task";
			capNames = "Tasks";
			setupChangeMessage(340, 520, 970);
			break;
		case ROLES:
			name = "role";
			capName = "Role";
			capNames = "Roles";
			setupChangeMessage(340, 520, 970);
			break;
		case CONDITIONS:
			name = "condition";
			capName = "Condition";
			capNames = "Conditions";
			setupChangeMessage(340, 360, 670);
			break;
		case EFFORTCATEGORIES:
			name = "effort category";
			capName = "Effort Category";
			capNames = "Effort Categories";
			setupChangeMessage(340, 520, 970);
			break;
		case ARTIFACTS:
			name = "artifact";
			capName = "Artifact";
			capNames = "Artifacts";
			setupChangeMessage(340, 360, 670);
			break;
		case PLANS:
			name = "plan";
			capName = "Plan";
			capNames = "Plans";
			setupChangeMessage(340, 360, 670);
			break;
		case INTERRUPTIONS:
			name = "interruption";
			capName = "Interruption";
			capNames = "Interruptions";
			setupChangeMessage(340, 360, 670);
			break;
		case DEFECTS:
			name = "defect";
			capName = "Defect";
			capNames = "Defects";
			setupChangeMessage(340, 360, 670);
			break;
		default:
			break;
		}
		
		// Set up the GUI elements with the item's descriptive name
		theTitle = new Label(capNames);
		theName = new Label(capName + " Name:");
		theDescription = new Label(capName + " Description:");
		nameColumn = new TableColumn <>(capName + " Name");
		
		// Set up the Tab title within the tab's window
		setupLabelUI(theTitle, "Arial", 24, 100, Pos.BASELINE_LEFT, 10 + xOffset, 10 + yOffset);
		
		// Set up the TableView object for the list of the named list items
		setupTableViewUI(theListItemList, 250, 555, 10 + xOffset,  40 + yOffset, false);
		// Establish the columns in the Table View
		seqColumn.setCellValueFactory(new PropertyValueFactory<>("sequenceNumber"));
		nameColumn.setCellValueFactory(new PropertyValueFactory<>("listItemName"));
		theListItemList.getColumns().add(seqColumn);
		theListItemList.getColumns().add(nameColumn);
		seqColumn.setMaxWidth(40);
		nameColumn.setMinWidth(190);
		
		// Set up the title for the item name TextField
		setupLabelUI(theName, "Arial", 14, 100, Pos.BASELINE_LEFT, 360 + xOffset, 20 + yOffset);
		
		// Set up the TextField for the item name
		setupTextUI(theNameField, "Arial", 14, 205, Pos.BASELINE_LEFT, 365 + xOffset, 40 + yOffset, true);
		theNameField.setOnKeyTyped((event) -> { changeEvent(); });
		limitTextField(theNameField, 20);	// Limit the number of characters in this field to 20
		
		// Set up the title for the item description TextArea
		setupLabelUI(theDescription, "Arial", 14, 100, Pos.BASELINE_LEFT, 360 + xOffset, 75 + yOffset);
		
		// Set up the TextArea for the item description
		setupTextAreaUI(theDescriptionArea, "Arial", 14, 300, 120, 365 + xOffset,  95 + yOffset, true);
		theDescriptionArea.setOnKeyTyped((event) -> { changeEvent(); });
		theDescriptionArea.setWrapText(true);
		
		// Set up the Edit button. It copies the content of the selected table entry an places it
		// into the edit fields to the right of the TableView.  Link the button to the action
		// method and define the tool tip to help the user understand the user interface.
		setupButtonUI(btnEdit, "Arial", 14, 65, Pos.BASELINE_CENTER, 275 + xOffset, 70 + yOffset);
		btnEdit.setOnAction((event) -> { editTheEntry(); });
		btnEdit.setTooltip(new Tooltip("Click here to copy the selected row to the editting fields."));
		
		// Set up the Save button. It copies the content of the edit files into the currently
		// selected table entry in the TableView.  Link the button to the action method
		// and define the tool tip to help the user understand the user interface.
		setupButtonUI(btnSave, "Arial", 14, 65, Pos.BASELINE_CENTER, 275 + xOffset, 110 + yOffset);
		btnSave.setOnAction((event) -> { saveTheEntry(); });		
		btnSave.setTooltip(new Tooltip("Click here to save the edited " + name + " information into the selected entry in the table."));
		
		// Set up the Add Bottom button. It copies the content of the edit files into a new table
		// entry and the bottom of the TableView list.  Link the button to the action method
		// and define the tool tip to help the user understand the user interface.
		setupButtonUI(btnAddBottom, "Arial", 14, 65, Pos.BASELINE_CENTER, 275 + xOffset, 150 + yOffset);
		btnAddBottom.setOnAction((event) -> { AddTheEntryToTheBottom(); });		
		btnAddBottom.setTooltip(new Tooltip("Click here to add the edited " + name + " information into a new entry at the bottom of the table."));
				
		// Set up the Add Up button. It copies the content of the edit files into a new table
		// entry and inserts it into the TableView list just above the currently selected entry.  
		// Link the button to the action method and define the tool tip to help the user understand
		// the user interface.
		setupButtonUI(btnAddUp, "Arial", 14, 65, Pos.BASELINE_CENTER, 275 + xOffset, 190 + yOffset);
		btnAddUp.setOnAction((event) -> { AddTheEntryAbove(); });		
		btnAddUp.setTooltip(new Tooltip("Click here to add the edited " + name + " information into a new entry above the currently selected entry."));
		
		// Set up the Add Dn button. It copies the content of the edit files into a new table
		// entry and inserts it into the TableView list just below the currently selected entry.  
		// Link the button to the action method and define the tool tip to help the user understand
		// the user interface.
		setupButtonUI(btnAddDn, "Arial", 14, 65, Pos.BASELINE_CENTER, 275 + xOffset, 230 + yOffset);
		btnAddDn.setOnAction((event) -> { AddTheEntryBelow(); });		
		btnAddDn.setTooltip(new Tooltip("Click here to add the edited " + name + " information into a new entry below the currently selected entry."));
		
		// Set up the Move Up button. It moves the current selected TabkeView entry up one
		// position. Link the button to the action method and define the tool tip to help the user
		// understand the user interface.
		setupButtonUI(btnMoveUp, "Arial", 14, 70, Pos.BASELINE_CENTER, 15 + xOffset, 600 + yOffset);
		btnMoveUp.setOnAction((event) -> { moveTheEntryUp(); });
		btnMoveUp.setTooltip(new Tooltip("Click here to move the above selected entry up one position"));
		
		// Set up the Move Dn button. It moves the current selected TabkeView entry down one
		// position. Link the button to the action method and define the tool tip to help the user
		// understand the user interface.
		setupButtonUI(btnMoveDn, "Arial", 14, 70, Pos.BASELINE_CENTER, 100 + xOffset, 600 + yOffset);
		btnMoveDn.setOnAction((event) -> { moveTheEntryDn(); });
		btnMoveDn.setTooltip(new Tooltip("Click here to move the above selected entry down one position"));

		// Set up the Delete button. It removes the current selected TabkeView entry. Link the 
		// button to the action method and define the tool tip to help the user understand the
		// user interface.
		setupButtonUI(btnDelete, "Arial", 14, 70, Pos.BASELINE_CENTER, 185 + xOffset, 600 + yOffset);
		btnDelete.setOnAction((event) -> { deleteTheEntry(); });
		btnDelete.setTooltip(new Tooltip("Click here to delete the above selected entry"));

		// Set up the Clear button. Clears the edit name and description fields. Link the 
		// button to the action method and define the tool tip to help the user understand the
		// user interface.
		setupButtonUI(btnClear, "Arial", 14, 290, Pos.BASELINE_CENTER, 370 + xOffset, 220 + yOffset);
		btnClear.setOnAction((event) -> { clearTheEntry(); });
		btnClear.setTooltip(new Tooltip("Click here to clear the " + capName + " Name and Description fields"));
				
		// Add all of the GUI elements to the group for this tab.
		g.getChildren().addAll(theTitle, theName, theNameField, theDescription, theListItemList, theDescriptionArea,
				btnSave, btnEdit, btnAddBottom, btnAddUp, btnMoveUp, btnAddDn, btnMoveDn, btnDelete, btnClear);
		
		g.getChildren().addAll(line, line2, line3, changeMessage);

	}
	
	private void setupChangeMessage(int left, int mid, int right) {
		setupLabelUI(changeMessage, "Arial", 14, 200, Pos.BASELINE_LEFT, mid + xOffset, 615 + yOffset);
		line.setStartX(left + xOffset); 
		line.setStartY(634 + yOffset); 
		line.setEndX(right + xOffset); 
		line.setEndY(634 + yOffset);
		line2.setStartX(left + xOffset); 
		line2.setStartY(634 + yOffset); 
		line2.setEndX(left + xOffset); 
		line2.setEndY(584 + yOffset);
		line3.setStartX(right + xOffset); 
		line3.setStartY(634 + yOffset); 
		line3.setEndX(right + xOffset); 
		line3.setEndY(584 + yOffset);
		line.setStroke(Color.RED);
		line2.setStroke(Color.RED);
		line3.setStroke(Color.RED);
		changeMessage.setTextFill(Color.RED);
		hideTheContentChangedMessage();

	}
	
	/**********
	 * Private local method to initialize the standard fields for a JavaFX Label object
	 * 
	 * @param l		The Label object to be initialized
	 * @param ff	The font face for the label's text
	 * @param f		The font size for the label's text
	 * @param w		The minimum width for the Label
	 * @param p		The alignment for the text within the specified width
	 * @param x		The x-axis location for the Label
	 * @param y		The y-axis location for the Label
	 */
	private void setupLabelUI(Label l, String ff, double f, double w, Pos p, double x, double y){
		l.setFont(Font.font(ff, f));
		l.setMinWidth(w);
		l.setAlignment(p);
		l.setLayoutX(x);
		l.setLayoutY(y);
	}
	
	/**********
	 * Private local method to initialize the standard fields for a text field
	 * 
	 * @param t		The TextField object to be initialized
	 * @param ff	The font face for the label's text
	 * @param f		The font size for the label's text
	 * @param w		The minimum width for the TextField
	 * @param p		The alignment for the text within the specified width
	 * @param x		The x-axis location for the TextField
	 * @param y		The y-axis location for the TextField
	 * @param e		A flag that specific if the TextField is editable
	 */
	private void setupTextUI(TextField t, String ff, double f, double w, Pos p, double x, double y, boolean e){
		t.setFont(Font.font(ff, f));
		t.setMinWidth(w);
		t.setMaxWidth(w);
		t.setAlignment(p);
		t.setLayoutX(x);
		t.setLayoutY(y);		
		t.setEditable(e);
	}

	/**********
	 * Private local method to initialize the standard fields for a TextArea
	 * 
	 * @param a		The TextArea to be initialized
	 * @param ff	The font face for the label's text
	 * @param f		The font size for the label's text
	 * @param w		The minimum width for the TextArea
	 * @param h		The maximum height for the TextArea
	 * @param x		The x-axis location for the TextArea
	 * @param y		The y-axis location for the TextArea
	 * @param e		A flag that specific if the TextArea is editable
	 */
	private void setupTextAreaUI(TextArea a, String ff, double f, double w, double h, double x, double y, boolean e){
		a.setFont(Font.font(ff, f));
		a.setMinWidth(w);
		a.setMaxWidth(w);
		a.setMinHeight(h);
		a.setMaxHeight(h);
		a.setLayoutX(x);
		a.setLayoutY(y);		
		a.setEditable(e);
	}

	/**********
	 * Private local method to initialize the standard fields for a TableView
	 * 
	 * @param a		The TableView to be initialized
	 * @param w		The minimum width for the TextArea
	 * @param h		The maximum height for the TextArea
	 * @param x		The x-axis location for the TextArea
	 * @param y		The y-axis location for the TextArea
	 * @param e		A flag that specific if the TextArea is editable
	 */
	private void setupTableViewUI(TableView<ListItemEntry> a, double w, double h, double x, double y, boolean e){
 		a.setMinWidth(w);
		a.setMaxWidth(w);
		a.setMinHeight(h);
		a.setMaxHeight(h);
		a.setLayoutX(x);
		a.setLayoutY(y);		
		a.setEditable(e);
	}

	/**********
	 * Private local method to initialize the standard fields for a button
	 * 
	 * @param b		The Button to be initialized
	 * @param ff	The font face for the label's text
	 * @param f		The font size for the label's text
	 * @param w		The minimum width for the TextArea
	 * @param p		The alignment for the text within the specified width
	 * @param x		The x-axis location for the TextField
	 * @param y		The y-axis location for the TextField
	 */
	private void setupButtonUI(Button b, String ff, double f, double w, Pos p, double x, double y){
		b.setFont(Font.font(ff, f));
		b.setMinWidth(w);
		b.setAlignment(p);
		b.setLayoutX(x);
		b.setLayoutY(y);		
	}
	
	public ObservableList<ListItemEntry> getTheList() {
		if (theListItemList == null) return null;
		return theListItemList.getItems();
	}
	
	protected void changeEvent() {
		showTheContentChangedMessage();
	}
	
	protected void hideTheContentChangedMessage() {
		line.setVisible(false);
		line2.setVisible(false);
		line3.setVisible(false);
		changeMessage.setVisible(false);
	}
	
	protected void showTheContentChangedMessage() {
		line.setVisible(true);
		line2.setVisible(true);
		line3.setVisible(true);
		changeMessage.setVisible(true);
	}
	
	public String toString() {
		int numElements = this.getTheList().size();
		String result = "ListItem: Size = " + numElements + "; Contents = [";
		for (int ndx = 0; ndx < numElements; ndx++) {
			result += this.getTheList().get(ndx).getListItemName();
			if (ndx < numElements - 1) result += ", ";
		}
		return result + "]";
	}

		
	/**********************************************************************************************

	Event Handlers
	
	The following methods process the GUI events as required to perform the application's work.
	
	Since this class is inherited to extent this basic functionality as required for those tabs
	where tab-specific information is added to the basic item name and item description that this
	class supports.  The class attribute tabSpecificIndex is used by the classes that inherit
	this class to manage a parallel list of attributes without requiring a rewrite of the following
	methods.
	
	**********************************************************************************************/

	
	/**********
	 * This event handler is called when the Move Up button is pressed
	 */
	protected void moveTheEntryUp() {
		// Set this attribute to imply that the tab specific data should not be added
		tabSpecificIndex = -1;

		// Fetch the index of the selected row in the TableView
		int theIndex = theListItemList.getSelectionModel().getSelectedIndex();
		
		// If no row is selected, give a warning and ignore the request
		if (theIndex == -1) {
			Alert alert = new Alert(AlertType.WARNING, "Move Up is only valid when a row has been selected!", ButtonType.OK);
			alert.showAndWait();
			return;
		}
		
		// If the selected row is the top most row, it is not possible to move it up, so give an
		// appropriate warning and ignore the request
		if (theIndex == 0) {
			Alert alert = new Alert(AlertType.WARNING, "Move Up is not valid for the first row!", ButtonType.OK);
			alert.showAndWait();
			return;
		}
		
		// Given a valid and proper index, produce an observable list from the TableView and swap
		// the selected item with the one above it.
		ObservableList<ListItemEntry> de = theListItemList.getItems();
		ListItemEntry tempDE = de.get(theIndex-1);
		de.set(theIndex-1, de.get(theIndex));
		de.set(theIndex, tempDE);

		// Renumber the swapped entries
		for (int ndx = theIndex-1; ndx <= theIndex; ndx++) {
			ListItemEntry def = de.get(ndx);
			def.setSequenceNumber(ndx + 1);
		}
		
		// Place the updated list back into the table view
		theListItemList.setItems(de);
		
		// Select the entry that was moved up and make sure it is visible
		theListItemList.scrollTo(theIndex-1);
		theListItemList.getSelectionModel().select(theIndex-1);
		
		// Set this attribute to imply that the tab specific data should be added to the parallel
		// list of tab specific attributes
		tabSpecificIndex = theIndex;
	}
	
	/**********
	 * This event handler is called when the Move Dn button is pressed
	 */
	protected void moveTheEntryDn() {
		// Set this attribute to imply that the tab specific data should not be added
		tabSpecificIndex = -1;

		// Fetch the index of the selected row in the TableView
		int theIndex = theListItemList.getSelectionModel().getSelectedIndex();
		
		// If no row is selected, give a warning and ignore the request
		if (theIndex == -1) {
			Alert alert = new Alert(AlertType.WARNING, "Move Dn is only valid when a row has been selected!", ButtonType.OK);
			alert.showAndWait();
			return;
		}
		
		// If the selected row is the bottom row, it is not possible to move it down, so give an
		// appropriate warning and ignore the request
		ObservableList<ListItemEntry> de = theListItemList.getItems();
		if (theIndex == de.size()-1) {
			Alert alert = new Alert(AlertType.WARNING, "Move Up is not valid for the last row!", ButtonType.OK);
			alert.showAndWait();
			return;
		}
		
		// Given a valid and proper index, produce an observable list from the TableView and swap
		// the selected item with the one above it.
		ListItemEntry tempDE = de.get(theIndex+1);
		de.set(theIndex+1, de.get(theIndex));
		de.set(theIndex, tempDE);

		// Renumber the swapped entries
		for (int ndx = theIndex; ndx <= theIndex+1; ndx++) {
			ListItemEntry def = de.get(ndx);
			def.setSequenceNumber(ndx + 1);
		}

		// Place the updated list back into the table view
		theListItemList.setItems(de);
		
		// Select the entry that was moved down and make sure it is visible
		theListItemList.scrollTo(theIndex+1);
		theListItemList.getSelectionModel().select(theIndex+1);
		
		// Set this attribute to imply that the tab specific data should be added to the parallel
		// list of tab specific attributes
		tabSpecificIndex = theIndex;
	}
	
	/**********
	 * This event handler is called when the Delete button is pressed
	 */
	protected void deleteTheEntry() {
		// Set this attribute to imply that the tab specific data should not be added
		tabSpecificIndex = -1;

		// Fetch the index of the selected row in the TableView
		int theIndex = theListItemList.getSelectionModel().getSelectedIndex();
		
		// If no row is selected, give a warning and ignore the request
		if (theIndex == -1) {
			Alert alert = new Alert(AlertType.WARNING, "Delete is only valid when a row has been selected!", ButtonType.OK);
			alert.showAndWait();
			return;
		}
		
		// If beyond the end of the list just return (This should never happen,
		// so we have not spent effort raising a warning.)
		ObservableList<ListItemEntry> de = theListItemList.getItems();
		if (theIndex > de.size()-1) return;
		
		// Remove the item from the list and shift the remaining up
		de.remove(theIndex);
		
		// Correct the following sequence numbers
		for (int ndx = theIndex; ndx < de.size(); ndx++){
			ListItemEntry d = de.get(ndx);
			d.sequenceNumber = ndx + 1;
			de.set(ndx, d);
		}
		
		// Place the list back into the TableView
		theListItemList.setItems(de);
		
		// Remove the selection from the TableView so no entry is selected
		theListItemList.getSelectionModel().select(-1);
		
		// Set this attribute to imply that the tab specific data should be added to the parallel
		// list of tab specific attributes
		tabSpecificIndex = theIndex;
	}
	
	/**********
	 * This event handler is called when the Edit button is pressed
	 */
	protected void editTheEntry() {
		// Set this attribute to imply that the tab specific data should not be added
		tabSpecificIndex = -1;

		// Fetch the index of the selected row in the TableView
		int theIndex = theListItemList.getSelectionModel().getSelectedIndex();
		
		// If no row is selected, give a warning and ignore the request
		if (theIndex == -1) {
			Alert alert = new Alert(AlertType.WARNING, "Edit is only valid when a row has been selected!", ButtonType.OK);
			alert.showAndWait();
			return;
		}
		
		// If beyond the end of the list just return (This should never happen,
		// so we have not spent effort raising a warning.)
		ObservableList<ListItemEntry> de = theListItemList.getItems();
		if (theIndex > de.size()-1) return;

		// Get the Item Entry from the selected entry in the list 
		ListItemEntry def = de.get(theIndex);
		
		// Extract the Name and the Description from it and place those values into the Edit fields
		theItemName = def.getListItemName();
		theItemDescription = def.getListItemDescription();
		theNameField.setText(theItemName);
		theDescriptionArea.setText(theItemDescription);
		
		// Set this attribute to imply that the tab specific data should be fetched from the 
		// parallel list of tab specific attributes
		tabSpecificIndex = theIndex;
	}
	
	/**********
	 * This event handler is called when the Save button is pressed
	 */
	protected void saveTheEntry() {
		// Set this attribute to imply that the tab specific data should not be added
		tabSpecificIndex = -1;

		// Fetch the index of the selected row in the TableView
		int theIndex = theListItemList.getSelectionModel().getSelectedIndex();

		// If no entry is selected, give a warning and ignore the request
		if (theIndex == -1) {
			Alert alert = new Alert(AlertType.WARNING, "Save is only valid when a row has been selected!", ButtonType.OK);
			alert.showAndWait();
			return;
		}
		
		// If beyond the end of the list just return (This should never happen,
		// so we have not spent effort raising a warning.)
		ObservableList<ListItemEntry> de = theListItemList.getItems();
		if (theIndex > de.size()-1) return;

		// If Name field is empty, give a warning, and just return without doing anything.
		if (theNameField.getText().length() < 1) {
			Alert alert = new Alert(AlertType.WARNING, "The " + capName + " Name must not be empty", ButtonType.OK);
			alert.showAndWait();
			return;
		}
		
		hideTheContentChangedMessage();
		
		// Fetch the values from the edit fields
		theItemName = theNameField.getText();
		theItemDescription = theDescriptionArea.getText();
		
		// Create a new item entry based on those values
		ListItemEntry def = new ListItemEntry(theIndex+1, theItemName, theItemDescription);
		
		// Place the new item entry back into the list at the position selected
		de.set(theIndex, def);
		
		// Place the list back into the TableView
		theListItemList.setItems(de);
		theListItemList.getSelectionModel().select(theIndex);
		
		// Set this attribute to imply that the tab specific data should be added to the parallel
		// list of tab specific attributes
		tabSpecificIndex = theIndex;
	}
	
	/**********
	 * This event handler is called when the Add to the Bottom button is pressed
	 */
	protected void AddTheEntryToTheBottom() {
		// Set this attribute to imply that the tab specific data should not be added
		tabSpecificIndex = -1;

		// Get the list from the TableView
		ObservableList<ListItemEntry> de = theListItemList.getItems();
		
		// Fetch the index of the last item in the list
		int theItemIndex = de.size();
		
		// If Name field is empty, give a warning, and just return without doing anything.
		if (theNameField.getText().length() < 1) {
			Alert alert = new Alert(AlertType.WARNING, "The " + capName + " Name may not be empty", ButtonType.OK);
			alert.showAndWait();
			return;
		}
		
		hideTheContentChangedMessage();
		
		// Fetch the values from the edit fields
		theItemName = theNameField.getText();
		theItemDescription = theDescriptionArea.getText();

		// Create a new item entry based on those values
		ListItemEntry def = new ListItemEntry(theItemIndex+1, theItemName, theItemDescription);
		
		// Add the new item entry at the end of the list
		de.add(def);
		
		// Place the list into the TableView
		theListItemList.setItems(de);
		
		// Make sure the just added item will be visible and then select it
		theListItemList.scrollTo(theItemIndex);
		theListItemList.getSelectionModel().select(theItemIndex);
		
		// Set this attribute to imply that the tab specific data should be added to the parallel
		// list of tab specific attributes
		tabSpecificIndex = theItemIndex;
		}
	
	/**********
	 * This event handler is called when the Add Above the current selected entry button is pressed
	 */
	protected void AddTheEntryAbove() {
		// Set this attribute to imply that the tab specific data should not be added
		tabSpecificIndex = -1;

		// Fetch the index of the selected row in the TableView
		int theIndex = theListItemList.getSelectionModel().getSelectedIndex();

		// If no entry is selected, give a warning and ignore the request
		if (theIndex == -1) {
			Alert alert = new Alert(AlertType.WARNING, "Add Up is only valid when a row has been selected!", ButtonType.OK);
			alert.showAndWait();
			return;
		}

		// If Name field is empty, give a warning, and just return without doing anything.
		if (theNameField.getText().length() < 1) {
			Alert alert = new Alert(AlertType.WARNING, "The " + capName + " Name may not be empty", ButtonType.OK);
			alert.showAndWait();
			return;
		}
		
		hideTheContentChangedMessage();

		// Fetch the list of entries
		ObservableList<ListItemEntry> de = theListItemList.getItems();
		
		// Fetch the values from the edit fields
		theItemName = theNameField.getText();
		theItemDescription = theDescriptionArea.getText();
		
		// Create a new item entry with the proper sequence number
		ListItemEntry def = new ListItemEntry(theIndex+1, theItemName, theItemDescription);
		
		// Add the new item entry into the list at the selected entry, pushing the others down
		de.add(theIndex, def);
		
		// Correct the following sequence numbers, if there are any there
		for (int ndx = theIndex+1; ndx < de.size(); ndx++){
			ListItemEntry d = de.get(ndx);
			d.sequenceNumber = ndx + 1;
			de.set(ndx, d);
		}
		
		// Make sure that the selected entry is visible
		theListItemList.scrollTo(theIndex);
		
		// Select the new entry
		theListItemList.getSelectionModel().select(theIndex);
		
		// Set this attribute to imply that the tab specific data should be added to the parallel
		// list of tab specific attributes
		tabSpecificIndex = theIndex;
	}
	
	/**********
	 * This event handler is called when the Add Below the current selected entry button is pressed
	 */
	protected void AddTheEntryBelow() {
		// Set this attribute to imply that the tab specific data should not be added
		tabSpecificIndex = -1;
		
		// Fetch the index of the selected row in the TableView
		int theIndex = theListItemList.getSelectionModel().getSelectedIndex();

		// If no entry is selected, give a warning and ignore the request
		if (theIndex == -1) {
			Alert alert = new Alert(AlertType.WARNING, "Add Down is only valid when a row has been selected!", ButtonType.OK);
			alert.showAndWait();
			return;
		}
		
		// If Name field is empty, give a warning, and just return without doing anything.
		if (theNameField.getText().length() < 1) {
			Alert alert = new Alert(AlertType.WARNING, "The " + capName + " Name may not be empty", ButtonType.OK);
			alert.showAndWait();
			return;
		}
		
		hideTheContentChangedMessage();
		
		// Fetch the list of entries
		ObservableList<ListItemEntry> de = theListItemList.getItems();
		
		// If beyond the end of the list just return (This should never happen,
		// so we have not spent effort raising a warning.)		
		if (theIndex > de.size()-1) return;
		
		// Fetch the values from the edit fields
		theItemName = theNameField.getText();
		theItemDescription = theDescriptionArea.getText();

		// Create a new item entry with the proper sequence number
		ListItemEntry def = new ListItemEntry(theIndex+2, theItemName, theItemDescription);
		
		// Place the new item entry just below the selected entry
		de.add(theIndex+1, def);
		
		// Correct the following sequence numbers, if there are any there
		for (int ndx = theIndex+2; ndx < de.size(); ndx++){
			ListItemEntry d = de.get(ndx);
			d.sequenceNumber = ndx + 1;
			de.set(ndx, d);
		}
		
		// Place the list of entries back into the TableView
		theListItemList.setItems(de);
		
		// Be sure that the just stored value is visible
		theListItemList.scrollTo(theIndex+1);
		
		// And select it
		theListItemList.getSelectionModel().select(theIndex+1);
		
		// Set this attribute to imply that the tab specific data should be added to the parallel
		// list of tab specific attributes
		tabSpecificIndex = theIndex+1;
	}
	
	/**********
	 * This event handler is called when the Clear button is pressed
	 */
	protected void clearTheEntry() {
		// Clear the name field and the description fields
		theItemName = "";
		theItemDescription = "";
		theNameField.setText(theItemName);
		theDescriptionArea.setText(theItemDescription);
		showTheContentChangedMessage();
	}
	
	/**********************************************************************************************

	Support methods
		
	**********************************************************************************************/

	
	/**********
	 * The limitTextField method sets a listener to watch changes to the Item Name.  If the new
	 * name is larger than maxLength characters, a warning is displayed and the field is set to
	 * just the first maxLength characters.
	 * 
	 * @param tf		This is the TextField
	 * @param maxLength	This is the limit
	 */
	public static void limitTextField(final TextField tf, final int maxLength) {
		// This statement sets a listener which will be called any time this field is changed
	    tf.textProperty().addListener((ChangeListener<? super String>) new ChangeListener<String>() {
	    	
	    	// The following is the methods that is called whenever the specific TextField is changed
	        @Override
	        public void changed(final ObservableValue<? extends String> ov, final String oldValue, final String newValue) {
	            // If the new length is larger than the maxLength
	        	if (tf.getText().length() > maxLength) {
	        		// Extract the first maxLength of characters
	                String s = tf.getText().substring(0, maxLength);
	                
	                // Set that truncated text as the input
	                tf.setText(s);
	                
	                // Display a Warning alert informing the user about what is happening.
	        		Alert alert = new Alert(AlertType.WARNING, "This name field may not exceed 20 characters", ButtonType.OK);
	        		alert.showAndWait();
	            }
	        }
	    });
	}
	
	/**********************************************************************************************

	List Item Entity class
	
	This subordinate class is the root of the set of tabs.  Each tab establishes a list of items and
	the following class establishes the entity object that is used to create the list.  Some of the
	tabs have tab specific information and so those tab will inherit this class and extend it by
	adding a parallel list of tab specific entity objects.
	
	**********************************************************************************************/


	/**********
	 * The following Class defines a Item Entry used by this application to build a TableView list
	 * of Item kinds for the purpose of logging effort against similar kinds of items. 
	 * 
	 * @author Lynn Robert Carter
	 *
	 */
	public class ListItemEntry {
		// These are the attributes for an item
		private Integer sequenceNumber = 0;
		private String listItemName = null;
		private String listItemDescription = null;
		
		// This is the Item Entry constructor
		public ListItemEntry(int sn, String dn, String dd) {
			sequenceNumber = sn;
			listItemName = dn;
			listItemDescription = dd;
		}
		
		// The debugging toString method
		public String toString() {
			return "\n" + sequenceNumber + " " + listItemName + "\n   " + listItemDescription;
		}
		
		// The sequence number getter as setter
		public int getSequenceNumber() {
			return sequenceNumber;
		}
		
		public void setSequenceNumber(int sn) {
			sequenceNumber = sn;
		}
		
		// The item name getter and setter
		public String getListItemName() {
			return listItemName;
		}
		
		public void setListItemName(String dn) {
			listItemName = dn;
		}
		
		// The item description getter and setter
		public String getListItemDescription() {
			return listItemDescription;
		}
		
		public void setListItemDescription(String dd) {
			listItemDescription = dd;
		}
	}
}