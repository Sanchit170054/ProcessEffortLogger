package actions;

import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.text.Font;
import mainline.UserInterface.TabNames;

/*******
* <p> Title: StepSpecific Class</p>
* 
* <p> Description: A JavaFX application: This controller class established the user interface for
* Steps Tab objects by inheriting the ListItem and building upon it.</p>
* 
* <p> Copyright: Lynn Robert Carter © 2019-05-11 </p>
* 
* @author Lynn Robert Carter
* 
* @version 1.00	2019-05-11 Baseline
* 
*/
public class StepSpecific extends ListItem {
	
	/**********************************************************************************************

	Class Attributes
	
	**********************************************************************************************/
	
	//---------------------------------------------------------------------------------------------
	// These attributes enable us to hide the details of the tab height and the height and width of
	// of the window decorations for the code that implements this user interface
	private int xOffset = 0;
	private int yOffset = 0;
	
	//---------------------------------------------------------------------------------------------
	// The following are the GUI objects that make up the Step specific elements of the
	// user interface	
	private Label theTitle = new Label("Step specific attributes");
	
	/**********
	 * This constructor establishes the base ListItem and then initializes the Step specific
	 * attributes for the application.
	 * 
	 * @param g		The Group link is used to establish the list of GUI elements for this tab
	 * @param x		The x offset for the GUI elements to fit into the decorative borders
	 * @param y		The y offset
	 * @param t		The enumeration that helps select the right strings for labels, etc.
	 */
	public StepSpecific(Group g, int x, int y, TabNames t) {
		super(g, x, y, t);
		xOffset = x;
		yOffset = y;
		setupLabelUI(theTitle, "Arial", 14, 100, Pos.BASELINE_LEFT, 690 + xOffset, 65 + yOffset);
		g.getChildren().add(theTitle);
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

}
