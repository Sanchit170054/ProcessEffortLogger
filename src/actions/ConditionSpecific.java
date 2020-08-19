package actions;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.text.Font;
import mainline.UserInterface.TabNames;

/*******
* <p> Title: ConditionSpecific Class</p>
* 
* <p> Description: A JavaFX application: This controller class established the user interface for
* Conditions Tab objects by inheriting the ListItem and building upon it with a tab specific entity
* list that parallels the ListItem list.</p>
* 
* <p> Copyright: Lynn Robert Carter © 2019-05-11 </p>
* 
* @author Lynn Robert Carter
* 
* @version 1.01	2019-06-05 Baseline
* 
*/
public class ConditionSpecific extends ListItem {
	
	/**********************************************************************************************

	Class Attributes
	
	**********************************************************************************************/
	
	//---------------------------------------------------------------------------------------------
	// These attributes enable us to hide the details of the tab height and the height and width of
	// of the window decorations for the code that implements this user interface
	private int xOffset = 0;
	private int yOffset = 0;
	
	//---------------------------------------------------------------------------------------------
	// These attributes define the Condition Specific aspects of the GUI
	private boolean theConditionState = true;
	private ObservableList<ConditionSpecificEntry> 
								theConditionSpecificEntryList = FXCollections.observableArrayList();
	
	//---------------------------------------------------------------------------------------------
	// The following are the GUI objects that make up the Condition specific elements of the user
	// interface	
	private Label theTitle = new Label("Default Condition state:");
	private String trueFalse[] = { "True", "False" };
	private ComboBox <String> theChoice = 
								new ComboBox <String>(FXCollections.observableArrayList(trueFalse));
	
