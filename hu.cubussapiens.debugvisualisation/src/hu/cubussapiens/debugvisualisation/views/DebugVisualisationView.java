package hu.cubussapiens.debugvisualisation.views;

import hu.cubussapiens.debugvisualisation.internal.DebugContextListener;
import hu.cubussapiens.debugvisualisation.internal.IStackFrameConsumer;
import hu.cubussapiens.debugvisualisation.internal.VariablesGraphContentProvider;
import hu.cubussapiens.debugvisualisation.internal.VariablesLabelProvider;
import hu.cubussapiens.debugvisualisation.internal.input.DebugContextInputFactory;
import hu.cubussapiens.debugvisualisation.internal.input.IDebugContextInput;
import hu.cubussapiens.debugvisualisation.views.actions.HideAction;
import hu.cubussapiens.debugvisualisation.views.actions.RefreshLayoutAction;
import hu.cubussapiens.debugvisualisation.views.actions.ShowHiddenChildNodesAction;
import hu.cubussapiens.debugvisualisation.views.actions.ToggleOpenAction;
import hu.cubussapiens.debugvisualisation.views.layouts.LayoutAlgorithmContentProvider;
import hu.cubussapiens.debugvisualisation.views.layouts.LayoutAlgorithmLabelProvider;
import hu.cubussapiens.zestlayouts.LayoutManager;
import hu.cubussapiens.zestlayouts.LayoutRegistry.LayoutEntry;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.debug.core.model.IStackFrame;
import org.eclipse.debug.ui.DebugUITools;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.viewers.ComboViewer;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.part.*;
import org.eclipse.zest.core.viewers.GraphViewer;
import org.eclipse.zest.core.widgets.ZestStyles;



public class DebugVisualisationView extends ViewPart implements IStackFrameConsumer {

	/**
	 * The viewer
	 */
	GraphViewer viewer;
	
	/**
	 * A layout manager which can provide layouts registered to the extension point
	 */
	LayoutManager layout = new LayoutManager();
	
	/**
	 * A factory to generate inputs from IStackFrames
	 */
	DebugContextInputFactory inputfactory = new DebugContextInputFactory();
	
	/**
	 * Listening to changes in debug context
	 */
	DebugContextListener listener = null;
	
	/**
	 * Label provider
	 */
	VariablesLabelProvider labelprovider = new VariablesLabelProvider();
	
	/**
	 * Content provider
	 */
	VariablesGraphContentProvider contentprovider = new VariablesGraphContentProvider();
	
	/**
	 * Combo viewer to select layouts
	 */
	ComboViewer layoutselector;
	
	//------------------------------------
	//Actions
	//------------------------------------
	
	/**
	 * toggle open/closed state of selected nodes
	 */
	IAction toggleOpen;
	
	/**
	 * hide selected nodes
	 */
	IAction hideNode;
	
	/**
	 * Show all hidden child nodes of the selected nodes
	 */
	IAction showChilds;
	
	/**
	 * Refresh layout
	 */
	IAction refresh;
	
	@Override
	public void createPartControl(Composite parent) {
		parent.setLayout(new FormLayout());
		
		Composite controlbar = new Composite(parent,SWT.NONE);
		
		layoutselector = new ComboViewer(controlbar);
		layoutselector.setContentProvider(new LayoutAlgorithmContentProvider());
		layoutselector.setLabelProvider(new LayoutAlgorithmLabelProvider());
		layoutselector.setInput(layout);
		layoutselector.setSelection(new StructuredSelection(layout.getDefaultEntry()));
		
		//create viewer
		viewer = new GraphViewer(parent,SWT.NONE);
		viewer.setLayoutAlgorithm(layout.getDefault());
		viewer.setLabelProvider(labelprovider);
		viewer.setContentProvider(contentprovider);
		viewer.setConnectionStyle(ZestStyles.CONNECTIONS_DIRECTED);
		
		//create actions
		toggleOpen = new ToggleOpenAction(viewer);
		hideNode = new HideAction(viewer);
		showChilds = new ShowHiddenChildNodesAction(viewer);
		refresh = new RefreshLayoutAction(viewer);
		
		ActionButton ab = new ActionButton(controlbar,refresh, true, false);
		
		MenuManager mm = new MenuManager();
		viewer.getGraphControl().setMenu(mm.createContextMenu(viewer.getGraphControl()));
		
		mm.add(toggleOpen);
		mm.add(hideNode);
		mm.add(showChilds);
		
		//double click on nodes
		viewer.getGraphControl().addMouseListener(new MouseAdapter(){
			@Override
			public void mouseDoubleClick(MouseEvent e) {
				toggleOpen.run();
			}
		});
		
		//listener on layout selector
		layoutselector.addSelectionChangedListener(new ISelectionChangedListener(){
			public void selectionChanged(SelectionChangedEvent event) {
				Object o = ((IStructuredSelection)layoutselector.getSelection()).getFirstElement();
				if (o != null)
					viewer.setLayoutAlgorithm(((LayoutEntry)o).getLayoutCreator().create(),true);
			}
		});
		
		//listener for debug context
		listener = new DebugContextListener(this);
		DebugUITools.getDebugContextManager().addDebugContextListener(listener);
		
		//Check if there is an already started debug context
		IAdaptable dc = DebugUITools.getDebugContext();
		if (dc != null){
			Object o = dc.getAdapter(IStackFrame.class);
			if (o instanceof IStackFrame)
				setStackFrame((IStackFrame)o);
		}
		
		FormData data = new FormData();
		data.top = new FormAttachment(0);
		data.left = new FormAttachment(0);
		data.right = new FormAttachment(100);
		controlbar.setLayoutData(data);
		
		data = new FormData();
		data.top = new FormAttachment(controlbar);
		data.left = new FormAttachment(0);
		data.right = new FormAttachment(100);
		data.bottom = new FormAttachment(100);
		viewer.getGraphControl().setLayoutData(data);
		
		controlbar.setLayout(new FormLayout());
		
		data = new FormData();
		data.top = new FormAttachment(0);
		data.left = new FormAttachment(0);
		data.bottom = new FormAttachment(100);
		layoutselector.getControl().setLayoutData(data);
		
		data = new FormData();
		data.top = new FormAttachment(0);
		data.left = new FormAttachment(layoutselector.getControl());
		data.bottom = new FormAttachment(100);
		ab.getControl().setLayoutData(data);
	}

	/**
	 * A new stack frame is given when the debug context is changed
	 */
	public void setStackFrame(IStackFrame stackframe) {
		IDebugContextInput input = inputfactory.getInput(stackframe);
		labelprovider.setInput(input);
		//viewer.setInput(null);
		//viewer.refresh();
		viewer.setInput(input);
		System.out.println("Refreshing.. "+input.getVisibleNodes().size());
		//viewer.refresh();
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