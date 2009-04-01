package hu.gbalage.debugvisualisation.view;

import hu.gbalage.debugvisualisation.Activator;
import hu.gbalage.debugvisualisation.DebugContextListener;
import hu.gbalage.debugvisualisation.layouts.LayoutManager;
import hu.gbalage.debugvisualisation.model.Model;

import org.eclipse.debug.ui.DebugUITools;
import org.eclipse.debug.ui.contexts.IDebugContextListener;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.part.ViewPart;

public class DebugVisualisationView extends ViewPart {

	private ZestDebugTreePresentation g = null;
	
	public DebugVisualisationView(){
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
				g.setLayoutAlgorithm(man.getLayoutAlgorithm(layoutid), false);
				g.refresh();
			}
		};
		
		
		
		g = new ZestDebugTreePresentation(parent,SWT.NONE);
		g.setLayoutData(gdata);
		g.addKeyListener(new KeyAdapter() {

			@Override
			public void keyPressed(KeyEvent e) {
				if (e.keyCode == SWT.DEL || e.character == 8) {
					//TODO remove selection
					//g.selectionHide(g); -- this is not available
				} else
					super.keyPressed(e);

			}

		});

		g.setLayoutAlgorithm(man.getDefault(), false);
		g.refresh();

		Model model = new Model(g);
		
		final IDebugContextListener listener = new DebugContextListener(model);

		DebugUITools.getDebugContextManager().
			getContextService(getSite().getWorkbenchWindow()).
				addDebugContextListener(listener);
		
		//set stackframe if debug is started before creating the view
		model.setStackFrame(Activator.getStackFrame());
		
		g.addDisposeListener(new DisposeListener(){
			public void widgetDisposed(DisposeEvent e) {
				DebugUITools.getDebugContextManager().
					getContextService(getSite().getWorkbenchWindow()).
						removeDebugContextListener(listener);
			}
		});
	}

	/**
	 * @see org.eclipse.ui.part.WorkbenchPart#setFocus()
	 */
	@Override
	public void setFocus() {
		if (g!=null) g.setFocus();
	}

}
