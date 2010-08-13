package hu.cubussapiens.debugvisualisation.views;

import hu.cubussapiens.debugvisualisation.internal.DebugContextListener;
import hu.cubussapiens.debugvisualisation.internal.IStackFrameConsumer;
import hu.cubussapiens.debugvisualisation.internal.StackFrameGraphContentProvider;
import hu.cubussapiens.debugvisualisation.internal.VariablesLabelProvider;
import hu.cubussapiens.debugvisualisation.internal.input.StackFrameContextInput;
import hu.cubussapiens.debugvisualisation.internal.input.StackFrameContextInputFactory;
import hu.cubussapiens.debugvisualisation.internal.widgets.VisualisationGraphViewer;
import hu.cubussapiens.debugvisualisation.layouts.LayoutManager;
import hu.cubussapiens.debugvisualisation.viewmodel.VisualisationSettings;
import hu.cubussapiens.debugvisualisation.views.actions.RefreshLayoutAction;
import hu.cubussapiens.debugvisualisation.views.actions.SaveImageAction;
import hu.cubussapiens.debugvisualisation.views.actions.SelectLayoutGroup;
import hu.cubussapiens.debugvisualisation.views.actions.ShowRootAction;
import hu.cubussapiens.debugvisualisation.views.actions.ToggleOpenAction;

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
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.part.ViewPart;
import org.eclipse.zest.core.viewers.AbstractZoomableViewer;
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
	VisualisationGraphViewer graphViewer;

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

	VariableSelectionSynchronizer selectionSynchronizer;

	// ------------------------------------
	// Actions
	// ------------------------------------

	/**
	 * Toggle open/closed state of selected nodes
	 */
	IAction toggleOpen;

	/**
	 * Refresh layout
	 */
	IAction refresh;


	/**
	 * Action to show root node
	 */
	IAction showRoot;
	/**
	 * Action to save the graph as an image file.
	 */
	SaveImageAction saveImage;

	/**
	 * The action group
	 */
	SelectLayoutGroup group;

	/*
	 * IStateListener stateListener = new IStateListener() {
	 * 
	 * public void handleStateChange(State state, Object oldValue) {
	 * graphViewer.refresh(); } };
	 */


	private ZoomContributionViewItem zoom;

	/*
	 * private String contextCommandID =
	 * "hu.cubussapiens.debugvisualisation.showlocalcontext"; private String
	 * contextStateID = "org.eclipse.ui.commands.toggleState";
	 * 
	 * private State getState() { ICommandService commandService =
	 * (ICommandService) PlatformUI
	 * .getWorkbench().getActiveWorkbenchWindow().getService(
	 * ICommandService.class); Command command =
	 * commandService.getCommand(contextCommandID); return
	 * command.getState(contextStateID); }
	 */

	/**
	 * @return the graphViewer
	 */
	public VisualisationGraphViewer getGraphViewer() {
		return graphViewer;
	}

	@Override
	public void createPartControl(Composite parent) {

		// create viewer
		graphViewer = new VisualisationGraphViewer(parent, SWT.NONE);
		graphViewer.setLayoutAlgorithm(layout.getDefault());
		graphViewer.setLabelProvider(labelprovider);
		graphViewer.setContentProvider(contentprovider);
		if (VisualisationSettings.filterPrimitiveTypes) {
			graphViewer.addFilter(VisualisationSettings.filter);
		}
		graphViewer.setConnectionStyle(ZestStyles.CONNECTIONS_DIRECTED);
		// TODO why is this needed for selection synchronizing?
		graphViewer.setUseHashlookup(false);

		initializeActions();
		createToolbar();
		createMenu();

		// double click on nodes
		graphViewer.getGraphControl().addMouseListener(new MouseAdapter() {

			@Override
			public void mouseDoubleClick(MouseEvent e) {
				toggleOpen.run();

			}

		});

		// graphViewer.getGraphControl().addKeyListener(new KeyAdapter() {
		//
		// @Override
		// public void keyReleased(KeyEvent e) {
		// if (e.keyCode == SWT.DEL || e.character == 8) {
		// hideNode.run();
		// }
		//
		// }
		//
		// });

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

		getSite().setSelectionProvider(graphViewer);
		selectionSynchronizer = new VariableSelectionSynchronizer(getSite());

	}

	public void selectionChanged(SelectionChangedEvent event) {
		graphViewer.setSelection(event.getSelection());
	}

	/**
	 * Creates the menu GUI contributions
	 */
	private void createMenu() {
		MenuManager mm = new MenuManager();
		graphViewer.getGraphControl().setMenu(
				mm.createContextMenu(graphViewer.getGraphControl()));

		mm.add(toggleOpen);
		// mm.add(hideNode);
		// mm.add(showChilds);
		mm.add(new Separator());
		mm.add(showRoot);
		getSite()
				.registerContextMenu(
						mm, graphViewer);

		IMenuManager imm = getViewSite().getActionBars().getMenuManager();
		imm.add(refresh);
		group.fillContextMenu(imm);
		imm.add(new Separator());
		imm.add(zoom);
		imm.add(saveImage);
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
		toggleOpen = new ToggleOpenAction(graphViewer);
		refresh = new RefreshLayoutAction(graphViewer);
		showRoot = new ShowRootAction(graphViewer);
		group = new SelectLayoutGroup(layout, graphViewer);
		zoom = new ZoomContributionViewItem(this);
		saveImage = new SaveImageAction(graphViewer);

		/*
		 * State state = getState(); state.addListener(stateListener);
		 */
	}

	/**
	 * A new stack frame is given when the debug context is changed
	 */
	public void setStackFrame(IStackFrame stackframe) {
		StackFrameContextInput input = inputfactory.getInput(stackframe);
		labelprovider.setInput(input);
		graphViewer.setInput(input);
		// graphViewer.refresh();
	}

	@Override
	public void setFocus() {
		graphViewer.getControl().setFocus();

	}

	@Override
	public void dispose() {
		super.dispose();
		if (listener != null)
			DebugUITools.getDebugContextManager().removeDebugContextListener(
					listener);
		/* getState().removeListener(stateListener); */
		if (selectionSynchronizer != null) {
			selectionSynchronizer.dispose();
		}
	}

	public AbstractZoomableViewer getZoomableViewer() {
		return graphViewer;
	}

}