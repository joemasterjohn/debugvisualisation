package hu.gbalage.debugvisualisation;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.swt.SWT;
import org.eclipse.zest.core.widgets.GraphConnection;

/**
 * 
 * @author Grill Balazs (balage.g@gmail.com)
 *
 */
public class VariableReference extends GraphConnection {

	private final ValueNode destination;
	
	public VariableReference(ValueNode parent, ValueNode dest, String varname) {
		super(parent.graph, SWT.NONE, parent.node, dest.node);
		destination = dest;
		this.setText(varname);
		dest.incref();
		this.setLineColor(ColorConstants.black);
	}
	
	@Override
	public void dispose() {
		destination.decref();
		super.dispose();
	}

}
