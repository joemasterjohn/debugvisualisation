/**
 * 
 */
package hu.cubussapiens.debugvisualisation.views;

import hu.cubussapiens.debugvisualisation.BundleImages;

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
 *
 */
public class ActionButton {

	Button button;
	
	IAction action;
	
	Image image = null;
	
	public ActionButton(Composite parent, IAction action, boolean icon, boolean text) {
		button = new Button(parent,SWT.PUSH);
		this.action = action; 
		if (icon) {
			image = BundleImages.getInstance().getImage(action.getImageDescriptor());//action.getImageDescriptor().createImage();
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
	
	public Button getControl(){
		return button;
	}
	
}
