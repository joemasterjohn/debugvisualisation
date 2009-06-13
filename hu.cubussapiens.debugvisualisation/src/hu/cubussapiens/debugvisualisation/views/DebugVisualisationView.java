package hu.cubussapiens.debugvisualisation.views;

import hu.cubussapiens.debugvisualisation.internal.DebugContextListener;
import hu.cubussapiens.debugvisualisation.internal.IStackFrameConsumer;
import hu.cubussapiens.debugvisualisation.internal.VariablesGraphContentProvider;
import hu.cubussapiens.debugvisualisation.internal.VariablesLabelProvider;
import hu.cubussapiens.debugvisualisation.internal.input.DebugContextInputFactory;
import hu.cubussapiens.debugvisualisation.internal.input.IDebugContextInput;
import hu.cubussapiens.zestlayouts.LayoutManager;

import org.eclipse.debug.core.model.IStackFrame;
import org.eclipse.debug.ui.DebugUITools;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.part.*;
import org.eclipse.zest.core.viewers.GraphViewer;



public class DebugVisualisationView extends ViewPart implements IStackFrameConsumer {

	GraphViewer viewer;
	
	LayoutManager layout = new LayoutManager();
	
	DebugContextInputFactory inputfactory = new DebugContextInputFactory();
	
	DebugContextListener listener = null;
	
	VariablesLabelProvider labelprovider = new VariablesLabelProvider();
	
	VariablesGraphContentProvider contentprovider = new VariablesGraphContentProvider();
	
	@Override
	public void createPartControl(Composite parent) {
		parent.setLayout(new FillLayout());
		viewer = new GraphViewer(parent,SWT.NONE);
		viewer.setLayoutAlgorithm(layout.getDefault());
		viewer.setLabelProvider(labelprovider);
		viewer.setContentProvider(contentprovider);
		
		//getSite().setSelectionProvider(viewer);
		
		//TODO: the previously started debug context could retrieved by this line:
		//IAdaptable dc = DebugUITools.getDebugContext();
		//viewer.setInput(null);
		
		listener = new DebugContextListener(this);
		DebugUITools.getDebugContextManager().addDebugContextListener(listener);
	}

	public void setStackFrame(IStackFrame stackframe) {
		IDebugContextInput input = inputfactory.getInput(stackframe);
		labelprovider.setInput(input);
		viewer.setInput(input);	
	}
	
	@Override
	public void setFocus() {
		viewer.getControl().setFocus();
		
	}
	
	@Override
	public void dispose() {
		super.dispose();
		if (listener != null)
			DebugUITools.getDebugContextManager().removeDebugContextListener(listener);
	}
	
}