package mainline;

import actions.ConditionSpecific;
import actions.EffortCategorySpecific;
import actions.LifeCycleSpecific;
import actions.ListItem;
import actions.RoleSpecific;
import actions.StepSpecific;
import actions.TaskSpecific;
import javafx.scene.Group;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TabPane.TabClosingPolicy;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;


/*******
* <p> Title: UserInterface Class. </p>
* 
* <p> Description: A JavaFX application: This controller class that provides the User Interface and 
* the business logic for the application.</p>
* 
* <p> Copyright: Lynn Robert Carter © 2019-05-06 </p>
* 
* @author Lynn Robert Carter
* 
* @version 1.01	2019-06-05 Baseline
* 
*/
public class UserInterface {
	
	/**********************************************************************************************

	Class Attributes
	
	**********************************************************************************************/
	
	// Attributes used to establish the display and control panel within the window provided to us
	// This values are passed in when this class is instantiated
	private static double CONTROL_PANEL_HEIGHT;
	private static double WINDOW_HEIGHT;
	private static double WINDOW_WIDTH;
	
	// These attributes put a graphical frame around the portion of the window that receives the
	// text for the various tabs. These are for decoration and perform no other function.
	private Rectangle rect_outer;
	private Rectangle rect_middle;
	private Rectangle rect_inner;
	
	// This is the root of the user interface
	private Group theRoot = null;
	
	//---------------------------------------------------------------------------------------------
	// These Tab, Group, and action classes define each of the tabs used by this user interface
	public enum TabNames {LIFECYCLES, STEPS, CONDITIONS, TASKS, ROLES, EFFORTCATEGORIES, ARTIFACTS, 
		PLANS, INTERRUPTIONS, DEFECTS
	}
	
	private Tab lifeCyclesTab = new Tab("Life Cycles    ");
	private Group lifeCyclesControls = new Group();
	public LifeCycleSpecific theLifeCycleActions = new LifeCycleSpecific(lifeCyclesControls, 6, 40, TabNames.LIFECYCLES);

	private Tab stepsTab = new Tab("Steps          ");
	private Group stepsControls = new Group();
	public StepSpecific theStepActions = new StepSpecific(stepsControls, 6, 40, TabNames.STEPS);
	
	private Tab conditionsTab = new Tab("Conditions     ");
	private Group conditionsControls = new Group();
	public ConditionSpecific theConditionActions = new ConditionSpecific(conditionsControls, 6, 40, TabNames.CONDITIONS);

	private Tab tasksTab = new Tab("Tasks          ");
	private Group tasksControls = new Group();
	public TaskSpecific theTaskActions = new TaskSpecific(tasksControls, 6, 40, TabNames.TASKS);

	private Tab rolesTab = new Tab("Roles          ");
	private Group rolesControls = new Group();
	public RoleSpecific theRoleActions = new RoleSpecific(rolesControls, 6, 40, TabNames.ROLES);

	private Tab artifactsTab = new Tab("Artifacts      ");
	private Group artifactsControls = new Group();
	public ListItem theArtifactActions = new ListItem(artifactsControls, 6, 40, TabNames.ARTIFACTS);
	
	private Tab plansTab = new Tab("Plans          ");
	private Group plansControls = new Group();
	private ListItem thePlanActions = new ListItem(plansControls, 6, 40, TabNames.PLANS);
	
	private Tab interruptionsTab = new Tab("Interruptions   ");
	private Group interruptionsControls = new Group();
	private ListItem theInterruptionActions = new ListItem(interruptionsControls, 6, 40, TabNames.INTERRUPTIONS);
	
	private Tab defectsTab = new Tab("Defects        ");
	private Group defectsControls = new Group();
	private ListItem theDefectActions = new ListItem(defectsControls, 6, 40, TabNames.DEFECTS);

	
	private Tab effortCategoriesTab = new Tab("Effort Categories   ");
	private Group effortCategoriesControls = new Group();
	private EffortCategorySpecific theEffortCategoryActions = new EffortCategorySpecific(effortCategoriesControls, 6, 40, TabNames.EFFORTCATEGORIES,
			 theArtifactActions, thePlanActions, theInterruptionActions, theDefectActions);
	
	//---------------------------------------------------------------------------------------------
	// The TabPane for the user interface
	
	private TabPane tabPane;
	

