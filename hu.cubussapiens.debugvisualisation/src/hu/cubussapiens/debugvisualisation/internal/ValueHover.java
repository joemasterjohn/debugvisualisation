/**
 * 
 */
package hu.cubussapiens.debugvisualisation.internal;

import hu.cubussapiens.debugvisualisation.viewmodel.IDVValue;

import org.eclipse.debug.core.model.IValue;
import org.eclipse.debug.ui.DebugUITools;
import org.eclipse.debug.ui.IDebugModelPresentation;
import org.eclipse.debug.ui.IValueDetailListener;
import org.eclipse.draw2d.Label;

/**
 *
 */
public class ValueHover extends Label {

	private IDebugModelPresentation modelPresentation;

	public ValueHover(IDVValue value) {
		super("Value is being computer...");
		modelPresentation = DebugUITools.newDebugModelPresentation();
		modelPresentation.computeDetail(value.getRelatedValue(),
				new IValueDetailListener() {
					public void detailComputed(IValue value, String result) {
						setText("Value: " + result);
					}
				});
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.draw2d.Figure#erase()
	 */
	@Override
	public void erase() {
		modelPresentation.dispose();
		super.erase();
	}

}
