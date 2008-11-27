package hu.gbalage.debugvisualisation;

import hu.gbalage.debugvisualisation.model.Model;
import hu.gbalage.debugvisualisation.view.ZestDebugTreePresentation;

import org.eclipse.debug.ui.DebugUITools;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.part.ViewPart;
import org.eclipse.zest.layouts.LayoutStyles;
import org.eclipse.zest.layouts.algorithms.TreeLayoutAlgorithm;

public class DebugVisualisation extends ViewPart {

private ZestDebugTreePresentation g = null;
	
	public DebugVisualisation(){
		super();
	}
	
	/**
	 * @see org.eclipse.ui.part.WorkbenchPart#createPartControl(org.eclipse.swt.widgets.Composite)
	 */
	@Override
	public void createPartControl(Composite parent) {
		
		
		g = new ZestDebugTreePresentation(parent,SWT.NONE);

		g.setLayoutAlgorithm(new TreeLayoutAlgorithm(LayoutStyles.NO_LAYOUT_NODE_RESIZING), true);

		Model model = new Model(g);
		

		DebugUITools.getDebugContextManager().
			getContextService(getSite().getWorkbenchWindow()).
				addDebugContextListener(new DebugContextListener(model));
	}

	/**
	 * @see org.eclipse.ui.part.WorkbenchPart#setFocus()
	 */
	@Override
	public void setFocus() {
		if (g!=null) g.setFocus();
	}

}