	/**********
	 * This constructor establishes the user interface with the needed tabs and widgets
	 * 
	 * @param r		The root of the widgets
	 * @param t		The TabPane we are to use
	 * @param h		The height we should use for the application window
	 * @param w		The width of the application window
	 * @param cph	The location of the controls widgets
	 */
	public UserInterface(Group r, TabPane t, double h, double w, double cph) {
		
		/**********************************************************************************************

		Class Attributes
		
		**********************************************************************************************/
		
		// Set the Stage boundaries to the visual bounds so the window does not totally fill the screen 
		WINDOW_WIDTH = w;
		WINDOW_HEIGHT = h;
		CONTROL_PANEL_HEIGHT = cph;
		
		tabPane = t;
		theRoot = r;

		// Hide all but the first tab
		lifeCyclesControls.setVisible(true);
		stepsControls.setVisible(false);
		conditionsControls.setVisible(false);
		tasksControls.setVisible(false);
		rolesControls.setVisible(false);
		conditionsControls.setVisible(false);
		effortCategoriesControls.setVisible(false);
		artifactsControls.setVisible(false);
		plansControls.setVisible(false);
		interruptionsControls.setVisible(false);
		defectsControls.setVisible(false);

		// These attributes put a graphical frame around the portion of the window that holds the
		// information this knowledge management tool hold, displays, and manipulates.  These
		// graphical elements give a three-dimensional look to the user interface.
		rect_outer =  new Rectangle(0, 0, WINDOW_WIDTH, WINDOW_HEIGHT);
		rect_middle = new Rectangle(5, 5, WINDOW_WIDTH-10, CONTROL_PANEL_HEIGHT-15);
		rect_inner =  new Rectangle(6, 6, WINDOW_WIDTH-12, CONTROL_PANEL_HEIGHT-17);
		
		// Set the fill colors for the border frame to give that three-dimensional look
		rect_outer.setFill(Color.LIGHTGRAY);
		rect_middle.setFill(Color.BLACK);
		rect_inner.setFill(Color.WHITE);
        
		// Use a BorderPane to hold the various tabs
        BorderPane borderPane = new BorderPane();
        borderPane.setLayoutX(9);							// The left edge
        borderPane.setLayoutY(9);							// The upper edge
        borderPane.setPrefWidth(WINDOW_WIDTH-18);			// The right edge
        borderPane.setPrefHeight(CONTROL_PANEL_HEIGHT-24);	// The lower edge
        borderPane.setCenter(tabPane);						// Place the tab pane in the center
        
        // Do not allow the user to close a tab 
        tabPane.setTabClosingPolicy(TabClosingPolicy.UNAVAILABLE);
        
        // Sets up the basic elements for the life cycle tab 
        lifeCyclesTab.setOnSelectionChanged((event) -> { lifeCyclesChanged(); });
		theLifeCycleActions = new LifeCycleSpecific(lifeCyclesControls, 6, 40, TabNames.LIFECYCLES);
        tabPane.getTabs().add(lifeCyclesTab);

        // Sets up the basic elements for the steps tab 
        stepsTab.setOnSelectionChanged((event) -> { stepsChanged(); });
        tabPane.getTabs().add(stepsTab);

        // Sets up the basic elements for the conditions tab)
        conditionsTab.setOnSelectionChanged((event) -> {  conditionsChanged(); });
        tabPane.getTabs().add(conditionsTab);

        // Sets up the basic elements for the tasks tab
        tasksTab.setOnSelectionChanged((event) -> {  tasksChanged(); });
        tabPane.getTabs().add(tasksTab);

        // Sets up the basic elements for the roles tab
        rolesTab.setOnSelectionChanged((event) -> {  rolesChanged(); });
        tabPane.getTabs().add(rolesTab);
        
        // Sets up the basic elements for the effort categories tab
        effortCategoriesTab.setOnSelectionChanged((event) -> {  effortCategoriesChanged(); });
        tabPane.getTabs().add(effortCategoriesTab);
        
        // Sets up the basic elements for the artifacts tab
        artifactsTab.setOnSelectionChanged((event) -> {  artifactsChanged(); });
        tabPane.getTabs().add(artifactsTab);
        
        // Sets up the basic elements for the plans tab
        plansTab.setOnSelectionChanged((event) -> {  plansChanged(); });
        tabPane.getTabs().add(plansTab);
        
        // Sets up the basic elements for the interruptions tab
        interruptionsTab.setOnSelectionChanged((event) -> {  interruptionsChanged(); });
        tabPane.getTabs().add(interruptionsTab);
        
        // Sets up the basic elements for the defects tab (this is for future functions)
        defectsTab.setOnSelectionChanged((event) -> {  defectsChanged(); });
        tabPane.getTabs().add(defectsTab);
        
		// Place the border pane with the table panel into the window
		theRoot.getChildren().addAll(rect_outer, rect_middle, rect_inner, borderPane);
		
		// Add the tab controls to the UI
		theRoot.getChildren().add(lifeCyclesControls);
		theRoot.getChildren().add(stepsControls);
		theRoot.getChildren().add(tasksControls);
		theRoot.getChildren().add(rolesControls);
		theRoot.getChildren().add(conditionsControls);
		theRoot.getChildren().add(effortCategoriesControls);
		theRoot.getChildren().add(artifactsControls);
		theRoot.getChildren().add(plansControls);
		theRoot.getChildren().add(interruptionsControls);
		theRoot.getChildren().add(defectsControls);
  }
	
	
	//---------------------------------------------------------------------------------------------
	//
	// The following private methods are used to manage the various kinds of information that this
	// tools supports from Life Cycles through to Defects
	//
	//---------------------------------------------------------------------------------------------

