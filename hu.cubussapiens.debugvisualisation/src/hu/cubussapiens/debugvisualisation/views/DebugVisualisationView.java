package hu.cubussapiens.debugvisualisation.views;

import hu.cubussapiens.debugvisualisation.internal.DebugContextListener;
import hu.cubussapiens.debugvisualisation.internal.IStackFrameConsumer;
import hu.cubussapiens.debugvisualisation.internal.StackFrameGraphContentProvider;
import hu.cubussapiens.debugvisualisation.internal.VariablesLabelProvider;
import hu.cubussapiens.debugvisualisation.internal.input.StackFrameContextInput;
import hu.cubussapiens.debugvisualisation.internal.input.StackFrameContextInputFactory;
import hu.cubussapiens.debugvisualisation.internal.widgets.VisualisationGraphViewer;
import hu.cubussapiens.debugvisualisation.views.actions.DigInAction;
import hu.cubussapiens.debugvisualisation.views.actions.HideAction;
import hu.cubussapiens.debugvisualisation.views.actions.RefreshLayoutAction;
import hu.cubussapiens.debugvisualisation.views.actions.SaveImageAction;
import hu.cubussapiens.debugvisualisation.views.actions.SelectLayoutGroup;
import hu.cubussapiens.debugvisualisation.views.actions.ShowHiddenChildNodesAction;
import hu.cubussapiens.debugvisualisation.views.actions.ShowRootAction;
import hu.cubussapiens.debugvisualisation.views.actions.ToggleOpenAction;
import hu.cubussapiens.zestlayouts.LayoutManager;

import org.eclipse.core.commands.Command;
import org.eclipse.core.commands.IStateListener;
import org.eclipse.core.commands.State;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.debug.core.model.IStackFrame;
import org.eclipse.debug.ui.AbstractDebugView;
import org.eclipse.debug.ui.DebugUITools;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.commands.ICommandService;
import org.eclipse.zest.core.viewers.AbstractZoomableViewer;
import org.eclipse.zest.core.viewers.IZoomableWorkbenchPart;
import org.eclipse.zest.core.viewers.ZoomContributionViewItem;
import org.eclipse.zest.core.widgets.ZestStyles;

/**
 *
 */
public class DebugVisualisationView extends AbstractDebugView implements
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
	 * Action to save the graph as an image file.
	 */
	SaveImageAction saveImage;

	/**
	 * The action group
	 */
	SelectLayoutGroup group;

	IStateListener stateListener = new IStateListener() {

		public void handleStateChange(State state, Object oldValue) {
			graphViewer.refresh();
		}
	};

	class LocalContextFilter extends ViewerFilter {

		@Override
		public boolean select(Viewer viewer, Object parentElement,
				Object element) {
			State state = getState();
			Boolean value = (Boolean) state.getValue();
			return value || !element.equals(-1);
		}
	}

	/**
	 * Other views to connect selection
	 */
	ConnectedViews connectedviews = new ConnectedViews();
	VariableSelectionSynchronizer selectionSynchronizer;

	private ZoomContributionViewItem zoom;

	private String contextCommandID = "hu.cubussapiens.debugvisualisation.showlocalcontext";
	private String contextStateID = "org.eclipse.ui.commands.toggleState";

	private State getState() {
		ICommandService commandService = (ICommandService) PlatformUI
				.getWorkbench().getActiveWorkbenchWindow().getService(
						ICommandService.class);
		Command command = commandService.getCommand(contextCommandID);
		return command.getState(contextStateID);
	}

	@Override
	protected void configureToolBar(IToolBarManager tbm) {
		tbm.add(refresh);
		group.fillActionBars(getViewSite().getActionBars());
		tbm.add(showRoot);
		// FIXME this causes java.lang.IllegalArgumentException: Argument cannot
		// be null
		// tm.add(zoom);

	}

	@Override
	protected void createActions() {

		toggleOpen = new ToggleOpenAction(graphViewer);
		hideNode = new HideAction(graphViewer);
		showChilds = new ShowHiddenChildNodesAction(graphViewer);
		refresh = new RefreshLayoutAction(graphViewer);
		digIn = new DigInAction(graphViewer);
		showRoot = new ShowRootAction(graphViewer);
		group = new SelectLayoutGroup(layout, graphViewer);
		zoom = new ZoomContributionViewItem(this);
		saveImage = new SaveImageAction(graphViewer);

		State state = getState();
		state.addListener(stateListener);
	}

	@Override
	protected Viewer createViewer(Composite parent) {
		// create viewer
		graphViewer = new VisualisationGraphViewer(parent, SWT.NONE);
		graphViewer.setLayoutAlgorithm(layout.getDefault());
		graphViewer.setLabelProvider(labelprovider);
		graphViewer.setContentProvider(contentprovider);
		graphViewer.addFilter(new LocalContextFilter());
		graphViewer.setConnectionStyle(ZestStyles.CONNECTIONS_DIRECTED);
		// double click on nodes
		graphViewer.getGraphControl().addMouseListener(new MouseAdapter() {

			@Override
			public void mouseDoubleClick(MouseEvent e) {
				toggleOpen.run();

			}

		});

		graphViewer.getGraphControl().addKeyListener(new KeyAdapter() {

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
		getSite().setSelectionProvider(graphViewer);
		selectionSynchronizer = new VariableSelectionSynchronizer(getSite());
		return graphViewer;
	}

	@Override
	protected void fillContextMenu(IMenuManager menu) {
		// TODO Auto-generated method stub
		menu.add(toggleOpen);
		menu.add(hideNode);
		menu.add(showChilds);
		menu.add(new Separator());
		menu.add(digIn);
		menu.add(showRoot);
	}

	@Override
	protected String getHelpContextId() {
		// TODO Auto-generated method stub
		return null;
	}

	public void setStackFrame(IStackFrame stackframe) {
		StackFrameContextInput input = inputfactory.getInput(stackframe);
		labelprovider.setInput(input);
		graphViewer.setInput(input);
	}

	public AbstractZoomableViewer getZoomableViewer() {
		return graphViewer;
	}

	public void selectionChanged(SelectionChangedEvent event) {
		graphViewer.setSelection(event.getSelection());
	}

	@Override
	public void dispose() {
		super.dispose();
		if (listener != null)
			DebugUITools.getDebugContextManager().removeDebugContextListener(
					listener);
		getState().removeListener(stateListener);
		if (selectionSynchronizer != null) {
			selectionSynchronizer.dispose();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.debug.ui.AbstractDebugView#createPartControl(org.eclipse.
	 * swt.widgets.Composite)
	 */
	@Override
	public void createPartControl(Composite parent) {
		super.createPartControl(parent);
		createMenu();
	}

	/**
	 * Creates the menu GUI contributions
	 */
	private void createMenu() {
		IMenuManager imm = getViewSite().getActionBars().getMenuManager();
		imm.add(refresh);
		group.fillContextMenu(imm);
		imm.add(new Separator());
		imm.add(zoom);
		imm.add(saveImage);
	}

}
