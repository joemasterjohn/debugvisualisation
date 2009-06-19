/**
 * 
 */
package hu.cubussapiens.debugvisualisation.views;

import hu.cubussapiens.debugvisualisation.ImagePool;

import org.eclipse.jface.action.IAction;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;

/**
 * A simple button, which can be bound to an action. Caption and image are retrieved from the
 * action.
 */
public class ActionButton {

	Button button;
	
	IAction action;
	
	Image image = null;
	
	/**
	 * Create an ActionButton. The button will get SWT.PUSH as style. The tool tip text 
	 * of the action will always be given to the button.
	 * @param parent composite, which will contain this button
	 * @param action an action to bind
	 * @param icon use icon of action as an image on the button
	 * @param text use text of action as a text on the button
	 */
	public ActionButton(Composite parent, IAction action, boolean icon, boolean text) {
		button = new Button(parent,SWT.PUSH);
		this.action = action; 
		if (icon) {
			image = ImagePool.getInstance().getImage(action.getImageDescriptor());//action.getImageDescriptor().createImage();
			button.setImage(image);
		}
		if (text) {
			button.setText(action.getText());
		}
		button.setToolTipText(action.getToolTipText());
		
		button.addSelectionListener(new SelectionAdapter(){
			@Override
			public void widgetSelected(SelectionEvent e) {
				ActionButton.this.action.run();
			}
		});
		
		button.addDisposeListener(new DisposeListener(){
			public void widgetDisposed(DisposeEvent e) {
				if (image != null) image.dispose();	
			}
		});
		
	}
	
	/**
	 * Retrieve the underlying button for direct usage.
	 * @return the Button
	 */
	public Button getControl(){
		return button;
	}
	
}