	/**********
	 * Whenever the user enters or leaves the Contacts Tab, this method is called
	 */
	private void lifeCyclesChanged() {
		// See if this is a deactivate or an activate event
		
		if (!lifeCyclesTab.isSelected()) {
			
			// This is a deactivate event, so hide this tab
			lifeCyclesControls.setVisible(false);
			return;
		}

		// This is an activate event, so make these controls visible
		lifeCyclesControls.setVisible(true);
	}

	
	//---------------------------------------------------------------------------------------------

	/**********
	 * This method is called when entering or leaving the Tasks tab
	 */
	private void stepsChanged() {
		// See if this is a deactivate or an activate event
		
		if (!stepsTab.isSelected()) {
			
			// This is a deactivate event, so hide this tab
			stepsControls.setVisible(false);
			return;
		}

		// This is an activate event, so make these controls visible
		stepsControls.setVisible(true);
	}

	
	//---------------------------------------------------------------------------------------------

	/**********
	 * This method is called when entering or leaving the Tasks tab
	 */
	private void tasksChanged() {
		// See if this is a deactivate or an activate event
		
		if (!tasksTab.isSelected()) {
			
			// This is a deactivate event, so hide this tab
			tasksControls.setVisible(false);
			return;
		}

		// This is an activate event, re-populate the comboBoxes and the task specific items
		theTaskActions.repopulate(theEffortCategoryActions, theEffortCategoryActions, theArtifactActions);

		// This is an activate event, so make these controls visible
		tasksControls.setVisible(true);
	}

	
	//---------------------------------------------------------------------------------------------

	/**********
	 * This method is called when entering or leaving the Tasks tab
	 */
	private void rolesChanged() {
		// See if this is a deactivate or an activate event
		
		if (!rolesTab.isSelected()) {
			
			// This is a deactivate event, so hide this tab
			rolesControls.setVisible(false);
			return;
		}

		// This is an activate event, re-populate the comboBoxes and the role specific items

		// This is an activate event, so make these controls visible
		rolesControls.setVisible(true);
	}


	//---------------------------------------------------------------------------------------------

	/**********
	 * This method is called when entering or leaving the Conditions tab
	 */
	private void conditionsChanged() {		// See if this is a deactivate or an activate event
		// See if this is a deactivate or an activate event
		
		if (!conditionsTab.isSelected()) {
			
			// This is a deactivate event, so hide this tab
			conditionsControls.setVisible(false);
			return;
		}

		// This is an activate event, so make these controls visible
		conditionsControls.setVisible(true);
	}

	//---------------------------------------------------------------------------------------------

	/**********
	 * This method is called when entering or leaving the Effort Categories tab
	 */
	private void effortCategoriesChanged() {
		// See if this is a deactivate or an activate event
		
		if (!effortCategoriesTab.isSelected()) {
			
			// This is a deactivate event, so hide this tab
			effortCategoriesControls.setVisible(false);
			return;
		}

		// This is an activate event, re-populate the comboBoxes and the TableViews
		theEffortCategoryActions.repopulate(theArtifactActions, thePlanActions, theInterruptionActions, theDefectActions);

		// and make the controls visible
		effortCategoriesControls.setVisible(true);
	}

	//---------------------------------------------------------------------------------------------

	/**********
	 * This method is called when entering or leaving the artifacts tab
	 */
	private void artifactsChanged() {
		// See if this is a deactivate or an activate event
		
		if (!artifactsTab.isSelected()) {
			// This is a deactivate event, so hide this tab
			artifactsControls.setVisible(false);
			return;
		}
		
		// This is an activate event, so make these controls visible
		artifactsControls.setVisible(true);
	}

	//---------------------------------------------------------------------------------------------

	/**********
	 * This method is called when entering or leaving the plans tab
	 */
	private void plansChanged() {
		// See if this is a deactivate or an activate event
		
		if (!plansTab.isSelected()) {
			// This is a deactivate event, so hide this tab
			plansControls.setVisible(false);
			return;
		}
				
		// This is an activate event, so make these controls visible
		plansControls.setVisible(true);
	}

	//---------------------------------------------------------------------------------------------

	/**********
	 * This method is called when entering or leaving the interruptions tab
	 */
	private void interruptionsChanged() {
		// See if this is a deactivate or an activate event
		
		if (!interruptionsTab.isSelected()) {
			// This is a deactivate event, so hide this tab
			interruptionsControls.setVisible(false);
			return;
		}
		
		// This is an activate event, so make these controls visible
		interruptionsControls.setVisible(true);
	}

	//---------------------------------------------------------------------------------------------

	/**********
	 * This method is called when entering or leaving the interruptions tab
	 */
	private void defectsChanged() {
		// See if this is a deactivate or an activate event
		
		if (!defectsTab.isSelected()) {
			// This is a deactivate event, so hide this tab
			defectsControls.setVisible(false);
			return;
		}
		
		// This is an activate event, so make these controls visible
		defectsControls.setVisible(true);
	}

	//---------------------------------------------------------------------------------------------
	
}