package hu.cubussapiens.debugvisualisation.views;

import hu.cubussapiens.debugvisualisation.internal.DebugContextListener;
import hu.cubussapiens.debugvisualisation.internal.IStackFrameConsumer;
import hu.cubussapiens.debugvisualisation.internal.StackFrameGraphContentProvider;
import hu.cubussapiens.debugvisualisation.internal.VariablesLabelProvider;
import hu.cubussapiens.debugvisualisation.internal.input.StackFrameContextInput;
import hu.cubussapiens.debugvisualisation.internal.input.StackFrameContextInputFactory;
import hu.cubussapiens.debugvisualisation.views.actions.DigInAction;
import hu.cubussapiens.debugvisualisation.views.actions.HideAction;
import hu.cubussapiens.debugvisualisation.views.actions.RefreshLayoutAction;
import hu.cubussapiens.debugvisualisation.views.actions.SelectLayoutGroup;
import hu.cubussapiens.debugvisualisation.views.actions.ShowHiddenChildNodesAction;
import hu.cubussapiens.debugvisualisation.views.actions.ShowRootAction;
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
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.SelectionChangedEvent;
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
public class DebugVisualisationView extends ViewPart implements
		IStackFrameConsumer, IZoomableWorkbenchPart, ISelectionChangedListener {

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
	StackFrameContextInputFactory inputfactory = new StackFrameContextInputFactory();

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
	StackFrameGraphContentProvider contentprovider = new StackFrameGraphContentProvider();

	// ------------------------------------
	// Actions
	// ------------------------------------

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
	 * Dig in graph command
	 */
	IAction digIn;

	/**
	 * Action to show root node
	 */
	IAction showRoot;

	/**
	 * The action group
	 */
	SelectLayoutGroup group;

	/**
	 * Other views to connect selection
	 */
	ConnectedViews connectedviews = new ConnectedViews();

	private ZoomContributionViewItem zoom;

	@Override
	public void createPartControl(Composite parent) {

		// create viewer
		viewer = new GraphViewer(parent, SWT.NONE);
		viewer.setLayoutAlgorithm(layout.getDefault());
		viewer.setLabelProvider(labelprovider);
		viewer.setContentProvider(contentprovider);
		viewer.setConnectionStyle(ZestStyles.CONNECTIONS_DIRECTED);

		initializeActions();
		createToolbar();
		createMenu();

		// double click on nodes
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

		// listener for debug context
		listener = new DebugContextListener(this);
		DebugUITools.getDebugContextManager().addDebugContextListener(listener);

		// Check if there is an already started debug context
		IAdaptable dc = DebugUITools.getDebugContext();
		if (dc != null) {
			Object o = dc.getAdapter(IStackFrame.class);
			if (o instanceof IStackFrame)
				setStackFrame((IStackFrame) o);
		}

		// Connecting the other views
		// TODO: I've commented this out because it causes some serious problems
		// connectedviews.init();
		// getSite().setSelectionProvider(viewer);
		//
		// viewer.addSelectionChangedListener(new ISelectionChangedListener() {
		// public void selectionChanged(SelectionChangedEvent event) {
		// connectedviews.setSelection(event.getSelection());
		// }
		// });
		//
		// connectedviews.addSelectionChangedListener(this);
	}

	public void selectionChanged(SelectionChangedEvent event) {
		viewer.setSelection(event.getSelection());
	}

	/**
	 * Creates the menu GUI contributions
	 */
	private void createMenu() {
		MenuManager mm = new MenuManager();
		viewer.getGraphControl().setMenu(
				mm.createContextMenu(viewer.getGraphControl()));

		mm.add(toggleOpen);
		mm.add(hideNode);
		mm.add(showChilds);
		mm.add(new Separator());
		mm.add(digIn);
		mm.add(showRoot);

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
		tm.add(showRoot);
		// FIXME this causes java.lang.IllegalArgumentException: Argument cannot
		// be null
		// tm.add(zoom);
	}

	/**
	 * Initializes the action components
	 */
	private void initializeActions() {
		toggleOpen = new ToggleOpenAction(viewer);
		hideNode = new HideAction(viewer);
		showChilds = new ShowHiddenChildNodesAction(viewer);
		refresh = new RefreshLayoutAction(viewer);
		digIn = new DigInAction(viewer);
		showRoot = new ShowRootAction(viewer);
		group = new SelectLayoutGroup(layout, viewer);
		zoom = new ZoomContributionViewItem(this);
	}

	/**
	 * A new stack frame is given when the debug context is changed
	 */
	public void setStackFrame(IStackFrame stackframe) {
		StackFrameContextInput input = inputfactory.getInput(stackframe);
		labelprovider.setInput(input);
		viewer.setInput(input);
		viewer.refresh();
	}

	@Override
	public void setFocus() {
		viewer.getControl().setFocus();

	}

	@Override
	public void dispose() {
		super.dispose();
		if (listener != null)
			DebugUITools.getDebugContextManager().removeDebugContextListener(
					listener);
	}

	public AbstractZoomableViewer getZoomableViewer() {
		return viewer;
	}

}