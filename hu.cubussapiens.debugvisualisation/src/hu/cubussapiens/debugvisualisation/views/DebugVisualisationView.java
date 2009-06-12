package hu.cubussapiens.debugvisualisation.views;

import hu.cubussapiens.zestlayouts.LayoutManager;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.part.*;
import org.eclipse.zest.core.viewers.GraphViewer;



public class DebugVisualisationView extends ViewPart {

	GraphViewer viewer;
	
	LayoutManager layout = new LayoutManager();
	
	@Override
	public void createPartControl(Composite parent) {
		parent.setLayout(new FillLayout());
		viewer = new GraphViewer(parent,SWT.NONE);
		viewer.setLayoutAlgorithm(layout.getDefault());
		
		getSite().setSelectionProvider(viewer);
		
	}

	@Override
	public void setFocus() {
		viewer.getControl().setFocus();
		
	}
	
}