	/**********
	 * This constructor establishes the base ListItem and then initializes the Condition specific
	 * attributes for the application.
	 * 
	 * @param g		The Group link is used to establish the list of GUI elements for this tab
	 * @param x		The x offset for the GUI elements to fit into the decorative borders
	 * @param y		The y offset
	 * @param t		The enumeration that helps select the right strings for labels, etc.
	 */
	public ConditionSpecific(Group g, int x, int y, TabNames t) {
		super(g, x, y, t);
		xOffset = x;
		yOffset = y;
		setupLabelUI(theTitle, "Arial", 14, 100, Pos.BASELINE_LEFT, 360 + xOffset, 260 + yOffset);
		setupComboBoxUI(theChoice, 75, 370 + xOffset, 280 + yOffset);
		theChoice.setValue("True");
		theChoice.setOnAction((event) -> { performTheChoice();});
		g.getChildren().addAll(theTitle, theChoice);
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
	 * Private local method to initialize the standard fields for a JavaFX ComboBox object
	 * 
	 * @param c		The ComboBox object
	 * @param w		The width of the GUI element
	 * @param x		The x-axis location for the ComboBox
	 * @param y		The y-axis location for the ComboBox
	 */
	private void setupComboBoxUI(ComboBox <String> c, double w, double x, double y){
		c.setMinWidth(w);
		c.setLayoutX(x);
		c.setLayoutY(y);
	}
	
	/**********
	 * The private handler for a ComboBox change event
	 */
	private void performTheChoice() {
		theConditionState = theChoice.getValue().contentEquals("True");
	}
	
	/**********
	 * The public getter for the value of the Condition Specific field
	 * @return
	 */
	public boolean getConditionState() {
		return theConditionState;
	}
	
	/**********************************************************************************************

	Event Handlers
	
	The following methods leverage the event handlers from the ListItem class by building on them
	and using the tabSpecificIndex attribute to perform the same action on the parallel list as
	was done on the ListItem list.  This dramatically reduces the size and the complexity of this
	class.
	
	**********************************************************************************************/
	
	/**********
	 * This event handler is called when the Move Up button is pressed
	 */
	protected void moveTheEntryUp() {
		// Have the super class do the bulk of the work
		super.moveTheEntryUp();
		
		// If the tabSpecificIndex is not negative, then we must do the same operations as were
		// performed by the super class method
		if (tabSpecificIndex > -1) {
			// This three-step activity moves the entity up a position in the list
			ConditionSpecificEntry temp = theConditionSpecificEntryList.get(tabSpecificIndex-1);
			theConditionSpecificEntryList.set(tabSpecificIndex-1, theConditionSpecificEntryList.get(tabSpecificIndex));
			theConditionSpecificEntryList.set(tabSpecificIndex, temp);
		}		
	}
	
	/**********
	 * This event handler is called when the Move Dn button is pressed
	 */
	protected void moveTheEntryDn() {
		// Have the super class do the bulk of the work
		super.moveTheEntryDn();
		
		// If the tabSpecificIndex is not negative, then we must do the same operations as were
		// performed by the super class method
		if (tabSpecificIndex > -1) {
			// This three-step activity moves the entity down a position in the list
			ConditionSpecificEntry temp = theConditionSpecificEntryList.get(tabSpecificIndex);
			theConditionSpecificEntryList.set(tabSpecificIndex, theConditionSpecificEntryList.get(tabSpecificIndex+1));
			theConditionSpecificEntryList.set(tabSpecificIndex+1, temp);
		}		
	}

	/**********
	 * This event handler is called when the Delete button is pressed
	 */
	protected void deleteTheEntry() {
		// Have the super class do the bulk of the work
		super.deleteTheEntry();
		
		// If the tabSpecificIndex is not negative, then we must do the same operation as was
		// performed by the super class method
		if (tabSpecificIndex > -1) {
			// This deletes the parallel element from the parallel list
			theConditionSpecificEntryList.remove(tabSpecificIndex);
		}		
	}
	
	/**********
	 * This event handler is called when the Edit button is pressed
	 */
	protected void editTheEntry() {
		// Have the super class do the bulk of the work
		super.editTheEntry();
		
		// If the tabSpecificIndex is not negative, then we must do the same operations as were
		// performed by the super class method
		if (tabSpecificIndex > -1) {
			// Fetch the boolean value and set up the ComboBox accordingly
			ConditionSpecificEntry theValue = theConditionSpecificEntryList.get(tabSpecificIndex);
			if (theValue.getDefaultConditionState())
				theChoice.setValue("True");
			else
				theChoice.setValue("False");
		}
	}

	/**********
	 * This event handler is called when the Save button is pressed
	 */
	protected void saveTheEntry() {
		// Have the super class do the bulk of the work
		super.saveTheEntry();
		
		// If the tabSpecificIndex is not negative, then we must do the same operations as were
		// performed by the super class method
		if (tabSpecificIndex > -1) {
			// Fetch the current value from the ComboBox and save it into the proper place in the
			// tab specific list
			theConditionSpecificEntryList.set(tabSpecificIndex, new ConditionSpecificEntry(
					theChoice.getValue().contentEquals("True")));
		}
	}
	
	/**********
	 * This event handler is called when the Add to the Bottom button is pressed
	 */
	protected void AddTheEntryToTheBottom() {
		// Have the super class do the bulk of the work
		super.AddTheEntryToTheBottom();
		
		// If the tabSpecificIndex is not negative, then we must do the same operations as were
		// performed by the super class method
		if (tabSpecificIndex == -1) return;
		
		// Add the tab specific entity into the tab specific list in the same relative position
		theConditionSpecificEntryList.add(new ConditionSpecificEntry(
				theChoice.getValue().contentEquals("True")));
	}
	
	/**********
	 * This event handler is called when the Add Above the current selected entry button is pressed
	 */
	protected void AddTheEntryAbove() {
		// Have the super class do the bulk of the work
		super.AddTheEntryAbove();
		
		// If the tabSpecificIndex is not negative, then we must do the same operations as were
		// performed by the super class method
		if (tabSpecificIndex == -1) return;
		
		// Add the tab specific entity into the tab specific list in the same relative position
		theConditionSpecificEntryList.set(tabSpecificIndex, new ConditionSpecificEntry(
				theChoice.getValue().contentEquals("True")));
	}
	
	/**********
	 * This event handler is called when the Add Below the current selected entry button is pressed
	 */
	protected void AddTheEntryBelow() {
		// Have the super class do the bulk of the work
		super.AddTheEntryBelow();
		
		// If the tabSpecificIndex is not negative, then we must do the same operations as were
		// performed by the super class method
		if (tabSpecificIndex == -1) return;
		
		// Add the tab specific entity into the tab specific list in the same relative position
		theConditionSpecificEntryList.set(tabSpecificIndex+1, new ConditionSpecificEntry(
				theChoice.getValue().contentEquals("True")));
		
	}
	
	/**********
	 * This event handler is called when the Clear button is pressed
	 */
	protected void clearTheEntry() {
		// Have the super class do the bulk of the work
		super.clearTheEntry();
		// Reset the Default Condition Specific attribute to "true"
		theChoice.setValue("True");
	}

	/**********************************************************************************************

	Condition Tab Specific Entity class
	
	This subordinate class is the root of the Condition Tab specific entity list.
	
	**********************************************************************************************/

	/**********
	 * The following Class defines a Condition Specific Entry used by this application to build a
	 * list of Item kinds for the purpose of logging effort against similar kinds of items
	 * 
	 * @author Lynn Robert Carter
	 *
	 */
	public class ConditionSpecificEntry {
		// These are the attributes for a condition specific item
		private boolean defaultConditionState = true;
		
		// This is the Item Entry constructor
		public ConditionSpecificEntry(boolean dcs) {
			defaultConditionState = dcs;
		}
		
		// The debugging toString method
		public String toString() {
			return "\n" + super.toString() + " " + defaultConditionState;
		}
		
		// The sequence number getter as setter
		public boolean getDefaultConditionState() {
			return defaultConditionState;
		}
		
		public void setSequenceNumber(boolean dcs) {
			defaultConditionState = dcs;
		}
	}
}
