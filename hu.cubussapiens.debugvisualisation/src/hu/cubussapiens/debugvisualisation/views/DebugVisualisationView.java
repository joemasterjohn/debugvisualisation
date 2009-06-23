package hu.cubussapiens.debugvisualisation.views;

import hu.cubussapiens.debugvisualisation.internal.DebugContextListener;
import hu.cubussapiens.debugvisualisation.internal.IStackFrameConsumer;
import hu.cubussapiens.debugvisualisation.internal.VariablesGraphContentProvider;
import hu.cubussapiens.debugvisualisation.internal.VariablesLabelProvider;
import hu.cubussapiens.debugvisualisation.internal.input.DebugContextInputFactory;
import hu.cubussapiens.debugvisualisation.internal.input.IDebugContextInput;
import hu.cubussapiens.debugvisualisation.views.actions.HideAction;
import hu.cubussapiens.debugvisualisation.views.actions.RefreshLayoutAction;
import hu.cubussapiens.debugvisualisation.views.actions.SelectLayoutGroup;
import hu.cubussapiens.debugvisualisation.views.actions.ShowHiddenChildNodesAction;
import hu.cubussapiens.debugvisualisation.views.actions.ToggleOpenAction;
import hu.cubussapiens.zestlayouts.LayoutManager;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.debug.core.model.IStackFrame;
import org.eclipse.debug.ui.DebugUITools;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.part.ViewPart;
import org.eclipse.zest.core.viewers.AbstractZoomableViewer;
import org.eclipse.zest.core.viewers.GraphViewer;
import org.eclipse.zest.core.viewers.IZoomableWorkbenchPart;
import org.eclipse.zest.core.viewers.ZoomContributionViewItem;
import org.eclipse.zest.core.widgets.ZestStyles;

/**
 * The main visualisation view element.
 */
public class DebugVisualisationView extends ViewPart implements IStackFrameConsumer, IZoomableWorkbenchPart {

	/**
	 * The viewer
	 */
	GraphViewer viewer;

	/**
	 * A layout manager which can provide layouts registered to the extension
	 * point
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

	//------------------------------------
	//Actions
	//------------------------------------

	/**
	 * Toggle open/closed state of selected nodes
	 */
	IAction toggleOpen;

	/**
	 * Hide selected nodes
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

	/**
	 * The action group
	 */
	SelectLayoutGroup group;
	
	private ZoomContributionViewItem zoom;

	@Override
	public void createPartControl(Composite parent) {

		//create viewer
		viewer = new GraphViewer(parent, SWT.NONE);
		viewer.setLayoutAlgorithm(layout.getDefault());
		viewer.setLabelProvider(labelprovider);
		viewer.setContentProvider(contentprovider);
		viewer.setConnectionStyle(ZestStyles.CONNECTIONS_DIRECTED);
		

		initializeActions();
		createToolbar();
		createMenu();

		//double click on nodes
		viewer.getGraphControl().addMouseListener(new MouseAdapter() {

			@Override
			public void mouseDoubleClick(MouseEvent e) {
				toggleOpen.run();

			}

		});

		viewer.getGraphControl().addKeyListener(new KeyAdapter() {

			@Override
			public void keyReleased(KeyEvent e) {
				if (e.keyCode == SWT.DEL || e.character == 8) {
					hideNode.run();
				}

			}

		});

		//listener for debug context
		listener = new DebugContextListener(this);
		DebugUITools.getDebugContextManager().addDebugContextListener(listener);

		//Check if there is an already started debug context
		IAdaptable dc = DebugUITools.getDebugContext();
		if (dc != null) {
			Object o = dc.getAdapter(IStackFrame.class);
			if (o instanceof IStackFrame)
				setStackFrame((IStackFrame)o);
		}
	}

	/**
	 * Creates the menu GUI contributions
	 */
	private void createMenu() {
		MenuManager mm = new MenuManager();
		viewer.getGraphControl().setMenu(mm.createContextMenu(viewer.getGraphControl()));

		mm.add(toggleOpen);
		mm.add(hideNode);
		mm.add(showChilds);

		IMenuManager imm = getViewSite().getActionBars().getMenuManager();
		imm.add(refresh);
		group.fillContextMenu(imm);
		imm.add(new Separator());
		imm.add(zoom);
	}

	/**
	 * Creates the toolbar GUI contributions
	 */
	private void createToolbar() {
		IToolBarManager tm = getViewSite().getActionBars().getToolBarManager();
		tm.add(refresh);
		group.fillActionBars(getViewSite().getActionBars());
		//FIXME this causes java.lang.IllegalArgumentException: Argument cannot be null
		//tm.add(zoom);
	}

	/**
	 * Initializes the action components
	 */
	private void initializeActions() {
		toggleOpen = new ToggleOpenAction(viewer);
		hideNode = new HideAction(viewer);
		showChilds = new ShowHiddenChildNodesAction(viewer);
		refresh = new RefreshLayoutAction(viewer);
		group = new SelectLayoutGroup(layout, viewer);
		zoom = new ZoomContributionViewItem(this);
	}

	/**
	 * A new stack frame is given when the debug context is changed
	 */
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

	public AbstractZoomableViewer getZoomableViewer() {
		return viewer;
	}

}