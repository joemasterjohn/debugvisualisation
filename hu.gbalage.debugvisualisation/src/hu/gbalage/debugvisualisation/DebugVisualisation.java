package hu.gbalage.debugvisualisation;

import hu.gbalage.debugvisualisation.layouts.LayoutManager;
import hu.gbalage.debugvisualisation.model.Model;
import hu.gbalage.debugvisualisation.view.ZestDebugTreePresentation;

import org.eclipse.debug.ui.DebugUITools;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.RowLayout;
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
		
		parent.setLayout(new FormLayout());
		
		FormData controlrowdata = new FormData();
		controlrowdata.left = new FormAttachment(0,0);
		controlrowdata.right = new FormAttachment(100,0);
		controlrowdata.top = new FormAttachment(0,0);
		controlrowdata.bottom = new FormAttachment(0,40);
		
		Composite controlrow = new Composite(parent,SWT.NONE);
		controlrow.setLayoutData(controlrowdata);
		controlrow.setLayout(new RowLayout());
		
		FormData gdata = new FormData();
		gdata.left = new FormAttachment(0,0);
		gdata.right = new FormAttachment(100,0);
		gdata.top = new FormAttachment(0,40);
		gdata.bottom = new FormAttachment(100,0);
		
		LayoutManager man = new LayoutManager();
		
		new LayoutCombo(controlrow, SWT.READ_ONLY,man){
			@Override
			public void change(String layoutid) {
				g.setLayoutAlgorithm(man.getLayoutAlgorithm(layoutid), true);	
			}
		};
		
		
		
		g = new ZestDebugTreePresentation(parent,SWT.NONE);
		g.setLayoutData(gdata);

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
