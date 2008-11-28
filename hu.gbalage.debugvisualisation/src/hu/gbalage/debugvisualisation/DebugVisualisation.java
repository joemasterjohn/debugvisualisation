package hu.gbalage.debugvisualisation;

import hu.gbalage.debugvisualisation.layouts.LayoutManager;
import hu.gbalage.debugvisualisation.model.Model;
import hu.gbalage.debugvisualisation.view.ZestDebugTreePresentation;

import org.eclipse.debug.ui.DebugUITools;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.part.ViewPart;

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
		
		LayoutManager man = new LayoutManager();
		
		g = new ZestDebugTreePresentation(parent,SWT.NONE);

		g.setLayoutAlgorithm(man.getDefault(), true);

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
