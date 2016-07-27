/**
 *   This package contains classes used to implement the SmartPower user interface
 *   this comprises classes prefixed by Ui of the following types:
 *   AboutBox			- the application description dialogue
 *   Frame				- the main container for the user Interface
 *   EntityAction		- Action handling class for displaying entity forms & tables
 *   UiData...			- General containers for displaying data
 *   TabbedTableDialogue- Dialogue for a tabbed multi-table display
 *   TabbedTablePanel	- Panel for a tabbed multi-table display
 *   SingleTableDialogue- Dialogue for a single table display
 *   TabbedFormDialogue - Dialogue for a tabbed multi-form display
 *   TabbedFormPanel	- Panel for a tabbed multi-form display
 *   SingleFormDialogue	- Dialogue for a single form display
 *   FormPanel			- Abstract form panel with general layout & selector
 *   TablePanel			- Abstract Table panel with generic layout & buttons
 *   ...
 *   xTableLink classes	- these connect the data to the relevant table panels and are
 *   					  x data specific
 *   xTablePanel		- these implement the table panels and are x data specific
 *   xForm				- these implement the forms and are x data specific
 *   Style				- this class defines general styling features such as fonts
 *   The BooleanSelector class is a utility data structure used on the table panels 
 */
/**
 * @author GJWood
 *
 */
package org.ladbury.userInterfacePkg;
/**
*	UI Design principles
*
*	use of controls on forms
*	labels				- use JLabel
*	plain text			- use JtextField
*	numbers				- use JFormattedText field
*	Form selection		- use UiEntitySelector class to implement JCombobox with "Add new" option
*	enumerated types	- use JComboBox
*	single entity ref.	- use UiEntitySelector class to implement JCombobox
*	multi-entity ref.	- use JList
**/